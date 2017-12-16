package com.renhuikeji.wanlb.wanlibao.bean;

/**登录注册实体类
 * Created by Administrator on 2017/5/17.
 */

public class LoginCodeBean extends BaseBean{


    private int infocode;

    private String mobile;

    private int infotime;

    private String worngMsg;

    private String uid;
    private String username;
    private String password;

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

    public void setWorngMsg(String worngMsg){
        this.worngMsg = worngMsg;
    }
    public String getWorngMsg(){
        return this.worngMsg;
    }

    public void setInfocode(int infocode){
        this.infocode = infocode;
    }
    public int getInfocode(){
        return this.infocode;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getMobile(){
        return this.mobile;
    }
    public void setInfotime(int infotime){
        this.infotime = infotime;
    }
    public int getInfotime(){
        return this.infotime;
    }
}
