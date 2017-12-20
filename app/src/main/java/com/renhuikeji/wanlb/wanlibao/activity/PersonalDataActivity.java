package com.renhuikeji.wanlb.wanlibao.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.LoginCodeBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.AliUtils.AliUtils;
import com.renhuikeji.wanlb.wanlibao.utils.CacheManager;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.VoidRepeatClickUtil;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;
import com.renhuikeji.wanlb.wanlibao.utils.glide.PhoneInfoUtil;
import com.renhuikeji.wanlb.wanlibao.views.CircleImageView;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Description:
 * 用户个人信息
 * Modification History:
 * Date       Author     Version
 * ---------------------------------------
 * 17.04.21  wuconghui   1.0.0
 * <p>
 * ----------------------------------------
 * Why & What is modified:
 * 1.0：
 */

public class PersonalDataActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.title_left_two_tv)
    TextView titleLeftTwoTv;
    @BindView(R.id.user_touxiang)
    CircleImageView head_portrait;
    @BindView(R.id.rel_userportrait)
    RelativeLayout rel_portrait;
    @BindView(R.id.user_nickname)
    TextView tv_nickname;
    @BindView(R.id.rel_user_nickname)
    RelativeLayout rel_name;
    @BindView(R.id.user_phonenum)
    TextView tv_userphone;
    @BindView(R.id.rel_userphone)
    RelativeLayout rel_phone;
    @BindView(R.id.rel_password)
    RelativeLayout rel_password;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.rel_loginout)
    RelativeLayout relLoginout;
    @BindView(R.id.rel_tixian)
    RelativeLayout relTixian;
    @BindView(R.id.rel_clearcache)
    RelativeLayout rel_clear;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.rel_version)
    RelativeLayout relVersion;
    private GlideImageLoader imageLoader = null;
    private String session = "";
    private String size;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.SUCCESS:
                    // ToastUtils.toastForShort(PersonalDataActivity.this, "环信退出成功!");
                    AliUtils.logout(PersonalDataActivity.this);
//                    loginOut();
                    break;
                case Constant.ERROR:
                    // ToastUtils.toastForShort(PersonalDataActivity.this, "环信退出失败!");
                    break;
            }
        }
    };
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        imageLoader = new GlideImageLoader(PersonalDataActivity.this);
        uid = (String) SPUtils.get(this, Constant.User_Uid, "");
        initTitle();
        getMemberInfo();

    }

    /**
     * 获取会员信息
     */
    private void getMemberInfo() {

        //修改手机号码中间4位为 *
        String user_phone = (String) SPUtils.get(PersonalDataActivity.this, Constant.User_Phone, "手机号: ******");
        String user_header = (String) SPUtils.get(PersonalDataActivity.this, Constant.User_Header, "");
        String user_nick = (String) SPUtils.get(PersonalDataActivity.this, Constant.User_Nick, "****");

        if (!TextUtils.isEmpty(user_header)) {
            imageLoader.display(head_portrait, user_header);
        }
        if (!TextUtils.isEmpty(user_nick)) {
            tv_nickname.setText(user_nick);
        }
        if (!TextUtils.isEmpty(user_phone)) {
//            String phone = StringDealUtil.encryptPhone(user_phone);
            tv_userphone.setText(user_phone);
        }

    }

    private void initTitle() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        titleLeftTwoTv.setVisibility(View.VISIBLE);
        titleLeftTwoTv.setText(getResources().getString(R.string.personal_data));
        tvMiddleTitle.setVisibility(View.GONE);
        titleLeftOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        session = (String) SPUtils.get(PersonalDataActivity.this, Constant.MSESSION, "");

        //设置当前版本号
        String version = PhoneInfoUtil.getInstance().getVersion(PersonalDataActivity.this);
        if (TextUtils.isEmpty(version)) {
            tvVersion.setText("");
        } else {
            tvVersion.setText(version);
        }

        //查看缓存大小
        try {
            size = CacheManager.getTotalCacheSize(getApplicationContext());
            if (size != null) {
                tvCacheSize.setText(size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rel_password, R.id.rel_loginout, R.id.rel_tixian, R.id.rel_version, R.id.rel_clearcache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_password:
                Intent intent = new Intent(PersonalDataActivity.this, ResetPassWordActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_loginout:
//                if (ChatClient.getInstance().isLoggedInBefore()) {
//                    //已经登录，可以直接进入会话界面
//                    HxLoginOut();
//                } else {
//                }
//                AliUtils.logout(PersonalDataActivity.this);     //退出淘宝登录
                loginOut();
                break;
            case R.id.rel_tixian:
                startActivity(new Intent(this, WeChatCashActivity.class));
                break;
            case R.id.rel_version:  //当前版本
//                if (!VoidRepeatClickUtil.isFastDoubleClick()) {
//                Beta.checkUpgrade();
//                }
                break;
            case R.id.rel_clearcache:
                String[] stringItems = {"清除缓存"};
                final ActionSheetDialog sheetDialog = DialogUtils.getBottomSheetDialog(this,
                        "确定要清除缓存数据吗？", stringItems);
                sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            CacheManager.clearAllCache(getApplicationContext());
                            ToastUtils.toastForShort(PersonalDataActivity.this, "清除缓存成功");
                            tvCacheSize.setText("0M");
                        }
                        sheetDialog.dismiss();
                    }
                });
                break;
        }
    }


    /**
     * 环信退出
     */
    private void HxLoginOut() {
        final Message msg = mHandler.obtainMessage();
        ChatClient.getInstance().logout(true, new Callback() {
            @Override
            public void onSuccess() {
                msg.what = Constant.SUCCESS;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(int i, String s) {
                msg.what = Constant.ERROR;
                mHandler.sendMessage(msg);

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    private void loginOut() {
        String url = ConfigValue.LOGIN_OUT + "&uid=" + uid;
        new OkHttpUtils().getDatas(this, url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                if (TextUtils.isEmpty(data)) {
                    ToastUtils.toastForShort(PersonalDataActivity.this, "请求数据有问题!");
                    return;
                }
                LoginCodeBean res = new Gson().fromJson(data, LoginCodeBean.class);
                if (TextUtils.equals(res.getResult().trim(), "LOGOUT_SUCESS")) {
                    SPUtils.clear(PersonalDataActivity.this);
                    SPUtils.put(PersonalDataActivity.this, Constant.First_START, false);
                    PersonalDataActivity.this.finish();
                    Intent i = new Intent(PersonalDataActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    AliUtils.logout(PersonalDataActivity.this);
                    if (ChatClient.getInstance().isLoggedInBefore()) {
                        //已经登录，可以直接进入会话界面
                        HxLoginOut();
                    }
                }
            }
        });
    }

}
