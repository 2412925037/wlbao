package com.renhuikeji.wanlb.wanlibao.utils;

/**
 * 
 * @author songdy
 * 
 */
public interface IKeySourceUtil {

	/**
	 * 存储路径
	 */
	String DIR_PATH = "/MPin/Image/Cache";
	String SAVE_PATH = "/MPin/Image/Save";

	/**
	 * 网络连接超时时间
	 */
	int CONNECTION_TIMEOUT_INT = 15 * 1000;
	int SO_TIMEOUT_INT = 15 * 1000;

	int DELAY_BEFORE_PURGE = 30 * 1000; // in milliseconds

	int CACHE_SIZE = 5;
	/**
	 * 缓存本地空间大小
	 */
	int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;// MB
	/**
	 * 缓存图片张数
	 */
	int IMAGE_NUMBER = 300;
	/**
	 * 
	 */
	Long MB = 1024l * 1024l;

	/* 超时类型 */
	int CONNECT_SUCCESS = 1;
	int CONNECT_TIMEOUT = 2;
	int NO_HTTP_RESPONSE = 3;
	int HTTP_HOST_CONNECT = 4;
	int SERVER_BUSY = 5;

	/* Handler MSG 类型 */
	int ERROR = 0;
	int ADD_FAR = 10;
	int UPDATE_GALLERY = 1;
	int UPDATE = 26;
	int ASYNC_IMAGE_LOADER = 2;
	int LOADING = 3;
	int INIT_LOADING = 4;
	int UPDATE_FOUR_SHOW = 5;
	int UPDATE_PRODUCT_DETAILS = 6;
	int LOADING_END = 7;
	int LOADING_PRODUCT_CLASS = 8;
	int LOADING_PRODUCT_LIST_BY_CLASS_SUCCESS = 9;
	int LOADING_ORDER_CREATE = 10;
	int LOADING_ORDER_DETAILS = 11;
	int GIFT_CARD_VALIDE = 12;
	int GIFT_CARD_FOR_PAY = 13;
	int UPDATA_CLIENT = 14;
	int GET_UNDATAINFO_ERROR = 15;
	int DOWN_ERROR = 16;
	int INIT_FAILED = 17;
	int LOADING_STORE_DATA = 18;
	int DELETE_STORE_DATA = 19;
	int LOADING_SEARCH_DATA = 20;
	int VERSION_CHECK = 21;
	int LOADING_SEARCH_CONTION = 22;
	int ERROR_MESSAGE = 23;
	int BILL_PAY_RESULT = 24;
	int GET_CITY_DATA_SUCCESS = 25;
	int DELETE_BOOK_DATA = 26;
	int LOADING_FINISH = 27;
	int GET_ORDER_COMMENT_INFO_DATA_SUCCESS = 28;
	int VALID_USER_CARD_AND_BIND = 29;
	int DELETE_SCAN_RECORDS_END = 30;
	int DELETE_RECORDS_END = 30;
	int MODIFY_TRAVEL_DREAM_DATA_END = 31;
	int AUTO_CHANGE_GALLERY = 32;
	int DELL_COLLECT_ONE_DATA = 33;
	int NOT_HISTORY_DATA = 34;
	int MARK_COLLECT_READ_ONE_DATA=35;
	int DELL_ONE_MESSAGE_DATA=36;

	/* 异常 Handler MSG 类型 */
	int JSON_EXCEPTION_NORMAL = 0;
	int JSON_EXCEPTION = 1;
	int EXCEPTION = 2;
	
	/**
	 * 城市数据库名称
	 */
	String CITY_DATABASE_NAME = "uzai_db";

	/**
	 * 筛选记录保存文件名
	 */
	String FILTER_NAME = "filter_name";
	/**
	 * 搜索关键字数据名称
	 */
	String SEARCH_KEYWORDS_DATABASE_NAME = "uzai_search_db";

	/**
	 * 默认定位城市
	 */
	String CITY = "郑州";
	/**
	 * 控制首次安装时默认接收推送信息
	 */
	boolean PUSH_OPEN = true;
	
	/**
	 * 四宫格中产品一次性加载数量
	 */
	int COUNT = 10;

	/**
	 * LIST 列表产品一次性加载数量
	 */
	int REQUEST_COUNT = 15;
	/**
	 * 更多按钮需要滑动的距离
	 */
	int MOVE_JULI = 20;

	/**
	 * 客户热线
	 */
//	String CUSTOMER_HOTLINE = "4000008888";

