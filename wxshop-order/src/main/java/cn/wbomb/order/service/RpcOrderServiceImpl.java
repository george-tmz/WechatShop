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
import cn.wbomb.api.generate.OrderGoods;
import cn.wbomb.api.generate.OrderGoodsExample;
import cn.wbomb.api.generate.OrderTable;
import cn.wbomb.api.generate.OrderTableExample;
import cn.wbomb.api.rpc.OrderRpcService;
import cn.wbomb.order.generate.OrderGoodsMapper;
import cn.wbomb.order.generate.OrderTableMapper;
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
    private final OrderTableMapper orderMapper;

    private final MyOrderMapper myOrderMapper;

    private final OrderGoodsMapper orderGoodsMapper;

    @Autowired
    public RpcOrderServiceImpl(OrderTableMapper orderMapper, MyOrderMapper myOrderMapper, OrderGoodsMapper orderGoodsMapper) {
        this.orderMapper = orderMapper;
        this.myOrderMapper = myOrderMapper;
        this.orderGoodsMapper = orderGoodsMapper;
    }

    @Override
    public OrderTable createOrder(OrderInfo orderInfo, OrderTable order) {
        insertOrder(order);
        orderInfo.setOrderId(order.getId());
        myOrderMapper.insertOrders(orderInfo);
        return order;
    }

    @Override
    public OrderTable getOrderById(long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public RpcOrderGoods deleteOrder(long orderId, long userId) {
        OrderTable order = orderMapper.selectByPrimaryKey(orderId);
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
        OrderTableExample countByStatus = new OrderTableExample();
        setStatus(countByStatus, status);
        int count = (int) orderMapper.countByExample(countByStatus);

        OrderTableExample pagedOrder = new OrderTableExample();
        pagedOrder.setOffset((pageNum - 1) * pageSize);
        pagedOrder.setLimit(pageNum);
        setStatus(pagedOrder, status).andUserIdEqualTo(userId);

        List<OrderTable> orders = orderMapper.selectByExample(pagedOrder);

        List<Long> orderIds = orders.stream().map(OrderTable::getId).collect(toList());

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
    public RpcOrderGoods updateOrder(OrderTable order) {
        orderMapper.updateByPrimaryKey(order);

        List<GoodsInfo> goodsInfo = myOrderMapper.getGoodsInfoOfOrder(order.getId());
        RpcOrderGoods result = new RpcOrderGoods();
        result.setGoods(goodsInfo);
        result.setOrder(orderMapper.selectByPrimaryKey(order.getId()));
        return result;
    }

    private RpcOrderGoods toRpcOrderGoods(OrderTable order, Map<Long, List<OrderGoods>> orderIdToGoodsMap) {
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

    private OrderTableExample.Criteria setStatus(OrderTableExample orderExample, DataStatus status) {
        if (status == null) {
            return orderExample.createCriteria().andStatusNotEqualTo(DELETED.getName());
        } else {
            return orderExample.createCriteria().andStatusNotEqualTo(status.getName());
        }
    }

    private void insertOrder(OrderTable order) {
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
