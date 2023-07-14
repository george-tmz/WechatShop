package cn.wbomb.api.rpc;

import cn.wbomb.api.DataStatus;
import cn.wbomb.api.data.OrderInfo;
import cn.wbomb.api.data.PageResponse;
import cn.wbomb.api.data.RpcOrderGoods;
import cn.wbomb.api.generate.OrderTable;

public interface OrderRpcService {

    OrderTable createOrder(OrderInfo orderInfo, OrderTable order);

    OrderTable getOrderById(long orderId);

    RpcOrderGoods deleteOrder(long orderId, long userId);

    PageResponse<RpcOrderGoods> getOrder(long userId, Integer pageNum, Integer pageSize, DataStatus status);

    RpcOrderGoods updateOrder(OrderTable order);
}
