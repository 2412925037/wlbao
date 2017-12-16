package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.MallListAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.LoginCodeBean;
import com.renhuikeji.wanlb.wanlibao.bean.MallBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.CodeUtils;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmBillActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.title_right_two_btn)
    ImageView titleRightTwoBtn;
    @BindView(R.id.ll_confirm_bill_search)
    LinearLayout llConfirmBillSearch;
    @BindView(R.id.et_confirm_bill_shop_name)
    EditText etConfirmBillShopName;
    @BindView(R.id.et_confirm_bill_number)
    EditText etConfirmBillNumber;
    @BindView(R.id.et_confirm_bill_yanzhengma)
    EditText etConfirmBillYanzhengma;
    @BindView(R.id.img_confirm_bill_yanzhengma)
    ImageView imgConfirmBillYanzhengma;
    @BindView(R.id.tv_confirm_bill_change_another)
    TextView tvConfirmBillChangeAnother;
    @BindView(R.id.tv_confirm_bill_submit)
    TextView tvConfirmBillComment;
    @BindView(R.id.ll_confirm_bill)
    LinearLayout llConfirmBill;
    @BindView(R.id.wv_confirm_bill)
    WebView wvConfirmBill;
    private String codeStr;
    private CodeUtils codeUtils;
    private EditText etSearchBillKeyword;
    private EditText etSearchBillLeastRebase;
    private LinearLayout llSearchBillCost;
    private EditText etSearchBillLeastCost;
    private EditText etSearchBillMostCost;
    private CheckBox cbSearchBillSuperRebase;
    private TextView tvSearchBillChoose;
    private TextView tvSearchBillClick;
    private TextView tvSearchBillCancel;
    private PopupWindow window;
    private String uid;
    private List<MallBean.MallList> datas;
    private String mallId;
    private String  yzmImgStr;
    boolean isShowDialog=false;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String res=msg.obj.toString().trim();
            if(TextUtils.isEmpty(res)){
                ToastUtils.toastForShort(ConfirmBillActivity.this,"请求数据失败!");
                return;
            }
            switch (msg.what){
                case Constant.SUCCESS:
                    MallBean mall=new Gson().fromJson(res,MallBean.class);
                    getAliData(mall);
                    if(isShowDialog){
                        showMallPopupWindow(mall);
                    }
                    break;
            }
        }
    };



    private String session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_bill);
        ButterKnife.bind(this);
        uid= (String) SPUtils.get(this, Constant.User_Uid,"");
        session= (String) SPUtils.get(this,Constant.MSESSION,"");
        initViews();
        getMallList();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("订单认领");
        titleRightOneBtn.setVisibility(View.VISIBLE);
