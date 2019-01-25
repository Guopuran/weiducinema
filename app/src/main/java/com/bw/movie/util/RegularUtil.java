package com.bw.movie.util;

import java.util.regex.Pattern;

/**
 *
 * @详情 正则判断
 *
 * @创建日期 2018/12/29 20:07
 *
 */
public class RegularUtil {
    public static final String REG_MOBILE = "^1\\d{10}$";

    //判断手机号
    public static boolean isPhone(String mphone)



    {
        return Pattern.matches(REG_MOBILE,mphone);
    }
//
    //判断是否为空
    public static boolean isNull(String mnull){
        return mnull.equals("");
    }

    //判断密码的长度
    public static boolean isPass(String mpass){
        return mpass.length()<6;
    }
}
