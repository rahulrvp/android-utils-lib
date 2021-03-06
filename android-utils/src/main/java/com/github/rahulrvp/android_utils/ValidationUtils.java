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

import android.telephony.PhoneNumberUtils;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Wraps commonly used form validation methods.
 *
 * @author Rahul Raveendran V P
 *         Created on 11/10/16 @ 4:39 PM
 *         https://github.com/rahulrvp
 */


public class ValidationUtils {

    private static final String LOG_TAG = "Validation Utils";

    /**
     * Checks if the email is valid.
     *
     * @param email email address to be validated.
     * @return true if valid, false otherwise
     */
    public static boolean isEmail(String email) {
        return !TextUtils.isEmpty(email)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Checks if the passed in phone number is valid according to global standards.
     *
     * @param phoneNumber the phone number to be validated.
     * @return true if valid, false otherwise
     */
    public static boolean isGlobalPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber)
                && PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber);
    }

    /**
     * Checks if the phone number is a valid indian phone number.
     * <p>
     * Note: This is a very basic validation I added. Any improvements are welcomed.
     * Please see the implementation for more details.
     *
     * @param phoneNumber the phone number to be validated.
     * @return true if valid, false otherwise
     */
    public static boolean isIndianPhoneNumber(String phoneNumber) {
        boolean result = isGlobalPhoneNumber(phoneNumber);

        if (result) {
            if (phoneNumber.length() > 10) {
                result = phoneNumber.length() == 11 && phoneNumber.startsWith("0")
                        || phoneNumber.length() == 13 && phoneNumber.startsWith("+91")
                        || phoneNumber.length() == 14 && phoneNumber.startsWith("0091");
            } else {
                result = phoneNumber.length() == 10 && !phoneNumber.startsWith("0");
            }
        }

        return result;
    }

    /**
     * This method helps to remove all the invalid characters added in the phone number
     * field once the edit text loses it's focus.
     * <p>
     * Note: This action will be overridden if the developer adds another focus change listener
     * after calling this method.
     *
     * @param editText The {@link EditText} on which we need to attach the listener.
     */
    public static void addPhoneNumberValidator(final EditText editText) {
        if (editText != null) {
            if (editText.getInputType() != InputType.TYPE_CLASS_PHONE) {
                try {
                    String idString = editText.getResources().getResourceEntryName(editText.getId());
                    Log.w(LOG_TAG, "The 'inputType' must be set to 'phone' for this EditText : @id/" + idString);
                } catch (Exception ignore) {
                    // do nothing.
                }
            }

            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String phoneNumber = EditTextUtils.getText(editText);
                        if (phoneNumber != null) {
                            phoneNumber = phoneNumber.replaceAll("[ -().*#,;N]", "");
                            EditTextUtils.setText(editText, phoneNumber);
                        }
                    }
                }
            });
        }
    }
}
