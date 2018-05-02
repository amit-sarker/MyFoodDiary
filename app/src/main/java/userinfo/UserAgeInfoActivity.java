package userinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.moumita.caloriecountergeb.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserAgeInfoActivity extends AppCompatActivity {

    private boolean isFemale, isfeet=true;
    private double height;

    private String[] dayValues = new String[32];
    private String[] monthValues = {"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    private String[] yearValues = new String[100];
    //private DatePicker simpleDatePicker;
    private Button submit;
    private NumberPicker mMonthPicker, mDayPicker, mYearPicker;


    public UserAgeInfoActivity() throws ParseException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_age_info);


        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Bundle bundle = getIntent().getExtras();
        isFemale = bundle.getBoolean("isfemale");
        height = bundle.getDouble("height");

        for (int i = 0; i < 32; i++) {
            dayValues[i] = String.valueOf(i+1);
        }

        for (int i = 0; i < 100; i++) {
            yearValues[i] = String.valueOf(i+1920);
        }

        mDayPicker = findViewById(R.id.num_picker_date);
        mMonthPicker = findViewById(R.id.num_picker_month);
        mYearPicker = findViewById(R.id.num_picker_year);

        mDayPicker.setMinValue(1);
        mDayPicker.setMaxValue(31);
        mDayPicker.setDisplayedValues(dayValues);

        mMonthPicker.setMinValue(1);
        mMonthPicker.setMaxValue(12);
        mMonthPicker.setDisplayedValues(monthValues);

        mYearPicker.setMinValue(0);
        mYearPicker.setMaxValue(99);
        mYearPicker.setDisplayedValues(yearValues);

        //simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        submit = (Button) findViewById(R.id.submitButton);

        mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(mMonthPicker.getValue()==2) {
                    if((mYearPicker.getValue()%4==0 && mYearPicker.getValue()%100!=0) ||
                            mYearPicker.getValue()%400==0 ) {
                        mDayPicker.setMinValue(1);
                        mDayPicker.setMaxValue(29);
                    }
                    else {
                        mDayPicker.setMinValue(1);
                        mDayPicker.setMaxValue(28);
                    }

                }
                else if(mMonthPicker.getValue()==9 || mMonthPicker.getValue()==4 ||
                        mMonthPicker.getValue()==6 || mMonthPicker.getValue()==11) {
                    mDayPicker.setMinValue(1);
                    mDayPicker.setMaxValue(30);
                }
                else {
                    mDayPicker.setMinValue(1);
                    mDayPicker.setMaxValue(31);
                }

                mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if(mMonthPicker.getValue()==2) {
                            if((mYearPicker.getValue()%4==0 && mYearPicker.getValue()%100!=0) ||
                                    mYearPicker.getValue()%400==0 ) {
                                mDayPicker.setMinValue(1);
                                mDayPicker.setMaxValue(29);
                            }
                            else {
                                mDayPicker.setMinValue(1);
                                mDayPicker.setMaxValue(28);
                            }

                        }
                        else if(mMonthPicker.getValue()==9 || mMonthPicker.getValue()==4 ||
                                mMonthPicker.getValue()==6 || mMonthPicker.getValue()==11) {
                            mDayPicker.setMinValue(1);
                            mDayPicker.setMaxValue(30);
                        }
                        else {
                            mDayPicker.setMinValue(1);
                            mDayPicker.setMaxValue(31);
                        }
                    }
                });

            }
        });

        mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if(mMonthPicker.getValue()==2) {
                            if((mYearPicker.getValue()%4==0 && mYearPicker.getValue()%100!=0) ||
                                    mYearPicker.getValue()%400==0 ) {
                                mDayPicker.setMinValue(1);
                                mDayPicker.setMaxValue(29);
                            }
                            else {
                                mDayPicker.setMinValue(1);
                                mDayPicker.setMaxValue(28);
                            }

                        }
                        else if(mMonthPicker.getValue()==9 || mMonthPicker.getValue()==4 ||
                                mMonthPicker.getValue()==6 || mMonthPicker.getValue()==11) {
                            mDayPicker.setMinValue(1);
                            mDayPicker.setMaxValue(30);
                        }
                        else {
                            mDayPicker.setMinValue(1);
                            mDayPicker.setMaxValue(31);
                        }
                    }
                });

            }
        });


        // perform click event on submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // get the values for day of month , month and year from a date picker
               // String day = "Day = " + simpleDatePicker.getDayOfMonth();
               // String month = "Month = " + (simpleDatePicker.getMonth() + 1);
               // String year = "Year = " + (simpleDatePicker.getYear());

                //final int dd = simpleDatePicker.getDayOfMonth();
                //final int mm = simpleDatePicker.getMonth() + 1;
                //final int yy = simpleDatePicker.getYear();


                final int dd = mDayPicker.getValue();
                final int mm = mMonthPicker.getValue();
                final int yy = mYearPicker.getValue() + 1920;


                // display the values by using a toast
                Toast.makeText(getApplicationContext(), dd + "\n" + mm + "\n" + yy, Toast.LENGTH_SHORT).show();

                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                final int age = getAge(yy,mm,dd,date);
                Toast.makeText(getApplicationContext(),age + " hhh", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UserAgeInfoActivity.this, UserWeightInfoActivity.class);
                intent.putExtra("isfemale", isFemale);
                intent.putExtra("height", height);
                intent.putExtra("age", age);
                startActivity(intent);
                finish();

            }
        });
    }
    public int getAge(int DOByear, int DOBmonth, int DOBday, String todaydate) {

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
        age = currentYear - DOByear;

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
