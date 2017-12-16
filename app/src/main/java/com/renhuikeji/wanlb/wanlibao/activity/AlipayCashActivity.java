package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.MemberInfoBean;
import com.renhuikeji.wanlb.wanlibao.bean.WeChatCrashBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.KeyBoardUtils;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;
import com.renhuikeji.wanlb.wanlibao.views.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 转账提现
 */
public class AlipayCashActivity extends BaseActivity {

    @BindView(R.id.img_wechat_cash_back)
    ImageView imgWechatCashBack;
    @BindView(R.id.tv_wechat_cash_title)
    TextView tvWechatCashTitle;
    @BindView(R.id.cimg_wechat_cash_icon)
    CircleImageView user_img;
    @BindView(R.id.et_wechat_cash_phone)
    TextView edit_userPhone;
    @BindView(R.id.et_wechat_cash_money)
    EditText edit_cashMoney;
    @BindView(R.id.tv_wechat_cash_money)
    TextView tv_money;
    @BindView(R.id.tv_wechat_cash_confirm)
    TextView btn_commit;

    private String openid_yasbao = "";
    private String suid = "";
    private Gson gson = null;
    private MaterialDialog dialog = null;
    private String user_money;
    private GlideImageLoader imageloader = null;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay_cash);
        ButterKnife.bind(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
//                        dialog = DialogUtils.getMaterialDialogOneBtn(AlipayCashActivity.this, "首次提现请先关注万利宝微信公众号(yasbao)," +
//                                "然后在公众号底部菜单:会员中心-》转账提现，以后就可以在这里直接提现啦！");
//                        dialog.show();
//                        dialog.setOnBtnClickL(new OnBtnClickL() {
//                            @Override
//                            public void onBtnClick() {
//                                dialog.dismiss();
//                            }
//                        });
                        break;
                    case 3:
                        getUserInfo();
                        break;
                }
            }
        };

        suid = (String) SPUtils.get(AlipayCashActivity.this, Constant.User_Uid, "");
        gson = new Gson();
        imageloader = new GlideImageLoader(AlipayCashActivity.this);
        //先获取用户信息 （money openid等）
        getUserInfo();


    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        String url = ConfigValue.MEMBER_INFO + "&uid=" + suid + "&fields=id,money,nick,vip,salesman,headimgurl,subscribe_yasbao,openid_yasbao,level,mobile";
        String msession = (String) SPUtils.get(AlipayCashActivity.this, Constant.MSESSION, "");
        new OkHttpUtils().getDatas(this,url, msession, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                MemberInfoBean bean = gson.fromJson(data, MemberInfoBean.class);
                if (TextUtils.equals("SUCESS", bean.getResult())) {
                    MemberInfoBean.UserinfoBean userinfoBean = bean.getUserinfo();
                    openid_yasbao = userinfoBean.getOpenid_yasbao();
                    Message message = mHandler.obtainMessage();
                    //如果openid为空了，弹出提示框
                    if (TextUtils.isEmpty(openid_yasbao)) {
                        message.what = 1;
                    }
                    mHandler.sendMessage(message);

                    user_money = userinfoBean.getMoney();
                    if (!TextUtils.isEmpty(user_money)) {
                        edit_userPhone.setText(userinfoBean.getMobile());
                        edit_cashMoney.setText(userinfoBean.getMoney());
                        tv_money.setText(userinfoBean.getMoney() + "元");
                    }
                    if (!TextUtils.isEmpty(userinfoBean.getHeadimgurl())) {
                        imageloader.display(user_img, userinfoBean.getHeadimgurl());
                    }
                } else {
                    ToastUtils.toastForLong(AlipayCashActivity.this, "用户信息获取失败");
                }


            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                AlipayCashActivity.this.finish();
                ToastUtils.toastForShort(AlipayCashActivity.this, getResources().getString(R.string.bad_net));
            }
        });
    }


    @OnClick({R.id.img_wechat_cash_back, R.id.tv_wechat_cash_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_wechat_cash_back:
                finish();
                break;
            case R.id.tv_wechat_cash_confirm:
                if (checkInfo()) {
                    String msession = (String) SPUtils.get(AlipayCashActivity.this, Constant.MSESSION, "");
                    if (!TextUtils.isEmpty(msession)) {
                        beginTransfer(msession);
                    }
                }
                break;
        }
    }


    /**
     * 检查信息
     */
    private boolean checkInfo() {

        String money_tixian = edit_cashMoney.getText().toString();

        if (TextUtils.isEmpty(openid_yasbao)) {
            ToastUtils.toastForShort(AlipayCashActivity.this, "请先关注万利宝微信公众号（yasbao）");
            return false;
        }

        if (!TextUtils.isEmpty(money_tixian) && Float.parseFloat(money_tixian) < 1.00) {
            ToastUtils.toastForShort(AlipayCashActivity.this, "转账金额不能小于1元");
            edit_cashMoney.setText("");
            return false;
        }

        return true;
    }

    /**
     * 开始转账
     */
    private void beginTransfer(final String msession) {
        //关闭软键盘
        KeyBoardUtils.closeKeybord(edit_cashMoney, AlipayCashActivity.this);
        DialogUtils.showProgressDlg(AlipayCashActivity.this, "正在提现，请勿操作");
        String money = edit_cashMoney.getText().toString().trim();
        final String str_url = ConfigValue.WECHAT_TRANSFER + "&uid=" + suid + "&money=" + money;
        new OkHttpUtils().getDatas(this,str_url, msession, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                DialogUtils.stopProgressDlg();
                WeChatCrashBean bean = gson.fromJson(data, WeChatCrashBean.class);
                if (TextUtils.equals("WRONG", bean.getResult())) {
                    ToastUtils.toastForShort(AlipayCashActivity.this, bean.getWorngMsg());
                } else {
                    ToastUtils.toastForShort(AlipayCashActivity.this, "提现成功");
                    //发送广播提示修改账户余额
                    Intent intent = new Intent("refreshmoney");
                    intent.putExtra("result", "1");
                    LocalBroadcastManager.getInstance(AlipayCashActivity.this).sendBroadcast(intent);

                    //提现成功后刷新一下余额显示
                    Message message = mHandler.obtainMessage();
                    message.what = 3;
                    mHandler.sendMessage(message);
                    // finish();
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                DialogUtils.stopProgressDlg();
                ToastUtils.toastForShort(AlipayCashActivity.this, getResources().getString(R.string.bad_net));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //----------------------------------------------------
}
