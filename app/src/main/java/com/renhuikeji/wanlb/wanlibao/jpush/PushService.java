package com.renhuikeji.wanlb.wanlibao.jpush;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.LockScreenMsgActivity;
import com.renhuikeji.wanlb.wanlibao.activity.TaoBaoSearchActivity;
import com.renhuikeji.wanlb.wanlibao.bean.Message;
import com.renhuikeji.wanlb.wanlibao.bean.TypeJumpBean;
import com.renhuikeji.wanlb.wanlibao.utils.ActivityUtils;
import com.renhuikeji.wanlb.wanlibao.utils.BackgroundUtil;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.JudgePhoneBrand;
import com.renhuikeji.wanlb.wanlibao.utils.JumpMsgUtil;
import com.renhuikeji.wanlb.wanlibao.utils.MsgDbHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

import static com.renhuikeji.wanlb.wanlibao.utils.Constant.JUMP_ACCOUNT;
import static com.renhuikeji.wanlb.wanlibao.utils.Constant.JUMP_CASH;
import static com.renhuikeji.wanlb.wanlibao.utils.Constant.JUMP_FRIEND;
import static com.renhuikeji.wanlb.wanlibao.utils.Constant.JUMP_ORDER;
import static com.renhuikeji.wanlb.wanlibao.utils.Constant.JUMP_RECOM;
import static com.renhuikeji.wanlb.wanlibao.utils.Constant.JUMP_SIGN;

/**
 * Created by Administrator on 2017/4/17.
 */

public class PushService extends BroadcastReceiver {
    private static final String TAG = "CCC";
    private static final String TYPE_THIS = "this";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");
            //  processCustomMessage(context, bundle);
            showInspectorRecordNotification(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");

            openNotification(context, bundle);

        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void showInspectorRecordNotification(Context context, Bundle bundle) {
        String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("title");  //用户点开通知，根据extra的key和value进行判断
        } catch (Exception e) {
            Log.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }

        //获取接收到推送时的系统时间
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
        String date = fmt.format(rightNow.getTime());

        RemoteViews customView = new RemoteViews(context.getPackageName(), R.layout.msg_item);
        customView.setTextViewText(R.id.tv_msg_title, "今日上新" + myValue + "万件");
        customView.setTextViewText(R.id.tv_msg_content, content);
        customView.setTextViewText(R.id.tv_msg_date, date);

        //自己创建通知
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        Intent transferIntent = new Intent(context, TaoBaoSearchActivity.class);
        transferIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //transferIntent.putExtra(PushReceiverConstant.KEY_FORM_TYPE, pushType);
        // 第二个参数不能写死，可以写一个随机数或者是时间毫秒数 保证唯一
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) (Math.random() * 100)
                , transferIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContent(customView)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setTicker("")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setSmallIcon(R.mipmap.ic_launcher);
        Notification notify = mBuilder.build();
        notify.contentView = customView;
        notify.flags |= Notification.FLAG_AUTO_CANCEL; // 点击通知后通知栏消失
        // 通知id需要唯一，要不然会覆盖前一条通知
        int notifyId = (int) System.currentTimeMillis();
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notifyId, notify);
    }


    private void processCustomMessage(Context context, Bundle bundle) {

// 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);

        System.out.println("收到了自定义消息@@消息内容是:" + content);
        System.out.println("收到了自定义消息@@消息extra是:" + extra);

        //**************解析推送过来的json数据并存放到集合中 begin******************
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(extra);
            String type = jsonObject.getString("type");
            map.put("type", type);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        map.put("content", content);
        //获取接收到推送时的系统时间
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String date = fmt.format(rightNow.getTime());
        map.put("date", date);
        // MyApp.data.add(map);


    }

    private void receivingNotification(Context context, Bundle bundle) {
        String rece_id = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        Log.d(TAG, " rece_id : " + rece_id);
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);
        toLockScreenActivity(context,title,message,extras,rece_id);
    }

    private void openNotification(Context context, Bundle bundle) {
        String rece_id = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        JumpMsgUtil.jumpTo(context,jumpType,-1,rece_id);

       /* Intent i=new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i)*/;
    }
    String jumpType;
    private void toLockScreenActivity(Context context,String title, String description,String jumpStr,String rece_id) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = pm.isInteractive();
        }else{
            isScreenOn = pm.isScreenOn();
        }

        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//        Log.d("CCC","km:"+ km.inKeyguardRestrictedInputMode());
