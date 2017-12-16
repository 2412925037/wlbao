package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class IntegralsBean extends BaseBean {

    /**
     * points : [{"addtime":"2017-05-24 21:37:53","type":"1","points":"15","balance":"30","descs":"签到送积分"},{"addtime":"2017-05-23 19:22:50","type":"1","points":"10","balance":"15","descs":"签到送积分"},{"addtime":"2017-05-22 18:32:20","type":"1","points":"5","balance":"5","descs":"签到送积分"}]
     * result : SUCESS
     * page : 0
     * pageTotals : 1
     * para : {"api":"yasbao.api.account.points","uid":"1","apiKey":"f40b1da52dece67017dbb0c7830e586e","p":"0"}
     *    "pageSize": 100,
     "totals": "4",
     */


    private String page;
    private int pageTotals;
    private ParaBean para;
    private int pageSize;
    private String totals;
    private String worngMsg;
    private List<PointsBean> points;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotals() {
        return totals;
    }

    public void setTotals(String totals) {
        this.totals = totals;
    }

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

    public ParaBean getPara() {
        return para;
    }

    public void setPara(ParaBean para) {
        this.para = para;
    }

    public List<PointsBean> getPoints() {
        return points;
    }

    public void setPoints(List<PointsBean> points) {
        this.points = points;
    }

    public static class ParaBean {
        /**
         * api : yasbao.api.account.points
         * uid : 1
         * apiKey : f40b1da52dece67017dbb0c7830e586e
         * p : 0
         */

        private String api;
        private String uid;
        private String apiKey;
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

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }

    public static class PointsBean {
        /**
         * addtime : 2017-05-24 21:37:53
         * type : 1
         * points : 15
         * balance : 30
         * descs : 签到送积分
         */

        private String addtime;
        private String type;
        private String points;
        private String balance;
        private String descs;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getDescs() {
            return descs;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }
    }
}
