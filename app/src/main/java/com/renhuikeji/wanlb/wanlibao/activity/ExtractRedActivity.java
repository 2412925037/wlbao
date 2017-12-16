package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 红包提取
 */
public class ExtractRedActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.web_extract_red)
    WebView webExtractRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_red);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        titleRightOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText("红包提取");
    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.title_right_one_btn:
                Intent extractIntent = new Intent(ExtractRedActivity.this, TaoBaoSearchActivity.class);
                startActivity(extractIntent);
                break;
        }
    }
}
