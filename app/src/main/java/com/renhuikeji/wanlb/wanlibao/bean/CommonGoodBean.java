package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/6.
 *
 * 普通商品实体
 */

public class CommonGoodBean extends BaseBean {

    private String worngMsg;
    private double settleRate;
    private int totalPages;
    private int goodsCount;
    private ParaBean para;
    private List<GoodsArrBean> goodsArr;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
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
         * q : 汽车挂件
         * type : 1
         */

        private String q;
        private String type;

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
    }

    public static class GoodsArrBean {
        /**
         * num_iid : 41659194717
         * title : 高档纯天然红玛瑙平安扣汽车挂件挂饰 车载镶钻吊坠辟邪保平安符
         * pict_url : http://img4.tbcdn.cn/tfscom/i1/TB1tYEzHXXXXXcMXVXXXXXXXXXX_!!0-item_pic.jpg
         * click_url : https://s.click.taobao.com/t?e=m%3D2%26s%3D99GlL5pWk4kcQipKwQzePOeEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67qcLib1rY98gPx3RkWSJTuwNlVQNDDDJBh7ZRek1C91pwxf6pM2WCbjy%2FLrTErXGYSY7GArwmmx1pZO8DRG5kdtfoXZI%2BKguwbkvXelkEs6E%2FphjKDmKW801L3D%2Bibr2Op6dl%2FjrIDfKGzjkCIBQkF%2F%2BMR7KufDmxSsj5LBFxdSkIYULNg46oBA%3D
         * zk_final_price : 128.00
         * tk_rate : 1.50
         * reserve_price : 128.00
         * user_type : 0
         * volume : 0
         * itemloc : 安徽 合肥
         * event_end_time : 2017-06-08 23:59:59
         * coupon : 0.00
         * coupon_url : null
         * isCoupon : 0
         * coupon_end_date : null
         * isSeller : 1
         * score : 1
         * item_url : http://h5.m.taobao.com/awp/core/detail.htm?id=43679473468
         * nick : 静穆尊贵
         * provcity : 福建 厦门
         * seller_id : 1889354930
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
        private String isSeller;
        private String score;
        private String item_url;
        private String nick;
        private String provcity;
        private long seller_id;

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

        public String getIsSeller() {
            return isSeller;
        }

        public void setIsSeller(String isSeller) {
            this.isSeller = isSeller;
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

        public long getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(long seller_id) {
            this.seller_id = seller_id;
        }
    }
}
