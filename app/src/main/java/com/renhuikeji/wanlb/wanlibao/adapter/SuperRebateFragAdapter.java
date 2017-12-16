package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.HomeRecyBean;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;
import com.renhuikeji.wanlb.wanlibao.views.LabelImageView;

/**
 * Created by Administrator on 2017/4/22.
 *
 * 超级返利
 */

public class SuperRebateFragAdapter extends ListBaseAdapter<HomeRecyBean> {

    GlideImageLoader imageLoader;

    public SuperRebateFragAdapter(Context context) {
        super(context);
        imageLoader = new GlideImageLoader(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.super_rebate_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {

        HomeRecyBean bean = mDataList.get(position);

        LabelImageView imageView = holder.getView(R.id.superfanli_img); //图片
        TextView text_quan = holder.getView(R.id.super_text_quan); //领券省5元
        TextView tv_goodIntroduce = holder.getView(R.id.super_goodIntroduce); //商品介绍
        TextView tv_nowPrice = holder.getView(R.id.super_tv_nowprice);  //现价
        TextView tv_oldPrice = holder.getView(R.id.super_tv_oldprice);  //原价
        TextView tv_address = holder.getView(R.id.super_tv_address);  //所在地
        TextView tv_shape1 = holder.getView(R.id.super_tv_haveshape);  //返
        TextView tv_shapeMiddle = holder.getView(R.id.super_tv_fanmiddle);  //返利
        TextView tv_liJifan = holder.getView(R.id.super_tv_lijifan);  //立即返利

        LinearLayout linearLayout = holder.getView(R.id.super_ll_lingquan); //领券布局
        TextView tv_lingQuan = holder.getView(R.id.super_tv_lingquan);  //领券
        TextView tv_quanNum = holder.getView(R.id.super_tv_quanNum);  //领券钱数

        TextView tv_fanBi = holder.getView(R.id.super_tv_fanbi);  //返比
        TextView tv_biliNum = holder.getView(R.id.super_tv_fanbili);  //返比比例
        TextView tv_xiaoLiang = holder.getView(R.id.super_tv_xiaoliang);  //销量


        TextView shopName = holder.getView(R.id.super_tv_shopName);  //有效期


        imageLoader.display(imageView, bean.imgUrl, R.mipmap.icon_wanlibao_grey);
        tv_nowPrice.setText(bean.newprices);
        if (null == bean.isSeller) {
            imageView.setLabelVisable(false);
        } else {
            imageView.setLabelVisable(true);
            imageView.setTextContent("推荐");
            imageView.setLabelBackGroundColor(Color.RED);
        }
        if (null == bean.valid_time){
            shopName.setVisibility(View.GONE);
        }else {
            shopName.setText("有效期至" + bean.valid_time);
        }
        switch (bean.type) {
            case 0:  //返利
                text_quan.setVisibility(View.GONE);
                tv_goodIntroduce.setText(bean.goodIntroduce);
                tv_oldPrice.setText(bean.oldprices);
                tv_address.setText(bean.address);
                tv_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

                tv_shape1.setVisibility(View.VISIBLE);
                tv_shapeMiddle.setText(bean.fan_num);
                tv_liJifan.setText("收货后立返");
                linearLayout.setVisibility(View.GONE);

                tv_fanBi.setVisibility(View.VISIBLE);
                tv_biliNum.setText(bean.fan_bi);
                tv_xiaoLiang.setText("销量" + bean.sales);
                break;
            case 1:  //有券
                text_quan.setVisibility(View.VISIBLE);
                text_quan.setText("领券省\r\n" + bean.quan_num + "元");
                tv_goodIntroduce.setText(bean.goodIntroduce);
                tv_oldPrice.setText(bean.oldprices);
                tv_address.setText(bean.address);
                tv_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

                tv_shape1.setVisibility(View.GONE);
                tv_shapeMiddle.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                tv_quanNum.setText(bean.quan_num + "元");
                tv_liJifan.setText("下单立减");

                tv_fanBi.setVisibility(View.GONE);
                tv_biliNum.setText("券后价" + bean.quan_hou_num);
                tv_xiaoLiang.setText("销量" + bean.sales);
                break;
        }


    }
}
