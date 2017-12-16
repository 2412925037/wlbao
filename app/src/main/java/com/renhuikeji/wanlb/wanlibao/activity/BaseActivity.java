package com.renhuikeji.wanlb.wanlibao.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.ChatManager;
import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogManager;
import com.renhuikeji.wanlb.wanlibao.utils.JudgePhoneBrand;
import com.renhuikeji.wanlb.wanlibao.utils.MsgDbHelper;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.StatusBarUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.widget.PushDialog;

import java.util.List;

import static com.renhuikeji.wanlb.wanlibao.utils.ActivityUtils.isTopActivity;

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



    }


    @Override
    protected void onResume() {
        super.onResume();
        //app.bindActivity(this);
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
        //invokeGc();
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
