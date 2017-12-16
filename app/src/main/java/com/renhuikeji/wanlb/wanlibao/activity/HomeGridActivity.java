package com.renhuikeji.wanlb.wanlibao.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeGridActivity extends BaseActivity {

    @BindView(R.id.yhd_web)
    WebView yhdWeb;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    private Unbinder unbinder;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yhd);
        unbinder = ButterKnife.bind(this);
        String param = getIntent().getStringExtra("param").trim();
        uid= (String) SPUtils.get(this, Constant.User_Uid,"");
        String url = getIntent().getStringExtra("url");

        titleLeftOneBtn.setVisibility(View.VISIBLE);
        initDatas(param);
        initWebView();
        yhdWeb.loadUrl(url);
    }

    private void initDatas(String param) {
        String title = "";
        if (TextUtils.equals(param, "juhuasuan")) {
            title = getString(R.string.grid_jhs);
        } else if (TextUtils.equals(param, "te")) {
            title = getString(R.string.grid_te);
        } else if (TextUtils.equals(param, "ai")) {
            title = getString(R.string.grid_ai);
        } else if (TextUtils.equals(param, "jd")) {
            title = getString(R.string.grid_jd);
        } else if (TextUtils.equals(param, "travel")) {
            title = getString(R.string.grid_travel);
        } else if (TextUtils.equals(param, "yhd")) {
            title = getString(R.string.grid_yhd);
        } else if (TextUtils.equals(param, "mogujie")){
            title = getString(R.string.grid_mogujie);
        } else if (TextUtils.equals(param, "vip")){
            title = getString(R.string.grid_vip);
        } else if (TextUtils.equals(param, "gome")){
            title = getString(R.string.grid_guomei);
        } else if (TextUtils.equals(param, "suning")){
            title = getString(R.string.grid_suning);
        } else if (TextUtils.equals(param, "meilishuo")){
            title = getString(R.string.grid_meilishuo);
        } else if (TextUtils.equals(param, "xssl")){
            title = getString(R.string.grid_xinshou);
        }
        tvMiddleTitle.setText(title);
    }

    private void initWebView() {
        DialogUtils.showProgressDlg(HomeGridActivity.this, "努力加载中....");
        yhdWeb.getSettings().setJavaScriptEnabled(true);
        yhdWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//              view.loadUrl(request.getUrl().toString());
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                DialogUtils.stopProgressDlg();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && yhdWeb.canGoBack()) {
            yhdWeb.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.title_left_one_btn)
    public void onViewClicked() {
        this.finish();
    }
    @Override
    protected void onDestroy() {
        if (yhdWeb != null) {
            yhdWeb.destroy();
        }
        unbinder.unbind();
        super.onDestroy();
    }

}
