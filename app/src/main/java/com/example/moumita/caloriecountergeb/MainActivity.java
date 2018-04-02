package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fooddatabase.Food;
import fooddatabase.FoodOperations;
import okhttp3.OkHttpClient;
import userinfo.UserGenderInfoActivity;


public class MainActivity extends AppCompatActivity {

    private FoodOperations foodData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Stetho */
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        Intent intent = new Intent(MainActivity.this, UserGenderInfoActivity.class);
        startActivity(intent);

        foodData = new FoodOperations(this);
        foodData.open();


        try {
            readFromAssets(this,"foodlist.txt", foodData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        foodData.close();
    }

    public static String readFromAssets(Context context, String filename, FoodOperations foodData) throws IOException {
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
            System.err.println(mLine);
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

            System.out.println(eachAttribute);

        }

        foodData.addFood(food);

    }
}