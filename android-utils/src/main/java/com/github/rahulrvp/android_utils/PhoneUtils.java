/**
 * Copyright 2016 Rahul Raveendran
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rahulrvp.android_utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Collection of phone related util methods to save lines of code.
 *
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
