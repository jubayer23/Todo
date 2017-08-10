package com.creative.todo.Utility;

import com.creative.todo.appdata.AppConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jubayer on 8/9/2017.
 */

public class DateTimeUtil {

    public static String printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        String output = "";

        if (elapsedDays > 0) {
            output = output + elapsedDays + "d ";
        }

        if (elapsedHours > 0) {
            output = output + elapsedHours + "h ";
        }
        if (elapsedMinutes > 0) {
            output = output + elapsedMinutes + "m ";
        }


        // System.out.printf(
        //        "%d days, %d hours, %d minutes, %d seconds%n",
        //        elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        return output;
    }

    /**
     * get datetime
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = AppConstant.getDateTimeFormat();
        Date date = new Date();
        return dateFormat.format(date);
    }
}
