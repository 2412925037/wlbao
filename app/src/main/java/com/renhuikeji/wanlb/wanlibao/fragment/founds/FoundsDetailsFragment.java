package com.renhuikeji.wanlb.wanlibao.fragment.founds;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.LoginActivity;
import com.renhuikeji.wanlb.wanlibao.adapter.FoundsDetailsAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.FoundsDetailsBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoundsDetailsFragment extends Fragment {

    private int loadType = 0;
    private LRecyclerView recyclerView = null;
    private FoundsDetailsAdapter adapter = null;
    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private int TOTAL_COUNTER = 1000;
    private static final int REQUEST_COUNT = 10;
    private static int mCurrentCounter = 0;
    private View emptyView = null;  //空数据
    private String url;
    private int page = 1;               //当前页数
    private int totalPage = 500;
    private Gson gson = null;
    private TextView tv_nointert;
    private String my_uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_founds_details, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            loadType = bundle.getInt("loadType");
        }
        my_uid = (String) SPUtils.get(getActivity(), Constant.User_Uid, "");

        gson = new Gson();
        DialogUtils.showProgressDlg(getActivity(), "加载中....");
        emptyView = view.findViewById(R.id.founds_empty_view);
        tv_nointert = (TextView) view.findViewById(R.id.tv_duanwang);
        recyclerView = (LRecyclerView) view.findViewById(R.id.foundfrag_recycler);
        //判断网络
        if (!NetworkManageUtil.checkNetworkAvailable(getActivity())) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            tv_nointert.setVisibility(View.VISIBLE);
            DialogUtils.stopProgressDlg();
        }

        initRecy();

        return view;
    }

    private void initRecy() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FoundsDetailsAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);

        recyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        recyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        //设置下拉刷新
        recyclerView.setPullRefreshEnabled(false);
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
        //recyclerView.setHeaderViewColor(R.color.colorAccent, R.color.fuzhu_black, R.color.colorWhite);
        //设置底部加载颜色
        recyclerView.setFooterViewColor(R.color.colorAccent, R.color.fuzhu_black, R.color.app_bg_color);
        //设置底部加载文字提示
        recyclerView.setFooterViewHint("加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        // lRecyclerViewAdapter.removeFooterView();
        //初始化数据
        getData();
    }

    public void getData() {
        /**
         * 获取网络数据
         */
        String session = (String) SPUtils.get(getActivity(), Constant.MSESSION, "");
        url = ConfigValue.FUNDS_DETAILS + "&uid=" + my_uid + "&p=" + page + "&type=" + loadType;
        new OkHttpUtils().getDatas(getActivity(),url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {

                DialogUtils.stopProgressDlg();
                FoundsDetailsBean bean = gson.fromJson(data, FoundsDetailsBean.class);

                if (TextUtils.equals("EMPTY", bean.getResult())) {
                    recyclerView.setEmptyView(emptyView);
                } else if (TextUtils.equals("SUCESS", bean.getResult())) {
                    totalPage = bean.getPageTotals();
                    page = Integer.parseInt(bean.getPage());
                    List<FoundsDetailsBean.FundsBean> fundsBeen = bean.getFunds();
                    adapter.addAll(fundsBeen);
                    mCurrentCounter = fundsBeen.size();
                    recyclerView.refreshComplete(fundsBeen.size());
                } else if(TextUtils.equals("NOLOGIN", bean.getResult())){
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    //ToastUtils.toastForShort(getActivity(), "请注册或登录");
                }

            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                DialogUtils.stopProgressDlg();
                ToastUtils.toastForShort(getActivity(), getActivity().getResources().getString(R.string.bad_net));
            }
        });
    }

    //---------------------------------------------------------
}
