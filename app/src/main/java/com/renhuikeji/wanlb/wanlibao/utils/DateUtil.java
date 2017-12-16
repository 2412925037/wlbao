package com.renhuikeji.wanlb.wanlibao.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.renhuikeji.wanlb.wanlibao.bean.TokenBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static Gson gson;


    public static TokenBean getTokenBean(String result) {
        result = removeBOM(result);
        TokenBean myOrderBean = null;
        if (gson == null) {
            gson = new Gson();
        }
        myOrderBean = gson.fromJson(result, TokenBean.class);
        return myOrderBean;
    }

    public static final String removeBOM(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }
        if (data.startsWith("\ufeff")) {
            return data.substring(1);
        } else {
            return data;
        }
    }


    public static String dateFormat(String sdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = null;
        try {
            dateString = formatter.format(formatter.parse(sdate.replace("/", "-")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }


    public static String converTime(long timestamp) {
        long currentSeconds = System.currentTimeMillis() / 1000;
        long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
        String timeStr;
        if (timeGap > 24 * 60 * 60) {// 1天以上
            timeStr = timeGap / (24 * 60 * 60) + "天前";
        } else if (timeGap > 60 * 60) {// 1小时-24小时
            timeStr = timeGap / (60 * 60) + "小时前";
        } else if (timeGap > 60) {// 1分钟-59分钟
            timeStr = timeGap / 60 + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * 将指定模板的时间类型转化成long型的
     *
     * @param sdate
     * @param fomat 将要进行转化的字符串的模板
     * @return
     * @throws ParseException
     */

    public static long dateToLong(String sdate, String fomat) throws ParseException {
//		Date d = new Date();
//		long t = d.getTime();
//
//
        // 将字符串类型转化成Date类型
        SimpleDateFormat sdf = new SimpleDateFormat(fomat);
        Date d2 = sdf.parse(sdate.replace("/", "-"));// 将String to Date类型
        long t3 = d2.getTime();
        return t3;
    }


    /**
     * 将long时间 转化为格式化时间
     *
     * @return
     */
    public static String getStandardTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return time <= 0 ? sdf.format(new Date()) : sdf.format(new Date(time));
    }

    /**
     * 得到当前的系统的时间 ，并按指定模板输出
     *
     * @return yyyy/MM/dd HH:mm:ss  yyyy-MM-dd HH:mm:ss:SS
     */
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return formatter.format(new Date());
    }

    /**
     * 获取当天时间的0点时间
     *
     * @return
     */
    public static long getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTimeInMillis();
    }


    /**
     * 获取当天时间24点时间
     *
     * @return
     */
    public static long getTimesnight() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 比较某时间点是否在某时间段
     */
    public static boolean commpareTime(long time, long startTime, long endTime) {

        if (time > startTime && time < endTime) {
            return true;
        }
        return false;

    }

    /**
     * 两个时间段相隔是否在一定的时间内，如两个时间是否相隔20分钟
     *
     * @param time      相隔的时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static boolean intervalTime(long time, long startTime, long endTime) {
        if (startTime - endTime >= time) {
            return true;
        }
        return false;
    }


    /**
     * 获取系统当前时间(毫秒)
     *
     * @return
     */
    public static long getSystemTime() {
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }


    /**
     * 将标准时间格式转换为毫秒
     *
     * @param str
     * @return
     */
    public static long changeMilles(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long millionSeconds;
        if (!TextUtils.isEmpty(str))
            try {
                millionSeconds = sdf.parse(str.replace("/", "-")).getTime();
                return millionSeconds;
            } catch (ParseException e) {
                e.printStackTrace();
            }// 毫秒
        return -1;
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 3) {
            return time / 3600 / 24 + "天前";
        } else {
            return getTime(timeStamp);
        }
    }

    /**
     * 得到昨天的日期
     *
     * @return
     */
    public static String getYestoryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yestoday = sdf.format(calendar.getTime());
        return yestoday;
    }

    /**
     * 得到今天的日期
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 得到今天的日期
     *
     * @return
     */
    public static String getTodayDate1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 得到日期   yyyy-MM-dd
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String formatDate(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 得到时间  HH:mm:ss
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String getTime(long timeStamp) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        return time / 60 + "";
    }


    public static void panduan(TextView textView, String string) {
        if (TextUtils.isEmpty(string)) {
            textView.setText("");
        } else {
            textView.setText(string);
        }

    }

    ///////////////////////////////// 自定义日历的需求方法 ////////////////////////////////////
    public static String[] weekName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

    public static CustomDate getNextSunday() {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7 - getWeekDay() + 1);
        CustomDate date = new CustomDate(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        return date;
    }

    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH) + 1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;

    }

    public static int getWeekDayFromDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month))
                + "-01";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static boolean isToday(CustomDate date) {
        return (date.year == DateUtil.getYear() &&
                date.month == DateUtil.getMonth()
                && date.day == DateUtil.getCurrentMonthDay());
    }

    public static boolean isCurrentMonth(CustomDate date) {
        return (date.year == DateUtil.getYear() &&
                date.month == DateUtil.getMonth());
    }
    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;// +1是因为返回来的值并不是代表月份，而是对应于Calendar.MAY常数的值，
        // Calendar在月份上的常数值从Calendar.JANUARY开始是0，到Calendar.DECEMBER的11
    }

    /**
     * 获取当前的时间为该月的第几天
     *
     * @return
     */
    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前的时间为该周的第几天
     *
     * @return
     */
    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前时间为该天的多少点
     *
     * @return
     */
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        // Calendar calendar = Calendar.getInstance();
        // System.out.println(calendar.get(Calendar.HOUR_OF_DAY)); // 24小时制
        // System.out.println(calendar.get(Calendar.HOUR)); // 12小时制
    }

    /**
     * 获取当前的分钟时间
     *
     * @return
     */
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 通过获得年份和月份确定该月的日期分布
     *
     * @param year
     * @param month
     * @return
     */
    public static int[][] getMonthNumFromDate(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);// -1是因为赋的值并不是代表月份，而是对应于Calendar.MAY常数的值，

        int days[][] = new int[6][7];// 存储该月的日期分布

        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);// 获得该月的第一天位于周几（需要注意的是，一周的第一天为周日，值为1）

        int monthDaysNum = getMonthDaysNum(year, month);// 获得该月的天数
        // 获得上个月的天数
        int lastMonthDaysNum = getLastMonthDaysNum(year, month);

        // 填充本月的日期
        int dayNum = 1;
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                if (i == 0 && j < firstDayOfWeek - 1) {// 填充上个月的剩余部分
                    days[i][j] = 0;
                } else if (dayNum <= monthDaysNum) {// 填充本月
                    days[i][j] = dayNum++;
                } else {// 填充下个月的未来部分
                    days[i][j] = 0;
                }
            }
        }

        return days;

    }
    /**
     * 根据年数以及月份数获得上个月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getLastMonthDaysNum(int year, int month) {

        int lastMonthDaysNum = 0;

        if (month == 1) {
            lastMonthDaysNum = getMonthDaysNum(year - 1, 12);
        } else {
            lastMonthDaysNum = getMonthDaysNum(year, month - 1);
        }
        return lastMonthDaysNum;

    }

    /**
     * 根据年数以及月份数获得该月的天数
     *
     * @param year
     * @param month
     * @return 若返回为负一，这说明输入的年数和月数不符合规格
     */
    public static int getMonthDaysNum(int year, int month) {

        if (year < 0 || month <= 0 || month > 12) {// 对于年份与月份进行简单判断
            return -1;
        }

        int[] array = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };// 一年中，每个月份的天数

        if (month != 2) {
            return array[month - 1];
        } else {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {// 闰年判断
                return 29;
            } else {
                return 28;
            }
        }

    }
}
