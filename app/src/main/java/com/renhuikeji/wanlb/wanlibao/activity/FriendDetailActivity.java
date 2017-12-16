package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.FriendDetailBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;
import com.renhuikeji.wanlb.wanlibao.views.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.renhuikeji.wanlb.wanlibao.utils.SPUtils.get;

public class FriendDetailActivity extends BaseActivity {

    @BindView(R.id.head_img)
    CircleImageView headImg;
    @BindView(R.id.nick)
    TextView nick;
    @BindView(R.id.detail_id)
    TextView detailId;
    @BindView(R.id.detail_phone)
    TextView detailPhone;
    @BindView(R.id.detail_money_title)
    TextView detailMoneyTitle;
    @BindView(R.id.detail_income_tv)
    TextView detailIncomeTv;
    @BindView(R.id.detail_sum_tv)
    TextView detailSumTv;
    @BindView(R.id.register_time_tv)
    TextView registerTimeTv;
    @BindView(R.id.lastlogintime_tv)
    TextView lastlogintimeTv;
    @BindView(R.id.friendnum_tv)
    TextView friendnumTv;
    @BindView(R.id.share_status_tv)
    TextView shareStatusTv;
    @BindView(R.id.detail_vip_tv)
    TextView detailVipTv;
    @BindView(R.id.total_income_tv)
    TextView totalIncomeTv;
    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.detail_friend_rl)
    RelativeLayout detailFriendRl;
    private String businessAccount;
    private String agentAccount;
    private String saleAccount;
    private String redBag;
    private String commission;
    private String returns;
    private String agentIncome;
    private String saleIncome;
    private String friendId;
    private String nickStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        ButterKnife.bind(this);
        tvMiddleTitle.setText("好友详情");
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        String id = getIntent().getStringExtra("id");
        getDatas(id);
    }

    private void getDatas(String id) {
//        http://app.yasbao.com/Home/Api/gw?api=yasbao.api.member.friendinfo&uid= &apiKey=
        String url = ConfigValue.APP_IP + "?api=yasbao.api.member.friendinfo&uid=" + id + "&apiKey=" + ConfigValue.API_KEY;

        String session = (String) get(this, Constant.MSESSION, "");
        new OkHttpUtils().getDatas(this,url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                FriendDetailBean bean = new Gson().fromJson(data, FriendDetailBean.class);
                if (TextUtils.equals(bean.getResult().trim(), "SUCESS")) {
                    updateView(bean.getFriendinfo());
                }
                else if(TextUtils.equals("NOLOGIN", bean.getResult().trim())){
                    ToastUtils.toastForLong(FriendDetailActivity.this,bean.getWorngMsg());
                    FriendDetailActivity.this.finish();
                    startActivity(new Intent(FriendDetailActivity.this, LoginActivity.class));
                }else {
                    ToastUtils.toastForShort(FriendDetailActivity.this, bean.getWorngMsg());
                }
                DialogUtils.stopProgressDlg();
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                ToastUtils.toastForLong(FriendDetailActivity.this, "请求失败!");
                DialogUtils.stopProgressDlg();
            }
        });
    }

    private void updateView(FriendDetailBean.FriendinfoBean bean) {
        friendId=bean.getUid();
        saleIncome = bean.getSales_income();
        agentIncome = bean.getAgent_income();
        returns = bean.getReturns();
        commission = bean.getCommission();
        redBag = bean.getReg_redbag();
        saleAccount = bean.getSales_account();
        agentAccount = bean.getAgent_account();
        businessAccount = bean.getBusiness_account();
        nickStr=bean.getNick();

        new GlideImageLoader(this).displayHeadImg(headImg, bean.getHeadimgurl());
        nick.setText(nickStr);
        String phone = bean.getMobile();
       /* if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            String start = phone.substring(0, 3);
            String end = phone.substring(7, 11);
            phone = start + "****" + end;
        }*/
//        detailPhone.setText("手机号:"+phone);
        detailId.setText("手机号:"+phone);
        totalIncomeTv.setText(bean.getIncome() + "元");
        registerTimeTv.setText(bean.getRegtime());
        lastlogintimeTv.setText(bean.getLastlogintime());
        friendnumTv.setText(bean.getTeam());
        if (TextUtils.equals(bean.getShare_status().trim(), "-1")) {

            shareStatusTv.setText("否");
        } else {
            shareStatusTv.setText("是");
        }
        if (TextUtils.equals(bean.getVip().trim(), "N")) {

            detailVipTv.setText("否");
        } else {
            detailVipTv.setText("是");
        }

    }

    @OnClick({R.id.detail_phone, R.id.detail_income_tv, R.id.detail_sum_tv, R.id.title_left_one_btn, R.id.detail_friend_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detail_phone:
                break;
            case R.id.detail_income_tv:
                Intent i = new Intent(FriendDetailActivity.this, FriendMoneyActivity.class);
                i.putExtra("saleIncome", saleIncome);
                i.putExtra("agentIncome", agentIncome);
                i.putExtra("returns", returns);
                i.putExtra("commission", commission);
                i.putExtra("redBag", redBag);
                i.putExtra("saleAccount", saleAccount);
                i.putExtra("agentAccount", agentAccount);
                i.putExtra("businessAccount", businessAccount);
                startActivity(i);

                break;
            case R.id.detail_sum_tv:
                break;
            case R.id.title_left_one_btn:
                FriendDetailActivity.this.finish();
                break;
            case R.id.detail_friend_rl:
                boolean isLook= (boolean) SPUtils.get(FriendDetailActivity.this,Constant.IS_LOOK,false);
                if(isLook){
                    Intent intent = new Intent(FriendDetailActivity.this, MyFriendActivity.class);
                    intent.putExtra("id",friendId);
                    intent.putExtra("nick",nickStr);
                    startActivity(intent);
                    FriendDetailActivity.this.finish();
                }
                break;
        }
    }

}