//        titleRightTwoBtn.setVisibility(View.VISIBLE);
        titleRightOneBtn.setImageDrawable(getResources().getDrawable(R.mipmap.icon_classify));
        titleRightTwoBtn.setImageDrawable(getResources().getDrawable(R.mipmap.icon_guanzhu_white));
        requestCodeNum();

    }

    private void requestCodeNum() {
        String url=ConfigValue.APP_IP+"?api=yasbao.api.user.authcode&uid="+uid+"&apiKey="+ConfigValue.API_KEY;
        new OkHttpUtils().getDatas(this,url,session ,new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {

                LoginCodeBean bean=new Gson().fromJson(data.trim(),LoginCodeBean.class);
                if(TextUtils.equals("SUCESS",bean.getResult().trim())){
                    yzmImgStr=bean.getCode().trim();
                    Bitmap bitmap=CodeUtils.getInstance().createBitmap(yzmImgStr);
                    imgConfirmBillYanzhengma.setImageBitmap(bitmap);
                }else if(TextUtils.equals("NOLOGIN", bean.getResult())){
                    ToastUtils.toastForLong(ConfirmBillActivity.this,bean.getWorngMsg());
                    ConfirmBillActivity.this.finish();
                    startActivity(new Intent(ConfirmBillActivity.this, LoginActivity.class));
                }
            }
        });
    }


    private void getAliData(MallBean mall) {
        List<MallBean.MallList> malls = mall.getMallList();
        for( MallBean.MallList item: malls){
            String name=item.getMall_name();
            if(name.contains("淘宝")||name.contains("天猫")){
                etConfirmBillShopName.setText(name);
                mallId= item.getMall_id();
            }
        }
    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_btn, R.id.title_right_two_btn,
            R.id.img_confirm_bill_yanzhengma, R.id.tv_confirm_bill_change_another,
            R.id.tv_confirm_bill_submit, R.id.ll_confirm_bill_search,R.id.et_confirm_bill_shop_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.title_right_one_btn:              //分类展示
                startActivity(new Intent(this, ClassifyActivity.class));
                /*FindBillsTopDialog dialog = new FindBillsTopDialog(ConfirmBillActivity.this);
                dialog.show();*/
                break;
            case R.id.title_right_two_btn:              //关注展示
                if (View.VISIBLE == llConfirmBill.getVisibility()) {
                    llConfirmBill.setVisibility(View.GONE);
                    wvConfirmBill.setVisibility(View.VISIBLE);
                }else {
                    llConfirmBill.setVisibility(View.VISIBLE);
                    wvConfirmBill.setVisibility(View.GONE);
                }
                break;
            case R.id.img_confirm_bill_yanzhengma:      //验证码展示
                break;
            case R.id.tv_confirm_bill_change_another:   //下一张验证码
                requestCodeNum();
                break;
            case R.id.tv_confirm_bill_submit:           //提交认领
                codeStr = etConfirmBillYanzhengma.getText().toString().trim();
                if (null == codeStr || TextUtils.isEmpty(codeStr)) {
                    ToastUtils.toastForShort(ConfirmBillActivity.this, "请输入验证码");
                    return;
                }
                String billNum=etConfirmBillNumber.getText().toString().trim();
                if(TextUtils.isEmpty(billNum)){
                    ToastUtils.toastForShort(ConfirmBillActivity.this,"请输入订单号");
                    return;
                }
                confirmBill(billNum,codeStr);
                break;
            case R.id.ll_confirm_bill_search:           //展示搜索页
               /* if (null == window || !window.isShowing()) {
                    showSearchPopupwindow();
                }*/
                startActivity(new Intent(ConfirmBillActivity.this,TaoBaoSearchActivity.class));
                break;
            case R.id.et_confirm_bill_shop_name:
                isShowDialog=true;
                getMallList();
                break;
        }
    }

    private void getMallList() {
        DialogUtils.showProgressDlg(this,"加载中....");
        String url=ConfigValue.APP_IP+"?api=yasbao.api.order.mall&uid="+uid+"&apiKey="+ConfigValue.API_KEY;
        new OkHttpUtils().getDatas(this,url, session,new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                Message msg=mHandler.obtainMessage();
                msg.what=Constant.SUCCESS;
                msg.obj=data;
                mHandler.sendMessage(msg);
                DialogUtils.stopProgressDlg();
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                ToastUtils.toastForShort(ConfirmBillActivity.this,"错误:"+meg);
                DialogUtils.stopProgressDlg();
            }
        });

    }


    private void showMallPopupWindow(final MallBean mall){
        View view=LayoutInflater.from(ConfirmBillActivity.this).inflate(R.layout.popupwindow_shop,null);
        LRecyclerView recyclerView= (LRecyclerView) view.findViewById(R.id.shop_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MallListAdapter adapter=new MallListAdapter(this);
        adapter.setDataList(mall.getMallList());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.refresh();
        recyclerView.setPullRefreshEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MallBean.MallList bean=mall.getMallList().get(position);
                etConfirmBillShopName.setText(bean.getMall_name().trim());
                mallId="";
                mallId=bean.getMall_id();
                if(window.isShowing()&&window!=null){
                    window.dismiss();
                }
            }
        });
        WindowManager wm=this.getWindowManager();

        int width=etConfirmBillShopName.getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        window = new PopupWindow(view, width, height);
        // 设置可以获取焦点
        window.setFocusable(true);
        window.setBackgroundDrawable(new PaintDrawable());
        // 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // 更新popupwindow的状态
        window.update();
        // 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(etConfirmBillShopName, 0, 20);

    }

    private void confirmBill(String billNum,String yzm) {
        String url= ConfigValue.APP_IP+"?api=yasbao.api.order.findorder&uid="
                +uid+"&apiKey="+ConfigValue.API_KEY+"&mall_id="+mallId+"&order_code="+billNum+"&authcode="+yzm;
        new OkHttpUtils().getDatas(this,url,session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                LoginCodeBean bean=new Gson().fromJson(data,LoginCodeBean.class);
                if(TextUtils.equals(bean.getResult(),"SECESS")){
                    ToastUtils.toastForShort(ConfirmBillActivity.this,"订单认领成功!");
                    etConfirmBillShopName.setText("");
                    etConfirmBillYanzhengma.setText("");
                    etConfirmBillNumber.setText("");
                    requestCodeNum();
                }else if(TextUtils.equals("NOLOGIN", bean.getResult())){
                    ConfirmBillActivity.this.finish();
                    startActivity(new Intent(ConfirmBillActivity.this, LoginActivity.class));
                }else{
                    ToastUtils.toastForShort(ConfirmBillActivity.this,bean.getWorngMsg());
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                ToastUtils.toastForShort(ConfirmBillActivity.this,"错误:"+meg);
            }
        });
    }

    private void showSearchPopupwindow() {
        View view1 = LayoutInflater.from(ConfirmBillActivity.this).inflate(R.layout.popupwindow_search_bills, null);
        // 创建PopupWindow对象，指定宽度和高度
        WindowManager wm = ConfirmBillActivity.this.getWindowManager();
        int with = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        window = new PopupWindow(view1, with, height);
        // 设置可以获取焦点
        window.setFocusable(true);
        window.setBackgroundDrawable(new PaintDrawable());
        // 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // 更新popupwindow的状态
        window.update();
        // 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(llConfirmBillSearch, 0, 20);


        etSearchBillKeyword = (EditText) view1.findViewById(R.id.et_search_bill_keyword);
        etSearchBillLeastRebase = (EditText) view1.findViewById(R.id.et_search_bill_least_rebase);
        llSearchBillCost = (LinearLayout) view1.findViewById(R.id.ll_search_bill_cost);
        etSearchBillLeastCost = (EditText) view1.findViewById(R.id.et_search_bill_least_cost);
        etSearchBillMostCost = (EditText) view1.findViewById(R.id.et_search_bill_most_cost);
        cbSearchBillSuperRebase = (CheckBox) view1.findViewById(R.id.cb_search_bill_super_rebase);
        tvSearchBillChoose = (TextView) view1.findViewById(R.id.tv_search_bill_choose);
        tvSearchBillClick = (TextView) view1.findViewById(R.id.tv_search_bill_click);
        tvSearchBillChoose.setOnClickListener(this);
        tvSearchBillClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_bill_choose:
                if (llSearchBillCost.getVisibility() == View.VISIBLE) {
                    llSearchBillCost.setVisibility(View.GONE);
                    tvSearchBillChoose.setText("收起");
                } else {
                    llSearchBillCost.setVisibility(View.VISIBLE);
                    tvSearchBillChoose.setText("筛选");
                }

                break;
            case R.id.tv_search_bill_click:
                break;
        }
    }
}
