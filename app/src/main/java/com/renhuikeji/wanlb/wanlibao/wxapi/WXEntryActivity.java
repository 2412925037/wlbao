package com.renhuikeji.wanlb.wanlibao.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.LogUtil;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


import org.json.JSONException;
import org.json.JSONObject;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private String APP_ID = "wxe67cf29ce9d34600";
    private String APP_SECRET = "f94840b1da52dece67a59d01c5415725";

    private static final String TAG = "WXEntryActivity";
    private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
    private static final int RETURN_MSG_TYPE_SHARE = 2; //分享

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("tag","分享");
        boolean handl = App.api.handleIntent(getIntent(), this);
        Log.i("tag","123"+handl);
        if (!handl) {
            finish();
        }
    }

    /**
     * 微信发送的请求将回调此方法
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {

    }

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
                    LogUtil.i(TAG, "code:------>" + code);

                    //这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息
                    getAppToken(code);

                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    ToastUtil.getInstance().showToast("微信分享成功");
                    finish();
                }
                break;
//            default:
//                finish();
               // break;
        }

    }

    /**
     * 获取openid  accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getAppToken(String code) {

        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + APP_ID
                + "&secret="
                + APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        new OkHttpUtils().getJson(path, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String result) {

                try {
                    JSONObject object = new JSONObject(result);

                    String access_token = object.getString("access_token");
                    String openid = object.getString("openid");

                    getUserMsg(access_token, openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 获取微信个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMsg(String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;

        new OkHttpUtils().getJson(url, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String result) {

                {
                    LogUtil.i("tag", result);
                    JSONObject jsonObject = null;
                    String name = null;
                    String icon = null;
                    try {
                        jsonObject = new JSONObject(result);
                        name = jsonObject.getString("nickname");
                        icon = jsonObject.getString("headimgurl");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                    JSONObject object = new JSONObject();
                    try {
                        object.put("sso_type", 1);
                        object.put("sso_openid", openid);
                        object.put("nickname", name);
                        object.put("avatar", icon);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                    // TODO: 2017/12/5 接入微信登录接口

                }
            }
        });

    }

}