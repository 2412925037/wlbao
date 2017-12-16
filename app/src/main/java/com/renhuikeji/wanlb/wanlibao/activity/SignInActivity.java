package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.SignInDateAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.DayIsSignBean;
import com.renhuikeji.wanlb.wanlibao.bean.SignRecordBean;
import com.renhuikeji.wanlb.wanlibao.bean.SignResultBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DateUtil;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.LogUtil;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.UpdateSessionUtil;
import com.renhuikeji.wanlb.wanlibao.views.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 签到界面
 */
public class SignInActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.tv_sign_in_username)
    TextView tvSignInUsername;
    @BindView(R.id.tv_sign_in_score)
    TextView tvSignInScore;
    @BindView(R.id.tv_sign_in_exchange)
    TextView tvSignInExchange;
    @BindView(R.id.tv_sign_in_now)
    TextView tvSignInNow;
    @BindView(R.id.tv_sign_in_record)
    TextView tvSignInRecord;
    @BindView(R.id.tv_sign_in_date)
    TextView tvSignInDate;
    @BindView(R.id.tv_sign_in_ruletitle)
    TextView tvSignInRuletitle;
    @BindView(R.id.ll_sign_in_detail)
    LinearLayout llSignInDetail;
    @BindView(R.id.gv_sign_in_date_week)
    MyGridView gvSignInDateWeek;
    @BindView(R.id.gv_sign_in_date_days)
    MyGridView gvSignInDateDays;
    @BindView(R.id.tv_sign_in_record_cancel)
    TextView tvSignInRecordCancel;
    @BindView(R.id.img_sign_in_left)
    ImageView imgSignInLeft;
    @BindView(R.id.img_sign_in_right)
    ImageView imgSignInRight;
    @BindView(R.id.tv_sign_in_rules1)
    TextView tvSignInRules1;

    @BindView(R.id.tv_sign_in_points)
    TextView tvSignInPoints;
    @BindView(R.id.rule_tv)
    TextView ruleTv;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private List<DayIsSignBean> weekList = new ArrayList<>();
    private List<DayIsSignBean> dayIsSignBeen = new ArrayList<>();
    private int currenYear = DateUtil.getYear();
    private int currenMonth = DateUtil.getMonth();
    private int currenDayOfMonth = DateUtil.getCurrentMonthDay();
    private String uid;
    private String session;
    private SignRecordBean signRecords;
    private String userName;
    private String lastPoints;
    private boolean isGetSignMsg = false;
    private boolean isSigned = false;
    private String TAG = "SignInActivity";
    private SignInDateAdapter weekAdapter;
    private SignInDateAdapter dayAdapter;
    private String exchangeMoney;
    private String orderMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        uid = (String) SPUtils.get(this, Constant.User_Uid, "");
        session = (String) SPUtils.get(this, Constant.MSESSION, "");
        userName = (String) SPUtils.get(this, Constant.User_Nick, "");

        initViews();
        DialogUtils.showProgressDlg(this, "签到信息获取中……");
        getData();
    }

    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("每 日 签 到");
        tvMiddleTitle.setTypeface(Typeface.DEFAULT_BOLD);
        tvSignInUsername.setText(userName);

        tvSignInDate.setText(currenYear + "年" + currenMonth + "月");
        tvSignInRuletitle.setText("<签到规则>");

        imgSignInLeft.setClickable(false);
        imgSignInRight.setClickable(false);

        initWeek();
        weekAdapter = new SignInDateAdapter(SignInActivity.this, true);
        gvSignInDateWeek.setAdapter(weekAdapter);
        weekAdapter.addAll(weekList);
        dayAdapter = new SignInDateAdapter(SignInActivity.this, false);
        gvSignInDateDays.setAdapter(dayAdapter);

    }

    //  今日应得积分
    private String currenDayOfpoint = "";

    /**
     * 获取签到信息
     */
    private void getData() {
        String url = ConfigValue.SIGN_INFO + "&uid=" + uid;
        new OkHttpUtils().getDatas(this,url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                DialogUtils.stopProgressDlg();
                LogUtil.d("CCC",data);
                signRecords = new Gson().fromJson(data, SignRecordBean.class);
                switch (signRecords.getResult()) {
                    case "SUCESS":
                        if (null != signRecords) {
                            exchangeMoney=signRecords.getExchangeMoney();
                            orderMoney=signRecords.getOrderMoney();
                            ruleTv.setText("\t\t当您通过万利宝去淘宝购物，订单满" +orderMoney+
                                    "元,系统自动兑付"+exchangeMoney+"元（每100积分兑换一元）,优惠券商品满" +
                                    orderMoney+"元，也可兑付，兑付款将直接打入您的万利宝资金账户，在资金流水中可见。");
                            tvSignInScore.setText(signRecords.getPointsTotal());
                            isGetSignMsg = true;
                            initDateData(currenYear, currenMonth);

                            isSigned = isSign(currenDayOfMonth);
                            tvSignInPoints.setText("+" + signRecords.getTodaySignPoints() + "积分");
                        }
                        if (isSigned) {
                            setSignBtnColor();
                        }
                        break;
                    case "NOLOGIN":
                        ToastUtils.toastForShort(SignInActivity.this, signRecords.getWorngMsg());
                        startActivity(new Intent(SignInActivity.this, LoginActivity.class));
                        break;
                    case "FAIL":
                        ToastUtils.toastForShort(SignInActivity.this, signRecords.getWorngMsg());
                        break;
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                DialogUtils.stopProgressDlg();
                SignInActivity.this.finish();
                ToastUtils.toastForShort(SignInActivity.this, getString(R.string.bad_net));
            }
        });
    }

    /**
     * 判断当日是否登录过
     *
     * @param currenDayOfMonth
     * @return
     */
    private boolean isSign(int currenDayOfMonth) {
        String currenDay = String.valueOf(currenDayOfMonth);
        boolean isSign = false;
        List<SignRecordBean.SignRecordsBean> currentMonthSignRecords = signRecords.getSignRecords();
        if (currentMonthSignRecords == null) {
            return isSign;
        }
        for (SignRecordBean.SignRecordsBean bean : currentMonthSignRecords) {
            String[] split = bean.getSign_date().split("-");
            String signDate = split[split.length - 1];
            if (currenDay.length() == 1) {
                currenDay = "0" + currenDay;
            }
            if (TextUtils.equals(currenDay, signDate)) {
                currenDayOfpoint = bean.getPoints();
                isSign = true;
                break;
            }
        }
        return isSign;
    }

    private void initWeek() {
        weekList.add(new DayIsSignBean("日"));
        weekList.add(new DayIsSignBean("一"));
        weekList.add(new DayIsSignBean("二"));
        weekList.add(new DayIsSignBean("三"));
        weekList.add(new DayIsSignBean("四"));
        weekList.add(new DayIsSignBean("五"));
        weekList.add(new DayIsSignBean("六"));
    }

    /**
     * 获取网络数据
     *
     * @param year
     * @param month
     */
    private void initDateData(int year, int month) {
        if (dayIsSignBeen != null) {
            dayIsSignBeen.clear();
        }

        int date[][] = DateUtil.getMonthNumFromDate(year, month);
        for (int daterow[] : date) {
            for (int dateItem : daterow) {
                if (0 == dateItem) {
                    dayIsSignBeen.add(new DayIsSignBean("", false));
                } else {
                    dayIsSignBeen.add(new DayIsSignBean("" + dateItem, isSign(dateItem)));
                }
            }
        }
        dayAdapter.addAll(dayIsSignBeen);
    }

    @OnClick({R.id.title_left_one_btn, R.id.tv_sign_in_exchange, R.id.tv_sign_in_now,
            R.id.tv_sign_in_record, R.id.tv_sign_in_record_cancel, R.id.img_sign_in_left,
            R.id.img_sign_in_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.tv_sign_in_exchange:
                Intent webIntent = new Intent(SignInActivity.this, WebShowActivity.class);
                webIntent.putExtra("url", ConfigValue.EXCHANGE_INTEGRAL);
                webIntent.putExtra("title", "积分兑换");
                webIntent.putExtra("right_text", "");
                startActivity(webIntent);
                break;
            case R.id.tv_sign_in_now:
                if (NetworkManageUtil.checkNetworkAvailable(this)) {
                    if (isSigned) {
                        setSignBtnColor();
                        ToastUtils.toastForShort(this, "您今日已经签到过");
                    } else {
                        DialogUtils.showProgressDlg(this, "数据请求中……");
                        sign();
                    }
                } else {
                    ToastUtils.toastForShort(this, "网络未连接……");
                }
                break;
            case R.id.tv_sign_in_record:
                if (isGetSignMsg) {
                    llSignInDetail.setVisibility(View.VISIBLE);
                    isGetSignMsg = false;
                } else {
                    isGetSignMsg = true;
                    llSignInDetail.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_sign_in_record_cancel:
                llSignInDetail.setVisibility(View.GONE);
                break;
            case R.id.img_sign_in_left:
                if (currenMonth > 1) {
                    currenMonth--;
                } else {
                    currenMonth = 12;
                    currenYear--;
                }
                tvSignInDate.setText(currenYear + "年" + currenMonth + "月");
                initDateData(currenYear, currenMonth);
                gvSignInDateDays.setAdapter(new SignInDateAdapter(SignInActivity.this, false));
                break;
            case R.id.img_sign_in_right:
                if (currenMonth < 12) {
                    currenMonth++;
                } else {
                    currenMonth = 1;
                    currenYear++;
                }
                tvSignInDate.setText(currenYear + "年" + currenMonth + "月");
                initDateData(currenYear, currenMonth);
                gvSignInDateDays.setAdapter(new SignInDateAdapter(SignInActivity.this, false));
                break;
        }
    }

    private void setSignBtnColor() {
        tvSignInNow.setText("今日已签到!");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvSignInNow.setBackgroundColor(getColor(R.color.gray_pressed));
            tvSignInNow.setTextColor(getColor(R.color.weakcolor_grey));
        } else {
            tvSignInNow.setTextColor(getResources().getColor(R.color.weakcolor_grey));
            tvSignInNow.setBackgroundColor(getResources().getColor(R.color.gray_pressed));
        }
    }

    /**
     * 签到
     */
    private void sign() {
        String url = ConfigValue.SIGN_ASK + "&uid=" + uid;
        new OkHttpUtils().getDatas(this,url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                DialogUtils.stopProgressDlg();
                SignResultBean signResultBean = new Gson().fromJson(data, SignResultBean.class);
                switch (signResultBean.getResult()) {
                    case "SUCESS":
                        showDialogSignInSuccess(signResultBean.getWorngMsg());
                        dayAdapter.clear();
                        DialogUtils.showProgressDlg(SignInActivity.this, "签到信息获取中……");
                        getData();
                        break;
                    case "NOLOGIN":
                        ToastUtils.toastForShort(SignInActivity.this, signResultBean.getWorngMsg());
                        SignInActivity.this.finish();
                        startActivity(new Intent(SignInActivity.this, LoginActivity.class));
                        break;
                    case "FAIL":
                        ToastUtils.toastForShort(SignInActivity.this, signResultBean.getWorngMsg());
                        break;
                    case "SIGNED":
                        ToastUtils.toastForShort(SignInActivity.this, signResultBean.getWorngMsg());
                        setSignBtnColor();
                        break;
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                DialogUtils.stopProgressDlg();
                if(NetworkManageUtil.isNetworkAvailable(SignInActivity.this)){
                    UpdateSessionUtil.getInstance().update(context, new OkHttpUtils.updateSessionFinish() {
                        @Override
                        public void requestDatas(String session) {
                            SignInActivity.this.session=session;
                            sign();
                        }
                    });
                }else{
                    ToastUtils.toastForShort(SignInActivity.this, meg.toString());
                }
            }
        });
    }

    private void showDialogSignInSuccess(String worngMsg) {
        builder = new AlertDialog.Builder(SignInActivity.this);
        View view = LayoutInflater.from(SignInActivity.this).inflate(R.layout.dialog_sign_in_success, null);
        TextView tvDays = (TextView) view.findViewById(R.id.tv_sign_in_success_days);
        TextView tvScores = (TextView) view.findViewById(R.id.tv_sign_in_success_scores);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_sign_in_success_confirm);
        tvScores.setText(worngMsg);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
