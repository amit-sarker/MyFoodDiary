package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomepageActivity extends AppCompatActivity {
    private Button breakfastButton, lunchButton, dinnerButton, snacksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        breakfastButton = findViewById(R.id.breakfast_btn);
        lunchButton = findViewById(R.id.lunch_btn);
        dinnerButton = findViewById(R.id.dinner_btn);
        snacksButton = findViewById(R.id.snacks_btn);


        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });

        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });

        dinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });

        snacksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });
    }
}
