package net.n256coding.calendarapp.Models;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nishan on 9/22/2017.
 */

public class DateEx extends Date{
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public DateEx(){
        super();
    }

    public static String getDateString(Date date){
        return dateFormat.format(date);
    }

    public static String getTimeString(Date date){
        return timeFormat.format(date);
    }

    public static String getDateTimeString(Date date){
        return dateTimeFormat.format(date);
    }

    public static Date getDateOfDate(String date){
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new DateEx();
    }

    public static Date getDateOfTime(String date){
        try {
            return timeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new DateEx();
    }

    public static Date getDateOfDateTime(String date){
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new DateEx();
    }

    public static int getYearOf(Date date){
        Calendar calendar = Calendar.getInstance();
        if(date == null)
            return calendar.get(Calendar.YEAR);
        else{
            calendar.clear();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        }
    }

    public static int getMonthOf(Date date){
        Calendar calendar = Calendar.getInstance();
        if(date == null)
            return calendar.get(Calendar.MONTH)+1;
        else{
            calendar.clear();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH)+1;
        }
    }

    public static int getDayOf(Date date){
        Calendar calendar = Calendar.getInstance();
        if(date == null)
            return calendar.get(Calendar.DAY_OF_MONTH);
        else{
            calendar.clear();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_MONTH);
        }
    }

    public static Date addMinutesTo(Date date, int noOfMinutes){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, noOfMinutes);
        return calendar.getTime();
    }

    public static Date getTodayMorning(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getTodayMidNight(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
}
