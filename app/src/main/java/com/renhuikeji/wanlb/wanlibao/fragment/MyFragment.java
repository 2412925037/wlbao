package com.renhuikeji.wanlb.wanlibao.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.orhanobut.logger.Logger;
import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.CashFlowActivity;
import com.renhuikeji.wanlb.wanlibao.activity.ConfirmBillActivity;
import com.renhuikeji.wanlb.wanlibao.activity.ExpiredBillsActivity;
import com.renhuikeji.wanlb.wanlibao.activity.FinancialDetailActivity;
import com.renhuikeji.wanlb.wanlibao.activity.FindBillsActivity;
import com.renhuikeji.wanlb.wanlibao.activity.IntegralActivity;
import com.renhuikeji.wanlb.wanlibao.activity.LoginActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MainActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MyFriendActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MyMasterActivity;
import com.renhuikeji.wanlb.wanlibao.activity.NotSettleBillsActivity;
import com.renhuikeji.wanlb.wanlibao.activity.PersonalDataActivity;
import com.renhuikeji.wanlb.wanlibao.activity.SettledBillsActivity;
import com.renhuikeji.wanlb.wanlibao.activity.SignInActivity;
import com.renhuikeji.wanlb.wanlibao.activity.WeChatCashActivity;
import com.renhuikeji.wanlb.wanlibao.activity.WebShowActivity;
import com.renhuikeji.wanlb.wanlibao.adapter.MyFragmentAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.MemberInfoBean;
import com.renhuikeji.wanlb.wanlibao.bean.MyFragmentItem;
import com.renhuikeji.wanlb.wanlibao.bean.ShareBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.config.Contants;
import com.renhuikeji.wanlb.wanlibao.utils.AliUtils.AliUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ButtonUtils;
import com.renhuikeji.wanlb.wanlibao.utils.CheckAppUtils;
import com.renhuikeji.wanlb.wanlibao.utils.CheckUtil;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.HuanXinUtils;
import com.renhuikeji.wanlb.wanlibao.utils.LogUtil;
import com.renhuikeji.wanlb.wanlibao.utils.NetworkManageUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.StatusBarUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.VoidRepeatClickUtil;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;
import com.renhuikeji.wanlb.wanlibao.views.CircleImageView;
import com.renhuikeji.wanlb.wanlibao.views.MyGridView;
import com.renhuikeji.wanlb.wanlibao.views.RoundCornerImageView;
import com.renhuikeji.wanlb.wanlibao.views.SharePopupWindow;
import com.tencent.bugly.beta.Beta;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.renhuikeji.wanlb.wanlibao.utils.SPUtils.get;

/**
 * Created by Administrator on 2017/4/20.
 * <p>
 * 会员中心界面
 */

