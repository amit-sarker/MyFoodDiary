package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends AppCompatActivity {
    private String categoryName;
    private CategoryOperations categoryData;
    private ListView mListView;
    private List<String> categoryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        categoryData = new CategoryOperations(this);
        Bundle bundle = getIntent().getExtras();
        categoryName = bundle.getString("categoryname");

        categoryData.open();

        List<FoodCategory> foodCategoryList = categoryData.getFoodCategoryByName(categoryName);

        for(FoodCategory a: foodCategoryList) {
            categoryList.add(a.getFoodName());
            System.err.println(a.toString());
        }


        categoryData.close();

        mListView = findViewById(R.id.food_list);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.my_list, categoryList);
        mListView.setAdapter(arrayAdapter);

        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {

                Object obj = mListView.getAdapter().getItem(itemNumber);
                final String foodName = obj.toString();
                Log.d("MyLog", "Value is: " + foodName);

                Intent intent = new Intent(FoodListActivity.this, FoodInfoActivity.class);
                intent.putExtra("foodname", foodName);
                startActivity(intent);
            }
        });

    }
}
