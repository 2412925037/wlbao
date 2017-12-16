package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/22.
 */

public class AccountInfo extends BaseBean {



    private AccountBean account;
    private String worngMsg;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public static class AccountBean {
        /**
         * id : 18546
         * nick :
         * headimgurl : http://wx.qlogo.cn/mmopen/PiajxSqBRaEIAowUSzLNzMNgNdiad3DCuHWC9Nc7qEUQv2SwNTL5bwPMb6GibrK0jjxzBL2d7xR8e7CjtI3EzZcNA/0
         * money : 0.00
         * returns : 0.00
         * commission : 0.00
         * reg_redbag : 0.00
         * points : 0
         * points_exchanged : 0
         * deposit : 0.00
         * income : 0.00
         * sales_income : 0.00
         * agent_income : 0.00
         * business_income : 0.00
         * sales_account : 0.00
         * agent_account : 0.00
         * business_account : 0.00
         * mobile : 15136198901
         */

        private String id;
        private String nick;
        private String headimgurl;
        private String money;
        private String returns;
        private String commission;
        private String reg_redbag;
        private String points;
        private String points_exchanged;
        private String deposit;
        private String income;
        private String sales_income;
        private String agent_income;
        private String business_income;
        private String sales_account;
        private String agent_account;
        private String business_account;
        private String mobile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getPoints_exchanged() {
            return points_exchanged;
        }

        public void setPoints_exchanged(String points_exchanged) {
            this.points_exchanged = points_exchanged;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
