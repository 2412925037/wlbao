package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.MallBean;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MallListAdapter extends ListBaseAdapter<MallBean.MallList> {

    public MallListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_malllist;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
       TextView nameTv= holder.getView(R.id.mall_name);
        nameTv.setText(mDataList.get(position).getMall_name());
    }
}