public class MyFragment extends Fragment {

    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    Unbinder unbinder;
    @BindView(R.id.circleimg_my_icon)
    CircleImageView circleimgMyIcon;
    @BindView(R.id.tv_my_login)
    TextView tvMyLogin;
    @BindView(R.id.ll_register_login)
    LinearLayout ll_register_login;
    @BindView(R.id.ll_my_zichanliushui)
    LinearLayout llMyZichanliushui;
    @BindView(R.id.ll_my_zhuanzhangtixian)
    LinearLayout llMyZhuanzhangtixian;
    @BindView(R.id.ll_my_hongbaotiqu)
    LinearLayout llMyHongbaotiqu;
    @BindView(R.id.ll_my_daechongzhi)
    LinearLayout llMyDaechongzhi;
    @BindView(R.id.ll_my_zhanghushezhi)
    LinearLayout llMyZhanghuchongzhi;
    @BindView(R.id.ll_my_shoujitaobao)
    LinearLayout llMyShoujitaobao;
    @BindView(R.id.ll_my_yaoqinghaoyou)
    LinearLayout llMyYaoqinghaoyou;
    @BindView(R.id.ll_my_ruheyaoqing)
    LinearLayout llMyRuheyaoqing;
    @BindView(R.id.ll_my_yaoqingshouyi)
    LinearLayout llMyYaoqingshouyi;
    @BindView(R.id.ll_my_wodeshifu)
    LinearLayout llMyWodeshifu;
    @BindView(R.id.ll_my_zhuangtaishuaxin)
    LinearLayout llMyZhuangtaishuaxin;
    @BindView(R.id.ll_my_huiyuanshengji)
    LinearLayout llMyHuiyuanshengji;
    @BindView(R.id.ll_my_ziliaogengxin)
    LinearLayout llMyZiliaogengxin;
    @BindView(R.id.tv_my_quit)
    TextView tvMyQuit;
    @BindView(R.id.ll_my_wodedingdan)
    LinearLayout llMyWodedingdan;
    @BindView(R.id.ll_my_gouwuche)
    LinearLayout llMyGouwuche;
    @BindView(R.id.ll_my_taobaodenglu)
    LinearLayout llMyTaobaodenglu;
    @BindView(R.id.ll_my_daifanli)
    LinearLayout llMyDaifanli;
    @BindView(R.id.ll_my_leijifanli)
    LinearLayout llMyLeijifanli;
    @BindView(R.id.ll_my_qiandao)
    LinearLayout llMyQiandao;
    @BindView(R.id.gv_my_bills)
    MyGridView gvMyBills;
    @BindView(R.id.ll_my_fragment_user_msg)
    LinearLayout llMyFragmentUserMsg;


    @BindView(R.id.user_phone)
    TextView userPhoneTv;

    @BindView(R.id.memeber_img)
    RoundCornerImageView memeberImg;

