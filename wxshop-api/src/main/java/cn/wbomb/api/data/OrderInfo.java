package cn.wbomb.api.data;

import java.io.Serializable;
import java.util.List;

public class OrderInfo implements Serializable {

    private static final long serialVersionUID = -3822347837421149078L;

    private long orderId;
    private List<GoodsInfo> goods;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<GoodsInfo> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsInfo> goods) {
        this.goods = goods;
    }
}
