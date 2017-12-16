package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/4.
 * <p>
 * 订单详情实体
 */

public class BillDetailBean extends BaseBean {

    private List<ProductBean> product;
    private String worngMsg;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class ProductBean {
        /**
         * p_id : 545264067064
         * p_name : 洗衣机底座海尔松下小天鹅三洋滚筒支架加高不锈钢移动万向轮托架
         * it_cnt : 1
         * p_marked_price : 136
         * p_price : 58.00
         * p_amount : 58
         * return_rate : 4.00%
         * returns : 2.32
         */

        private String p_id;
        private String p_name;
        private String it_cnt;
        private String p_marked_price;
        private String p_price;
        private double p_amount;
        private String return_rate;
        private String returns;

        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

        public String getIt_cnt() {
            return it_cnt;
        }

        public void setIt_cnt(String it_cnt) {
            this.it_cnt = it_cnt;
        }

        public String getP_marked_price() {
            return p_marked_price;
        }

        public void setP_marked_price(String p_marked_price) {
            this.p_marked_price = p_marked_price;
        }

        public String getP_price() {
            return p_price;
        }

        public void setP_price(String p_price) {
            this.p_price = p_price;
        }

        public double getP_amount() {
            return p_amount;
        }

        public void setP_amount(double p_amount) {
            this.p_amount = p_amount;
        }

        public String getReturn_rate() {
            return return_rate;
        }

        public void setReturn_rate(String return_rate) {
            this.return_rate = return_rate;
        }

        public String getReturns() {
            return returns;
        }

        public void setReturns(String returns) {
            this.returns = returns;
        }
    }
}
