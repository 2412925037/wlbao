package com.renhuikeji.wanlb.wanlibao.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.AlipayBindActivity;
import com.renhuikeji.wanlb.wanlibao.activity.LoginActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MainActivity;
import com.renhuikeji.wanlb.wanlibao.alipay.AuthResult;
import com.renhuikeji.wanlb.wanlibao.alipay.OrderInfoUtil2_0;
import com.renhuikeji.wanlb.wanlibao.bean.AlipayLoginBean;
import com.renhuikeji.wanlb.wanlibao.bean.MemberInfoBean;
import com.renhuikeji.wanlb.wanlibao.bean.WeChatCrashBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.config.Contants;
import com.renhuikeji.wanlb.wanlibao.utils.ButtonUtils;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.KeyBoardUtils;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;
import com.renhuikeji.wanlb.wanlibao.views.CircleImageView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 转账提现
 */
public class WithDrawFragment extends Fragment {

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

    private MainActivity context ;
    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_withdraw,container,false);
        context = (MainActivity) getActivity();
        ButterKnife.bind(this,view);

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.relativeLayout1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            layout.setPadding(0, context.getStatusHeight(), 0, 0);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        dialog = DialogUtils.getMaterialDialogOneBtn(context, "首次提现请先关注万利宝微信公众号(yasbao)," +
                                "然后在公众号底部菜单:会员中心-》转账提现，以后就可以在这里直接提现啦！");
                        dialog.show();
                        dialog.setOnBtnClickL(new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                dialog.dismiss();
                            }
                        });
                        break;
                    case 3:
                        getUserInfo();
                        break;
                }
            }
        };

        suid = (String) SPUtils.get(context, Constant.User_Uid, "");
        gson = new Gson();
        imageloader = new GlideImageLoader(context);
        //先获取用户信息 （money openid等）
        getUserInfo();

        return view;

    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        String url = ConfigValue.MEMBER_INFO + "&uid=" + suid + "&fields=id,money,nick,vip,salesman,headimgurl,subscribe_yasbao,openid_yasbao,level,mobile";
        String msession = (String) SPUtils.get(context, Constant.MSESSION, "");
        new OkHttpUtils().getDatas(context,url, msession, new OkHttpUtils.HttpCallBack() {
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
                    ToastUtils.toastForLong(context, "用户信息获取失败");
                }


            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                context.finish();
                ToastUtils.toastForShort(context, getResources().getString(R.string.bad_net));
            }
        });
    }


    @OnClick({R.id.img_wechat_cash_back, R.id.tv_wechat_cash_confirm,R.id.tv_alipay_cash_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_wechat_cash_back:
                context.finish();
                break;
            case R.id.tv_wechat_cash_confirm:
                if (checkInfo()) {
                    String msession = (String) SPUtils.get(context, Constant.MSESSION, "");
                    if (!TextUtils.isEmpty(msession)) {
                        beginTransfer(msession);
                    }
                }
                break;
            case R.id.tv_alipay_cash_confirm:


                    String msession = (String) SPUtils.get(context, Constant.MSESSION, "");
                    if (!TextUtils.isEmpty(msession)) {
                        beginWithDraw(msession);
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
            ToastUtils.toastForShort(context, "请先关注万利宝微信公众号（yasbao）");
            return false;
        }




        if (!TextUtils.isEmpty(money_tixian) && Float.parseFloat(money_tixian) < 1.00) {
            ToastUtils.toastForShort(context, "转账金额不能小于1元");
            edit_cashMoney.setText("");
            return false;
        }

        return true;
    }

    /**
     * 微信登录
     */
    private void setWXLogin() {
        if (App.api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wlbao_login";
            App.api.sendReq(req);
            context.finish();
        } else
            Toast.makeText(context, "用户未安装微信", Toast.LENGTH_SHORT).show();
    }

    /**
     * 开始转账
     */
    private void beginTransfer(String msession) {
        //关闭软键盘
        KeyBoardUtils.closeKeybord(edit_cashMoney, context);
        DialogUtils.showProgressDlg(context, "正在提现，请勿操作");
        String money = edit_cashMoney.getText().toString().trim();
        final String str_url = ConfigValue.WECHAT_TRANSFER + "&uid=" + suid + "&money=" + money;
        new OkHttpUtils().getDatas(context,str_url, msession, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                DialogUtils.stopProgressDlg();
                WeChatCrashBean bean = gson.fromJson(data, WeChatCrashBean.class);

                if(TextUtils.equals("NOBIND",bean.getResult())){

                    if (!ButtonUtils.isFastDoubleClick()) {
                        setWXLogin();
                    }
                    return;
                }

                if (TextUtils.equals("WRONG", bean.getResult())) {
                    ToastUtils.toastForShort(context, bean.getWorngMsg());
                } else {
                    ToastUtils.toastForShort(context, "提现成功");
                    //发送广播提示修改账户余额
                    Intent intent = new Intent("refreshmoney");
                    intent.putExtra("result", "1");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

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
                ToastUtils.toastForShort(context, getResources().getString(R.string.bad_net));
            }
        });
    }
    //----------------------------------------------------


    @SuppressLint("HandlerLeak")
    private Handler mHandler2 = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SDK_AUTH_FLAG:

                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    final String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户

                        String authcode = authResult.getAuthCode();
                        Log.i("logauthcode", authcode);

                        OkHttpUtils.getInstance().getYzmJson(Contants.ALIPAY_LOGIN + "&auth_code=" + authcode, new OkHttpUtils.HttpCallBack() {
                            @Override
                            public void onSusscess(String data) {

                                Log.i("tag",OkHttpUtils.decodeUnicode(data));
                                String[] str = data.split("@");
                                if (str.length > 1) {
                                    AlipayLoginBean bean = new Gson().fromJson(str[0], AlipayLoginBean.class);
                                    String session = str[1];
                                    SPUtils.put(context, Constant.MSESSION, session);

                                    Log.i("tag", "user" + bean.getUid()+"--"+session);

                                    if (TextUtils.equals("NOBIND", bean.getResult())) {
                                        //ToastUtil.getInstance().showToast(OkHttpUtils.decodeUnicode(bean.getWorngMsg()));
                                        startActivity(new Intent(context, AlipayBindActivity.class).putExtra("access_code", bean.getAccess_token()).putExtra("uid", bean.getUser_id()));

                                    } else if (TextUtils.equals("LOGIN_SUCESS", bean.getResult())) {

                                        SPUtils.put(context, Constant.MSESSION, session);
                                        SPUtils.put(context, Constant.User_Uid, bean.getUid().trim());
                                        SPUtils.put(context, Constant.User_Phone, bean.getUsername());
                                        SPUtils.put(context, Constant.User_Psw, bean.getPassword());
                                        Intent i = new Intent(context, MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        context.finish();
                                    } else {
                                        ToastUtils.toastForShort(context, bean.getWorngMsg());
                                    }
                                }

                            }

                            @Override
                            public void onError(String meg) {
                                super.onError(meg);
                                ToastUtils.toastForShort(context, "登录出错");
                            }
                        });

                        //                        Toast.makeText(LoginActivity.this,
                        //                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                        //                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context, "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;

                default:
                    break;
            }
        }

    };

    private static final int SDK_AUTH_FLAG = 2;
    private void alipaylogin(){

        String uid = (String) SPUtils.get(context, Constant.User_Uid, "");
        OkHttpUtils.getInstance().getJson(Contants.AUTH_ALIPAY + "&uid=" + uid, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {

                Log.i("tag", OkHttpUtils.decodeUnicode(data));
                try {
                    JSONObject object = new JSONObject(data);
                    String sign = object.getString("sign");
                    String target_id = object.getString("target_id");

                    Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(Contants.PID, Contants.APPID, target_id, false);
                    String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

                    final String authInfo = info + "&" + sign;

                    Runnable authRunnable = new Runnable() {

                        @Override
                        public void run() {
                            // 构造AuthTask 对象
                            AuthTask authTask = new AuthTask(context);
                            // 调用授权接口，获取授权结果
                            Map<String, String> result = authTask.authV2(authInfo, true);

                            Message msg = new Message();
                            msg.what = SDK_AUTH_FLAG;
                            msg.obj = result;
                            mHandler2.sendMessage(msg);
                        }
                    };

                    // 必须异步调用
                    Thread authThread = new Thread(authRunnable);
                    authThread.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                Log.i("tag", OkHttpUtils.decodeUnicode(meg));
            }
        });


    }


    /**
     * 开始转账 - 支付宝
     */
    private void beginWithDraw(String msession) {
        //关闭软键盘
        KeyBoardUtils.closeKeybord(edit_cashMoney, context);
        DialogUtils.showProgressDlg(context, "正在提现，请勿操作");
        String money = edit_cashMoney.getText().toString().trim();
        final String str_url = Contants.ALIPAY_WITHDRAW + "&uid=" + suid + "&money=" + money;
        OkHttpUtils.getInstance().getDatas(context,str_url, msession, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {

                DialogUtils.stopProgressDlg();
                WeChatCrashBean bean = gson.fromJson(data, WeChatCrashBean.class);

                if(TextUtils.equals("NOBIND",bean.getResult())){

                    alipaylogin();
                    return;
                }

                if (TextUtils.equals("WRONG", bean.getResult())) {
                    ToastUtils.toastForShort(context, bean.getWorngMsg());
                } else {
                    ToastUtils.toastForShort(context, "提现成功");
                    //发送广播提示修改账户余额
                    Intent intent = new Intent("refreshmoney");
                    intent.putExtra("result", "1");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

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
                ToastUtils.toastForShort(context, getResources().getString(R.string.bad_net));
            }
        });
    }
}
