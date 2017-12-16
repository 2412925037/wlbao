package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 *
 * 搜索商品实体
 */

public class SearchGoodBean extends BaseBean {

    private String worngMsg;
    private double settleRate;
    private int goodsCount;
    private int goodsTotal;
    private double couponSettleRate;
    private int totalPages;
    private SearchGoodBean.ParaBean para;
    private List<SearchGoodBean.GoodsArrBean> goodsArr;

    public double getCouponSettleRate() {
        return couponSettleRate;
    }

    public void setCouponSettleRate(double couponSettleRate) {
        this.couponSettleRate = couponSettleRate;
    }

    public int getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(int goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

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

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ParaBean getPara() {
        return para;
    }

    public void setPara(SearchGoodBean.ParaBean para) {
        this.para = para;
    }

    public List<GoodsArrBean> getGoodsArr() {
        return goodsArr;
    }

    public void setGoodsArr(List<GoodsArrBean> goodsArr) {
        this.goodsArr = goodsArr;
    }

    public static class ParaBean {
        private String uid;
        private String apiKey;
        private String q;
        private String tq;
        private String tid;
        private String start_tk_rate;
        private String end_tk_rate;
        private String start_coupon;
        private String end_coupon;
        private String start_price;
        private String end_price;
        private String type;
        private String sort;
        private String p;

        public String getStart_tk_rate() {
            return start_tk_rate;
        }

        public void setStart_tk_rate(String start_tk_rate) {
            this.start_tk_rate = start_tk_rate;
        }

        public String getEnd_tk_rate() {
            return end_tk_rate;
        }

        public void setEnd_tk_rate(String end_tk_rate) {
            this.end_tk_rate = end_tk_rate;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getTq() {
            return tq;
        }

        public void setTq(String tq) {
            this.tq = tq;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getStart_coupon() {
            return start_coupon;
        }

        public void setStart_coupon(String start_coupon) {
            this.start_coupon = start_coupon;
        }

        public String getEnd_coupon() {
            return end_coupon;
        }

        public void setEnd_coupon(String end_coupon) {
            this.end_coupon = end_coupon;
        }

        public String getStart_price() {
            return start_price;
        }

        public void setStart_price(String start_price) {
            this.start_price = start_price;
        }

        public String getEnd_price() {
            return end_price;
        }

        public void setEnd_price(String end_price) {
            this.end_price = end_price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }

    public static class GoodsArrBean {
        private String num_iid;
        private String title;
        private String pict_url;
        private String click_url;
        private String zk_final_price;
        private String tk_rate;
        private String nick;
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
        private String isSeller;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getClick_url() {
            return click_url;
        }

        public void setClick_url(String click_url) {
            this.click_url = click_url;
        }

        public String getIsSeller() {
            return isSeller;
        }

        public void setIsSeller(String isSeller) {
            this.isSeller = isSeller;
        }

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
    }
}
