package com.renhuikeji.wanlb.wanlibao.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2017/3/27.
 */

public class GlideImageLoaderForBanner extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
//        imageView.setScaleType(ImageView.ScaleType.CENTER);
        Glide.with(context).load(path).into(imageView);
    }
}
