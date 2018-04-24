package fooddiarydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DiaryOperations {
    public static final String LOGTAG = "FOOD_MNG_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DiaryDBHandler.COLUMN_DIARY_ID,
            DiaryDBHandler.COLUMN_DATE,
            DiaryDBHandler.COLUMN_FOOD_NAME,
            DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT,  //gram/unit/cup/spoon etc serving units
            DiaryDBHandler.COLUMN_FOOD_SERVING_AMOUNT,  //amount of the selected serving unit
            DiaryDBHandler.COLUMN_MEAL_TYPE,
            DiaryDBHandler.COLUMN_TOTAL_CAL_SELECTED_FOOD
    };

    public DiaryOperations(Context context) {
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

    public FoodDiary addFoodDiary(FoodDiary foodDiary) {

        ContentValues values = new ContentValues();
        values.put(DiaryDBHandler.COLUMN_DATE, foodDiary.getDate());
        values.put(DiaryDBHandler.COLUMN_FOOD_NAME, foodDiary.getFood_name());
        values.put(DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT, foodDiary.getFood_serving_measurement());
        values.put(DiaryDBHandler.COLUMN_FOOD_SERVING_AMOUNT, foodDiary.getFood_serving_amount());
        values.put(DiaryDBHandler.COLUMN_MEAL_TYPE, foodDiary.getMeal_type());
        values.put(DiaryDBHandler.COLUMN_TOTAL_CAL_SELECTED_FOOD, foodDiary.getTotal_cal_selected_food());

        long insertid = database.insert(DiaryDBHandler.TABLE_DIARY,null, values);
        foodDiary.setDiary_id(insertid);
        return foodDiary;
    }

    public FoodDiary getFoodDiary(long id) {

        Cursor cursor = database.query(DiaryDBHandler.TABLE_DIARY, allColumns,DiaryDBHandler.COLUMN_DIARY_ID + "=?",
                new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        FoodDiary e = new FoodDiary(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

        return e;
    }

    public List<FoodDiary> getAllFoodDiary() {
        Cursor cursor = database.query(DiaryDBHandler.TABLE_DIARY, allColumns,null,null,null, null, null);
        List<FoodDiary> foodDiaryList = new ArrayList<>();

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                FoodDiary foodDiary = new FoodDiary();
                foodDiary.setDiary_id(cursor.getLong(cursor.getColumnIndex(DiaryDBHandler.COLUMN_DIARY_ID)));
                foodDiary.setDate(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_DATE)));
                foodDiary.setFood_name(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_FOOD_NAME)));
                foodDiary.setFood_serving_measurement(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT)));
                foodDiary.setFood_serving_amount(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_FOOD_SERVING_AMOUNT)));
                foodDiary.setMeal_type(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_MEAL_TYPE)));
                foodDiary.setTotal_cal_selected_food(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_TOTAL_CAL_SELECTED_FOOD)));

                foodDiaryList.add(foodDiary);
            }
        }
        return foodDiaryList;
    }

    public List<FoodDiary> getFoodListByMealType(String current_date, String meal_type) {
        Cursor cursor = database.query(DiaryDBHandler.TABLE_DIARY, allColumns,null,null,null, null, null);
        List<FoodDiary> foodDiaryList = new ArrayList<>();

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                if(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_MEAL_TYPE)).equals(meal_type) && cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_DATE)).equals(current_date))
                {
                    FoodDiary foodDiary = new FoodDiary();
                    foodDiary.setDiary_id(cursor.getLong(cursor.getColumnIndex(DiaryDBHandler.COLUMN_DIARY_ID)));
                    foodDiary.setDate(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_DATE)));
                    foodDiary.setFood_name(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_FOOD_NAME)));
                    foodDiary.setFood_serving_measurement(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT)));
                    foodDiary.setFood_serving_amount(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_FOOD_SERVING_AMOUNT)));
                    foodDiary.setMeal_type(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_MEAL_TYPE)));
                    foodDiary.setTotal_cal_selected_food(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_TOTAL_CAL_SELECTED_FOOD)));

                    foodDiaryList.add(foodDiary);
                }

            }
        }

        return foodDiaryList;
    }






    public int updateFoodDiary(FoodDiary foodDiary) {
        ContentValues values = new ContentValues();
        values.put(DiaryDBHandler.COLUMN_DATE, foodDiary.getDate());
        values.put(DiaryDBHandler.COLUMN_FOOD_NAME, foodDiary.getFood_name());
        values.put(DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT, foodDiary.getFood_serving_measurement());
        values.put(DiaryDBHandler.COLUMN_FOOD_SERVING_AMOUNT, foodDiary.getFood_serving_amount());
        values.put(DiaryDBHandler.COLUMN_MEAL_TYPE, foodDiary.getMeal_type());
        values.put(DiaryDBHandler.COLUMN_TOTAL_CAL_SELECTED_FOOD, foodDiary.getTotal_cal_selected_food());

        return database.update(DiaryDBHandler.TABLE_DIARY, values,
                DiaryDBHandler.COLUMN_DIARY_ID + "=?", new String[] { String.valueOf(foodDiary.getDiary_id())});
    }

    public void removeFoodDiary(FoodDiary foodDiary) {
        database.delete(DiaryDBHandler.TABLE_DIARY,DiaryDBHandler.COLUMN_DIARY_ID + "=" + foodDiary.getDiary_id(),null);
    }
}
