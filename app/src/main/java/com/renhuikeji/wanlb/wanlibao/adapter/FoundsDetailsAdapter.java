package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.FoundsDetailsBean;

/**
 * Created by Administrator on 2017/5/24.
 */

public class FoundsDetailsAdapter extends ListBaseAdapter<FoundsDetailsBean.FundsBean> {


    public FoundsDetailsAdapter(Context context) {
        super(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.founds_details_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        FoundsDetailsBean.FundsBean fundsBean = mDataList.get(position);

        TextView tv_date = holder.getView(R.id.founds_item_date);
        //TextView tv_type = holder.getView(R.id.founds_item_type);
        TextView tv_money = holder.getView(R.id.founds_item_jiner);
        TextView tv_yuer = holder.getView(R.id.founds_item_yuer);
        TextView tv_beizhu = holder.getView(R.id.founds_item_beizhu);

        tv_date.setText("日期: " + fundsBean.getAddtime());
        // tv_type.setText("类型: " + fundsBean.getType());
        int length = ("金额: " + fundsBean.getMoney()).toString().length();
        SpannableStringBuilder spannableStr = new SpannableStringBuilder("金额: " + fundsBean.getMoney());


        int type=Integer.parseInt(fundsBean.getType());
        if (type<0) { //支出
            spannableStr.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.all_pink)), 4, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            tv_money.setText(spannableStr);
        } else {   //收入
            spannableStr=new SpannableStringBuilder("金额: +" + fundsBean.getMoney());
            spannableStr.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.jiaobiao_green)), 4, spannableStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            tv_money.setText(spannableStr);
        }
/*        Log.d("CCC","金额:"+fundsBean.getMoney());
        Log.d("CCC","余额:"+fundsBean.getBalance());*/
        tv_yuer.setText("余额: " + fundsBean.getBalance());
        tv_beizhu.setText(fundsBean.getDescs());

    }

}
