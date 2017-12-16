package com.renhuikeji.wanlb.wanlibao.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.renhuikeji.wanlb.wanlibao.receiver.AlarmReceiver;

/**
 * 定时器类
 */

public class TimeRemind {
    public static void setAlarmTime(Context context, long timeInMillis, String action, long time) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(action);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //6.0
            am.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeInMillis, sender);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //参数2是开始时间、参数3是允许系统延迟的时间
            //am.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeInMillis, time, sender);
            am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeInMillis, sender);
        } else {
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeInMillis, time, sender);
        }
    }

    public static void canalAlarm(Context context, String action) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(action);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }
}
