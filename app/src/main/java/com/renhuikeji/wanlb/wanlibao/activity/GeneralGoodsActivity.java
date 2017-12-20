package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.GeneralGoodAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.HomeRecyBean;
import com.renhuikeji.wanlb.wanlibao.bean.SearchGoodBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.MyThreadPool;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SearchHistorySQLite;
import com.renhuikeji.wanlb.wanlibao.utils.StringDealUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.PhoneInfoUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GeneralGoodsActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.general_shaixuan)
    TextView tv_shaixuan;            //筛选
    @BindView(R.id.general_text_xiaoliang)
    TextView txt_xiaoliang;          //销量
    @BindView(R.id.general_text_fanli)
    TextView txt_fanli;              //返利
    @BindView(R.id.general_text_jiage)
    TextView txt_jiage;            //价格
    @BindView(R.id.general_search)
    LinearLayout tv_search;             //分类
    @BindView(R.id.generalgood_button_cancle)
    TextView btn_cancle;                //筛选取消
    @BindView(R.id.generalgood_button_sure)
    TextView btn_reset;  //筛选重置
    @BindView(R.id.edittext_minprice)
    TextInputEditText edittextMinprice;  //筛选最低价
    @BindView(R.id.edittext_maxprice)
    TextInputEditText edittextMaxprice;  //筛选高价
    @BindView(R.id.generalgood_commit)
    TextView btn_ommit;   //筛选确定
    @BindView(R.id.generalgood_bottom_sheet)
    LinearLayout bottomSheet;  //筛选布局
    @BindView(R.id.edittext_min_bili)
    TextInputEditText edittextMinBili;
    @BindView(R.id.edittext_max_bili)
    TextInputEditText edittextMaxBili;
    @BindView(R.id.tv_fanbi_coupon)
    TextView tvFanbiCoupon;
    @BindView(R.id.fab_general_goods)
    FloatingActionButton fabGeneralGoods;
    @BindView(R.id.tv_general_goods_no_net)
    TextView tvGeneralGoodsNoNet;
    @BindView(R.id.title_right_two_btn)
    ImageView titleRightTwoBtn;

    private SQLiteDatabase db;
    private SearchHistorySQLite helper;
    private BottomSheetBehavior behavior;
    private String keyword;             //需要展示的普通商品的关键词
    private LRecyclerView recyclerView = null;
    private GeneralGoodAdapter adapter = null;
    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private int TOTAL_COUNTER = 20;     //商品总数
    private static int REQUEST_COUNT = 10;    //每次加载数量
    private static int mCurrentCounter = 0;   //当前商品位置
    private int sort = 0;               //排序方式
    private int page = 1;               //当前页数
    private Handler myHandler = null;
    private boolean isChoose = false;   //是否筛选
    private String minPrice;            //最低价
    private String maxPrice;            //最高价
    private String minRate;             //最低返利
    private String maxRate;             //最高返利
    private int searchType = 1;         //商品类型      普通：1    超级返：2   优惠券：3
    private String url;
    private SearchGoodBean searchGoodBean = null;
    private int totalPage = 1;          //总页数
    int pastVisiblesItems;
    private static final int HIDE_THRESHOLD = 400;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    private boolean isRefresh = true;   //加载下一页：false   改变筛选条件/第一次进入：true
    private boolean isFromSearch = false;//是否从搜索页面跳转过来
    private View emptyView;             //无数据界面
    private String cat;                 //分类ID
    private String uid;                 //用户uid
    private String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_goods);
        ButterKnife.bind(this);

        //实例化数据库SQLiteOpenHelper子类对象
        helper = new SearchHistorySQLite(GeneralGoodsActivity.this);

        uid = SPUtils.get(this, Constant.User_Uid, "").toString().trim();
        session = (String) SPUtils.get(this, Constant.MSESSION, "");

        Bundle bundle = getIntent().getBundleExtra("search");
        if (bundle != null) {
            isFromSearch = bundle.getBoolean("isFromSearch");
            keyword = bundle.getString("q", "");
            searchType = bundle.getInt("type", 1);
            isChoose = bundle.getBoolean("isChoose", false);
            cat = bundle.getString("cat");
            if (isChoose) {
                minPrice = bundle.getString("minPrice");
                maxPrice = bundle.getString("maxPrice");
                minRate = bundle.getString("minRate");
                maxRate = bundle.getString("maxRate");
            }
        }

        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case -1:
                        addData();
                        break;
                }
                super.handleMessage(msg);
            }
        };
        DialogUtils.showProgressDlg(this, "努力加载中....");//请求数据前开始刷新
        initTitle();
        getData();
        initShaiXuan();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getBundleExtra("search");
        if (bundle != null) {
            isFromSearch = bundle.getBoolean("isFromSearch");
            keyword = bundle.getString("q", "");
            tvMiddleTitle.setText(keyword);
            searchType = bundle.getInt("type", 1);
            isChoose = bundle.getBoolean("isChoose", false);
            cat = bundle.getString("cat");
            if (isChoose) {
                minPrice = bundle.getString("minPrice");
                maxPrice = bundle.getString("maxPrice");
                minRate = bundle.getString("minRate");
                maxRate = bundle.getString("maxRate");
            }
        }
        DialogUtils.showProgressDlg(this, "努力加载中....");//请求数据前开始刷新
        adapter.clear();
        getData();
    }

    public void getData() {
        /**
         * 获取网络数据
         */
        //searchType 1：普通   2：超级返  3：优惠券
        if (TextUtils.isEmpty(cat)) {
            url = ConfigValue.SEARCH + "&uid=" + uid + "&q=" + keyword + "&type=" + searchType +
                    "&sort=" + sort + "&p=" + page;
        } else {
            url = ConfigValue.SEARCH + "&uid=" + uid + "&q=" + keyword + "&type=" + searchType +
                    "&sort=" + sort + "&p=" + page + "&cat=" + cat;
        }

        if (isChoose) {             //筛选（最低价、最高价等）
            if (!TextUtils.isEmpty(minPrice)) {
                url = url + "&start_price=" + minPrice;
            }
            if (!TextUtils.isEmpty(maxPrice)) {
                url = url + "&end_price=" + maxPrice;
            }
            if (!TextUtils.isEmpty(minRate)) {
                if (searchType == 3) {
                    url = url + "&start_coupon=" + minRate;
                } else {
                    url = url + "&start_tk_rate=" + minRate;
                }
            }
            if (!TextUtils.isEmpty(maxRate)) {
                if (searchType == 3) {
                    url = url + "&end_coupon=" + maxRate;
                } else {
                    url = url + "&end_tk_rate=" + maxRate;
                }
            }
        }

        if (!NetworkManageUtil.checkNetworkAvailable(GeneralGoodsActivity.this)) {
            tvGeneralGoodsNoNet.setVisibility(View.VISIBLE);
            tvGeneralGoodsNoNet.setClickable(false);
            DialogUtils.stopProgressDlg();
            return;
        }
        Log.i("tag",url);
        new OkHttpUtils().getDatas(context, url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                Log.i("tag","-->"+data);
                searchGoodBean = new Gson().fromJson(data, SearchGoodBean.class);
                switch (searchGoodBean.getResult()) {
                    case "SUCESS":
                        if (isFromSearch) {
                            SPUtils.put(GeneralGoodsActivity.this, Constant.search_q, searchGoodBean.getPara().getQ());
                            //                            SPUtils.put(GeneralGoodsActivity.this, Constant.search_type, searchGoodBean.getPara().getType());
                        }
                        List<SearchGoodBean.GoodsArrBean> beans = searchGoodBean.getGoodsArr();
                        if (beans == null || beans.isEmpty()) {
                            DialogUtils.stopProgressDlg();
                            ToastUtils.toastForShort(GeneralGoodsActivity.this, "无数据");
                            recyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                            return;
                        }
                        TOTAL_COUNTER = searchGoodBean.getGoodsTotal();
                        REQUEST_COUNT = searchGoodBean.getGoodsArr().size();
                        totalPage = searchGoodBean.getTotalPages();
                        if (isRefresh) {
                            if (null != lRecyclerViewAdapter) {
                                adapter.clear();
                                lRecyclerViewAdapter.notifyDataSetChanged();
                            }
                            if (View.VISIBLE == fabGeneralGoods.getVisibility()) {
                                fabGeneralGoods.setVisibility(View.GONE);
                                controlsVisible = true;
                            }
                            initRecycler();
                            isRefresh = false;
                        } else {
                            addData();
                        }
                        break;
                    case "NOLOGIN":
                        GeneralGoodsActivity.this.finish();
                        ToastUtils.toastForShort(GeneralGoodsActivity.this, searchGoodBean.getWorngMsg());
                        startActivity(new Intent(GeneralGoodsActivity.this, LoginActivity.class));
                        break;
                    case "FAIL":
                        ToastUtils.toastForShort(GeneralGoodsActivity.this, searchGoodBean.getWorngMsg());
                        break;
                    case "SYSTEMWRONG":
                        ToastUtils.toastForShort(GeneralGoodsActivity.this, "系统错误");
                        break;
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);

                Log.i("tag",meg);
                DialogUtils.stopProgressDlg();
                // ToastUtils.toastForShort(GeneralGoodsActivity.this, meg);
                tvGeneralGoodsNoNet.setVisibility(View.VISIBLE);
                tvGeneralGoodsNoNet.setText("数据获取失败，点击刷新");
                tvGeneralGoodsNoNet.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvGeneralGoodsNoNet.setClickable(true);
                if (adapter == null) {
                    return;
                }
                if (lRecyclerViewAdapter == null) {
                    return;
                }
                adapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();

            }
        });
    }

    /**
     * 创建筛选框
     */
    private void initShaiXuan() {
        //获取到Bottom Sheet对象
        behavior = BottomSheetBehavior.from(bottomSheet);
        //默认设置为隐藏
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setPeekHeight(PhoneInfoUtil.getInstance().getDisplayHeight(this));
    }

    /**
     * 初始化recyclerview
     */
    GridLayoutManager manager;

    private void initRecycler() {
        //        manager = new GridLayoutManager(GeneralGoodsActivity.this, 2);
        //        recyclerView = (LRecyclerView) findViewById(R.id.generalgood_recyclerview);
        //        if (0 == TOTAL_COUNTER) {
        //            recyclerView.setVisibility(View.GONE);
        //            emptyView.setVisibility(View.VISIBLE);
        //            DialogUtils.stopProgressDlg();
        //        }
        manager = new GridLayoutManager(GeneralGoodsActivity.this, raw);
        recyclerView.setLayoutManager(manager);
        adapter = new GeneralGoodAdapter(GeneralGoodsActivity.this);
        adapter.setLayoutType(raw);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);

        //设置lrecyclerview
        GridItemDecoration divider = new GridItemDecoration.Builder(GeneralGoodsActivity.this)
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
                //mCurrentCounter = 0;
                page = 1;
                adapter.clear();
                getData();
                //requestData();
            }
        });
        //设置上拉加载更多
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (page < totalPage) {
                    page++;
                    getData();
                } else {
                    recyclerView.setNoMore(true);
                }
            }
        });

