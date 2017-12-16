package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class ToastUtils {
    private static Toast mToast;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static String oldMsg;

    private  Toast toast;
    private LinearLayout toastView;

    /**
     * 修改原布局的Toast
     ToastUtils
     */
    public ToastUtils() {

    }
    /**
     * 完全自定义布局Toast
     * @param context
     * @param view
     */
    public ToastUtils(Context context, View view, int duration){
        toast=new Toast(context);
        toast.setView(view);
        toast.setDuration(duration);
    }
    /**
     * 设置Toast字体及背景颜色
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public ToastUtils setToastColor(int messageColor, int backgroundColor) {
        View view = toast.getView();
        if(view!=null){
            TextView message=((TextView) view.findViewById(android.R.id.message));
            message.setBackgroundColor(backgroundColor);
            message.setTextColor(messageColor);
        }
        return this;
    }

    /**
     * 长时间弹出消息提示
     *
     * @param context
     * @param msg
     */
    public static void toastForLong(Context context, String msg) {
        if (context == null)
            return;

//		try {
//			new SnackBar.Builder((Activity)context)
//					.withMessage(msg)
//					.withDuration(SnackBar.LONG_SNACK)
//					.show();
//		}catch (ClassCastException ex){
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            mToast.setGravity(Gravity.CENTER, 0, 0);
            if (!TextUtils.isEmpty(msg) && msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_LONG) {
                    mToast.show();
                }
            } else {
                if (TextUtils.isEmpty(msg)) {
                    msg = "";
                }
                oldMsg = msg;
                mToast.setText(msg);
                mToast.show();
            }
        }
        oneTime = twoTime;
//		}
    }

    /**
     * 短时间弹出消息提示
     *
     * @param context
     * @param msg
     */
    public static void toastForShort1(Context context, String msg) {
        if (context == null)
            return;
//		try {
//			SnackBar sBar = new SnackBar.Builder((Activity)context)
//					.withMessage(msg)
//					.withDuration(SnackBar.SHORT_SNACK)
//					.show();
//		}catch (ClassCastException ex){
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (!TextUtils.isEmpty(msg) && msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    mToast.show();
                }
            } else {
                if (TextUtils.isEmpty(msg)) {
                    msg = "";
                }
                oldMsg = msg;
                mToast.setText(msg);
                mToast.show();
            }
        }
        oneTime = twoTime;
        return;
//		}
    }

    public static void toastForShort(Context context, String msg) {
        if (context == null)
            return;

//		try {
//			new SnackBar.Builder((Activity)context)
//					.withMessage(msg)
//					.withDuration(SnackBar.LONG_SNACK)
//					.show();
//		}catch (ClassCastException ex){
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            mToast.setGravity(Gravity.CENTER, 0, 0);
            twoTime = System.currentTimeMillis();
            if (!TextUtils.isEmpty(msg) && msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_LONG) {
                    mToast.show();
                }
            } else {
                if (TextUtils.isEmpty(msg)) {
                    msg = "";
                }
                oldMsg = msg;
                mToast.setText(msg);
                mToast.show();
            }
        }
        oneTime = twoTime;
//		}
    }


}
