package com.renhuikeji.wanlb.wanlibao.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.alipay.AuthResult;
import com.renhuikeji.wanlb.wanlibao.alipay.OrderInfoUtil2_0;
import com.renhuikeji.wanlb.wanlibao.bean.AlipayLoginBean;
import com.renhuikeji.wanlb.wanlibao.bean.LoginCodeBean;
import com.renhuikeji.wanlb.wanlibao.config.Contants;
import com.renhuikeji.wanlb.wanlibao.utils.ButtonUtils;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.KeyBoardUtils;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.StringUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone_login)
    EditText et_phone_login;
    @BindView(R.id.et_password_login)
    EditText et_password_login;
    @BindView(R.id.iv_show_pd)
    ImageView iv_show_pd;
    @BindView(R.id.btn_login)
    TextView btn_login;

    public boolean showPd = false;
    @BindView(R.id.tv_to_register_login)
    TextView tv_to_register_login;
    @BindView(R.id.img_login_back)
    ImageView imgLoginBack;
    @BindView(R.id.tv_forget_psw)
    TextView tv_forgetpsw;
    private String session;
    private String old_session;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        old_session = (String) SPUtils.get(LoginActivity.this, Constant.MSESSION, "");
        uid = (String) SPUtils.get(this, Constant.User_Uid, "");

    }

    public void showToast(String string) {
        ToastUtils.toastForShort(this,string);
    }

    private void booleanLogin() {
        String phoneLogin = et_phone_login.getText().toString().trim();
        if (TextUtils.isEmpty(phoneLogin)) {
            showToast("请输入正确的用户名");
            return;
        }

        String passwordLogin = et_password_login.getText().toString().trim();
        if (StringUtil.isEmpty(passwordLogin)) {
            showToast("请输入密码");
            return;
        }
        if (!StringUtil.passwordCheck(passwordLogin)) {
            showToast("密码格式不对");
            return;
        }

        //关闭软键盘
        KeyBoardUtils.closeKeybord(et_password_login, LoginActivity.this);
        login(phoneLogin, passwordLogin);
    }

    private void login(final String name, final String psw) {
        // DialogUtils.showProgressDlg(LoginActivity.this, getString(R.string.logining));
        String url = "http://app.yasbao.com/Home/Api/gw?api=yasbao.api.user.login&uid="+ uid +"&apiKey=f40b1da52dece67017dbb0c7830e586e&username=" + name + "&password=" + psw;
        new OkHttpUtils().getYzmJson(url, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                // DialogUtils.stopProgressDlg();

                Log.i("tag",data);
                String[] str = data.split("@");
                if (str.length > 1) {
                    LoginCodeBean res = new Gson().fromJson(str[0], LoginCodeBean.class);
                    session = str[1];
                    SPUtils.put(LoginActivity.this, Constant.MSESSION, session);
                    if (TextUtils.equals("LOGIN_SUCESS", res.getResult())) {
                        SPUtils.put(LoginActivity.this, Constant.User_Uid, res.getUid().trim());
                        SPUtils.put(LoginActivity.this, Constant.User_Phone, name);
                        SPUtils.put(LoginActivity.this, Constant.User_Psw, psw);
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    } else {
                        ToastUtils.toastForShort(LoginActivity.this, res.getWorngMsg());
                    }
                }

            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                // DialogUtils.stopProgressDlg();
                ToastUtils.toastForShort(LoginActivity.this, "登录出错");
            }
        });
    }

    //    显示或隐藏密码
    private void isShowPd() {

        if (showPd) {
            et_password_login.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPd = false;
        } else {
            et_password_login.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPd = true;
        }
    }

    @OnClick({R.id.img_login_back, R.id.iv_show_pd, R.id.btn_login, R.id.tv_to_register_login, R.id.tv_forget_psw, R.id.alipay, R.id.wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_login_back:
                LoginActivity.this.finish();
                break;
            case R.id.iv_show_pd:
                isShowPd();
                break;
            case R.id.btn_login:
                booleanLogin();
                break;
            case R.id.tv_to_register_login:  //注册
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forget_psw:  //忘记密码
                Intent intent1 = new Intent(LoginActivity.this, ResetPassWordActivity.class);
                startActivity(intent1);
                break;

            case R.id.alipay:


                OkHttpUtils.getInstance().getJson(Contants.AUTH_ALIPAY + "&uid=" + uid, new OkHttpUtils.HttpCallBack() {
                    @Override
                    public void onSusscess(String data) {

                        Log.i("tag", OkHttpUtils.decodeUnicode(data));
                        try {
                            JSONObject object = new JSONObject(data);
                            String sign = object.getString("signStr");
                            String target_id = object.getString("target_id");

                            Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(Contants.PID, Contants.APPID, target_id, false);
                            String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

                            final String authInfo = info + "&" + sign;

                            Runnable authRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    // 构造AuthTask 对象
                                    AuthTask authTask = new AuthTask(LoginActivity.this);
                                    // 调用授权接口，获取授权结果
                                    Map<String, String> result = authTask.authV2(authInfo, true);

                                    Message msg = new Message();
                                    msg.what = SDK_AUTH_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
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
                break;
            case R.id.wechat:

                if (!ButtonUtils.isFastDoubleClick()) {
                    setWXLogin();
                }
                break;
        }
    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    String authcode;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
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

                            authcode = authResult.getAuthCode();
                            Log.i("logauthcode",authcode);

                            OkHttpUtils.getInstance().getJson(Contants.ALIPAY_LOGIN + "&auth_code=" + authcode, new OkHttpUtils.HttpCallBack() {
                                @Override
                                public void onSusscess(String data) {
                                    // DialogUtils.stopProgressDlg();
                                    Log.i("taghandler", OkHttpUtils.decodeUnicode(data));

                                    AlipayLoginBean bean = new Gson().fromJson(data, AlipayLoginBean.class);
                                    Log.i("tag","user"+bean.getUid());
                                    Log.i("tag","session"+session);

//                                    String[] str = data.split("@");
//                                    //SPUtils.put(LoginActivity.this, Constant.MSESSION, session);
//                                    if (str.length > 1) {
//                                        AlipayLoginBean bean = new Gson().fromJson(data, AlipayLoginBean.class);
//                                        session = str[1];
//                                        SPUtils.put(LoginActivity.this, Constant.MSESSION, session);
//
//                                        Log.i("tag","user"+bean.getUid());
//                                        Log.i("tag","session"+session);
//                                        if (TextUtils.equals("NOBIND", bean.getResult())) {
//                                            //ToastUtil.getInstance().showToast(OkHttpUtils.decodeUnicode(bean.getWorngMsg()));
//                                            startActivityForResult(new Intent(LoginActivity.this, AlipayBindActivity.class).putExtra("access_code", bean.getAccess_token()).putExtra("uid", bean.getUser_id()), 101);
//
//                                        } else if (TextUtils.equals("LOGIN_SUCESS", bean.getResult())) {
//
//                                            Log.i("tag","user"+bean.getUid());
//                                            Log.i("tag","session"+session);
//
////                                            Log.i("tag1","startactivity");
////                                            SPUtils.put(LoginActivity.this, Constant.MSESSION, session);
////                                            SPUtils.put(LoginActivity.this, Constant.User_Uid, bean.getUid().trim());
////                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
////                                            //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////                                            startActivity(i);
////                                            finish();
//                                        } else {
//                                            ToastUtils.toastForShort(LoginActivity.this, bean.getWorngMsg());
//                                        }
                                    }

                                //}

                                @Override
                                public void onError(String meg) {
                                    super.onError(meg);
                                    // DialogUtils.stopProgressDlg();
                                    Log.i("tagerr", OkHttpUtils.decodeUnicode(meg));
                                    ToastUtils.toastForShort(LoginActivity.this, "登录出错");
                                }
                            });

                                                    Toast.makeText(LoginActivity.this,
                                                            "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                                            .show();
                        } else {
                            // 其他状态值则为授权失败
                            Toast.makeText(LoginActivity.this,
                                    "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                        }
                        break;

                    default:
                        break;
                }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == 101 && resultCode == RESULT_OK) {
//
//            if (authcode == null) {
//                ToastUtil.getInstance().showToast("xxx");
//                return;
//            }
//
//
//            new OkHttpUtils().getYzmJson(Contants.ALIPAY_LOGIN + "&auth_code=" + authcode, new OkHttpUtils.HttpCallBack() {
//                @Override
//                public void onSusscess(String data) {
//                    // DialogUtils.stopProgressDlg();
//                    Log.i("tag", OkHttpUtils.decodeUnicode(data));
//                    String[] str = data.split("@");
//                    if (str.length > 1) {
//                        AlipayLoginBean bean = new Gson().fromJson(data, AlipayLoginBean.class);
//                        session = str[1];
//                        //SPUtils.put(LoginActivity.this, Constant.MSESSION, session);
//                        if (TextUtils.equals("NOBIND", bean.getResult())) {
//                            //ToastUtil.getInstance().showToast(OkHttpUtils.decodeUnicode(bean.getWorngMsg()));
//                            startActivityForResult(new Intent(LoginActivity.this, AlipayBindActivity.class).putExtra("access_code", bean.getAccess_token()).putExtra("uid", bean.getUser_id()), 101);
//
//                        } else if (TextUtils.equals("LOGIN_SUCESS", bean.getResult())) {
//
//                            Log.i("tag","user"+bean.getUid());
//                            Log.i("tag","session"+session);
//
//                            Log.i("tag1","startactivity");
//                            //                                            SPUtils.put(LoginActivity.this, Constant.MSESSION, session);
//                            //                                            SPUtils.put(LoginActivity.this, Constant.User_Uid, bean.getUid().trim());
//                            //                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                            //                                            //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            //                                            startActivity(i);
//                            //                                            finish();
//                        } else {
//                            ToastUtils.toastForShort(LoginActivity.this, bean.getWorngMsg());
//                        }
//                    }
//
//                }
//
//                @Override
//                public void onError(String meg) {
//                    super.onError(meg);
//                    // DialogUtils.stopProgressDlg();
//                    Log.i("tagerr", OkHttpUtils.decodeUnicode(meg));
//                    ToastUtils.toastForShort(LoginActivity.this, "登录出错");
//                }
//            });
//
//
//            new OkHttpUtils().getJson(Contants.ALIPAY_LOGIN + "&auth_code=" + authcode, new OkHttpUtils.HttpCallBack() {
//                @Override
//                public void onSusscess(String data) {
//
//                    Log.i("taag", OkHttpUtils.decodeUnicode(data));
//
//                                /*{
//    "user_id": "2088712912254324",
//    "access_token": "kuaijieBad8d890952a74a41ba8ea3aeb5c2aE32",
//    "result": "NOBIND",
//    "worngMsg": "该用户没有绑定支付宝，请绑定！"
//}*/
//
//                    AlipayLoginBean bean = new Gson().fromJson(data, AlipayLoginBean.class);
//                    String result = bean.getResult();
//
//                    if (TextUtils.equals("NOBIND", result)) {
//                        ToastUtil.getInstance().showToast(OkHttpUtils.decodeUnicode(bean.getWorngMsg()));
//                        startActivityForResult(new Intent(LoginActivity.this, AlipayBindActivity.class).putExtra("access_code", bean.getAccess_token()).putExtra("uid", bean.getUser_id()), 101);
//
//                    } else if (TextUtils.equals("LOGIN_SUCESS", result)) {
//
//                        ToastUtil.getInstance().showToast("登录成功");
//                        SPUtils.put(LoginActivity.this, Constant.User_Uid, bean.getUid().trim());
//                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);
//
//                    } else {
//                        ToastUtils.toastForShort(LoginActivity.this, bean.getWorngMsg());
//                    }
//                }
//
//                @Override
//                public void onError(String meg) {
//                    super.onError(meg);
//                    Log.i("tag", OkHttpUtils.decodeUnicode(meg));
//                }
//            });
//
//        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //getApp().finishAllActivity();
    }

    private String APP_ID = "wxf75d50c809f568e7";
    private IWXAPI api;

    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
    }

    /**
     * 微信登录
     */
    private void setWXLogin() {
        regToWx();
        if (api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wlbao_login";
            api.sendReq(req);
            finish();
        } else
            Toast.makeText(LoginActivity.this, "用户未安装微信", Toast.LENGTH_SHORT).show();
    }

}