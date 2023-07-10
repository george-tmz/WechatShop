package cn.wbomb.wxshop.dao;

import cn.wbomb.wxshop.generate.Shop;
import cn.wbomb.wxshop.generate.ShopMapper;
import cn.wbomb.wxshop.generate.UserMapper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopDao {

    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public ShopDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Shop findShopById(Long shopId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            ShopMapper shopMapper = sqlSession.getMapper(ShopMapper.class);
            return shopMapper.selectByPrimaryKey(shopId);
        }
    }
}
