package com.github.rahulrvp.android_utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * @author Rahul Raveendran V P
 *         Created on 23/1/17 @ 5:27 PM
 *         https://github.com/rahulrvp
 */

public class JsonFormatter {

    private String colorBraces;
    private String colorSqBracket;
    private String colorComma;
    private String colorKey;
    private String colorString;
    private String colorNumber;
    private String colorBoolean;
    private OutputFormat outputFormat;

    private JsonFormatter() {
        colorBraces = "#729fcf";
        colorSqBracket = "#a4074f";
        colorComma = "#370007";
        colorKey = "#2d4a8e";
        colorString = "#53a06b";
        colorNumber = "#af83a8";
        colorBoolean = "#ddab1f";
        outputFormat = OutputFormat.String;
    }

    public String format(String text) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            formatJson(stringBuilder, 0, new JSONObject(text), false);
        } catch (Exception e) {
            try {
                formatJson(stringBuilder, 0, new JSONArray(text), false);
            } catch (Exception err) {
                Log.e("JsonFormatter", e.getMessage());
            }
        }

        return stringBuilder.toString();
    }

    public void formatJson(StringBuilder builder, int level, JSONObject jsonObject, boolean isArrayElement) throws JSONException {
        if (jsonObject != null && builder != null) {
            String levelZeroTab = tabs(level);
            String levelOneTab = tabs(level + 1);
            String newLine = getNewlineString();

            if (isArrayElement) {
                builder.append(levelZeroTab);
            }

            addNonQuotedElement(builder, "{", colorBraces);

            if (jsonObject.length() > 0) {
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    builder.append(getNewlineString());

                    String key = keys.next();
                    Object value = jsonObject.get(key);

                    builder.append(levelOneTab);

                    addQuotedElement(builder, key, colorKey);

                    builder.append(" : ");

                    if (value instanceof JSONArray) {
                        formatJson(builder, level + 1, (JSONArray) value, false);
                    } else if (value instanceof JSONObject) {
                        formatJson(builder, level + 1, (JSONObject) value, false);
                    } else {
                        addValueElement(builder, value);
                    }

                    if (keys.hasNext()) {
                        addNonQuotedElement(builder, ",", colorComma);
                    }
                }

                builder
                        .append(newLine)
                        .append(levelZeroTab);
            }

            addNonQuotedElement(builder, "}", colorBraces);
        }
    }

    public void formatJson(StringBuilder builder, int level, JSONArray jsonArray, boolean isArrayElement) throws JSONException {
        if (jsonArray != null && builder != null) {
            int len = jsonArray.length();
            String levelZeroTab = tabs(level);
            String levelOneTab = tabs(level + 1);
            String newLine = getNewlineString();

            if (isArrayElement) {
                builder.append(levelZeroTab);
            }

            addNonQuotedElement(builder, "[", colorSqBracket);

            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    builder.append(newLine);

                    Object element = jsonArray.get(i);
                    if (element instanceof JSONArray) {
                        formatJson(builder, level + 1, (JSONArray) element, true);
                    } else if (element instanceof JSONObject) {
                        formatJson(builder, level + 1, (JSONObject) element, true);
                    } else {
                        builder.append(levelOneTab);
                        addValueElement(builder, element);
                    }

                    if (len - i != 1) {
                        addNonQuotedElement(builder, ",", colorComma);
                    }
                }

                builder
                        .append(newLine)
                        .append(levelZeroTab);
            }

            addNonQuotedElement(builder, "]", colorSqBracket);
        }
    }

    private void addValueElement(StringBuilder builder, Object element) {
        if (element instanceof String) {
            addQuotedElement(builder, element, colorString);
        } else if (element instanceof Boolean) {
            addNonQuotedElement(builder, element, colorBoolean);
        } else {
            addNonQuotedElement(builder, element, colorNumber);
        }
    }

    private void addQuotedElement(StringBuilder builder, Object value, String colorHash) {
        if (outputFormat != null && outputFormat == OutputFormat.Html) {
            builder
                    .append(fontTagOpen(colorHash))
                    .append("\"")
                    .append(value)
                    .append("\"")
                    .append(fontTagClose());
        } else {
            builder.
                    append("\"")
                    .append(value)
                    .append("\"");
        }
    }

    private void addNonQuotedElement(StringBuilder builder, Object value, String colorHash) {
        if (outputFormat != null && outputFormat == OutputFormat.Html) {
            builder
                    .append(fontTagOpen(colorHash))
                    .append(value)
                    .append(fontTagClose());
        } else {
            builder.append(value);
        }
    }

    private String fontTagOpen(String colorHash) {
        if (TextUtils.isEmpty(colorHash)) {
            colorHash = "#000";
        }

        return "<font color=\"" + colorHash + "\">";
    }

    private String fontTagClose() {
        return "</font>";
    }

    private String tabs(int level) {
        String tabs = "";
        String tabString = getTabString();
        for (int i = 0; i < level; i++) {
            tabs += tabString;
        }
        return tabs;
    }

    private String getTabString() {
        String tabString = "";

        if (outputFormat != null) {
            switch (outputFormat) {
                case String:
                    tabString = "\t";
                    break;

                case Html:
                    tabString = "&emsp;";
                    break;
            }
        }

        return tabString;
    }

    private String getNewlineString() {
        String newlineString = "";

        if (outputFormat != null) {
            switch (outputFormat) {
                case String:
                    newlineString = "\n";
                    break;

                case Html:
                    newlineString = "<br>";
                    break;
            }
        }

        return newlineString;
    }

    public enum OutputFormat {
        String,
        Html
    }

    public static class Builder {
        final JsonFormatter formatter;

        public Builder() {
            formatter = new JsonFormatter();
        }

        public Builder setObjectBracketColor(String colorHashCode) {
            formatter.colorBraces = colorHashCode;

            return this;
        }

        public Builder setArrayBracketColor(String colorHashCode) {
            formatter.colorSqBracket = colorHashCode;

            return this;
        }

        public Builder setCommaColor(String colorHashCode) {
            formatter.colorComma = colorHashCode;

            return this;
        }

        public Builder setKeyColor(String colorHashCode) {
            formatter.colorKey = colorHashCode;

            return this;
        }

        public Builder setValueColorString(String colorHashCode) {
            formatter.colorString = colorHashCode;

            return this;
        }

        public Builder setValueColorNumber(String colorHashCode) {
            formatter.colorNumber = colorHashCode;

            return this;
        }

        public Builder setValueColorBoolean(String colorHashCode) {
            formatter.colorBoolean = colorHashCode;

            return this;
        }

        public Builder setOutputFormat(OutputFormat outputFormat) {
            formatter.outputFormat = outputFormat;

            return this;
        }

        public JsonFormatter build() {
            return formatter;
        }
    }
}
