package com.renhuikeji.wanlb.wanlibao.utils.AliUtils;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.ResultType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

/**
 * Created by fenghaoxiu on 16/8/23.
 */
public class DemoTradeCallback implements AlibcTradeCallback {

    @Override
    public void onTradeSuccess(TradeResult tradeResult) {
        //当addCartPage加购成功和其他page支付成功的时候会回调

        if (tradeResult.resultType.equals(ResultType.TYPECART)) {
            //加购成功
            ToastUtils.toastForShort(App.application, "加购成功");
        } else if (tradeResult.resultType.equals(ResultType.TYPEPAY)) {
            //支付成功
            ToastUtils.toastForShort(App.application, "支付成功,成功订单号为" + tradeResult.payResult.paySuccessOrders);
        }
    }

    @Override
    public void onFailure(int errCode, String errMsg) {
        ToastUtils.toastForShort(App.application, "电商SDK出错");
    }
}
