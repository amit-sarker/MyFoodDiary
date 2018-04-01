package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import generalpersondatabase.Person;
import generalpersondatabase.PersonHelper;

public class UserGenderInfo extends AppCompatActivity {

    private boolean isFemale;
    private TextView mGenderInfoText;
    private ImageView mGenderInfoImg;
    private Button mFemaleOptionBtn, mMaleOptionBtn,mNextPageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gender_info);

        PersonHelper userHelper = new PersonHelper();
        //Intent intent = getIntent();
        //final Person User = (Person) intent.getSerializableExtra("sampleObject");

        mGenderInfoText = findViewById(R.id.gender_info_text);
        mGenderInfoImg = (ImageView) findViewById(R.id.gender_info_img);
        mFemaleOptionBtn = findViewById(R.id.female_btn);
        mMaleOptionBtn = findViewById(R.id.male_btn);
        mNextPageBtn = findViewById(R.id.next_page_btn);

        mFemaleOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isFemale = true;
               // User.setGender("female");

            }
        });
        mMaleOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isFemale = false;
                // User.setGender("male");

            }
        });

        mNextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserGenderInfo.this, UserHeightInfo.class);
                intent.putExtra("isfemale", isFemale);
                //intent.putExtra("fromGender", (Serializable) User);
                startActivity(intent);
            }
        });

       // mGenderInfoImg.setImageResource(R.drawable.female_male_icon);
    }
}
