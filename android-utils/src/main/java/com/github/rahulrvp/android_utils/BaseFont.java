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
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

/**
 * @author Rahul Raveendran V P
 *         Created on 26/10/16 @ 1:48 PM
 *         https://github.com/rahulrvp
 */


public abstract class BaseFont {

    private static final int TYPE_ASSET = 0;
    private static final int TYPE_FILE = 1;
    private static final String LOG_TAG = "BaseFont";
    private Typeface mTypeface;

    protected BaseFont() {
    }

    public void apply(TextView textView, Integer style) {
        if (textView != null && textView.getContext() != null) {
            Typeface typeface = getFont(textView.getContext());
            if (typeface != null) {
                if (style != null) {
                    textView.setTypeface(typeface, style);
                } else {
                    textView.setTypeface(typeface);
                }
            }
        }
    }

    public void apply(TextView textView) {
        if (textView != null) {
            apply(textView, null);
        }
    }

    public void apply(View view) {
        if (view != null) {
            apply(view, null);
        }
    }

    public void apply(View view, Integer style) {
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int count = viewGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    View childView = viewGroup.getChildAt(i);

                    apply(childView, style);
                }
            } else if (view instanceof TextView) {
                apply((TextView) view, style);
            }
        }
    }

    private Typeface getFont(Context context) {
        if (mTypeface == null) {
            switch (getType()) {
                case TYPE_ASSET:
                    mTypeface = fromAssets(context);
                    break;

                case TYPE_FILE:
                    mTypeface = fromFile();
                    break;

                default:
                    Log.e(LOG_TAG, "Invalid type found: " + getType() + ". Type should be either BaseFont.TYPE_ASSET or BaseFont.TYPE_FILE");
            }
        }

        return mTypeface;
    }

    private Typeface fromAssets(Context context) {
        Typeface typeface = null;

        if (context != null) {
            AssetManager assetManager = context.getAssets();
            if (assetManager != null) {
                String path = getPath();
                if (!TextUtils.isEmpty(path)) {
                    typeface = Typeface.createFromAsset(assetManager, path);
                } else {
                    Log.e(LOG_TAG, "No path found.");
                }
            }
        } else {
            Log.e(LOG_TAG, "AssetManager is null");
        }

        return typeface;
    }

    private Typeface fromFile() {
        Typeface typeface = null;

        String path = getPath();
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                typeface = Typeface.createFromFile(getPath());
            } else {
                Log.e(LOG_TAG, "File not found at: " + path);
            }
        } else {
            Log.e(LOG_TAG, "No path found.");
        }

        return typeface;
    }

    protected int getType() {
        return TYPE_ASSET;
    }

    protected abstract String getPath();
}
