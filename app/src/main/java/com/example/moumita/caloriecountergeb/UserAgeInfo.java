package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserAgeInfo extends AppCompatActivity {

    private DatePicker simpleDatePicker;
    private Button submit;


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
                final int mm = simpleDatePicker.getMonth() + 1;
                final int yy = simpleDatePicker.getYear();



                // display the values by using a toast
                Toast.makeText(getApplicationContext(), dd + "\n" + mm + "\n" + yy, Toast.LENGTH_SHORT).show();

                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                final int age = getAge(yy,mm,dd,date);
                Toast.makeText(getApplicationContext(),age + " hhh", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UserAgeInfo.this, UserHeightInfo.class);
                //intent.putExtra("fromGender", (Serializable) User);
                startActivity(intent);

            }
        });
    }
    public int getAge(int DOByear, int DOBmonth, int DOBday, String todaydate) {

        System.out.println("mmmmmmmmmmmmmmmm  " + DOBday + " " + DOBmonth + " " + DOByear);
        int todayDay=0, currentMonth=0, currentYear=0;
        for(int i=0;i<4;i++)
        {
            if(i>0) currentYear *= 10;
            currentYear += todaydate.charAt(i) - '0';
        }
        for(int i=5;i<7;i++)
        {
            if(i==6) currentMonth *= 10;
            currentMonth += todaydate.charAt(i) - '0';
        }
        for(int i=8;i<10;i++)
        {
            if(i==9) todayDay *= 10;
            todayDay += todaydate.charAt(i) - '0';
        }

        int age;

        //final Calendar calenderToday = Calendar.getInstance();
        //int currentYear = calenderToday.get(Calendar.YEAR);
        //int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        //int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);


        age = currentYear - DOByear;
        System.out.println("priiiiiiiiiiiiiiiiiiiiiiiint "+ currentMonth + " " + currentYear + " " + todayDay + " mmmmmmmmmmmmmmmm  " + DOBday + " " + DOBmonth + " " + DOByear + " " + age);

        if(DOBmonth > currentMonth){
            --age;
        }
        else if(DOBmonth == currentMonth){
            if(DOBday > todayDay){
                --age;
            }
        }
        return age;
    }

}
