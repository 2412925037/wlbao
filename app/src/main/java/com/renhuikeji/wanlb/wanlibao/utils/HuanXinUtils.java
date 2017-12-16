package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.renhuikeji.wanlb.wanlibao.R;

import static com.renhuikeji.wanlb.wanlibao.utils.SPUtils.get;

/**
 * Created by Administrator on 2017/6/12.
 */

public class HuanXinUtils {
    private Context context;
    private static HuanXinUtils instance=new HuanXinUtils();

    public static HuanXinUtils getInstance(){
        return instance;
    }

    public  void openHuanXin(Context context) {
        this.context=context;
        String name = (String) get(context, Constant.User_Phone, "");
        String psw="qqq111";
        boolean b = (boolean) get(context, Constant.IS_FIRST_HX, true);
        String str = context.getString(R.string.loading);
        if (b) {
            str = "加载客服,正在初始化...";
        }
        DialogUtils.showProgressDlg(context, str);
        ChatClient.getInstance().setDebugMode(true);
        registeHx(name,psw);
    }

    private void registeHx(final String name , final String psw) {
        ChatClient.getInstance().createAccount(name, psw, new Callback() {
            @Override
            public void onSuccess() {
                isLogin(name,psw);
            }

            @Override
            public void onError(int i, String s) {
                if (i == 203) {
                    isLogin(name,psw);
                } else {
                    //ToastUtils.toastForShort(context, s);
                    ToastUtil.getInstance().showToast(s);
                    DialogUtils.stopProgressDlg();
                }
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }



    private void loginHx(String name, String psw) {
        ChatClient.getInstance().login(name, psw, new Callback() {
            @Override
            public void onSuccess() {
                toChat();
            }

            @Override
            public void onError(int i, String s) {
                DialogUtils.stopProgressDlg();
                ToastUtils.toastForShort(context, s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    private void isLogin(String name, String psw) {
        if (ChatClient.getInstance().isLoggedInBefore()) {
            //已经登录，可以直接进入会话界面
            toChat();
        } else {
            //未登录，需要登录后，再进入会话界面
            loginHx(name,psw);
        }
    }


    private void toChat() {
        SPUtils.put(context, Constant.IS_FIRST_HX, false);
        DialogUtils.stopProgressDlg();
        Intent intent = new IntentBuilder(context)
                .setServiceIMNumber("kefuchannelimid_887434") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                .build();
        context.startActivity(intent);
    }
}
