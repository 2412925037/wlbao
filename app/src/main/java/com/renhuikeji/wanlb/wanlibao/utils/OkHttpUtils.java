package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.baichuan.android.trade.utils.http.HttpHelper;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.renhuikeji.wanlb.wanlibao.App;
import com.renhuikeji.wanlb.wanlibao.bean.BaseBean;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/5.
 */

public class OkHttpUtils {

    private OkHttpClient client;
    //超时时间
    public static final int TIMEOUT = 1000 * 5;

    //json请求
    public static final MediaType JSON = MediaType
            .parse("application/json; charset=utf-8");

    private Handler handler = new Handler(Looper.getMainLooper());

    public OkHttpUtils() {
        this.init();
    }

    private static OkHttpUtils mInstance;
    public static OkHttpUtils getInstance() {

        if (mInstance == null) {

            synchronized (OkHttpUtils.class) {

                if (mInstance == null) {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    public Cache provideCache() {
        return new Cache(App.getContext().getCacheDir(), 10240 * 1024);
    }

    private void init() {
        Cache cache = provideCache();
        client = new OkHttpClient();

        //设置超时
        client.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                //.addNetworkInterceptor(new CacheInterceptor())
                //.cache(cache)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * post请求  json数据为body
     */
    public void postJson(String url, String json, final HttpCallBack callBack) {
        RequestBody body = RequestBody.create(JSON, json);
        final Request request = new Request.Builder().url(url).post(body).build();

        OnStart(callBack);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callBack, response.body().string());
                } else {
                    OnError(callBack, response.message());
                }
            }
        });
    }

    /**
     * post请求  map是body
     *
     * @param url
     * @param map
     * @param callBack
     */
    public void postMap(String url, Map<String, String> map, final HttpCallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();

        //遍历map
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        OnStart(callBack);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callBack, response.body().string());
                } else {
                    OnError(callBack, response.message());
                }
            }
        });
    }

    /**
     * get 请求
     *
     * @param url
     * @param callBack
     */
    public void getJson(final String url, final HttpCallBack callBack) {
        final Request request = new Request.Builder().url(url).build();
        OnStart(callBack);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack, "failure:"+ e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                Log.i("tag","url:"+url+" "+decodeUnicode(result));
                if (response.isSuccessful()) {
                    onSuccess(callBack, result);
                } else {
                    OnError(callBack, "error:"+ result);
                }
            }
        });
    }

    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 验证码请求,取得seesion
     *
     * @param url
     * @param callBack
     */
    public void getYzmJson(final String url, final HttpCallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        OnStart(callBack);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                Log.i("tagYzmJso","url:"+url+" "+decodeUnicode(result));
                if (response.isSuccessful()) {
                    //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
                    Headers headers = response.headers();
                    List<String> cookies = headers.values("Set-Cookie");
                    String session = cookies.get(0);
                    String s = session.substring(0, session.indexOf(";"));
                    onSuccess(callBack, result + "@" + s);
                } else {
                    OnError(callBack, response.message());
                }
            }
        });
    }

    /**
     * 注册请求
     *
     * @param url
     * @param session
     * @param callBack
     */
    public void getJson(String url, String session, final HttpCallBack callBack) {
        Request request = new Request.Builder()
                .addHeader("cookie", session)
                .url(url)
                .build();
        OnStart(callBack);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callBack, response.body().string());
                } else {
                    OnError(callBack, response.message());
                }
            }
        });
    }
    /**
     *
     * @param url
     * @param session
     * @param callBack
     */
    public void getDatas( final Context context,final String url, String session, final HttpCallBack callBack) {
        final Request request = new Request.Builder()
                .addHeader("cookie", session)
                .url(url)
                .build();
        OnStart(callBack);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack, e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String res=response.body().string().trim();
                Logger.i("url:"+url+" "+decodeUnicode(res));
                if (response.isSuccessful()) {
                    BaseBean  resBean = new Gson().fromJson(res, BaseBean.class);
//                    如果session过期,去更新session
                    if(TextUtils.equals("NOLOGIN",resBean.getResult())){
                        UpdateSessionUtil.getInstance().update(context, new updateSessionFinish() {
                            @Override
                            public void requestDatas(String session) {
                                getJson(url,session,callBack);
                            }
                        });
                    }else{
                        onSuccess(callBack, res);
                    }
                } else {
                    OnError(callBack, response.message());
                }
            }
        });
    }

    /**
     * @param callBack
     */
    public void OnStart(HttpCallBack callBack) {
        if (callBack != null) {
            callBack.onstart();
        }
    }

    public void onSuccess(final HttpCallBack callBack, final String data) {
        if (callBack != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {//在主线程操作
                    callBack.onSusscess(data);
                }
            });
        }
    }

    public void OnError(final HttpCallBack callBack, final String msg) {
        if (callBack != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onError(msg);
                }
            });
        }
    }

    public static abstract class HttpCallBack {
        //开始
        public void onstart() {
        }

        //成功回调
        public abstract void onSusscess(String data);

        //失败
        public void onError(String meg) {
        }
    }

    /**
     * 更新完seesion后
     */
    public  interface updateSessionFinish{
        void requestDatas(String session);
    }
    /**
     * postFormBody
     *
     * @param url
     * @param body
     * @param callBack
     */
    public void postFormBody(String url, FormBody body, final HttpCallBack callBack) {
    /*    FormBody body = new FormBody.Builder()
                .add("your_param_1", "your_value_1")
                .add("your_param_2", "your_value_2")
                .build();*/
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callBack, response.body().string());
                } else {
                    OnError(callBack, response.message());
                }
            }
        });
    }
}
