package com.renhuikeji.wanlb.wanlibao.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatManager;
import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.AppManager;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.ShareBean;
import com.renhuikeji.wanlb.wanlibao.config.Contants;
import com.renhuikeji.wanlb.wanlibao.fragment.MyFragment;
import com.renhuikeji.wanlb.wanlibao.utils.ButtonUtils;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogManager;
import com.renhuikeji.wanlb.wanlibao.utils.JudgePhoneBrand;
import com.renhuikeji.wanlb.wanlibao.utils.MsgDbHelper;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.StatusBarUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.views.SharePopupWindow;
import com.renhuikeji.wanlb.wanlibao.widget.PushDialog;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;
import java.util.List;

import static com.renhuikeji.wanlb.wanlibao.utils.ActivityUtils.isTopActivity;
import static com.renhuikeji.wanlb.wanlibao.utils.SPUtils.get;

//封装基类activity，自带自定义dialog
public class BaseActivity extends AppCompatActivity {

    private App app;

    public Context context;
    private DialogReceiver dialogReceiver;
    private boolean isMainDisplay=true;
    private MsgDbHelper helper;
    private ChatManager.MessageListener HxListener;

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Log.i("tag",getClass().getSimpleName());

        if(!getClass().getSimpleName().equals("MainActivity")) {

//            if(getClass().getSimpleName().equals("TaoBaoSearchActivity")){
//                StatusBarUtil.setColor(this, getResources().getColor(R.color.colorWhite));
//            }else
//
//            StatusBarUtil.setColor(this, getResources().getColor(R.color.all_pink), 55);
//            if (JudgePhoneBrand.SYS_MIUI.equals(JudgePhoneBrand.getSystem())) {
//                //            initSystemBar();
//                requestWindowFeature(Window.FEATURE_NO_TITLE);
//                //         隐藏状态栏
//                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            } else {
//                StatusBarUtil.setColor(this, getResources().getColor(R.color.all_pink), 55);
//            }
        }


        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        app = (App) this.getApplication();
        helper=app.getDbHelper();
        dialogReceiver = new DialogReceiver();
        IntentFilter filter = new IntentFilter(Constant.RECEIVER_DIALOG);
        IntentFilter filter1 = new IntentFilter(Constant.QIANDAO);
        registerReceiver(dialogReceiver, filter);
        //AppManager.getAppManager().addActivity(this);
        this.context = this;

        String APP_ID = "1106235186";
        mTencent = Tencent.createInstance(APP_ID, this);
        mListener = new BaseUiListener();
        initSharePop();

    }

    public SharePopupWindow sharePopupWindow;
    private String share_title;
    private String share_content;
    private String share_logo;
    private String share_url;

    private void initSharePop() {

        final String uid = (String) get(this, Constant.User_Uid, "");
        final String msession = (String) get(this, Constant.MSESSION, "");

        sharePopupWindow = new SharePopupWindow(this);
        sharePopupWindow.setonItemClickListener(new SharePopupWindow.onMyItemClickListener() {
            @Override
            public void onitemclick(final View view) {
                sharePopupWindow.dismiss();
                String url = Contants.SHARE_URL + "&uid="+uid;
                new OkHttpUtils().getDatas(BaseActivity.this, url, msession, new OkHttpUtils.HttpCallBack() {
                    @Override
                    public void onSusscess(String data) {

                        ShareBean shareBean = new Gson().fromJson(data,ShareBean.class);
                        share_title = shareBean.getShareData().getTitle();
                        share_content = shareBean.getShareData().getContent();
                        share_logo = shareBean.getShareData().getLogo();
                        share_url = shareBean.getShareData().getUrl();

                        int id = (int) view.getTag();
                        switch (id) {
                            case 0:
                                if (!ButtonUtils.isFastDoubleClick())
                                    shareToQZone();
                                break;
                            case 1:
                                if (!ButtonUtils.isFastDoubleClick())
                                    shareToQQ();
                                break;
                            case 2:
                                //会话
                                if (!ButtonUtils.isFastDoubleClick()) {

                                    if (App.api.isWXAppInstalled()) {
                                        shareLinkPage(false);
                                    } else {
                                        ToastUtil.getInstance().showToast("你当前并未安装微信");
                                    }
                                }
                                break;
                            case 3:
                                if (!ButtonUtils.isFastDoubleClick()) {

                                    //朋友圈
                                    if (App.api.isWXAppInstalled()) {
                                        shareLinkPage(true);
                                    } else {
                                        ToastUtil.getInstance().showToast("你当前并未安装微信");
                                    }
                                }
                                break;
                        }

                    }

                    @Override
                    public void onError(String meg) {
                        super.onError(meg);
                        ToastUtil.getInstance().showToast("分享失败");
                    }
                });
            }
        });

    }

    private void shareToQZone() {
        Bundle params2 = new Bundle();
        params2.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params2.putString(QzoneShare.SHARE_TO_QQ_TITLE, share_title);// 标题
        params2.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, share_content);// 摘要
        params2.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, share_url);// 内容地址
        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add(share_logo);
        params2.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
        mTencent.shareToQzone(this, params2, mListener);
    }

    private void shareToQQ() {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, share_title);// 标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, share_content);// 摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, share_url);// 内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, share_logo);// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其它附加功能");

        mTencent.shareToQQ(this, params, mListener);
    }

    private Tencent mTencent;

    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            ToastUtil.getInstance().showToast("分享成功");
        }

        @Override
        public void onError(UiError uiError) {
            ToastUtil.getInstance().showToast("分享失败");
        }

        @Override
        public void onCancel() {
            ToastUtil.getInstance().showToast("分享取消");
        }
    }

    private BaseUiListener mListener;

    public void shareLinkPage(boolean isTimelineCb) {


        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = share_url;

        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = share_title;
        msg.description = share_content;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        App.api.sendReq(req);
    }

    @Override
    protected void onResume() {
        super.onResume();
        app.bindActivity(this);
        isMainDisplay= isTopActivity("com.renhuikeji.wanlb.wanlibao","com.renhuikeji.wanlb.wanlibao.activity.MainActivity",this);
        SPUtils.put(this,Constant.MAIN_DISPLAY,isMainDisplay);
//        initHuanXinListener();

    }

    // 内存紧张时回收图片资源
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    // 内存紧张时回收图片资源 API4.0
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }

    public void invokeGc() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    finalize();
                } catch (Throwable e) {
                }
                // 提醒系统及时回收
                System.gc();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.unbindActivity(this);
        if (dialogReceiver != null) {
            unregisterReceiver(dialogReceiver);
        }
        invokeGc();
       /* if(HxListener!=null){
            ChatClient.getInstance().getChat().removeMessageListener(HxListener);
        }*/

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void showToast(String string) {
        ToastUtils.toastForShort(BaseActivity.this,string);
    }

    class DialogReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//           当mainactivity不在前台时候,只打开一个dialog
            if(!isMainDisplay){
                SPUtils.put(context,Constant.RECEIVE_MSG,true);
            }
            showMsgDialog();

        }
    }

    PushDialog dialog=null;
    private void showMsgDialog() {
        DialogManager.clear();
        List<com.renhuikeji.wanlb.wanlibao.bean.Message> msgs=helper.queryUnReadMsg();
        if(msgs.isEmpty()){
            return;
        }
        for(com.renhuikeji.wanlb.wanlibao.bean.Message item :msgs ){
             dialog=new PushDialog(this,item);
             DialogManager.add(dialog);
            try {
                if(!dialog.isShowing()){
                    dialog.show();
                }
            } catch (Exception e) {

            }
        }

    }
    public Dialog getDialog(){
        return dialog;
    }



}
