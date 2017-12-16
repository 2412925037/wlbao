package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class MyOrderBean extends BaseBean {

    /**
     * orders : [{"id":"47128","order_code":"12706128334274477","order_time":"2017-04-30 17:20:36","order_money":"58.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"2.32","order_butie":"0.00"},{"id":"45031","order_code":"5016351372274477","order_time":"2017-04-04 14:25:59","order_money":"29.90","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"11.73","order_butie":"0.00"},{"id":"45032","order_code":"5012351913274477","order_time":"2017-04-04 14:21:07","order_money":"19.99","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"6.09","order_butie":"0.00"},{"id":"41529","order_code":"3139080899894477","order_time":"2017-02-17 09:27:53","order_money":"435.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"43.50","order_butie":"0.00"},{"id":"41474","order_code":"3122156081374477","order_time":"2017-02-14 10:09:40","order_money":"85.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"4.25","order_butie":"0.00"},{"id":"41475","order_code":"3122156081384477","order_time":"2017-02-14 10:09:40","order_money":"138.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"31.05","order_butie":"0.00"},{"id":"38601","order_code":"2726929689324477","order_time":"2016-11-25 10:41:01","order_money":"69.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"4.14","order_butie":"0.00"},{"id":"38500","order_code":"2712609115014477","order_time":"2016-11-22 14:37:02","order_money":"198.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"64.60","order_butie":"0.00"},{"id":"38358","order_code":"2687080906864477","order_time":"2016-11-16 21:33:58","order_money":"96.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"0.86","order_butie":"0.00"},{"id":"38144","order_code":"2649887302194477","order_time":"2016-11-11 14:34:35","order_money":"144.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"22.32","order_butie":"0.00"},{"id":"38139","order_code":"2647711280904477","order_time":"2016-11-11 14:22:07","order_money":"74.90","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"18.73","order_butie":"0.00"},{"id":"38140","order_code":"2649432900114477","order_time":"2016-11-11 14:16:46","order_money":"149.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"29.80","order_butie":"0.00"},{"id":"37306","order_code":"2488745087004477","order_time":"2016-10-23 11:20:53","order_money":"144.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"18.72","order_butie":"0.00"},{"id":"36791","order_code":"2519931428201447","order_time":"2016-10-11 16:09:36","order_money":"312.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"0.94","order_butie":"0.00"},{"id":"36354","order_code":"2215186994211447","order_time":"2016-09-26 10:40:56","order_money":"312.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"0.78","order_butie":"0.00"},{"id":"36290","order_code":"2304833909674477","order_time":"2016-09-22 15:05:36","order_money":"96.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"28.80","order_butie":"0.00"},{"id":"35405","order_code":"2182044507924477","order_time":"2016-08-16 23:38:43","order_money":"168.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"29.40","order_butie":"0.00"},{"id":"35255","order_code":"2152296097784477","order_time":"2016-08-07 13:22:06","order_money":"345.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"6.21","order_butie":"0.00"},{"id":"35246","order_code":"2147977087244477","order_time":"2016-08-06 14:41:28","order_money":"29.90","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"16.15","order_butie":"0.00"},{"id":"35245","order_code":"2147977087234477","order_time":"2016-08-06 14:41:28","order_money":"89.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"37.38","order_butie":"0.00"},{"id":"35140","order_code":"2137806421544477","order_time":"2016-07-30 21:03:47","order_money":"29.90","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"6.82","order_butie":"0.00"},{"id":"34503","order_code":"2046613796174477","order_time":"2016-07-05 14:00:56","order_money":"35.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"1.26","order_butie":"0.00"},{"id":"34390","order_code":"1644510987354477","order_time":"2016-06-27 23:08:46","order_money":"12.80","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"1.54","order_butie":"0.00"},{"id":"34387","order_code":"1644614324704477","order_time":"2016-06-27 18:08:11","order_money":"28.80","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"5.18","order_butie":"0.00"},{"id":"33594","order_code":"1577870962984477","order_time":"2016-05-21 14:52:36","order_money":"98.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"52.79","order_butie":"0.00"},{"id":"33573","order_code":"1574608596194477","order_time":"2016-05-20 11:58:57","order_money":"29.91","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"0.00","order_butie":"0.00"},{"id":"33293","order_code":"1548330508354477","order_time":"2016-04-29 12:03:51","order_money":"199.00","mall_name":"淘宝/天猫","order_status":"订单结算","status":"1","returns":"39.80","order_butie":"0.00"},{"id":"11743","order_code":"1270507119674477","order_time":"2015-11-14 16:09:21","order_money":"238.00","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"4.28","order_butie":"0.00"},{"id":"11741","order_code":"1272694280144477","order_time":"2015-11-14 16:01:46","order_money":"89.00","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"1.60","order_butie":"0.00"},{"id":"6645","order_code":"1238268068264477","order_time":"2015-10-21 09:20:03","order_money":"110.00","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"13.20","order_butie":"0.00"},{"id":"999","order_code":"1186846240881447","order_time":"2015-08-01 12:11:30","order_money":"79.00","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"10.34","order_butie":"0.00"},{"id":"900","order_code":"931156281174477","order_time":"2015-04-24 13:21:53","order_money":"49.90","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"1.75","order_butie":"0.00"},{"id":"899","order_code":"931156281164477","order_time":"2015-04-24 13:21:53","order_money":"79.00","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"5.53","order_butie":"0.00"},{"id":"536","order_code":"799544468904477","order_time":"2014-09-22 14:55:24","order_money":"79.00","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"2.77","order_butie":"0.00"},{"id":"535","order_code":"799133112514477","order_time":"2014-09-22 14:43:27","order_money":"75.00","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"10.50","order_butie":"0.00"},{"id":"534","order_code":"799081594664477","order_time":"2014-09-22 13:51:34","order_money":"48.97","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"10.28","order_butie":"0.00"},{"id":"415","order_code":"779654810384477","order_time":"2014-08-20 17:25:21","order_money":"28.00","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"0.59","order_butie":"0.00"},{"id":"330","order_code":"754270330654477","order_time":"2014-07-31 22:56:56","order_money":"95.00","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"6.65","order_butie":"0.00"},{"id":"329","order_code":"754269052304477","order_time":"2014-07-31 22:56:56","order_money":"69.00","mall_name":"淘宝","order_status":"订单结算","status":"1","returns":"16.91","order_butie":"0.00"},{"id":"287","order_code":"736597130144477","order_time":"2014-07-17 17:07:07","order_money":"0.00","mall_name":"淘宝","order_status":null,"status":"1","returns":"0.00","order_butie":"0.00"},{"id":"288","order_code":"735958423054477","order_time":"2014-07-17 16:56:14","order_money":"38.00","mall_name":"淘宝","order_status":null,"status":"1","returns":"1.33","order_butie":"0.00"}]
     * result : SUCESS
     * page : 0
     * pageTotals : 1
     * totals : 41
     * pageSize : 100
     * para : {"api":"yasbao.api.order.orderlist","uid":"1","apiKey":"f40b1da52dece67017dbb0c7830e586e","status":"1","p":"0"}
     */


    private String page;
    private int pageTotals;
    private String totals;
    private int pageSize;
    private ParaBean para;
    private List<OrdersBean> orders;
    private String worngMsg;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getPageTotals() {
        return pageTotals;
    }

    public void setPageTotals(int pageTotals) {
        this.pageTotals = pageTotals;
    }

    public String getTotals() {
        return totals;
    }

    public void setTotals(String totals) {
        this.totals = totals;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ParaBean getPara() {
        return para;
    }

    public void setPara(ParaBean para) {
        this.para = para;
    }

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class ParaBean {
        /**
         * api : yasbao.api.order.orderlist
         * uid : 1
         * apiKey : f40b1da52dece67017dbb0c7830e586e
         * status : 1
         * p : 0
         */

        private String api;
        private String uid;
        private String apiKey;
        private String status;
        private String p;

        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }

    public static class OrdersBean {
        /**
         * id : 47128
         * order_code : 12706128334274477
         * order_time : 2017-04-30 17:20:36
         * order_money : 58.00
         * mall_name : 淘宝/天猫
         * order_status : 订单结算
         * status : 1
         * returns : 2.32
         * order_butie : 0.00
         * item_count
         * title
         */

        private String id;
        private String order_code;
        private String order_time;
        private String order_money;
        private String mall_name;
        private String item_count;
        private String order_status;
        private String status;
        private String returns;
        private String order_butie;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getItem_count() {
            return item_count;
        }

        public void setItem_count(String item_count) {
            this.item_count = item_count;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public String getOrder_money() {
            return order_money;
        }

        public void setOrder_money(String order_money) {
            this.order_money = order_money;
        }

        public String getMall_name() {
            return mall_name;
        }

        public void setMall_name(String mall_name) {
            this.mall_name = mall_name;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReturns() {
            return returns;
        }

        public void setReturns(String returns) {
            this.returns = returns;
        }

        public String getOrder_butie() {
            return order_butie;
        }

        public void setOrder_butie(String order_butie) {
            this.order_butie = order_butie;
        }
    }
}
