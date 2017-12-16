package com.renhuikeji.wanlb.wanlibao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;

import java.util.List;

public class TestActivity extends AppCompatActivity {
    private LRecyclerView recyclerView = null;
    private TestAdapter adapter = null;
    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private int TOTAL_COUNTER = 1000;
    private static final int REQUEST_COUNT = 10;
    private static int mCurrentCounter = 0;
    private View emptyView = null;  //空数据

    private String url;
    private int page = 1;               //当前页数
    private int totalPage = 500;
    // private TestBean testBean = null;
    private Gson gson = null;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        DialogUtils.showProgressDlg(TestActivity.this, "正在努力加载.......");
        gson = new Gson();
        textView = (TextView) findViewById(R.id.text_duanwang);

        recyclerView = (LRecyclerView) findViewById(R.id.test_recycler);
        emptyView = findViewById(R.id.test_empty_view);

        DialogUtils.showProgressDlg(TestActivity.this, "拼命加载中....");
        initRecy();
        //判断网络
        if (!NetworkManageUtil.checkNetworkAvailable(TestActivity.this)) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            DialogUtils.stopProgressDlg();
        }
    }

    private void initRecy() {
        recyclerView.setLayoutManager(new GridLayoutManager(TestActivity.this, 2));
        adapter = new TestAdapter(TestActivity.this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);

        //设置lrecyclerview
        GridItemDecoration divider = new GridItemDecoration.Builder(TestActivity.this)
                .setHorizontal(R.dimen.line_height)
                .setVertical(R.dimen.line_height)
                .setColorResource(R.color.line_gray)
                .build();
        recyclerView.addItemDecoration(divider);
        recyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        recyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        //设置下拉刷新
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //请求第一页数据
                url = "http://app.yasbao.com/Home/Api/search.do? q=\"帽子\"& type=1&p=1";
                new OkHttpUtils().getJson(url, new OkHttpUtils.HttpCallBack() {
                    @Override
                    public void onSusscess(String data) {

                        // testBean = new Gson().fromJson(data, TestBean.class);
                        TestBean testBean = gson.fromJson(data, TestBean.class);
                        TOTAL_COUNTER = testBean.getGoodsCount(); //总数
                        totalPage = testBean.getTotalPages();
                        List<TestBean.GoodsArrBean> list = testBean.getGoodsArr();

                        adapter.addAll(list);
                        mCurrentCounter = list.size();
                        recyclerView.refreshComplete(REQUEST_COUNT);
                    }
                });

            }
        });
        //设置上拉加载更多
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER && page < totalPage) {
                    page++;
                    getData();
                } else {
                    recyclerView.setNoMore(true);
                }
            }
        });
        //设置头部加载颜色
        recyclerView.setHeaderViewColor(R.color.colorAccent, R.color.fuzhu_black, R.color.colorWhite);
        //设置底部加载颜色
        recyclerView.setFooterViewColor(R.color.colorAccent, R.color.fuzhu_black, R.color.colorWhite);
        //设置底部加载文字提示
        recyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        //初始化数据
        getData();

    }

    public void getData() {
        /**
         * 获取网络数据
         */
        url = "http://app.yasbao.com/Home/Api/search.do? q=\"帽子\"& type=1&p=" + page;

        new OkHttpUtils().getJson(url, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                // testBean = new Gson().fromJson(data, TestBean.class);
                TestBean testBean = gson.fromJson(data, TestBean.class);
                TOTAL_COUNTER = testBean.getGoodsCount(); //总数
                totalPage = testBean.getTotalPages();
                List<TestBean.GoodsArrBean> list = testBean.getGoodsArr();

                if (list.size() == 0 || list == null) {
                    recyclerView.setEmptyView(emptyView);
                    DialogUtils.stopProgressDlg();
                    return;
                }
                DialogUtils.stopProgressDlg();
                adapter.addAll(list);
                // mCurrentCounter += newList.size();
                mCurrentCounter = list.size();
                // recyclerView.refreshComplete(REQUEST_COUNT);
                recyclerView.refreshComplete(list.size());

            }
        });
    }


//-----------------------------------------------------------------------
}
