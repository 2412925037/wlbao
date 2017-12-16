package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/25.
 *
 * 签到记录实体类
 */

public class DayIsSignBean extends BaseBean {
    private String date;
    private boolean isSign;

    public DayIsSignBean(String date) {
        this.date = date;
    }

    public DayIsSignBean(String date, boolean isSign) {
        this.date = date;
        this.isSign = isSign;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }
}
