package com.algalopez.mytv.data.omdb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/31/16
 */

public class dateUtil {

    public static boolean isDate(String date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // "2016-07-15"

        try {
            df.parse(date);
        } catch (java.text.ParseException e) {
            return false;
        }
        return true;
    }


    // Check if date2 is before date1
    public static boolean isBefore(String date1, String date2) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // "2016-07-15"

        if (isDate(date1) && !isDate(date2)){
            return false;
        } else if (!isDate(date1) && isDate(date2)) {
            return true;
        } else if (!isDate(date1) && !isDate(date2)) {
            return false;
        }

        try {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);

            return  (d2.before(d1));


        } catch (java.text.ParseException e) {
            return false;
        }

    }


    // Check if date2 is after date1
    public static boolean isAfter(String date1, String date2) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // "2016-07-15"

        if (isDate(date1) && !isDate(date2)){
            return false;
        } else if (!isDate(date1) && isDate(date2)) {
            return true;
        } else if (!isDate(date1) && !isDate(date2)) {
            return false;
        }

        try {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);

            return (d2.after(d1));

        } catch (java.text.ParseException e) {
            return false;
        }

    }
}
