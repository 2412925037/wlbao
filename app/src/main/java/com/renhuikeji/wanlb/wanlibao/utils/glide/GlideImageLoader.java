package com.renhuikeji.wanlb.wanlibao.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.utils.LogUtil;
import com.renhuikeji.wanlb.wanlibao.views.CircleImageView;
import com.renhuikeji.wanlb.wanlibao.views.RoundCornerImageView;


/**
 * Author: wuxingliang
 * Version：5.4.4
 * Date: 2016-04-08
 * Description:
 * Glide的调用使用类
 * <p>
 * Modification History:
 * Date          Author     Version    Description
 * -----------------------------------------------------------
 * 2016.04.08                5.4.4        1.0
 * <p>
 * ------------------------
 * Why & What is modified:
 * 1.0：
 */
public class GlideImageLoader {
    private Context mContext;

    public GlideImageLoader(Context context) {
        this.mContext = context;
    }

    /**
     * 加载图片方法
     *
     * @param lodingImagerView
     * @param resourcesId      drawable中的图片id
     */
    public void display(ImageView lodingImagerView, int resourcesId) {//外部接口函数
        Glide.with(mContext)
                .load(resourcesId)
                .placeholder(R.color.loading_img_default_color)
                .error(R.mipmap.icon_wanlibao_grey)
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                .diskCacheStrategy(DiskCacheStrategy.RESULT)  //只保留转换后资源
                //.skipMemoryCache(true)    //跳过内存缓存
                .into(lodingImagerView);
    }

    public void display(CircleImageView lodingImagerView, Uri picUrl) {//外部接口函数
        Glide.with(mContext)
                .load(picUrl)
                .placeholder(R.color.loading_img_default_color)
                .error(R.mipmap.icon_wanlibao_grey)
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                .diskCacheStrategy(DiskCacheStrategy.RESULT)  //只保留转换后资源
                .into(lodingImagerView);
    }

    public void display(CircleImageView lodingImagerView, String picUrl) {//外部接口函数
        Glide.with(mContext)
                .load(picUrl)
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                .diskCacheStrategy(DiskCacheStrategy.RESULT)  //只保留转换后资源
                .placeholder(R.color.loading_img_default_color)
                .error(R.mipmap.icon_wanlibao_grey)
                .into(lodingImagerView);
    }

    public void display(CircleImageView lodingImagerView, byte[] picUrl) {//外部接口函数
        Glide.with(mContext)
                .load(picUrl)
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                .placeholder(R.color.loading_img_default_color)
                .error(R.mipmap.icon_wanlibao_grey)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)  //只保留转换后资源
                .into(lodingImagerView);
    }

    /**
     * 加载图片方法
     *
     * @param lodingImagerView
     * @param picUrl
     */
    public void display(ImageView lodingImagerView, String picUrl) {//外部接口函数
        Glide.with(mContext)
                .load(picUrl)
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                .placeholder(R.color.loading_img_default_color)
                .error(R.mipmap.icon_wanlibao_grey)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(lodingImagerView);
    }

    public void display(RoundCornerImageView lodingImagerView, String picUrl) {//外部接口函数
        Glide.with(mContext)
                .load(picUrl)
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                .placeholder(R.mipmap.icon_yh)
                .error(R.mipmap.icon_yh)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(lodingImagerView);
    }

    /**
     * 加载好友头像图片方法
     *
     * @param lodingImagerView
     * @param picUrl
     */
    public void displayHeadImg(ImageView lodingImagerView, String picUrl) {//外部接口函数
        Glide.with(mContext)
                .load(picUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.icon_yh)
                .into(lodingImagerView);
    }

    /**
     * 加载图片方法
     *
     * @param activity
     * @param lodingImagerView
     * @param picUrl
     */
    public void display(Activity activity, ImageView lodingImagerView, String picUrl) {//外部接口函数
        Glide.with(activity)
                .load(picUrl)
                .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                .placeholder(R.color.loading_img_default_color)
                .error(R.mipmap.icon_wanlibao_grey)
//                .placeholder(R.drawable.default_loading_image2)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(lodingImagerView);
    }

    /**
     * 加载图片方法，加载图片为 defaultImg
     *
     * @param lodingImagerView
     * @param picUrl
     * @param defaultImg
     */
    public void display(ImageView lodingImagerView, String picUrl, int defaultImg) {//外部接口函数
        if (defaultImg != 0) {
            Glide.with(mContext)
                    .load(picUrl)
                    .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(defaultImg)
                    .error(defaultImg)
                    .into(lodingImagerView);
        } else {
            Glide.with(mContext)
                    .load(picUrl)
                    .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.color.loading_img_default_color)
                    .error(defaultImg)
                    .into(lodingImagerView);
        }
    }

    /**
     * 加载图片方法，加载图片为 defaultImg
     *
     * @param lodingImagerView
     * @param picUrl
     * @param defaultImg
     */
    public void displayNoType(ImageView lodingImagerView, String picUrl, int defaultImg) {//外部接口函数
        if (defaultImg != 0) {
            Glide.with(mContext)
                    .load(picUrl)
                    .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(defaultImg)
                    .error(R.mipmap.icon_wanlibao_grey)
                    .into(lodingImagerView);
        } else {
            Glide.with(mContext)
                    .load(picUrl)
                    .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.color.loading_img_default_color)
                    .error(R.mipmap.icon_wanlibao_grey)
                    .into(lodingImagerView);
        }
    }

    /**
     * 加载图片方法，加载图片为 defaultImg
     *
     * @param activity
     * @param lodingImagerView
     * @param picUrl
     * @param defaultImg
     */
    public void display(Activity activity, ImageView lodingImagerView, String picUrl, int defaultImg) {//外部接口函数
        if (defaultImg != 0) {
            Glide.with(activity)
                    .load(picUrl)
                    .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                    .placeholder(defaultImg)
                    .error(R.mipmap.icon_wanlibao_grey)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(lodingImagerView);
        } else {
            Glide.with(activity)
                    .load(picUrl)
                    .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                    .placeholder(R.color.loading_img_default_color)
                    .error(R.mipmap.icon_wanlibao_grey)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(lodingImagerView);
        }
    }

    /**
     * 加载图片方法，加载图片为 defaultImg
     *
     * @param lodingImagerView
     * @param picUrl
     * @param defaultImg
     */
    public void displayRound(ImageView lodingImagerView, String picUrl, int defaultImg, int roundSize) {//外部接口函数
        if (defaultImg != 0) {
            Glide.with(mContext)
                    .load(picUrl).asBitmap()
                    .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
                    .placeholder(defaultImg)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .transform(new GlideRoundTransform(mContext, roundSize))
                    .into(lodingImagerView);
        } else {
            Glide.with(mContext)
                    .load(picUrl)
                    .animate(android.R.anim.fade_in)  // 自己设置渐现动画可以解决加载图片变形问题
//                    .placeholder(R.drawable.default_loading_image2)
                    .placeholder(R.color.loading_img_default_color)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .transform(new GlideRoundTransform(mContext, roundSize))
                    .into(lodingImagerView);
        }
    }

    public void clearImageCache() {
        new Thread(runnable).start();
        Glide.get(mContext).clearMemory(); // 必须在主线程中执行
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Glide.get(mContext).clearDiskCache(); // 必须在子线程中执行
            } catch (Exception ex) {
                LogUtil.e(mContext, ex.toString());
            }
        }
    };

}
