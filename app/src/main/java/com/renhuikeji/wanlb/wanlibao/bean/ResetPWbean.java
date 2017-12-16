package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ResetPWbean extends  BaseBean{

    /**
     * result : WRONG
     * worngMsg : 重置失败！短信验证码输入错误！
     */


    private String worngMsg;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }
}