//        recyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
//            @Override
//            public void onScrollUp() {
//
//            }
//
//            @Override
//            public void onScrollDown() {
//
//            }
//
//            @Override
//            public void onScrolled(int distanceX, int distanceY) {
//
//
//            }
//
//            @Override
//            public void onScrollStateChanged(int state) {
//                //如果是惯性滑动，停止加载图片
//                if (state == RecyclerView.SCROLL_STATE_SETTLING) {
//                    Glide.with(GeneralGoodsActivity.this).pauseRequests();
//                } else {
//                    //停止和手指滑动状态，加载图片
//                    Glide.with(GeneralGoodsActivity.this).resumeRequests();
//                }
//            }
//        });


        //设置头部加载颜色
        recyclerView.setHeaderViewColor(R.color.colorAccent, R.color.fuzhu_black, R.color.colorWhite);
        //设置底部加载颜色
        recyclerView.setFooterViewColor(R.color.colorAccent, R.color.fuzhu_black, R.color.colorWhite);
        //设置底部加载文字提示
        recyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        DialogUtils.stopProgressDlg();//
        //初始化数据
        recyclerView.refresh();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                pastVisiblesItems = manager.findFirstVisibleItemPosition();

                if (scrolledDistance > HIDE_THRESHOLD) {
                    fabGeneralGoods.setVisibility(View.VISIBLE);
                    controlsVisible = false;
                    scrolledDistance = 0;
                } else if (pastVisiblesItems == 1 && !controlsVisible && dy < 0) {
                    fabGeneralGoods.setVisibility(View.GONE);
                    controlsVisible = true;
                    scrolledDistance = 0;
                }
                if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                    scrolledDistance += dy;
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        if (TextUtils.isEmpty(keyword)) {
            switch (searchType) {
                case 1:
                    tvMiddleTitle.setText("搜淘宝拿返利");
                    break;
                case 2:
                    tvMiddleTitle.setText("超级返");
                    break;
                case 3:
                    tvMiddleTitle.setText("优惠券");
                    break;
            }
        } else {
            tvMiddleTitle.setText(keyword);
        }
        if (searchType == 3) {
            txt_fanli.setText("优惠券");
            tvFanbiCoupon.setText("优惠券(金额)");
            edittextMaxBili.setHint("最高金额");
            edittextMinBili.setHint("最低金额");
        }

        titleLeftOneBtn.setVisibility(View.VISIBLE);
        titleRightTwoBtn.setVisibility(View.VISIBLE);
        titleRightTwoBtn.setImageResource(R.mipmap.list);
        titleRightOneBtn.setVisibility(View.VISIBLE);
        emptyView = findViewById(R.id.empty_general_goods);


        recyclerView = (LRecyclerView) findViewById(R.id.generalgood_recyclerview);
        if (0 == TOTAL_COUNTER) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            DialogUtils.stopProgressDlg();
        }
    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_btn, R.id.general_shaixuan,
            R.id.general_text_xiaoliang, R.id.general_text_fanli, R.id.general_text_jiage,
            R.id.general_search, R.id.generalgood_button_cancle, R.id.generalgood_button_sure,
            R.id.generalgood_commit, R.id.fab_general_goods, R.id.tv_general_goods_no_net
            , R.id.title_right_two_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                mCurrentCounter = 0;
                if (!TextUtils.isEmpty(keyword)) {
                    //SPUtils.put(GeneralGoodsActivity.this, Constant.HISTORY_STR, tvMiddleTitle.getText().toString());
                    insertData(keyword);
                    Log.d("ccc", "onViewClicked: " + tvMiddleTitle.getText().toString());
                    SPUtils.put(GeneralGoodsActivity.this, Constant.BACKWHERE, "general");
                }
                finish();
                break;
            case R.id.title_right_one_btn:      //搜索
                mCurrentCounter = 0;
                if (!TextUtils.isEmpty(keyword)) {
                    // SPUtils.put(GeneralGoodsActivity.this, Constant.HISTORY_STR, keyword);
                    insertData(keyword);
                    SPUtils.put(GeneralGoodsActivity.this, Constant.BACKWHERE, "general");
                }
                startActivity(new Intent(GeneralGoodsActivity.this, TaoBaoSearchActivity.class));
                break;
            case R.id.general_shaixuan:         //筛选
                showBottomSheet(behavior);
                break;
            case R.id.general_text_xiaoliang:   //按销量排序
                titleRightTwoBtn.setImageResource(R.mipmap.list);
                isRefresh = true;
                mCurrentCounter = 0;
                page = 1;
                sort = 1;
                DialogUtils.showProgressDlg(this, "努力加载中....");//请求数据前开始刷新
                getData();
                break;
            case R.id.general_text_fanli://按返利排序
                titleRightTwoBtn.setImageResource(R.mipmap.list);
                isRefresh = true;
                mCurrentCounter = 0;
                page = 1;
                if (searchType == 3) {
                    sort = 4;
                } else {
                    sort = 2;
                }
                DialogUtils.showProgressDlg(this, "努力加载中....");//请求数据前开始刷新
                getData();
                break;
            case R.id.general_text_jiage://按价格排序
                titleRightTwoBtn.setImageResource(R.mipmap.list);
                isRefresh = true;
                mCurrentCounter = 0;
                page = 1;
                sort = 3;
                DialogUtils.showProgressDlg(this, "努力加载中....");//请求数据前开始刷新
                getData();
                break;
            case R.id.general_search:                //分类
                if (!TextUtils.isEmpty(keyword)) {
                    // SPUtils.put(GeneralGoodsActivity.this, Constant.HISTORY_STR, keyword);
                    insertData(keyword);
                    SPUtils.put(GeneralGoodsActivity.this, Constant.BACKWHERE, "general");
                }

                Intent intent = new Intent(GeneralGoodsActivity.this, ClassifyActivity.class);
                intent.putExtra("type", searchType);
                intent.putExtra("q", keyword);
                startActivity(intent);
                break;
            case R.id.generalgood_button_cancle:         //筛选取消
                clearAllFocus();
                closeKeyboard();
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.generalgood_button_sure:          //筛选重置
                clearAllFocus();
                if (!edittextMinprice.hasFocus()) {
                    edittextMinprice.requestFocus();
                }
                break;
            case R.id.generalgood_commit:               //提交筛选
                commitChoose();
                clearAllFocus();
                closeKeyboard();
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.fab_general_goods:                //点击滑到顶部
                recyclerView.smoothScrollToPosition(0);
                fabGeneralGoods.setVisibility(View.GONE);
                controlsVisible = true;
                break;
            case R.id.tv_general_goods_no_net:          //点击无网络重新加载
                isRefresh = true;
                DialogUtils.showProgressDlg(this, "努力加载中....");
                getData();
                tvGeneralGoodsNoNet.setVisibility(View.GONE);
                break;
            case R.id.title_right_two_btn:          //点击无网络重新加载
                switch (manager.getSpanCount()) {
                    case 1:
                        adapter.setLayoutType(Constant.LAYOUT_Grid);
                        titleRightTwoBtn.setImageResource(R.mipmap.list);
                        manager.setSpanCount(Constant.DOUBLE);
                        raw=Constant.DOUBLE;
                        break;
                    case 2:
                        adapter.setLayoutType(Constant.LAYOUT_LIST);
                        titleRightTwoBtn.setImageResource(R.mipmap.cascades);
                        manager.setSpanCount(Constant.SINGLE);
                        raw=Constant.SINGLE;
                        break;
                }
                adapter.notifyItemRangeChanged(0, lRecyclerViewAdapter.getItemCount());

                break;
        }
    }
    private int raw=Constant.DOUBLE;

    /**
     * 提交筛选
     */
    private void commitChoose() {
        //先传递数据
        minPrice = edittextMinprice.getText().toString().trim();
        maxPrice = edittextMaxprice.getText().toString().trim();
        minRate = edittextMinBili.getText().toString().trim();
        maxRate = edittextMaxBili.getText().toString().trim();
        //先传递数据
        if (TextUtils.isEmpty(minPrice) && TextUtils.isEmpty(maxPrice) && TextUtils.isEmpty(minRate)
                && TextUtils.isEmpty(maxRate)) {
            ToastUtils.toastForShort(GeneralGoodsActivity.this, "筛选条件不可均为空");
        } else if (!TextUtils.isEmpty(minPrice) && !TextUtils.isEmpty(maxPrice) &&
                Integer.parseInt(minPrice) > Integer.parseInt(maxPrice)) {
            ToastUtils.toastForShort(GeneralGoodsActivity.this, "筛选最低价格不可高于最高价格");
        } else if (!TextUtils.isEmpty(minRate) && !TextUtils.isEmpty(maxRate) &&
                Integer.parseInt(minRate) > Integer.parseInt(maxRate)) {
            ToastUtils.toastForShort(GeneralGoodsActivity.this, "最低返利比例不可高于最高返利比例");
        } else {
            isChoose = true;
            isRefresh = true;
            DialogUtils.showProgressDlg(this, "努力加载中....");//请求数据前开始刷新
            getData();
        }
    }

    //模拟请求数据
    private void requestData() {
        MyThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(-1);
            }
        });
    }

    /**
     * 添加数据
     */
    private void addData() {
        int currentSize = adapter.getItemCount();
        ArrayList<HomeRecyBean> newList = new ArrayList<>();
        for (int i = 0; i < REQUEST_COUNT; i++) {
            if (newList.size() + currentSize >= TOTAL_COUNTER) { //超出总数据
                break;
            }
            HomeRecyBean item = new HomeRecyBean();
            SearchGoodBean.GoodsArrBean goodsBean = searchGoodBean.getGoodsArr().get(i);
            item.isSeller = goodsBean.getIsSeller();
            item.id = goodsBean.getNum_iid();
            item.imgUrl = goodsBean.getPict_url();
            item.address = goodsBean.getItemloc();
            item.click_url = goodsBean.getClick_url();
            item.isSeller = goodsBean.getIsSeller();
            //item.sales = goodsBean.getVolume();
            item.goodIntroduce = goodsBean.getTitle();
            item.oldprices = goodsBean.getReserve_price();
            item.user_type = goodsBean.getUser_type();
            item.nick = goodsBean.getNick();

            item.newprices = goodsBean.getZk_final_price();
            if (!TextUtils.isEmpty(goodsBean.getCoupon()) && !TextUtils.isEmpty(goodsBean.getClick_url()) &&
                    Double.parseDouble(goodsBean.getCoupon()) > 0 && "1".equals(goodsBean.getIsCoupon())) {
                item.type = 3;//优惠券
                item.click_url = goodsBean.getCoupon_url();
                item.quan_num = goodsBean.getCoupon();
                item.sales = goodsBean.getVolume();
                if (TextUtils.isEmpty(goodsBean.getZk_final_price()) || TextUtils.isEmpty(goodsBean.getTk_rate())) {
                    goodsBean.setZk_final_price("0.00");
                    goodsBean.setCoupon("0.00");
                } else if (TextUtils.isEmpty(searchGoodBean.getSettleRate() + "")) {
                    searchGoodBean.setSettleRate(1);
                }
                item.quan_hou_num = StringDealUtil.checkDecimal(Double.parseDouble(goodsBean.getZk_final_price()) -
                        Double.parseDouble(goodsBean.getCoupon()) + "");
                item.valid_time = goodsBean.getCoupon_end_date();
                item.return_num=StringDealUtil.checkDecimal((Double.parseDouble(goodsBean.getZk_final_price())-Double.parseDouble(goodsBean.getCoupon()))
                        *(searchGoodBean.getCouponSettleRate())*(Double.parseDouble(goodsBean.getTk_rate()))/100+"");
            } else if (!TextUtils.isEmpty(goodsBean.getTk_rate()) && Double.parseDouble(goodsBean.getTk_rate()) > 0) {
                item.type = 2; //返利
                if (TextUtils.isEmpty(searchGoodBean.getSettleRate() + "")) {
                    searchGoodBean.setSettleRate(1);
                }
                item.fan_num = StringDealUtil.checkDecimal(Double.parseDouble(goodsBean.getZk_final_price())
                        * Double.parseDouble(goodsBean.getTk_rate()) * searchGoodBean.getSettleRate() / 100 + "");
                item.fan_bi = StringDealUtil.checkDecimal(Double.parseDouble(goodsBean.getTk_rate()) *
                        (searchGoodBean.getSettleRate()) + "");
                if (TextUtils.isEmpty(goodsBean.getZk_final_price())) {
                    item.fan_num = "0.00";
                }
                if (TextUtils.isEmpty(goodsBean.getTk_rate())) {
                    item.fan_num = "0.00";
                    item.fan_bi = "0.00";
                }
                item.valid_time = goodsBean.getEvent_end_time();
                item.sales = goodsBean.getVolume();
            } else {
                item.quan_num = goodsBean.getCoupon();
                item.type = 1; //普通商品
                item.sales = goodsBean.getVolume();
            }
           /* switch (searchType) {
                case 1:                 //普通返利
                    item.quan_num = goodsBean.getCoupon();
                    item.type = 1; //普通商品
                    break;
                case 2:                 //超级返利
                    item.type = 2; //返利
                    if (TextUtils.isEmpty(searchGoodBean.getSettleRate() + "")) {
                        searchGoodBean.setSettleRate(1);
                    }
                    item.fan_num = StringDealUtil.checkDecimal(Double.parseDouble(goodsBean.getZk_final_price())
                            * Double.parseDouble(goodsBean.getTk_rate()) * searchGoodBean.getSettleRate() / 100 + "");
                    item.fan_bi = StringDealUtil.checkDecimal(Double.parseDouble(goodsBean.getTk_rate()) *
                            (searchGoodBean.getSettleRate()) + "");
                    if (TextUtils.isEmpty(goodsBean.getZk_final_price())) {
                        item.fan_num = "0.00";
                    }
                    if (TextUtils.isEmpty(goodsBean.getTk_rate())) {
                        item.fan_num = "0.00";
                        item.fan_bi = "0.00";
                    }
                    item.valid_time = goodsBean.getEvent_end_time();
                    break;
                case 3:                 //优惠券
                    item.type = 3;
                    item.click_url = goodsBean.getCoupon_url();
                    item.quan_num = goodsBean.getCoupon();
                    if (TextUtils.isEmpty(goodsBean.getZk_final_price()) || TextUtils.isEmpty(goodsBean.getTk_rate())) {
                        goodsBean.setZk_final_price("0.00");
                        goodsBean.setCoupon("0.00");
                    } else if (TextUtils.isEmpty(searchGoodBean.getSettleRate() + "")) {
                        searchGoodBean.setSettleRate(1);
                    }
                    item.quan_hou_num = StringDealUtil.checkDecimal(Double.parseDouble(goodsBean.getZk_final_price()) -
                            Double.parseDouble(goodsBean.getCoupon()) + "");
                    item.valid_time = goodsBean.getCoupon_end_date();
                    break;
            }*/
            newList.add(item);
        }
        DialogUtils.stopProgressDlg();
        adapter.addAll(newList);
        mCurrentCounter += newList.size();
        recyclerView.refreshComplete(REQUEST_COUNT);
    }

    /**
     * 是否显示筛选框
     */
    private void showBottomSheet(BottomSheetBehavior behavior) {
        if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    /**
     * 清除焦点
     */
    private void clearAllFocus() {
        edittextMinprice.setText("");
        edittextMaxprice.setText("");
        edittextMaxBili.setText("");
        edittextMinBili.setText("");
        edittextMaxprice.clearFocus();
        edittextMinprice.clearFocus();
        edittextMaxBili.clearFocus();
        edittextMinBili.clearFocus();
    }

    /**
     * 关闭软件盘
     */
    private void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 回退键和返回按钮效果一样
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCurrentCounter = 0;
        if (!TextUtils.isEmpty(keyword)) {
            insertData(keyword);
            SPUtils.put(GeneralGoodsActivity.this, Constant.BACKWHERE, "general");
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AlibcTradeSDK.destory();
        //该界面图片较多，每次退出时清除磁盘缓存，内存缓存清除是在baseactivity中
        MyThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                Glide.get(GeneralGoodsActivity.this).clearDiskCache();
            }
        });

    }

    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        if (TextUtils.equals("优惠券", tvMiddleTitle.getText().toString()) || TextUtils.equals("超级返", tvMiddleTitle.getText().toString())) {
            // Log.d("ccc", "nonono ");
        } else if (!hasData(tempName)) {
            db.execSQL("insert into records(name) values('" + tempName + "')");
        } else {
            //先删除在添加
            Cursor cursor = helper.getReadableDatabase().rawQuery(
                    "select id as _id,name from records where name =?", new String[]{tempName});
            if (cursor.moveToNext()) {
                db.delete("records", "name=?", new String[]{tempName});
                db.execSQL("insert into records(name) values('" + tempName + "')");
            }
        }
        db.close();
    }

    /*检查数据库中是否已经有该条记录*/
    private boolean hasData(String tempName) {
        //从Record这个表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }
    //----------------------------------------------------------------------------
}
