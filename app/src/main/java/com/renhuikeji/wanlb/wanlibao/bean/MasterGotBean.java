package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/3.
 *
 * 师傅所得的费用实体
 */

public class MasterGotBean extends BaseBean{
    private String costName;
    private String costNum;

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getCostNum() {
        return costNum;
    }

    public void setCostNum(String costNum) {
        this.costNum = costNum;
    }
}
