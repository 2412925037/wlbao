package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/6/3.
 */

public class TypeJumpBean extends BaseBean{
//    账户:a 0
//    订单:o  1
//    好友:f  2
//    推荐好友:r  3
//    资金流水:c  4
//    签到:s 5
//    积分integral 6
    private String a;
    private String o;
    private String f;
    private String r;
    private String c;
    private String s;
    private String i;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }
}
