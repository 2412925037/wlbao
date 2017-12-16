package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.LoginActivity;
import com.renhuikeji.wanlb.wanlibao.bean.GetClickUrlBean;
import com.renhuikeji.wanlb.wanlibao.bean.HomeRecyBean;
import com.renhuikeji.wanlb.wanlibao.bean.VisitResultBean;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.AliUtils.AliUtils;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.OkHttpUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;
import com.renhuikeji.wanlb.wanlibao.views.LabelImageView;

/**
 * Created by Administrator on 2017/4/21.
 * <p>
 * 首页推荐商品展示适配器
 */

public class HomeRecyAdapter extends ListBaseAdapter<HomeRecyBean> {
    GlideImageLoader imageLoader;
    String uid;
    String session;
    private Context context;

    public HomeRecyAdapter(Context context) {
        super(context);
        this.context=context;
        imageLoader = new GlideImageLoader(context);
        uid = (String) SPUtils.get(context, Constant.User_Uid, "");
        session = (String) SPUtils.get(context, Constant.MSESSION, "");
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_recy_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        final HomeRecyBean bean = mDataList.get(position);
        LinearLayout llGoodsItem = holder.getView(R.id.ll_goods_item);

        LabelImageView imageView = holder.getView(R.id.imageview); //图片
        TextView text_quan = holder.getView(R.id.text_quan); //领券省5元
        TextView tv_goodIntroduce = holder.getView(R.id.tv_goodIntroduce); //商品介绍
        TextView tv_nowPrice = holder.getView(R.id.tv_nowprice);  //现价
        TextView tv_oldPrice = holder.getView(R.id.tv_oldprice);  //原价
        TextView tv_address = holder.getView(R.id.tv_address);  //所在地
        TextView tv_shape1 = holder.getView(R.id.tv_haveshape);  //返
        TextView tv_shapeMiddle = holder.getView(R.id.tv_fanmiddle);  //返利
        TextView tv_liJifan = holder.getView(R.id.tv_lijifan);  //立即返利

        LinearLayout linearLayout = holder.getView(R.id.ll_lingquan); //领券布局
        TextView tv_lingQuan = holder.getView(R.id.tv_lingquan);  //领券
        TextView tv_quanNum = holder.getView(R.id.tv_quanNum);  //领券钱数

        TextView tv_fanBi = holder.getView(R.id.tv_fanbi);  //返比
        TextView tv_biliNum = holder.getView(R.id.tv_fanbili);  //返比比例
        TextView tv_xiaoLiang = holder.getView(R.id.tv_xiaoliang);  //销量
        TextView goToTianMao = holder.getView(R.id.tv_quTianMao);  //去天猫

        TextView shopName = holder.getView(R.id.tv_shopName);  //有效期和店铺名

        imageLoader.display(imageView, bean.imgUrl, R.mipmap.icon_wanlibao_grey);
        if (!"1".equals(bean.isSeller) || null == bean.isSeller) {
            imageView.setLabelVisable(false);
        } else {
            imageView.setLabelVisable(true);
        }

        if (null != bean.quan_num && Double.parseDouble(bean.quan_num) > 0) {
            text_quan.setVisibility(View.VISIBLE);
            text_quan.setText("领券省\r\n" + bean.quan_num + "元");
        } else {
            text_quan.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(bean.newprices)) {
            tv_nowPrice.setText("");
        } else {
            tv_nowPrice.setText("￥" + bean.newprices);
        }

        if (TextUtils.isEmpty(bean.oldprices)) {
            tv_oldPrice.setText("");
        } else {
            tv_oldPrice.setText("￥" + bean.oldprices);
            tv_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        }

        if (TextUtils.isEmpty(bean.address)) {
            tv_address.setText("");
        } else {
            tv_address.setText(bean.address);
        }

        if (TextUtils.isEmpty(bean.sales)) {
            tv_xiaoLiang.setText("");
        } else {
            tv_xiaoLiang.setText("销量" + bean.sales);
        }
        //tv_goodIntroduce.setText(bean.goodIntroduce);
        if (TextUtils.isEmpty(bean.goodIntroduce)) {
            tv_goodIntroduce.setText("");
        } else {
            if (TextUtils.equals("1", bean.user_type)) {
                ImageSpan imgSpan = new ImageSpan(mContext, R.mipmap.icon_tianmao);
                SpannableString spannableString = new SpannableString("天猫 " + bean.goodIntroduce);
                spannableString.setSpan(imgSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_goodIntroduce.setText(spannableString);
            } else {
                ImageSpan imgSpan = new ImageSpan(mContext, R.mipmap.icon_qutaobao);
                SpannableString spannableString = new SpannableString("淘宝 " + bean.goodIntroduce);
                spannableString.setSpan(imgSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_goodIntroduce.setText(spannableString);
            }

        }

        tv_liJifan.setTextColor(context.getResources().getColor(R.color.grey_text));
        switch (bean.type) {
            case 1:  //有券
                tv_shape1.setVisibility(View.GONE);
                tv_shapeMiddle.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                tv_quanNum.setText(bean.quan_num + "元");
                tv_liJifan.setTextColor(context.getResources().getColor(R.color.fuzhu_red));
                tv_liJifan.setText("再返"+bean.getReturn_num());

                tv_fanBi.setVisibility(View.GONE);
                goToTianMao.setVisibility(View.GONE);
                tv_biliNum.setVisibility(View.VISIBLE);
                tv_biliNum.setText("券后价￥" + bean.quan_hou_num);

                if (null != bean.valid_time) {
                    shopName.setText("有效期至" + bean.valid_time);
                } else {
                    shopName.setText("");
                }

                break;
            case 0:  //返利
                imageView.setTextContent("推荐");
                imageView.setLabelBackGroundColor(Color.RED);

                linearLayout.setVisibility(View.GONE);
                tv_shape1.setVisibility(View.VISIBLE);
                tv_shapeMiddle.setVisibility(View.VISIBLE);
                tv_shapeMiddle.setText("￥" + bean.fan_num);
                tv_liJifan.setText("收货后立返");

                goToTianMao.setVisibility(View.GONE);
                tv_fanBi.setVisibility(View.VISIBLE);
                tv_biliNum.setVisibility(View.VISIBLE);
                tv_biliNum.setText(bean.fan_bi + "%");

                if (null != bean.valid_time) {
                    shopName.setText("有效期至" + bean.valid_time);
                } else {
                    shopName.setText("");
                }


                break;
            case 2:  //普通
                tv_shape1.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                tv_shapeMiddle.setVisibility(View.VISIBLE);
                tv_shapeMiddle.setText("有返利");
                tv_liJifan.setText("收货后立返");

                tv_fanBi.setVisibility(View.GONE);
                tv_biliNum.setVisibility(View.GONE);
                goToTianMao.setVisibility(View.VISIBLE);
                if ("0".equals(bean.user_type)) {
                    Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_qutaobao);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    goToTianMao.setCompoundDrawables(drawable, null, null, null);
                    goToTianMao.setText(" 去淘宝");
                }

                shopName.setText(bean.shopname);

                break;
        }


        llGoodsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("tag","1111111");


                if (TextUtils.isEmpty(bean.getId())) {
                    ToastUtils.toastForShort(mContext, "未获取到商品ID");
                } else {
                    if (!TextUtils.isEmpty(bean.getClick_url())) {
                        String url = ConfigValue.TAO_VISIT + "&uid=" + uid + "&iid=" + bean.getId();
                        new OkHttpUtils().getDatas(context,url, session, new OkHttpUtils.HttpCallBack() {
                            @Override
                            public void onSusscess(String data) {
                                VisitResultBean visitResultBean = new Gson().fromJson(data, VisitResultBean.class);
                                switch (visitResultBean.getResult()) {
                                    case "SUCESS":
                                        AliUtils.showUrl(mContext, bean.getClick_url());
                                        break;
                                    case "NOLOGIN":
                                        ToastUtils.toastForShort(mContext, visitResultBean.getWorngMsg());
                                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                                        break;
                                    case "FAIL":
                                        ToastUtils.toastForShort(mContext, visitResultBean.getWorngMsg());
                                        break;
                                }
                            }

                            @Override
                            public void onError(String meg) {
                                super.onError(meg);
                                ToastUtils.toastForShort(mContext, meg);
                            }
                        });
                    } else {
                        String url = ConfigValue.GET_CLICK_URL + "&uid=" + uid + "&iid=" + bean.getId();
                        new OkHttpUtils().getDatas(context,url, session, new OkHttpUtils.HttpCallBack() {
                            @Override
                            public void onSusscess(String data) {
                                GetClickUrlBean clickUrlBean = new Gson().fromJson(data, GetClickUrlBean.class);
                                switch (clickUrlBean.getResult()) {
                                    case "SUCESS":
                                        AliUtils.showUrl(mContext, clickUrlBean.getClick_url());
                                        break;
                                    case "NOLOGIN":
                                        ToastUtils.toastForShort(mContext, clickUrlBean.getWorngMsg());
                                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                                        break;
                                    case "FAIL":
                                        ToastUtils.toastForShort(mContext, clickUrlBean.getWorngMsg());
                                        break;
                                }
                            }

                            @Override
                            public void onError(String meg) {
                                super.onError(meg);
                                ToastUtils.toastForShort(mContext, meg);
                            }
                        });
                    }
                }
            }
        });

    }
}
