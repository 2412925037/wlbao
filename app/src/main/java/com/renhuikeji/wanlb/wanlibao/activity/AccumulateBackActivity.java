package com.renhuikeji.wanlb.wanlibao.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 累计返利界面
 */
public class AccumulateBackActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.rb_accumulate_shouru)
    RadioButton rbAccumulateShouru;
    @BindView(R.id.rb_accumulate_zhichu)
    RadioButton rbAccumulateZhichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accumulate_back);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        rbAccumulateShouru.setChecked(true);
        titleLeftOneBtn.setImageDrawable(getResources().getDrawable(R.mipmap.icon_back_grey));
    }

    @OnClick({R.id.title_left_one_btn, R.id.rb_accumulate_shouru, R.id.rb_accumulate_zhichu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.rb_accumulate_shouru:
                break;
            case R.id.rb_accumulate_zhichu:
                break;
        }
    }
}
