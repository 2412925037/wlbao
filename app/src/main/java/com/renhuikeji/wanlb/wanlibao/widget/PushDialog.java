package com.renhuikeji.wanlb.wanlibao.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.CashFlowActivity;
import com.renhuikeji.wanlb.wanlibao.activity.FinancialDetailActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MainActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MyFriendActivity;
import com.renhuikeji.wanlb.wanlibao.activity.SettledBillsActivity;
import com.renhuikeji.wanlb.wanlibao.activity.SignInActivity;
import com.renhuikeji.wanlb.wanlibao.activity.WebShowActivity;
import com.renhuikeji.wanlb.wanlibao.bean.Message;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.CheckUtil;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.HuanXinUtils;
import com.renhuikeji.wanlb.wanlibao.utils.MsgDbHelper;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

/**
 * Created by Administrator on 2017/6/9.
 */

public class PushDialog extends BaseDialog<PushDialog> {
    private Context context;
    private String content;
    private String type;
    private TextView contentTv,confirmTv;
    private  Intent i;
    private int msgId;
    private Message msg;
    private TextView titleTv;

    public PushDialog(final Context context, Message msg) {
        super(context);
        this.context=context;
        this.content=msg.getContent();
        this.type=msg.getJumpType();
        this.msgId=msg.getId();
    }

    private void initType() {
        i = new Intent();
        i.setClass(context, MainActivity.class);
        if (!TextUtils.isEmpty(type)) {
            switch (type) {
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
                        MsgDbHelper helper=new MsgDbHelper(context);
                        helper.deteMsgByKefu();
                    } else {
                        ToastUtils.toastForLong(context, "请先登录!");
                    }
                    break;
            }
        }
        deleteMsg(msgId);
        dismiss();
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
//        showAnim(new Swing());
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(context, R.layout.dialog_push, null);
        contentTv= (TextView) inflate.findViewById(R.id.lock_msg_tv);
        titleTv= (TextView) inflate.findViewById(R.id.msg_title_tv);
        if (TextUtils.equals(type, Constant.JUMP_SESSION)) {
            titleTv.setText("万利宝客服");
        }else{
            titleTv.setText("万利宝");

        }
        contentTv.setText(content);
        confirmTv= (TextView) inflate.findViewById(R.id.confirm);
       /* inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));*/

        return inflate;
    }

    public void deleteDialog(){
        if(!((Activity)context).isFinishing()){
            this.dismiss();
        }
    }

    @Override
    public void setUiBeforShow() {
        contentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initType();

            }
        });

        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMsg(msgId);
                dismiss();
            }
        });

    }
//    把未读信息更新为已读.
    private void updateMsg(int id){
        MsgDbHelper helper=new MsgDbHelper(context);
        int i=helper.updateMsg(id,1);
    }
//    把已读信息删除
    private void deleteMsg(int id){
        MsgDbHelper helper=new MsgDbHelper(context);
        helper.deteMsg(id);
    }

}
