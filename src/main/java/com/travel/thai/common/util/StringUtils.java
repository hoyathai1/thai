package com.travel.thai.common.util;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return str==null || str.length()==0 || str.trim().length()==0;
    }

    public static boolean isNotEmpty(String str) {
        return !(str==null || str.length()==0 || str.trim().length()==0);
    }
}
