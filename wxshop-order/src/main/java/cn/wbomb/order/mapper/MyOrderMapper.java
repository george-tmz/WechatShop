package cn.wbomb.order.mapper;

import cn.wbomb.api.data.GoodsInfo;
import cn.wbomb.api.data.OrderInfo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyOrderMapper {
    void insertOrders(OrderInfo orderInfo);

    List<GoodsInfo> getGoodsInfoOfOrder(long orderId);
}
