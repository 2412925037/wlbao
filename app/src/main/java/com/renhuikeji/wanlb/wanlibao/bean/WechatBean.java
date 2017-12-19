package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/12/18.
 * 微信登录
 */

public class WechatBean {


    /**
     * openid : oan_e1DHEJ7ypsjoEGLhQ4Ls8RB8
     * access_token : 5_Q1unSDyiQq2C8Mb6iOzTii7szhVMJDLmVzIlJ1vTKRIlMWGmEkqubvGkMfA-plNukicmg5QrZkL-apf2TjmCEu26x39ziKwJBLCpPYJifOM
     * result : NOBIND
     * worngMsg : 该用户没有绑定微信号，请绑定！
     */

    private String openid;
    private String access_token;
    private String result;
    private String worngMsg;
    private String uid;
    private String username;
    private String password;
    private String nickname;
    private String headimgurl;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }
}
