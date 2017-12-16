package com.renhuikeji.wanlb.wanlibao.utils;
/**
 * 防止控件被重复点击
 * 
 * 解决的思路如下：
 * 1. 需要定义一个全局变量 lastClickTime, 用来记录最后点击的时间.
 * 2. 每次点击前需要进行判断, 用lastClickTime 和当前时间想比较，并且
 * 更新最后点击时间，若小于临界值，则算无效点击，不触发事件
 * @author user
 *
 */
public class VoidRepeatClickUtil {
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 800) {   
            return true;   
        }   
        lastClickTime = time;   
        return false;   
    }
}
