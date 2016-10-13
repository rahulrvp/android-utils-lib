package io.github.rahulrvp.androidutilslib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.github.rahulrvp.android_utils.ValidationUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTestClicked(View view) {
        ValidationUtils.isIndianPhoneNumber("******226");
    }
}
