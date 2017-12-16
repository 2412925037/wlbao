package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.DayIsSignBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 *
 * 日历展示适配器
 */

public class SignInDateAdapter extends BaseAdapter {
    private List<DayIsSignBean> dayIsSignBeen = new ArrayList<>();
    private Context context;
    private boolean isWeek;

    public SignInDateAdapter( Context context, boolean isWeek) {
        this.context = context;
        this.isWeek = isWeek;
    }

    public void addAll(List<DayIsSignBean> dayIsSignBeen){
        this.dayIsSignBeen.addAll(dayIsSignBeen);
        notifyDataSetChanged();
    }

    public void clear(){
        dayIsSignBeen.clear();
    }

    @Override
    public int getCount() {
        return dayIsSignBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return dayIsSignBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.item_sign_in_date, viewGroup, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_sign_in_date);
        ImageView imgSigned = (ImageView) view.findViewById(R.id.img_signed);
        textView.setText(dayIsSignBeen.get(i).getDate());
        if (isWeek){
            textView.setTextColor(context.getResources().getColor(R.color.colorWhite));
        }else {
            textView.setTextColor(context.getResources().getColor(R.color.all_text_color));
        }

        if (dayIsSignBeen.get(i).isSign()){
            imgSigned.setVisibility(View.VISIBLE);
        }else {
            imgSigned.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}
