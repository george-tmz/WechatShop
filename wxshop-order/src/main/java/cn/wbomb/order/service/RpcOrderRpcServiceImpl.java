package cn.wbomb.order.service;

import cn.wbomb.api.DataStatus;
import cn.wbomb.api.data.OrderInfo;
import cn.wbomb.api.generate.Order;
import cn.wbomb.api.generate.OrderMapper;
import cn.wbomb.api.rpc.OrderRpcService;
import cn.wbomb.order.mapper.MyOrderMapper;

import java.util.Date;
import java.util.function.BooleanSupplier;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "${wxshop.orderservice.version}")
public class RpcOrderRpcServiceImpl implements OrderRpcService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MyOrderMapper myOrderMapper;

    @Override
    public Order createOrder(OrderInfo orderInfo, Order order) {
        insertOrder(order);
        myOrderMapper.insertOrders(orderInfo);
        return order;
    }

    private void insertOrder(Order order) {
        order.setStatus(DataStatus.PENDING.getName());

        verify(() -> order.getUserId() == null, "userI is not null");
        verify(() -> order.getTotalPrice() == null || order.getTotalPrice() < 0, "totalPrice is illegal");
        verify(() -> order.getAddress() == null, "Address is not null");

        order.setExpressCompany(null);
        order.setExpressId(null);
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());

        long id = orderMapper.insert(order);
        order.setId(id);
    }

    private void verify(BooleanSupplier booleanSupplier, String message) {
        if (booleanSupplier.getAsBoolean()) {
            throw new IllegalArgumentException(message);
        }
    }
}
