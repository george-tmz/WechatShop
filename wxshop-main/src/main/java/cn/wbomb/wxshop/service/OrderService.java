package cn.wbomb.wxshop.service;

import static java.util.stream.Collectors.toList;

import cn.wbomb.api.DataStatus;
import cn.wbomb.api.data.GoodsInfo;
import cn.wbomb.api.data.OrderInfo;
import cn.wbomb.api.data.PageResponse;
import cn.wbomb.api.data.RpcOrderGoods;
import cn.wbomb.api.generate.OrderTable;
import cn.wbomb.api.rpc.OrderRpcService;
import cn.wbomb.wxshop.dao.GoodsStockMapper;
import cn.wbomb.wxshop.entity.GoodsWithNumber;
import cn.wbomb.wxshop.entity.OrderResponse;
import cn.wbomb.api.exception.HttpException;
import cn.wbomb.wxshop.generate.Goods;
import cn.wbomb.wxshop.generate.Shop;
import cn.wbomb.wxshop.generate.ShopMapper;
import cn.wbomb.wxshop.generate.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderService {
    @Reference(version = "${wxshop.orderservice.version}")
    private OrderRpcService orderRpcService;

    private final UserMapper userMapper;

    private final GoodsStockMapper goodsStockMapper;

    private final GoodsService goodsService;

    private final ShopMapper shopMapper;


    @Autowired
    public OrderService(UserMapper userMapper,
                        GoodsStockMapper goodsStockMapper,
                        ShopMapper shopMapper,
                        GoodsService goodsService) {
        this.userMapper = userMapper;
        this.shopMapper = shopMapper;
        this.goodsService = goodsService;
        this.goodsStockMapper = goodsStockMapper;
    }

    public OrderResponse createOrder(OrderInfo orderInfo, Long userId) {
        Map<Long, Goods> idToGoodsMap = getIdToGoodsMap(orderInfo.getGoods());
        OrderTable createdOrder = createOrderViaRpc(orderInfo, userId, idToGoodsMap);
        return generateResponse(createdOrder, idToGoodsMap, orderInfo.getGoods());
    }

    private OrderResponse generateResponse(OrderTable createdOrder, Map<Long, Goods> idToGoodsMap, List<GoodsInfo> goodsInfo) {
        OrderResponse response = new OrderResponse(createdOrder);

        Long shopId = new ArrayList<>(idToGoodsMap.values()).get(0).getShopId();
        response.setShop(shopMapper.selectByPrimaryKey(shopId));
        response.setGoods(
            goodsInfo
                .stream()
                .map(goods -> toGoodsWithNumber(goods, idToGoodsMap))
                .collect(toList())
        );

        return response;
    }

    private Map<Long, Goods> getIdToGoodsMap(List<GoodsInfo> goodsInfo) {
        List<Long> goodsId = goodsInfo
            .stream()
            .map(GoodsInfo::getId)
            .collect(toList());
        return goodsService.getIdToGoodsMap(goodsId);
    }

    private OrderTable createOrderViaRpc(OrderInfo orderInfo, Long userId, Map<Long, Goods> idToGoodsMap) {
        OrderTable order = new OrderTable();
        order.setUserId(userId);
        order.setStatus(DataStatus.PENDING.getName());
        order.setAddress(userMapper.selectByPrimaryKey(userId).getAddress());
        order.setTotalPrice(calculateTotalPrice(orderInfo, idToGoodsMap));

        return orderRpcService.createOrder(orderInfo, order);
    }

    /*
     * 扣减库存
     */
    @Transactional
    public void deductStock(OrderInfo orderInfo) {
        for (GoodsInfo goodsInfo : orderInfo.getGoods()) {
            if (goodsStockMapper.deductStock(goodsInfo) <= 0) {
                log.error("扣减库存失败, 商品id: " + goodsInfo.getId() + "，数量：" + goodsInfo.getNumber());
                throw HttpException.gone("扣减库存失败！");
            }
        }
    }

    private GoodsWithNumber toGoodsWithNumber(GoodsInfo goodsInfo, Map<Long, Goods> idToGoodsMap) {
        GoodsWithNumber ret = new GoodsWithNumber(idToGoodsMap.get(goodsInfo.getId()));
        ret.setNumber(goodsInfo.getNumber());
        return ret;
    }

    private long calculateTotalPrice(OrderInfo orderInfo, Map<Long, Goods> idToGoodsMap) {
        long result = 0;

        for (GoodsInfo goodsInfo : orderInfo.getGoods()) {
            Goods goods = idToGoodsMap.get(goodsInfo.getId());
            if (goods == null) {
                throw HttpException.badRequest("goods id非法：" + goodsInfo.getId());
            }
            if (goodsInfo.getNumber() <= 0) {
                throw HttpException.badRequest("number非法：" + goodsInfo.getNumber());
            }

            result = result + goods.getPrice() * goodsInfo.getNumber();
        }

        return result;
    }

    public OrderResponse deleteOrder(long orderId, long userId) {
        return toOrderResponse(orderRpcService.deleteOrder(orderId, userId));
    }

    private OrderResponse toOrderResponse(RpcOrderGoods rpcOrderGoods) {
        Map<Long, Goods> idToGoodsMap = getIdToGoodsMap(rpcOrderGoods.getGoods());
        return generateResponse(rpcOrderGoods.getOrder(), idToGoodsMap, rpcOrderGoods.getGoods());
    }


    public PageResponse<OrderResponse> getOrder(long userId, Integer pageNum, Integer pageSize, DataStatus status) {
        PageResponse<RpcOrderGoods> rpcOrderGoods = orderRpcService.getOrder(userId, pageNum, pageSize, status);

        List<GoodsInfo> goodIds = rpcOrderGoods
            .getData()
            .stream()
            .map(RpcOrderGoods::getGoods)
            .flatMap(List::stream)
            .collect(toList());

        Map<Long, Goods> idToGoodsMap = getIdToGoodsMap(goodIds);

        List<OrderResponse> orders = rpcOrderGoods.getData()
            .stream()
            .map(order -> generateResponse(order.getOrder(), idToGoodsMap, order.getGoods()))
            .collect(toList());


        return PageResponse.pagedData(
            rpcOrderGoods.getPageNum(),
            rpcOrderGoods.getPageSize(),
            rpcOrderGoods.getTotalPage(),
            orders
        );
    }

    public OrderResponse updateExpressInformation(OrderTable order, long userId) {
        OrderTable orderInDatabase = orderRpcService.getOrderById(order.getId());
        if (orderInDatabase == null) {
            throw HttpException.notFound("订单未找到: " + order.getId());
        }

        Shop shop = shopMapper.selectByPrimaryKey(orderInDatabase.getShopId());
        if (shop == null) {
            throw HttpException.notFound("店铺未找到: " + orderInDatabase.getShopId());
        }

        if (shop.getOwnerUserId() != userId) {
            throw HttpException.forbidden("无权访问！");
        }

        OrderTable copy = new OrderTable();
        copy.setId(order.getId());
        copy.setExpressId(order.getExpressId());
        copy.setExpressCompany(order.getExpressCompany());
        return toOrderResponse(orderRpcService.updateOrder(copy));
    }

    public OrderResponse updateOrderStatus(OrderTable order, long userId) {
        OrderTable orderInDatabase = orderRpcService.getOrderById(order.getId());
        if (orderInDatabase == null) {
            throw HttpException.notFound("订单未找到: " + order.getId());
        }

        if (orderInDatabase.getUserId() != userId) {
            throw HttpException.forbidden("无权访问！");
        }

        OrderTable copy = new OrderTable();
        copy.setStatus(order.getStatus());
        return toOrderResponse(orderRpcService.updateOrder(copy));
    }
}
