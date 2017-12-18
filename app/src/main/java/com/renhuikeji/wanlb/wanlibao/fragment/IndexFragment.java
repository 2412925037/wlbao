package com.renhuikeji.wanlb.wanlibao.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.view.CommonHeader;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.ClassifyActivity;
import com.renhuikeji.wanlb.wanlibao.activity.GeneralGoodsActivity;
import com.renhuikeji.wanlb.wanlibao.activity.HomeGridActivity;
import com.renhuikeji.wanlb.wanlibao.activity.LoginActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MainActivity;
import com.renhuikeji.wanlb.wanlibao.activity.RebateShopActivity;
import com.renhuikeji.wanlb.wanlibao.activity.SignInActivity;
import com.renhuikeji.wanlb.wanlibao.activity.TaoBaoSearchActivity;
import com.renhuikeji.wanlb.wanlibao.adapter.HomeFragGridAdapter;
import com.renhuikeji.wanlb.wanlibao.adapter.HomeRecyAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.HomeRecyBean;
import com.renhuikeji.wanlb.wanlibao.bean.IndexGoodBean;
import com.renhuikeji.wanlb.wanlibao.bean.MallListBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.config.Contants;
import com.renhuikeji.wanlb.wanlibao.utils.AliUtils.AliUtils;
import com.renhuikeji.wanlb.wanlibao.utils.CheckUtil;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.MyThreadPool;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.StatusBarUtil;
import com.renhuikeji.wanlb.wanlibao.utils.StringDealUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoaderForBanner;
import com.tencent.bugly.beta.Beta;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页
 */