    @BindView(R.id.member_money)
    TextView memberMoney;
    @BindView(R.id.member_type)
    ImageView memberType;
    @BindView(R.id.tv_leijifanli)
    TextView tv_leijifanli;
    @BindView(R.id.tv_income_all)
    TextView tv_allincome;
    @BindView(R.id.ll_my_wodehaoyou)
    LinearLayout llMyWodehaoyou;
    @BindView(R.id.ll_my_zaixiankefu)
    LinearLayout llMyZaixiankefu;
    @BindView(R.id.ll_my_weixindenglu)
    LinearLayout llMyWeixindenglu;
    @BindView(R.id.back_ic_back)
    ImageView backIcBack;
    private View myView;
    private List<MyFragmentItem> myBills = new ArrayList<>();
    private String uid;
    private String msession;
    private GlideImageLoader imageLoader = null;
    private String userName;
    private String userPsw;
    private String user_headerurl;
    private String hxPsw = "qqq111";
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String res = msg.obj.toString().trim();
            if (TextUtils.isEmpty(res)) {
                return;
            }
            switch (msg.what) {
                case Constant.ERROR:
                    ToastUtils.toastForShort(getActivity(), res);
                    break;
            }
        }
    };

    private MainActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_my, null);

        //StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.all_pink), 55);

        activity = (MainActivity) getActivity();

        RelativeLayout layout = (RelativeLayout) myView.findViewById(R.id.relativeLayout1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            layout.setPadding(0, activity.getStatusHeight(), 0, 0);

        mTencent = Tencent.createInstance(APP_ID, getActivity());
        mListener = new BaseUiListener();

        unbinder = ButterKnife.bind(this, myView);
        userName = (String) get(getActivity(), Constant.User_Phone, "");
        userPsw = (String) get(getActivity(), Constant.User_Psw, "");
        uid = (String) get(getActivity(), Constant.User_Uid, "");
        msession = (String) get(getActivity(), Constant.MSESSION, "");
        user_headerurl = (String) SPUtils.get(getActivity(), Constant.User_Header, "");
        imageLoader = new GlideImageLoader(getActivity());
        initViews();

        if (CheckUtil.isLogin(getActivity())) {              //已登录
            llMyFragmentUserMsg.setVisibility(View.VISIBLE);
            ll_register_login.setVisibility(View.GONE);
            tvMyQuit.setVisibility(View.VISIBLE);
            getMemberInfo();
        } else {                                                 //未登录
            llMyFragmentUserMsg.setVisibility(View.GONE);
            tvMyQuit.setVisibility(View.GONE);
            ll_register_login.setVisibility(View.VISIBLE);
            ll_register_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent();
                    loginIntent.setClass(getActivity(), LoginActivity.class);
                    startActivity(loginIntent);
                }
            });
        }
        receiveAdDownload();

        initSharePop();
        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 获取会员信息
     */
    private void getMemberInfo() {

        //如果缓存中有头像地址，直接加载
        if (!TextUtils.isEmpty(user_headerurl)) {
            imageLoader.display(memeberImg, user_headerurl, R.mipmap.icon_yh);
        }

        String url = ConfigValue.MEMBER_INFO + "&uid=" + uid + "&fields="
                + "id,money,nick,vip,salesman,headimgurl,subscribe_yasbao,openid_yasbao,level,mobile,recommender,returns,income";
        //判断网络
        if (NetworkManageUtil.checkNetworkAvailable(getActivity())) {
            OkHttpUtils.getInstance().getDatas(getActivity(),url, msession, new OkHttpUtils.HttpCallBack() {
                @Override
                public void onSusscess(String data) {

                    Logger.i(data);

                    MemberInfoBean bean = new Gson().fromJson(data, MemberInfoBean.class);
                    if (TextUtils.equals("SUCESS", bean.getResult())) {
                        MemberInfoBean.UserinfoBean userinfo = bean.getUserinfo();
                        if (!TextUtils.isEmpty(userinfo.getNick())) {
                            SPUtils.put(getActivity(), Constant.User_Nick, userinfo.getNick());
                            userPhoneTv.setText(userinfo.getNick());
                        }
                        if (!TextUtils.isEmpty(userinfo.getHeadimgurl())) {
                            SPUtils.put(getActivity(), Constant.User_Header, userinfo.getHeadimgurl());
                        }
                        //如果缓存中没有头像地址，加载
                        if (TextUtils.isEmpty(user_headerurl)) {
                            imageLoader.display(memeberImg, userinfo.getHeadimgurl());
                        }
                        if (!TextUtils.isEmpty(userinfo.getMoney())) {
                            SPUtils.put(getActivity(), Constant.User_Money, userinfo.getMoney());
                            memberMoney.setText(userinfo.getMoney());
                        }
                        if (!TextUtils.equals("N", userinfo.getVip())) {
                            imageLoader.display(memberType, R.mipmap.vip_user);
                        } else {
                            imageLoader.display(memberType, R.mipmap.commen_user);
                        }
                        tv_leijifanli.setText(userinfo.getReturns());  //累计返利
                        tv_allincome.setText(userinfo.getIncome());    //总收入
                        SPUtils.put(getActivity(), Constant.User_Returns, userinfo.getReturns());
                        SPUtils.put(getActivity(), Constant.User_Incomes, userinfo.getIncome());
                        SPUtils.put(getActivity(), Constant.Is_Vip, userinfo.getVip());
                        SPUtils.put(getActivity(), Constant.RECOMMENDER, userinfo.getRecommender());
                        SPUtils.put(getActivity(), Constant.User_Phone, userinfo.getMobile().trim());
                    } else {
                        ToastUtils.toastForShort(getActivity(), "获取个人信息失败");
                    }
                }

                @Override
                public void onError(String meg) {
                    super.onError(meg);
                    ToastUtils.toastForLong(getActivity(),"请重新登录!");
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
            });
        } else {
            ToastUtils.toastForLong(getActivity(),"请重新登录!");
            startActivity(new Intent(getActivity(),LoginActivity.class));

          /*  String isvip = (String) SPUtils.get(getActivity(), Constant.Is_Vip, "N");
            userPhoneTv.setText((String) SPUtils.get(getActivity(), Constant.User_Nick, "万利宝"));
            memberMoney.setText((String) SPUtils.get(getActivity(), Constant.User_Money, "0.0"));
            tv_leijifanli.setText((String) SPUtils.get(getActivity(), Constant.User_Returns, "0.0"));
            tv_allincome.setText((String) SPUtils.get(getActivity(), Constant.User_Incomes, "0.0"));
            if (TextUtils.equals("N", isvip)) {
                imageLoader.display(memberType, R.mipmap.commen_user);
            } else {
                imageLoader.display(memberType, R.mipmap.vip_user);
            }*/
        }

    }

    private void initViews() {
        tvMiddleTitle.setText("个人中心");
        //我的账单
        initMyBills();
        gvMyBills.setAdapter(new MyFragmentAdapter(getActivity(), myBills));
        gvMyBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) gvMyBills.getChildAt(i).findViewById(R.id.tv_item_my_fragment);
                LogUtil.i("MyFragment:", myBills.get(3).getItemName());
                LogUtil.i("MyFragment:", textView.getText().toString());
                if (!CheckUtil.isLogin(getActivity())) {
                    ToastUtils.toastForShort(getActivity(), "请先登录");
                    return;
                }
                switch (i) {
                    case 0:
                        Intent settledIntent = new Intent(getActivity(), SettledBillsActivity.class);
                        startActivity(settledIntent);
                        break;
                    case 1:
                        Intent notSettledIntent = new Intent(getActivity(), NotSettleBillsActivity.class);
                        startActivity(notSettledIntent);
                        break;
                    case 2:
                        Intent expireIntent = new Intent(getActivity(), ExpiredBillsActivity.class);
                        startActivity(expireIntent);
                        break;
                    case 3:                             //认领订单
                        Intent intent = new Intent(getActivity(), ConfirmBillActivity.class);
                        startActivity(intent);
                        break;
                    case 4:                             //认领招领
                        Intent findbillIntent = new Intent(getActivity(), FindBillsActivity.class);
                        startActivity(findbillIntent);
                        break;
                    case 5:                             //丢失订单
                        Intent lostbillIntent = new Intent(getActivity(), WebShowActivity.class);
                        lostbillIntent.putExtra("url", ConfigValue.LOST_BILLS);
                        lostbillIntent.putExtra("title", "订单提示");
                        lostbillIntent.putExtra("right_text", "认领订单");
                        startActivity(lostbillIntent);
                        break;
                }
//                if (myBills.get(3).getItemName().equals(textView.getText().toString())){
//                }
            }
        });
    }

    /**
     * 我的订单子选项
     */
    private void initMyBills() {
        if (null != myBills) {
            myBills.clear();
        }
        MyFragmentItem item = null;

        item = new MyFragmentItem();
        item.setIconResoures(R.mipmap.icon_yijiesuan);
        item.setItemName("已结算");
        myBills.add(item);

        item = new MyFragmentItem();
        item.setIconResoures(R.mipmap.icon_daijiesuan);
        item.setItemName("待结算");
        myBills.add(item);

        item = new MyFragmentItem();
        item.setIconResoures(R.mipmap.icon_yishixiao);
        item.setItemName("已失效");
        myBills.add(item);

        item = new MyFragmentItem();
        item.setIconResoures(R.mipmap.icon_renlingdingdan);
        item.setItemName("认领订单");
        myBills.add(item);

        item = new MyFragmentItem();
        item.setIconResoures(R.mipmap.icon_renlingzhaoling);
        item.setItemName("认领招领");
        myBills.add(item);

        item = new MyFragmentItem();
        item.setIconResoures(R.mipmap.icon_diushidingdan);
        item.setItemName("丢失订单");
        myBills.add(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private SharePopupWindow sharePopupWindow;

    @OnClick({R.id.back_ic_back, R.id.title_right_one_btn, R.id.ll_my_zichanliushui, R.id.ll_my_zhuanzhangtixian,
            R.id.ll_my_hongbaotiqu, R.id.ll_my_daechongzhi, R.id.ll_my_zhanghushezhi,
            R.id.ll_my_shoujitaobao, R.id.ll_my_yaoqinghaoyou, R.id.ll_my_ruheyaoqing,
            R.id.ll_my_yaoqingshouyi, R.id.ll_my_wodeshifu, R.id.ll_my_zhuangtaishuaxin,
            R.id.ll_my_huiyuanshengji, R.id.ll_my_ziliaogengxin, R.id.tv_my_quit,
            R.id.ll_my_wodedingdan, R.id.ll_my_gouwuche, R.id.ll_my_taobaodenglu,
            R.id.ll_my_qiandao, R.id.ll_my_wodehaoyou, R.id.ll_my_zaixiankefu,
            R.id.ll_my_weixindenglu,R.id.ll_my_share})
    public void onViewClicked(View view) {
        if (!CheckUtil.isLogin(getActivity())) {
            ToastUtils.toastForShort(getActivity(), "请先登录");
            return;
        }

        Intent webIntent = new Intent(getActivity(), WebShowActivity.class);
        switch (view.getId()) {
            case R.id.back_ic_back:       //返回首页
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.title_right_one_btn:      //个人资料设置
                Intent intent = new Intent();
                intent.setClass(getActivity(), PersonalDataActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_my_zichanliushui:      //我的钱包
                Intent cashFlowIntent = new Intent();
                cashFlowIntent.setClass(getActivity(), CashFlowActivity.class);
                startActivity(cashFlowIntent);
                break;
            case R.id.ll_my_zhuanzhangtixian:   //微信提现
                MainActivity mainActivity1 = (MainActivity) getActivity();
                mainActivity1.select(3);
//                Intent cashIntent = new Intent();
//                cashIntent.setClass(getActivity(), WeChatCashActivity.class);
//                startActivity(cashIntent);
                break;
            case R.id.ll_my_hongbaotiqu:        //资金流水
                startActivity(new Intent(getActivity(), FinancialDetailActivity.class));
                break;
            case R.id.ll_my_daechongzhi:        //积分流水
                Intent largeIntent = new Intent(getActivity(), IntegralActivity.class);
                startActivity(largeIntent);
                break;
            case R.id.ll_my_zhanghushezhi:      //账户设置
                Intent accountIntent = new Intent();
                accountIntent.setClass(getActivity(), PersonalDataActivity.class);
                startActivity(accountIntent);
                break;
            case R.id.ll_my_shoujitaobao:       //手机淘宝
//                AliUtils.showUrl(getActivity(), "https://m.taobao.com/");
                webIntent.putExtra("url", "https://m.taobao.com/");
                webIntent.putExtra("title", "手机淘宝");
                webIntent.putExtra("right_text", "");
                startActivity(webIntent);
                break;
            case R.id.ll_my_yaoqinghaoyou:      //邀请好友
                webIntent.putExtra("url", ConfigValue.INVITE_FRIENDS);
                webIntent.putExtra("title", "邀请好友");
                webIntent.putExtra("right_text", "");
                startActivity(webIntent);
                break;
            case R.id.ll_my_ruheyaoqing:        //如何邀请
                webIntent.putExtra("url", ConfigValue.INVITATION_FRIENDS);
                webIntent.putExtra("title", "如何邀请");
                webIntent.putExtra("right_text", "开始推广");
                startActivity(webIntent);
                break;
            case R.id.ll_my_yaoqingshouyi:      //推广收益
                webIntent.putExtra("url", ConfigValue.INVITATION_GAIN);
                webIntent.putExtra("title", "推广收益");
                webIntent.putExtra("right_text", "开始推广");
                startActivity(webIntent);
                break;
            case R.id.ll_my_wodeshifu:          //我的师傅
                startActivity(new Intent(getActivity(), MyMasterActivity.class));
                break;

            case R.id.ll_my_share:          //我的分享
                if (!ButtonUtils.isFastDoubleClick()) {
                    if (sharePopupWindow != null)
                        sharePopupWindow.showPopupWindow(view);
                }

                break;
            case R.id.ll_my_zhuangtaishuaxin:   //检测升级
                if (!VoidRepeatClickUtil.isFastDoubleClick()) {
                    Beta.checkUpgrade();
                }
                break;
            case R.id.ll_my_huiyuanshengji:     //会员升级
                webIntent.putExtra("url", ConfigValue.LEVEL_UP);
                webIntent.putExtra("title", "会员升级");
                webIntent.putExtra("right_text", "打开微信");
                startActivity(webIntent);
                break;
            case R.id.ll_my_ziliaogengxin:      //资料更新
                /*DialogUtils.showProgressDlg(getActivity(), "正在更新信息......");
                getMemberInfo();
                DialogUtils.stopProgressDlg();*/
                webIntent.putExtra("url", ConfigValue.UPDATE_INFO);
                webIntent.putExtra("title", "资料更新");
                webIntent.putExtra("right_text", "打开微信");
                startActivity(webIntent);
                break;
            case R.id.ll_my_wodedingdan:        //我的订单
                AliUtils.showAllBills(getActivity(), 0, true);
                break;
            case R.id.ll_my_gouwuche:           //购物车
                AliUtils.showShopCar(getActivity());
                break;
            case R.id.ll_my_taobaodenglu:       //返利订单
              /*  boolean b = (boolean) SPUtils.get(getActivity(), Constant.INIT_ALI, false);
                if (b) {
                    AliUtils.login(getActivity());
                } else {
                    ToastUtils.toastForShort(getActivity(), "阿里sdk初始化失败!");
                }*/
                AliUtils.showAllBills(getActivity(), 0, false);
                break;
            case R.id.ll_my_qiandao:            //签到
                startActivity(new Intent(getActivity(), SignInActivity.class));
                break;
            case R.id.ll_my_wodehaoyou:         //我的好友
                startActivity(new Intent(getActivity(), MyFriendActivity.class));
                break;
            case R.id.ll_my_zaixiankefu:        //在线客服
                if (CheckUtil.isLogin(getActivity())) {
                    HuanXinUtils.getInstance().openHuanXin(getActivity());
                } else {
                    ToastUtils.toastForLong(getActivity(), "请先登录!");
                }
                break;
            case R.id.ll_my_weixindenglu:       //微信登录
                if (CheckAppUtils.isAvilible(getActivity(), "com.tencent.mm")) {// 传入指定应用包名
                    Intent wxIntent = new Intent();
                    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                    wxIntent.setAction(Intent.ACTION_MAIN);
                    wxIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    wxIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    wxIntent.setComponent(cmp);
                    startActivity(wxIntent);
                } else {
                    ToastUtils.toastForShort(getActivity(), "未安装微信");
                }
                break;
//            case R.id.ll_register_login:        //登录/注册
//                Intent loginIntent = new Intent();
//                loginIntent.setClass(getActivity(), LoginActivity.class);
//                startActivity(loginIntent);
//                break;
        }
    }

    private String share_title;
    private String share_content;
    private String share_logo;
    private String share_url;

    private void initSharePop() {


        sharePopupWindow = new SharePopupWindow(getActivity());
        sharePopupWindow.setonItemClickListener(new SharePopupWindow.onMyItemClickListener() {
            @Override
            public void onitemclick(final View view) {
                sharePopupWindow.dismiss();
                String url = Contants.SHARE_URL + "&uid="+uid;
                new OkHttpUtils().getDatas(activity, url, msession, new OkHttpUtils.HttpCallBack() {
                    @Override
                    public void onSusscess(String data) {

                        ShareBean shareBean = new Gson().fromJson(data,ShareBean.class);
                        share_title = shareBean.getShareData().getTitle();
                        share_content = shareBean.getShareData().getContent();
                        share_logo = shareBean.getShareData().getLogo();
                        share_url = shareBean.getShareData().getUrl();

                        int id = (int) view.getTag();
                        switch (id) {
                            case 0:
                                if (!ButtonUtils.isFastDoubleClick())
                                    shareToQZone();
                                break;
                            case 1:
                                if (!ButtonUtils.isFastDoubleClick())
                                    shareToQQ();
                                break;
                            case 2:
                                //会话
                                if (!ButtonUtils.isFastDoubleClick()) {

                                    if (App.api.isWXAppInstalled()) {
                                        shareLinkPage(false);
                                    } else {
                                        ToastUtil.getInstance().showToast("你当前并未安装微信");
                                    }
                                }
                                break;
                            case 3:
                                if (!ButtonUtils.isFastDoubleClick()) {

                                    //朋友圈
                                    if (App.api.isWXAppInstalled()) {
                                        shareLinkPage(true);
                                    } else {
                                        ToastUtil.getInstance().showToast("你当前并未安装微信");
                                    }
                                }
                                break;
                        }

                    }

                    @Override
                    public void onError(String meg) {
                        super.onError(meg);
                        ToastUtil.getInstance().showToast("分享失败");
                    }
                });
            }
        });

    }

    private void shareToQZone() {
        Bundle params2 = new Bundle();
        params2.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params2.putString(QzoneShare.SHARE_TO_QQ_TITLE, share_title);// 标题
        params2.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, share_content);// 摘要
        params2.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, share_url);// 内容地址
        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add(share_logo);
        params2.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
        mTencent.shareToQzone(activity, params2, mListener);
    }

    private void shareToQQ() {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, share_title);// 标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, share_content);// 摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, share_url);// 内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, share_logo);// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其它附加功能");

        mTencent.shareToQQ(getActivity(), params, mListener);
    }

    private String APP_ID = "1106235186";
    private Tencent mTencent;

    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            ToastUtil.getInstance().showToast("分享成功");
        }

        @Override
        public void onError(UiError uiError) {
            ToastUtil.getInstance().showToast("分享失败");
        }

        @Override
        public void onCancel() {
            ToastUtil.getInstance().showToast("分享取消");
        }
    }

    private BaseUiListener mListener;

    public void shareLinkPage(boolean isTimelineCb) {


        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = share_url;

        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = share_title;
        msg.description = share_content;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        App.api.sendReq(req);
    }


    private void testHuanXin() {
        boolean b = (boolean) SPUtils.get(getActivity(), Constant.IS_FIRST_HX, true);
        String str = getString(R.string.loading);
        if (b) {
            str = "加载客服,正在初始化...";
        }
        DialogUtils.showProgressDlg(getActivity(), str);
        ChatClient.getInstance().setDebugMode(true);
        registeHx();
    }

    private void toChat() {
        SPUtils.put(getActivity(), Constant.IS_FIRST_HX, false);
        DialogUtils.stopProgressDlg();
        Intent intent = new IntentBuilder(getActivity())
                .setServiceIMNumber("kefuchannelimid_887434") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                .build();
        startActivity(intent);
    }

    private void loginHx() {
        ChatClient.getInstance().login(userName, hxPsw, new Callback() {
            @Override
            public void onSuccess() {
                toChat();
            }

            @Override
            public void onError(int i, String s) {
                DialogUtils.stopProgressDlg();
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.ERROR;
                msg.obj = s;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    public void registeHx() {
        ChatClient.getInstance().createAccount(userName, hxPsw, new Callback() {
            @Override
            public void onSuccess() {
                isLogin();
            }

            @Override
            public void onError(int i, String s) {
                if (i == 203) {
                    isLogin();
                } else {
                    Message msg = mHandler.obtainMessage();
                    msg.what = Constant.ERROR;
                    msg.obj = s;
                    mHandler.sendMessage(msg);
                    DialogUtils.stopProgressDlg();
                }

            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }

    public void isLogin() {
        if (ChatClient.getInstance().isLoggedInBefore()) {
            //已经登录，可以直接进入会话界面
            toChat();
        } else {
            //未登录，需要登录后，再进入会话界面
            loginHx();
        }
    }

    LocalBroadcastManager broadcastManager;

    /**
     * 注册广播接收器
     */
    private void receiveAdDownload() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refreshmoney");
        broadcastManager.registerReceiver(mAdDownLoadReceiver, intentFilter);
    }

    BroadcastReceiver mAdDownLoadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra("result");
            if (TextUtils.equals("1", result)) {
                getMemberInfo();
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        AlibcTradeSDK.destory();
        broadcastManager.unregisterReceiver(mAdDownLoadReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("ttt","adfad");
        if (mTencent != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
