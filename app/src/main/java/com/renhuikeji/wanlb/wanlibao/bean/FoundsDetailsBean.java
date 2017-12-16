package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class FoundsDetailsBean extends  BaseBean{

    /**
     * funds : [{"addtime":"2017-05-24 09:43:54","type":"-1","money":"-1.00","balance":"0.00","descs":"提现到微信钱包"},{"addtime":"2017-05-24 09:40:26","type":"2","money":"1.00","balance":"1.00","descs":"账户充值"}]
     * result : SUCESS
     * page : 1
     * pageTotals : 1
     * para : {"api":"yasbao.api.account.funds","uid":"18543","apiKey":"f40b1da52dece67017dbb0c7830e586e","type":"0","p":"1"}
     */

    private String page;
    private int pageTotals;
    private ParaBean para;
    private List<FundsBean> funds;



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

    public List<FundsBean> getFunds() {
        return funds;
    }

    public void setFunds(List<FundsBean> funds) {
        this.funds = funds;
    }

    public static class ParaBean {
        /**
         * api : yasbao.api.account.funds
         * uid : 18543
         * apiKey : f40b1da52dece67017dbb0c7830e586e
         * type : 0
         * p : 1
         */

        private String api;
        private String uid;
        private String apiKey;
        private String type;
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

    public static class FundsBean {
        /**
         * addtime : 2017-05-24 09:43:54
         * type : -1
         * money : -1.00
         * balance : 0.00
         * descs : 提现到微信钱包
         */

        private String addtime;
        private String type;
        private String money;
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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
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
