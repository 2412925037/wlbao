package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/12/14.
 * 支付宝绑定
 */

public class AlipayLoginCodeBean {


    /**
     * result : SUCESS
     * infocode : 205697
     * yan_code : 205697
     * mobile : 18356058024
     * infotime : 1513235408
     */

    private String result;
    private String infocode;
    private String yan_code;
    private String mobile;
    private long infotime;
    private String worngMsg;

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }

    public String getWorngMsg() {
        return worngMsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getYan_code() {
        return yan_code;
    }

    public void setYan_code(String yan_code) {
        this.yan_code = yan_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getInfotime() {
        return infotime;
    }

    public void setInfotime(long infotime) {
        this.infotime = infotime;
    }
}
