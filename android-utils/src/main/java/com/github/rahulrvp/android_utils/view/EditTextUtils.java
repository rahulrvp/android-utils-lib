/**
 *
 * Copyright 2016 Rahul Raveendran
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.github.rahulrvp.android_utils.view;

import android.content.Context;
import android.text.Editable;
import android.widget.EditText;

/**
 * Abstracts the commonly used methods on an {@link EditText}
 *
 * @author Rahul Raveendran V P
 *         Created on 3/10/16 @ 8:13 PM
 *         https://github.com/rahulrvp
 */


public class EditTextUtils {

    /**
     * Read text from a given {@link EditText} and returns the value as string.
     *
     * @param editText {@link EditText} from which the text should be read
     * @return text in {@link String} format if {@link EditText} is valid, else 'null'
     */
    public static String getText(EditText editText) {
        String result = null;

        if (editText != null) {
            Editable editable = editText.getText();
            if (editable != null) {
                result = editable.toString();
            }
        }

        return result;
    }

    /**
     * Sets text in given {@link EditText}
     *
     * @param editText Valid {@link EditText} object
     * @param resId    Resource id of the String to be shown in {@link EditText}
     */
    public static void setText(EditText editText, int resId) {
        setText(editText, resId, new Object[0]);
    }

    /**
     * Sets text in given {@link EditText}
     *
     * @param editText   Valid {@link EditText} object
     * @param resId      Resource id of the String to be shown in {@link EditText}
     * @param formatArgs Format Arguments
     */
    public static void setText(EditText editText, int resId, Object... formatArgs) {
        if (editText != null) {
            Context context = editText.getContext();
            setText(editText, getString(context, resId, formatArgs));
        }
    }

    /**
     * Sets text in given {@link EditText}
     *
     * @param editText Valid {@link EditText} object
     * @param text     Text to be shown in the given {@link EditText}
     */
    public static void setText(EditText editText, String text) {
        if (editText != null) {
            editText.setText(text);
        }
    }

    /**
     * Sets error text in given {@link EditText}
     *
     * @param editText Valid {@link EditText} object
     * @param resId    Resource id of the error text to be shown in {@link EditText}
     */
    public static void setError(EditText editText, int resId) {
        setError(editText, resId, new Object[0]);
    }

    /**
     * Sets error text in given {@link EditText}
     *
     * @param editText   Valid {@link EditText} object
     * @param resId      Resource id of the error text to be shown in {@link EditText}
     * @param formatArgs Format Arguments
     */
    public static void setError(EditText editText, int resId, Object... formatArgs) {
        if (editText != null) {
            Context context = editText.getContext();
            setError(editText, getString(context, resId, formatArgs));
        }
    }

    /**
     * Sets error text in given {@link EditText}
     *
     * @param editText Valid {@link EditText} object
     * @param error    Text to be shown as error in the given {@link EditText}
     */
    public static void setError(EditText editText, String error) {
        if (editText != null) {
            editText.setError(error);
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
