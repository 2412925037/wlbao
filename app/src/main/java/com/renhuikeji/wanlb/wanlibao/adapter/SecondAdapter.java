package com.renhuikeji.wanlb.wanlibao.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.GeneralGoodsActivity;
import com.renhuikeji.wanlb.wanlibao.bean.SecondBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/5/18.
 * <p>
 * 分类二级展示适配器
 */

public class SecondAdapter extends SectionedRecyclerViewAdapter<SecondAdapter.SecondViewHolder, SecondAdapter.ThirdViewHolder, RecyclerView.ViewHolder> {
    private Context context;
    private List<SecondBean> secondBeen = new ArrayList<>();
    private int type;

    public SecondAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    public void addAll(List<SecondBean> been) {
        secondBeen.addAll(been);
    }

    public void clear() {
        secondBeen.clear();
    }

    @Override
    protected int getSectionCount() {
        return null == secondBeen ? 0 : secondBeen.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return null == secondBeen.get(section).getCatBeen() ? 0 : secondBeen.get(section).getCatBeen().size();
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected SecondViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_second_cat, parent, false);
        return new SecondViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected ThirdViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_third_cat, parent, false);
        return new ThirdViewHolder(view);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(SecondViewHolder holder, int section) {
        holder.tvSecond.setText(secondBeen.get(section).getTitle());
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {
        //不显示底部布局
    }

    @Override
    protected void onBindItemViewHolder(ThirdViewHolder holder, final int section, final int position) {
        holder.tvThird.setText(secondBeen.get(section).getCatBeen().get(position).getTitle());
        new GlideImageLoader(context).display(holder.imgThird, ConfigValue.PIC_FRONT +
                secondBeen.get(section).getCatBeen().get(position).getPict_url(), R.mipmap.icon_wanlibao_grey);
//        holder.tvThird.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, GeneralGoodsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("q", secondBeen.get(section).getCatBeen().get(position).getQ());
//                bundle.putInt("type", type);
//                bundle.putString("cid", secondBeen.get(section).getCatBeen().get(position).getCid());
//                bundle.putBoolean("isFromSearch", false);
//                intent.putExtra("search", bundle);
//                context.startActivity(intent);
//                ((Activity) context).finish();
//            }
//        });

//        holder.imgThird.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, GeneralGoodsActivity.class);
//                Bundle bundle = new Bundle();
//                String str_keyword = secondBeen.get(section).getCatBeen().get(position).getQ();
//                bundle.putString("q", str_keyword);
//                bundle.putInt("type", type);
//                bundle.putString("cid", secondBeen.get(section).getCatBeen().get(position).getCid());
//                bundle.putString("cat", secondBeen.get(section).getCatBeen().get(position).getCat());
//                bundle.putBoolean("isFromSearch", false);
//                intent.putExtra("search", bundle);
//
////                if (!TextUtils.isEmpty(str_keyword)) {
////                    SPUtils.put(context, Constant.HISTORY_STR, str_keyword);
////                    Log.d("ccc", "adapter "+str_keyword);
////                    SPUtils.put(context, Constant.BACKWHERE, "general");
////                }
//
//                context.startActivity(intent);
//                ((Activity) context).finish();
//            }
//        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GeneralGoodsActivity.class);
                Bundle bundle = new Bundle();
                String str_keyword = secondBeen.get(section).getCatBeen().get(position).getQ();
                bundle.putString("q", str_keyword);
                bundle.putInt("type", type);
                bundle.putString("cid", secondBeen.get(section).getCatBeen().get(position).getCid());
                bundle.putString("cat", secondBeen.get(section).getCatBeen().get(position).getCat());
                bundle.putBoolean("isFromSearch", false);
                intent.putExtra("search", bundle);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

    }

    class SecondViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSecond;

        public SecondViewHolder(View itemView) {
            super(itemView);
            tvSecond = (TextView) itemView.findViewById(R.id.tv_second_cat_item);
        }
    }

    class ThirdViewHolder extends RecyclerView.ViewHolder {
        private TextView tvThird;
        private ImageView imgThird;
        private LinearLayout linearLayout;

        public ThirdViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.classfy_ll);
            tvThird = (TextView) itemView.findViewById(R.id.tv_third_cat_item);
            imgThird = (ImageView) itemView.findViewById(R.id.img_third_cat_item);
        }
    }
}
