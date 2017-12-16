package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 *
 * 签到记录的实体类
 */

public class SignRecordBean extends BaseBean {


    /**
     * signRecords : [{"points":"15","sign_date":"2017-06-01","sign_time":"2017-06-01 10:02:51"}]
     * signRule : {"sign1":"5","sign2":"10","sign3":"15","sign4":"20","sign5":"25","sign6":"30"}
     * result : SUCESS
     * pointsTotal
     */

    private SignRuleBean signRule;

    private List<SignRecordsBean> signRecords;
    private String worngMsg;
    private String pointsTotal;
    private String todaySignPoints;
    private String orderMoney;
    private String exchangeMoney;

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getExchangeMoney() {
        return exchangeMoney;
    }

    public void setExchangeMoney(String exchangeMoney) {
        this.exchangeMoney = exchangeMoney;
    }

    public String getTodaySignPoints() {
        return todaySignPoints;
    }

    public void setTodaySignPoints(String todaySignPoints) {
        this.todaySignPoints = todaySignPoints;
    }

    public String getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(String pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }

    public SignRuleBean getSignRule() {
        return signRule;
    }

    public void setSignRule(SignRuleBean signRule) {
        this.signRule = signRule;
    }



    public List<SignRecordsBean> getSignRecords() {
        return signRecords;
    }

    public void setSignRecords(List<SignRecordsBean> signRecords) {
        this.signRecords = signRecords;
    }

    public static class SignRuleBean {
        /**
         * sign1 : 5
         * sign2 : 10
         * sign3 : 15
         * sign4 : 20
         * sign5 : 25
         * sign6 : 30
         */

        private String sign1;
        private String sign2;
        private String sign3;
        private String sign4;
        private String sign5;
        private String sign6;

        public String getSign1() {
            return sign1;
        }

        public void setSign1(String sign1) {
            this.sign1 = sign1;
        }

        public String getSign2() {
            return sign2;
        }

        public void setSign2(String sign2) {
            this.sign2 = sign2;
        }

        public String getSign3() {
            return sign3;
        }

        public void setSign3(String sign3) {
            this.sign3 = sign3;
        }

        public String getSign4() {
            return sign4;
        }

        public void setSign4(String sign4) {
            this.sign4 = sign4;
        }

        public String getSign5() {
            return sign5;
        }

        public void setSign5(String sign5) {
            this.sign5 = sign5;
        }

        public String getSign6() {
            return sign6;
        }

        public void setSign6(String sign6) {
            this.sign6 = sign6;
        }
    }

    public static class SignRecordsBean {
        /**
         * points : 15
         * sign_date : 2017-06-01
         * sign_time : 2017-06-01 10:02:51
         */

        private String points;
        private String sign_date;
        private String sign_time;

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getSign_date() {
            return sign_date;
        }

        public void setSign_date(String sign_date) {
            this.sign_date = sign_date;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }
    }
}
