package com.renhuikeji.wanlb.wanlibao.activity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ali.auth.third.ui.context.CallbackContext;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.Message;
import com.renhuikeji.wanlb.wanlibao.bean.ShareBean;
import com.renhuikeji.wanlb.wanlibao.config.Contants;
import com.renhuikeji.wanlb.wanlibao.fragment.HandleFragment;
import com.renhuikeji.wanlb.wanlibao.fragment.IndexFragment;
import com.renhuikeji.wanlb.wanlibao.fragment.MyFragment;
import com.renhuikeji.wanlb.wanlibao.fragment.TaoBaoFragment;
import com.renhuikeji.wanlb.wanlibao.fragment.WithDrawFragment;
import com.renhuikeji.wanlb.wanlibao.interf.IBackInterface;
import com.renhuikeji.wanlb.wanlibao.utils.ButtonUtils;
import com.renhuikeji.wanlb.wanlibao.utils.CheckUtil;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogManager;
import com.renhuikeji.wanlb.wanlibao.utils.HuanXinUtils;
import com.renhuikeji.wanlb.wanlibao.utils.JgSetAliasUtil;
import com.renhuikeji.wanlb.wanlibao.utils.MsgDbHelper;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.TimeRemind;
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
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.renhuikeji.wanlb.wanlibao.utils.SPUtils.get;

public class MainActivity extends BaseActivity implements View.OnClickListener, IBackInterface {

    @BindView(R.id.bottom_nav_container)
    LinearLayout bottom_nav_container;
    @BindView(R.id.fragmentRoot)
    FrameLayout fragmentRoot;
    private ArrayList<View> bottomNavs;
    //private Fragment fragment; //用于传递监听back键的fragment
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private IndexFragment indexFragment;
    private TaoBaoFragment taoBaoFragment;
    //private HandleFragment handleFragment;
    private WithDrawFragment withDrawFragment;
    private MyFragment myFragment;
    private Calendar mCalendar;
    private String uid;
    private AlarmManager am;
    private App app;
    private MsgDbHelper helper;

    private int getStatusBarHeight() {
        int statusBarHeight;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        return statusBarHeight;
    }
    private int statusHeight;
    public int getStatusHeight() {
        return statusHeight;
    }
    String APP_ID = "1106235186";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        app = (App) getApplication();
        helper=app.getDbHelper();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
//        permission();
        startRemind();
        initView();
        uid = (String) SPUtils.get(this, Constant.User_Uid, "");
        JgSetAliasUtil util = new JgSetAliasUtil(this);
        util.setAlias(uid);

        statusHeight = getStatusBarHeight();


        mTencent = Tencent.createInstance(APP_ID, MainActivity.this);
        mListener = new BaseUiListener();

        initSharePop();

        //app.bindActivity(MainActivity.this);
        boolean b = (boolean) SPUtils.get(this, Constant.MAIN_DISPLAY, false);
        boolean isReceive = (boolean) SPUtils.get(this, Constant.RECEIVE_MSG, false);
        //        如果此时不在mainactivity并且接受到推送,就把dialog取消.
        if (isReceive) {
            Dialog dialog = getDialog();
            if (dialog != null) {
                dialog.dismiss();
            }
        }
        showMsgDialog();
    }

    public void permission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                ToastUtils.toastForLong(MainActivity.this,"请允许在其他应用的上层显示,来通知信息.");
                return;
            }
        }
    }

    private void initView() {
        //initFragment();
        bottomNavs = new ArrayList<>();
        for (int i = 0; i < bottom_nav_container.getChildCount(); i++) {

                View child = bottom_nav_container.getChildAt(i);
                child.setTag(i);
                bottomNavs.add(child);
                child.setOnClickListener(this);
        }
        bottomNavs.get(0).setSelected(true);
        startFragment(0);
    }

