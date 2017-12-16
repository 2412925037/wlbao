package com.renhuikeji.wanlb.wanlibao.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.widget.base.TopBaseDialog;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.FindBillsTopAdapter;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 认领订单顶部分类导航
 */

public class FindBillsTopDialog extends TopBaseDialog<FindBillsTopDialog> {

    @BindView(R.id.tl_find_bills_top)
    TabLayout tlFindBillsTop;
    @BindView(R.id.img_find_bills_top)
    ImageView imgFindBillsTop;
    @BindView(R.id.gv_find_bills_top)
    GridView gvFindBillsTop;
    private String string;
    private List<String> list = new ArrayList<>();
    private Context context;

    public FindBillsTopDialog(Context context) {
        super(context);
        this.context = context;
    }

    public FindBillsTopDialog(Context context, String string) {
        super(context);
        this.context = context;
        this.string = string;
    }

    @Override
    public View onCreateView() {
        showAnim(new BounceTopEnter());
        dismissAnim(new SlideBottomExit());
        View inflate = View.inflate(mContext, R.layout.dialog_find_bills_top, null);
        ButterKnife.bind(this, inflate);

        initViews();

        return inflate;
    }

    private void initFindBills() {
        list.add("内衣");
        list.add("饰品");
        list.add("手表");
        list.add("美妆");
        list.add("母婴");
        list.add("珠宝");
        list.add("家装");
        list.add("数码");
        list.add("食品");
        list.add("汽车");
    }


    private void initViews() {
        tlFindBillsTop.addTab(tlFindBillsTop.newTab().setText("女装"));
        tlFindBillsTop.addTab(tlFindBillsTop.newTab().setText("女鞋"));
        tlFindBillsTop.addTab(tlFindBillsTop.newTab().setText("箱包"));
        tlFindBillsTop.addTab(tlFindBillsTop.newTab().setText("男装"));
        tlFindBillsTop.addTab(tlFindBillsTop.newTab().setText("男鞋"));

    initFindBills();
        gvFindBillsTop.setAdapter(new FindBillsTopAdapter(context, list));
    }

    @Override
    public void setUiBeforShow() {
        imgFindBillsTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindBillsTopDialog.this.dismiss();
            }
        });
        tlFindBillsTop.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        gvFindBillsTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtils.toastForShort(context, list.get(i));
                FindBillsTopDialog.this.dismiss();
            }
        });
    }
}