	/**
	 * 用户注册信息类型
	 */
	int REG_USERNAME = 1;
	int REG_PHONE = 2;
	int REG_EMAIL = 3;
	int REG_PASSWORD = 4;
	int REG_MESSAGECODE=5;
	/**
	 * SharedPreferences 名字
	 */
	String LOGIN_STATUS = "LoginStatus";
	String SINA_WEIBO_BINDING_STATUS = "SinaWeibo";
	String TENCENT_WEIBO_BINDING_STATUS = "TencentWeibo";
	String RENREN_WEIBO_BINDING_STATUS = "RenRenWeibo";
	String PHONE_TOKEN="PhoneToken";
	/**
	 * 代金卡随机码
	 */
	String[] GIFT_CARD_LETTER = { "A", "B", "C", "D", "E", "F", "G" };
	String[] GIFT_CARD_NUM = { "1", "2", "3", "4", "5" };

	float TARGET_HEAP_UTILIZATION = 0.75f;
	int CWJ_HEAP_SIZE = 6 * 1024 * 1024;

	int ROUND_CORNER = 12;

	/**
	 * 登录返回结果码
	 */
	// int INTENT_RESULT = 1;
	int INTENT_REQUEST = 0;

	String FROM = "xweibo";
	String HISTROY = "histroy";

	int WEIBO_MAX_LENGTH = 140;

	String UZAI_URL = "http://www.uzai.com/";

	/**
	 * 本地图片缓存时间
	 */
	long CACHE_TIME = 1000 * 60 * 60 * 24 * 7;
	// long CACHE_TIME = 1000 * 60 * 2;

	/**
	 * GA根目录
	 */
	String GA_ROOT = "MainActivity";

	String GA_FROM_FLAG = "from";
	
	/** =================测试环境和发布环境控制开关区域 Begin===================== */
	/**
	 * 日志打印开关 true 测试， false 发布
	 */
	boolean log_flag = true;
//	boolean log_flag = com.uzai.app.BuildConfig.LOG_DEBUG;

	/**
	 * 是否开启新功能引导界面 true 启动 ,false 关闭
	 *
	 */
	boolean isUpdateShow = false;
	/**
	 * 是否启动引导蒙版页 true 启动 ,false 关闭
	 * 根据需求要开启蒙版页时启动
	 */
	boolean isShowGuidePage=false;
	/**
	 * GA分析开关 true 发布, false 测试
	 */
	boolean flagForGA = false;

	/**
	 * 版本开关 true 发布, false 测试
	 */
	boolean flagForPhoneVersion = false;
	/*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    String mMode = "00";
	/**
	 * ==============================测试环境和发布环境控制开关区域 End=============================
	 */

    
    
	/**
	 * ====================================接口数据地址 Begin=====================================
	 */
	/**
	 * 服务端请求地址 new
	 */
	/* 发布环境 new */
	String HOST_URL_STR = "https://mapi.uzai.com/Api/";//集成与线上共用这个地址，测试集成时把wifi的DNS改为10.1.3.19
//	String HOST_URL_STR = "http://mapi.uzai.com/Api/";//集成与线上共用这个地址成与线上共用这个地址，测试集成时把wifi的DNS改为10.1.3.19
//	String HOST_URL_STR = "http://10.1.3.205:8011/Api/";//集成与线上共用这个地址，测试集成时把wifi的DNS改为10.1.3.19 //// TODO: 16/5/13
//	String HOST_URL_STR = "http://mapi.uzai.com:9023/Api/";//集成与线上共用这个地址，测试集成时把wifi的DNS改为10.1.3.19 //// TODO: 16/5/13
//	String HOST_URL_STR = "http://172.16.25.2/content/hybrid/";//朱加昭ip服务地址
	String M_Pin_HOST_URL_STR = "http://60.205.180.165:8080/meipin/";

//
	/* 测试环境公司内网 new */
	//上海测试环境
//	String HOST_URL_STR = "http://mapi.uzai.com/Api/";//无需ssl证书认证测试环境
//	String HOST_URL_STR = "http://10.1.3.203:9944/Api/";//单点

	//北京测试环境
//	String HOST_URL_STR = "http://10.1.3.203:9945/Api/";//集成与线上共用这个地址，测试集成时把wifi的DNS改为10.1.3.19 //// TODO: 16/5/13
//	String HOST_URL_STR = "http://mapi.uzai.com:9023/Api/";//集成与线上共用这个地址，测试集成时把wifi的DNS改为10.1.3.19 //// TODO: 16/5/13

	//====================================app支付获取配置信息地址 Begin==================================
	/**
	 * app支付获取配置信息PayConfig接口服务器请求地址
	 */
	/* 发布环境 */
//	String PAY_CONFIG_URL_STR = "https://pay.uzai.com";

	/* 测试环境 */
//	String PAY_CONFIG_URL_STR = "http://pay.uzai.com";//无需ssl证书认证测试环境
	String PAY_CONFIG_URL_STR = "http://paycbm.uzai.com";
	//====================================app支付获取配置信息地址End====================================

	/**
	 * wifi接口服务器请求地址
	 */
	String WIFI_URL_STR = "http://wifiapi.uzai.com/api";
	
	/**
	 * ====================================接口数据地址  End=======================================
	 */

	
	

