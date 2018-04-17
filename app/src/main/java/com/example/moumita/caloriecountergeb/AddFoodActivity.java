package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import activities.TestTabActivity;
import fooddatabase.Food;
import fooddatabase.FoodOperations;

public class AddFoodActivity extends AppCompatActivity implements View.OnClickListener {

    private String foodName;
    Toolbar toolbar;
    AutoCompleteTextView text;
    private FoodOperations foodData;
    private List<String> foodList = new ArrayList<>();
    private CardView CategoriesCard, RecentCard, FrequentCard, SimpleCaloriesCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        CategoriesCard = findViewById(R.id.categories_card);
        FrequentCard = findViewById(R.id.frequent_card);
        RecentCard = findViewById(R.id.recent_card);
        SimpleCaloriesCard = findViewById(R.id.simple_calories_card);




        foodData = new FoodOperations(this);

        foodData.open();

        List<Food> getfoodList = foodData.getAllFood();
        //System.err.println("Ekhaneeeeeeeeeeeeeeeeeeeeeeeeee " + getfoodList.size());
        for(Food food: getfoodList) {
            foodList.add(food.getFood_name());
            System.err.println("Print Name : " + food.getFood_name());
        }


        foodData.close();

        text = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

        final ArrayAdapter adapter = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, foodList);

        text.setAdapter(adapter);
        text.setThreshold(1);
        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),adapter.getItem(position).toString(),Toast.LENGTH_SHORT).show();
            }
        });

        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                foodName = adapter.getItem(position).toString();
                Intent intent = new Intent(AddFoodActivity.this, FoodInfoActivity.class);
                intent.putExtra("foodname", foodName);
                startActivity(intent);
               // System.err.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
               // Toast.makeText(getApplicationContext(), "AAAAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
            }
        });

        CategoriesCard.setOnClickListener(this);
        RecentCard.setOnClickListener(this);
        FrequentCard.setOnClickListener(this);
        SimpleCaloriesCard.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId())
        {
            case R.id.categories_card:
                intent = new Intent(this, CategoryListActivity.class);
                startActivity(intent);
                 break;
            case R.id.recent_card:
                intent = new Intent(this, RecentListActivity.class);
                startActivity(intent);
                break;
            case R.id.frequent_card:
                intent = new Intent(this, FrequentListActivity.class);
                startActivity(intent);
                break;
            case R.id.simple_calories_card:
                intent = new Intent(this, AddSimpleCaloriesActivity.class);
                startActivity(intent);
                break;
            default:break;

        }


    }
}
