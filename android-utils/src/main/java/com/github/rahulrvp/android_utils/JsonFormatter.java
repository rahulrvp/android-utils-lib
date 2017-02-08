package com.github.rahulrvp.android_utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This class helps you format a raw JSON input and give you a pretty formatted output in
 * either String or HTML format. The HTML format supports color. User can customize the
 * colors used with the help of {@link Builder} class.
 * <p>
 * The class supports inputs as String, JSONObject and JSONArray.
 *
 * @author Rahul Raveendran V P
 *         Created on 23/1/17 @ 5:27 PM
 *         https://github.com/rahulrvp
 */

public class JsonFormatter {

    @SuppressWarnings("WeakerAccess")
    public static final int STRING = 900;
    @SuppressWarnings("WeakerAccess")
    public static final int HTML = 901;

    private String colorBraces;
    private String colorSqBracket;
    private String colorComma;
    private String colorKey;
    private String colorString;
    private String colorNumber;
    private String colorBoolean;
    private String colorNull;
    private int outputFormat;

    private JsonFormatter() {
        colorBraces = "#729fcf";
        colorSqBracket = "#a4074f";
        colorComma = "#370007";
        colorKey = "#2d4a8e";
        colorString = "#53a06b";
        colorNumber = "#af83a8";
        colorBoolean = "#ddab1f";
        colorNull = "#c0c3ca";
        outputFormat = STRING;
    }

    /**
     * Formats the raw JSON string input. The output will be either in String or HTML format.
     *
     * @param jsonString input JSON String
     * @return if input is valid, returns formatted output JSON either in String or HTML format; else empty string.
     */
    public String format(String jsonString) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            formatJson(stringBuilder, 0, new JSONObject(jsonString), false);
        } catch (Exception e) {
            try {
                formatJson(stringBuilder, 0, new JSONArray(jsonString), false);
            } catch (Exception err) {
                Log.e("JsonFormatter", "Input is not a valid JSON. Value: " + String.valueOf(jsonString));
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Formats the JSONObject instance input. The output will be either in String or HTML format.
     *
     * @param jsonObject input JSONObject instance.
     * @return if input is valid, returns formatted output JSON either in String or HTML format; else empty string.
     */
    public String format(JSONObject jsonObject) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            formatJson(stringBuilder, 0, jsonObject, false);
        } catch (JSONException e) {
            Log.e("JsonFormatter", e.getMessage());
        }

        return stringBuilder.toString();
    }

    /**
     * Formats the JSONArray instance input. The output will be either in String or HTML format.
     *
     * @param jsonArray input JSONArray instance.
     * @return if input is valid, returns formatted output JSON either in String or HTML format; else empty string.
     */
    public String format(JSONArray jsonArray) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            formatJson(stringBuilder, 0, jsonArray, false);
        } catch (JSONException e) {
            Log.e("JsonFormatter", e.getMessage());
        }

        return stringBuilder.toString();
    }

    private void formatJson(StringBuilder builder, int level, JSONObject jsonObject, boolean isArrayElement) throws JSONException {
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

    private void formatJson(StringBuilder builder, int level, JSONArray jsonArray, boolean isArrayElement) throws JSONException {
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
        } else if ("null".equals(element.toString())) {
            addNullElement(builder);
        } else if (element instanceof Boolean) {
            addNonQuotedElement(builder, element, colorBoolean);
        } else {
            addNonQuotedElement(builder, element, colorNumber);
        }
    }

    private void addNullElement(StringBuilder builder) {
        if (outputFormat == HTML) {
            builder
                    .append("<i>")
                    .append(fontTagOpen(colorNull))
                    .append("null")
                    .append(fontTagClose())
                    .append("</i>");
        } else {
            builder.append("null");
        }
    }

    private void addQuotedElement(StringBuilder builder, Object value, String colorHash) {
        if (outputFormat == HTML) {
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
        if (outputFormat == HTML) {
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
        String tabString;

        switch (outputFormat) {
            case HTML:
                tabString = "&emsp;";
                break;

            case STRING:
            default:
                tabString = "\t";

        }

        return tabString;
    }

    private String getNewlineString() {
        String newlineString;

        switch (outputFormat) {
            case HTML:
                newlineString = "<br>";
                break;

            case STRING:
            default:
                newlineString = "\n";
        }

        return newlineString;
    }

    /**
     * This Builder class is responsible for creating {@link JsonFormatter} instance with
     * a set of customizations including colors.
     */
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

        public Builder setValueColorNull(String colorHashCode) {
            formatter.colorNull = colorHashCode;

            return this;
        }

        public Builder setOutputFormat(int outputFormat) {
            formatter.outputFormat = outputFormat;

            return this;
        }

        public JsonFormatter build() {
            return formatter;
        }
    }
}
