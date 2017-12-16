package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * 二级分类，相当于右侧菜单
 * Created by hanj on 14-9-25.
 */
public class SecondClassItem {
    private String url;
    private String name;

    public SecondClassItem() {
    }

    public SecondClassItem(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
