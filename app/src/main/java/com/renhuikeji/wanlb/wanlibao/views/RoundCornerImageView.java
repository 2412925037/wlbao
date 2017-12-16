package com.renhuikeji.wanlb.wanlibao.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.hyphenate.util.DensityUtil;
import com.renhuikeji.wanlb.wanlibao.R;

/**
 * Created by Administrator on 2017/5/25.
 */

public class RoundCornerImageView extends AppCompatImageView {
    private int cornerSize;//圆角大小
    private Context mcontext;

    public RoundCornerImageView(Context context){
        this(context,null);
        mcontext=context;
    }

    public RoundCornerImageView(Context context, AttributeSet attrs){
        this(context,attrs,0);
        mcontext=context;
    }

    public RoundCornerImageView(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        mcontext=context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView,defStyle,0);
        cornerSize = a.getInt(R.styleable.RoundCornerImageView_corner_size,5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = getWidth();
        int h = getHeight();
        //这里对path添加一个圆角区域，这里一般需要将dp转换为pixel
        path.addRoundRect(new RectF(0,0,w,h), DensityUtil.dip2px(mcontext,cornerSize),DensityUtil.dip2px(mcontext,cornerSize), Path.Direction.CW);
        canvas.clipPath(path);//将Canvas按照上面的圆角区域截取
        super.onDraw(canvas);
    }

    /**
     * 设置圆角的大小
     * @param size
     */
    public void setCornerSize(int size){
        cornerSize = size;
    }
}
