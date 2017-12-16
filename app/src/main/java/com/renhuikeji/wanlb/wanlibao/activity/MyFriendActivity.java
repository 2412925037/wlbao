package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.renhuikeji.wanlb.wanlibao.adapter.MyFriendAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.FriendsBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFriendActivity extends BaseActivity {

    @BindView(R.id.myfriend_recyclerview)
    LRecyclerView myfriendRecyclerview;
    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.tv_general_goods_no_net)
    TextView tvGeneralGoodsNoNet;
    private View emptyView;
    private String uid;
    private int page = 1;
    private MyFriendAdapter adapter;
    private List<FriendsBean.FriendBean> datas = new ArrayList<>();

    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int TOTAL_COUNTER;     //商品总数
    private static int REQUEST_COUNT;    //每次加载数量
    private static int mCurrentCounter;   //当前商品位置
    private int totalPage;
    private String nickStr;
    //    是否允许查看用户的好友
    private boolean isLook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);
        ButterKnife.bind(this);
        uid = getIntent().getStringExtra("id");
        nickStr = getIntent().getStringExtra("nick");
        if (TextUtils.isEmpty(uid)) {
            uid = (String) SPUtils.get(this, Constant.User_Uid, "");
        }
        initView();

    }

    private void initView() {
        emptyView = findViewById(R.id.empty_general_goods);
        if (TextUtils.isEmpty(nickStr)) {
            isLook = true;
            SPUtils.put(this, Constant.IS_LOOK, isLook);
            tvMiddleTitle.setText("好友列表");
        } else {
            tvMiddleTitle.setText("好友的好友列表");
        }
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        adapter = new MyFriendAdapter(this);
        myfriendRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter.setDataList(datas);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        myfriendRecyclerview.setAdapter(lRecyclerViewAdapter);
        myfriendRecyclerview.setPullRefreshEnabled(true);
        DividerDecoration decoration = new DividerDecoration.Builder(MyFriendActivity.this)
                .setHeight(R.dimen.line_height)
                .setColorResource(R.color.line_gray)
                .build();
        myfriendRecyclerview.addItemDecoration(decoration);

        //设置下拉刷新
        myfriendRecyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                adapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();
                getDatas();
            }
        });
        myfriendRecyclerview.refresh();
        //设置上拉加载更多
        myfriendRecyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (page < totalPage) {
                    page++;
                    getDatas();
                } else {
                    myfriendRecyclerview.setNoMore(true);
                }
            }
        });

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!TextUtils.isEmpty(nickStr)) {
                    isLook = false;
                    SPUtils.put(MyFriendActivity.this, Constant.IS_LOOK, isLook);
                } else {
                    isLook = true;
                    SPUtils.put(MyFriendActivity.this, Constant.IS_LOOK, isLook);
                }
                String id = adapter.getDataList().get(position).getUid();
                Intent i = new Intent(MyFriendActivity.this, FriendDetailActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });
    }

    private void getDatas() {
        String session = (String) SPUtils.get(this, Constant.MSESSION, "");
//        好友列表http://app.yasbao.com/Home/Api/gw?api=yasbao.api.member.friends&uid=1&apiKey=f40b1da52dece67017dbb0c7830e586e&p=0
//        uid = "1";
        String url = ConfigValue.APP_IP + "?api=yasbao.api.member.friends&uid=" + uid + "&apiKey=" + ConfigValue.API_KEY + "&p=" + page;
        new OkHttpUtils().getDatas(this,url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                FriendsBean bean = new Gson().fromJson(data, FriendsBean.class);
                if (TextUtils.equals(bean.getResult().trim(), "SUCESS")) {
                    totalPage = bean.getPageTotals();
                    REQUEST_COUNT = bean.getPageSize();
                    TOTAL_COUNTER = Integer.parseInt(bean.getTotals());
                    adapter.addAll(bean.getFriends());
                    lRecyclerViewAdapter.notifyDataSetChanged();
                    myfriendRecyclerview.refreshComplete(REQUEST_COUNT);
                } else if(TextUtils.equals("NOLOGIN", bean.getResult().trim())){
                    ToastUtils.toastForShort(MyFriendActivity.this, bean.getWorngMsg());
                    MyFriendActivity.this.finish();
                    startActivity(new Intent(MyFriendActivity.this, LoginActivity.class));
                }else {
                    myfriendRecyclerview.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    ToastUtils.toastForShort(MyFriendActivity.this, bean.getWorngMsg());
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                myfriendRecyclerview.setVisibility(View.GONE);
                tvGeneralGoodsNoNet.setVisibility(View.VISIBLE);
                ToastUtils.toastForLong(MyFriendActivity.this, getResources().getString(R.string.bad_net));
            }
        });
    }

    @OnClick({R.id.title_left_one_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                MyFriendActivity.this.finish();
                break;
        }
    }
}
