package cn.wbomb.api.data;

import cn.wbomb.api.generate.OrderTable;

import java.io.Serializable;
import java.util.List;

public class RpcOrderGoods implements Serializable {
    private OrderTable order;
    private List<GoodsInfo> goods;

    public OrderTable getOrder() {
        return order;
    }

    public void setOrder(OrderTable order) {
        this.order = order;
    }

    public List<GoodsInfo> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsInfo> goods) {
        this.goods = goods;
    }
}
