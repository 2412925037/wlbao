package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;

/**
 * Created by Administrator on 2017/4/21.
 */

public class HomeFragGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int[] images;
    private String[] text;
    GlideImageLoader imageLoader ;

    public HomeFragGridAdapter(Context context, int[] images, String[] text) {
        this.context = context;
        this.images = images;
        this.text = text;
        layoutInflater = LayoutInflater.from(context);
        imageLoader = new GlideImageLoader(context);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.item_grideview_layout, null);
        ImageView iv = (ImageView) v.findViewById(R.id.iv_gridView_item);
        TextView tv = (TextView) v.findViewById(R.id.tv_gridView_item);
        imageLoader.display(iv, images[position]);
        tv.setText(text[position]);
        return v;
    }
}
