package com.renhuikeji.wanlb.wanlibao.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.activity.AlipayBindActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MainActivity;
import com.renhuikeji.wanlb.wanlibao.activity.WechatBindActivity;
import com.renhuikeji.wanlb.wanlibao.bean.AlipayLoginBean;
import com.renhuikeji.wanlb.wanlibao.bean.WechatBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.config.Contants;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.LogUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


import org.json.JSONException;
import org.json.JSONObject;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private String APP_ID = "wx33c86a95584f8c11";
    private static final String TAG = "WXEntryActivity";
    private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
    private static final int RETURN_MSG_TYPE_SHARE = 2; //分享

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果没回调onResp，八成是这句没有写
        App.api.handleIntent(getIntent(), this);
//        boolean handl = App.api.handleIntent(getIntent(), this);
//        Log.i("tag",handl+"");
//        if (!handl) {
//            finish();
//        }
    }

    /**
     * 微信发送的请求将回调此方法
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {

    }

    private String session;

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp baseResp) {
        Log.i("tag", "onResp:------>");
        Log.i("tag", "error_code:---->" + baseResp.errCode);
        int type = baseResp.getType(); //类型：分享还是登录
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                ToastUtil.getInstance().showToast("拒绝授权微信登录");
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                String message = "";
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    message = "取消了微信登录";
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    message = "取消了微信分享";
                }
                ToastUtil.getInstance().showToast(message);
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                //用户同意
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    //用户换取access_token的code，仅在ErrCode为0时有效
                    String code = ((SendAuth.Resp) baseResp).code;

                    Log.i("tag",code);
                    OkHttpUtils.getInstance().getYzmJson(Contants.WECHAT_LOGIN + "&code=" + code, new OkHttpUtils.HttpCallBack() {
                        @Override
                        public void onSusscess(String data) {

                            String[] str = data.split("@");
                            if (str.length > 1) {
                                WechatBean bean = new Gson().fromJson(str[0], WechatBean.class);
                                session = str[1];
                                SPUtils.put(WXEntryActivity.this, Constant.MSESSION, session);

                                if (TextUtils.equals("NOBIND", bean.getResult())) {
                                    startActivity(new Intent(WXEntryActivity.this, WechatBindActivity.class).putExtra("access_code", bean.getAccess_token()).putExtra("openid",bean.getOpenid()).putExtra("avatar",bean.getHeadimgurl()).putExtra("nickname",bean.getNickname()));
                                    finish();
                                } else if (TextUtils.equals("LOGIN_SUCESS", bean.getResult())) {

                                    SPUtils.put(WXEntryActivity.this, Constant.MSESSION, session);
                                    SPUtils.put(WXEntryActivity.this, Constant.User_Uid, bean.getUid().trim());
                                    SPUtils.put(WXEntryActivity.this, Constant.User_Phone, bean.getUsername());
                                    SPUtils.put(WXEntryActivity.this, Constant.User_Psw, bean.getPassword());
                                    Intent i = new Intent(WXEntryActivity.this, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();
                                } else {
                                    ToastUtils.toastForShort(WXEntryActivity.this, bean.getWorngMsg());
                                    finish();
                                }
                            }else{
                                finish();
                            }
                        }

                        @Override
                        public void onError(String meg) {
                            super.onError(meg);
                            finish();
                            ToastUtil.getInstance().showToast(meg);
                            Log.i("tag",OkHttpUtils.decodeUnicode(meg));
                        }
                    });


                    //LogUtil.i(TAG, "code:------>" + code);

                    //这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息


                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    ToastUtil.getInstance().showToast("微信分享成功");
                    finish();
                }
                break;
            default:
                finish();
                break;
        }

    }

}