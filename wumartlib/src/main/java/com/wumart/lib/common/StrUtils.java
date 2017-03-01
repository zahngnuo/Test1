package com.wumart.lib.common;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StrUtils {

    //检验手机号格式
    public static boolean checkPhoneValidity(String phone) {
        return phone.matches("^1[3456789]\\d{9}$");
    }

    //检验座机号格式(至少7位以上)
    public static boolean checktel(String tel) {
        boolean flag = tel.matches("\\d{7,}$");
        return flag;
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(CharSequence str) {
        return null == str || "".equals(str.toString().trim());
    }

    /**
     * 判断字符串不为空
     */
    public static boolean isNotEmpty(CharSequence str) {
        return null != str && 0 < str.toString().trim().length();
    }

    /**
     * 把字符串以4位分开的计数法
     */
    public static String forMat(String str) {
        if (isEmpty(str)) {
            return null;
        }
        int length = str.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(str.charAt(i));
            if ((i + 1) % 4 == 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * desc: 取str中满足rgx的首次出现的index位置
     *
     * @author andy he
     * @time 2016/4/8 14:53
     */
    public static int getMatcherIndex(String str, String rgx) {
        Matcher matcher = Pattern.compile(rgx).matcher(str);
        if (matcher.find()) {
            return matcher.start();
        }
        return str.length();
    }

    /**
     * desc: 检测车牌号是否合法
     *
     * @author andy he
     * @time 2016/5/17 10:06
     */
    public static boolean isCarNo(String carNo) {
        return carNo.matches("^[\\u4e00-\\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$");
    }

    public static String strFormat(String formart,String def, Object... args) {
        if (formart == null)
            return "";
        return String.format(formart, args).replaceAll("null", def);
    }

    public static String strDefFormat(String formart, Object... args) {
        return strFormat(formart,"0",args);
    }
}
