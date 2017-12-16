package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.MyAccountBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MyCashFlowAdapter extends BaseAdapter {
    private List<MyAccountBean> myAccount;
    private Context context;

    public MyCashFlowAdapter(List<MyAccountBean> myAccount, Context context) {
        this.myAccount = myAccount;
        this.context = context;
    }

    @Override
    public int getCount() {
        return myAccount.size();
    }

    @Override
    public Object getItem(int i) {
        return myAccount.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (null == view ){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_cash_flow_listview,viewGroup,false);

            holder.tvName = (TextView) view.findViewById(R.id.tv_cash_flow_item1);
            holder.tvNum = (TextView) view.findViewById(R.id.tv_cash_flow_item2);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        MyAccountBean myAccountBean = myAccount.get(i);
        holder.tvName.setText(myAccountBean.getCostName()+"：");
        if(i==4){

            holder.tvNum.setText(myAccountBean.getCostNum()+"分");
        }else{
            holder.tvNum.setText(myAccountBean.getCostNum()+"元");

        }
        return view;
    }

    class ViewHolder{
        private TextView tvName,tvNum;
    }
}
