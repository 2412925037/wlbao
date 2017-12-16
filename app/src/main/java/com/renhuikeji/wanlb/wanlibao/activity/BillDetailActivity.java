package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.BillDetailAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.BillDetailBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.wireless.security.open.nocaptcha.INoCaptchaComponent.status;

/**
 * 订单详情
 */
public class BillDetailActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.lv_bill_detail)
    ListView lvBillDetail;
    @BindView(R.id.tv_bill_detail_more)
    TextView tvBillDetailMore;
    @BindView(R.id.tv_bill_detail_no_net)
    TextView tvBillDetailNoNet;
    @BindView(R.id.detail_fl)
    FrameLayout detailFl;
    private int mCurrenCount = 0;
    private int TOAL_NUM = 5;
    private BillDetailAdapter adapter;
    private String id;
    private BillDetailBean billDetailBean;
    private View emptyView;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.all_pink), 55);
        uid = (String) SPUtils.get(this, Constant.User_Uid, "");
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        initViews();
        getDatas();
    }

    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        titleRightOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("订单详情");
        emptyView = findViewById(R.id.empty_bill_detail);

//        lvBillDetail.setEmptyView(emptyView);
        adapter = new BillDetailAdapter(BillDetailActivity.this);
        lvBillDetail.setAdapter(adapter);
    }

    /**
     * 获取订单列表
     */
    private void getDatas() {
        DialogUtils.showProgressDlg(this, "加载中...");

        if (!NetworkManageUtil.checkNetworkAvailable(this)) {
            DialogUtils.stopProgressDlg();
            tvBillDetailNoNet.setVisibility(View.VISIBLE);
        }

        String session = (String) SPUtils.get(this, Constant.MSESSION, "");
        String url = ConfigValue.APP_IP + "?api=yasbao.api.order.detail&uid=" + uid + "&apiKey=" + ConfigValue.API_KEY + "&status=" + status + "&id=" + id;
        new OkHttpUtils().getDatas(this, url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                Log.d("CCC",data);
                DialogUtils.stopProgressDlg();
                billDetailBean = new Gson().fromJson(data, BillDetailBean.class);
                if (null != billDetailBean) {
                    switch (billDetailBean.getResult()) {
                        case "SUCESS":
                            TOAL_NUM = billDetailBean.getProduct().size();
                            addBillsMsg();
                            break;
                        case "NOLOGIN":
                            ToastUtils.toastForShort(BillDetailActivity.this, billDetailBean.getWorngMsg());
                            BillDetailActivity.this.finish();
                            BillDetailActivity.this.startActivity(new Intent(BillDetailActivity.this, LoginActivity.class));
                            break;
                        case "FAIL":
                            detailFl.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                            ToastUtils.toastForShort(BillDetailActivity.this, billDetailBean.getWorngMsg());
                            break;
                    }
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                DialogUtils.stopProgressDlg();
                ToastUtils.toastForShort(BillDetailActivity.this, meg);
                adapter.clear();
                tvBillDetailNoNet.setText("数据获取失败，点击刷新");
                // tvBillDetailNoNet.setTextColor(getColor(R.color.colorPrimaryDark));
                tvBillDetailNoNet.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
    }

    /**
     * 获取数据
     */
    private void addBillsMsg() {
        List<BillDetailBean.ProductBean> productBeen = new ArrayList<>();

        if (2 >= TOAL_NUM - mCurrenCount) {
            tvBillDetailMore.setVisibility(View.GONE);
            int restNum = TOAL_NUM - mCurrenCount;
            for (int i = 0; i < restNum; i++) {
                productBeen.add(billDetailBean.getProduct().get(mCurrenCount));
                mCurrenCount++;
            }
        } else {
            tvBillDetailMore.setVisibility(View.VISIBLE);
            for (int i = 0; i < 2; i++) {
                productBeen.add(billDetailBean.getProduct().get(mCurrenCount));
                mCurrenCount++;
            }
        }
        adapter.addAll(productBeen);
    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_btn, R.id.tv_bill_detail_more,
            R.id.tv_bill_detail_no_net})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.title_right_one_btn:
                Intent intent = new Intent(BillDetailActivity.this, TaoBaoSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_bill_detail_more:
                addBillsMsg();
                break;
            case R.id.tv_bill_detail_no_net:
                mCurrenCount = 0;
                getDatas();
        }
    }
}
