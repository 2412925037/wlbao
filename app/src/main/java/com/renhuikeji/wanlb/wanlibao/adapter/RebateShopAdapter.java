package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;

/**
 * Created by Administrator on 2017/5/22.
 *
 * 商城返利的适配器
 */

public class RebateShopAdapter extends BaseAdapter {
    private int[] imgs;
    private Context context;

    public RebateShopAdapter(int[] imgs, Context context) {
        this.imgs = imgs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int i) {
        return imgs[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = new ImageView(context);
        ViewGroup.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        view.setLayoutParams(params);
        view.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        ((ImageView)view).setImageDrawable(context.getResources().getDrawable(imgs[i]));
        ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_CENTER);
        view.setPadding(15, 25, 15, 25);
        return view;
    }
}
