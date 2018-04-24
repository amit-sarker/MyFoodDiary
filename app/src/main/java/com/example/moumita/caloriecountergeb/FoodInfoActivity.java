package com.example.moumita.caloriecountergeb;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fooddatabase.Food;
import fooddatabase.FoodOperations;
import generalpersondatabase.Person;
import generalpersondatabase.PersonOperations;

public class FoodInfoActivity extends AppCompatActivity {
    private String foodName;
    private FoodOperations foodData;
    private Food selectedFood;
    private TextView foodText, foodInfoText;
    private String foodInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        foodText = findViewById(R.id.food_text);
        foodInfoText = findViewById(R.id.food_info_text);
        foodData = new FoodOperations(this);
        foodInfo = "";

        Bundle bundle = getIntent().getExtras();
        foodName = bundle.getString("foodname");

        foodData.open();

        selectedFood = foodData.getFoodByName(foodName);
        System.err.println(selectedFood.toString());
        foodInfo += "Calorie: " + selectedFood.getFood_energy() + "\n" + "Protein: " + selectedFood.getFood_proteins() + "\n" +
                "Carbohydrates: " + selectedFood.getFood_carbohydrates() + "\n" + "Fat: " + selectedFood.getFood_fat() + "\n" +
                "Notes: " + selectedFood.getFood_notes();

        foodText.setText(selectedFood.getFood_name());
        foodText.setTextColor(Color.parseColor("#F44336"));
        foodInfoText.setText(foodInfo);


    }
}
