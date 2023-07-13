package cn.wbomb.wxshop.dao;

import cn.wbomb.api.data.GoodsInfo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsStockMapper {
    int deductStock(GoodsInfo goodsInfo);
}

