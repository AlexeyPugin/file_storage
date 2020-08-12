package com.shared.utils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public final static String DATE_TIME = "dd-MM-yyyy HH:mm:ss";

    public static Date parseDateTime(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME);
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date dateTimeNow() {
        final DateTime dateTime = new DateTime().toDateTime();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME);
        try {
            return dateFormat.parse(dateTime.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
