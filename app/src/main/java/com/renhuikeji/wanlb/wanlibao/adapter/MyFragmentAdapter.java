package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.MyFragmentItem;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/22.
 */

public class MyFragmentAdapter extends BaseAdapter {
    private Context context;
    private List<MyFragmentItem> items;

    public MyFragmentAdapter(Context context, List<MyFragmentItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyFragmentItem item = items.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_gridview_my_fragment, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        new GlideImageLoader(context).display(holder.imgItemMyFragment, item.getIconResoures());
        holder.tvItemMyFragment.setText(item.getItemName());
        return view;
    }

    class ViewHolder {
        @BindView(R.id.img_item_my_fragment)
        ImageView imgItemMyFragment;
        @BindView(R.id.tv_item_my_fragment)
        TextView tvItemMyFragment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
