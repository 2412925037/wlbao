package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.MyCashFlowAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.AccountInfo;
import com.renhuikeji.wanlb.wanlibao.bean.MyAccountBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.views.MyListView;
import com.renhuikeji.wanlb.wanlibao.views.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的钱包
 */
public class CashFlowActivity extends BaseActivity {

    @BindView(R.id.img_cash_flow_back)
    ImageView imgCashFlowBack;
    @BindView(R.id.tv_cash_flow_title)
    TextView tvCashFlowTitle;
    @BindView(R.id.lv_cash_flow)
    MyListView lvCashFlow;
    @BindView(R.id.msv_cash_flow)
    MyScrollView msvCashFlow;
    @BindView(R.id.cashflow_id_tv)
    TextView cashflowIdTv;
    @BindView(R.id.cashflow_money_tv)
    TextView cashflowMoneyTv;
    @BindView(R.id.to_WeChat_tv)
    TextView toWeChatTv;
    @BindView(R.id.btn_financialdetails)
    TextView btn_financialdetails;
    private List<MyAccountBean> myAccountBeen = new ArrayList<>();
    private String[] myAccount = {"推广费", "提成费", "返利收入", "佣金收入", "未兑积分", "红包余额",
            "收入总额", "账户余额", "预估推广费余额", "预估代理费余额", "预估提成费余额", "信用保证金余额"};
    private String uid;
    private MyCashFlowAdapter adapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String res = msg.obj.toString().trim();
            if (TextUtils.isEmpty(res)) {
                ToastUtils.toastForShort(CashFlowActivity.this, "请求数据失败!");
                return;
            }
            switch (msg.what) {
                case Constant.SUCCESS:
                    AccountInfo info = new Gson().fromJson(res, AccountInfo.class);
                    if (TextUtils.equals("SUCESS", info.getResult())) {
                        setDatas(info.getAccount());
                    }else if(TextUtils.equals("NOLOGIN", info.getResult())){
                        ToastUtils.toastForLong(CashFlowActivity.this,info.getWorngMsg());
                        CashFlowActivity.this.finish();
                        startActivity(new Intent(CashFlowActivity.this, LoginActivity.class));
                    }

                    break;
            }
        }
    };

    private void setDatas(AccountInfo.AccountBean info) {
        cashflowIdTv.setText("653699"+info.getId().trim());
        cashflowMoneyTv.setText(info.getMoney());
        String[] accountMoney = new String[]{
                info.getSales_income(),
                info.getBusiness_income(),
                info.getReturns(),
                info.getCommission(),
                info.getPoints(),
                info.getReg_redbag(),
                info.getIncome(),
                info.getMoney(),
                info.getSales_account(),
                info.getAgent_account(),
                info.getBusiness_account(),
                info.getDeposit()};

        MyAccountBean myAccountBean = null;
        for (int i = 0; i < myAccount.length; i++) {
            myAccountBean = new MyAccountBean();
            myAccountBean.setCostName(myAccount[i]);
            myAccountBean.setCostNum(accountMoney[i]);
            myAccountBeen.add(myAccountBean);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);
        ButterKnife.bind(this);
        uid = (String) SPUtils.get(this, Constant.User_Uid, "");
        initViews();
        getDatas();
    }

    private void initViews() {
        adapter = new MyCashFlowAdapter(myAccountBeen, CashFlowActivity.this);
        lvCashFlow.setAdapter(adapter);
        msvCashFlow.smoothScrollTo(20, 0);
    }


    @OnClick({R.id.img_cash_flow_back, R.id.to_WeChat_tv, R.id.btn_financialdetails})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_cash_flow_back:
                finish();
                break;
            case R.id.to_WeChat_tv:          //立即提现
                startActivity(new Intent(CashFlowActivity.this, WeChatCashActivity.class));
                break;
            case R.id.btn_financialdetails:  //资金明细界面
                startActivity(new Intent(CashFlowActivity.this, FinancialDetailActivity.class));
                break;
        }
    }

    public void getDatas() {
        DialogUtils.showProgressDlg(this, "加载中....");
        String url = ConfigValue.APP_IP + "?api=yasbao.api.member.account&uid=" + uid + "&apiKey=" + ConfigValue.API_KEY;
        String session= (String) SPUtils.get(this,Constant.MSESSION,"");
        new OkHttpUtils().getDatas(CashFlowActivity.this,url,session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.SUCCESS;
                msg.obj = data;
                mHandler.sendMessage(msg);
                DialogUtils.stopProgressDlg();
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                CashFlowActivity.this.finish();
                ToastUtils.toastForShort(CashFlowActivity.this,getString(R.string.bad_net));
                DialogUtils.stopProgressDlg();
            }
        });

    }

}
