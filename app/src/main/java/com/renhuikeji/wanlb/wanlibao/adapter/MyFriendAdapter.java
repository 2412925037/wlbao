package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.FriendsBean;
import com.renhuikeji.wanlb.wanlibao.utils.glide.GlideImageLoader;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MyFriendAdapter extends ListBaseAdapter<FriendsBean.FriendBean> {
    private Context context;

    public MyFriendAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_myfriend;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        FriendsBean.FriendBean bean = mDataList.get(position);
        TextView nameTv=holder.getView(R.id.friend_name_tv);
        Drawable d;

        if(TextUtils.equals(bean.getVip().trim(),"N")){
           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                d = context.getDrawable(R.mipmap.commen_user);
            }else{
                d=context.getResources().getDrawable(R.mipmap.commen_user);
            }*/
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                d = context.getDrawable(R.mipmap.vip_user);
            }else{
                d=context.getResources().getDrawable(R.mipmap.vip_user);
            }
            d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
//            nameTv.setCompoundDrawables(null,null,d,null);
        }
        TextView idTv=holder.getView(R.id.friend_id_tv);
        TextView numTv=holder.getView(R.id.friend_num_tv);
        TextView orderTv=holder.getView(R.id.friend_order_tv);
        ImageView userImg=holder.getView(R.id.friend_img);

        nameTv.setText(bean.getNick());
        idTv.setText("注册时间:"+bean.getRegtime());
        numTv.setText("好友:"+bean.getFriends());
        orderTv.setText("订单:"+bean.getOrders());
        new GlideImageLoader(context).displayHeadImg(userImg,bean.getHeadimgurl());
    }
}