package com.ppab1.dreamsaver.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.ppab1.dreamsaver.utils.AppUtils.locale;

public class DateUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, locale);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentTime(){
        DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, locale);
        Date time = new Date();
        return timeFormat.format(time);
    }

    // Konversi tanggal dari yyyy-MM-dd ke dd MMMM yyyy -> 2020-02-13 to 13 Februari 2020
    public static String getFullDate(String date, boolean isSimple){
        if (isValidDateFormat(date)){
            int[] arrayDate = getArrayDate(date);
            String month = new DateFormatSymbols().getMonths()[(arrayDate[1])];
            if (isSimple) month = month.substring(0,3);
            return arrayDate[2] + " " + month + " " + arrayDate[0];
        } else return "-1";
    }

    // Mendapatkan tanggal baru setelah tanggal sebelumnya ditambah n hari
    static String addDay(String oldDate, int numberOfDays){
        if (isValidDateFormat(oldDate)){
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, locale);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateFormat.parse(oldDate));
                calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);
                return dateFormat.format(calendar.getTime());
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        return "-1";
    }

    /*
    * Fungsi private
    * */

    // Konversi tanggal ke array
    private static int[] getArrayDate(String date){
        if (isValidDateFormat(date)){
            String[] stringArrayDate = date.split("-");
            int[] integerArrayDate = new int[3];
            for (int i = 0; i < 3; i++) integerArrayDate[i] = Integer.parseInt(stringArrayDate[i]);
            // Karena bulan di mulai dari 0, jadi dikurangi 1
            return new int[] {integerArrayDate[0], integerArrayDate[1]-1, integerArrayDate[2]};
        } else return null;
    }

    // Mengecek apakah tanggal sudah sesuai dengan format
    private static boolean isValidDateFormat(String date){
        if (date != null){
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, locale);
                dateFormat.parse(date);
                return true;
            } catch (ParseException e) {
                return false;
            }
        } else return false;
    }
}
