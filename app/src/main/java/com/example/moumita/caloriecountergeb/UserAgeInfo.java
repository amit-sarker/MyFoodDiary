package com.example.moumita.caloriecountergeb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserAgeInfo extends AppCompatActivity {

    private DatePicker simpleDatePicker;
    private Button submit;

    String dateStr = "04/05/2010";

    SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
    Date dateObj = curFormater.parse(dateStr);
    SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");

    String newDateStr = postFormater.format(dateObj);

    public UserAgeInfo() throws ParseException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_age_info);

        simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        submit = (Button) findViewById(R.id.submitButton);
        // perform click event on submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the values for day of month , month and year from a date picker
                String day = "Day = " + simpleDatePicker.getDayOfMonth();
                String month = "Month = " + (simpleDatePicker.getMonth() + 1);
                String year = "Year = " + (simpleDatePicker.getYear());

                final int dd = simpleDatePicker.getDayOfMonth();
                final int mm = simpleDatePicker.getMonth();
                final int yy = simpleDatePicker.getYear();


                // display the values by using a toast
                Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();
            }
        });
    }
}
