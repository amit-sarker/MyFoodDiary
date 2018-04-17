package com.example.moumita.caloriecountergeb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fooddatabase.Food;
import fooddatabase.FoodDBHandler;

public class DiaryOperations {
    public static final String LOGTAG = "FOOD_MNG_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DiaryDBHandler.COLUMN_DIARY_ID,
            DiaryDBHandler.COLUMN_DATE,
            DiaryDBHandler.COLUMN_FOOD_NAME,
            DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT,
            DiaryDBHandler.COLUMN_MEAL_TYPE
    };

    public DiaryOperations(Context context){
        dbhandler = new DiaryDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public FoodDiary addFoodDiary(FoodDiary foodDiary) throws ParseException {
        /*String current_date_str = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Date current_date = new SimpleDateFormat("yyyy-MM-dd").parse(current_date_str);*/
        ContentValues values = new ContentValues();
        values.put(DiaryDBHandler.COLUMN_DATE, foodDiary.getDate());
        values.put(DiaryDBHandler.COLUMN_FOOD_SERVING_SIZE, food.getFood_serving_size());
        values.put(DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT, food.getFood_serving_mesurment());
        values.put(DiaryDBHandler.COLUMN_FOOD_ENERGY, food.getFood_energy());
        values.put(FoodDBHandler.COLUMN_FOOD_PROTEINS, food.getFood_proteins());
        values.put(FoodDBHandler.COLUMN_FOOD_CARBOHYDRATES, food.getFood_carbohydrates());
        values.put(FoodDBHandler.COLUMN_FOOD_FAT, food.getFood_fat());
        values.put(FoodDBHandler.COLUMN_FOOD_ENERGY_CALCULATED, food.getFood_energy_calculated());
        values.put(FoodDBHandler.COLUMN_FOOD_PROTEINS_CALCULATED, food.getFood_proteins_calculated());
        values.put(FoodDBHandler.COLUMN_FOOD_CARBOHYDRATES_CALCULATED, food.getFood_carbohydrates_calculated());
        values.put(FoodDBHandler.COLUMN_FOOD_FAT_CALCULATED, food.getFood_fat_calculated());
        values.put(FoodDBHandler.COLUMN_FOOD_CATEGORY_ID, food.getFood_category_id());
        values.put(FoodDBHandler.COLUMN_FOOD_IMAGE, food.getFood_image());
        values.put(FoodDBHandler.COLUMN_FOOD_NOTES, food.getFood_notes());

        long insertid = database.insert(FoodDBHandler.TABLE_FOOD,null, values);
        food.setFood_id(insertid);
        return food;
    }

}
