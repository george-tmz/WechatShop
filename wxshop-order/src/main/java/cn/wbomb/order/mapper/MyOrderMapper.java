package cn.wbomb.order.mapper;

import cn.wbomb.api.data.OrderInfo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyOrderMapper {
    void insertOrders(OrderInfo orderInfo);
}
