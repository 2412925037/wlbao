package com.renhuikeji.wanlb.wanlibao.widget;

import android.support.v7.widget.RecyclerView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;

/**
 * Created by Administrator on 2017/5/11.
 */

public abstract class HidingScrollListener extends LRecyclerView.OnScrollListener {
    private static final int HIDE_THRESHOLD = 406;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onShow();
            controlsVisible = false;
            scrolledDistance = 0;

        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onHide();
            controlsVisible = true ;
            scrolledDistance = 0;
        }
        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    public abstract void onHide();

    public abstract void onShow();

}
