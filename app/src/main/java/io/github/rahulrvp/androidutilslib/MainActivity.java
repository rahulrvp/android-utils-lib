package io.github.rahulrvp.androidutilslib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.rahulrvp.android_utils.DateTimeUtils;
import com.github.rahulrvp.android_utils.EditTextUtils;
import com.github.rahulrvp.android_utils.TextViewUtils;
import com.github.rahulrvp.android_utils.ValidationUtils;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.sample_textview);
        String text = TextViewUtils.getText(textView);
        TextViewUtils.setError(textView, "New text! " + text);

        new RobotoRegular().apply(textView);

        mEditText = (EditText) findViewById(R.id.edit_text);
        ValidationUtils.addPhoneNumberValidator(mEditText);

        Log.d("LOG", DateTimeUtils.getTimeAgoString(624652200000L));
    }

    public void onTestClicked(View view) {
        String phoneNumber = EditTextUtils.getText(mEditText);
        boolean status = ValidationUtils.isGlobalPhoneNumber(phoneNumber);

        Log.d("Sample App", phoneNumber + " " + status);
    }
}
