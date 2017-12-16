package com.renhuikeji.wanlb.wanlibao.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.bean.IconTitleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/28.
 * 定制版pop
 */

public class SharePopupWindow extends PopupWindow {

    private View popview;
    private LinearLayout layout;
    private TextView clear;
    private Context context;

    private List<IconTitleBean> datas;
    private onMyItemClickListener listener;

    public SharePopupWindow(Context context) {
        super(context);
        this.datas = getData();
        this.context = context;
        init(context);
        setPopupWindow();
    }

    private void setPopupWindow() {
        this.setContentView(popview);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        popview.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = popview.findViewById(R.id.id_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //this.setAnimationStyle(R.style.anim_menu_bottombar);  //设置加载动画

        //pop.update();
        this.setOnDismissListener(new OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });

    }

    private void init(Context context) {

        popview = LayoutInflater.from(context).inflate(R.layout.share_pop_window, null);
        layout = (LinearLayout) popview.findViewById(R.id.content);
        clear = (TextView) popview.findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        initLayout();
    }

    public void showPopupWindow(View parent) {

        // 产生背景变暗效果
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) context).getWindow().setAttributes(lp);

        this.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void initLayout() {
        for (int i = 0; i < datas.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_share_pop, null);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            TextView title = (TextView) view.findViewById(R.id.title);
            LinearLayout all = (LinearLayout) view.findViewById(R.id.all);
            all.setTag(i);

            icon.setImageResource(datas.get(i).getResId());
            title.setText(datas.get(i).getTitle());

            all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onitemclick(view);
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            view.setLayoutParams(params);
            layout.addView(view);
        }
    }

    public void setonItemClickListener(onMyItemClickListener listener) {
        this.listener = listener;
    }

    private List<IconTitleBean> getData() {

        String[] str = {"QQ空间", "QQ好友", "微信好友", "朋友圈"};
        int[] img = {R.mipmap.icon_share_qqz, R.mipmap.icon_share_qq, R.mipmap.icon_share_wechat, R.mipmap.icon_share_friends};
        List<IconTitleBean> data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            IconTitleBean bean = new IconTitleBean(img[i], str[i]);
            data.add(bean);
        }
        return data;
    }


    public interface onMyItemClickListener {
        void onitemclick(View view);
    }

}
