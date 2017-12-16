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
import android.widget.RelativeLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import static com.renhuikeji.wanlb.wanlibao.utils.Constant.LAYOUT_Grid;

/**
 * Created by Administrator on 2017/4/24.
 * <p>
 * 普通商品展示适配器
 */

public class GeneralGoodAdapter extends ListBaseAdapter<HomeRecyBean> {
    GlideImageLoader imageLoader;
    private String uid;
    private String session;
    private int layoutType = 0;
    private Context context;
    
    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }

    public GeneralGoodAdapter(Context context) {
        super(context);
        this.context=context;
        imageLoader = new GlideImageLoader(context);
        uid = (String) SPUtils.get(context, Constant.User_Uid, "");
        session = (String) SPUtils.get(context, Constant.MSESSION, "");
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getLayoutId() {
        return R.layout.general_goods_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final HomeRecyBean bean = mDataList.get(position);

        LinearLayout ll_big = holder.getView(R.id.big);
        ll_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(bean.getId())) {
                    ToastUtils.toastForShort(mContext, "未获取到商品ID");
                } else {
                    if (!TextUtils.isEmpty(bean.getClick_url())) {
                        String url = ConfigValue.TAO_VISIT + "&uid=" + uid + "&iid=" + bean.getId();
                        new OkHttpUtils().getDatas(context,url, session, new OkHttpUtils.HttpCallBack() {
                            @Override
                            public void onSusscess(String data) {
                                AliUtils.showUrl(mContext, bean.getClick_url());


                                Log.i("tag","url:"+bean.getClick_url()+"  item:"+data);

//                                try {
//                                    JSONObject object = new JSONObject(data);
//                                    if(TextUtils.equals("SUCESS",object.getString("result"))){
//                                        AliUtils.showUrl(mContext, bean.getClick_url());
//                                    }else{
//                                        ToastUtils.toastForShort(mContext, data);
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }

//                                VisitResultBean visitResultBean = new Gson().fromJson(data, VisitResultBean.class);
//                                switch (visitResultBean.getResult()) {
//                                    case "SUCESS":
//                                      AliUtils.showUrl(mContext, bean.getClick_url());
//                                        break;
////                                    case "LOGIN_FAIL":
////                                        ToastUtils.toastForShort(mContext, visitResultBean.getWorngMsg());
////                                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
////                                        break;
////                                    case "FAIL":
////                                        ToastUtils.toastForShort(mContext, visitResultBean.getWorngMsg());
////                                        break;
//                                    default:
//                                        ToastUtils.toastForShort(mContext, "电商SDK出错");
//                                        break;
//                                }
                            }

                            @Override
                            public void onError(String meg) {
                                super.onError(meg);
                                ToastUtils.toastForShort(mContext, meg);
                            }
                        });
                    } else {
                        String url = ConfigValue.GET_CLICK_URL + "&uid=" + uid + "&iid=" + bean.getId();
                        Log.d("CCC","-2->"+url);
                        new OkHttpUtils().getDatas(context,url, session, new OkHttpUtils.HttpCallBack() {
                            @Override
                            public void onSusscess(String data) {
                                GetClickUrlBean clickUrlBean = new Gson().fromJson(data, GetClickUrlBean.class);
                                switch (clickUrlBean.getResult()) {
                                    case "SUCESS":
                                        AliUtils.showUrl(mContext, clickUrlBean.getClick_url());
                                        break;
                                    case "LOGIN_FAIL":
                                        ToastUtils.toastForShort(mContext, clickUrlBean.getWorngMsg());
                                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                                        break;
                                    case "FAIL":
                                        ToastUtils.toastForShort(mContext, clickUrlBean.getWorngMsg());
                                        break;
                                    case "NOLOGIN":
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
        RelativeLayout relativeLayout = holder.getView(R.id.item_list_layout);
        LabelImageView imageView = holder.getView(R.id.general_item_img); //图片
        TextView text_quan = holder.getView(R.id.text_quan); //领券省5元
//
        RelativeLayout relativeLayout1 = holder.getView(R.id.item_list_layout1);
        LabelImageView imageView1 = holder.getView(R.id.general_itemlist_img); //图片
        TextView text_quan1 = holder.getView(R.id.text_list_quan); //领券省5元

        TextView tv_goodIntroduce = holder.getView(R.id.general_item_goodIntroduce); //商品介绍
        TextView tv_nowPrice = holder.getView(R.id.general_item_nowprice);  //现价
        TextView tv_oldprice = holder.getView(R.id.general_item_oldprice);  //原价
        TextView tv_shape1 = holder.getView(R.id.super_tv_haveshape);  //返
        TextView tv_shapeMiddle = holder.getView(R.id.super_tv_fanmiddle);  //返利
        TextView tv_liJifan = holder.getView(R.id.general_item_lijifan);  //立即返利
        LinearLayout linearLayout = holder.getView(R.id.super_ll_lingquan); //领券布局
        TextView tv_xiaoLiang = holder.getView(R.id.super_tv_xiaoliang);  //销量
        TextView tv_xiaoLiang_common = holder.getView(R.id.general_item_xiaoliang);  //普通 销量
        TextView youfanli_tv = holder.getView(R.id.general_item_youfanli);  //有返利

        TextView shopName = holder.getView(R.id.general_item_shopName);  //有效期
        TextView bottomAddress = holder.getView(R.id.general_item_bottomAddress);  //地址
        TextView tv_quanNum = holder.getView(R.id.super_tv_quanNum);  //领券钱数
        TextView tv_fanBi = holder.getView(R.id.super_tv_fanbi);  //返比
        TextView tv_biliNum = holder.getView(R.id.super_tv_fanbili);  //返比比例
        TextView tvQutianmao = holder.getView(R.id.general_item_quTianMao);//去天猫
        RelativeLayout rlSuperFanbi = holder.getView(R.id.rl_super_goods_fanbi);//  返比/券后价
        RelativeLayout rlGeneralGoods = holder.getView(R.id.rl_general_goods_totianmao);//  去天猫

        if (layoutType == LAYOUT_Grid) {
            relativeLayout.setVisibility(View.VISIBLE);
            relativeLayout1.setVisibility(View.GONE);
            if (!"1".equals(bean.isSeller) || null == bean.isSeller) {
                imageView.setLabelVisable(false);
            } else {
                imageView.setLabelVisable(true);
            }
            imageLoader.display(imageView, bean.imgUrl, R.mipmap.icon_wanlibao_grey);

            if (null != bean.quan_num && Double.parseDouble(bean.quan_num) > 0) {
                text_quan.setVisibility(View.VISIBLE);
                text_quan.setText("领券省\r\n" + bean.quan_num + "元");
            } else {
                text_quan.setVisibility(View.GONE);
            }

        } else {
            relativeLayout.setVisibility(View.GONE);
            relativeLayout1.setVisibility(View.VISIBLE);
            if (!"1".equals(bean.isSeller) || null == bean.isSeller) {
                imageView1.setLabelVisable(false);
            } else {
                imageView1.setLabelVisable(true);
            }
            imageLoader.display(imageView1, bean.imgUrl, R.mipmap.icon_wanlibao_grey);

            if (null != bean.quan_num && Double.parseDouble(bean.quan_num) > 0) {
                text_quan1.setVisibility(View.VISIBLE);
                text_quan1.setText("领券省\r\n" + bean.quan_num + "元");
            } else {
                text_quan1.setVisibility(View.GONE);
            }

        }


        if (TextUtils.isEmpty(bean.newprices)) {
            tv_nowPrice.setText("");
        } else {
            tv_nowPrice.setText("￥" + bean.newprices);
        }

        if (TextUtils.isEmpty(bean.newprices)) {
            tv_oldprice.setText("");
        } else {
            tv_oldprice.setText("￥" + bean.oldprices);
            tv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        }


        if (!"1".equals(bean.isSeller) || null == bean.isSeller) {
            imageView.setLabelVisable(false);
        } else {
            imageView.setLabelVisable(true);
            imageView.setTextContent("推荐");
            imageView.setLabelBackGroundColor(Color.RED);
        }

        if (TextUtils.isEmpty(bean.sales)) {
            tv_xiaoLiang.setText("");
        } else {
            tv_xiaoLiang.setText("销量" + bean.sales);
        }
        tv_liJifan.setTextColor(context.getResources().getColor(R.color.grey_text));
        switch (bean.type) {
            case 1:              //普通商品
                if (TextUtils.isEmpty(bean.sales)) {
                    tv_xiaoLiang_common.setText("");
                } else {
                    //tv_xiaoLiang_common.setText("销量" + bean.sales);
                    tv_xiaoLiang_common.setText("");
                }

                linearLayout.setVisibility(View.GONE);
                tv_shape1.setVisibility(View.GONE);
                tv_shapeMiddle.setVisibility(View.VISIBLE);
                tv_shapeMiddle.setText("有返利");
//                youfanli_tv.setText("有返利");


                //tv_liJifan.setText("收货后立返");
                tv_liJifan.setText("销量" + bean.sales);
                shopName.setText(bean.shopname);
                rlSuperFanbi.setVisibility(View.GONE);
                rlGeneralGoods.setVisibility(View.VISIBLE);
                bottomAddress.setText(bean.address);
//                if ("0".equals(bean.user_type)) {
//                    Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_qutaobao);
//                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                    tvQutianmao.setCompoundDrawables(drawable, null, null, null);
//                    tvQutianmao.setText(" 去淘宝");
//                }

                tvQutianmao.setText(bean.getNick());
                if (TextUtils.equals("1", bean.user_type)) {
                    ImageSpan imgSpan = new ImageSpan(mContext, R.mipmap.icon_tianmao);
                    SpannableString spannableString = new SpannableString("天猫 " + bean.goodIntroduce);
                    spannableString.setSpan(imgSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_goodIntroduce.setText(spannableString);
                    //tv_goodIntroduce.setText("天猫  "+bean.goodIntroduce);
                } else {
                    ImageSpan imgSpan = new ImageSpan(mContext, R.mipmap.icon_qutaobao);
                    SpannableString spannableString = new SpannableString("淘宝 " + bean.goodIntroduce);
                    spannableString.setSpan(imgSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_goodIntroduce.setText(spannableString);
                    //tv_goodIntroduce.setText("淘宝  "+bean.goodIntroduce);
                }

//                if (TextUtils.isEmpty(bean.goodIntroduce)) {
//                    tv_goodIntroduce.setText("");
//                } else {
//                    tv_goodIntroduce.setText(bean.goodIntroduce);
//                }

                break;
            case 2:             //超级返利
                linearLayout.setVisibility(View.GONE);
                tv_shape1.setVisibility(View.VISIBLE);
                tv_shapeMiddle.setVisibility(View.VISIBLE);
                tv_shapeMiddle.setText("￥" + bean.fan_num);
                tv_liJifan.setText("收货后立返");


                rlSuperFanbi.setVisibility(View.VISIBLE);
                rlGeneralGoods.setVisibility(View.GONE);
                tv_fanBi.setVisibility(View.VISIBLE);
                tv_biliNum.setText(bean.fan_bi + "%");
                bottomAddress.setVisibility(View.GONE);
                if (TextUtils.isEmpty(bean.valid_time)) {
                    shopName.setVisibility(View.GONE);
                } else {
                    //shopName.setText("有效期至" + bean.valid_time);
                    shopName.setText(bean.getNick());
                }

                if (TextUtils.isEmpty(bean.goodIntroduce)) {
                    tv_goodIntroduce.setText("");
                } else {
                    if (TextUtils.equals("1", bean.user_type)) {
                        ImageSpan imgSpan = new ImageSpan(mContext, R.mipmap.icon_tianmao);
                        SpannableString spannableString = new SpannableString("天猫 " + bean.goodIntroduce);
                        spannableString.setSpan(imgSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_goodIntroduce.setText(spannableString);
                        //tv_goodIntroduce.setText("天猫  "+bean.goodIntroduce);
                    } else {
                        ImageSpan imgSpan = new ImageSpan(mContext, R.mipmap.icon_qutaobao);
                        SpannableString spannableString = new SpannableString("淘宝 " + bean.goodIntroduce);
                        spannableString.setSpan(imgSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_goodIntroduce.setText(spannableString);
                        //tv_goodIntroduce.setText("淘宝  "+bean.goodIntroduce);
                    }

                }

                break;
            case 3:             //优惠券
                tv_shape1.setVisibility(View.GONE);
                tv_shapeMiddle.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                tv_quanNum.setText(bean.quan_num + "元");
                tv_liJifan.setTextColor(context.getResources().getColor(R.color.fuzhu_red));

                if(bean.getReturn_num().equals("0") || bean.getReturn_num().equals("0.0") || bean.getReturn_num().equals("0.00")){
                    tv_liJifan.setText("有返利");
                }else
                tv_liJifan.setText("再返"+bean.getReturn_num());

                rlSuperFanbi.setVisibility(View.VISIBLE);
                rlGeneralGoods.setVisibility(View.GONE);
                tv_fanBi.setVisibility(View.GONE);
                tv_biliNum.setText("券后价￥" + bean.quan_hou_num);
                bottomAddress.setVisibility(View.GONE);
                if (TextUtils.isEmpty(bean.valid_time)) {
                    shopName.setVisibility(View.GONE);
                } else {
                    //shopName.setText("有效期至" + bean.valid_time);
                    shopName.setText(bean.getNick());
                }

                if (TextUtils.isEmpty(bean.goodIntroduce)) {
                    tv_goodIntroduce.setText("");
                } else {
                    if (TextUtils.equals("1", bean.user_type)) {
                        ImageSpan imgSpan = new ImageSpan(mContext, R.mipmap.icon_tianmao);
                        SpannableString spannableString = new SpannableString("天猫 " + bean.goodIntroduce);
                        spannableString.setSpan(imgSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_goodIntroduce.setText(spannableString);
                        //tv_goodIntroduce.setText("天猫  "+bean.goodIntroduce);
                    } else {
                        ImageSpan imgSpan = new ImageSpan(mContext, R.mipmap.icon_qutaobao);
                        SpannableString spannableString = new SpannableString("淘宝 " + bean.goodIntroduce);
                        spannableString.setSpan(imgSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_goodIntroduce.setText(spannableString);
                        //tv_goodIntroduce.setText("淘宝  "+bean.goodIntroduce);
                    }

                }

                break;
        }

    }

    //------------------------------------------------
}
