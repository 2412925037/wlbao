package com.renhuikeji.wanlb.wanlibao.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.LoginCodeBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.jpush.JpushUtil;
import com.renhuikeji.wanlb.wanlibao.utils.CodeUtils;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.StringUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.widget.LoadingDialog;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class RegisterActivity extends BaseActivity {


    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_yzm)
    EditText et_yzm;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_password_again)
    EditText et_password_again;
    @BindView(R.id.inputcode)
    EditText inputcode;

    @BindView(R.id.checkbox_register)
    CheckBox checkbox_register;
    @BindView(R.id.btn_register)
    TextView btn_register;
    @BindView(R.id.img_register_back)
    ImageView imgRegisterBack;
    @BindView(R.id.tv_get_yzm)
    TextView tvGetYzm;
    private CodeUtils codeUtils;
    public int res_yzm;
    public LoadingDialog loadingDialog;
    private String baseUrl;
    private String apikey;
    private String uid;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        context = this;

        baseUrl = ConfigValue.APP_IP;
        apikey = ConfigValue.API_KEY;
        uid = (String) SPUtils.get(this,Constant.User_Uid,"");
        initView();
    }

    private void initView() {
        loadingDialog = new LoadingDialog(this, R.style.WhiteDialog);
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
    }

    String phone;

    private void checkPersonalData() {
        phone = et_phone.getText().toString().trim();
        if (!StringUtil.phoneCheck(phone)) {
            showToast("请输入正确的手机号码");
            return;
        }

        String yzm = et_yzm.getText().toString().trim();
        if (StringUtil.isEmpty(yzm)) {
            showToast("请输入短信验证码");
            return;
        }

        String password = et_password.getText().toString();
        if (!StringUtil.passwordCheck(password)) {
            showToast("密码为6-20位大小写字母、符号或数字");
            return;
        }

        String password_again = et_password_again.getText().toString().trim();
        if (StringUtil.isEmpty(password_again)) {
            showToast("请再次输入密码");
            return;
        } else if (!password_again.equals(password)) {
            showToast("两次密码不相同");
            return;
        }

        boolean checked = checkbox_register.isChecked();

        if (!checked) {
            showToast("请阅读并同意《用户服务协议》");
            return;
        }
        requestRegister(phone, password, yzm);
    }


    @OnClick({R.id.img_register_back, R.id.btn_register, R.id.tv_get_yzm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_register_back:
                finish();
                break;
            case R.id.btn_register:
                checkPersonalData();
                break;
            case R.id.tv_get_yzm:
                getYzm();
                break;
        }
    }

    /**
     * 验证码
     */
    String session;

    private void getYzm() {
        phone = et_phone.getText().toString().trim();
        if (!StringUtil.phoneCheck(phone)) {
            showToast("请输入正确的手机号码");
            return;
        }

        tvGetYzm.setClickable(false);
        index = 120;//60秒


        if (NetworkManageUtil.isNetworkAvailable(this)) {
            ToastUtils.toastForShort(this, "正在发送...");
        }
        String url = baseUrl + "?api=yasbao.api.user.getcode&uid="+uid+"&apiKey=" + apikey + "&mobile=" + phone + "&type=1";
        OkHttpUtils.getInstance().getYzmJson(url, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                //                {"result":"SUCESS","infocode":311910,"mobile":"15136198901","infotime":1495095271}@PHPSESSID=u6b5029a123np5kub907v3ej92
                if (TextUtils.isEmpty(data)) {
                    tvGetYzm.setClickable(true);
                    ToastUtils.toastForShort(RegisterActivity.this, "请求数据有问题!");
                    return;
                }
                String[] str = data.split("@");
                if (str.length > 1) {
                    LoginCodeBean res = new Gson().fromJson(str[0], LoginCodeBean.class);
                    session = str[1];
                    if (TextUtils.equals("SUCESS", res.getResult())) {
                        changeBtnGetCode();
                        res_yzm = res.getInfocode();
                        ToastUtils.toastForLong(RegisterActivity.this, "发送成功");
                    } else {
                        tvGetYzm.setClickable(true);
                        ToastUtils.toastForLong(RegisterActivity.this, res.getWorngMsg());
                    }
                }else{
                    tvGetYzm.setClickable(true);
                }

            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                tvGetYzm.setClickable(true);
                ToastUtils.toastForLong(RegisterActivity.this, "请求失败!");
            }
        });
    }

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param yzm
     */
    private void requestRegister(String phone, String password, String yzm) {
        int post_yzm = Integer.parseInt(yzm);
        if (res_yzm != post_yzm) {
            ToastUtils.toastForShort(this, "验证码不正确!");
            return;
        }
        requestRegist(phone, password, post_yzm);
    }

    private void requestRegist(final String phone, final String password, int post_yzm) {
        DialogUtils.showProgressDlg(RegisterActivity.this, getString(R.string.loading));

        String url = baseUrl + "?api=yasbao.api.user.regist&uid=&apiKey=" + apikey + "&username=" + phone + "&password=" + password + "&yan=" + post_yzm;
        String code = inputcode.getText().toString();
        //推荐码为6位数
        if (!TextUtils.isEmpty(code) && code.length() == 6) {
            url = baseUrl + "?api=yasbao.api.user.regist&uid="+uid+"&apiKey=" + apikey + "&username=" + phone + "&password=" + password + "&yan=" + post_yzm + "&recCode=" + code;
        }

        new OkHttpUtils().getDatas(this, url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                DialogUtils.stopProgressDlg();
                if (TextUtils.isEmpty(data)) {
                    ToastUtils.toastForShort(RegisterActivity.this, "请求数据有问题!");
                    return;
                }
                LoginCodeBean res = new Gson().fromJson(data, LoginCodeBean.class);
                if (TextUtils.equals(res.getResult(), "REGIST_SUCESS")) {
                    uid = res.getUid();
                    SPUtils.put(RegisterActivity.this, Constant.User_Uid, res.getUid().trim());
                    SPUtils.put(RegisterActivity.this, Constant.User_Phone, phone);
                    SPUtils.put(RegisterActivity.this, Constant.User_Psw, password);
                    SPUtils.put(RegisterActivity.this, Constant.IsLogin, true);


                    ToastUtils.toastForShort(RegisterActivity.this, "注册成功!");
                    //极光添加别名
                    setAlias(res.getUid());
                    RegisterActivity.this.finish();
                } else {
                    ToastUtils.toastForLong(RegisterActivity.this, res.getWorngMsg());
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                DialogUtils.stopProgressDlg();
            }
        });
    }


    private void setAlias(String name) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.toastForShort(RegisterActivity.this, "别名不能为空");
            return;
        }
        if (!JpushUtil.isValidTagAndAlias(name)) { //检查格式
            ToastUtils.toastForShort(RegisterActivity.this, "别名格式错误");
            return;
        }

        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, name));
    }

    private static final int MSG_SET_ALIAS = 1001;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("wch", logs);
                    break;

                case 6002:
                    logs = "别名设置超时，60s后重试";
                    Log.i("wch", logs);
                    if (JpushUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i("wch", "无网络");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("wch", logs);
            }

            //            JpushUtil.showToast(logs, getApplicationContext());
        }

    };
    //------------------------------------------------------------

    private final Handler handler = new Handler(Looper.getMainLooper());
    private int index;

    private void changeBtnGetCode() {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        if (context == null) {
                            timer.cancel();
                            return;
                        }

                        index--;
                        if (index <= 0) {
                            tvGetYzm.setClickable(true);
                            tvGetYzm.setText("可操作");

                            timer.cancel();
                            return;
                        }

                        tvGetYzm.setText(index + "秒(不可操作)");
                    }
                });

            }
        };
        timer.schedule(timerTask, 100, 1000);
    }
}
