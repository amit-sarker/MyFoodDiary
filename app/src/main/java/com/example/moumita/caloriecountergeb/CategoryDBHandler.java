package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CategoryDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "category.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ID = "categoryId";
    public static final String COLUMN_CATEGORY_NAME = "categoryname";
    public static final String COLUMN_FOOD_ID = "foodid";
    public static final String COLUMN_FOOD_NAME = "foodname";
    public static final String COLUMN_FOOD_IMAGE = "foodimage";
    public static final String COLUMN_CATEGORY_IMAGE = "categoryimage";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CATEGORY + " (" +
                    COLUMN_CATEGORY_ID + " INTEGER, " +
                    COLUMN_CATEGORY_NAME + " TEXT, " +
                    COLUMN_FOOD_ID + " INTEGER, " +
                    COLUMN_FOOD_NAME + " TEXT, " +
                    COLUMN_FOOD_IMAGE + " TEXT, " +
                    COLUMN_CATEGORY_IMAGE + " TEXT " +
                    ")";

    public CategoryDBHandler(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }
}
