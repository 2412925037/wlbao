package com.renhuikeji.wanlb.wanlibao.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取手机基本信息
 *
 * @author songdy
 */
public class PhoneInfoUtil {
    private static final String TAG = "PhoneInfoUtil";
    private static PhoneInfoUtil instance;
    public static String mVersionName;
    public static int mDisplayWidth;
    public static int mDisplayHeight;
    public static float mDensity;
    private static String urlregEx1 = "imageView2/\\d*";
    private static String urlregEx2 = "/w/\\d*";
    private static String urlregEx3 = "/h/\\d*";

    private PhoneInfoUtil() {
    }

    public static synchronized PhoneInfoUtil getInstance() {
        if (instance == null) {
            instance = new PhoneInfoUtil();
        }
        return instance;
    }






    public DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metric);
        return metric;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public int getDisplayWidth(Context context) {
        if (mDisplayWidth == 0) {
            DisplayMetrics metric = getDisplayMetrics(context);
            mDisplayWidth = metric.widthPixels;
        }
        return mDisplayWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public int getDisplayHeight(Context context) {
        if (mDisplayHeight == 0) {
            DisplayMetrics metric = getDisplayMetrics(context);
            mDisplayHeight = metric.heightPixels;
        }
        return mDisplayHeight;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 5.4.1根据屏幕或实际宽度补全图片URL
     *
     * @param context
     * @param imgUrl   图片URL
     * @param type     0为根据imgWidth值来补全
     *                 1为根据屏幕实际宽度补全
     *                 2为根据屏幕实际宽度的1/2补全
     *                 3为根据屏幕实际宽度的1/3补全
     *                 4为根据屏幕实际宽度的1/4补全
     * @param imgWidth 图片的实际width，传过来时已计算好
     * @param zoom     等比缩放的比例，默认传1
     * @return
     */
    public String getImageUrl(Context context, String imgUrl, int type, int imgWidth, int zoom) {
        StringBuilder builder = new StringBuilder();
        if (mDisplayWidth == 0) {
            DisplayMetrics metric = getDisplayMetrics(context);
            mDisplayWidth = metric.widthPixels;
        }
        int imageWidth = mDisplayWidth;
        switch (type) {
            case 0:
                imageWidth = imgWidth;
                break;
            case 1:
                imageWidth = mDisplayWidth;
                break;
            case 2:
                imageWidth = mDisplayWidth / 2;
                break;
            case 3:
                imageWidth = mDisplayWidth / 3;
                break;
            case 4:
                imageWidth = mDisplayWidth / 4;
                break;
        }
        if (!TextUtils.isEmpty(imgUrl) && imgUrl.contains("?")) {
            String url[] = imgUrl.split("\\?");
            builder.append(url[0]);
            builder.append("?");
            if (url[1].contains("/w/"))
                builder.append(url[1].replaceAll(urlregEx1, "imageView2/3").replaceAll(urlregEx2, "/w/" + imageWidth).replaceAll(urlregEx3, ""));
            else {
                builder.append(url[1].replaceAll(urlregEx1, "imageView2/3").replaceAll(urlregEx3, ""));
                builder.append("/w/");
                builder.append(imageWidth);
            }
            return builder.toString();
        } else if (!TextUtils.isEmpty(imgUrl) && imgUrl.endsWith(".jpg")) {
            builder.append(imgUrl);
            builder.append("?imageView2/3/w/");
            builder.append(imageWidth);
            builder.append("/format/jpg");
            return builder.toString();
        }
        builder.append(imgUrl);
        builder.append("?imageView2/3/w/");
        builder.append(imageWidth);
        return builder.toString();

    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public float getDisplayDensity(Context context) {
        if (mDensity == 0) {
            DisplayMetrics metric = getDisplayMetrics(context);
            mDensity = metric.density;
        }
        return mDensity;
    }

    public int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {

        }
        return version;
    }

    /**
     * 获取手机版本
     *
     * @param context
     */
    public String getVersion(Context context) {
        if (TextUtils.isEmpty(mVersionName)) {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
                // 获取在AndroidManifest.xml中配置的版本号
                mVersionName = pi.versionName;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(mVersionName)) {
            if (mVersionName.contains("-")) {
                mVersionName = mVersionName.split("-")[0];
            }
        }
        return mVersionName;
    }

    /**
     * 得到屏幕分辨率
     *
     * @return
     */
    public String getPixels(Context context) {
        DisplayMetrics displayMetrics = PhoneInfoUtil.getInstance()
                .getDisplayMetrics(context);
        return "手机分辨率： " + displayMetrics.widthPixels + "x"
                + displayMetrics.heightPixels;
    }

    /**
     * 获取手机的硬件信息
     *
     * @return
     */
    public String getMobileInfo() {
        StringBuilder sb = new StringBuilder();
        // 通过反射获取系统的硬件信息
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                // 暴力反射 ,获取私有的信息
                field.setAccessible(true);
                String name = field.getName();
                // if("MANUFACTURER".equals(name) || "MODEL".equals(name) ||
                // "SERIAL".equals(name)){
                String value = field.get(null).toString();
                sb.append(name).append("=").append(value);
                sb.append("\n");
                // }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 5.4.0新GA统计(按键统计)
     */
//    public void trackEventForGA(String category, String action, String label) {
//        LogUtil.i(this, "GAPathClickEvent is ---->" + category + "   " + action
//                + "   " + label);
//        if (IKeySourceUtil.flagForGA) {
//            BaseApplication.tracker().send(new HitBuilders.EventBuilder()
//                    .setCategory(category)
//                    .setAction(action)
//                    .setLabel(label)
//                    .build());
//        }
//    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 去掉指定字符串的开头和结尾的指定字符
     *
     * @param stream 要处理的字符串
     * @return 处理后的字符串
     */
    public static String sideTrim(String stream, String trimst) {
        String trimstr = "\\s*|\t|\r|\n";//去除字符串中的空格、回车、换行符、制表符
        if (!TextUtils.isEmpty(trimst)) {
            trimstr = trimst;
        }
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trimstr == null
                || trimstr.length() == 0) {
            return stream;
        }

        // 结束位置
        int epos;

        // 正规表达式
        String regpattern = "[" + trimstr + "]*+";
        Pattern pattern = Pattern.compile(regpattern, Pattern.CASE_INSENSITIVE);

        // 去掉结尾的指定字符
        StringBuffer buffer = new StringBuffer(stream).reverse();
        Matcher matcher = pattern.matcher(buffer);
        if (matcher.lookingAt()) {
            epos = matcher.end();
            stream = new StringBuffer(buffer.substring(epos)).reverse()
                    .toString();
        }

        // 去掉开头的指定字符
        matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            epos = matcher.end();
            stream = stream.substring(epos);
        }

        // 返回处理后的字符串
        return stream;
    }

    /**
     * 将url链接中的中文字符串转换成utf-8并用%做分隔
     *
     * @param s
     * @return
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 判断应付款和实付款小数点后几位，若超过两位则截取前两位
     */
    public static String subStringPrice(String str, int size) {
        String[] split = str.split("\\.");
        if(size == 0){
            return split[0];
        } else if (split[1].length() > size) {
            String substring = split[1].substring(0, size);
            String s = new String(split[0] + "." + substring);
            return s;
        } else {
            return str;
        }
    }

    /**
     * 将用户手机号中间4位变成*号
     */
    public static String encryptPhone(String phone) {
        String substring = phone.substring(0, 3);
        String substring1 = phone.substring(7, 11);
        String s = new String(substring + "****" + substring1);
        return s;
    }

    /**
     * 获取应用keystroy的SHA1
     * @param context
     * @return
     */
    public String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                if(i < publicKey.length -1){
                    hexString.append(":");
                }
            }

            return hexString.toString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
