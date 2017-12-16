package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.renhuikeji.wanlb.wanlibao.jpush.JpushUtil;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2017/5/27.
 */

public class JgSetAliasUtil {
    private static final int MSG_SET_ALIAS = 1001;
    private Context context;

    public JgSetAliasUtil(Context context) {
        this.context = context;
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(context, (String) msg.obj, null, mAliasCallback);
                    break;
            }
        }
    };
    public void setAlias(String name) {
        if (TextUtils.isEmpty(name)) {
//            ToastUtils.toastForShort(context, "别名不能为空");
            return;
        }
        if (!JpushUtil.isValidTagAndAlias(name)) { //检查格式
            ToastUtils.toastForShort(context, "别名格式错误");
            return;
        }

        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, name));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("wch", logs);
                    break;

                case 6002:
                    logs = "别名设置超时，60s后重试";
                    Log.i("wch", logs);
                    if (JpushUtil.isConnected(context)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i("wch", "无网络");
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("wch", logs);
            }
//            JpushUtil.showToast(logs, context);
        }

    };
}
