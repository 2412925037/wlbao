package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/12/14.
 * 支付宝登录返回
 */

public class AlipayLoginBean {


    /**
     * user_id : 2088712912254324
     * access_token : kuaijieBad8d890952a74a41ba8ea3aeb5c2aE32
     * result : NOBIND
     * worngMsg : 该用户没有绑定支付宝，请绑定！
     */

    private String uid;
    private String user_id;
    private String access_token;
    private String result;
    private String worngMsg;
    private String username;
    private String password;
    private String nick_name;
    private String avatar;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
