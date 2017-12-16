package com.renhuikeji.wanlb.wanlibao.utils;

/**
 * Created by Administrator on 2017/4/20.
 */

public class Constant {

    //    注册
    public static final String REGISTER = "http://app.yasbao.com/Home/Api/regist.do";


    //    查询
    public static final String SEARCH = "http://app.yasbao.com/Home/Api/search.do?";

    public static final String First_START = "isFirst";
// 登录参数和状态
    public static final String User_Uid = "uid";
    public static final String User_Phone = "phone";
    public static final String User_Psw = "psw";
    public static final String IsLogin = "is_login";
    public static final String MSESSION = "wch_session";
    public static final String RECOMMENDER = "wch_recommender";  //师傅UID
    public static final String User_Nick = "nickname";
    public static final String User_Header = "headerurl";
    public static final String User_Money = "money";
    public static final String Is_Vip = "isvip";
    public static final String User_Returns = "returns";  //累计返利
    public static final String User_Incomes = "allincome";  //总收入

// 上次搜索参数       search_q:关键词   type:搜索类型
    public static final String search_q = "search_q";
    public static final String search_type = "search_type";
//
    public static final int  SUCCESS = 1;
    public static final int  ERROR = 0;
    public static final int  UPDATE = 2;
//  好友的好友查看
    public static final String  IS_LOOK="islook";
//    第一次注册环信
    public static final String  IS_FIRST_HX="is_first_hx";
//    阿里sdk初始化
    public static final String  INIT_ALI="init_ali";
//
    public static final String  RECEIVER_DIALOG="com.rhkj.dialog";
//    签到
    public static final String  QIANDAO="com.rhkj.qiandao";
//
    public static final int  LAYOUT_LIST = 1;
    public static final int  LAYOUT_Grid = 2;
//    跳转
    public static final String  JUMP = "jump";
//
    /**
     * 账户,订单,好友,推荐好友,资金流水,签到
     */
    public static final String  JUMP_ACCOUNT = "0";
    public static final String  JUMP_ORDER = "1";
    public static final String  JUMP_FRIEND = "2";
    public static final String  JUMP_RECOM = "3";
    public static final String  JUMP_CASH = "4";
    public static final String  JUMP_SIGN = "5";
    public static final String  JUMP_SESSION = "6";
    //搜索历史记录
    public static final String  HISTORY_STR = "history_str";
    public static final String  BACKWHERE = "back";
//
    public static final String  MAIN_DISPLAY = "ismaindisplay";
    public static final String  RECEIVE_MSG = "receive";
//    数据库表名
    public static final String  DB_RECORDS = "records";
    public static final String  DB_MSGS = "messages";
//    商品结果页1,2列展示
    public static final int   SINGLE = 1;
    public static final int  DOUBLE = 2;
}
