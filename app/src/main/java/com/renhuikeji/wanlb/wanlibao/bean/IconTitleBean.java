package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/10/20.
 * 图片+title
 */

public class IconTitleBean {

    private int resId;
    private String title;

    public IconTitleBean(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
