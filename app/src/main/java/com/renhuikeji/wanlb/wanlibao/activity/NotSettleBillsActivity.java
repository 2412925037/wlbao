package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.MyBillsAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.MyOrderBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.MyThreadPool;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待结算订单
 */
public class NotSettleBillsActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.lrv_my_bills)
    LRecyclerView lrvMyBills;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    private int mCurrenCunt = 1;
    private final static int REQUEST_NUM = 10;
    private final static int TOTAL_NUM = 30;
    private MyBillsAdapter myBillsAdapter;
    private LRecyclerViewAdapter adapter;
    private List<MyOrderBean.OrdersBean> list = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    getDatas();
                    break;
            }
        }
    };

    private int totalPage;
    private int TOTAL_COUNTER;
    private String uid;
    private int status;
    private int page=1;
    private int REQUEST_COUNT;
    @BindView(R.id.tv_general_goods_no_net)
    TextView tvGeneralGoodsNoNet;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bills);
        ButterKnife.bind(this);
        uid= (String) SPUtils.get(this,Constant.User_Uid,"");
        initViews();
    }

    /**
     * 获取订单列表
     */
    private void getDatas() {
        String session = (String) SPUtils.get(this, Constant.MSESSION, "");
//        http://app.yasbao.com/Home/Api/gw?api=yasbao.api.order.orderlist&uid= &apiKey=&status=&p=
        status=-1;
        String url = ConfigValue.APP_IP + "?api=yasbao.api.order.orderlist&uid=" + uid + "&apiKey=" + ConfigValue.API_KEY + "&status="+status+"&p=" + page;

        new OkHttpUtils().getDatas(context,url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                MyOrderBean bean = new Gson().fromJson(data, MyOrderBean.class);
                if (TextUtils.equals(bean.getResult().trim(), "SUCESS")) {
                    totalPage = bean.getPageTotals();
                    REQUEST_COUNT = bean.getPageSize();
                    TOTAL_COUNTER = Integer.parseInt(bean.getTotals());
                    myBillsAdapter.addIn(bean.getOrders());
                    adapter.notifyDataSetChanged();
                    lrvMyBills.refreshComplete(REQUEST_COUNT);
                }
                else if(TextUtils.equals("NOLOGIN", bean.getResult().trim())){
                    ToastUtils.toastForShort(NotSettleBillsActivity.this, bean.getWorngMsg());
                    NotSettleBillsActivity.this.finish();
                    startActivity(new Intent(NotSettleBillsActivity.this, LoginActivity.class));
                }else {
                    lrvMyBills.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    ToastUtils.toastForShort(NotSettleBillsActivity.this, bean.getWorngMsg());
                }
                DialogUtils.stopProgressDlg();
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                lrvMyBills.setVisibility(View.GONE);
                tvGeneralGoodsNoNet.setVisibility(View.VISIBLE);
                ToastUtils.toastForLong(NotSettleBillsActivity.this, "请求失败!");
            }
        });
    }
    private void initViews() {
        emptyView=findViewById(R.id.empty_general_goods);
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("待结算订单");
        titleRightOneBtn.setVisibility(View.VISIBLE);
        new GlideImageLoader(NotSettleBillsActivity.this).display(titleRightOneBtn, R.mipmap.icon_search);

        lrvMyBills.setLayoutManager(new LinearLayoutManager(NotSettleBillsActivity.this));
        myBillsAdapter = new MyBillsAdapter(NotSettleBillsActivity.this,false);
        adapter = new LRecyclerViewAdapter(myBillsAdapter);
        lrvMyBills.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(NotSettleBillsActivity.this, BillDetailActivity.class);
                String id=myBillsAdapter.getList().get(position).getId();
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        DividerDecoration decoration = new DividerDecoration.Builder(NotSettleBillsActivity.this)
                .setHeight(R.dimen.line_height)
                .setColorResource(R.color.line_gray)
                .build();
//        lrvMyBills.addItemDecoration(decoration);

        //设置上拉刷新
        lrvMyBills.setOnRefreshListener(this);
        //设置上拉加载更多
        lrvMyBills.setOnLoadMoreListener(this);
        //设置头部加载颜色
        lrvMyBills.setHeaderViewColor(R.color.colorAccent, R.color.fuzhu_black, R.color.colorWhite);
        //设置底部加载颜色
        lrvMyBills.setFooterViewColor(R.color.colorAccent, R.color.fuzhu_black, R.color.colorWhite);
        //设置底部加载文字提示
        lrvMyBills.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        lrvMyBills.refresh();
    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
            finish();
                break;
            case R.id.title_right_one_btn:
                Intent intent = new Intent(NotSettleBillsActivity.this, TaoBaoSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        myBillsAdapter.clear();
        myBillsAdapter.notifyDataSetChanged();
        page=1;
        requestData();
    }

    @Override
    public void onLoadMore() {
        if (page < totalPage) {
            page++;
            requestData();
        } else {
            lrvMyBills.setNoMore(true);
        }
    }

    private void requestData() {
        MyThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(-1);
            }
        });
    }
}
