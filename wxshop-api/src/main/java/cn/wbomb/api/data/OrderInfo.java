package cn.wbomb.api.data;

import java.io.Serializable;
import java.util.List;

public class OrderInfo implements Serializable {

    private static final long serialVersionUID = -3822347837421149078L;

    private List<GoodsInfo> goods;

    public List<GoodsInfo> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsInfo> goods) {
        this.goods = goods;
    }
}
