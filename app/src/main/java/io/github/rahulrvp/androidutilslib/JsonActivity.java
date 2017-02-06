package io.github.rahulrvp.androidutilslib;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.github.rahulrvp.android_utils.JsonFormatter;
import com.github.rahulrvp.android_utils.TextViewUtils;

public class JsonActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        mTextView = (TextView) findViewById(R.id.json_output_text);
    }

    public void onParseColoredClicked(View view) {
        new JsonTask(this, new JsonTask.Callback() {
            @Override
            public void onReceive(String json) {
                String formatted =
                        new JsonFormatter
                                .Builder()
                                .setOutputFormat(JsonFormatter.OutputFormat.Html)
                                .build()
                                .format(json);

                TextViewUtils.setText(mTextView, Html.fromHtml(formatted));
            }
        }).execute();
    }

    public void onParseClicked(View view) {
        new JsonTask(this, new JsonTask.Callback() {
            @Override
            public void onReceive(String json) {
                String formatted = new JsonFormatter.Builder().build().format(json);
                TextViewUtils.setText(mTextView, formatted);
            }
        }).execute();
    }

    private static class JsonTask extends AsyncTask<Void, Void, String> {
        Callback callback;
        Context context;

        public JsonTask(Context context, Callback callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return TestUtils.readTextFileFromAssets(this.context, "sample_json.json");
        }

        @Override
        protected void onPostExecute(String json) {
            if (this.callback != null) {
                this.callback.onReceive(json);
            }
        }

        interface Callback {
            void onReceive(String json);
        }
    }
}
