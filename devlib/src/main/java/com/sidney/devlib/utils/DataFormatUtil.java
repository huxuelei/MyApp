package com.sidney.devlib.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间格式化处理
 *
 * @author huxuelei
 * 时间格式的处理
 */
public class DataFormatUtil {
    /*
     * 时间戳转化为时间
     * dateFormat 时间格式  data 时间戳
     * 1、yyyy-MM-dd HH:mm:ss 年月日时分秒
     * 2、MM/dd 月日
     * 3、HH:mm 时分
     * 4、yyyy-MM-dd HH:mm 年月日时分
     * */
    public static String dataFormat(String dateFormat, String data) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String sd = "";
        if (data.toString().length() == 13) {
            sd = sdf.format(new Date(Long.parseLong(data)));// 服务端传过来的时间戳Java要乘以1000
        } else if (data.toString().length() == 10) {
            sd = sdf.format(new Date(Long.parseLong(data) * 1000));// 服务端传过来的时间戳Java要乘以1000
        }
        return sd;
    }

    /*
     * 将长时间格式字符串转换为时间
     * dateFormat 时间格式
     * 1、yyyy-MM-dd HH:mm:ss 年月日时分秒
     * 2、MM/dd 月日
     * 3、HH:mm 时分
     * 4、yyyy-MM-dd HH:mm 年月日时分
     * */
    public static Date strToDateLong(String strDate, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /*
     * 获得当前系统时间
     * dateFormat 时间格式
     * 1、yyyy-MM-dd HH:mm:ss 年月日时分秒
     * 2、MM/dd 月日
     * 3、HH:mm 时分
     * 4、yyyy-MM-dd HH:mm 年月日时分
     * */
    public static String nowData(String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);// 设置日期格式
        return df.format(System.currentTimeMillis());
    }

    /*
     * 时间比较
     * dateFormat 时间格式
     * 1、yyyy-MM-dd HH:mm:ss 年月日时分秒
     * 2、MM/dd 月日
     * 3、HH:mm 时分
     * 4、yyyy-MM-dd HH:mm 年月日时分
     * */
    public static boolean compareData(String dateFormat, String nowtime, String time) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(nowtime));
            c2.setTime(df.parse(time));
        } catch (java.text.ParseException e) {
            // System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);
        if (result == 0) {
            //System.out.println("c1相等c2");
            return false;
        } else if (result < 0) {
            //System.out.println("c1小于c2");
            return false;
        } else {
            //System.out.println("c1大于c2");
            return true;
        }
        //return false;
    }

    /*
     * 获得昨天的时间
     * dateFormat 时间格式  data 时间戳
     * 1、yyyy-MM-dd HH:mm:ss 年月日时分秒
     * 2、MM/dd 月日
     * 3、HH:mm 时分
     * 4、yyyy-MM-dd HH:mm 年月日时分
     * */
    public static String yesterdayData(String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);// 设置日期格式
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date time = cal.getTime();
        return df.format(time);
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateTimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 日期格式字符串转换成毫秒值
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Long dateTimeMlili(String dateStr, String format) {
        long mTimeMilo;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            mTimeMilo = sdf.parse(dateStr).getTime();
        } catch (Exception e) {
            mTimeMilo = 0;
            e.printStackTrace();
        }
        return mTimeMilo;
    }

    /**
     * 得到某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 得到某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 计算data1与date2相差几天，其中date1必须大于date2
     *
     * @param date1 较靠后的日期
     * @param date2 较靠前的日期
     * @return -1--输入的时间有误
     * >=0--两天的时间间隔
     */
    public static int getDayInterval(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        Date d1 = sdf.parse(date1, new ParsePosition(0));
        Date d2 = sdf.parse(date2, new ParsePosition(0));
        long m1 = d1.getTime();
        long m2 = d2.getTime();
        if (m1 < m2) {
            return -1;
        } else {
            return (int) ((m1 - m2) / 1000 / 3600 / 24) + 1;
        }
    }

    /**
     * 时间相差多少
     *
     * @param date1  date1
     * @param date2  date2
     * @param format "yyyy-MM-dd HH:mm:ss"
     * @return 相差小时数
     */
    public static long differDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            long time = dt1.getTime() - dt2.getTime();
            return time / (1000 * 60 * 60);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 当前时间在不在开始时间和结束时间之内
     *
     * @param startDate 开始时间 （例：161120 ，16年11月20号）
     * @param endDate   结束时间 （例：161127 ，16年11月27号）
     * @param value     当前日期  （例：1611260000 ，16年11月26号）
     * @return true 在时间段之内， false 不在时间段之内
     */
    public static boolean between(String startDate, String endDate, String value) {
        return value.compareTo(startDate + "0000") >= 0 && value.compareTo(endDate + "0000") <= 0;
    }

}
