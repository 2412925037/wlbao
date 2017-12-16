package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.IntegralAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.IntegralsBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 积分流水
 */
public class IntegralActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.Integral_sum_tv)
    TextView IntegralSumTv;
    @BindView(R.id.btn_all)
    TextView btnAll;
    @BindView(R.id.btn_expend)
    TextView btnExpend;
    @BindView(R.id.btn_income)
    TextView btnIncome;
    @BindView(R.id.Integral_recycler)
    LRecyclerView IntegralRecycler;
    @BindView(R.id.tv_duanwang)
    TextView tvDuanwang;
    private String uid;
    private int page=1;
    private View emptyView;
    private int totalPage;
    private int TOTAL_COUNTER;
    private static int REQUEST_COUNT;    //每次加载数量
    private IntegralAdapter adapter;
    private List<IntegralsBean.PointsBean> datas=new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        ButterKnife.bind(this);
        uid=(String) SPUtils.get(this, Constant.User_Uid, "");
        initViews();
        type=0;
        adapter.setType(type+"");
        getDatas();
    }

    private void initViews() {
        emptyView=findViewById(R.id.founds_empty_view);
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        titleRightOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("积分流水");

        adapter = new IntegralAdapter(this);
        IntegralRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter.setDataList(datas);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        IntegralRecycler.setAdapter(lRecyclerViewAdapter);
        IntegralRecycler.setPullRefreshEnabled(true);

        //设置下拉刷新
        IntegralRecycler.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                adapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();
                getDatas();
            }
        });
        //设置上拉加载更多
        IntegralRecycler.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (page < totalPage) {
                    page++;
                    getDatas();
                } else {
                    IntegralRecycler.setNoMore(true);
                }
            }
        });

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent i = new Intent(IntegralActivity.this, FriendDetailActivity.class);
//                startActivity(i);
            }
        });
    }

    private void getDatas() {
        DialogUtils.showProgressDlg(this, "加载中...");
        String session = (String) SPUtils.get(this, Constant.MSESSION, "");
//        http://app.yasbao.com/Home/Api/gw?api=yasbao.api.account.points&uid= &apiKey=&type=&p=
//        uid = "1";
        String url = ConfigValue.APP_IP + "?api=yasbao.api.account.points&uid=" + uid + "&apiKey=" + ConfigValue.API_KEY + "&p=" + page+"&type="+type;

        new OkHttpUtils().getDatas(this,url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                IntegralsBean bean = new Gson().fromJson(data, IntegralsBean.class);
                if (TextUtils.equals(bean.getResult().trim(), "SUCESS")) {
                    totalPage = bean.getPageTotals();
                    REQUEST_COUNT= bean.getPageSize();
                    bean.getTotals();
                    adapter.addAll(bean.getPoints());
                    lRecyclerViewAdapter.notifyDataSetChanged();
                    IntegralRecycler.refreshComplete(REQUEST_COUNT);
                } else if(TextUtils.equals("NOLOGIN", bean.getResult().trim())){
                    ToastUtils.toastForShort(IntegralActivity.this, bean.getWorngMsg());
                    IntegralActivity.this.finish();
                    startActivity(new Intent(IntegralActivity.this, LoginActivity.class));
                }else {
                    IntegralRecycler.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    //ToastUtils.toastForShort(IntegralActivity.this, bean.getWorngMsg());
                }
                DialogUtils.stopProgressDlg();
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                IntegralRecycler.setVisibility(View.GONE);
                tvDuanwang.setVisibility(View.VISIBLE);
                IntegralActivity.this.finish();
                ToastUtils.toastForLong(IntegralActivity.this, getString(R.string.bad_net));
                DialogUtils.stopProgressDlg();
            }
        });
    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_btn,R.id.btn_expend, R.id.btn_income,R.id.btn_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                IntegralActivity.this.finish();
                break;
            case R.id.title_right_one_btn:
                Intent largeIntent = new Intent(IntegralActivity.this, TaoBaoSearchActivity.class);
                startActivity(largeIntent);
                break;

            case R.id.btn_all:
                emptyView.setVisibility(View.GONE);
                tvDuanwang.setVisibility(View.GONE);
                IntegralRecycler.setVisibility(View.VISIBLE);
                btnAll.setBackgroundColor(getResources().getColor(R.color.app_bg_color));
                btnExpend.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                btnIncome.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                type=0;

                adapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();
                adapter.setType(""+type);
                getDatas();


                break;
            case R.id.btn_expend:
                emptyView.setVisibility(View.GONE);
                tvDuanwang.setVisibility(View.GONE);
                IntegralRecycler.setVisibility(View.VISIBLE);
                btnExpend.setBackgroundColor(getResources().getColor(R.color.app_bg_color));
                btnIncome.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                btnAll.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                type=-1;

                adapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();
                adapter.setType(""+type);
                getDatas();
                break;
            case R.id.btn_income:
                emptyView.setVisibility(View.GONE);
                tvDuanwang.setVisibility(View.GONE);
                IntegralRecycler.setVisibility(View.VISIBLE);
                btnExpend.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                btnAll.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                btnIncome.setBackgroundColor(getResources().getColor(R.color.app_bg_color));
                type=1;
                adapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();
                adapter.setType(""+type);
                getDatas();
                break;
        }
    }


}
