package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.bean.LoginCodeBean;

/**
 * Created by Administrator on 2017/6/6.
 */

public class UpdateSessionUtil {
    private String session;
    private static UpdateSessionUtil instance=new UpdateSessionUtil();

    public UpdateSessionUtil() {

    }
    public static  UpdateSessionUtil getInstance(){
        return instance;
    }

    public void update(final Context context, final OkHttpUtils.updateSessionFinish updateSessionFinish){
        final String name= (String) SPUtils.get(context,Constant.User_Phone,"");
        final String psw= (String) SPUtils.get(context,Constant.User_Psw,"");
        String url = "http://app.yasbao.com/Home/Api/gw?api=yasbao.api.user.login&uid=4500&apiKey=f40b1da52dece67017dbb0c7830e586e&username=" + name + "&password=" + psw;
        new OkHttpUtils().getYzmJson(url, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                String[] str = data.split("@");
                if (str.length > 1) {
                    LoginCodeBean res = new Gson().fromJson(str[0], LoginCodeBean.class);
                    session = str[1];
                    SPUtils.put(context, Constant.MSESSION, session);
                    updateSessionFinish.requestDatas(session);
                    if (TextUtils.equals("LOGIN_SUCESS", res.getResult())) {
                        SPUtils.put(context, Constant.User_Uid, res.getUid().trim());
                        SPUtils.put(context, Constant.User_Phone, res.getUsername().trim());
                        SPUtils.put(context, Constant.User_Psw, res.getPassword().trim());

                    } else {
                        ToastUtils.toastForShort(context, res.getWorngMsg());
                    }
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                ToastUtils.toastForShort(context, "出错:" + meg);
            }
        });

    }
}
