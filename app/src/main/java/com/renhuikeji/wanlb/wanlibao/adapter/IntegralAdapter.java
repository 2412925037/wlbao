package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.IntegralsBean;

/**
 * Created by Administrator on 2017/5/23.
 */

public class IntegralAdapter extends ListBaseAdapter<IntegralsBean.PointsBean> {
    private Context context;
    private String type= "";


    public IntegralAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_integral;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
         IntegralsBean.PointsBean bean=mDataList.get(position);
        TextView timeTv=holder.getView(R.id.integral_time_tv);
        TextView billTv=holder.getView(R.id.integral_bill_tv);
        TextView surplusTv=holder.getView(R.id.integral_surplus_tv);
        TextView noteTv=holder.getView(R.id.integral_note_tv);
        SpannableStringBuilder spanStr;

        spanStr=new SpannableStringBuilder("发  生  额:  "+bean.getPoints());

        int type=Integer.parseInt(this.type);
        if(type<0){
            spanStr.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.all_pink)), 9, spanStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            billTv.setText(spanStr);

        }else {

            spanStr=new SpannableStringBuilder("发  生  额:  +"+bean.getPoints());
            spanStr.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.jiaobiao_green)), 9, spanStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            billTv.setText(spanStr);

        }


        timeTv.setText("日        期:  "+bean.getAddtime());
        surplusTv.setText("账户余额:  "+bean.getBalance());
        noteTv.setText(bean.getDescs());

    }
    public void setType(String type){
        this.type=type;
    }
}