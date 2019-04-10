package com.szilardmakai.simplenoteapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatUtils {

    private static final String MONTH_FORMAT = "MMM";

    private static String convertCalendarToTime(Calendar calendar) {
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        hour = singleDigitToDouble(hour);
        minute = singleDigitToDouble(minute);

        return hour + ":" + minute;
    }

    private static String convertCalendarToDate(Calendar calendar) {
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = new SimpleDateFormat(MONTH_FORMAT).format(calendar.getTime());
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        return day + " " + month + " " + year;
    }

    public static String convertLongToDateString(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String hourMinute = convertCalendarToTime(calendar);
        String date = convertCalendarToDate(calendar);
        String seconds = String.valueOf(calendar.get(Calendar.SECOND));
        seconds = singleDigitToDouble(seconds);

        return hourMinute + ":" + seconds + "   " + date;
    }

    private static String singleDigitToDouble(String value) {
        if (value.length() <2) {
            value = "0" + value;
        }
        return value;
    }
}
