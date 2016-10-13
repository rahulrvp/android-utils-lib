package io.github.rahulrvp.androidutilslib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.github.rahulrvp.android_utils.EditTextUtils;
import io.github.rahulrvp.android_utils.TextViewUtils;
import io.github.rahulrvp.android_utils.ValidationUtils;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.sample_textview);
        String text = TextViewUtils.getText(textView);
        Log.wtf("LOG", text);
        TextViewUtils.setError(textView, "New text!");

        mEditText = (EditText) findViewById(R.id.edit_text);
        ValidationUtils.addPhoneNumberValidator(mEditText);
    }

    public void onTestClicked(View view) {
        String phoneNumber = EditTextUtils.getText(mEditText);
        boolean status = ValidationUtils.isGlobalPhoneNumber(phoneNumber);

        Log.d("Sample App", phoneNumber + " " + status);
    }
}
