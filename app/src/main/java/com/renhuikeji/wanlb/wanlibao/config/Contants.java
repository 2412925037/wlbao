package com.renhuikeji.wanlb.wanlibao.config;

/**
 * Created by Administrator on 2017/12/5.
 *
 */

public class Contants {


    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2016013001130544";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088701836072927";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "wlb";

    //apiKey
    public static final String API_KEY = "f40b1da52dece67017dbb0c7830e586e";
    //请求服务器地址
    public static final String APP_IP = "http://app.yasbao.com/Home/Api/gw";

    //微信登录
    public static final String SHARE_URL = APP_IP + "?api=yasbao.api.user.share&apiKey=" + API_KEY;

    //支付宝登录
    public static final String ALIPAY_LOGIN = APP_IP + "?api=yasbao.api.alipay.alipaylogin&apiKey=" + API_KEY;

    //授权
    public static final String AUTH_ALIPAY = APP_IP + "?api=yasbao.api.alipay.alipaysign&apiKey=" + API_KEY;

    //支付宝绑定  yasbao.api.user.alipaybind
    public static final String ALIPAY_BIND = APP_IP + "?api=yasbao.api.alipay.alipaybind&apiKey=" + API_KEY;

    //支付宝提现
    public static final String ALIPAY_WITHDRAW = APP_IP + "?api=yasbao.api.account.withdrawtoalipay&apiKey="+API_KEY;

    //微信登录
    public static final String WECHAT_LOGIN = APP_IP + "?api=yasbao.api.weixin.weixinlogin&apiKey=" + API_KEY;

    //微信绑定
    public static final String WECHAT_BIND = APP_IP + "?api=yasbao.api.weixin.weixinbind&apiKey="+API_KEY;
}
