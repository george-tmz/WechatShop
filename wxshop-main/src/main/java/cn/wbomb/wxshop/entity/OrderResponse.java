package cn.wbomb.wxshop.entity;

import cn.wbomb.api.generate.OrderTable;
import cn.wbomb.wxshop.generate.Shop;

import java.util.List;

public class OrderResponse extends OrderTable {
    private Shop shop;
    private List<GoodsWithNumber> goods;

    public OrderResponse() {

    }

    public OrderResponse(OrderTable order) {
        this.setId(order.getId());
        this.setUserId(order.getUserId());
        this.setTotalPrice(order.getTotalPrice());
        this.setAddress(order.getAddress());
        this.setExpressCompany(order.getExpressCompany());
        this.setExpressId(order.getExpressId());
        this.setStatus(order.getStatus());
        this.setCreatedAt(order.getCreatedAt());
        this.setUpdatedAt(order.getUpdatedAt());
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<GoodsWithNumber> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsWithNumber> goods) {
        this.goods = goods;
    }
}
