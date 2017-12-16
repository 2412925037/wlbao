package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MallBean extends BaseBean {

    private List<MallList> mallList;


    public void setMallList(List<MallList> mallList) {
        this.mallList = mallList;
    }

    public List<MallList> getMallList() {
        return this.mallList;
    }


    public class MallList {
        private String mall_id;

        private String mall_name;

        public void setMall_id(String mall_id) {
            this.mall_id = mall_id;
        }

        public String getMall_id() {
            return this.mall_id;
        }

        public void setMall_name(String mall_name) {
            this.mall_name = mall_name;
        }

        public String getMall_name() {
            return this.mall_name;
        }
    }
}
