package com.hd.hdtool.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtils {

    public static String getNowTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int seccond = calendar.get(Calendar.SECOND);
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + seccond;
    }

}
