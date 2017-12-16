package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/25.
 *
 * 签到结果实体类
 */

public class SignResultBean extends BaseBean {

    /**
     * result : SUCESS
     * worngMsg : 签到成功,您获得20积分！
     */


    private String worngMsg;



    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }
}
