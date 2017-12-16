package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.MyOrderBean;
import com.renhuikeji.wanlb.wanlibao.utils.StringDealUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 * <p>
 * 我的订单适配器
 */

public class MyBillsAdapter extends RecyclerView.Adapter<MyBillsAdapter.ViewHolder> {

    private Context context;
    private boolean isHide;
    private List<MyOrderBean.OrdersBean> list = new ArrayList<>();

    public MyBillsAdapter(Context context,boolean isHide) {
        this.context = context;
        this.isHide=isHide;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_bills_lrecycler, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyOrderBean.OrdersBean bean = list.get(position);
        holder.tvMyBillsName.setText(bean.getTitle());
        if(isHide){
            holder.tvMyBillsId.setText(StringDealUtil.encryptString(bean.getOrder_code()));
        }else{
            holder.tvMyBillsId.setText(bean.getOrder_code());
        }
        holder.tvMyBillsState.setText(bean.getOrder_status());
        holder.tvMyBillsDate.setText(bean.getOrder_time());
        holder.tvMyBillsNumber.setText(bean.getItem_count());
        holder.tvMyBillsPaycost.setText(bean.getOrder_money());
        holder.tvMyBillsBackmoney.setText(bean.getReturns());
        holder.tvMyBillsPaybyseller.setText(bean.getMall_name());
        holder.tvMyBillsWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addIn(List<MyOrderBean.OrdersBean> billsItemBeen) {
        list.addAll(billsItemBeen);
    }

    public void clear(){
        if (0 <= list.size()){
            list.clear();
        }
    }

    public List<MyOrderBean.OrdersBean> getList(){
        return list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMyBillsId, tvMyBillsState, tvMyBillsDate, tvMyBillsTime, tvMyBillsNumber,
                tvMyBillsPaycost, tvMyBillsBackmoney, tvMyBillsPaybyseller, tvMyBillsWatch,tvMyBillsName;
        private ImageView imgMyBillsPic, imgMyBillsShop;

        public ViewHolder(View view) {
            super(view);
            tvMyBillsName = (TextView) view.findViewById(R.id.tv_my_bills_name);
            tvMyBillsId = (TextView) view.findViewById(R.id.tv_my_bills_id);
            tvMyBillsState = (TextView) view.findViewById(R.id.tv_my_bills_state);
            tvMyBillsDate = (TextView) view.findViewById(R.id.tv_my_bills_date);
            tvMyBillsTime = (TextView) view.findViewById(R.id.tv_my_bills_time);
            tvMyBillsNumber = (TextView) view.findViewById(R.id.tv_my_bills_number);
            tvMyBillsPaycost = (TextView) view.findViewById(R.id.tv_my_bills_paycost);
            tvMyBillsBackmoney = (TextView) view.findViewById(R.id.tv_my_bills_backmoney);
            tvMyBillsPaybyseller = (TextView) view.findViewById(R.id.tv_my_bills_paybyseller);
            tvMyBillsWatch = (TextView) view.findViewById(R.id.tv_my_bills_watch);
            imgMyBillsPic = (ImageView) view.findViewById(R.id.img_my_bills_pic);
            imgMyBillsShop = (ImageView) view.findViewById(R.id.img_my_bills_shop);
        }
    }
}
