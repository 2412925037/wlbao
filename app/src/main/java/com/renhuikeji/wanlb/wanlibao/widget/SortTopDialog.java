package com.renhuikeji.wanlb.wanlibao.widget;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.widget.base.TopBaseDialog;
import com.renhuikeji.wanlb.wanlibao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 显示排序选项在顶部
 */

public class SortTopDialog extends TopBaseDialog<SortTopDialog> {


    @BindView(R.id.rel_fanli)
    RelativeLayout relFanli;
    @BindView(R.id.rel_xiaoliang)
    RelativeLayout relXiaoliang;
    @BindView(R.id.rel_jiage)
    RelativeLayout relJiage;
    private String string;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SortTopDialog(Context context) {
        super(context);
    }

    public SortTopDialog(Context context, String string) {
        super(context);
        this.string = string;
    }

    @Override
    public View onCreateView() {
        showAnim(new BounceTopEnter());
        dismissAnim(new SlideBottomExit());
        View inflate = View.inflate(mContext, R.layout.dialog_share, null);
        ButterKnife.bind(this, inflate);

        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        relFanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onOperItemClick(v);
            }
        });
        relXiaoliang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onOperItemClick(v);
            }
        });
        relJiage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickListener.onOperItemClick(v);
            }
        });
    }

    public interface OnItemClickListener {
        void onOperItemClick(View view);
    }


}
