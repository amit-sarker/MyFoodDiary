package com.example.moumita.caloriecountergeb;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AddSimpleCalorieActivity extends AppCompatActivity {

    private TextView addFoodName, addSimpleCalorie;
    private Button addFoodBtn;
    private String foodName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_simple_calorie);

        addFoodName = findViewById(R.id.add_food_name);
        addSimpleCalorie = findViewById(R.id.add_simple_calorie);
        addFoodBtn = findViewById(R.id.add_food_btn);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.90);

        getWindow().setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#795548")));




    }
}
