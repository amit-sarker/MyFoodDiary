package com.example.moumita.caloriecountergeb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;
import mehdi.sakout.fancybuttons.FancyButton;

public class DialogActivity extends AppCompatActivity {

    private TextView updateText, weightText1, weightText2;
    private FloatingActionButton plusFab, minusFab;
    private FancyButton unitBtn, updateWeightBtn;
    private Typeface mTfLight, mTfRegular, mtfBold;
    private PersonOperations personData;
    private String weightOne, weightTwo;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.90);

        getWindow().setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#795548")));

        mTfRegular = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(this.getAssets(), "OpenSans-Light.ttf");
        mtfBold = Typeface.createFromAsset(this.getAssets(), "OpenSans-Bold.ttf");

        updateText = findViewById(R.id.weight_text);
        weightText1 = findViewById(R.id.weight_text1);
        weightText2 = findViewById(R.id.weight_text2);

        plusFab = findViewById(R.id.plus_fab_btn);
        minusFab = findViewById(R.id.minus_fab_btn);
        unitBtn = findViewById(R.id.weight_unit_btn);
        updateWeightBtn = findViewById(R.id.btn_update_weight_dialog);

        personData = new PersonOperations(this);

        updateText.setTypeface(mTfLight);
        updateText.setTextSize(20);

        weightText1.setTypeface(mTfRegular);
        weightText1.setTextSize(50);

        weightText2.setTypeface(mTfRegular);
        weightText2.setTextSize(50);


        personData.open();
        Person person = new Person();
        person = personData.getPerson(1);
        String current_weight = person.getWeight();

        String[] weight_text = current_weight.split("\\.");
        personData.close();


        weightText1.setText(weight_text[0]);
        weightText2.setText("." + weight_text[1]);

        weightText1.setClickable(true);
        weightText2.setClickable(true);
        plusFab.setClickable(true);
        minusFab.setClickable(true);
        unitBtn.setClickable(true);

        weightText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightText1.setTextSize(60);
                weightText2.setTextSize(50);
                weightText1.setTextColor(Color.WHITE);

                plusFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        weightOne = weightText1.getText().toString();
                        if(Integer.parseInt(weightOne) + 1 <= 300)
                            weightText1.setText(String.valueOf(Integer.parseInt(weightOne) + 1));
                        else weightText1.setText("300");
                    }
                });

                minusFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        weightOne = weightText1.getText().toString();
                        if(Integer.parseInt(weightOne) - 1 >= 30)
                            weightText1.setText(String.valueOf(Integer.parseInt(weightOne) - 1));
                        else weightText1.setText("30");
                    }
                });
            }
        });

        weightText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightText1.setTextSize(50);
                weightText2.setTextSize(60);
                weightText2.setTextColor(Color.WHITE);

                plusFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        weightTwo = weightText2.getText().toString();
                        String tempWeight;
                        tempWeight = String.valueOf(weightTwo.charAt(1));
                        if(Integer.parseInt(tempWeight) + 1 > 9) {
                            weightOne = weightText1.getText().toString();
                            if(Integer.parseInt(weightOne) + 1 <= 300)
                                weightText1.setText(String.valueOf(Integer.parseInt(weightOne) + 1));
                            else weightText1.setText("300");
                        } else {
                            weightText2.setText("." + String.valueOf(Integer.parseInt(tempWeight) + 1));
                        }

                        if(Integer.parseInt(tempWeight) + 1 > 9) weightText2.setText(".0");

                    }
                });

                minusFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        weightTwo = weightText2.getText().toString();
                        String tempWeight;
                        tempWeight = String.valueOf(weightTwo.charAt(1));
                        if(Integer.parseInt(tempWeight) - 1 < 0) {
                            weightOne = weightText1.getText().toString();
                            if(Integer.parseInt(weightOne) - 1 >= 30)
                                weightText1.setText(String.valueOf(Integer.parseInt(weightOne) - 1));
                            else weightText1.setText("30");
                        } else {
                            weightText2.setText("." + String.valueOf(Integer.parseInt(tempWeight) - 1));
                        }

                        if(Integer.parseInt(tempWeight) - 1 < 0) weightText2.setText(".9");
                    }
                });
            }
        });

    }
}