public class IndexFragment extends Fragment implements TabLayout.OnTabSelectedListener, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.index_search_rl)
    RelativeLayout indexSearchRl;


    Unbinder unbinder;
    private View view;
    private HomeFragGridAdapter gridAdapter = null;
    private int[] images = {R.mipmap.icon_chao, R.mipmap.icon_quan, R.mipmap.icon_ju,
            R.mipmap.icon_tiantian, R.mipmap.icon_tao, R.mipmap.icon_jingdong, R.mipmap.icon_yihaodian,
            R.mipmap.icon_fanli, R.mipmap.icon_xinshou, R.mipmap.icon_qiandao};
    private String[] text = {"超级返利", "优惠券", "聚划算", "天天特价", "淘宝返利", "京东返利"
            , "超市生鲜", "商城返利", "新手上路", "签到领钱"};
    private FloatingActionButton floatButton = null;
    private LRecyclerView recyclerView = null;
    private int TOTAL_COUNTER = 10;
    private static final int REQUEST_COUNT = 10;
    private static int mCurrentCounter;
    private HomeRecyAdapter recyAdapter = null;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private Handler myHandler = null;
    private Banner banner = null;
    private GridView gridItem = null;
    private TabLayout tablayout = null;
    private LinearLayout searchView = null;
    private IndexGoodBean indexGoodBean = null;
    int pastVisiblesItems;
    private static final int HIDE_THRESHOLD = 912;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    private TextView tvNoNet;
    private View empty;
    private String uid;
    private List<IndexGoodBean.CatArrBean> title = new ArrayList<>();
    private String session;
    private RelativeLayout main_rel;
    private LinearLayoutManager manager;
    //页面滑动距离
    private int dis = 0;
    //刷新
    private boolean isToLogin=false;
    RelativeLayout main;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_index, container,false);
        unbinder = ButterKnife.bind(this, view);

        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);

        main = (RelativeLayout) view.findViewById(R.id.main);

        MainActivity context = (MainActivity) getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            main.setPadding(0, context.getStatusHeight(), 0, 0);

        //StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), banner);
        uid = (String) SPUtils.get(getActivity(), Constant.User_Uid, "");
        session = (String) SPUtils.get(getActivity(), Constant.MSESSION, "");

        Log.i("tagsession",uid + session);

        mCurrentCounter = 0;
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


        initViews();
        if (NetworkManageUtil.checkNetworkAvailable(getActivity())) {
            DialogUtils.showProgressDlg(getActivity(), "拼命加载中....");
            getData();
        } else {
            main_rel.setVisibility(View.GONE);
            tvNoNet.setVisibility(View.VISIBLE);
            tvNoNet.setClickable(true);
            DialogUtils.stopProgressDlg();
        }

        //bugly检查升级
        if (CheckUtil.isLogin(getActivity())) {
            Beta.checkUpgrade(false, false);
        }


        return view;
    }

    private void initViews() {
        main_rel = (RelativeLayout) view.findViewById(R.id.main_rel);
        tvNoNet = (TextView) view.findViewById(R.id.tv_index_no_net);
        floatButton = (FloatingActionButton) view.findViewById(R.id.floating_btn_main);
        searchView = (LinearLayout) view.findViewById(R.id.main_search_ll);

        recyclerView = (LRecyclerView) view.findViewById(R.id.main_recycler);
        empty = view.findViewById(R.id.index_empty);


        tvNoNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tablayout != null) {
                    tablayout.setVisibility(View.VISIBLE);
                }
                recyclerView.setVisibility(View.VISIBLE);
                tvNoNet.setVisibility(View.GONE);
                if (NetworkManageUtil.checkNetworkAvailable(getActivity())) {
                    DialogUtils.showProgressDlg(getActivity(), "拼命加载中....");
                    isToLogin=true;
                    getData();
                } else {
                    main_rel.setVisibility(View.GONE);
                    tvNoNet.setVisibility(View.VISIBLE);
                    DialogUtils.stopProgressDlg();
                }
            }
        });
    }

    private void initTopView() {
        //悬浮按钮
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);

                floatButton.setVisibility(View.GONE);
                setSearchBarColor(R.color.transparency_title);
                //manager.scrollToPositionWithOffset(0, 0);
                //manager.setStackFromEnd(true);


            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TaoBaoSearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initTabView() {
        //添加tab
        for (int i = 0; i < indexGoodBean.getCatArr().size(); i++) {
            if ("1".equals(indexGoodBean.getCatArr().get(i).getLevel())) {
                title.add(indexGoodBean.getCatArr().get(i));
                tablayout.addTab(tablayout.newTab().setText(indexGoodBean.getCatArr().get(i).getTitle()));
            }
        }
        if (null != tablayout && null != tablayout.getTabAt(0)) {
            tablayout.getTabAt(0).select();
        }
        tablayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        DialogUtils.stopProgressDlg();
        if (null != tablayout && null != tablayout.getTabAt(0)) {
            tablayout.getTabAt(0).select();
        }
    }

    private void initRecycler() {
        main_rel.setVisibility(View.VISIBLE);
        tvNoNet.setVisibility(View.GONE);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyAdapter = new HomeRecyAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(recyAdapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);

        //设置lrecyclerview
        final DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.line_height)
                .setColorResource(R.color.line_gray)
                .build();
        recyclerView.addItemDecoration(divider);
        recyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        recyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        //添加头部
        if (NetworkManageUtil.checkNetworkAvailable(getActivity())) {
            initBanner();
            //StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), banner);
        }


        //设置上拉刷新
        recyclerView.setOnRefreshListener(this);
        //设置上拉加载更多
        recyclerView.setOnLoadMoreListener(this);
        //如果滑动状态glide不加载
        recyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {


            }

            @Override
            public void onScrollStateChanged(int state) {
                if (state == RecyclerView.SCROLL_STATE_SETTLING) {
                    Glide.with(getActivity()).pauseRequests();
                } else {
                    Glide.with(getActivity()).resumeRequests();
                }
            }
        });
        //设置头部加载颜色
        recyclerView.setHeaderViewColor(R.color.colorAccent, R.color.fuzhu_black, R.color.colorWhite);
        //设置底部加载颜色
        recyclerView.setFooterViewColor(R.color.colorAccent, R.color.fuzhu_black, R.color.colorWhite);
        //设置底部加载文字提示
        recyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                dis += dy;
                if (dis < 0) {
                    dis = 0;
                }
                pastVisiblesItems = manager.findFirstVisibleItemPosition();
                if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                    scrolledDistance += dy;
                }
                if (scrolledDistance > HIDE_THRESHOLD) {
                    floatButton.setVisibility(View.VISIBLE);
                    controlsVisible = false;
                    scrolledDistance = 0;

                } else if (pastVisiblesItems == 1 && !controlsVisible && dy < 0) {
                    floatButton.setVisibility(View.GONE);
                    controlsVisible = true;
                    scrolledDistance = 0;

                }
                if (dis > indexSearchRl.getHeight()) {
                    setSearchBarColor(R.color.all_pink);
                    main.setBackgroundColor(getResources().getColor(R.color.all_pink));
                    //StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.all_pink), 55);
                }
                if (dy < 0 && pastVisiblesItems == 1 && dis < indexSearchRl.getHeight()) {
                    setSearchBarColor(R.color.transparency_title);
                    main.setBackgroundColor(getResources().getColor(R.color.transparency_title));
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //初始化数据
        addData();
    }

    //创建header
    private void initBanner() {
        CommonHeader header = new CommonHeader(getActivity(), R.layout.groupon_header);
        tablayout = (TabLayout) header.findViewById(R.id.order_tablayout);

        if (NetworkManageUtil.checkNetworkAvailable(getActivity())) {
            initTabView();
        }
        banner = (Banner) header.findViewById(R.id.groupon_header_banner);
        gridItem = (GridView) header.findViewById(R.id.grid_item);
        List<String> pics = new ArrayList<>();
        if (null == indexGoodBean.getSalesArr() || 0 == indexGoodBean.getSalesArr().size()) {
            ToastUtils.toastForShort(getActivity(), "无推荐活动");
        } else {
            for (int i = 0; i < indexGoodBean.getSalesArr().size(); i++) {
                pics.add(indexGoodBean.getSalesArr().get(i).getPict_url());
            }
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    AliUtils.showUrl(getActivity(), indexGoodBean.getSalesArr().get(position).getClick_url());
                }
            });
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setImageLoader(new GlideImageLoaderForBanner());
            banner.setImages(pics);
            banner.start();
        }

        gridAdapter = new HomeFragGridAdapter(getActivity(), images, text);
        gridItem.setAdapter(gridAdapter);
        gridItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int poisiton, long l) {
                final int mPosition = poisiton;
                final Intent i = new Intent();
                i.setClass(getActivity(), HomeGridActivity.class);
                if (null == indexGoodBean.getMallArr()) {
                    String mallUrl = ConfigValue.GRID_DO + "&uid=" + uid;
                    new OkHttpUtils().getDatas(getActivity(), mallUrl, session, new OkHttpUtils.HttpCallBack() {
                        @Override
                        public void onSusscess(String data) {
                            MallListBean mallListBean = new Gson().fromJson(data, MallListBean.class);
                            switch (mallListBean.getResult()) {
                                case "SUCESS":
                                    if (null == mallListBean.getMallArr()) {
                                        ToastUtils.toastForShort(getActivity(), "未获取到商城链接");
                                    } else {
                                        switch (mPosition) {
                                            case 0:
                                                Intent superRebateIntent = new Intent(getActivity(), GeneralGoodsActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("type", 2);
                                                superRebateIntent.putExtra("search", bundle);
                                                startActivity(superRebateIntent);
                                                break;
                                            case 1:
                                                Intent couponIntent = new Intent(getActivity(), GeneralGoodsActivity.class);
                                                Bundle bundle2 = new Bundle();
                                                bundle2.putInt("type", 3);
                                                couponIntent.putExtra("search", bundle2);
                                                startActivity(couponIntent);
                                                break;
                                            case 2:
                                                i.putExtra("param", "juhuasuan");
                                                i.putExtra("url", mallListBean.getMallArr().getJiu());
                                                startActivity(i);
                                                break;
                                            case 3:
                                                i.putExtra("param", "te");
                                                i.putExtra("url", mallListBean.getMallArr().getTe());
                                                startActivity(i);
                                                break;
                                            case 4:
                                                startActivity(new Intent(getActivity(), TaoBaoSearchActivity.class));
                                                break;
                                            case 5:
                                                i.putExtra("param", "jd");
                                                i.putExtra("url", mallListBean.getMallArr().getJd());
                                                startActivity(i);
                                                break;
                                            case 6:
                                                i.putExtra("param", "yhd");
                                                i.putExtra("url", mallListBean.getMallArr().getYhd());
                                                startActivity(i);
                                                break;
                                            case 7:
                                                Intent rebateShop = new Intent(getActivity(), RebateShopActivity.class);
                                                rebateShop.putExtra("mall_class_style", "mallListBean");
                                                rebateShop.putExtra("mallArr", mallListBean.getMallArr());
                                                startActivity(rebateShop);
                                                break;
                                            case 8:
                                                i.putExtra("param", "xssl");
                                                i.putExtra("url", ConfigValue.NEW_USER);
                                                startActivity(i);
                                                break;
                                            case 9:
                                               /* i.putExtra("param", "yhd");
                                                i.putExtra("url", mallListBean.getMallArr().getYhd());
                                                startActivity(i);*/
                                                startActivity(new Intent(getActivity(), SignInActivity.class));
                                                break;
                                        }
                                    }
                                    break;
                                case "NOLOGIN":
                                    ToastUtils.toastForShort(getActivity(), mallListBean.getWorngMsg());
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                    break;
                                case "FAIL":
                                    ToastUtils.toastForShort(getActivity(), mallListBean.getWorngMsg());
                                    break;
                            }
                        }

                        @Override
                        public void onError(String meg) {
                            super.onError(meg);
                            ToastUtils.toastForShort(getActivity(), meg);
                        }
                    });
                } else {
                    switch (poisiton) {
                        case 0:
                            Intent superRebateIntent = new Intent(getActivity(), GeneralGoodsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("type", 2);
                            superRebateIntent.putExtra("search", bundle);
                            startActivity(superRebateIntent);
                            break;
                        case 1:
                            Intent couponIntent = new Intent(getActivity(), GeneralGoodsActivity.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("type", 3);
                            couponIntent.putExtra("search", bundle2);
                            startActivity(couponIntent);
                            break;
                        case 2:
                            i.putExtra("param", "juhuasuan");
                            i.putExtra("url", indexGoodBean.getMallArr().getJiu());
                            startActivity(i);
                            break;
                        case 3:
                            i.putExtra("param", "te");
                            i.putExtra("url", indexGoodBean.getMallArr().getTe());
                            startActivity(i);
                            break;
                        case 4:
                            startActivity(new Intent(getActivity(), TaoBaoSearchActivity.class));
                            break;
                        case 5:
                            i.putExtra("param", "jd");
                            i.putExtra("url", indexGoodBean.getMallArr().getJd());
                            startActivity(i);
                            break;
                        case 6:
                            i.putExtra("param", "yhd");
                            i.putExtra("url", indexGoodBean.getMallArr().getYhd());
                            startActivity(i);
                            break;
                        case 7:
                            Intent rebateShop = new Intent(getActivity(), RebateShopActivity.class);
                            rebateShop.putExtra("mall_class_style", "indexGoodBean");
                            rebateShop.putExtra("mallArr", indexGoodBean.getMallArr());
                            startActivity(rebateShop);
                            break;
                        case 8:
                            i.putExtra("param", "xssl");
                            i.putExtra("url", ConfigValue.NEW_USER);
                            startActivity(i);
                            break;
                        case 9:
//                            i.putExtra("param", "yhd");
//                            i.putExtra("url", indexGoodBean.getMallArr().getYhd());
//                            startActivity(i);
                            startActivity(new Intent(getActivity(), SignInActivity.class));
                            break;
                    }
                }
            }
        });

        mLRecyclerViewAdapter.addHeaderView(header);
    }

    //-------------------------------------创建tablayout---------------------------------
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        if (position == 0) {//这是首页
            // ToastUtils.toastForShort(getActivity(), "首页");
        } else {//跳转到其他activity
            int choosePosition = tablayout.getSelectedTabPosition();
            Intent intent = new Intent(getActivity(), ClassifyActivity.class);
            intent.putExtra("cid", title.get(choosePosition).getCid());
            intent.putExtra("type", 1);
            startActivity(intent);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //-------------------------------------tablayout---------------------------------


    public void getData() {

        String url = ConfigValue.INDEX + "&uid=" + uid;

        OkHttpUtils.getInstance().getDatas(getActivity(), url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {

                Logger.i(OkHttpUtils.decodeUnicode(data));


                DialogUtils.stopProgressDlg();


                indexGoodBean = new Gson().fromJson(data, IndexGoodBean.class);
                switch (indexGoodBean.getResult()) {
                    case "SUCESS":
                        TOTAL_COUNTER = indexGoodBean.getGoodsArr().size();
                        if (0 == TOTAL_COUNTER) {
                            tablayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                        } else {
                            initRecycler();
                            initTopView();
                        }
                        break;
                    case "NOLOGIN":
                        ToastUtils.toastForShort(getActivity(), indexGoodBean.getWorngMsg());
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        break;
                    case "FAIL":
                        ToastUtils.toastForShort(getActivity(), indexGoodBean.getWorngMsg());
                        break;
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                if(isToLogin){
                   startActivity(new Intent(getActivity(),LoginActivity.class));
                }else{
                    if (tablayout != null) {
                        tablayout.setVisibility(View.GONE);
                    }
                    recyclerView.setVisibility(View.GONE);
                    tvNoNet.setVisibility(View.VISIBLE);
                    tvNoNet.setText("数据获取失败，点击刷新");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tvNoNet.setTextColor(getContext().getColor(R.color.colorPrimaryDark));
                    } else {
                        tvNoNet.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                }
                DialogUtils.stopProgressDlg();
            }
        });
    }

    /**
     * 模拟添加数据
     */
    private void addData() {
        final ArrayList<HomeRecyBean> newList = new ArrayList<>();

        for (int i = 0; i < REQUEST_COUNT; i++) {
            HomeRecyBean item = new HomeRecyBean();
            if (i + mCurrentCounter >= TOTAL_COUNTER) { //超出总数据
                break;
            }
            IndexGoodBean.GoodsArrBean bean = indexGoodBean.getGoodsArr().get(mCurrentCounter + i);
            item.id = bean.getNum_iid();
            item.imgUrl = bean.getPict_url();
            item.isSeller = bean.getIsSeller();
            item.quan_num = bean.getCoupon();
            item.goodIntroduce = bean.getTitle();
            item.newprices = bean.getZk_final_price();
            item.oldprices = bean.getReserve_price();
            item.address = bean.getItemloc();
            item.sales = bean.getVolume();
            item.user_type = bean.getUser_type();
            item.click_url = bean.getClick_url();
            if (!TextUtils.isEmpty(bean.getCoupon()) && !TextUtils.isEmpty(bean.getClick_url()) &&
                    Double.parseDouble(bean.getCoupon()) > 0 && "1".equals(bean.getIsCoupon())) {             //有券
                item.type = 1;
                item.click_url = bean.getCoupon_url();
                item.quan_hou_num = StringDealUtil.checkDecimal(Double.parseDouble(bean.getZk_final_price()) -
                        Double.parseDouble(bean.getCoupon()) + "");
                item.valid_time = bean.getEvent_end_time();
                item.return_num=StringDealUtil.checkDecimal((Double.parseDouble(bean.getZk_final_price())-Double.parseDouble(bean.getCoupon()))
                        *(indexGoodBean.getSettleRate())*(Double.parseDouble(bean.getTk_rate()))/100+"");
            } else if (!TextUtils.isEmpty(bean.getTk_rate()) && Double.parseDouble(bean.getTk_rate()) > 0) {               //超级返利
                item.type = 0; //返利
                if (TextUtils.isEmpty(indexGoodBean.getSettleRate() + "")) {
                    indexGoodBean.setSettleRate(1);
                }
                item.fan_num = StringDealUtil.checkDecimal(Double.parseDouble(bean.getZk_final_price())
                        * Double.parseDouble(bean.getTk_rate()) * indexGoodBean.getSettleRate() / 100 + "");
                item.fan_bi = StringDealUtil.checkDecimal(Double.parseDouble(bean.getTk_rate()) *
                        (indexGoodBean.getSettleRate()) + "");
                if (TextUtils.isEmpty(bean.getZk_final_price())) {
                    item.fan_num = "0.00";
                }
                item.valid_time = bean.getEvent_end_time();
            } else {                                 //普通
                item.type = 2;
                item.valid_time = bean.getCoupon_end_date();
            }
            newList.add(item);
        }
        recyAdapter.addAll(newList);
        mCurrentCounter += newList.size();
        recyclerView.refreshComplete(REQUEST_COUNT);
    }

    @Override
    public void onRefresh() {
        recyAdapter.clear();
        mLRecyclerViewAdapter.notifyDataSetChanged();
        mCurrentCounter = 0;
        requestData();
        dis = 0;
    }

    @Override
    public void onLoadMore() {
        if (mCurrentCounter < TOTAL_COUNTER) {
            requestData();
        } else {
            recyclerView.setNoMore(true);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AlibcTradeSDK.destory();
        unbinder.unbind();
    }

    private void setSearchBarColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            indexSearchRl.setBackgroundColor(getResources().getColor(id, getContext().getTheme()));
        } else {
            indexSearchRl.setBackgroundColor(getResources().getColor(id));
        }
    }
    //-------------------------------end-----------------------------------------------
}
