package com.renhuikeji.wanlb.wanlibao.utils.AliUtils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import static com.alibaba.baichuan.android.trade.AlibcTrade.show;

/**
 * Created by Administrator on 2017/4/24.
 *
 * 电商SDK工具类
 */

public class AliUtils {
    static AlibcLogin alibcLogin = AlibcLogin.getInstance();
    static AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5,true);
    private static Map<String, String> exParams = new HashMap<>();//yhhpass参数


    /**
     * 登录
     *
     * @param context
     */
    public static void login(final Context context) {
        alibcLogin.showLogin((Activity) context, new AlibcLoginCallback() {
            @Override
            public void onSuccess() {
                ToastUtils.toastForShort(context,"登录成功");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.toastForShort(context,"登录失败");
            }
        });
    }

    /**
     * 退出登录
     */
    public static void logout(final Context context) {

        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.logout((Activity) context, new LogoutCallback() {
            @Override
            public void onSuccess() {
               // ToastUtils.toastForShort(context,"淘宝退出登录成功");
            }

            @Override
            public void onFailure(int code, String msg) {
               // ToastUtils.toastForShort(context,"淘宝退出登录失败");
            }
        });
    }

    /**
     * 判断用户淘宝是否登录
     *
     * @return
     */
    public static boolean isLogin(){
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        return alibcLogin.isLogin();
    }

    /**
     * 打开指定链接
     */
    public static void showUrl(Context context, String url) {

        Log.i("tag","打开链接");
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        if(TextUtils.isEmpty(url)) {
            ToastUtils.toastForShort(context,"URL为空");
            return;
        }

        AlibcTrade.show((Activity) context, new AlibcPage(url), alibcShowParams, null, exParams , new DemoTradeCallback());
    }

    public  static void showDetailGoods(Context context,String id){
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        if(TextUtils.isEmpty(id)) {
            ToastUtils.toastForShort(context,"URL为空");
            return;
        }
        AlibcTrade.show((Activity) context, new AlibcDetailPage(id), alibcShowParams, null, exParams , new DemoTradeCallback());
    }

    /**
     * 打开购物车
     * @param context
     */
    public static void showShopCar(Context context){
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        AlibcBasePage alibcBasePage = new AlibcMyCartsPage();
        show((Activity) context, alibcBasePage, alibcShowParams, null, exParams, new DemoTradeCallback());
    }

    /**
     * 展示订单
     *
     * @param context
     * @param orderType     0：全部订单  1：待付款   2：待发货   3：待收货   4：待评价
     */
    public static void showAllBills(Context context, int orderType,boolean isAllOrder){
//        boolean isAllOrder = true;//进行订单分域（只展示通过当前app下单的订单），true 显示所有订单
        AlibcBasePage alibcBasePage = new AlibcMyOrdersPage(orderType, isAllOrder);
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        show((Activity)context, alibcBasePage, alibcShowParams, null, exParams, new DemoTradeCallback());
    }
}
