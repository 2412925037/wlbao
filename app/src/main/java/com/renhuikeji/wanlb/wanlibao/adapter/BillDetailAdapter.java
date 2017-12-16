package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.BillDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/4.
 * <p>
 * 订单详情的适配器
 */

public class BillDetailAdapter extends BaseAdapter {
    private List<BillDetailBean.ProductBean> productBeen = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public BillDetailAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addAll(List<BillDetailBean.ProductBean> productBeen) {
        this.productBeen.addAll(productBeen);
        notifyDataSetChanged();
    }

    public void clear() {
        productBeen.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return productBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return productBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (null == view) {
            view = inflater.inflate(R.layout.item_bill_detail, viewGroup, false);
            viewHolder = new ViewHolder(view);

            viewHolder.tvBillDetailContent = (TextView) view.findViewById(R.id.tv_bill_detail_content);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvBillDetailContent.setText(productBeen.get(i).getP_name());
        viewHolder.tvBillDetailId.setText(productBeen.get(i).getP_id());
        viewHolder.tvBillDetailReturnsRate.setText(productBeen.get(i).getReturn_rate());

        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");

        double d1 = Double.valueOf(productBeen.get(i).getP_marked_price());
        if(d1==0.0){

            viewHolder.tvBillDetailMarkedPrice.setText("0.00");
        }else{

            viewHolder.tvBillDetailMarkedPrice.setText(""+df.format(d1));
        }

        double d2 = Double.valueOf(productBeen.get(i).getP_price());
        if(d2==0.0){

            viewHolder.tvBillDetailPrice.setText("0.00");
        }else{

            viewHolder.tvBillDetailPrice.setText(""+df.format(d2));
        }


        viewHolder.tvBillDetailAmount.setText(df.format(productBeen.get(i).getP_amount()));

        double d3 = Double.valueOf(productBeen.get(i).getReturns());
        if(d3==0.0){
            viewHolder.tvBillDetailReturns.setText("0.00");
        }else{
            viewHolder.tvBillDetailReturns.setText(""+df.format(d3));
        }

        viewHolder.tvBillDetailCnt.setText(productBeen.get(i).getIt_cnt());

        return view;
    }

    class ViewHolder {
        @BindView(R.id.tv_bill_detail_content)
        TextView tvBillDetailContent;
        @BindView(R.id.tv_bill_detail_id)
        TextView tvBillDetailId;
        @BindView(R.id.tv_bill_detail_returns_rate)
        TextView tvBillDetailReturnsRate;
        @BindView(R.id.tv_bill_detail_marked_price)
        TextView tvBillDetailMarkedPrice;
        @BindView(R.id.tv_bill_detail_price)
        TextView tvBillDetailPrice;
        @BindView(R.id.tv_bill_detail_cnt)
        TextView tvBillDetailCnt;
        @BindView(R.id.tv_bill_detail_amount)
        TextView tvBillDetailAmount;
        @BindView(R.id.tv_bill_detail_returns)
        TextView tvBillDetailReturns;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
