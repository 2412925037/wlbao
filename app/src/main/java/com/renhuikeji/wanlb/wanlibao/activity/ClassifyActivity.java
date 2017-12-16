package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.FirstClassAdapter;
import com.renhuikeji.wanlb.wanlibao.adapter.SecondAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.CatBean;
import com.renhuikeji.wanlb.wanlibao.bean.SecondBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SectionedSpanSizeLookup;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassifyActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.pop_listview_left)
    ListView popListviewLeft;
    @BindView(R.id.pop_listview_right)
    RecyclerView popListviewRight;
    @BindView(R.id.tv_classify_no_net)
    TextView tvClassifyNoNet;
    @BindView(R.id.empty_classify)
    View emptyClassify;
    /**
     * 左侧一级分类的数据
     */
    private List<CatBean> firstList = new ArrayList<>();
    /**
     * 右侧二级分类的数据
     */
    private List<SecondBean> secondList;
    /**
     * 右侧三级分类的数据
     */
    private List<CatBean> thirdList;
    private boolean isFirstIn = true;   //第一次进入
    private List<CatBean> catBeen = new ArrayList<>();
    private String choose_cid = "10";
    private SecondAdapter mAdapter;
    private int type = 1;               //商品类型
    private String q;                   //搜索关键字
    private String session;
    private String uid;
    private Gson gson=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        ButterKnife.bind(this);

        uid = SPUtils.get(this, Constant.User_Uid, "").toString().trim();
        session = (String) SPUtils.get(this, Constant.MSESSION,"");
        gson=new Gson();
        Intent intent = getIntent();
        if (null != intent) {
            if (!TextUtils.isEmpty(intent.getStringExtra("cid"))) {
                choose_cid = intent.getStringExtra("cid");
            }
            type = intent.getIntExtra("type", 1);
            q = intent.getStringExtra("q");
        }
        initViews();
        DialogUtils.showProgressDlg(ClassifyActivity.this, "分类数据获取中……");
        getData();
    }

    /**
     * 获取网络数据
     */
    private void getData() {
        if (!NetworkManageUtil.checkNetworkAvailable(ClassifyActivity.this)){
            popListviewLeft.setVisibility(View.GONE);
            popListviewRight.setVisibility(View.GONE);
            tvClassifyNoNet.setVisibility(View.VISIBLE);
            DialogUtils.stopProgressDlg();
        }

        String searchUrl = ConfigValue.CAT + "&uid=" + uid;
        if (isFirstIn) {                 //获取分类信息
            new OkHttpUtils().getJson(searchUrl, session, new OkHttpUtils.HttpCallBack() {
                @Override
                public void onSusscess(String data) {

                    JsonParser parser = new JsonParser();
                    //将JSON的String 转成一个JsonArray对象
                    JsonArray jsonArray = parser.parse(data).getAsJsonArray();

                    //加强for循环遍历JsonArray
                    for (JsonElement user : jsonArray) {
                        //使用GSON，直接转成Bean对象
                        CatBean catBean = gson.fromJson(user, CatBean.class);
                        catBeen.add(catBean);
                        if ("1".equals(catBean.getLevel())) {
                            firstList.add(catBean);
                        }
                    }
                    initPopup();
                    isFirstIn = false;

                    DialogUtils.stopProgressDlg();
                }

                @Override
                public void onError(String meg) {
                    super.onError(meg);
                    ToastUtils.toastForShort(ClassifyActivity.this, meg);
                    tvClassifyNoNet.setVisibility(View.VISIBLE);
                    tvClassifyNoNet.setText("数据获取失败");
                    DialogUtils.stopProgressDlg();
                }
            });
        }
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        titleRightOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("分类");
    }

    private void initPopup() {
        final FirstClassAdapter firstAdapter = new FirstClassAdapter(this, firstList);
        popListviewLeft.setAdapter(firstAdapter);

        //左侧ListView点击事件
        popListviewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirstClassAdapter adapter = (FirstClassAdapter) (parent.getAdapter());
                //如果上次点击的就是这一个item，则不进行任何操作
                if (adapter.getSelectedPosition() == position) {
                    return;
                }
                //根据左侧一级分类选中情况，更新背景色
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();

                //显示右侧二级分类
                updateSecondListView(firstList.get(position).getCid());
            }
        });

        for (int i = 0; i < firstList.size(); i++) {
            if (choose_cid.equals(firstList.get(i).getCid())) {
                firstAdapter.setSelectedPosition(i);
                firstAdapter.notifyDataSetChanged();
                updateSecondListView(firstList.get(i).getCid());
                break;
            }
        }
    }

    private void updateSecondListView(String cid) {
        mAdapter = new SecondAdapter(ClassifyActivity.this, type);
        GridLayoutManager manager = new GridLayoutManager(this, 3);//我们需要网格式的布局
        //设置header占据的空间
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter, manager));
        popListviewRight.setLayoutManager(manager);
        popListviewRight.setAdapter(mAdapter);
        secondList = new ArrayList<>();
        for (CatBean catBean :
                catBeen) {
            if ("2".equals(catBean.getLevel())) {
                SecondBean secondBean = new SecondBean();
                if (cid.equals(catBean.getFather_cid())) {
                    secondBean.setCat(catBean.getCat());
                    secondBean.setCid(catBean.getCid());
                    secondBean.setTitle(catBean.getTitle());
                    secondBean.setFather_cid(catBean.getFather_cid());
                    secondBean.setQ(catBean.getQ());
                    secondBean.setLevel(catBean.getLevel());
                    secondBean.setPict_url(catBean.getPict_url());
                    secondBean.setCatBeen(getThirdCat(catBean.getCid()));
                    secondList.add(secondBean);
                }
            }
        }
        mAdapter.addAll(secondList);
        mAdapter.notifyDataSetChanged();
    }

    private List<CatBean> getThirdCat(String cid) {
        thirdList = new ArrayList<>();
        for (CatBean catBean2 :
                catBeen) {
            if ("3".equals(catBean2.getLevel())) {
                if (cid.equals(catBean2.getFather_cid())) {
                    thirdList.add(catBean2);
                }
            }
        }
        return thirdList;
    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.title_right_one_btn:
                startActivity(new Intent(ClassifyActivity.this, TaoBaoSearchActivity.class));
               // finish();
                break;
        }
    }
}
