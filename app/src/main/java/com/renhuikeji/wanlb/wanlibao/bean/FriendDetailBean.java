package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/24.
 */

public class FriendDetailBean extends BaseBean {

    /**
     * friendinfo : {"uid":"18566","recommender":"1","nick":"我就是我","headimgurl":"http://wx.qlogo.cn/mmopen/ZMdxSDafpxSXFqAoQJx9EkGicUPjNK8tibJRL6ZnJ9qjnngib6cvxic0JapVUEvPRb90ca4MmPhuTtNzE5dwCW2Y4Y1qfgwXC7O6/0","returns":"0.00","mobile":"15385170982","regtime":"2017-05-23 21:23:35","lastlogintime":"2017-05-23 21:33:25","team":"1","share_status":"1","vip":"N","isApp":"Y","orders":"0","commission":"0.00","reg_redbag":"0.00","income":"0.00","sales_income":"0.00","agent_income":"0.00","business_income":"0.00","sales_account":"0.00","agent_account":"0.00","business_account":"0.00"}
     * result : SUCESS
     */

    private FriendinfoBean friendinfo;

    private String worngMsg;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }

    public FriendinfoBean getFriendinfo() {
        return friendinfo;
    }

    public void setFriendinfo(FriendinfoBean friendinfo) {
        this.friendinfo = friendinfo;
    }


    public static class FriendinfoBean {
        /**
         * uid : 18566
         * recommender : 1
         * nick : 我就是我
         * headimgurl : http://wx.qlogo.cn/mmopen/ZMdxSDafpxSXFqAoQJx9EkGicUPjNK8tibJRL6ZnJ9qjnngib6cvxic0JapVUEvPRb90ca4MmPhuTtNzE5dwCW2Y4Y1qfgwXC7O6/0
         * returns : 0.00
         * mobile : 15385170982
         * regtime : 2017-05-23 21:23:35
         * lastlogintime : 2017-05-23 21:33:25
         * team : 1
         * share_status : 1
         * vip : N
         * isApp : Y
         * orders : 0
         * commission : 0.00
         * reg_redbag : 0.00
         * income : 0.00
         * sales_income : 0.00
         * agent_income : 0.00
         * business_income : 0.00
         * sales_account : 0.00
         * agent_account : 0.00
         * business_account : 0.00
         */

        private String uid;
        private String recommender;
        private String nick;
        private String headimgurl;
        private String returns;
        private String mobile;
        private String regtime;
        private String lastlogintime;
        private String team;
        private String share_status;
        private String vip;
        private String isApp;
        private String orders;
        private String commission;
        private String reg_redbag;
        private String income;
        private String sales_income;
        private String agent_income;
        private String business_income;
        private String sales_account;
        private String agent_account;
        private String business_account;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getRecommender() {
            return recommender;
        }

        public void setRecommender(String recommender) {
            this.recommender = recommender;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getReturns() {
            return returns;
        }

        public void setReturns(String returns) {
            this.returns = returns;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public String getLastlogintime() {
            return lastlogintime;
        }

        public void setLastlogintime(String lastlogintime) {
            this.lastlogintime = lastlogintime;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getShare_status() {
            return share_status;
        }

        public void setShare_status(String share_status) {
            this.share_status = share_status;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getIsApp() {
            return isApp;
        }

        public void setIsApp(String isApp) {
            this.isApp = isApp;
        }

        public String getOrders() {
            return orders;
        }

        public void setOrders(String orders) {
            this.orders = orders;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getReg_redbag() {
            return reg_redbag;
        }

        public void setReg_redbag(String reg_redbag) {
            this.reg_redbag = reg_redbag;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getSales_income() {
            return sales_income;
        }

        public void setSales_income(String sales_income) {
            this.sales_income = sales_income;
        }

        public String getAgent_income() {
            return agent_income;
        }

        public void setAgent_income(String agent_income) {
            this.agent_income = agent_income;
        }

        public String getBusiness_income() {
            return business_income;
        }

        public void setBusiness_income(String business_income) {
            this.business_income = business_income;
        }

        public String getSales_account() {
            return sales_account;
        }

        public void setSales_account(String sales_account) {
            this.sales_account = sales_account;
        }

        public String getAgent_account() {
            return agent_account;
        }

        public void setAgent_account(String agent_account) {
            this.agent_account = agent_account;
        }

        public String getBusiness_account() {
            return business_account;
        }

        public void setBusiness_account(String business_account) {
            this.business_account = business_account;
        }
    }
}
