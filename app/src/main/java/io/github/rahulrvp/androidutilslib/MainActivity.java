package io.github.rahulrvp.androidutilslib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import io.github.rahulrvp.android_utils.EditTextUtils;
import io.github.rahulrvp.android_utils.ValidationUtils;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.edit_text);
        ValidationUtils.addPhoneNumberValidator(mEditText);
    }

    public void onTestClicked(View view) {
        String phoneNumber = EditTextUtils.getText(mEditText);
        boolean status = ValidationUtils.isGlobalPhoneNumber(phoneNumber);

        Log.d("Sample App", phoneNumber + " " + status);
    }
}
