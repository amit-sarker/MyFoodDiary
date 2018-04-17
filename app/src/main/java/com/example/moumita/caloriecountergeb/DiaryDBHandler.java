package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 11;

    public static final String TABLE_DIARY = "diary";
    public static final String COLUMN_DIARY_ID = "diaryId";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_FOOD_NAME = "foodName";
    public static final String COLUMN_FOOD_SERVING_MEASUREMENT = "servingMeasurement";
    public static final String COLUMN_MEAL_TYPE = "mealType";


    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_DIARY + " (" +
                    COLUMN_DIARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " DATE, " +
                    COLUMN_FOOD_NAME + " TEXT, " +
                    COLUMN_FOOD_SERVING_MEASUREMENT + " TEXT, " +
                    COLUMN_MEAL_TYPE + " TEXT " +
                    ")";

    public DiaryDBHandler(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }
}
