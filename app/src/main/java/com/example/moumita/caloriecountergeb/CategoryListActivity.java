package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

    Toolbar toolbar;
    private List<FoodListHelper> categoryList = new ArrayList<>();
    private CategoryOperations categoryData;
    private String mealType;
    private ListView categoryListView;
    private FoodListAdapter foodListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Bundle bundle = getIntent().getExtras();
        mealType = bundle.getString("meal_type");

        categoryListView = findViewById(R.id.category_list_view);


        categoryData = new CategoryOperations(this);
        categoryData.open();

        final List<FoodCategory> foodCategoryList = categoryData.getDistinctFoodCategory();

        for(FoodCategory a: foodCategoryList) {
            String categoryName = a.getCategoryName();
            String categoryImage = a.getCategoryImage();
            categoryList.add(new FoodListHelper(categoryName,ImageID(categoryImage)));
            //System.err.println(a.toString());
        }

        categoryData.close();

        foodListAdapter= new FoodListAdapter(categoryList,this);

        categoryListView.setAdapter(foodListAdapter);
        categoryListView.setClickable(true);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
                Object obj = categoryListView.getAdapter().getItem(itemNumber);
                final String categoryName = foodCategoryList.get(itemNumber).getCategoryName();
                Log.d("MyLog", "Value is: " + categoryName);

                Intent intent = new Intent(CategoryListActivity.this, FoodListActivity.class);
                intent.putExtra("meal_type", mealType);
                intent.putExtra("categoryname", categoryName);
                startActivity(intent);
            }
        });


       /* mListView = findViewById(R.id.listview);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_value_layout, categoryList);
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
        */
    }

    public int ImageID(String image_name) {
        int resID = this.getResources().getIdentifier(image_name, "drawable", getPackageName());
        return resID;
    }
}
