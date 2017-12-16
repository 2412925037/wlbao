package com.renhuikeji.wanlb.wanlibao.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/20.
 *
 * 个人中心类别的实体类
 */

public class MyFragmentItem implements Serializable {
    private int iconResoures;
    private String itemName;

    public int getIconResoures() {
        return iconResoures;
    }

    public void setIconResoures(int iconResoures) {
        this.iconResoures = iconResoures;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
