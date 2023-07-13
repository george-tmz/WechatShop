package cn.wbomb.api.rpc;

import cn.wbomb.api.data.OrderInfo;
import cn.wbomb.api.generate.Order;

public interface OrderRpcService {

    Order createOrder(OrderInfo orderInfo, Order order);
}
