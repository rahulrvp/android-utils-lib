package com.github.rahulrvp.android_utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.Calendar;

/**
 * @author Rahul Raveendran V P
 *         Created on 30/10/16 @ 3:34 PM
 *         https://github.com/rahulrvp
 */


public class DateTimeUtils {

    private static final String LOG_TAG = "DateTimeUtils";

    public static String getTimeAgoString(long millis) {
        String result = "";

        Calendar now = Calendar.getInstance();

        Calendar then = Calendar.getInstance();
        then.setTimeInMillis(millis);

        if (now.getTimeInMillis() > then.getTimeInMillis()) {

            String yearDiffString = getDiffString(now, then, Calendar.YEAR, "Year");

            if (!TextUtils.isEmpty(yearDiffString)) {
                result += yearDiffString;
            }

            String monthDiffString = getDiffString(now, then, Calendar.MONTH, "Month");

            if (!TextUtils.isEmpty(yearDiffString)) {
                result += " ";
            }

            result += monthDiffString;

            String weekDiffString = getDiffString(now, then, Calendar.WEEK_OF_MONTH, "Week");
            if (!TextUtils.isEmpty(monthDiffString)) {
                result += " ";
            }

            result += weekDiffString;

            String dayDiffString = getDiffString(now, then, Calendar.DAY_OF_MONTH, "Day");
            if (!TextUtils.isEmpty(weekDiffString)) {
                result += " ";
            }

            result += dayDiffString;

            String hourDiffString = getDiffString(now, then, Calendar.HOUR_OF_DAY, "Hour");
            if (!TextUtils.isEmpty(dayDiffString)) {
                result += " ";
            }

            result += hourDiffString;

            String minDiffString = getDiffString(now, then, Calendar.MINUTE, "Minute");
            if (!TextUtils.isEmpty(hourDiffString)) {
                result += " ";
            }

            result += minDiffString;

            String secDiffString = getDiffString(now, then, Calendar.SECOND, "Second");
            if (!TextUtils.isEmpty(minDiffString)) {
                result += " ";
            }

            result += secDiffString;

            if (!TextUtils.isEmpty(result)) {
                result += " ago";
            }
        } else {
            Log.e(LOG_TAG, "Given time is in future.");
        }

        return result;
    }

    public static String getDiffString(Calendar now, Calendar then, int fieldInt, String fieldString) {
        String result = "";

        int diff = now.get(fieldInt) - then.get(fieldInt);

        if (diff > 0) {
            result = result + diff + " " + ((diff == 1) ? fieldString : fieldString + "s");
        }

        return result;
    }
}
