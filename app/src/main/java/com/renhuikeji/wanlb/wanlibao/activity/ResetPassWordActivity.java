package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.ResetPWbean;
import com.renhuikeji.wanlb.wanlibao.bean.YzmBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.CheckUtil;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.KeyBoardUtils;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.TimeCount;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPassWordActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.edit_passwore_new)
    EditText edit_new;
    @BindView(R.id.edit_passwore_new_sure)
    EditText edit_newSure;
    @BindView(R.id.alter_passwore_commit)
    TextView alterPassworeCommit;
    @BindView(R.id.time_tv)
    TextView tv_daojishi;
    @BindView(R.id.edit_getphone_number)
    EditText edit_phone;
    @BindView(R.id.edit_get_yzm)
    EditText edit_yan;

    private String session = "";
    private TimeCount timeCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_word);
        ButterKnife.bind(this);
        initTop();
    }

    private void initTop() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("重置密码");
        //倒计时
        timeCount = new TimeCount(tv_daojishi, 120000, 1000);

        //点击输入框显示光标
        edit_new.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    edit_new.setCursorVisible(true);
                }
                return false;
            }
        });
        String user_phone = (String) SPUtils.get(ResetPassWordActivity.this, Constant.User_Phone, "");
        if (!TextUtils.isEmpty(user_phone)) {
            edit_phone.setText(user_phone);
        } else {
            edit_phone.setHint("请输入手机号");
        }

    }

    @OnClick({R.id.title_left_one_btn, R.id.alter_passwore_commit, R.id.time_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.alter_passwore_commit: //提交
                if (checkYanZhengMa() && checkPassWord()) {
                    //提交
                    commitInfo();
                }
                break;
            case R.id.time_tv:   //倒计时
                //获取验证码之前先判断密码是否一致，是否符合要求（6--20位组成）
                if (checkPassWord()) {
                    sendYZM();
                }
                break;
        }
    }

    /**
     * 提交信息
     */
    private void commitInfo() {
        Log.d("ddd", "session: " + session);
        DialogUtils.showProgressDlg(ResetPassWordActivity.this, "正在重置密码.....");
        KeyBoardUtils.closeKeybord(edit_yan, ResetPassWordActivity.this);
        String uid = (String) SPUtils.get(ResetPassWordActivity.this, Constant.User_Uid, "4500");
        String re_phone = edit_phone.getText().toString().trim();
        String re_yan = edit_yan.getText().toString().trim();
        String re_psw = edit_new.getText().toString().trim();
        String url = "http://app.yasbao.com/Home/Api/gw?api=yasbao.api.user.resetpwd&uid=" + uid + "&apiKey=" + ConfigValue.API_KEY + "&mobile=" + re_phone + "&yan=" + re_yan + "&password=" + re_psw;
        new OkHttpUtils().getDatas(this,url, session, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                DialogUtils.stopProgressDlg();
                Log.d("ddd", "   " + data);
                ResetPWbean pWbean = new Gson().fromJson(data, ResetPWbean.class);
                if (TextUtils.equals("WRONG", pWbean.getResult())) {
                    ToastUtils.toastForShort(ResetPassWordActivity.this, pWbean.getWorngMsg());
                    edit_new.setText("");
                    edit_yan.setText("");
                    edit_newSure.setText("");
                    if (!edit_new.hasFocus()) {
                        edit_new.requestFocus();
                    }
                    //取消计时器

                } else {
                    //修改成功后，修改SP中的数据，跳转到登录界面重新登录
                    ToastUtils.toastForShort(ResetPassWordActivity.this, "修改密码成功");
                    SPUtils.put(ResetPassWordActivity.this, Constant.User_Psw, pWbean.getPassword());
                    Intent intent = new Intent(ResetPassWordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    ResetPassWordActivity.this.finish();
                }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                DialogUtils.stopProgressDlg();
                ToastUtils.toastForShort(ResetPassWordActivity.this, "修改密码失败");
            }
        });
    }

    /**
     * 发送验证码
     * 带上session
     */
    private void sendYZM() {
        String trim = edit_phone.getText().toString().trim();
        String url = "http://app.yasbao.com/Home/Api/gw?api=yasbao.api.user.getcode&apiKey=f40b1da52dece67017dbb0c7830e586e&mobile=" + trim;
        new OkHttpUtils().getYzmJson(url, new OkHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                //{"result":"SUCESS","infocode":595766,"mobile":"18039513911","infotime":1495100103}@PHPSESSID=mtbnstvknse64b4i00h12gmvq6
                String[] str = data.split("@");
                String res="";
                if (str.length <2) {
                    ToastUtils.toastForShort(ResetPassWordActivity.this, "获取数据失败,请检查网络!");
                    return;
                }
                    session = str[1];
                    res=str[0];
                YzmBean bean=new Gson().fromJson(res,YzmBean.class);
                    if(TextUtils.equals(bean.getResult(),"SUCESS")){
                        timeCount.start();
                        ToastUtils.toastForShort(ResetPassWordActivity.this, "已发送验证码");
                    }else{
                        ToastUtils.toastForShort(ResetPassWordActivity.this, bean.getWorngMsg());
                    }
            }

            @Override
            public void onError(String meg) {
                super.onError(meg);
                ToastUtils.toastForShort(ResetPassWordActivity.this, "验证码获取失败");
            }
        });


    }

    /**
     * 检验验证码
     */
    private boolean checkYanZhengMa() {
        String str_yan = edit_yan.getText().toString().trim();
        if (str_yan.length() < 6 || TextUtils.isEmpty(str_yan)) {
            ToastUtils.toastForShort(ResetPassWordActivity.this, "验证码错误！");
            return false;
        }
        if (!CheckUtil.isPhoneNum(edit_phone.getText().toString().trim())) {
            ToastUtils.toastForShort(ResetPassWordActivity.this, "手机号错误！");
            return false;
        }

        return true;
    }

    /**
     * 检验密码
     */
    private boolean checkPassWord() {
        String string_new = edit_new.getText().toString().trim();
        String string_sure = edit_newSure.getText().toString().trim();
        String str_phone = edit_phone.getText().toString().trim();

        if (!TextUtils.equals(string_new, string_sure)) {
            ToastUtils.toastForShort(ResetPassWordActivity.this, "密码不一致！");
            return false;
        }
        if (string_new.length() < 6) {
            ToastUtils.toastForShort(ResetPassWordActivity.this, "新密码不能少于6位！");
            return false;
        }
        if (TextUtils.isEmpty(str_phone)) {
            ToastUtils.toastForShort(ResetPassWordActivity.this, "用户手机号不能为空！");
            return false;
        }
        if (!CheckUtil.isPhoneNum(str_phone)) {
            ToastUtils.toastForShort(ResetPassWordActivity.this, "手机号错误！");
            return false;
        }
        //不能去除空行去判断，因为这样无法判断密码中是否含有空格
        if (!CheckUtil.checkPassword(edit_new.getText().toString())) {
            ToastUtils.toastForShort(ResetPassWordActivity.this, "密码格式不正确");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁倒计时器
        if (timeCount != null) {
            timeCount.cancel();
        }
    }
    //-------------------------------------
}
