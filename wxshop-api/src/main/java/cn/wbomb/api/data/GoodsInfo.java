package cn.wbomb.api.data;

import java.io.Serializable;


public class GoodsInfo implements Serializable {
    private static final long serialVersionUID = -2428041198254054895L;

    private long id;

    private int number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
