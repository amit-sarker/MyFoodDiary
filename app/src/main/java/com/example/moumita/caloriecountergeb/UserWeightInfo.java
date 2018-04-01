package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class UserWeightInfo extends AppCompatActivity {
    private boolean isFemale, isfeet=true, iskg = true;
    private double height, weight;
    private int age;

    EditText mWeightInputText;
    TextView mWeightText,mKgText;
    ImageView mWeightImg;
    Button mNextPageBtn;
    ToggleButton mlbsvsKg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_weight_info);

        Bundle bundle = getIntent().getExtras();
        isFemale = bundle.getBoolean("isfemale");
        isfeet = bundle.getBoolean("isfeet");
        height = bundle.getDouble("height");
        age = bundle.getInt("age");


        mWeightInputText = (EditText) findViewById(R.id.weight_editText_id);
        mWeightText = (TextView) findViewById(R.id.weight_info_text);
        mKgText = (TextView) findViewById(R.id.kg_text);
        mWeightImg = (ImageView) findViewById(R.id.weight_info_img);
        mNextPageBtn = (Button) findViewById(R.id.next_page_btn);



        mlbsvsKg = findViewById(R.id.lbs_vs_kg);

        mlbsvsKg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mKgText.setText("Kg");
                    iskg = true;

                } else {
                    mKgText.setText("lbs");
                    iskg = false;

                }
            }
        });

        mNextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String weightstr = mWeightInputText.getText().toString();
                weight = Double.parseDouble(weightstr);

                if(iskg)
                {

                }
                else
                {
                    weight *= 0.454;
                }

                Intent intent = new Intent(UserWeightInfo.this, GeneralUserActivity.class);
                intent.putExtra("isfemale", isFemale);
                //intent.putExtra("isfeet", isfeet);
                intent.putExtra("height", height);
                intent.putExtra("age", age);
                //intent.putExtra("iskg",iskg);
                intent.putExtra("weight", weight);

                System.out.println("Innnnnnnnnnnnnn  " + isFemale + " " + isfeet + " " + height + " " + age + " " + iskg + " " + weight);
                //intent.putExtra("fromGender", (Serializable) User);
                startActivity(intent);
            }
        });
    }
}
