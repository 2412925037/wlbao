package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.renhuikeji.wanlb.wanlibao.activity.CashFlowActivity;
import com.renhuikeji.wanlb.wanlibao.activity.FinancialDetailActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MainActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MyFriendActivity;
import com.renhuikeji.wanlb.wanlibao.activity.SettledBillsActivity;
import com.renhuikeji.wanlb.wanlibao.activity.SignInActivity;
import com.renhuikeji.wanlb.wanlibao.activity.WebShowActivity;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;

/**
 * Created by Administrator on 2017/6/16.
 */

public class JumpMsgUtil {

    static MsgDbHelper helper;
    public static void jumpTo(Context context,String type,int msgID,String receid){
        if(helper==null){
            helper=new MsgDbHelper(context);
        }
        Intent i=new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setClass(context,MainActivity.class);
        if(!TextUtils.isEmpty(type)){
            switch (type){
                case Constant.JUMP_ACCOUNT:
                    i.setClass(context, CashFlowActivity.class);
                    context.startActivity(i);
                    break;
                case Constant.JUMP_ORDER:
                    i.setClass(context, SettledBillsActivity.class);
                    context.startActivity(i);
                    break;
                case Constant.JUMP_FRIEND:
                    i.setClass(context, MyFriendActivity.class);
                    context.startActivity(i);
                    break;
                case Constant.JUMP_RECOM:
                    i.putExtra("url", ConfigValue.INVITE_FRIENDS);
                    i.putExtra("title", "邀请好友");
                    i.putExtra("right_text", "");
                    i.setClass(context, WebShowActivity.class);
                    context.startActivity(i);
                    break;
                case Constant.JUMP_CASH:
                    i.setClass(context, FinancialDetailActivity.class);
                    context.startActivity(i);
                    break;
                case Constant.JUMP_SIGN:
                    i.setClass(context, SignInActivity.class);
                    context.startActivity(i);
                    break;
                case Constant.JUMP_SESSION:
                    if (CheckUtil.isLogin(context)) {
                        HuanXinUtils.getInstance().openHuanXin(context);
//                        helper.deteMsgByKefu();
                    } else {
                        ToastUtils.toastForLong(context, "请先登录!");
                    }
                    break;
            }
        }else{
            context.startActivity(i);
        }
//        helper.deteMsg(msgID);
//        helper.deleteByRece(receid);
    }
}
