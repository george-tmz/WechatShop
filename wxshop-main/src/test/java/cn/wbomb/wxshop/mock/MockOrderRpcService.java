package cn.wbomb.wxshop.mock;


import cn.wbomb.api.data.OrderInfo;
import cn.wbomb.api.generate.Order;
import cn.wbomb.api.rpc.OrderRpcService;

import org.apache.dubbo.config.annotation.Service;
import org.mockito.Mock;

@Service(version = "${wxshop.orderservice.version}")
public class MockOrderRpcService implements OrderRpcService {
    @Mock
    public OrderRpcService orderRpcService;

    @Override
    public Order createOrder(OrderInfo orderInfo, Order order) {
        return orderRpcService.createOrder(orderInfo, order);
    }
}