//       判断锁屏界面是否在显示
//        boolean b= (boolean) SPUtils.get(context,"showScreenMsg",false);
        Intent i=new Intent();
        i.putExtra("description",description);
        i.putExtra(Constant.JUMP,jumpStr);
        jumpType=parseMsg(jumpStr);

        Message msg=new Message();
        msg.setContent(description);
        msg.setJumpType(jumpType);
        msg.setIsread(0);
        msg.setRece_id(rece_id);
        MsgDbHelper helper=new MsgDbHelper(context);
        int indext=helper.insert(description,0,jumpType,rece_id);
//        判断是否是小米
        Boolean isForeground= BackgroundUtil.getRunningAppProcesses(context, context.getPackageName());
        if (JudgePhoneBrand.SYS_MIUI.equals(JudgePhoneBrand.getSystem())) {
            if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.LOLLIPOP){
                isForeground=BackgroundUtil.getRunningTask(context, context.getPackageName());
            }
        }
        boolean isLockShow=ActivityUtils.isTopActivity("com.renhuikeji.wanlb.wanlibao","com.renhuikeji.wanlb.wanlibao.activity.LockScreenMsgActivity",context);
        i.putExtra(Constant.JUMP, Constant.JUMP);
//       当屏幕被点亮,任务在前台,并且锁屏没在top
        if(isScreenOn&&isForeground&&!isLockShow){
            i.setAction(Constant.RECEIVER_DIALOG);
            i.putExtra("startType","unlock");
            context.sendBroadcast(i);
        }else{
            i.putExtra("title",title);
            i.setClass(context, LockScreenMsgActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("startType","lock");
            try{
                context.startActivity(i);
            }catch(Exception e){
            }
        }

      /*  if (km.inKeyguardRestrictedInputMode()) {
           *//* KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
            //解锁
            kl.disableKeyguard();*//*
            // 处于锁屏状态
           *//* Intent intent = new Intent(context,ScreenAndLockService.class);
            context.startService(intent);*//*
            context.startActivity(i);
        }*/
    }
    private String  parseMsg(String jumpStr){
        TypeJumpBean bean=null;
        String type="";
        if(!TextUtils.isEmpty(jumpStr)){
            try {
                bean= new Gson().fromJson(jumpStr, TypeJumpBean.class);
            }catch (Exception e){}
        }
        if(bean!=null){
//
            if(!TextUtils.isEmpty(bean.getA())&&TextUtils.equals(bean.getA(),JUMP_ACCOUNT)){
                type=bean.getA();
            }
            if(!TextUtils.isEmpty(bean.getO())&&TextUtils.equals(bean.getO(),JUMP_ORDER)){
                type=bean.getO();
            }
            if(!TextUtils.isEmpty(bean.getF())&&TextUtils.equals(bean.getF(),JUMP_FRIEND)){
                type=bean.getF();
            }
            if(!TextUtils.isEmpty(bean.getR())&&TextUtils.equals(bean.getR(),JUMP_RECOM)){
                type=bean.getR();
            }
            if(!TextUtils.isEmpty(bean.getC())&&TextUtils.equals(bean.getC(),JUMP_CASH)){
                type=bean.getC();
            }
            if(!TextUtils.isEmpty(bean.getS())&&TextUtils.equals(bean.getS(),JUMP_SIGN)){
                type=bean.getS();
            }
        }
        return type;

    }
}

