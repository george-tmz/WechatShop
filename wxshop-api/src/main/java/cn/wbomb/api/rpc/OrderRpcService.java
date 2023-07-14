package cn.wbomb.api.rpc;

import cn.wbomb.api.DataStatus;
import cn.wbomb.api.data.OrderInfo;
import cn.wbomb.api.data.PageResponse;
import cn.wbomb.api.data.RpcOrderGoods;
import cn.wbomb.api.generate.Order;

public interface OrderRpcService {

    Order createOrder(OrderInfo orderInfo, Order order);

    Order getOrderById(long orderId);

    RpcOrderGoods deleteOrder(long orderId, long userId);

    PageResponse<RpcOrderGoods> getOrder(long userId, Integer pageNum, Integer pageSize, DataStatus status);

    RpcOrderGoods updateOrder(Order order);
}
