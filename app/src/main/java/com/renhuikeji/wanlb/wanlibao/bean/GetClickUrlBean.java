package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/23.
 *
 * 获取Click_url的结果实体类
 */

public class GetClickUrlBean extends BaseBean {

    private String click_url;
    private String worngMsg;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }


    public String getClick_url() {
        return click_url;
    }

    public void setClick_url(String click_url) {
        this.click_url = click_url;
    }
}
