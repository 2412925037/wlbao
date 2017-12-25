package com.renhuikeji.wanlb.wanlibao.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.AlipayLoginCodeBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.config.Contants;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.StringUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideCircleTransform;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/14.
 * 微信绑定界面
 */

public class WechatBindActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.inputcode)
    EditText inputcode;
    @BindView(R.id.cimg_wechat_cash_icon)
    ImageView cimgWechatCashIcon;
    @BindView(R.id.et_wechat_cash_phone)
    TextView etWechatCashPhone;
    @BindView(R.id.tv_get_yzm)
    TextView tvGetYzm;

    private String uid;
    private String openid;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_wechat);
        ButterKnife.bind(this);
        context = this;

        uid = (String) SPUtils.get(this,Constant.User_Uid,"");
        access_code = getIntent().getStringExtra("access_code");
        openid = getIntent().getStringExtra("openid");

        String nickname = getIntent().getStringExtra("nickname");
        String avatar = getIntent().getStringExtra("avatar");
        etWechatCashPhone.setText(nickname);
        Glide.with(this).load(avatar).error(R.mipmap.icon_yh).transform(new GlideCircleTransform(this)).into(cimgWechatCashIcon);
    }

    @OnClick({R.id.tv_get_yzm, R.id.btn_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_yzm:

                getYzm();
                break;
            case R.id.btn_bind:

                checkPersonalData();

                break;
        }
    }

    String session;
    String res_yzm = "";

    //获取验证码
    private void getYzm() {
        String phone = etPhone.getText().toString().trim();
        if (!StringUtil.phoneCheck(phone)) {
            showToast("请输入正确的手机号码");
            return;
        }
        tvGetYzm.setClickable(false);
        index = 120;//60秒


        if (NetworkManageUtil.isNetworkAvailable(this)) {
            ToastUtils.toastForShort(this, "正在发送...");
        }

        String baseUrl = ConfigValue.APP_IP;
        String apikey = ConfigValue.API_KEY;
        String url = baseUrl + "?api=yasbao.api.user.getcode&uid="+uid+"&apiKey=" + apikey + "&mobile=" + phone+"&type=4";
        OkHttpUtils.getInstance().getYzmJson(url, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {

/*{
    "result": "SUCESS",
    "infocode": "205697",
    "yan_code": "205697",
    "mobile": "18356058024",
    "infotime": 1513235408
}@PHPSESSID=66voka4o45hv6kb3jjah7k78o1*/
                if (TextUtils.isEmpty(data)) {
                    tvGetYzm.setClickable(true);
                    ToastUtils.toastForShort(WechatBindActivity.this, "请求数据有问题!");
                    return;
                }
                String[] str = data.split("@");
                if (str.length > 1) {

                    AlipayLoginCodeBean res = new Gson().fromJson(str[0], AlipayLoginCodeBean.class);
                    session = str[1];
                    if (TextUtils.equals("SUCESS", res.getResult())) {
                        changeBtnGetCode();
                        res_yzm = res.getInfocode();
                        ToastUtils.toastForLong(WechatBindActivity.this, "发送成功");
                    } else {
                        tvGetYzm.setClickable(true);
                        ToastUtils.toastForLong(WechatBindActivity.this, res.getWorngMsg());
                    }
                }else{
                    tvGetYzm.setClickable(true);
                }

            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                tvGetYzm.setClickable(true);
                ToastUtils.toastForLong(WechatBindActivity.this, "请求失败!");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.i("tag","返回键");
        getApp().finishAllActivity();
    }

    public void showToast(String string) {
        ToastUtils.toastForShort(WechatBindActivity.this, string);
    }

    private void checkPersonalData() {
        String phone = etPhone.getText().toString().trim();
        if (!StringUtil.phoneCheck(phone)) {
            showToast("请输入正确的手机号码");
            return;
        }

        String yzm = etYzm.getText().toString().trim();
        if (StringUtil.isEmpty(yzm)) {
            showToast("请输入短信验证码");
            return;
        }

        requestRegister(phone, yzm);
    }

    private void requestRegister(String phone, String yzm) {
        if (!TextUtils.equals(res_yzm, yzm)) {
            ToastUtils.toastForShort(this, "验证码不正确!");
            return;
        }
        requestRegist(phone, yzm);
    }

    private String access_code;

    private void requestRegist(final String phone, String yzm) {
        //DialogUtils.showProgressDlg(AlipayBindActivity.this, getString(R.string.loading));

        String url = Contants.WECHAT_BIND + "&mobile=" + phone + "&access_token=" + access_code + "&yan=" + yzm + "&openid=" + openid;

        String code = inputcode.getText().toString();
        //推荐码为6位数
        if (!TextUtils.isEmpty(code) && code.length() == 6) {
            url = Contants.ALIPAY_BIND + "&mobile=" + phone + "&access_token=" + access_code + "&yan=" + yzm + "&recCode=" + code + "&openid=" + openid;
        }

        final String finalUrl = url;

        OkHttpUtils.getInstance().getJson(url, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                Log.i("tag", "url" + finalUrl + " result:" + data);
/*{
    "result": "BIND_SUCESS",
    "uid": "75986",
    "worngMsg": "绑定成功！"
}*/
                try {
                    JSONObject object = new JSONObject(data);
                    String uid = object.getString("uid");
                    String result = object.getString("result");
                    String mobile = object.getString("username");
                    String password = object.getString("password");

                    if (TextUtils.equals("BIND_SUCESS", result)) {
                        ToastUtil.getInstance().showToast("绑定成功");

                        SPUtils.put(WechatBindActivity.this, Constant.User_Uid, uid);
                        SPUtils.put(WechatBindActivity.this, Constant.User_Phone, mobile);
                        SPUtils.put(WechatBindActivity.this, Constant.User_Psw, password);

                        Intent i = new Intent(WechatBindActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }else{

                        ToastUtil.getInstance().showToast(object.getString("worngMsg"));
//                        if(object.getString("msg")!=null){
//                            ToastUtil.getInstance().showToast(object.getString("msg"));
//                        }else{
//                            ToastUtil.getInstance().showToast(object.getString("worngMsg"));
//                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String meg) {
                //super.onError(meg);
                ToastUtil.getInstance().showToast("绑定失败");
                Log.i("tag", "url:" + finalUrl + " result:" + meg);
            }
        });

    }

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
