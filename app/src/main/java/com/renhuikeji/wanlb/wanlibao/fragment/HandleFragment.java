package com.renhuikeji.wanlb.wanlibao.fragment;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.MainActivity;
import com.renhuikeji.wanlb.wanlibao.activity.TaoBaoSearchActivity;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/4/20.
 * <p>
 * 操作指南
 */

public class HandleFragment extends Fragment {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.title_right_one_tv)
    TextView titleRightOneTv;
    Unbinder unbinder;
    private WebView webHandle;
    private View ll_fail;

    private MainActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View handleView = inflater.inflate(R.layout.fragment_handle, null);
        webHandle = (WebView) handleView.findViewById(R.id.web_handle);
        ll_fail = handleView.findViewById(R.id.load_fail_handle);
        unbinder = ButterKnife.bind(this, handleView);
        initViews();

        activity = (MainActivity) getActivity();

        RelativeLayout layout = (RelativeLayout) handleView.findViewById(R.id.relativeLayout1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            layout.setPadding(0, activity.getStatusHeight(), 0, 0);

        return handleView;
    }

    @Override
    public void onResume() {
        super.onResume();
       // initViews();
    }

    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        titleRightOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("操作指南");
        DialogUtils.showProgressDlg(getActivity(), "努力加载中……");
        webHandle.getSettings().setJavaScriptEnabled(true);
        webHandle.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                DialogUtils.stopProgressDlg();
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                webHandle.setVisibility(View.GONE);
                ll_fail.setVisibility(View.VISIBLE);
                DialogUtils.stopProgressDlg();
            }
        });
//        webHandle.loadUrl("https://www.taobao.com/");
//        webHandle.loadUrl("http://www.weiyunsd.cn/");
        webHandle.loadUrl(ConfigValue.HELP_CENTER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webHandle != null) {
            webHandle.destroy();
        }
        unbinder.unbind();
    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_btn, R.id.title_right_one_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.title_right_one_btn:
                startActivity(new Intent(getActivity(), TaoBaoSearchActivity.class));
                break;
            case R.id.title_right_one_tv:
                break;
        }
    }

    public void test(){
        if (webHandle.canGoBack()){
            webHandle.goBack();
        }
    }
    public boolean onBackPressed() {

        if(webHandle.canGoBack()) {

            return true;//监听back键，用于处理自己的点击事件
        }
        return false;//原生back效果
    }
}
