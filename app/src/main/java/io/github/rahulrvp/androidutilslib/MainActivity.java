package io.github.rahulrvp.androidutilslib;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahulrvp.android_utils.DateTimeUtils;
import com.github.rahulrvp.android_utils.EditTextUtils;
import com.github.rahulrvp.android_utils.ValidationUtils;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText phoneInputField2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Applying custom font.
         */
        TextView textView = (TextView) findViewById(R.id.font_text_view);
        new RobotoRegular().apply(textView, Typeface.ITALIC);

        /**
         * Adding automatic phone number validator. The validation takes place
         * when we leave the edit text. (on focus change)
         */
        EditText phoneInputField1 = (EditText) findViewById(R.id.phone_field_1);
        ValidationUtils.addPhoneNumberValidator(phoneInputField1);

        /**
         * EditText for taking phone number for validating later.
         */
        phoneInputField2 = (EditText) findViewById(R.id.phone_field_2);

        /**
         * Time ago string demo.
         */
        Calendar now = Calendar.getInstance();
        now.set(2012, 0, 1);

        Calendar yesterday = Calendar.getInstance();
        yesterday.set(2016, 10, 8);
        yesterday.set(Calendar.HOUR_OF_DAY, 10);
        yesterday.set(Calendar.MINUTE, 0);

        Log.d("LOG", DateTimeUtils.getTimeAgoString(yesterday.getTimeInMillis(), null, false));
    }

    public void onValidateGlobalNumberClick(View view) {
        String phoneNumber = EditTextUtils.getText(phoneInputField2);
        boolean isGlobal = ValidationUtils.isGlobalPhoneNumber(phoneNumber);
        if (!isGlobal) {
            EditTextUtils.setError(phoneInputField2, "Not a valid global phone number.");
        } else {
            Toast.makeText(this, "Valid Global Phone Number.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onValidateIndianNumberClick(View view) {
        String phoneNumber = EditTextUtils.getText(phoneInputField2);
        boolean isGlobal = ValidationUtils.isIndianPhoneNumber(phoneNumber);
        if (!isGlobal) {
            EditTextUtils.setError(phoneInputField2, "Not a valid Indian phone number.");
        } else {
            Toast.makeText(this, "Valid Indian Phone Number.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.demo_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.json_formatter_demo) {
            startActivity(new Intent(this, JsonActivity.class));
        }

        return true;
    }
}
