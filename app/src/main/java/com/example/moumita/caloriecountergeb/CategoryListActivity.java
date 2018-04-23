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

import categorydatabase.CategoryOperations;
import categorydatabase.FoodCategory;

public class CategoryListActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> categoryList = new ArrayList<>();

    private CategoryOperations categoryData;
    private String mealType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        Bundle bundle = getIntent().getExtras();
        mealType = bundle.getString("meal_type");


        categoryData = new CategoryOperations(this);
        categoryData.open();

        List<FoodCategory> foodCategoryList = categoryData.getDistinctFoodCategory();

        for(FoodCategory a: foodCategoryList) {
            categoryList.add(a.getCategoryName());
            //System.err.println(a.toString());
        }

        categoryData.close();
        mListView = findViewById(R.id.listview);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.my_list, categoryList);
        mListView.setAdapter(arrayAdapter);

        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {

                Object obj = mListView.getAdapter().getItem(itemNumber);
                final String categoryName = obj.toString();
                Log.d("MyLog", "Value is: " + categoryName);

                Intent intent = new Intent(CategoryListActivity.this, FoodListActivity.class);
                intent.putExtra("meal_type", mealType);
                intent.putExtra("categoryname", categoryName);
                startActivity(intent);
            }
        });
    }
}
