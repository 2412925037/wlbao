package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.MyMasterAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.AccountInfo;
import com.renhuikeji.wanlb.wanlibao.bean.MasterGotBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;
import com.renhuikeji.wanlb.wanlibao.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的师傅界面
 */
public class MyMasterActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.civ_my_master_user)
    CircleImageView civMyMasterUser;
    @BindView(R.id.tv_my_master_wechat_name)
    TextView tvMyMasterWechatName;
    @BindView(R.id.tv_my_master_phone_number)
    TextView tvMyMasterPhoneNumber;
    @BindView(R.id.lv_my_master)
    ListView lvMyMaster;
    private String uid;
    private List<MasterGotBean> masterGotBeen = new ArrayList<>();
    MyMasterAdapter adapter;
    private GlideImageLoader imageLoader;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String res = msg.obj.toString().trim();
            if (TextUtils.isEmpty(res)) {
                ToastUtils.toastForShort(MyMasterActivity.this, "请求数据失败!");
                return;
            }
            switch (msg.what) {
                case Constant.SUCCESS:
                    AccountInfo info = new Gson().fromJson(res, AccountInfo.class);
                    if (TextUtils.equals("SUCESS", info.getResult())) {
                        setDatas(info.getAccount());
                    }else if(TextUtils.equals("NOLOGIN", info.getResult())){
                        ToastUtils.toastForShort(MyMasterActivity.this, info.getWorngMsg());
                        MyMasterActivity.this.finish();
                        startActivity(new Intent(MyMasterActivity.this, LoginActivity.class));
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_master);
        ButterKnife.bind(this);
        imageLoader = new GlideImageLoader(MyMasterActivity.this);
        uid = (String) SPUtils.get(this, Constant.RECOMMENDER, "");
        initViews();
        if (TextUtils.isEmpty(uid)) {
            ToastUtils.toastForShort(this, "无信息!");
            this.finish();
        } else {
            getDatas();
        }

    }

    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("我的师傅");
        titleRightOneBtn.setVisibility(View.VISIBLE);

        initMasterData();
        adapter = new MyMasterAdapter(MyMasterActivity.this, masterGotBeen);
        lvMyMaster.setAdapter(adapter);
    }

    private void initMasterData() {

    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.title_right_one_btn:
                Intent intent = new Intent(MyMasterActivity.this, TaoBaoSearchActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void getDatas() {
        DialogUtils.showProgressDlg(this, "加载中....");
        String url = ConfigValue.APP_IP + "?api=yasbao.api.member.account&uid=" + uid + "&apiKey=" + ConfigValue.API_KEY;
        String session = (String) SPUtils.get(this, Constant.MSESSION, "");
        new OkHttpUtils().getDatas(this,url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.SUCCESS;
                msg.obj = data;
                mHandler.sendMessage(msg);
                DialogUtils.stopProgressDlg();
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                ToastUtils.toastForShort(MyMasterActivity.this, getResources().getString(R.string.bad_net));
                DialogUtils.stopProgressDlg();
            }
        });

    }

    private void setDatas(AccountInfo.AccountBean info) {
        tvMyMasterWechatName.setText(info.getNick());
        tvMyMasterPhoneNumber.setText(info.getMobile());
        //tvMyMasterPhoneNumber.setText(info.getId());
        if (!TextUtils.isEmpty(info.getHeadimgurl())) {
            imageLoader.display(civMyMasterUser, info.getHeadimgurl());
        }

        MasterGotBean masterGotBean = null;

        masterGotBean = new MasterGotBean();
        masterGotBean.setCostName("推广费：");
        masterGotBean.setCostNum(info.getSales_income());
        masterGotBeen.add(masterGotBean);

        masterGotBean = new MasterGotBean();
        masterGotBean.setCostName("代理费：");
        masterGotBean.setCostNum(info.getAgent_income());
        masterGotBeen.add(masterGotBean);

        masterGotBean = new MasterGotBean();
        masterGotBean.setCostName("提成费：");
        masterGotBean.setCostNum(info.getBusiness_income());
        masterGotBeen.add(masterGotBean);

        masterGotBean = new MasterGotBean();
        masterGotBean.setCostName("返利收入：");
        masterGotBean.setCostNum(info.getReturns());
        masterGotBeen.add(masterGotBean);

        masterGotBean = new MasterGotBean();
        masterGotBean.setCostName("佣金收入：");
        masterGotBean.setCostNum(info.getCommission());
        masterGotBeen.add(masterGotBean);

        masterGotBean = new MasterGotBean();
        masterGotBean.setCostName("收入总额：");
        masterGotBean.setCostNum(info.getIncome());
        masterGotBeen.add(masterGotBean);

        masterGotBean = new MasterGotBean();
        masterGotBean.setCostName("预估推广金金额：");
        masterGotBean.setCostNum(info.getSales_account());
        masterGotBeen.add(masterGotBean);

        masterGotBean = new MasterGotBean();
        masterGotBean.setCostName("预估代理费余额：");
        masterGotBean.setCostNum(info.getAgent_account());
        masterGotBeen.add(masterGotBean);

        masterGotBean = new MasterGotBean();
        masterGotBean.setCostName("预估提成费余额：");
        masterGotBean.setCostNum(info.getBusiness_account());
        masterGotBeen.add(masterGotBean);
        adapter.notifyDataSetChanged();
    }
}
