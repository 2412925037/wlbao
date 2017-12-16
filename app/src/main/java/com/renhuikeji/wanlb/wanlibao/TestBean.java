package com.renhuikeji.wanlb.wanlibao;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class TestBean {

    /**
     * result : SUCESS
     * settleRate : 0.5
     * totalPages : 500
     * goodsCount : 10000
     * para : {"q":"\"帽子\"","type":"1","p":"1"}
     */

    private String result;
    private double settleRate;
    private int totalPages;
    private int goodsCount;
    private ParaBean para;
    private List<GoodsArrBean> goodsArr;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getSettleRate() {
        return settleRate;
    }

    public void setSettleRate(double settleRate) {
        this.settleRate = settleRate;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public ParaBean getPara() {
        return para;
    }

    public void setPara(ParaBean para) {
        this.para = para;
    }

    public List<GoodsArrBean> getGoodsArr() {
        return goodsArr;
    }

    public void setGoodsArr(List<GoodsArrBean> goodsArr) {
        this.goodsArr = goodsArr;
    }

    public static class ParaBean {
        /**
         * q : "帽子"
         * type : 1
         * p : 1
         */

        private String q;
        private String type;
        private String p;

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }

    public static class GoodsArrBean {
        /**
         * num_iid : 545144283504
         * title : 帽子女士夏天韩版潮时尚棒球帽男休闲百搭太阳帽春秋季青年鸭舌帽
         * pict_url : http://img02.taobaocdn.com/bao/uploaded/i2/TB1I2GeQpXXXXb.XXXXXXXXXXXX_!!0-item_pic.jpg
         * click_url : https://s.click.taobao.com/t?e=m%3D2%26s%3D%2BFYkknHyUW0cQipKwQzePOeEDrYVVa64K7Vc7tFgwiFRAdhuF14FMRa2Z4IPo0p%2B8sviUM61dt0vlC0sBtnXJwIsctzvbhNbFWHrLfRfzUgoCRqpj7DQcCYTn%2B%2FQ90GWD4sSHHSJqFOwoIKYQis77JVHnnAYXQdVxg5p7bh%2BFbQ%3D
         * zk_final_price : 9.90
         * tk_rate : 5.50
         * reserve_price : 98.00
         * user_type : 1
         * volume : 100209
         * itemloc : 浙江 金华
         * event_end_time : 2017-05-01 23:59:59
         * coupon : 0.00
         * coupon_url : https://uland.taobao.com/coupon/edetail?e=B6%2Fjp1ovoYia2P%2BN2ppgB2XOs4W9Ff10FrAA%2FtV2bXy70kYCOm8W4LtLmdOqMMWWUjOtHefbGTQ24%2BsnHzWYeR0HgBdG%2FDDL%2F1M%2FBw7Sf%2FdbZIFaH8mWA0gNAiCookA%2FBeDkRM8BF24sFVOlvMCU4avNT%2BVXEC9s&pid=mm_56050984_12662588_55952292&af=1
         * isCoupon : 0
         * coupon_end_date : 2017-05-10
         * score : 1
         * item_url : http://h5.m.taobao.com/awp/core/detail.htm?id=527296598880
         * nick : 方大井
         * provcity : 浙江 金华
         * seller_id : 341393797
         */

        private String num_iid;
        private String title;
        private String pict_url;
        private String click_url;
        private String zk_final_price;
        private String tk_rate;
        private String reserve_price;
        private String user_type;
        private String volume;
        private String itemloc;
        private String event_end_time;
        private String coupon;
        private String coupon_url;
        private String isCoupon;
        private String coupon_end_date;
        private String score;
        private String item_url;
        private String nick;
        private String provcity;
        private String seller_id;

        public String getNum_iid() {
            return num_iid;
        }

        public void setNum_iid(String num_iid) {
            this.num_iid = num_iid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPict_url() {
            return pict_url;
        }

        public void setPict_url(String pict_url) {
            this.pict_url = pict_url;
        }

        public String getClick_url() {
            return click_url;
        }

        public void setClick_url(String click_url) {
            this.click_url = click_url;
        }

        public String getZk_final_price() {
            return zk_final_price;
        }

        public void setZk_final_price(String zk_final_price) {
            this.zk_final_price = zk_final_price;
        }

        public String getTk_rate() {
            return tk_rate;
        }

        public void setTk_rate(String tk_rate) {
            this.tk_rate = tk_rate;
        }

        public String getReserve_price() {
            return reserve_price;
        }

        public void setReserve_price(String reserve_price) {
            this.reserve_price = reserve_price;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getItemloc() {
            return itemloc;
        }

        public void setItemloc(String itemloc) {
            this.itemloc = itemloc;
        }

        public String getEvent_end_time() {
            return event_end_time;
        }

        public void setEvent_end_time(String event_end_time) {
            this.event_end_time = event_end_time;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getCoupon_url() {
            return coupon_url;
        }

        public void setCoupon_url(String coupon_url) {
            this.coupon_url = coupon_url;
        }

        public String getIsCoupon() {
            return isCoupon;
        }

        public void setIsCoupon(String isCoupon) {
            this.isCoupon = isCoupon;
        }

        public String getCoupon_end_date() {
            return coupon_end_date;
        }

        public void setCoupon_end_date(String coupon_end_date) {
            this.coupon_end_date = coupon_end_date;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getItem_url() {
            return item_url;
        }

        public void setItem_url(String item_url) {
            this.item_url = item_url;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getProvcity() {
            return provcity;
        }

        public void setProvcity(String provcity) {
            this.provcity = provcity;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }
    }
}