//    private void initFragment() {
//        indexFragment = new IndexFragment();
//        FragmentTransaction fragmentTransaction = manager.beginTransaction();
//        fragmentTransaction.add(R.id.fragmentRoot, indexFragment, "homeFragment");
//        fragmentTransaction.commit();
//    }


    /**
     * 开启提醒
     */
    public void startRemind() {
        long firstTime = SystemClock.elapsedRealtime();    //获取系统当前时间
        long systemTime = System.currentTimeMillis();//
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); //  这里时区需要设置一下，不然会有8个小时的时间差
        calendar.set(Calendar.HOUR_OF_DAY, 11);//设置为11：00点提醒  24进制
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //选择的定时时间
        long selectTime = calendar.getTimeInMillis();    //计算出设定的时间
        //  如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if (systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }
        long time = selectTime - systemTime;// 计算现在时间到设定时间的时间差
        long my_Time = firstTime + time;//系统 当前的时间+时间差
        TimeRemind.setAlarmTime(MainActivity.this, my_Time, "activity.wch.alarm", AlarmManager.INTERVAL_DAY);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //app.unbindActivity(MainActivity.this);
        TimeRemind.canalAlarm(MainActivity.this, "activity.wch.alarm");
    }

    /***************分享按钮点击*****************/
    public SharePopupWindow sharePopupWindow;
    private String share_title;
    private String share_content;
    private String share_logo;
    private String share_url;

    private void initSharePop() {

        sharePopupWindow = new SharePopupWindow(this);
        sharePopupWindow.setonItemClickListener(new SharePopupWindow.onMyItemClickListener() {
            @Override
            public void onitemclick(final View view) {

                 String uid = (String) get(MainActivity.this, Constant.User_Uid, "");
                 String msession = (String) get(MainActivity.this, Constant.MSESSION, "");

                sharePopupWindow.dismiss();
                String url = Contants.SHARE_URL + "&uid="+uid;
                OkHttpUtils.getInstance().getDatas(MainActivity.this, url, msession, new OkHttpUtils.HttpCallBack() {
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
                                //if (!ButtonUtils.isFastDoubleClick())
                                    shareToQZone();
                                break;
                            case 1:
                                //if (!ButtonUtils.isFastDoubleClick())
                                    shareToQQ();
                                break;
                            case 2:
                                //会话
                                //if (!ButtonUtils.isFastDoubleClick()) {

                                    if (App.api.isWXAppInstalled()) {
                                        shareLinkPage(false);
                                    } else {
                                        ToastUtil.getInstance().showToast("你当前并未安装微信");
                                    }
                                //}
                                break;
                            case 3:
                                //if (!ButtonUtils.isFastDoubleClick()) {

                                    //朋友圈
                                    if (App.api.isWXAppInstalled()) {
                                        shareLinkPage(true);
                                    } else {
                                        ToastUtil.getInstance().showToast("你当前并未安装微信");
                                    }
                                //}
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
    public void onClick(View v) {
        int i = (int) v.getTag();
        if (i == 2) {
            if (!ButtonUtils.isFastDoubleClick()) {
                if (sharePopupWindow != null)
                    sharePopupWindow.showPopupWindow(findViewById(R.id.viewview));
            }

            return;
        }

        reset();
        v.setSelected(true);

        transaction = manager.beginTransaction();
        startFragment(i);

    }

    public void select(int i){

        reset();
        bottomNavs.get(i).setSelected(true);
        transaction = manager.beginTransaction();
        startFragment(i);
    }

    private int selectedId = 0;

    private void startFragment(int i) {
        // transaction = manager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        if(i==1){
            if (CheckUtil.isLogin(MainActivity.this)) {
                HuanXinUtils.getInstance().openHuanXin(MainActivity.this);
            } else {
                ToastUtils.toastForLong(MainActivity.this, "请先登录!");
            }
            bottomNavs.get(selectedId).setSelected(true);
        }else{
            selectedId=i;
            hideFragments(transaction);
        }
        switch (i) {
            case 0:
                if (indexFragment != null) {
                    transaction.show(indexFragment);
                } else {
                    indexFragment = new IndexFragment();
                    transaction.add(R.id.fragmentRoot, indexFragment, "homeFragment");
                }
                break;
            case 1:

               /* if (taoBaoFragment != null) {
                    transaction.show(taoBaoFragment);
                } else {
                    taoBaoFragment = new TaoBaoFragment();
                    transaction.add(R.id.fragmentRoot, taoBaoFragment, "categoryFragment");
                }*/
                break;

            case 2:

                break;
//            case 2:
//
//
////                if (handleFragment != null) {
////                    transaction.show(handleFragment);
////                } else {
////                    handleFragment = new HandleFragment();
////                    transaction.add(R.id.fragmentRoot, handleFragment, "shoppingCartFragment");
////                }
//                break;
            case 3:
                if (withDrawFragment != null) {
                    transaction.show(withDrawFragment);
                } else {
                    withDrawFragment = new WithDrawFragment();
                    transaction.add(R.id.fragmentRoot, withDrawFragment, "withDrawFragment");
                }
                break;

            case 4:
                if (myFragment != null) {
                    transaction.show(myFragment);
                } else {
                    myFragment = new MyFragment();
                    transaction.add(R.id.fragmentRoot, myFragment, "meFragment");
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    long exitTime = 0;

    // 点击返回按钮
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (taoBaoFragment != null && taoBaoFragment.onBackPressed()) {
                //实现具体的点击效果
                //((TaoBaoFragment) fragment).webTaobao.goBack();
                taoBaoFragment.test();
                return true;
            //}
//            else if (handleFragment != null && handleFragment.onBackPressed()) {
//                handleFragment.test();
//                return true;
            } else if (!bottomNavs.get(0).isSelected()) {
                transaction = manager.beginTransaction();
                hideFragments(transaction);
                if (indexFragment != null) {
                    transaction.show(indexFragment);

                    bottomNavs.get(0).setSelected(true);
                    bottomNavs.get(1).setSelected(false);
                    //bottomNavs.get(2).setSelected(false);
                    bottomNavs.get(3).setSelected(false);
                    bottomNavs.get(4).setSelected(false);
                } else {
                    indexFragment = new IndexFragment();
                    transaction.add(R.id.fragmentRoot, indexFragment);

                    bottomNavs.get(0).setSelected(true);
                    bottomNavs.get(1).setSelected(false);
                    //bottomNavs.get(2).setSelected(false);
                    bottomNavs.get(3).setSelected(false);
                    bottomNavs.get(4).setSelected(false);
                }
                transaction.commitAllowingStateLoss();
                return false;
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ToastUtils.toastForShort(MainActivity.this, "再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                    return false;
                } else {
                    finish();
                }
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    private void reset() {
        for (View v : bottomNavs) {
            v.setSelected(false);
        }
    }

    /**
     * 隐藏fragment
     */
    private void hideFragments(FragmentTransaction transaction) {

        if (indexFragment != null) {
            transaction.hide(indexFragment);
        }
//        if (handleFragment != null) {
//            transaction.hide(handleFragment);
//        }
        if (taoBaoFragment != null) {
            transaction.hide(taoBaoFragment);
        }
        if (withDrawFragment != null) {
            transaction.hide(withDrawFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }


    }

    //登录须重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        CallbackContext.onActivityResult(requestCode, resultCode, data);

        if (mTencent != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
        }
    }

    @Override
    public void setSelectedFragment(TaoBaoFragment fragment) {
        this.taoBaoFragment = fragment;
    }

    PushDialog dialog=null;
    private void showMsgDialog() {
        DialogManager.clear();
        List<Message> msgs=helper.queryUnReadMsg();
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
}
