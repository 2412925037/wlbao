package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.BaseBean;

/**
 * Created by Administrator on 2016/9/22.
 */

public class ScreenMsgAdapter extends ListBaseAdapter<BaseBean> {


    public ScreenMsgAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_screenmsg;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        BaseBean bean=mDataList.get(position);
        TextView titleTv=holder.getView(R.id.title);
        TextView descTv=holder.getView(R.id.description);
        TextView timeTv=holder.getView(R.id.time);
        timeTv.setText(bean.getCode());
        titleTv.setText(bean.getMsg());
    }
}
