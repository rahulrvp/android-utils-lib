package com.github.rahulrvp.android_utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

        int diff = getDiffInt(now, then, fieldInt);

        if (diff > 0) {
            result = result + diff + " " + ((diff == 1) ? fieldString : fieldString + "s");
        }

        return result;
    }

    public static int getDiffInt(Calendar now, Calendar then, int fieldInt) {
        int result = 0;

        if (now != null && then != null) {
            result = now.get(fieldInt) - then.get(fieldInt);
        }

        return result;
    }

    public static String getLastActivityTimeString(long millisToCompare, String dateFormat, boolean shouldShowToday) {
        String result = null;

        Calendar today = Calendar.getInstance();

        Calendar thatDay = Calendar.getInstance();
        thatDay.setTimeInMillis(millisToCompare);

        if (getDiffInt(today, thatDay, Calendar.YEAR) == 0 && getDiffInt(today, thatDay, Calendar.MONTH) == 0) {
            int dayDiff = getDiffInt(today, thatDay, Calendar.DAY_OF_YEAR);

            // check if it is today
            if (dayDiff == 0) {
                if (shouldShowToday) {
                    result = "Today";
                } else {
                    int hours = getDiffInt(today, thatDay, Calendar.HOUR_OF_DAY);
                    if (hours < 24 && hours > 0) {
                        if (hours == 1) {
                            result = "An hour ago";
                        } else {
                            result = (hours + " hours ago");
                        }
                    } else {
                        int minutes = getDiffInt(today, thatDay, Calendar.MINUTE);
                        if (minutes < 60) {
                            if (minutes == 0) {
                                result = "Just Now";
                            } else {
                                result = (minutes + " minutes ago");
                            }
                        }
                    }
                }
            } else if (dayDiff == 1) {
                // normal yesterday
                result = "Yesterday";
            }
        } else if (getDiffInt(today, thatDay, Calendar.YEAR) == 1) {
            // new year's yesterday
            int thisMonth = today.get(Calendar.MONTH);
            int thisDate = today.get(Calendar.DAY_OF_MONTH);
            int thatMonth = thatDay.get(Calendar.MONTH);
            int thatDate = thatDay.get(Calendar.DAY_OF_MONTH);

            if (thisMonth == Calendar.JANUARY
                    && thatMonth == Calendar.DECEMBER
                    && thisDate == 1
                    && thatDate == 31) {

                result = "Yesterday";
            }
        } else {
            SimpleDateFormat sdf = null;

            if (!TextUtils.isEmpty(dateFormat)) {
                try {
                    sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }

            if (sdf == null) {
                sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
            }

            result = sdf.format(millisToCompare);
        }

        return result;
    }
}
