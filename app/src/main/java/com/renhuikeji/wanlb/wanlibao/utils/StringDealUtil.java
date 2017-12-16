package com.renhuikeji.wanlb.wanlibao.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;

/**
 * 字符串工具类
 *
 * @author songdy
 */
public class StringDealUtil {
    public static String formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    public static String dealPrice(String price) {
        if (price.contains(".00")) {
            price = price.replace(".00", "");
        } else if (price.contains(".0")) {
            price = price.replace(".0", "");
        }
        return price;
    }

    public static String urlEncode(String url)
            throws UnsupportedEncodingException {
        String path = url;
        int postion = path.lastIndexOf("/") + 1;
        String appName = path.substring(postion, path.length() - 4);

        appName = URLEncoder.encode(appName, "utf-8");
        path = path.substring(0, postion);
        return path + appName + ".apk";

    }

    public static String replaceDateSeparator(String date) {
        String separator = "-";
        if (date.contains("/")) {
            date.replaceAll("/", separator);
        } else if (date.contains("|")) {
            date.replaceAll("|", separator);
        } else if (date.contains(".")) {
            date.replaceAll(".", separator);
        }
        return date;
    }

    public static String formatPhoneNum(String phoneNum) {

        String phoneNum_1 = phoneNum.substring(0, 3);
        String phoneNum_2 = phoneNum.substring(3, 7);
        String phoneNum_3 = phoneNum.substring(7);

        return phoneNum_1 + "-" + phoneNum_2 + "-" + phoneNum_3;
    }

    /**
     * 比较版本
     *
     * @param clientVer 客户端版本
     * @param serverVer 服务端版本
     * @return false 不需要升级
     */
    public static boolean compareVersion(String clientVer, String serverVer) {
        boolean flag = false;
        if (clientVer != null && clientVer.length() > 0 && serverVer != null && serverVer.length() > 0) {
            String[] clientStr = clientVer.trim().split("[.]");
            String[] serverStr = serverVer.trim().split("[.]");

            if (clientStr.length == 2) {
                clientVer = clientVer + ".0";
                clientStr = clientVer.trim().split("[.]");
            }

            if (serverStr.length == 2) {
                serverVer = serverVer + ".0";
                serverStr = serverVer.trim().split("[.]");
            }


            for (int i = 0; i < 3; i++) {
                if (clientStr[i] != null && serverStr != null) {
                    if (Integer.parseInt(clientStr[i]) > Integer.parseInt(serverStr[i])) {
                        break;
                    } else if (Integer.parseInt(clientStr[i]) == Integer.parseInt(serverStr[i])) {
                        continue;
                    } else {
                        flag = true;
                        break;
                    }
                }
            }
        }

        return flag;
    }

    /**
     * 替换HTML标签属性
     *
     * @param str
     * @return
     */
    public static String replaceHtmlTag(String str) {
        str = str.replace("&nbsp;", " ");
        str = str.replace("<br/>", "\n");
        return str;
    }


    /**
     * 把String中的标签替换成html可识别的标签
     *
     * @param str
     * @return
     */
    public static String changeToHtml(String str) {
        //<font color=000000> <strong></strong></font> 黑体加粗
        //<font color=000000> </font>黑体
        //<br/>换行
        String html = str.replace("<b>", "<font color=000000>")
                .replace("</b>", "</font>")
                .replace("\r\n", "<br/>");
        return html;
    }


    /**
     * 处理图片链接地址宽高度
     *
     * @param url
     * @return
     */
    public static String dealImageUrl(String url) {
        String dealUrl = url;
        if (url.contains("&w=") && url.contains("&h=")) {
            int index;
            int index1 = url.indexOf("&w=");
            int index2 = url.indexOf("&h=");
            if (index1 < index2) {
                index = index1;
            } else {
                index = index2;
            }
            dealUrl = url.substring(0, index);
        }/* else if(url.contains("/w") && url.contains("/h")){//http://r.uzaicdn.com/pic/17240/p43520/w540/h303/t1/
            String newUrl = "";
            int whith = Integer.valueOf(url.split("/w")[1].split("/h")[0]);
            int height = Integer.valueOf(url.split("/h")[1].split("/")[0]);
            int index = url.split("/h")[1].indexOf("/");
            String endUrl = url.split("/h")[1].substring(index);
            newUrl = newUrl + url.split("/w") + "/w" + whith + "/h" + height + endUrl;
            dealUrl = newUrl;
        }*/
        return dealUrl;
    }


    /**
     * 把输入流转换成字符串
     *
     * @param is 输入流
     * @return 字符串
     */
    public static String inputStreamToString(InputStream is) throws Exception {
        byte[] b = readInputStream(is);
        String str = new String(b, "UTF-8");

        return str;
    }


    /**
     * 把输入流转换成字节数组
     *
     * @param inStream 输入流
     * @return 字节数组
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 判断应付款和实付款小数点后几位，若超过两位则截取前两位
     * 如90.0000，则变成90.00
     */
    public static String checkDecimal(String str) {
        if (str.contains(".")) {
            String[] split = str.split("\\.");
            if (split[1].length() > 2) {
                String substring = split[1].substring(0, 2);
                String s = new String(split[0] + "." + substring);
                return s;
            } else {
                return str;
            }
        } else {
            return str;
        }
    }

    /**
     * 将用户手机号中间4位变成*号
     */
    public static String encryptPhone(String phone) {
        String s="";
        if(phone.length()>10){
            String substring = phone.substring(0, 3);
            String substring1 = phone.substring(7, 11);
            s = new String(substring + "****" + substring1);
        }
        return s;
    }
    /**
     * 将订单号中间4位变成*号
     */
    public static String encryptString(String str) {
        String s="";
        int l=str.length();
        if(l>11){
            String substring = str.substring(0, 6);
            String substring1 = str.substring(11, l);
            s = new String(substring + "*****" + substring1);
        }
        return s;
    }


}
