package com.renhuikeji.wanlb.wanlibao.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.fragment.founds.FoundsDetailsFragment;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.VoidRepeatClickUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 资金明细
 */
public class FinancialDetailActivity extends BaseActivity {

    @BindView(R.id.financial_back)
    ImageView iconback;
    @BindView(R.id.btn_all)
    TextView btn_all;
    @BindView(R.id.btn_expend)
    TextView btn_expend;
    @BindView(R.id.btn_income)
    TextView btn_income;
    private FragmentManager fragmentManager;
    private FoundsDetailsFragment allFragment;   //全部
    private FoundsDetailsFragment incomeFragment;  //收入
    private FoundsDetailsFragment expendFragment;  //支出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_detail);
        ButterKnife.bind(this);

        fragmentManager = this.getSupportFragmentManager();
        //默认全部被选中
        clickPosition(0);
        creatFragment(0);
}


    @OnClick({R.id.financial_back, R.id.btn_all, R.id.btn_expend, R.id.btn_income})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.financial_back:
                finish();
                break;
            case R.id.btn_all:   //全部
                if (!VoidRepeatClickUtil.isFastDoubleClick()) {
                    clickPosition(0);
                    creatFragment(0);
                }
                break;
            case R.id.btn_expend: //支出
                if (!VoidRepeatClickUtil.isFastDoubleClick()) {
                    clickPosition(-1);
                    creatFragment(-1);
                }
                break;
            case R.id.btn_income:  //收入
                if (!VoidRepeatClickUtil.isFastDoubleClick()) {
                    clickPosition(1);
                    creatFragment(1);
                }
                break;

        }
    }

    private void creatFragment(int position) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (position) {
            case 0:  //全部
                if (allFragment == null) {
                    allFragment = new FoundsDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("loadType", 0); //type=0  全部
                    allFragment.setArguments(bundle);
                    transaction.add(R.id.foundsdetails_container, allFragment);
                } else {
                    if (!allFragment.isVisible()) {
                    }
                    transaction.show(allFragment);
                }
                break;
            case 1:   //type=1  收入
                if (incomeFragment == null) {
                    incomeFragment = new FoundsDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("loadType", 1); //type=1  收入
                    incomeFragment.setArguments(bundle);
                    transaction.add(R.id.foundsdetails_container, incomeFragment);
                } else {
                    if (!incomeFragment.isVisible()) {
                    }
                    transaction.show(incomeFragment);
                }
                break;
            case -1://type=-1 支出
                if (expendFragment == null) {
                    expendFragment = new FoundsDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("loadType", -1); //type=-1 支出
                    expendFragment.setArguments(bundle);
                    transaction.add(R.id.foundsdetails_container, expendFragment);
                } else {
                    if (!expendFragment.isVisible()) {
                    }
                    transaction.show(expendFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();

    }

    /**
     * 隐藏fragment
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (allFragment != null) {
            transaction.hide(allFragment);
        }
        if (incomeFragment != null) {
            transaction.hide(incomeFragment);
        }
        if (expendFragment != null) {
            transaction.hide(expendFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        allFragment = null;
        incomeFragment = null;
        expendFragment = null;

    }

    /**
     * 点击位置
     */
    private void clickPosition(int i) {
        switch (i) {
            case 0:
                btn_all.setBackgroundColor(getResources().getColor(R.color.app_bg_color));
                btn_expend.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                btn_income.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                break;
            case 1:
                btn_all.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                btn_expend.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                btn_income.setBackgroundColor(getResources().getColor(R.color.app_bg_color));
                break;
            case -1:
                btn_all.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                btn_expend.setBackgroundColor(getResources().getColor(R.color.app_bg_color));
                btn_income.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                break;
        }

    }


    //------------------------END------------------------------
}
