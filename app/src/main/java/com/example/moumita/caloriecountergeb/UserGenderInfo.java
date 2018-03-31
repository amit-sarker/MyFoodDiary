package com.example.moumita.caloriecountergeb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserGenderInfo extends AppCompatActivity {
    private TextView mGenderInfoText;
    private ImageView mGenderInfoImg;
    private Button mFemaleOptionBtn, mMaleOptionBtn,mNextPageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gender_info);

        mGenderInfoText = findViewById(R.id.gender_info_text);
        mGenderInfoImg = (ImageView) findViewById(R.id.gender_info_img);
        mFemaleOptionBtn = findViewById(R.id.female_btn);
        mMaleOptionBtn = findViewById(R.id.male_btn);
        mNextPageBtn = findViewById(R.id.next_page_btn);

       // mGenderInfoImg.setImageResource(R.drawable.female_male_icon);
    }
}
