package com.renhuikeji.wanlb.wanlibao.receiver;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.view.WindowManager;

import com.renhuikeji.wanlb.wanlibao.utils.TimeRemind;

/**
 * 每天11点接受到广播，显示签到提示信息
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //因为setWindow只执行一次，所以要重新定义闹钟实现循环。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TimeRemind.setAlarmTime(context, (SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY)
                    , "activity.wch.alarm", AlarmManager.INTERVAL_DAY);
        }

        //当系统到我们设定的时间点的时候会发送广播，执行这里
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("签到提示");
        dialogBuilder.setMessage("您今天签到了吗？如果还没有的话快去签到吧。");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("我知道了", null);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        try {
            alertDialog.show();
        } catch (Exception e) {

        }

    }
}