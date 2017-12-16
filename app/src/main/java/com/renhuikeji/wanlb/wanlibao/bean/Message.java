package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/6/14.
 */

public class Message extends BaseBean{
    private int id;
    private String content;
    private int  isread;
    private String jumpType;
    private String rece_id;

    public Message() {
    }

    public Message(int id, String content, int isread, String jumpType,String rece_id) {

        this.id = id;
        this.content = content;
        this.isread = isread;
        this.jumpType = jumpType;
        this.rece_id = rece_id;
    }

    public String getRece_id() {
        return rece_id;
    }

    public void setRece_id(String rece_id) {
        this.rece_id = rece_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }
    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }
}
