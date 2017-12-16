package com.renhuikeji.wanlb.wanlibao.config;

/**
 * Created by sks on 2015/9/17.
 */
public class ConfigValue {

    // 请求服务器 成功code
    public static final String Success_Code = "1";
    // 请求服务器失败code
    public static final String Error_Code = "0";

    //腾讯appid
    public static final String QQAPP_ID = "1104912240";
    //腾讯appkey
    public static final String QQAPP_KEY = "dWfNU8NjY99GJ2y9";
    //微信appid
    public static final String WXAPP_ID = "wx1aa1a833d7810a8f";
    //微信appsecret
    public static final String WXAPP_SECRET = "ea9fe2139c93560cebecfa0ca85e6e69";


    //apiKey
    public static final String API_KEY = "f40b1da52dece67017dbb0c7830e586e";
    //请求服务器地址
    public static final String APP_IP = "http://app.yasbao.com/Home/Api/gw";
    //图片前接片段
    public static final String PIC_FRONT = "http://app.yasbao.com";
    /**
     * 首页API输出结果
     */
    public static final String INDEX = APP_IP + "?api=yasbao.api.main.index&apiKey=" + API_KEY;
    /**
     * 搜索引擎API请求
     */
    public static final String SEARCH = APP_IP + "?api=yasbao.api.main.search&apiKey=" + API_KEY;
    /**
     * 分类API请求
     */
    public static final String CAT = APP_IP + "?api=yasbao.api.main.cat&apiKey=" + API_KEY;
    /**
     * 会员中心
     */
    public static final String MEMBER_CENTER = APP_IP + "?api=yasbao.api.member.index&apiKey=" + API_KEY;
    /**
     * 会员信息
     */
    public static final String MEMBER_INFO = APP_IP + "?api=yasbao.api.member.userinfo&apiKey=" + API_KEY;
    /**
     * 微信提现
     */
    public static final String WECHAT_TRANSFER = APP_IP + "?api=yasbao.api.account.withdraw&apiKey=" + API_KEY;
    /**
     * 资金明细
     */
    public static final String FUNDS_DETAILS = APP_IP + "?api=yasbao.api.account.funds&apiKey=" + API_KEY;

    /**
     * 活动&搜索热词API
     */
    public static final String HOT_KEY = APP_IP + "?api=yasbao.api.main.input&apiKey=" + API_KEY;
    /**
     * 关键词获取API
     */
    public static final String GET_KEYWORD = APP_IP + "?api=yasbao.api.main.keyword&apiKey=" + API_KEY;
    /**
     * 首页网格API请求
     */
    public static final String GRID_DO = APP_IP + "?api=yasbao.api.main.mall&apiKey=" + API_KEY;
    /**
     * 记录商品访问的API
     */
    public static final String TAO_VISIT = APP_IP + "?api=yasbao.api.main.taovisit&apiKey=" + API_KEY;
    /**
     * 普通商品获取click_url的API
     */
    public static final String GET_CLICK_URL = APP_IP + "?api=yasbao.api.main.clickurl&apiKey=" + API_KEY;
    /**
     * 退出登录的API
     */
    public static final String LOGIN_OUT = APP_IP + "?api=yasbao.api.user.logout&apiKey=" + API_KEY;
    /**
     * 签到记录API
     */
    public static final String SIGN_INFO = APP_IP + "?api=yasbao.api.member.signinfo&apiKey=" + API_KEY;
    /**
     * 签到请求API
     */
    public static final String SIGN_ASK = APP_IP + "?api=yasbao.api.member.sign&apiKey=" + API_KEY;



    //H5地址
    /**
     * 如何邀请
     */
    public static final String INVITATION_FRIENDS = "http://app.yasbao.com/index.php/Home/Member/invite_method_app.html";
    /**
     * 丢失订单
     */
    public static final String LOST_BILLS = "http://app.yasbao.com/index.php/Home/Order/noorder_app.html";
    /**
     * 推广收益
     */
    public static final String INVITATION_GAIN = "http://app.yasbao.com/index.php/Home/Member/invite_income_app.html";
    /**
     * 帮助中心
     */
    public static final String HELP_CENTER = "http://app.yasbao.com/index.php/Home/Help/center_app.html";
    /**
     * 资料更新
     */
    public static final String UPDATE_INFO = "http://app.yasbao.com/index.php/Home/Member/renewInfo_app.html";
    /**
     * 会员升级
     */
    public static final String LEVEL_UP = "http://app.yasbao.com/index.php/Home/Member/renewInfo_app.html";
    /**
     * 新手上路
     */
    public static final String NEW_USER = "http://app.yasbao.com/index.php/Home/Help/guide_app.html";
    /**
     * 邀请好友
     */
    public static final String INVITE_FRIENDS = "http://app.yasbao.com/index.php/Home/Member/InviteFriends_app.html";
    /**
     * 积分兑换
     */
    public static final String EXCHANGE_INTEGRAL = "http://app.yasbao.com/Home/Help/pointsExchange.html";
}
