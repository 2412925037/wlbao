package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc:一些用到的验证.
 */

public class CheckUtil {

    /**
     * 验证用户是否登录
     */
    public static boolean isLogin(Context mcontext) {
        String str_uid = (String) SPUtils.get(mcontext, Constant.User_Uid, "");
        String str_phone = (String) SPUtils.get(mcontext, Constant.User_Phone, "");
        if (!TextUtils.isEmpty(str_uid) && !TextUtils.isEmpty(str_phone)) {
            return true;
        }

//
        return false;
    }

    /**
     * 密码验证字母数字下划线
     */
    public static boolean checkPassword(String password) {
        if (password.matches("^[A-Za-z0-9_]+$")) {
            return true;
        }
        return false;
    }

    /*
     * 验证手机号 ,
     */
    public static boolean isMobileNO(String mobiles) {
        String regExp = "^[1]([3][0-9]{1}|53|59|81|80|85|86|88|89|87|55|56|50|51|52|58|57|82|47)[0-9]{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobiles);
        boolean ismobi = m.find();
        return ismobi;
    }

    /**
     * 验证邮箱.
     */
    public static boolean isEmail(String strEmail) {
        String regExp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 判断是否全是数字
     *
     * @param str
     * @return
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 验证手机格式
     */
    public static boolean isPhoneNum(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][35748]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    /**
     * 验证是否为中文
     */
    public static boolean isChineseChar(String str) {
        //中文文字的unicode范围[\u4E00-\u9FA5]
        String regex = "[\\u4E00-\\u9FA5]+";
        return !TextUtils.isEmpty(str) && str.matches(regex);
    }

    /**
     * 验证是否为英文
     */
    public static boolean isEnglishChar(String str) {
        //英文文字的unicode范围[a-zA-Z]
        String regex = "[a-zA-Z]+";
        return !TextUtils.isEmpty(str) && str.matches(regex);
    }

    /**
     * 校验邮箱格式是否正确
     * 正则表达式校验法
     *
     * @param strEmail
     * @return boolean
     */
    public static boolean checkEmail(String strEmail) {
        if (TextUtils.isEmpty(strEmail)) return false;

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(strEmail);

        return m.matches();
    }

}
