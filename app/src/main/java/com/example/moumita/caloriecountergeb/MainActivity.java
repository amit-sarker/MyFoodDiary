package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import categorydatabase.CategoryDBHandler;
import categorydatabase.CategoryOperations;
import categorydatabase.FoodCategory;
import fooddatabase.Food;
import fooddatabase.FoodDBHandler;
import fooddatabase.FoodOperations;
import okhttp3.OkHttpClient;
import piechart.PiePolylineChartActivity;


public class MainActivity extends AppCompatActivity {

    private FoodOperations foodData;
    private CategoryOperations categoryData;
    int oldVersion, newVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Stetho */
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        FoodDBHandler foodDBHandler = new FoodDBHandler(this);
        SQLiteDatabase food_database = foodDBHandler.getWritableDatabase();
        food_database.execSQL("DROP TABLE IF EXISTS " + FoodDBHandler.TABLE_FOOD);
        food_database.execSQL(FoodDBHandler.TABLE_CREATE);

        CategoryDBHandler categoryDBHandler = new CategoryDBHandler(this);
        SQLiteDatabase cat_database = categoryDBHandler.getWritableDatabase();
        cat_database.execSQL("DROP TABLE IF EXISTS " + categoryDBHandler.TABLE_CATEGORY);
        cat_database.execSQL(CategoryDBHandler.TABLE_CREATE);


        foodData = new FoodOperations(this);
        foodData.open();


        try {
            readFromAssetsFood(this,"foodlist.txt", foodData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //PrintFood(foodData);

        foodData.close();

        categoryData = new CategoryOperations(this);
        categoryData.open();

        try {
            readFromAssetsCategory(this, "category.txt", categoryData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //PrintCategory(categoryData);

        categoryData.close();

        Intent intent = new Intent(MainActivity.this, PiePolylineChartActivity.class);
        startActivity(intent);
        finish();
    }


    public static String readFromAssetsCategory(Context context, String filename, CategoryOperations categoryData) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

        // do reading, usually loop until end of file reading
        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        //System.out.println(mLine);
        categoryToDatabase(mLine, categoryData);
        while (mLine != null) {
            sb.append(mLine); // process line
            mLine = reader.readLine();
            if(mLine != null) {
                categoryToDatabase(mLine, categoryData);
            }
            //System.err.println(mLine);
        }
        reader.close();
        return sb.toString();
    }


    public static void categoryToDatabase(String lineFromFile, CategoryOperations categoryData) {
        //System.err.println(lineFromFile);
        String categoryAttributes[]= lineFromFile.split(",");

        FoodCategory foodCategory = new FoodCategory();
        //System.err.println("CCCCCCCCCCCCCCCCCCCCCCCCCCC" + categoryAttributes[0]);
        foodCategory.setCategoryID(Long.parseLong(categoryAttributes[0]));
        foodCategory.setCategoryName(categoryAttributes[1]);
        foodCategory.setFoodID(Long.parseLong(categoryAttributes[2]));
        foodCategory.setFoodName(categoryAttributes[3]);
        foodCategory.setFoodImage(categoryAttributes[4]);
        foodCategory.setCategoryImage(categoryAttributes[5]);

        for (String eachAttribute: categoryAttributes){
            if(eachAttribute.equals("\0"))
                eachAttribute = "null";

            //System.out.println(eachAttribute);

        }

        categoryData.addCategory(foodCategory);

    }



    public static String readFromAssetsFood(Context context, String filename, FoodOperations foodData) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

        // do reading, usually loop until end of file reading
        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        //System.out.println(mLine);
        foodToDatabase(mLine, foodData);
        while (mLine != null) {
            sb.append(mLine); // process line
            mLine = reader.readLine();
            if(mLine != null) {
                foodToDatabase(mLine, foodData);
            }
            //System.err.println(mLine);
        }
        reader.close();
        return sb.toString();
    }

    public static void foodToDatabase(String lineFromFile, FoodOperations foodData) {
        System.err.println(lineFromFile);
        String foodAttributes[]= lineFromFile.split(",");

        Food food = new Food();
        food.setFood_name(foodAttributes[0]);
        food.setFood_serving_size(Double.parseDouble(foodAttributes[1]));
        food.setFood_serving_mesurment(foodAttributes[2]);
        food.setFood_energy(Double.parseDouble(foodAttributes[3]));
        food.setFood_proteins(Double.parseDouble(foodAttributes[4]));
        food.setFood_carbohydrates(Double.parseDouble(foodAttributes[5]));
        food.setFood_fat(Double.parseDouble(foodAttributes[6]));
        food.setFood_energy_calculated(Double.parseDouble(foodAttributes[7]));
        food.setFood_proteins_calculated(Double.parseDouble(foodAttributes[8]));
        food.setFood_carbohydrates_calculated(Double.parseDouble(foodAttributes[9]));
        food.setFood_fat_calculated(Double.parseDouble(foodAttributes[10]));
        food.setFood_category_id(Long.parseLong(foodAttributes[11]));
        food.setFood_image(foodAttributes[12]);
        food.setFood_notes(foodAttributes[13]);

        for (String eachAttribute: foodAttributes){
            if(eachAttribute.equals("\0"))
                eachAttribute = "null";

            //System.out.println(eachAttribute);

        }

        foodData.addFood(food);

    }

    public static void PrintFood(FoodOperations foodData) {
        List<Food> foodList = new ArrayList<>();
        foodList = foodData.getAllFood();
        for(Food a: foodList) {
            System.err.println(a);
        }
    }

    public static void PrintCategory(CategoryOperations categoryData) {
        List<FoodCategory> categoryList = new ArrayList<>();

        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        categoryList = categoryData.getFoodCategory(2);
        for(FoodCategory a: categoryList) {
            System.err.println("AAAA" + a.toString());
        }
    }
}