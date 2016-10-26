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

package io.github.rahulrvp.android_utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

/**
 * Helper class to create and set fonts in {@link TextView} sub classes.
 * <p>
 * Override the {@link #getPath()} method in derived class to provide path
 * to the font file.
 * <p>
 * The default source is /assets folder. to change this you may override
 * the {@link #getSource()} method and return {@link #FILES}. This will
 * change the source to files. Make sure that you provide valid path.
 * <p>
 * It is recommended to make the derived class to singleton for better performance.
 *
 * @author Rahul Raveendran V P
 *         Created on 4/10/16 @ 12:28 PM
 *         https://github.com/rahulrvp
 */


public abstract class FontUtils {

    public static final int FILES = 1;
    public static final int ASSETS = 2;

    private static final String LOG_TAG = "FontUtils";

    private static Typeface sTypeface;

    private AssetManager mAssetManager;

    protected FontUtils(AssetManager assetManager) {
        mAssetManager = assetManager;

        prepareAsset();
    }

    /**
     * Apply font to all {@link TextView} objects inside a {@link ViewGroup}
     *
     * @param viewGroup parent viewGroup to look for textViews
     */
    public void applyFont(ViewGroup viewGroup) {
        if (viewGroup != null && sTypeface != null) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = viewGroup.getChildAt(i);
                if (childView != null && childView instanceof TextView) {
                    ((TextView) childView).setTypeface(sTypeface);
                }
            }
        }
    }

    /**
     * Applies the loaded font to the given {@link TextView}.
     *
     * @param textView textView to set font.
     */
    public void applyFont(TextView textView) {
        if (textView != null && sTypeface != null) {
            textView.setTypeface(sTypeface);
        }
    }

    /**
     * Applies the loaded font to the given {@link TextView}.
     *
     * @param textView {@link TextView} to set font.
     * @param style    Style of font. Use one among
     *                 {@link Typeface#BOLD}, {@link Typeface#BOLD_ITALIC},
     *                 {@link Typeface#ITALIC}, {@link Typeface#NORMAL}
     */
    public void applyFont(TextView textView, int style) {
        if (textView != null && sTypeface != null) {
            textView.setTypeface(sTypeface, style);
        }
    }

    private void prepareAsset() {
        int source = getSource();

        switch (source) {
            case ASSETS:
                sTypeface = fromAssets();
                break;

            case FILES:
                sTypeface = fromFile();
                break;

            default:
                sTypeface = null;
        }
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

    private Typeface fromAssets() {
        Typeface typeface = null;

        if (mAssetManager != null) {
            String path = getPath();
            if (!TextUtils.isEmpty(path)) {
                typeface = Typeface.createFromAsset(mAssetManager, path);
            } else {
                Log.e(LOG_TAG, "No path found.");
            }
        } else {
            Log.e(LOG_TAG, "AssetManager is null");
        }

        return typeface;
    }

    protected int getSource() {
        return ASSETS;
    }

    protected abstract String getPath();
}
