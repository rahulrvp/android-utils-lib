package io.github.rahulrvp.android_utils;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

/**
 * @author Rahul Raveendran V P
 *         Created on 13/10/16 @ 7:05 PM
 *         https://github.com/rahulrvp
 */


public class TextViewUtils {

    private static final String LOG_TAG = "TextView Utils";

    /**
     * Read text from a given {@link TextView} and returns the value as string.
     *
     * @param textView {@link TextView} from which the text should be read
     * @return text in {@link String} format if {@link TextView} is valid, else 'null'
     */
    public static String getText(TextView textView) {
        String result = null;

        if (textView != null) {
            CharSequence charSequence = textView.getText();
            if (charSequence != null) {
                result = charSequence.toString();
            }
        }

        return result;
    }

    /**
     * Sets text in given {@link TextView}
     *
     * @param textView Valid {@link TextView} object
     * @param resId    Resource id of the String to be shown in {@link TextView}
     */
    public static void setText(TextView textView, int resId) {
        setText(textView, resId, new Object[0]);
    }

    /**
     * Sets text in given {@link TextView}
     *
     * @param textView   Valid {@link TextView} object
     * @param resId      Resource id of the String to be shown in {@link TextView}
     * @param formatArgs Format Arguments
     */
    public static void setText(TextView textView, int resId, Object... formatArgs) {
        if (textView != null) {
            Context context = textView.getContext();
            setText(textView, getString(context, resId, formatArgs));
        }
    }

    /**
     * Sets text in given {@link TextView}
     *
     * @param textView Valid {@link TextView} object
     * @param text     Text to be shown in the given {@link TextView}
     */
    public static void setText(TextView textView, String text) {
        if (textView != null) {
            textView.setText(text);
        }
    }

    /**
     * Sets error text in given {@link TextView}
     *
     * @param textView Valid {@link TextView} object
     * @param resId    Resource id of the error text to be shown in {@link TextView}
     */
    public static void setError(TextView textView, int resId) {
        setError(textView, resId, new Object[0]);
    }

    /**
     * Sets error text in given {@link TextView}
     *
     * @param textView   Valid {@link TextView} object
     * @param resId      Resource id of the error text to be shown in {@link TextView}
     * @param formatArgs Format Arguments
     */
    public static void setError(TextView textView, int resId, Object... formatArgs) {
        if (textView != null) {
            Context context = textView.getContext();
            setError(textView, getString(context, resId, formatArgs));
        }
    }

    /**
     * Sets error text in given {@link TextView}
     *
     * @param textView Valid {@link TextView} object
     * @param error    Text to be shown as error in the given {@link TextView}
     */
    public static void setError(TextView textView, String error) {
        if (textView != null) {
            if (!textView.isFocusable() && !textView.isFocusableInTouchMode()) {
                try {
                    String idString = textView.getResources().getResourceEntryName(textView.getId());
                    Log.i(LOG_TAG, "Add following attributes to your TextView (@id/" + idString + ") to see the error popup on touch." +
                            "\nandroid:focusable=\"true\"" +
                            "\nandroid:focusableInTouchMode=\"true\"");
                } catch (Exception ignore) {
                    // do nothing
                }
            }

            textView.setError(error);
        }
    }

    /**
     * Helper method to read string from resources
     *
     * @param context    Valid {@link Context} object.
     * @param resId      Resource id of the string to be read.
     * @param formatArgs Format Arguments
     * @return The string with given resID.
     */
    private static String getString(Context context, int resId, Object... formatArgs) {
        String result = null;

        if (context != null) {
            if (formatArgs == null || formatArgs.length > 0) {
                result = context.getString(resId, formatArgs);
            } else {
                result = context.getString(resId);
            }
        }

        return result;
    }
}