package com.renhuikeji.wanlb.wanlibao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.interf.IBackInterface;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;

/**
 * 淘宝天猫
 */

public class TaoBaoFragment extends Fragment {
    public WebView webTaobao = null;
    private LinearLayout ll_fail = null;
    private View tbView;
    private IBackInterface backInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        tbView = inflater.inflate(R.layout.fragment_taobao, null);
        webTaobao = (WebView) tbView.findViewById(R.id.web_taobao);
        ll_fail = (LinearLayout) tbView.findViewById(R.id.ll_load_fail);
        initViews();

        backInterface = (IBackInterface)getActivity();
        backInterface.setSelectedFragment(this);//将fragment传递到Activity中
        return tbView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AliUtils.showUrl(getActivity(), "https://m.taobao.com/");
    }

    @Override
    public void onResume() {
        super.onResume();
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        getView().setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(backlistener);
//    }
//
//    private View.OnKeyListener backlistener = new View.OnKeyListener() {
//        @Override
//        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
//            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
//                ToastUtils.toastForShort(getActivity(), "jjj");
//                Log.d("www", "onKey: " + "jjj");
//                return true;    //已处理
//            }
//            return false;
//        }
//    };

    public void test(){
        if (webTaobao.canGoBack()){
            webTaobao.goBack();
        }
    }
    public boolean onBackPressed() {

        if(webTaobao.canGoBack()) {

            return true;//监听back键，用于处理自己的点击事件
        }
        return false;//原生back效果
    }
    private void initViews() {
        DialogUtils.showProgressDlg(getActivity(),"加载中...");
        webTaobao.loadUrl("https://m.taobao.com/");
        webTaobao.getSettings().setJavaScriptEnabled(true);
//        int screenDensity = getResources().getDisplayMetrics().densityDpi ;
//        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.FAR;
//        Log.d("CCC","i:"+screenDensity);
      /*  switch (screenDensity){
            case DisplayMetrics.DENSITY_LOW :
                zoomDensity = WebSettings.ZoomDensity.CLOSE;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                zoomDensity = WebSettings.ZoomDensity.FAR;
                break ;
        }*/
//        webSettings.setDefaultZoom(zoomDensity);
        WebSettings webSettings = webTaobao.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webTaobao.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return super.shouldOverrideUrlLoading(webView, s);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                DialogUtils.stopProgressDlg();
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                webTaobao.setVisibility(View.GONE);
                ll_fail.setVisibility(View.VISIBLE);
                DialogUtils.stopProgressDlg();
                ll_fail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initViews();
                    }
                });
            }



          /*  @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                DialogUtils.stopProgressDlg();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                webTaobao.setVisibility(View.GONE);
                ll_fail.setVisibility(View.VISIBLE);
                DialogUtils.stopProgressDlg();
                ll_fail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initViews();
                    }
                });
            }*/
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webTaobao != null) {
            webTaobao.destroy();
        }
    }
}
