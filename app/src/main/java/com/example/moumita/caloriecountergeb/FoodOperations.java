package com.example.moumita.caloriecountergeb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import generalpersondatabase.Person;
import generalpersondatabase.PersonDBHandler;

public class FoodOperations {
    public static final String LOGTAG = "FOOD_MNG_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            FoodDBHandler.COLUMN_FOOD_ID,
            FoodDBHandler.COLUMN_FOOD_NAME,
            FoodDBHandler.COLUMN_FOOD_SERVING_SIZE,
            FoodDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT,
            FoodDBHandler.COLUMN_FOOD_ENERGY,
            FoodDBHandler.COLUMN_FOOD_PROTEINS,
            FoodDBHandler.COLUMN_FOOD_CARBOHYDRATES,
            FoodDBHandler.COLUMN_FOOD_FAT,
            FoodDBHandler.COLUMN_FOOD_ENERGY_CALCULATED,
            FoodDBHandler.COLUMN_FOOD_PROTEINS_CALCULATED,
            FoodDBHandler.COLUMN_FOOD_CARBOHYDRATES_CALCULATED,
            FoodDBHandler.COLUMN_FOOD_FAT_CALCULATED,
            FoodDBHandler.COLUMN_FOOD_CATEGORY_ID,
            FoodDBHandler.COLUMN_FOOD_IMAGE,
            FoodDBHandler.COLUMN_FOOD_NOTES
    };


    public FoodOperations(Context context){
        dbhandler = new FoodDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public Food addFood(Food food) {
        ContentValues values = new ContentValues();
        values.put(FoodDBHandler.COLUMN_FOOD_NAME, food.getFood_name());
        values.put(FoodDBHandler.COLUMN_FOOD_SERVING_SIZE, food.getFood_serving_size());
        values.put(FoodDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT, food.getFood_serving_mesurment());
        values.put(FoodDBHandler.COLUMN_FOOD_ENERGY, food.getFood_energy());
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


    public Food getFood(long id) {
        Cursor cursor = database.query(FoodDBHandler.TABLE_FOOD, allColumns,FoodDBHandler.COLUMN_FOOD_ID + "=?", new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Food e = new Food(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getDouble(2),
                cursor.getString(3), cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6),
                cursor.getDouble(7), cursor.getDouble(8), cursor.getDouble(9), cursor.getDouble(10),
                cursor.getDouble(11), Long.parseLong(cursor.getString(12)), cursor.getString(13),
                cursor.getString(14));
        return e;
    }


    public List<Food> getAllFood() {
        Cursor cursor = database.query(FoodDBHandler.TABLE_FOOD, allColumns,null,null,null, null, null);
        List<Food> foodList = new ArrayList<>();

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                Food food = new Food();
                food.setFood_id(cursor.getLong(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_ID)));
                food.setFood_name(cursor.getString(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_NAME)));
                food.setFood_serving_size(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_SERVING_SIZE)));
                food.setFood_serving_mesurment(cursor.getString(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT)));
                food.setFood_energy(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_ENERGY)));
                food.setFood_proteins(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_PROTEINS)));
                food.setFood_carbohydrates(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_CARBOHYDRATES)));
                food.setFood_fat(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_FAT)));
                food.setFood_energy_calculated(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_ENERGY_CALCULATED)));
                food.setFood_proteins_calculated(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_PROTEINS_CALCULATED)));
                food.setFood_carbohydrates_calculated(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_CARBOHYDRATES_CALCULATED)));
                food.setFood_fat_calculated(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_FAT_CALCULATED)));
                food.setFood_category_id(cursor.getLong(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_CATEGORY_ID)));
                food.setFood_image(cursor.getString(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_IMAGE)));
                food.setFood_notes(cursor.getString(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_NOTES)));
            }

        }
        return foodList;
    }

    public int updateFood(Food food) {
        ContentValues values = new ContentValues();
        values.put(FoodDBHandler.COLUMN_FOOD_NAME, food.getFood_name());
        values.put(FoodDBHandler.COLUMN_FOOD_SERVING_SIZE, food.getFood_serving_size());
        values.put(FoodDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT, food.getFood_serving_mesurment());
        values.put(FoodDBHandler.COLUMN_FOOD_ENERGY, food.getFood_energy());
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

        return database.update(FoodDBHandler.TABLE_FOOD, values,
                FoodDBHandler.COLUMN_FOOD_ID + "=?", new String[] { String.valueOf(food.getFood_id())});
    }

    public void removeFood(Food food) {
        database.delete(FoodDBHandler.TABLE_FOOD,FoodDBHandler.COLUMN_FOOD_ID + "=" + food.getFood_id(),null);

    }
}