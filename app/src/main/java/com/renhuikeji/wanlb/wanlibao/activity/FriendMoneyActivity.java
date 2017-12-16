package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendMoneyActivity extends BaseActivity {

    @BindView(R.id.sales_income_tv)
    TextView salesIncomeTv;
    @BindView(R.id.agent_income_tv)
    TextView agentIncomeTv;
    @BindView(R.id.returns_tv)
    TextView returnsTv;
    @BindView(R.id.commission_tv)
    TextView commissionTv;
    @BindView(R.id.reg_redbag_tv)
    TextView regRedbagTv;
    @BindView(R.id.sales_account_tv)
    TextView salesAccountTv;
    @BindView(R.id.agent_account_tv)
    TextView agentAccountTv;
    @BindView(R.id.business_account_tv)
    TextView businessAccountTv;
    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_money);
        ButterKnife.bind(this);
        tvMiddleTitle.setText("收入-余额");
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        initDatas();
    }

    private void initDatas() {
        Intent i = getIntent();
        salesIncomeTv.setText(i.getStringExtra("saleIncome"));
        agentIncomeTv.setText(i.getStringExtra("agentIncome"));
        returnsTv.setText(i.getStringExtra("returns"));
        commissionTv.setText(i.getStringExtra("commission"));
        regRedbagTv.setText(i.getStringExtra("redBag"));
        salesAccountTv.setText(i.getStringExtra("saleAccount"));
        agentAccountTv.setText(i.getStringExtra("agentAccount"));
        businessAccountTv.setText(i.getStringExtra("businessAccount"));
    }

    @OnClick(R.id.title_left_one_btn)
    public void onViewClicked() {
        FriendMoneyActivity.this.finish();
    }
}
