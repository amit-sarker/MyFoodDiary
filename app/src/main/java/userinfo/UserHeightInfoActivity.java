package userinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.moumita.caloriecountergeb.R;

public class UserHeightInfoActivity extends AppCompatActivity {

    private boolean isFemale, isfeet=true;
    private double height;

    private String [] feetValues = new String[10];
    private String [] inchValues = new String[100];
    //private String [] meterValues = new String[10];
    //private String [] cmValues = new String[100];
    NumberPicker mFeetPicker,mInchPicker;
    TextView mHeightText,mFeetText,mInchText;
    ImageView mHeightImg;
    Button mNextPageBtn;
    ToggleButton mFeetvsInch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_height_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }



        for(int i=0;i<10;i++)
        {
            feetValues[i] = String.valueOf(i);
        }
        for(int i=0;i<100;i++)
        {
            inchValues[i] = String.valueOf(i);
        }


        mHeightText = findViewById(R.id.height_info_text);
        mFeetText = findViewById(R.id.feet_text);
        mInchText = findViewById(R.id.inch_text);
        mHeightImg = findViewById(R.id.height_info_img);
        mNextPageBtn = findViewById(R.id.next_page_btn);

        mFeetPicker = findViewById(R.id.num_picker_feet);
        mFeetPicker.setMinValue(0);
        mFeetPicker.setMaxValue(7);
        mFeetPicker.setDisplayedValues(feetValues);

        mInchPicker = findViewById(R.id.num_picker_inch);
        mInchPicker.setMinValue(0);
        mInchPicker.setMaxValue(11);
        mInchPicker.setDisplayedValues(inchValues);

        mFeetvsInch = findViewById(R.id.feet_vs_inch);

        mFeetvsInch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mFeetText.setText("Feet");
                    mInchText.setText("Inches");
                    mFeetPicker.setMinValue(0);
                    mFeetPicker.setMaxValue(7);
                    mInchPicker.setMinValue(0);
                    mInchPicker.setMaxValue(11);
                    isfeet = true;
                } else {
                    mFeetText.setText("Meter");
                    mInchText.setText("Cm");
                    mFeetPicker.setMinValue(0);
                    mFeetPicker.setMaxValue(7);
                    //mFeetPicker.setDisplayedValues(meterValues);
                    mInchPicker.setMinValue(0);
                    mInchPicker.setMaxValue(99);
                    //mInchPicker.setDisplayedValues(cmValues);
                    isfeet = false;

                    // The toggle is disabled
                }
            }
        });

        mNextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isfeet)
                {
                    height = mFeetPicker.getValue() * 12;
                    height += mInchPicker.getValue();

                }
                else
                {
                    height = mFeetPicker.getValue() * 100;
                    height += mInchPicker.getValue();
                    height *= 0.393701;
                }

                Intent intent = new Intent(UserHeightInfoActivity.this, UserAgeInfoActivity.class);
                intent.putExtra("isfemale", isFemale);
                intent.putExtra("height", height);
                System.out.println("Innnnnnnnnnnnnn  " + isFemale + " " + isfeet + " " + height);
                startActivity(intent);
                finish();
            }
        });


    }
}
