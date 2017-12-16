package com.renhuikeji.wanlb.wanlibao;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.adapter.ListBaseAdapter;
import com.renhuikeji.wanlb.wanlibao.adapter.SuperViewHolder;
import com.renhuikeji.wanlb.wanlibao.bean.HomeRecyBean;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;
import com.renhuikeji.wanlb.wanlibao.views.LabelImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 * <p>
 * 普通商品展示适配器
 */

public class TestAdapter extends ListBaseAdapter<TestBean.GoodsArrBean> {
    GlideImageLoader imageLoader;
    private List<TestBean> beanList;

    public TestAdapter(Context context) {
        super(context);
        imageLoader = new GlideImageLoader(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.general_goods_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final TestBean.GoodsArrBean bean = mDataList.get(position);

        LinearLayout bigll = holder.getView(R.id.big);
        bigll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.toastForShort(mContext, position + "     " + bean.getClick_url());
            }
        });

        LabelImageView imageView = holder.getView(R.id.general_item_img); //图片
        TextView text_quan = holder.getView(R.id.text_quan); //领券省5元
        TextView tv_goodIntroduce = holder.getView(R.id.general_item_goodIntroduce); //商品介绍
        TextView tv_nowPrice = holder.getView(R.id.general_item_nowprice);  //现价
        TextView tv_oldprice = holder.getView(R.id.general_item_oldprice);  //原价
        TextView tv_shape1 = holder.getView(R.id.super_tv_haveshape);  //返
        TextView tv_shapeMiddle = holder.getView(R.id.super_tv_fanmiddle);  //返利
        TextView tv_liJifan = holder.getView(R.id.general_item_lijifan);  //立即返利
        LinearLayout linearLayout = holder.getView(R.id.super_ll_lingquan); //领券布局
        TextView tv_xiaoLiang = holder.getView(R.id.general_item_xiaoliang);  //销量
        TextView shopName = holder.getView(R.id.general_item_shopName);  //有效期
        TextView bottomAddress = holder.getView(R.id.general_item_bottomAddress);  //地址
        TextView tv_quanNum = holder.getView(R.id.super_tv_quanNum);  //领券钱数
        TextView tv_fanBi = holder.getView(R.id.super_tv_fanbi);  //返比
        TextView tv_biliNum = holder.getView(R.id.super_tv_fanbili);  //返比比例
        RelativeLayout rlSuperFanbi = holder.getView(R.id.rl_super_goods_fanbi);//  返比/券后价
        RelativeLayout rlGeneralGoods = holder.getView(R.id.rl_general_goods_totianmao);//  去天猫

        imageLoader.display(imageView, bean.getPict_url(), R.mipmap.icon_wanlibao_grey);
        tv_goodIntroduce.setText(bean.getTitle());


    }

    //------------------------------------------------
}
