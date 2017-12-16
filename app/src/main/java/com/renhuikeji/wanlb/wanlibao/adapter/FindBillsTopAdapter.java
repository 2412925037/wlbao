package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 *
 * 认领订单顶部分类的点击弹窗
 */

public class FindBillsTopAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public FindBillsTopAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_find_bills_top, viewGroup, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_find_bills_top);
        textView.setText(list.get(i));
        return view;
    }
}
