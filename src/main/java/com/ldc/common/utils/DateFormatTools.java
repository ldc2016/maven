package com.ldc.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dacheng.liu on 2017/11/20.
 */
public class DateFormatTools{

    private static final Logger LOG = LoggerFactory.getLogger(DateFormatTools.class);

    public static final String SHORT_DATE_FORMAT = "yyyyMMdd";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String SHORT_DATE_TIME_FORMAT = "yyyyMMddHHmm";
    public static final String SHORT_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    public static final String LONG_TIMESTAMP_FORMAT = "yyyyMMddHHmmssS";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    public static final String CHINESE_DATE_FORMAT = "yyyy年MM月dd日";
    public static final String DAY_TIME_START = "00:00:00";
    public static final String DAY_TIME_END = "23:59:59";

    private static final String[] FORMATS = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "HH:mm", "HH:mm:ss", "yyyy-MM", "yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss" };

    public static Date convertDateStrToDate(String dateStr)
    {
        if ((dateStr != null) && (dateStr.length() > 0)) {
            if ((dateStr.length() > 10) && (dateStr.charAt(10) == 'T')) {
                dateStr = dateStr.replace('T', ' ');
            }
            for (String format : FORMATS) {
                if (dateStr.length() == format.length()) {
                    try {
                        return new SimpleDateFormat(format).parse(dateStr);
                    }
                    catch (ParseException e) {
                        LOG.warn(e.getMessage(), e);
                    }
                }
            }
        }
        return null;
    }

    public static Date convertDateTimeStrToDate(String dateTimeStr, String dateFormat) {
        if (!StringUtils.isEmpty(dateTimeStr)) {
            try {
                return new SimpleDateFormat(dateFormat).parse(dateTimeStr);
            }
            catch (ParseException e) {
                LOG.warn(e.getMessage(), e);
            }
        }

        return null;
    }

    public static Date concatYmdAndHm(String ymdStr, String hmStr)
    {
        if ((!StringUtils.isEmpty(ymdStr)) && (!StringUtils.isEmpty(hmStr))) {
            try {
                String dateString = ymdStr.concat(" ")
                                       .concat(hmStr.substring(0, 2)
                                       .concat(":")
                                       .concat(hmStr.substring(2, 4))
                                       .concat(":00"));

                return convertDateTimeStrToDate(dateString, "yyyy-MM-dd HH:mm:ss");
            }
            catch (NullPointerException e) {
                LOG.warn(e.getMessage(), e);
            }
        }
        return null;
    }

    public static String getShortDateFormatStr(Date date)
    {
        return convertDateToString(date, "yyyyMMdd");
    }

    public static String getChineseDateStr(Date date)
    {
        return convertDateToString(date, "yyyy年MM月dd日");
    }

    public static Date convertDateTimeStrToDate(String dateTimeStr)
    {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatDate.parse(dateTimeStr);
        }
        catch (Exception e) {
        }
        return date;
    }

    public static String convertDateToString(Date date) {
        return convertDateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String convertDateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (null == format) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static Date getStartDateTime(Date date)
    {
        String nowDate = convertDateToString(date, "yyyy-MM-dd");
        return convertDateTimeStrToDate(nowDate + " " + "00:00:00");
    }

    public static Date getStartDateTimeByDiffDays(Date date, Integer diffDays)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = df.format(Long.valueOf(date.getTime() + 86400000L * diffDays.intValue()));
        return convertDateTimeStrToDate(nowDate + " " + "00:00:00");
    }

    public static Date getEndDateTime(Date date)
    {
        String nowDate = convertDateToString(date, "yyyy-MM-dd");
        return convertDateTimeStrToDate(nowDate + " " + "23:59:59");
    }

    public static Date getEndDateTimeByDiffDays(Date date, Integer diffDays)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = df.format(Long.valueOf(date.getTime() + 86400000L * diffDays.intValue()));
        return convertDateTimeStrToDate(nowDate + " " + "23:59:59");
    }

    public static Timestamp getLastEndDateTime(Date endDateTime)
    {
        Timestamp ts = new Timestamp(endDateTime.getTime());
        ts.setNanos(999999999);
        return ts;
    }

    public static Timestamp getEndTimeAdd(Date endTime)
    {
        Timestamp ts = new Timestamp(endTime.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(ts);
        c.add(14, 1000);
        c.set(14, 0);
        return new Timestamp(c.getTimeInMillis());
    }

    public static String addDayByDiffDays(Date date, int days)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(date.getTime() + 86400000L * days));
    }


    public static Date addDayForDateByDiffDays(Date date, int day)
    {
        return (new Date(date.getTime() + 86400000L * day));
    }


    public static Long getTimeDiffByDateStr(String startTimeStr, String endTimeStr)
    {
        return getDayDiffByDateStr(startTimeStr, endTimeStr);
    }

    public static Long getTimeDiffByDateTime(Date startDateTime, Date endDateTime)
    {
        return getDayDiffByDateTime(startDateTime, endDateTime);
    }

    public static Long getDayDiffByDateStr(String startTimeStr, String endTimeStr)
    {
        Long days = null;
        Date startShortDate = null;
        Date endShortDate = null;
        try {
            if ((startTimeStr.length() == 10) && (endTimeStr.length() == 10)) {
                startShortDate = new SimpleDateFormat("yyyy-MM-dd").parse(startTimeStr);
                endShortDate = new SimpleDateFormat("yyyy-MM-dd").parse(endTimeStr);
            } else {
                startShortDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTimeStr);
                endShortDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTimeStr);
            }
            days = getDayDiffByDateTime(startShortDate, endShortDate);
        } catch (ParseException e) {
            LOG.warn(e.getMessage());
            days = null;
        }
        return days;
    }

    public static Long getDayDiffByDateTime(Date startDateTime, Date endDateTime)
    {
        Long days = null;

        Calendar c = Calendar.getInstance();
        c.setTime(startDateTime);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        long startLong = c.getTimeInMillis();
        c.setTime(endDateTime);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        long endLong = c.getTimeInMillis();
        days = Long.valueOf((endLong - startLong) / 86400000L);
        return days;
    }

    public static Long getMinuteDiffByDateTime(Date startTime, Date endTime)
    {
        Long diffMins = null;

        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        long startLong = c.getTimeInMillis();
        c.setTime(endTime);
        long endLong = c.getTimeInMillis();
        diffMins = Long.valueOf((endLong - startLong) / 60000L);
        return diffMins;
    }

    public static Long getSecondDiffByDateTime(Date startTime, Date endTime)
    {
        Long diffSeconds = null;
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        long startLong = c.getTimeInMillis();
        c.setTime(endTime);
        long endLong = c.getTimeInMillis();
        diffSeconds = Long.valueOf((endLong - startLong) / 1000L);
        return diffSeconds;
    }

    public static Long getSecondDiffByDateTimeStr(String startTimeStr, String endTimeStr)
    {
        Long diffSeconds = null;
        Date startDate = null;
        Date endDate = null;
        try {
            if ((startTimeStr.length() == 10) && (endTimeStr.length() == 10)) {
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startTimeStr);
                endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endTimeStr);
            } else {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTimeStr);
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTimeStr);
            }

            diffSeconds = getSecondDiffByDateTime(startDate, endDate);
        } catch (ParseException e) {
            LOG.warn(e.getMessage());
            diffSeconds = null;
        }
        return diffSeconds;
    }


    public static Date addSecoundToDateByDiffSeconds(Date date, long secound)
    {
        return new Date(date.getTime() + 1000L * secound);
    }


    public static String getLastMonthDateTimeStrFromDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return sdf.format(calendar.getTime());
    }

    public static Date addDayFormat(Date date, int day){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = df.format(new Date(date.getTime() + 86400000L * day));
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            LOG.error("error date format  date = " + dateString);
        }
        return date;
    }

    public static String getMonthStrFromYearMonth(String yearMonthStr) {
        String month = "";
        if ("0".equals(yearMonthStr.substring(4, 5))) {
            month = yearMonthStr.substring(5, 6);
        } else {
            month = yearMonthStr.substring(4, 6);
        }
        return month;
    }


    public static Date getMonthBillTimeFromDate(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String dateTimeString = df.format(date);
        try {
            date = df.parse(dateTimeString);
        } catch (ParseException e) {
            LOG.error("error date format  date = " + dateTimeString);
        }
        return date;
    }


    public static int getDayIntegerFromDate(Date date){
        String day = convertDateToString(date, "d");
        if(day==null){
            return -1;
        }
        return Integer.parseInt(day);
    }
}
