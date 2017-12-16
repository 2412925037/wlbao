package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/2.
 *
 * 订单的实体类
 */

public class BillsItemBean extends BaseBean{
    public String id;
    public String dateAndTime;
    public int tradeState;      //交易状态   0：订单付款   1：订单未付款  -1：订单关闭
    public String content;
    public String price;
    public int number;
    public String backMoney;
    public String backBySeller;
    public String url;
    public int shop;            //1:天猫

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(String backMoney) {
        this.backMoney = backMoney;
    }

    public String getBackBySeller() {
        return backBySeller;
    }

    public void setBackBySeller(String backBySeller) {
        this.backBySeller = backBySeller;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }

    public int getTradeState() {
        return tradeState;
    }

    public void setTradeState(int tradeState) {
        this.tradeState = tradeState;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
