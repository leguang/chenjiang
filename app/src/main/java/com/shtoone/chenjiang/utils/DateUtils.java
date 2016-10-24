package com.shtoone.chenjiang.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    @SuppressLint("SimpleDateFormat")
    public static String ChangeTimeToLong(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(time);
            time = String.valueOf(date.getTime() / 1000);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return time;
    }

    public static String[] GetTimeFromTo() throws ParseException {
        // ½ñÌìµÄ´¦ÖÃ
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endtime = format.format(date);
        date = format.parse(endtime);
        endtime = String.valueOf(date.getTime() / 1000);

        // ÏòÇ°Ò»Äê
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -1);

        // ½«1ÄêÇ°Ê±¼ä×ª»»ÎªÊ±¼ä´Á
        Date dateFrom = cal.getTime();
        String starttime = format.format(dateFrom);
        dateFrom = format.parse(starttime);
        starttime = String.valueOf(dateFrom.getTime() / 1000);

        // ·µ»Ø1ÄêÇ°ºÍ½ñÌì
        String[] arr = new String[]{starttime, endtime};

        return arr;
    }

    public static String[] GetTimeFromToYYYYMMM() throws ParseException {
        // ½ñÌìµÄ´¦ÖÃ
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endtime = format.format(date).replace(" ", "%20");

        // ÏòÇ°Ò»Äê
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -1);

        // ½«1ÄêÇ°Ê±¼ä×ª»»ÎªÊ±¼ä´Á
        Date dateFrom = cal.getTime();
        String starttime = format.format(dateFrom).replace(" ", "%20");

        // ·µ»Ø1ÄêÇ°ºÍ½ñÌì
        String[] arr = new String[]{starttime, endtime};

        return arr;
    }

    /**
     * <li>¹¦ÄÜÃèÊö£ºÊ±¼äÏà¼õµÃµ½ÌìÊý
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        beginDateStr = beginDateStr.replace("Äê", "-").replace("ÔÂ", "-").replace("ÈÕ", "");
        endDateStr = endDateStr.replace("Äê", "-").replace("ÔÂ", "-").replace("ÈÕ", "");
        beginDateStr = beginDateStr.split(" ")[0];
        endDateStr = endDateStr.split(" ")[0];
        Log.d("day1", beginDateStr);
        Log.d("day2", endDateStr);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
            // System.out.println("Ïà¸ôµÄÌìÊý="+day);
        } catch (ParseException e) {
            // TODO ×Ô¶¯Éú³É catch ¿é
            e.printStackTrace();
        }
        return day;
    }

}
