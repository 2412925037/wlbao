package com.renhuikeji.wanlb.wanlibao.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;
import com.renhuikeji.wanlb.wanlibao.views.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户设置界面
 */
public class AccountSettingActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.title_left_two_tv)
    TextView titleLeftTwoTv;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.tv_account_setting_tixian)
    LinearLayout tvAccountSettingTixian;
    @BindView(R.id.tv_account_setting_guanyu)
    LinearLayout tvAccountSettingGuanyu;
    @BindView(R.id.tv_account_setting_jianceshengji)
    LinearLayout tvAccountSettingJianceshengji;
    @BindView(R.id.tv_account_setting_qinglihuancun)
    LinearLayout tvAccountSettingQinglihuancun;
    @BindView(R.id.ll_account_user_msg)
    LinearLayout llAccountUserMsg;
    @BindView(R.id.civ_my_account_icon)
    CircleImageView civMyAccountIcon;
    @BindView(R.id.tv_my_account_name)
    TextView tvMyAccountName;
    @BindView(R.id.tv_my_account_phone)
    TextView tvMyAccountPhone;
    @BindView(R.id.tv_account_setting_tuichudenglu)
    LinearLayout tvAccountSettingTuichudenglu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        ButterKnife.bind(this);

        initViews();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        titleLeftTwoTv.setVisibility(View.VISIBLE);
        tvMiddleTitle.setVisibility(View.GONE);
        titleLeftTwoTv.setText("账户设置");
        titleLeftOneBtn.setImageDrawable(getResources().getDrawable(R.mipmap.black_back));

        String icon_url = (String) SPUtils.get(AccountSettingActivity.this, Constant.User_Header, "");
        new GlideImageLoader(this).display(civMyAccountIcon, icon_url);
        tvMyAccountName.setText((String)SPUtils.get(this, Constant.User_Nick, ""));
        tvMyAccountPhone.setText((String)SPUtils.get(this, Constant.User_Phone, ""));
    }

    @OnClick({R.id.title_left_one_btn, R.id.tv_account_setting_tixian,
            R.id.tv_account_setting_guanyu, R.id.tv_account_setting_jianceshengji,
            R.id.tv_account_setting_qinglihuancun, R.id.ll_account_user_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:                   //返回
                finish();
                break;
            case R.id.tv_account_setting_tixian:            //提现
                break;
            case R.id.tv_account_setting_guanyu:            //关于
                break;
            case R.id.tv_account_setting_jianceshengji:     //检测升级
                break;
            case R.id.tv_account_setting_qinglihuancun:     //清理缓存
                break;
            case R.id.ll_account_user_msg:                  //会员信息
                break;
        }
    }
}
