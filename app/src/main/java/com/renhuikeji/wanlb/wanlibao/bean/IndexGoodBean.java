package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 *
 * 首页商品实体
 */

public class IndexGoodBean extends BaseBean{
    private MallArrBean mallArr;
    private double settleRate;
    private List<SalesArrBean> salesArr;
    private List<GoodsArrBean> goodsArr;
    private List<CatArrBean> catArr;
    private String worngMsg;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }

    public MallArrBean getMallArr() {
        return mallArr;
    }

    public void setMallArr(MallArrBean mallArr) {
        this.mallArr = mallArr;
    }

    public double getSettleRate() {
        return settleRate;
    }

    public void setSettleRate(double settleRate) {
        this.settleRate = settleRate;
    }


    public List<SalesArrBean> getSalesArr() {
        return salesArr;
    }

    public void setSalesArr(List<SalesArrBean> salesArr) {
        this.salesArr = salesArr;
    }

    public List<GoodsArrBean> getGoodsArr() {
        return goodsArr;
    }

    public void setGoodsArr(List<GoodsArrBean> goodsArr) {
        this.goodsArr = goodsArr;
    }

    public List<CatArrBean> getCatArr() {
        return catArr;
    }

    public void setCatArr(List<CatArrBean> catArr) {
        this.catArr = catArr;
    }

    public static class MallArrBean extends BaseBean {
        /**
         * mogujie : http://www.mogujie.com/cps/open/track?uid=13p36mk&feedback=&channel=&target=http%3A%2F%2Fm.mogujie.com
         * jd : http://union.click.jd.com/jdc?e=&p=AyIHVCtaJQMiQwpDBUoyS0IQWlALHE4YDk5ER1xONwtSLxx3YgMHezBzeXtbMBpTd0F5ezUXVyUHEwNXGlIUChQ3VRpSEQAVBl0bayVkbzcedVolAiIHUB9dHQIaD1AdXRQEIgBlG1oTChoDXBpcHGxON2Ua&t=W1dCFBBFC14NXAAECUte
         * gome : http://m.gome.com.cn?cmpid=cps_7872_9659_&sid=7872&wid=9659&feedback=
         * suning : https://sucs.suning.com/visitor.htm?userId=8083505&webSiteId=0&adInfoId=0&adBookId=0&channel=14&subUserEx=&vistURL=http://m.suning.com
         * vip : http://click.union.vip.com/redirect.php?url=eyJzY2hlbWVjb2RlIjoiMmY0MXpnaGEiLCJkZXN0dXJsIjoiaHR0cDpcL1wvbS52aXAuY29tIiwidWNvZGUiOiJzZzA5dGhhZyJ9&chan=
         * meilishuo : http://m.meilishuo.com?nmref=NM_s13072_0_&channel=40106&pstrc=NM_s13072_0_
         * yhd : http://click.yhd.com/?ut=103585641&s=OTQ2OGQ2NzNlMjcxM2JiNDkyNTUwZGVkZjYwY2UxNTY4ODBiN2IzNzczODhiZThkNjllMTU4Y2FhMzlmMzQyM2Q5ODhiNzM1NmY4ZDlhOGJhMDVlZTczYmYzNWY3NzNk&cv=1&website_id=myasbao&uid=
         * jiu : https://temai.m.taobao.com/cheap.htm?pid=mm_56050984_11110270_55994110
         * te : https://h5.m.taobao.com/market/tejia/tttjjpcx.html?spm=a3126.7698742.1998640214.7&prepvid=201_10.177.98.12_235668235696_1487558088578&extra=&pid=mm_56050984_12662588_55952292&env&unid&hidebar&isout&clk1&smart&rb
         * ai : https://ai.m.taobao.com/cat.html?spm=a311n.7676423.10029.1&prepvid=201_10.184.72.33_255718255794_1487411346219&extra=&pid=mm_56050984_11110270_55994110&env&unid&hidebar&isout&clk1&smart&rb
         * travel : https://s.click.taobao.com/t?e=m%3D2%26s%3DkMvchHg%2F0UgcQipKwQzePCperVdZeJviEViQ0P1Vf2kguMN8XjClAhfWGKZLUubUumD%2Bcnjvyyogc1BSIVmrhFG84aQ1Vn4V3SA6Sl2iOtmyd5owU1PbF3cJvP5UHXuoCOYKCMu0i0ncHtRpEUy6RAJ15mV%2F6I7leEV%2BM%2BWE6men25NGaver4GfZtqEP54ovALOvC7LQ70yUB7RP8CFvRuCw0ic3Y8CsccaDuq6T7n5%2BrNyGPSC2%2B8Yl7w3%2FA2kb
         */

        private String mogujie;
        private String jd;
        private String gome;
        private String suning;
        private String vip;
        private String meilishuo;
        private String yhd;
        private String jiu;
        private String te;
        private String ai;
        private String travel;

        public String getMogujie() {
            return mogujie;
        }

        public void setMogujie(String mogujie) {
            this.mogujie = mogujie;
        }

        public String getJd() {
            return jd;
        }

        public void setJd(String jd) {
            this.jd = jd;
        }

        public String getGome() {
            return gome;
        }

        public void setGome(String gome) {
            this.gome = gome;
        }

        public String getSuning() {
            return suning;
        }

        public void setSuning(String suning) {
            this.suning = suning;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getMeilishuo() {
            return meilishuo;
        }

        public void setMeilishuo(String meilishuo) {
            this.meilishuo = meilishuo;
        }

        public String getYhd() {
            return yhd;
        }

        public void setYhd(String yhd) {
            this.yhd = yhd;
        }

        public String getJiu() {
            return jiu;
        }

        public void setJiu(String jiu) {
            this.jiu = jiu;
        }

        public String getTe() {
            return te;
        }

        public void setTe(String te) {
            this.te = te;
        }

        public String getAi() {
            return ai;
        }

        public void setAi(String ai) {
            this.ai = ai;
        }

        public String getTravel() {
            return travel;
        }

        public void setTravel(String travel) {
            this.travel = travel;
        }
    }

    public static class SalesArrBean {
        /**
         * id : 300
         * title : 3.8妇女节
         * pict_url : /Uploads/images/%E4%BF%83%E9%94%80%E6%B4%BB%E5%8A%A8/20170522005845.jpg
         * click_url : https://s.click.taobao.com/BVZoa2x
         * ent_start_time : 2017-05-22
         * ent_end_time : 2017-05-31
         * commission_rate : 2.00
         * addtime : 2017-05-22 00:59:52
         * sorts : 0
         * type : 2
         */

        private String id;
        private String title;
        private String pict_url;
        private String click_url;
        private String ent_start_time;
        private String ent_end_time;
        private String commission_rate;
        private String addtime;
        private String sorts;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getEnt_start_time() {
            return ent_start_time;
        }

        public void setEnt_start_time(String ent_start_time) {
            this.ent_start_time = ent_start_time;
        }

        public String getEnt_end_time() {
            return ent_end_time;
        }

        public void setEnt_end_time(String ent_end_time) {
            this.ent_end_time = ent_end_time;
        }

        public String getCommission_rate() {
            return commission_rate;
        }

        public void setCommission_rate(String commission_rate) {
            this.commission_rate = commission_rate;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getSorts() {
            return sorts;
        }

        public void setSorts(String sorts) {
            this.sorts = sorts;
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
        private String nick;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
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
    }

    public static class CatArrBean {
        /**
         * cid : 11
         * cat : null
         * pict_url : null
         * level : 1
         * father_cid : 0
         * title : 女人
         * q :
         */

        private String cid;
        private String cat;
        private String pict_url;
        private String level;
        private String father_cid;
        private String title;
        private String q;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }

        public String getPict_url() {
            return pict_url;
        }

        public void setPict_url(String pict_url) {
            this.pict_url = pict_url;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getFather_cid() {
            return father_cid;
        }

        public void setFather_cid(String father_cid) {
            this.father_cid = father_cid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }
    }
}
