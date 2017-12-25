package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * 微信提现bean
 */

public class WeChatCrashBean extends BaseBean{

    /**
     * result : WRONG
     * worngMsg : 提现金额不可以超过你的可用余额！请重新操作！
     */

    private String msg;//系统提示错误

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String worngMsg;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }
}
