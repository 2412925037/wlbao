package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/4/21.
 */

public class HomeRecyBean {
    public String id;
    public String shopname;
    public String goodIntroduce;
    public String imgUrl;
    public String oldprices;
    public String newprices;
    public String address;
    public String sales;  //销量
    public String valid_time; //有效时间
    //    public boolean is_havequan; //是否有券
    public int type;//2:超级返  3：有券  1：普通
    public String quan_num;  //券价格
    public String quan_hou_num; //券后价格
    public String return_num; //领券返额

    public String nick;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getReturn_num() {
        return return_num;
    }

    public void setReturn_num(String return_num) {
        this.return_num = return_num;
    }

    public String fan_num;  //返利num
    public String fan_bi;  //返利比例
    public String isSeller;//是否推荐   1：推荐
    public int defaultimg;
    public String click_url;//点击跳转网址
    public String user_type;//商品类型  1：天猫    0：淘宝

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getClick_url() {
        return click_url;
    }

    public void setClick_url(String click_url) {
        this.click_url = click_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getGoodIntroduce() {
        return goodIntroduce;
    }

    public void setGoodIntroduce(String goodIntroduce) {
        this.goodIntroduce = goodIntroduce;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getOldprices() {
        return oldprices;
    }

    public void setOldprices(String oldprices) {
        this.oldprices = oldprices;
    }

    public String getNewprices() {
        return newprices;
    }

    public void setNewprices(String newprices) {
        this.newprices = newprices;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getValid_time() {
        return valid_time;
    }

    public void setValid_time(String valid_time) {
        this.valid_time = valid_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuan_num() {
        return quan_num;
    }

    public void setQuan_num(String quan_num) {
        this.quan_num = quan_num;
    }

    public String getQuan_hou_num() {
        return quan_hou_num;
    }

    public void setQuan_hou_num(String quan_hou_num) {
        this.quan_hou_num = quan_hou_num;
    }

    public String getFan_num() {
        return fan_num;
    }

    public void setFan_num(String fan_num) {
        this.fan_num = fan_num;
    }

    public String getFan_bi() {
        return fan_bi;
    }

    public void setFan_bi(String fan_bi) {
        this.fan_bi = fan_bi;
    }

    public String getIsSeller() {
        return isSeller;
    }

    public void setIsSeller(String isSeller) {
        this.isSeller = isSeller;
    }

    public int getDefaultimg() {
        return defaultimg;
    }

    public void setDefaultimg(int defaultimg) {
        this.defaultimg = defaultimg;
    }
}
