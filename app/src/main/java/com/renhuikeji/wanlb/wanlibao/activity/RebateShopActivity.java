package com.renhuikeji.wanlb.wanlibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.RebateShopAdapter;
import com.renhuikeji.wanlb.wanlibao.bean.IndexGoodBean;
import com.renhuikeji.wanlb.wanlibao.bean.MallListBean;
import com.renhuikeji.wanlb.wanlibao.views.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 返利商城
 */
public class RebateShopActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOneBtn;
    @BindView(R.id.mgv_rebate_shop)
    MyGridView mgvRebateShop;
    private boolean isFromIndex = true;
    private IndexGoodBean.MallArrBean mallArrBean;
    private MallListBean.MallArrBean mallArrBean2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_shop);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String mallClassStyle = intent.getStringExtra("mall_class_style");
        if ("mallListBean".equals(mallClassStyle)){
            isFromIndex = false;
            mallArrBean2 = (MallListBean.MallArrBean) intent.getSerializableExtra("mallArr");
        }else {
            isFromIndex = true;
            mallArrBean = (IndexGoodBean.MallArrBean) intent.getSerializableExtra("mallArr");
        }

        initViews();
    }

    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        titleRightOneBtn.setVisibility(View.VISIBLE);
        titleRightOneBtn.setImageDrawable(getResources().getDrawable(R.mipmap.icon_classify));
        tvMiddleTitle.setText("商城返利");

        int[] imgs = {R.mipmap.icon_taobao, R.mipmap.icon_jingd, R.mipmap.icon_guomei,
                R.mipmap.icon_suning, R.mipmap.icon_vip, R.mipmap.icon_meilishuo,
                R.mipmap.icon_yihaodian1, R.mipmap.icon_mogujie};
        mgvRebateShop.setAdapter(new RebateShopAdapter(imgs, RebateShopActivity.this));
        mgvRebateShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RebateShopActivity.this, HomeGridActivity.class);
                if (isFromIndex) {
                    switch (i) {
                        case 0:
                            intent.setClass(RebateShopActivity.this,TaoBaoSearchActivity.class);
                            /*intent.putExtra("param", "ai");
                            intent.putExtra("url", mallArrBean.getAi());*/
                            break;
                        case 1:
                            intent.putExtra("param", "jd");
                            intent.putExtra("url", mallArrBean.getJd());
                            break;
                        case 2:
                            intent.putExtra("param", "gome");
                            intent.putExtra("url", mallArrBean.getGome());
                            break;
                        case 3:
                            intent.putExtra("param", "suning");
                            intent.putExtra("url", mallArrBean.getSuning());
                            break;
                        case 4:
                            intent.putExtra("param", "vip");
                            intent.putExtra("url", mallArrBean.getVip());
                            break;
                        case 5:
                            intent.putExtra("param", "meilishuo");
                            intent.putExtra("url", mallArrBean.getMeilishuo());
                            break;
                        case 6:
                            intent.putExtra("param", "yhd");
                            intent.putExtra("url", mallArrBean.getYhd());
                            break;
                        case 7:
                            intent.putExtra("param", "mogujie");
                            intent.putExtra("url", mallArrBean.getMogujie());
                            break;
                    }
                }else {
                    switch (i) {
                        case 0:
                            intent.setClass(RebateShopActivity.this,TaoBaoSearchActivity.class);
                           /* intent.putExtra("param", "ai");
                            intent.putExtra("url", mallArrBean2.getAi());*/
                            break;
                        case 1:
                            intent.putExtra("param", "jd");
                            intent.putExtra("url", mallArrBean2.getJd());
                            break;
                        case 2:
                            intent.putExtra("param", "gome");
                            intent.putExtra("url", mallArrBean2.getGome());
                            break;
                        case 3:
                            intent.putExtra("param", "suning");
                            intent.putExtra("url", mallArrBean2.getSuning());
                            break;
                        case 4:
                            intent.putExtra("param", "vip");
                            intent.putExtra("url", mallArrBean2.getVip());
                            break;
                        case 5:
                            intent.putExtra("param", "meilishuo");
                            intent.putExtra("url", mallArrBean2.getMeilishuo());
                            break;
                        case 6:
                            intent.putExtra("param", "yhd");
                            intent.putExtra("url", mallArrBean2.getYhd());
                            break;
                        case 7:
                            intent.putExtra("param", "mogujie");
                            intent.putExtra("url", mallArrBean2.getMogujie());
                            break;
                    }
                }
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.title_right_one_btn:
                startActivity(new Intent(RebateShopActivity.this, ClassifyActivity.class));
                break;
        }
    }
}
