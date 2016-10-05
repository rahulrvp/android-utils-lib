package io.github.rahulrvp.android_utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author Rahul Raveendran V P
 *         Created on 3/10/16 @ 7:40 PM
 *         https://github.com/rahulrvp
 */


public class PhoneUtils {

    private static final String LOG_TAG = "PhoneUtils";

    /**
     * Provides the network operator's name of the primary SIM in use.
     *
     * @param context valid {@link Context} object
     * @return primary SIM operator's name
     */
    public static String getSimOperatorName(Context context) {
        String simOperatorName = null;

        if (context != null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                simOperatorName = telephonyManager.getSimOperatorName();
            }
        }

        if (TextUtils.isEmpty(simOperatorName)) {
            Log.e(LOG_TAG, "Could not read primary SIM operator name.");
        }

        return simOperatorName;
    }

}
