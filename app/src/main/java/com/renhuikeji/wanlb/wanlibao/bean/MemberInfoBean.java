package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MemberInfoBean extends BaseBean{


    /**
     * result : SUCESS
     * userinfo : {"id":"18543","money":"0.10",
     * "returns":"0.00","income":"0.00","nick":"武聪辉","
     * vip":"N","salesman":"-1",
     * "headimgurl":"http://wx.qlogo.cn/mmopen/8M3InDbZfSMLzUpqLwqicicOXLQdnsCZoTQt4sW3TATicqtb5XHZ9Wgn9NcdvKcoRwt8PBOSdglkPtjvKg0sMJPKLgZEVqeRaicP/0",
     * "subscribe_yasbao":"1","openid_yasbao":
     * "oArRfwmUeQ0Cf3mfUNyaH0OYJcQ4","level":"1",
     * "mobile":"18039513911","recommender":"1"}
     */

    private UserinfoBean userinfo;


    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserinfoBean {
        /**
         * id : 18543
         * money : 0.10
         * returns : 0.00
         * income : 0.00
         * nick : 武聪辉
         * vip : N
         * salesman : -1
         * headimgurl : http://wx.qlogo.cn/mmopen/8M3InDbZfSMLzUpqLwqicicOXLQdnsCZoTQt4sW3TATicqtb5XHZ9Wgn9NcdvKcoRwt8PBOSdglkPtjvKg0sMJPKLgZEVqeRaicP/0
         * subscribe_yasbao : 1
         * openid_yasbao : oArRfwmUeQ0Cf3mfUNyaH0OYJcQ4
         * level : 1
         * mobile : 18039513911
         * recommender : 1
         */

        private String id;
        private String money;
        private String returns;
        private String income;
        private String nick;
        private String vip;
        private String salesman;
        private String headimgurl;
        private String subscribe_yasbao;
        private String openid_yasbao;
        private String level;
        private String mobile;
        private String recommender;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getReturns() {
            return returns;
        }

        public void setReturns(String returns) {
            this.returns = returns;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getSalesman() {
            return salesman;
        }

        public void setSalesman(String salesman) {
            this.salesman = salesman;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getSubscribe_yasbao() {
            return subscribe_yasbao;
        }

        public void setSubscribe_yasbao(String subscribe_yasbao) {
            this.subscribe_yasbao = subscribe_yasbao;
        }

        public String getOpenid_yasbao() {
            return openid_yasbao;
        }

        public void setOpenid_yasbao(String openid_yasbao) {
            this.openid_yasbao = openid_yasbao;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRecommender() {
            return recommender;
        }

        public void setRecommender(String recommender) {
            this.recommender = recommender;
        }
    }
}
