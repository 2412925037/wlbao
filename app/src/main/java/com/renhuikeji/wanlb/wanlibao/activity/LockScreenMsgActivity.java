package com.renhuikeji.wanlb.wanlibao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.ScreenMsgAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.BaseBean;
import com.renhuikeji.wanlb.wanlibao.bean.Message;
import com.renhuikeji.wanlb.wanlibao.bean.TypeJumpBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.CheckUtil;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.HuanXinUtils;
import com.renhuikeji.wanlb.wanlibao.utils.MsgDbHelper;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LockScreenMsgActivity extends Activity {


    @BindView(R.id.activity_lock_screen_msg)
    RelativeLayout activityLockScreenMsg;
    @BindView(R.id.clock)
    TextClock clock;
    @BindView(R.id.unlock)
    TextView unlock;
    @BindView(R.id.lock_msg_tv)
    TextView msgTv;
    @BindView(R.id.lock_time_tv)
    TextView timeTv;
    @BindView(R.id.lock_title_tv)
    TextView lockTitleTv;
    private List<BaseBean> mDatas = new ArrayList<>();
    private ScreenMsgAdapter msgAdapter;
    private PowerManager.WakeLock wakeLock;
    private boolean toMianActivity = false;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private App app;
    private MsgDbHelper helper;
    private int msgID;
    TypeJumpBean bean = null;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                |WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        lightScreen();
        setContentView(R.layout.activity_lock_screen_msg);
        ButterKnife.bind(this);
        app = (App) this.getApplication();
        helper = app.getDbHelper();

        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");//设置日期格式
        timeTv.setText(df.format(new Date()));
//        记录当前activity在前台
        SPUtils.put(this, "showScreenMsg", true);
    }


    /**
     * 点亮屏幕
     */
    private void lightScreen() {
        PowerManager pm = (PowerManager) this.getSystemService(this.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            if (!pm.isInteractive()) {
                wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
                wakeLock.acquire();
            }
        } else {
            if (!pm.isScreenOn()) {
                wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
                wakeLock.acquire();
            }
        }
      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(wakeLock.isHeld()){
                    wakeLock.release();
                }
            }
        },3000);*/
    }


    //Activity中复写OnNewIntent
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        lightScreen();
        getPushMsg(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getPushMsg(getIntent());
    }

    private void getPushMsg(Intent i) {
        List<Message> msgs = helper.queryUnReadMsg();
        Log.d("CCC","size-lock:"+msgs.size());
        if (!msgs.isEmpty()) {
            Message item = msgs.get(msgs.size() - 1);
            msgTv.setText(item.getContent());
            msgID = item.getId();
            type = item.getJumpType();

        }else{
           type= i.getStringExtra(Constant.JUMP);
        }
        setMsgTitle(type);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wakeLock == null) {
            return;
        }
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
        SPUtils.put(this, "showScreenMsg", false);
    }


    private void setMsgTitle(String type) {
        if (TextUtils.equals(type, Constant.JUMP_SESSION)) {
            lockTitleTv.setText("万利宝客服");
        }else{
            lockTitleTv.setText("万利宝");
        }
    }

    @OnClick({R.id.unlock, R.id.lock_msg_tv})
    public void onClick(View view) {
        Intent i = new Intent();
        i.setClass(LockScreenMsgActivity.this, MainActivity.class);
        switch (view.getId()) {
            case R.id.lock_msg_tv:
                if (!TextUtils.isEmpty(type)) {
                    switch (type) {
                        case Constant.JUMP_ACCOUNT:
                            i.setClass(LockScreenMsgActivity.this, CashFlowActivity.class);
                            startActivity(i);
                            break;
                        case Constant.JUMP_ORDER:
                            i.setClass(LockScreenMsgActivity.this, SettledBillsActivity.class);
                            startActivity(i);
                            break;
                        case Constant.JUMP_FRIEND:
                            i.setClass(LockScreenMsgActivity.this, MyFriendActivity.class);
                            startActivity(i);
                            break;
                        case Constant.JUMP_RECOM:
                            i.putExtra("url", ConfigValue.INVITE_FRIENDS);
                            i.putExtra("title", "邀请好友");
                            i.putExtra("right_text", "");
                            i.setClass(LockScreenMsgActivity.this, WebShowActivity.class);
                            startActivity(i);
                            break;
                        case Constant.JUMP_CASH:
                            i.setClass(LockScreenMsgActivity.this, FinancialDetailActivity.class);
                            startActivity(i);
                            break;
                        case Constant.JUMP_SIGN:
                            i.setClass(LockScreenMsgActivity.this, SignInActivity.class);
                            startActivity(i);
                            break;
                        case Constant.JUMP_SESSION:
                            if (CheckUtil.isLogin(LockScreenMsgActivity.this)) {
                                HuanXinUtils.getInstance().openHuanXin(LockScreenMsgActivity.this);
                                helper.deteMsgByKefu();
                            } else {
                                ToastUtils.toastForLong(LockScreenMsgActivity.this, "请先登录!");
                            }
                            break;
                    }
                } else {
                    startActivity(i);
                }
                LockScreenMsgActivity.this.finish();
                helper.deteMsg(msgID);
                break;
            case R.id.unlock:
                LockScreenMsgActivity.this.finish();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
     /*   LogUtil.showLog("recycler:"+recycler.getY()+"-top:"+recycler.getHeight()+"-h:");
        LogUtil.showLog("dianji:"+ev.getY());*/
//        int click= (int) ev.getY();
//        int recyclerViewHeight=recycler.getHeight();
//        int recyclerTop= (int) recycler.getY();
//        if(click>=recyclerTop&&click<=(recyclerTop+recyclerViewHeight)){
//            this.getSwipeBackLayout().setEnableGesture(false);
//        }else{
//            this.getSwipeBackLayout().setEnableGesture(true);
//        }
        return super.dispatchTouchEvent(ev);
    }
}
