package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.SecondClassItem;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/4.
 *
 * 普通商品右侧展示的适配器
 */

public class GeneralGoodsRightAdapter extends BaseAdapter {
    private Context context;
    private List<SecondClassItem> goodDetails = new ArrayList<>();

    public GeneralGoodsRightAdapter(Context context, List<SecondClassItem> goodDetails) {
        this.context = context;
        this.goodDetails = goodDetails;
    }

    public void clear(){
        if (null != goodDetails) {
            goodDetails.clear();
        }
    }

    @Override
    public int getCount() {
        return goodDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return goodDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SecondClassItem generalGoodBean = goodDetails.get(i);
        ViewHolder holder = null;
        if (null == view){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_genetal_goods_right,viewGroup,false);

            holder.mImgIcon = (ImageView) view.findViewById(R.id.img_general_goods_icon);
            holder.mTvName = (TextView) view.findViewById(R.id.tv_general_goods_name);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

            holder.mTvName.setText(generalGoodBean.getName());
        if (null == generalGoodBean.getUrl() || "".equals(generalGoodBean.getUrl())){
            new GlideImageLoader(context).display(holder.mImgIcon, R.mipmap.icon_wanlibao_grey);
        }else {
            new GlideImageLoader(context).display(holder.mImgIcon, generalGoodBean.getUrl());
        }
        return view;
    }

    class ViewHolder{
        private ImageView mImgIcon;
        private TextView mTvName;
    }
}
