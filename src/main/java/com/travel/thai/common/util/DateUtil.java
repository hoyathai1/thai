package com.travel.thai.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String today_date() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }

    public static String today_time() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        return formatter.format(date);
    }
}
