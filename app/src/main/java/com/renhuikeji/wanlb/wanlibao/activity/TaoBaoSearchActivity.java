package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.KeySearchAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.HotKeyBean;
import com.renhuikeji.wanlb.wanlibao.bean.KeywordBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.AliUtils.AliUtils;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.KeyBoardUtils;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SearchHistorySQLite;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.VoidRepeatClickUtil;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoaderForBanner;
import com.renhuikeji.wanlb.wanlibao.views.MyListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 通用搜索界面
 */
public class TaoBaoSearchActivity extends BaseActivity implements TagFlowLayout.OnTagClickListener, TextWatcher {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.et_taobao_search_input)
    EditText etTaobaoSearchInput;
    @BindView(R.id.tv_taobao_search_cancel)
    TextView tvTaobaoSearchCancel;
    @BindView(R.id.rb_taobao_search_common)
    RadioButton rbTaobaoSearchCommon;
    @BindView(R.id.rb_taobao_search_super)
    RadioButton rbTaobaoSearchSuper;
    @BindView(R.id.rb_taobao_search_quan)
    RadioButton rbTaobaoSearchQuan;
    @BindView(R.id.et_taobao_search_minprice)
    TextInputEditText etTaobaoSearchMinprice;
    @BindView(R.id.et_taobao_search_maxprice)
    TextInputEditText etTaobaoSearchMaxprice;
    @BindView(R.id.et_taobao_search_min_bili)
    TextInputEditText etTaobaoSearchMinBili;
    @BindView(R.id.et_taobao_search_max_bili)
    TextInputEditText etTaobaoSearchMaxBili;
    @BindView(R.id.tv_taobao_search_get_bills)
    TextView tvTaobaoSearchGetBills;
    @BindView(R.id.tfl_taobao_search_hotkey)
    TagFlowLayout tflTaobaoSearchHotkey;
    @BindView(R.id.tv_taobao_search_choose)
    TextView tvTaobaoSearchChoose;
    @BindView(R.id.ll_taobao_search_choose)
    LinearLayout llTaobaoSearchChoose;
    @BindView(R.id.img_taobao_search_delete)
    ImageView imgTaobaoSearchDelete;
    @BindView(R.id.fl_taobao_search_history)
    TagFlowLayout flTaobaoSearchHistory;
    @BindView(R.id.banner_taobao_search)
    Banner bannerTaobaoSearch;
    @BindView(R.id.tv_fanbi_coupon)
    TextView tvFanbiCoupon;
    @BindView(R.id.lv_search_keywords)
    MyListView lvSearchKeywords;
    @BindView(R.id.img_search_delete)
    ImageView imgSearchDelete;
    @BindView(R.id.ll_taobao_search)
    LinearLayout llTaobaoSearch;
    @BindView(R.id.search_finish)
    ImageView searchFinish;
    private List<String> historyLabels = new ArrayList<>();
    /*数据库变量*/
    private SearchHistorySQLite helper;
    private SQLiteDatabase db;
    private String searchStr;
    private boolean isChoose = false;
    private String minPrice;
    private String maxPrice;
    private String minRate;
    private String maxRate;
    private int searchType = 1;
    private List<String> pics = new ArrayList<>();
    private HotKeyBean hotKeyBean;
    private KeywordBean keywordBean;
    private String uid;
    private String session;
    private KeySearchAdapter keySearchadapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_bao_search);
        ButterKnife.bind(this);
        uid = SPUtils.get(this, Constant.User_Uid, "").toString().trim();
        session = (String) SPUtils.get(this, Constant.MSESSION, "");
        Intent intent = getIntent();
      /*  if (null != intent) {
            searchType = intent.getIntExtra("search_type", -1);
            if (searchType == -1) {
                searchType = Integer.parseInt((String) SPUtils.get(TaoBaoSearchActivity.this, Constant.search_type, "1"));
            }
        }*/
        DialogUtils.showProgressDlg(this, "努力加载中....");
        getData();
        initViews();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        queryData();
        //将上一次搜索默认显示在搜索框
        if (historyLabels != null && historyLabels.size() > 0) {
            etTaobaoSearchInput.setText(historyLabels.get(historyLabels.size() - 1));
            imgSearchDelete.setVisibility(View.VISIBLE);
//            Log.d("ccc", "回来来了: ");
//
//            etTaobaoSearchInput.clearFocus();
//            lvSearchKeywords.setVisibility(View.GONE);
//            llTaobaoSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchType=1;
        switch (searchType) {
            case 1:
                rbTaobaoSearchCommon.setChecked(true);
                break;
            case 2:
                rbTaobaoSearchSuper.setChecked(true);
                break;
            case 3:
                rbTaobaoSearchQuan.setChecked(true);
                tvFanbiCoupon.setText("优惠券(金额)");
                etTaobaoSearchMinBili.setHint("最低金额");
                etTaobaoSearchMaxBili.setHint("最高金额");
                break;
        }

    }

    /**
     * 初始化广告轮播图
     */
    private void initBanner() {

        if (null != hotKeyBean.getHotWords() || 0 == hotKeyBean.getHotWords().size()) {
            tflTaobaoSearchHotkey.setAdapter(new TagAdapter(hotKeyBean.getHotWords()) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    String tip = hotKeyBean.getHotWords().get(position).getQ();
                    TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_taobao_search_hot, tflTaobaoSearchHotkey, false);
                    textView.setText(tip);
                    return textView;
                }
            });
            tflTaobaoSearchHotkey.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    insertData(hotKeyBean.getHotWords().get(position).getQ());
                    search(hotKeyBean.getHotWords().get(position).getQ(), hotKeyBean.getHotWords().get(position).getCat());
                    return false;
                }
            });
        }

        if (null == hotKeyBean.getSalesAcrive() || 0 == hotKeyBean.getSalesAcrive().size()) {
            bannerTaobaoSearch.setVisibility(View.GONE);
            return;
        }

        for (int i = 0; i < hotKeyBean.getSalesAcrive().size(); i++) {
            pics.add(ConfigValue.PIC_FRONT + hotKeyBean.getSalesAcrive().get(i).getPict_url());
        }

        bannerTaobaoSearch.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                AliUtils.showUrl(TaoBaoSearchActivity.this, hotKeyBean.getSalesAcrive().get(position).getClick_url());
            }
        });
        bannerTaobaoSearch.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        bannerTaobaoSearch.setImageLoader(new GlideImageLoaderForBanner());
        bannerTaobaoSearch.setImages(pics);
        if (!this.isFinishing()) {
            bannerTaobaoSearch.start();
        }
    }

    /**
     * 获取活动及热门关键词
     */
    public void getData() {
//        if (!NetworkManageUtil.isNetworkAvailable(TaoBaoSearchActivity.this)) {
//            ToastUtils.toastForShort(TaoBaoSearchActivity.this, "无网络连接");
//            bannerTaobaoSearch.setVisibility(View.GONE);
//            DialogUtils.stopProgressDlg();
//            return;
//        }

        if (!NetworkManageUtil.checkNetworkAvailable(TaoBaoSearchActivity.this)) {
            //ToastUtils.toastForShort(TaoBaoSearchActivity.this, "无网络连接");
            bannerTaobaoSearch.setVisibility(View.GONE);
            DialogUtils.stopProgressDlg();
            return;
        }

        String url = ConfigValue.HOT_KEY + "&uid=" + uid;
        new OkHttpUtils().getDatas(this, url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                DialogUtils.stopProgressDlg();
                hotKeyBean = new Gson().fromJson(data, HotKeyBean.class);
                switch (hotKeyBean.getResult()) {
                    case "SUCESS":
                        initBanner();
                        break;
                    case "NOLOGIN":
                        ToastUtils.toastForShort(TaoBaoSearchActivity.this, hotKeyBean.getWorngMsg());
                        TaoBaoSearchActivity.this.finish();
                        startActivity(new Intent(TaoBaoSearchActivity.this, LoginActivity.class));
                        break;
                    case "FAIL":
                        ToastUtils.toastForShort(TaoBaoSearchActivity.this, hotKeyBean.getWorngMsg());
                        break;
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                ToastUtils.toastForShort(TaoBaoSearchActivity.this, meg);
                DialogUtils.stopProgressDlg();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        titleRightOneBtn.setVisibility(View.VISIBLE);
        titleRightOneBtn.setImageDrawable(getResources().getDrawable(R.mipmap.icon_classify));
        tvMiddleTitle.setText("搜淘宝拿返利");


        //默认获取焦点并打开软键盘
//        etTaobaoSearchInput.setFocusable(true);
//        etTaobaoSearchInput.setFocusableInTouchMode(true);
//        etTaobaoSearchInput.requestFocus();
//        KeyBoardUtils.openKeybord(etTaobaoSearchInput, TaoBaoSearchActivity.this);

        //实例化数据库SQLiteOpenHelper子类对象
        helper = new SearchHistorySQLite(TaoBaoSearchActivity.this);
        // 第一次进入时查询所有的历史记录
//        String str = (String) SPUtils.get(TaoBaoSearchActivity.this, Constant.HISTORY_STR, "");
//        if (!TextUtils.isEmpty(str)) {
//            fromeFenLei(str);
//        }
        queryData();
        if (historyLabels != null && historyLabels.size() > 0) {
            etTaobaoSearchInput.setText(historyLabels.get(historyLabels.size() - 1));
            imgSearchDelete.setVisibility(View.VISIBLE);
        }

        flTaobaoSearchHistory.setOnTagClickListener(this);
        etTaobaoSearchInput.addTextChangedListener(this);
        //回车键或者搜索键监听
        etTaobaoSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    clickSearch();
                    //关闭软键盘
                    KeyBoardUtils.closeKeybord(etTaobaoSearchInput, TaoBaoSearchActivity.this);
                    return true;
                }
                return false;
            }
        });
    }

    private void fromeFenLei(String tempName) {
        db = helper.getWritableDatabase();
        if (!hasData(tempName)) {
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

    /**
     * 获取数据库中所有数据
     */
    private void queryData() {
        historyLabels.clear();
        Cursor query = helper.getReadableDatabase().query("records", null, null, null, null, null, null);
        while (query.moveToNext()) {
            historyLabels.add(query.getString(query.getColumnIndex("name")));
        }
        flTaobaoSearchHistory.setAdapter(new TagAdapter(historyLabels) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                String tip = historyLabels.get(position);
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_taobao_search_history, flTaobaoSearchHistory, false);
                textView.setText(tip);
                return textView;
            }
        });
    }

    /**
     * 添加历史记录
     *
     * @param tempName
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        if (!hasData(tempName)) {
            db.execSQL("insert into records(name) values('" + tempName + "')");
            db.close();
        } else {
            changeHistoryPosi(tempName);
        }

    }

    /*清空数据*/
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
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

    @OnClick({R.id.search_finish, R.id.tv_taobao_search_cancel,
            R.id.tv_taobao_search_get_bills, R.id.title_left_one_btn, R.id.tv_taobao_search_choose,
            R.id.img_taobao_search_delete, R.id.rb_taobao_search_common, R.id.rb_taobao_search_super,
            R.id.rb_taobao_search_quan, R.id.title_right_one_btn, R.id.img_search_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_finish:
                if (!VoidRepeatClickUtil.isFastDoubleClick()) {
//                    if (!TextUtils.isEmpty(etTaobaoSearchInput.getText().toString())) {
//                        SPUtils.put(TaoBaoSearchActivity.this, Constant.HISTORY_STR, etTaobaoSearchInput.getText().toString());
//                    }
                    TaoBaoSearchActivity.this.finish();
                    KeyBoardUtils.closeKeybord(etTaobaoSearchInput, TaoBaoSearchActivity.this);
                }
                break;
            case R.id.tv_taobao_search_cancel:
                if (TextUtils.isEmpty(etTaobaoSearchInput.getText().toString())) {
                    ToastUtils.toastForShort(TaoBaoSearchActivity.this, "关键字不能为空！");
                } else {
                    clickSearch();
                    //关闭软键盘
                    KeyBoardUtils.closeKeybord(etTaobaoSearchInput, TaoBaoSearchActivity.this);
                }
                break;
            case R.id.tv_taobao_search_get_bills:   //认领订单
                Intent intent = new Intent(TaoBaoSearchActivity.this, ConfirmBillActivity.class);
                startActivity(intent);
                break;
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.tv_taobao_search_choose:
                if (View.VISIBLE == llTaobaoSearchChoose.getVisibility()) {
                    llTaobaoSearchChoose.setVisibility(View.GONE);
                    tvTaobaoSearchChoose.setText("筛选");
                } else if (View.GONE == llTaobaoSearchChoose.getVisibility()) {
                    llTaobaoSearchChoose.setVisibility(View.VISIBLE);
                    tvTaobaoSearchChoose.setText("收起");
                }
                break;
            case R.id.img_taobao_search_delete:     //清除历史搜索记录
                etTaobaoSearchInput.setText("");
                etTaobaoSearchInput.clearFocus();
                deleteData();
                // SPUtils.put(TaoBaoSearchActivity.this, Constant.HISTORY_STR, "");
                queryData();
                break;
            case R.id.rb_taobao_search_common:
                searchType = 1;
                tvFanbiCoupon.setText("返比比例");
                etTaobaoSearchMinBili.setHint("0.5%");
                etTaobaoSearchMaxBili.setHint("72%");
                break;
            case R.id.rb_taobao_search_super:
                searchType = 2;
                tvFanbiCoupon.setText("返比比例");
                etTaobaoSearchMinBili.setHint("0.5%");
                etTaobaoSearchMaxBili.setHint("72%");
                break;
            case R.id.rb_taobao_search_quan:
                tvFanbiCoupon.setText("优惠券(金额)");
                etTaobaoSearchMinBili.setHint("最低金额");
                etTaobaoSearchMaxBili.setHint("最高金额");
                searchType = 3;
                break;
            case R.id.title_right_one_btn:
                startActivity(new Intent(TaoBaoSearchActivity.this, ClassifyActivity.class));
                finish();
                break;
            case R.id.img_search_delete:
                etTaobaoSearchInput.setText("");
                break;
        }
    }

    /**
     * 点击搜索后的处理事件
     */
    private void clickSearch() {
        searchStr = etTaobaoSearchInput.getText().toString().trim();
        if (searchType == 1) {
            if (TextUtils.isEmpty(searchStr)) {
                ToastUtils.toastForShort(TaoBaoSearchActivity.this, "输入为空");
                etTaobaoSearchInput.setText("");
                return;
            }
        }
        // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
        if (!TextUtils.isEmpty(etTaobaoSearchInput.getText().toString().trim())) {
            if (!TextUtils.equals("超级返", searchStr) && !TextUtils.equals("优惠券", searchStr)) {
                insertData(etTaobaoSearchInput.getText().toString().trim());
            }
        }

        search(searchStr, "");
    }

    /**
     * 获取推荐关键词
     *
     * @param s
     */
    private void getKeyword(String s) {
        String keywordUrl = ConfigValue.GET_KEYWORD + "&uid=" + uid + "&q=" + s;
        new OkHttpUtils().getDatas(this, keywordUrl, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                if (TextUtils.isEmpty(data)) {
                    lvSearchKeywords.setVisibility(View.GONE);
                    llTaobaoSearch.setVisibility(View.VISIBLE);
                } else {
                    keywordBean = new Gson().fromJson(data, KeywordBean.class);
                    if (null == keywordBean.getKeywords() || 0 == keywordBean.getKeywords().size()) {
                        lvSearchKeywords.setVisibility(View.GONE);
                        llTaobaoSearch.setVisibility(View.VISIBLE);
                    } else {
                        switch (keywordBean.getResult()) {
                            case "SUCESS":
                                String backstr = (String) SPUtils.get(TaoBaoSearchActivity.this, Constant.BACKWHERE, "");
                                if (TextUtils.equals("general", backstr)) {
                                    lvSearchKeywords.setVisibility(View.GONE);
                                    llTaobaoSearch.setVisibility(View.VISIBLE);
                                    // etTaobaoSearchInput.clearFocus();
                                    imgSearchDelete.setVisibility(View.VISIBLE);
                                    SPUtils.put(TaoBaoSearchActivity.this, Constant.BACKWHERE, "");
                                } else {
                                    keySearchadapter = new KeySearchAdapter(keywordBean.getKeywords(), TaoBaoSearchActivity.this);
                                    lvSearchKeywords.setVisibility(View.VISIBLE);
                                    llTaobaoSearch.setVisibility(View.GONE);
                                    lvSearchKeywords.setAdapter(keySearchadapter);
                                    lvSearchKeywords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            //点击关键字列表直接搜索并加入搜索历史
                                            search(keywordBean.getKeywords().get(i).getQ(), keywordBean.getKeywords().get(i).getCat());
                                            insertData(keywordBean.getKeywords().get(i).getQ());
                                        }
                                    });
                                }
                                break;
                            case "NOLOGIN":
                                lvSearchKeywords.setVisibility(View.GONE);
                                llTaobaoSearch.setVisibility(View.VISIBLE);
                                ToastUtils.toastForShort(TaoBaoSearchActivity.this, keywordBean.getWorngMsg());
                                TaoBaoSearchActivity.this.finish();
                                startActivity(new Intent(TaoBaoSearchActivity.this, LoginActivity.class));
                                break;
                            case "FAIL":
                                lvSearchKeywords.setVisibility(View.GONE);
                                llTaobaoSearch.setVisibility(View.VISIBLE);
                                ToastUtils.toastForShort(TaoBaoSearchActivity.this, keywordBean.getWorngMsg());
                                break;
                            case "EMPTY":
                                lvSearchKeywords.setVisibility(View.GONE);
                                llTaobaoSearch.setVisibility(View.VISIBLE);
                                ToastUtils.toastForShort(TaoBaoSearchActivity.this, keywordBean.getWorngMsg());
                                break;
                        }
                    }
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                lvSearchKeywords.setVisibility(View.GONE);
                llTaobaoSearch.setVisibility(View.VISIBLE);
                ToastUtils.toastForShort(TaoBaoSearchActivity.this, meg);
            }
        });
    }

    /**
     * 搜索前的判断处理
     */
    private void search(String str, String cat) {
        commitChoose();

        Intent intent = new Intent(TaoBaoSearchActivity.this, GeneralGoodsActivity.class);
        Bundle bundle = new Bundle();
        if (rbTaobaoSearchCommon.isChecked()) {
            bundle.putInt("type", 1);
        } else if (rbTaobaoSearchSuper.isChecked()) {
            bundle.putInt("type", 2);
        } else {
            bundle.putInt("type", 3);
        }
        bundle.putString("q", str);
        bundle.putString("cat", cat);
        bundle.putBoolean("isFromSearch", true);
        bundle.putBoolean("isChoose", isChoose);
        bundle.putString("minPrice", minPrice);
        bundle.putString("maxPrice", maxPrice);
        bundle.putString("minRate", minRate);
        bundle.putString("maxRate", maxRate);
        intent.putExtra("search", bundle);
        startActivity(intent);


        KeyBoardUtils.closeKeybord(etTaobaoSearchInput, TaoBaoSearchActivity.this);
    }

    /**
     * 提交筛选
     */
    private void commitChoose() {
        //先传递数据
        minPrice = etTaobaoSearchMinprice.getText().toString().trim();
        maxPrice = etTaobaoSearchMaxprice.getText().toString().trim();
        minRate = etTaobaoSearchMinBili.getText().toString().trim();
        maxRate = etTaobaoSearchMaxBili.getText().toString().trim();
        //先传递数据
        if (!TextUtils.isEmpty(minPrice) || !TextUtils.isEmpty(maxPrice) || !TextUtils.isEmpty(minRate)
                || !TextUtils.isEmpty(maxRate)) {
            isChoose = true;
        } else if (!TextUtils.isEmpty(minPrice) && !TextUtils.isEmpty(maxPrice) &&
                Integer.parseInt(minPrice) > Integer.parseInt(maxPrice)) {
            ToastUtils.toastForShort(TaoBaoSearchActivity.this, "筛选最低价格不可高于最高价格");
        } else if (!TextUtils.isEmpty(minRate) && !TextUtils.isEmpty(maxRate) &&
                Integer.parseInt(minRate) > Integer.parseInt(maxRate)) {
            ToastUtils.toastForShort(TaoBaoSearchActivity.this, "最低返利比例不可高于最高返利比例");
        }
    }

    //////////////////////// 搜索框的搜索键点击监听 /////////////////////


    //////////////////////// 搜索历史标签点击事件 /////////////////////////
    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        search(historyLabels.get(position), "");
        changeHistoryPosi(historyLabels.get(position));
        return false;
    }

    private void changeHistoryPosi(String tempName) {
        //先删除在添加
        db = helper.getWritableDatabase();
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        if (cursor.moveToNext()) {
            db.delete("records", "name=?", new String[]{tempName});
            db.execSQL("insert into records(name) values('" + tempName + "')");
            db.close();
        }
        queryData();
    }

    ///////////////////////////// 搜索框输入监听 /////////////////////////
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (TextUtils.isEmpty(etTaobaoSearchInput.getText().toString())) {
            imgSearchDelete.setVisibility(View.GONE);
            lvSearchKeywords.setVisibility(View.GONE);
            llTaobaoSearch.setVisibility(View.VISIBLE);
        } else {
            imgSearchDelete.setVisibility(View.VISIBLE);
            getKeyword(etTaobaoSearchInput.getText().toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    protected void onDestroy() {
//        SPUtils.put(TaoBaoSearchActivity.this, Constant.search_type, "1");
        super.onDestroy();
    }
}