	/**
	 * ====================================WEB线路详情页地址 Begin=================================
	 */
	/**
	 * 自由行详情页和自由行订单支付共用地址
	 */
	/* 发布环境 */
	 String WAP_FREE_TRAVEL_PRODUCT_DETAIL_URL= "http://www.uzai.com/trip/";
	
	/* 测试环境公司内网 */
//	String WAP_FREE_TRAVEL_PRODUCT_DETAIL_URL = "http://10.1.3.203:3333/trip/";

	/**
	 * 跟团游详情页地址
	 */
	 String WAP_GROUP_TRAVEL_PRODUCT_DETAIL_URL = "http://sh.uzai.com/waptour-";
	
	/* 测试环境公司内网 */
//	String WAP_GROUP_TRAVEL_PRODUCT_DETAIL_URL = "http://10.1.3.203:3003/waptour-";
	 
	/**
	 * 邮轮游选择仓房页地址
	 */
	String WAP_YOULUN_TRAVEL_PRODUCT_DETAIL_URL = "http://www.uzai.com/youlun/";
	
	/**
	 * 邮轮游完善游客信息页地址
	 */
	String WAP_YOULUN_TRAVEL_PERFECT_INFO_URL = "https://buy.uzai.com/m_cruise_two/";
	 
	/**
	 * ====================================WEB线路详情页地址 End====================================
	 */

	/**
	 * ====================================订单支付地址 Begin=======================================
	 */
	/**
	 * 建行支付地址
	 */
	/* 发布环境 */
	String CREATE_ORDER_URL_PAY = "http://wap.uzai.com/";
	/* 测试环境公司内网 */
//	 String CREATE_ORDER_URL_PAY = "http://10.1.3.203:90/";

	/**
	 * 跟团游订单支付地址
	 */
	/* 发布环境 */
	String CREATE_ORDER_URL = "https://buy.uzai.com/";
	/* 测试环境公司内网 */
//	 String CREATE_ORDER_URL= "http://10.1.3.203:90/";

	/**
	 * 现金账户支付地址
	 */
	/* 发布环境 */
	String WAP_CASH_PAY_URL = "https://pay.uzai.com/";
	/* 测试环境 */
//	 String WAP_CASH_PAY_URL = "http://10.1.3.203:8881/";

	/**
	 * 快钱WEB支付地址
	 */
	/* 发布环境 */
	String WEB_PAY_URL = "http://wap.uzai.com/QuickPayBank";
	/* 测试环境 */
//	 String WEB_PAY_URL = "http://10.1.3.203:2088/QuickPayBank";

	/**
	 * ====================================订单支付地址 End=======================================
	 */

	/**
	 * ====================================我的现金帐户地址 Begin==================================
	 */

	/**
	 * 我的现金帐户地址
	 */
	/* 发布环境 */
	String WAP_CASH_ACCOUNT_URL = "https://u.uzai.com/";
	/* 测试环境 */
//	 String WAP_CASH_ACCOUNT_URL = "http://10.1.3.203:5432/";

	/**
	 * ====================================我的现金帐户地址End====================================
	 */

	/**
	 * ====================================我的机票订单地址 Begin==================================
	 */

	/**
	 * 我的机票订单地址
	 */
	/* 发布环境 */
	String MY_PLANE_TICKET_URL = "https://u.uzai.com/mobile/SpecialTicketOrders";
	/* 测试环境 */
//	 String MY_PLANE_TICKET_URL = "http://10.1.3.203:3003/mobile/SpecialTicketOrders";

	/**
	 * ====================================我的机票订单地址End====================================
	 */
	
	/**
	 * 私家团地址
	 */
	String PRIVATE_GROUP_URL = "http://sijia.uzai.com";

	/**
	 * 加密key
	 */
	/* 发布 */
	String PASSWORD_CRYPT_KEY = "uzai0118";
	
	/**
	 * 加密token KEY
	 */
	String TOKEN_CRYPT_KEY = "$app123$";
	
	/**
	 * app支付获取配置信息PayConfig加密token KEY
	 */
	String TOKEN_PAYCONFIG_KEY = "$uz@y15$";

	/**
	 * 快钱信息
	 */
	String MERCHANTID = "999310047220010";//商户ID号
	String URL_TEST_SERVER = "https://mobile.99bill.com:443/payment";
	String PRIVATE_KEY_PATH = "private.pem";//商户私钥

	/* GCM */
	String SENDER_ID = "359334109836";
	/* 微信appid */
	String APP_ID = "wxfdf6170c5b6714a3";
	/**支付宝appid*/
	String ZFB_APPid="2015091700293886";
	/**
	 * 微信开放平台和商户约定的密钥
	 * 
	 * 注意：不能hardcode在客户端，建议genSign这个过程由服务器端完成
	 */
	String APP_SECRET = "3593ce097089ba2d8ae1663a3f7061b6"; // wxd930ea5d5a258f4f 对应的密钥
}
