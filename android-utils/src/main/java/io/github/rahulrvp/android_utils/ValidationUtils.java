package io.github.rahulrvp.android_utils;

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
                result = phoneNumber.length() == 13 && (phoneNumber.startsWith("+91")
                        || phoneNumber.length() == 11 && phoneNumber.startsWith("0"));
            } else if (phoneNumber.length() == 10) {
                result = !phoneNumber.startsWith("0");
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
                String idString = editText.getResources().getResourceEntryName(editText.getId());
                Log.w(LOG_TAG, "The 'inputType' must be set to 'phone' for this EditText : @id/" + idString);
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
