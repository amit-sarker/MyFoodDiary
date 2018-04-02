package com.example.moumita.caloriecountergeb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import userinfo.UserAgeInfoActivity;
import userinfo.UserGenderInfoActivity;
import userinfo.UserWeightInfoActivity;


public class MainActivity extends AppCompatActivity {

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

        /*
        //InputStream inputStream = getResources().openRawResource();
        String lineFromFile;
        File file = new File("res\\files\\foodlist.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Ammmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
            e.printStackTrace();
        }

        Food food = new Food();

        assert sc != null;
        while (sc.hasNextLine()) {

            lineFromFile = sc.nextLine();
            System.out.println(lineFromFile);

            String foodAttributes[]= lineFromFile.split(",");

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
        }*/

    }
}