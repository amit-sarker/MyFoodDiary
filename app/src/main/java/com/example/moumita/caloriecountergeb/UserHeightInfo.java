package com.example.moumita.caloriecountergeb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class UserHeightInfo extends AppCompatActivity {

    private String [] feetValues = new String[10];
    private String [] inchValues = new String[12];
    NumberPicker mFeetPicker,mInchPicker;
    TextView mHeightText,mFeetText,mInchText;
    ImageView mHeightImg;
    Button mNextPageBtn;
    ToggleButton mFeetvsInch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_height_info);


        for(int i=0;i<10;i++)
        {
            feetValues[i] = String.valueOf(i);
        }
        for(int i=0;i<12;i++)
        {
            inchValues[i] = String.valueOf(i);
        }
        mHeightText = (TextView) findViewById(R.id.height_info_text);
        mFeetText = (TextView) findViewById(R.id.feet_text);
        mInchText = (TextView) findViewById(R.id.inch_text);
        mHeightImg = (ImageView) findViewById(R.id.height_info_img);
        mNextPageBtn = (Button) findViewById(R.id.next_page_btn);

        mFeetPicker = (NumberPicker) findViewById(R.id.num_picker_feet);
        mFeetPicker.setMinValue(0);
        mFeetPicker.setMaxValue(7);
        mFeetPicker.setDisplayedValues(feetValues);

        mInchPicker = (NumberPicker) findViewById(R.id.num_picker_inch);
        mInchPicker.setMinValue(0);
        mInchPicker.setMaxValue(11);
        mInchPicker.setDisplayedValues(inchValues);

        mFeetvsInch = findViewById(R.id.feet_vs_inch);

        mFeetvsInch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mFeetText.setText("Feet");
                    mInchText.setText("Inches");
                } else {
                    mFeetText.setText("Meter");
                    mInchText.setText("Cm");

                    // The toggle is disabled
                }
            }
        });


    }
}
