package com.example.apartmenttourschedular.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    public static final String DEFAULT_DATE_TIME_PATTERN_24_HOURS = "dd-MM-yyyy   HH:mm";
    public static final String DEFAULT_DATE_TIME_PATTERN_12_HOURS = "dd-MM-yyyy   hh:mm aa";
    public static final String DEFAULT_TIME_PATTERN = "hh.mm aa";

    public static Date convertStringToDate(String inputDateString, String pattern){

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = null;
        try {

            date = formatter.parse(inputDateString);
            System.out.println(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date convertStringToDate(String inputDate){
        return convertStringToDate(inputDate, DEFAULT_DATE_TIME_PATTERN_12_HOURS);
    }

    public static Date convertStringToDate(String inputDate, boolean is24Hours){
        if(is24Hours){
            return convertStringToDate(inputDate, DEFAULT_DATE_TIME_PATTERN_24_HOURS);
        } else {
            return convertStringToDate(inputDate, DEFAULT_DATE_TIME_PATTERN_12_HOURS);
        }

    }

    public static String convertDateToString(Date date, String pattern){

        return DateFormatUtils.format(date, pattern);

       /* SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);*/
    }

    public static String convertDateToString(Date date){
        return convertDateToString(date, DEFAULT_DATE_TIME_PATTERN_12_HOURS);
    }

    public static void convertToQuarterlyFormat(Calendar cal){
        if(cal != null){
            int mins = cal.get( Calendar.MINUTE);
            cal.set( Calendar.MINUTE, mins / 15 * 15);
        }
    }

    public static String extractTimeFromDate(Date date){
        return convertDateToString(date, DEFAULT_TIME_PATTERN);
    }
}
