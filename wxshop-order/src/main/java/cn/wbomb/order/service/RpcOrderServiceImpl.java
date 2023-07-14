package cn.wbomb.order.service;

import static cn.wbomb.api.DataStatus.DELETED;
import static cn.wbomb.api.DataStatus.PENDING;
import static java.util.stream.Collectors.toList;

import cn.wbomb.api.DataStatus;
import cn.wbomb.api.data.GoodsInfo;
import cn.wbomb.api.data.OrderInfo;
import cn.wbomb.api.data.PageResponse;
import cn.wbomb.api.data.RpcOrderGoods;
import cn.wbomb.api.exception.HttpException;
import cn.wbomb.api.generate.Order;
import cn.wbomb.api.generate.OrderExample;
import cn.wbomb.api.generate.OrderGoods;
import cn.wbomb.api.generate.OrderGoodsExample;
import cn.wbomb.api.generate.OrderGoodsMapper;
import cn.wbomb.api.generate.OrderMapper;
import cn.wbomb.api.rpc.OrderRpcService;
import cn.wbomb.order.mapper.MyOrderMapper;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "${wxshop.orderservice.version}")
public class RpcOrderServiceImpl implements OrderRpcService {
    private final OrderMapper orderMapper;

    private final MyOrderMapper myOrderMapper;

    private final OrderGoodsMapper orderGoodsMapper;

    @Autowired
    public RpcOrderServiceImpl(OrderMapper orderMapper, MyOrderMapper myOrderMapper, OrderGoodsMapper orderGoodsMapper) {
        this.orderMapper = orderMapper;
        this.myOrderMapper = myOrderMapper;
        this.orderGoodsMapper = orderGoodsMapper;
    }

    @Override
    public Order createOrder(OrderInfo orderInfo, Order order) {
        insertOrder(order);
        orderInfo.setOrderId(order.getId());
        myOrderMapper.insertOrders(orderInfo);
        return order;
    }

    @Override
    public Order getOrderById(long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public RpcOrderGoods deleteOrder(long orderId, long userId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw HttpException.notFound("订单未找到: " + orderId);
        }
        if (order.getUserId() != userId) {
            throw HttpException.forbidden("无权访问！");
        }

        List<GoodsInfo> goodsInfo = myOrderMapper.getGoodsInfoOfOrder(orderId);

        order.setStatus(DELETED.getName());
        order.setUpdatedAt(new Date());
        orderMapper.updateByPrimaryKey(order);

        RpcOrderGoods result = new RpcOrderGoods();
        result.setGoods(goodsInfo);
        result.setOrder(order);
        return result;
    }

    @Override
    public PageResponse<RpcOrderGoods> getOrder(long userId,
                                                Integer pageNum,
                                                Integer pageSize,
                                                DataStatus status) {
        OrderExample countByStatus = new OrderExample();
        setStatus(countByStatus, status);
        int count = (int) orderMapper.countByExample(countByStatus);

        OrderExample pagedOrder = new OrderExample();
        pagedOrder.setOffset((pageNum - 1) * pageSize);
        pagedOrder.setLimit(pageNum);
        setStatus(pagedOrder, status).andUserIdEqualTo(userId);

        List<Order> orders = orderMapper.selectByExample(pagedOrder);

        List<Long> orderIds = orders.stream().map(Order::getId).collect(toList());

        OrderGoodsExample selectByOrderIds = new OrderGoodsExample();
        selectByOrderIds.createCriteria().andOrderIdIn(orderIds);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(selectByOrderIds);

        int totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        Map<Long, List<OrderGoods>> orderIdToGoodsMap = orderGoods
            .stream()
            .collect(Collectors.groupingBy(OrderGoods::getOrderId, toList()));

        List<RpcOrderGoods> rpcOrderGoods = orders.stream()
            .map(order -> toRpcOrderGoods(order, orderIdToGoodsMap))
            .collect(toList());

        return PageResponse.pagedData(pageNum,
            pageSize,
            totalPage,
            rpcOrderGoods);
    }

    @Override
    public RpcOrderGoods updateOrder(Order order) {
        orderMapper.updateByPrimaryKey(order);

        List<GoodsInfo> goodsInfo = myOrderMapper.getGoodsInfoOfOrder(order.getId());
        RpcOrderGoods result = new RpcOrderGoods();
        result.setGoods(goodsInfo);
        result.setOrder(orderMapper.selectByPrimaryKey(order.getId()));
        return result;
    }

    private RpcOrderGoods toRpcOrderGoods(Order order, Map<Long, List<OrderGoods>> orderIdToGoodsMap) {
        RpcOrderGoods result = new RpcOrderGoods();
        result.setOrder(order);
        List<GoodsInfo> goodsInfos = orderIdToGoodsMap
            .getOrDefault(order.getId(), Collections.emptyList())
            .stream()
            .map(this::toGoodsInfo)
            .collect(toList());
        result.setGoods(goodsInfos);
        return result;
    }

    private GoodsInfo toGoodsInfo(OrderGoods orderGoods) {
        GoodsInfo result = new GoodsInfo();
        result.setId(orderGoods.getGoodsId());
        result.setNumber(orderGoods.getNumber().intValue());
        return result;
    }

    private OrderExample.Criteria setStatus(OrderExample orderExample, DataStatus status) {
        if (status == null) {
            return orderExample.createCriteria().andStatusNotEqualTo(DELETED.getName());
        } else {
            return orderExample.createCriteria().andStatusNotEqualTo(status.getName());
        }
    }

    private void insertOrder(Order order) {
        order.setStatus(PENDING.getName());

        verify(() -> order.getUserId() == null, "userId不能为空！");
        verify(() -> order.getTotalPrice() == null || order.getTotalPrice().doubleValue() < 0, "totalPrice非法！");
        verify(() -> order.getAddress() == null, "address不能为空！");

        order.setExpressCompany(null);
        order.setExpressId(null);
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());

        orderMapper.insert(order);
    }

    private void verify(BooleanSupplier supplier, String message) {
        if (supplier.getAsBoolean()) {
            throw new IllegalArgumentException(message);
        }
    }
}
