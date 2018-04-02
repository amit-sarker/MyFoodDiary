package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "food.db";
    private static final int DATABASE_VERSION = 5;

    public static final String TABLE_FOOD = "food";
    public static final String COLUMN_FOOD_ID = "food_id";
    public static final String COLUMN_FOOD_NAME = "food_name";
    public static final String COLUMN_FOOD_SERVING_SIZE = "food_serving_size";
    public static final String COLUMN_FOOD_SERVING_MEASUREMENT = "food_serving_mesurment";
    public static final String COLUMN_FOOD_ENERGY = "food_energy";
    public static final String COLUMN_FOOD_PROTEINS = "food_proteins";
    public static final String COLUMN_FOOD_CARBOHYDRATES = "food_carbohydrates";
    public static final String COLUMN_FOOD_FAT = "food_fat";
    public static final String COLUMN_FOOD_ENERGY_CALCULATED = "food_energy_calculated";
    public static final String COLUMN_FOOD_PROTEINS_CALCULATED = "food_proteins_calculated";
    public static final String COLUMN_FOOD_CARBOHYDRATES_CALCULATED = "food_carbohydrates_calculated";
    public static final String COLUMN_FOOD_FAT_CALCULATED = "food_fat_calculated";
    public static final String COLUMN_FOOD_CATEGORY_ID = "food_category_id";
    public static final String COLUMN_FOOD_IMAGE = "food_image";
    public static final String COLUMN_FOOD_NOTES = "food_notes";


    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_FOOD + " (" +
                    COLUMN_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FOOD_NAME + " TEXT, " +
                    COLUMN_FOOD_SERVING_SIZE + " DOUBLE, " +
                    COLUMN_FOOD_SERVING_MEASUREMENT + " TEXT, " +
                    COLUMN_FOOD_ENERGY + " DOUBLE, " +
                    COLUMN_FOOD_PROTEINS + " DOUBLE, " +
                    COLUMN_FOOD_CARBOHYDRATES + " DOUBLE, " +
                    COLUMN_FOOD_FAT + " DOUBLE, " +
                    COLUMN_FOOD_ENERGY_CALCULATED + " DOUBLE, " +
                    COLUMN_FOOD_PROTEINS_CALCULATED + " DOUBLE, " +
                    COLUMN_FOOD_CARBOHYDRATES_CALCULATED + " DOUBLE, " +
                    COLUMN_FOOD_FAT_CALCULATED + " DOUBLE, " +
                    COLUMN_FOOD_CATEGORY_ID + " INTEGER, " +
                    COLUMN_FOOD_IMAGE + " TEXT, " +
                    COLUMN_FOOD_NOTES + " TEXT " +
                    ")";


    public FoodDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }
}
