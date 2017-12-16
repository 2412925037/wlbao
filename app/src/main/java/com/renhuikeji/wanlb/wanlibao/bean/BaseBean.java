package com.renhuikeji.wanlb.wanlibao.bean;

import java.io.Serializable;

/**
 * 基类bean
 */
public class BaseBean implements Serializable {
    public String code;
    public String msg;
    public Object data;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
