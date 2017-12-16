package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.MyFragmentItem;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/20.
 * <p>
 * 个人中心的RecyclerView展示适配器
 */

public class MyFragmentGridViewAdapter extends RecyclerView.Adapter<MyFragmentGridViewAdapter.ViewHolder> {
    private Context context;
    private List<MyFragmentItem> items;

    public MyFragmentGridViewAdapter(Context context, List<MyFragmentItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_gridview_my_fragment, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyFragmentItem item = items.get(position);
        new GlideImageLoader(context).display(holder.imgItemMyFragment,item.getIconResoures());
        holder.tvItemMyFragment.setText(item.getItemName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_my_fragment)
        ImageView imgItemMyFragment;
        @BindView(R.id.tv_item_my_fragment)
        TextView tvItemMyFragment;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            imgItemMyFragment = (ImageView) view.findViewById(R.id.img_item_my_fragment);
            tvItemMyFragment = (TextView) view.findViewById(R.id.tv_item_my_fragment);
        }
    }
}
