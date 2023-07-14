package cn.wbomb.wxshop.mock;


import cn.wbomb.api.DataStatus;
import cn.wbomb.api.data.OrderInfo;
import cn.wbomb.api.data.PageResponse;
import cn.wbomb.api.data.RpcOrderGoods;
import cn.wbomb.api.generate.OrderTable;
import cn.wbomb.api.rpc.OrderRpcService;

import org.apache.dubbo.config.annotation.Service;
import org.mockito.Mock;

@Service(version = "${wxshop.orderservice.version}")
public class MockOrderRpcService implements OrderRpcService {
    @Mock
    public OrderRpcService orderRpcService;

    @Override
    public OrderTable createOrder(OrderInfo orderInfo, OrderTable order) {
        return orderRpcService.createOrder(orderInfo, order);
    }

    @Override
    public OrderTable getOrderById(long orderId) {
        return orderRpcService.getOrderById(orderId);
    }

    @Override
    public RpcOrderGoods deleteOrder(long orderId, long userId) {
        return orderRpcService.deleteOrder(orderId, userId);
    }

    @Override
    public PageResponse<RpcOrderGoods> getOrder(long userId, Integer pageNum, Integer pageSize, DataStatus status) {
        return orderRpcService.getOrder(userId, pageNum, pageSize, status);
    }

    @Override
    public RpcOrderGoods updateOrder(OrderTable order) {
        return orderRpcService.updateOrder(order);
    }
}
