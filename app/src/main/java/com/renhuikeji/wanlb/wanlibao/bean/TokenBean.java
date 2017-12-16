package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2016/4/28.
 */
public class TokenBean extends BaseBean {
    private String token;
    private String validity;
    private String overdueTime;

    public String getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(String overdueTime) {
        this.overdueTime = overdueTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}
