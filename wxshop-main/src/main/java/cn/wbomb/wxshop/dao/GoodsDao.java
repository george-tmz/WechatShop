package cn.wbomb.wxshop.dao;

import cn.wbomb.api.DataStatus;
import cn.wbomb.wxshop.generate.Goods;
import cn.wbomb.wxshop.generate.GoodsMapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsDao {

    private final GoodsMapper goodsMapper;

    @Autowired
    public GoodsDao(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }


    public Goods insertGoods(Goods goods) {
        long id = goodsMapper.insert(goods);
        goods.setId(id);
        return goods;
    }

    public Goods deleteGoodsById(Long goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (goods == null) {
            throw new ResourceNotFoundException("商品未找到");
        }
        goods.setStatus(DataStatus.DELETED.getName());
        goods.setUpdatedAt(new Date());
        goodsMapper.updateByPrimaryKey(goods);
        return goods;
    }

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
