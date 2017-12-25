package com.renhuikeji.wanlb.wanlibao;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.renhuikeji.wanlb.wanlibao.activity.LockScreenMsgActivity;
import com.renhuikeji.wanlb.wanlibao.utils.BackgroundUtil;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.MsgDbHelper;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static com.renhuikeji.wanlb.wanlibao.utils.ActivityUtils.isTopActivity;

/**
 * Created by Administrator on 2017/4/19.
 *
 */

public class App extends Application {

    public static Context context;

    public static final String PERSONAL_DATA = "personal_data";
    private List<Activity> activities = new ArrayList<>();
    private ChatManager.MessageListener HxListener;
    private String content;
    private String receId;

    public static IWXAPI api;
    public static final String APP_ID = "wx33c86a95584f8c11";

    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
    }

    public void bindActivity(Activity activity) {
        activities.add(activity);
    }

    public void unbindActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {

        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        activities.clear();

    }

    ////////////////////////// 电商相关 //////////////////////////
    public static App application = null;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new AndroidLogAdapter());

        //qrcode初始化
        //ZXingLibrary.initDisplayOpinion(this);
        application = this;
        context = getApplicationContext();

        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                AlibcTradeSDK.setForceH5(true);
                SPUtils.put(getApplicationContext(), Constant.INIT_ALI, true);
                /**
                 * 是否走强制H5的逻辑。false：按照默认规则策略打开页面；true：全部页面均为H5打开;
                 * 注意：初始化完成后调用才能生效
                 *
                 * @param isforceH5 （默认为false）
                 * @return 返回打开策略是否设置成功
                 */
            }

            @Override
            public void onFailure(int i, String s) {
                SPUtils.put(getApplicationContext(), Constant.INIT_ALI, false);
                //Toast.makeText(App.this, "初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT).show();
                ToastUtils.toastForShort(App.this, "初始化失败");
            }
        });
        // 环信
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey("1186170603115537#kefuchannelapp42516");//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId("42516");//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”
        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(this, options)) {
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(this);
        //后面可以设置其他属性

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //腾讯bugly开始
        //是否自动检查更新
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = false;
        //bugly
        Bugly.init(getApplicationContext(), "3508e6ebc0", false);
        //腾讯bugly开始

        //   初始化数据库
        initDataBase();
        //  初始化环信
        initHuanXinListener();

        regToWx();
    }


    MsgDbHelper helper;

    private void initDataBase() {
        helper = new MsgDbHelper(getApplicationContext());

    }

    public MsgDbHelper getDbHelper() {
        return helper;
    }


    public static Context getContext() {
        return context;
    }

    private void initHuanXinListener() {
        if (HxListener == null) {

            HxListener = new ChatManager.MessageListener() {

                @Override
                public void onMessage(final List<Message> list) {
                    //收到普通消息
                    boolean isHxOpen = isTopActivity("com.renhuikeji.wanlb.wanlibao", "com.hyphenate.helpdesk.easeui.ui.BaseChatActivity", context);
                    if (isHxOpen) {
                        return;
                    }
                    Log.d("CCC", "&&&&&");
                    content = "有一条会话信息!";
                    String res = list.get(0).getBody().toString();
                    receId = list.get(0).getMsgId();
                    String[] ress = res.split(":");
                    if (ress.length > 1) {
                        String type = ress[0];
                        switch (type) {
                            case "txt":
                                content = ress[1];
                                break;
                            case "voice":
                                content = "有一条语音信息!";
                                break;
                            case "image":
                                content = "有一条图片信息!";
                                break;
                            case "normal file":
                                content = ress[1];
                                break;
                        }
                    }
                    toLockScreenActivity();
                }

                @Override
                public void onCmdMessage(List<Message> list) {
                    //收到命令消息，命令消息不存数据库，一般用来作为系统通知，例如留言评论更新，
                    //会话被客服接入，被转接，被关闭提醒
                    Log.d("CCC", "dsf:" + list.get(0).toString());
                }

                @Override
                public void onMessageStatusUpdate() {
                    //消息的状态修改，一般可以用来刷新列表，显示最新的状态

                }

                @Override
                public void onMessageSent() {
                    //发送消息后，会调用，可以在此刷新列表，显示最新的消息

                }
            };
            ChatClient.getInstance().getChat().addMessageListener(HxListener);
        }

    }

    private void toLockScreenActivity() {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = pm.isInteractive();
        } else {
            isScreenOn = pm.isScreenOn();
        }
        Intent i = new Intent();
        MsgDbHelper helper = new MsgDbHelper(context);
        int indext = helper.insert(content, 0, Constant.JUMP_SESSION, receId);
        Boolean isForeground = BackgroundUtil.getRunningAppProcesses(context, context.getPackageName());
        boolean isLockShow = isTopActivity("com.renhuikeji.wanlb.wanlibao", "com.renhuikeji.wanlb.wanlibao.activity.LockScreenMsgActivity", context);
        i.putExtra(Constant.JUMP, Constant.JUMP_SESSION);
        if (isScreenOn && isForeground && !isLockShow) {
            i.setAction(Constant.RECEIVER_DIALOG);
            i.putExtra("startType", "unlock");
            context.sendBroadcast(i);
        } else {
            i.setClass(context, LockScreenMsgActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("startType", "lock");
            try {
                context.startActivity(i);
            } catch (Exception e) {

            }
        }
    }


}
