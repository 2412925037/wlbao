package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.MasterGotBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 * <p>
 * 我的师傅界面适配器
 */

public class MyMasterAdapter extends BaseAdapter {
    private Context context;
    private List<MasterGotBean> costList;

    public MyMasterAdapter(Context context, List<MasterGotBean> costList) {
        this.context = context;
        this.costList = costList;
    }

    @Override
    public int getCount() {
        return costList.size();
    }

    @Override
    public Object getItem(int i) {
        return costList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MasterGotBean bean = costList.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_my_master, viewGroup, false);
        TextView tvCostName = (TextView) view.findViewById(R.id.tv_my_master_cost_name);
        TextView tvCostNumber = (TextView) view.findViewById(R.id.tv_my_master_cost_number);

        tvCostName.setText(bean.getCostName());
        switch (i) {
            case 3:
                tvCostNumber.setTextColor(context.getResources().getColor(R.color.red_text));
                tvCostNumber.setText(bean.getCostNum() + "元");
                break;
            default:
                tvCostNumber.setText(bean.getCostNum() + "元");
                break;
        }

        return view;
    }
}
