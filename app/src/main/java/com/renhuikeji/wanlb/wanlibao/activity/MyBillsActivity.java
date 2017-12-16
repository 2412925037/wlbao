package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.MyBillsAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.BillsItemBean;
import com.renhuikeji.wanlb.wanlibao.utils.MyThreadPool;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 */
public class MyBillsActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.lrv_my_bills)
    LRecyclerView lrvMyBills;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    private List<BillsItemBean> billsItemBeen = new ArrayList<>();
    private int mCurrenCunt = 1;
    private final static int REQUEST_NUM = 10;
    private final static int TOTAL_NUM = 30;
    private MyBillsAdapter myBillsAdapter;
    private LRecyclerViewAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    initBillData();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bills);
        ButterKnife.bind(this);

        initViews();
    }

    /**
     * 获取订单列表
     */
    private void initBillData() {
      /*  BillsItemBean billsItemBean = null;
        for (int i = 0; i < REQUEST_NUM; i++) {
            if (mCurrenCunt >= TOTAL_NUM) {
                break;
            }

            billsItemBean = new BillsItemBean();
            billsItemBean.setId("1234567");
            billsItemBean.setTradeState(-1);
//            billsItemBean.setUrl("http://static.firefoxchina.cn/img/201705/8_59094c16a2cd50.jpg");
            billsItemBeen.add(billsItemBean);
            mCurrenCunt++;
        }
        myBillsAdapter.addIn(billsItemBeen);
        lrvMyBills.refreshComplete(REQUEST_NUM);*/

    }

    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("我的订单");
        titleRightOneBtn.setVisibility(View.VISIBLE);
        new GlideImageLoader(MyBillsActivity.this).display(titleRightOneBtn, R.mipmap.icon_search);

        lrvMyBills.setLayoutManager(new LinearLayoutManager(MyBillsActivity.this));
        myBillsAdapter = new MyBillsAdapter(MyBillsActivity.this,false);
        adapter = new LRecyclerViewAdapter(myBillsAdapter);
        lrvMyBills.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyBillsActivity.this, BillDetailActivity.class);
                startActivity(intent);
            }
        });

        DividerDecoration decoration = new DividerDecoration.Builder(MyBillsActivity.this)
                .setHeight(R.dimen.line_height)
                .setColorResource(R.color.line_gray)
                .build();
        lrvMyBills.addItemDecoration(decoration);

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
                Intent intent = new Intent(MyBillsActivity.this, TaoBaoSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        myBillsAdapter.clear();
        mCurrenCunt = 1;
        requestData();
    }

    @Override
    public void onLoadMore() {
        if (mCurrenCunt < TOTAL_NUM) {
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
