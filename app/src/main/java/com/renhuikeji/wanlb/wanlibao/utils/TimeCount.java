package com.renhuikeji.wanlb.wanlibao.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class TimeCount extends CountDownTimer {

    private TextView textView;

    //    //参数依次为总时长,和计时的时间间隔
    public TimeCount(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    //计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
        String time = millisUntilFinished / 1000 + "秒";
        setButtonInfo(time, false);
    }

    //计时完毕时触发
    @Override
    public void onFinish() {
        setButtonInfo("重新获取", true);
    }

    /**
     * 验证按钮在点击前后相关设置
     *
     * @param content 要显示的内容
     * @param isClick 是否可点击
     */
    private void setButtonInfo(String content, boolean isClick) {
        textView.setText(content);
        textView.setClickable(isClick);
        textView.setTextColor(Color.parseColor("#FF4323"));
        textView.setTextSize(14);
        textView.setGravity(Gravity.CENTER);
    }

//┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃ 　
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//┃　　　┃   神兽保佑　　　　　　　　
//┃　　　┃   代码无BUG！
//┃　　　┗━━━┓
//┃　　　　　　　┣┓____________
//┃　　　　　　　┏┛
//┗┓┓┏━┳┓┏┛
//  ┃┫┫　┃┫┫
//  ┗┻┛　┗┻┛

    //--------------------------------------------------------
}
