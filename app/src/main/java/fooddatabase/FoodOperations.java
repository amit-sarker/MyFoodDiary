package fooddatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
            FoodDBHandler.COLUMN_FOOD_WATER,
            FoodDBHandler.COLUMN_FOOD_FIBER,
            FoodDBHandler.COLUMN_FOOD_VIT_A,
            FoodDBHandler.COLUMN_FOOD_VIT_C,
            FoodDBHandler.COLUMN_FOOD_VIT_E,
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
        values.put(FoodDBHandler.COLUMN_FOOD_WATER, food.getFood_water());
        values.put(FoodDBHandler.COLUMN_FOOD_FIBER, food.getFood_fiber());
        values.put(FoodDBHandler.COLUMN_FOOD_VIT_A, food.getFood_vit_a());
        values.put(FoodDBHandler.COLUMN_FOOD_VIT_C, food.getFood_vit_c());
        values.put(FoodDBHandler.COLUMN_FOOD_VIT_E, food.getFood_vit_e());
        values.put(FoodDBHandler.COLUMN_FOOD_CATEGORY_ID, food.getFood_category_id());
        values.put(FoodDBHandler.COLUMN_FOOD_IMAGE, food.getFood_image());
        values.put(FoodDBHandler.COLUMN_FOOD_NOTES, food.getFood_notes());

        long insertid = database.insert(FoodDBHandler.TABLE_FOOD,null, values);
        food.setFood_id(insertid);
        return food;
    }


    public Food getFood(long id) {

        Cursor cursor = database.query(FoodDBHandler.TABLE_FOOD, allColumns,FoodDBHandler.COLUMN_FOOD_ID + "=?", new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Food e = new Food(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getDouble(2),
                cursor.getString(3), cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6),
                cursor.getDouble(7), cursor.getDouble(8), cursor.getDouble(9), cursor.getDouble(10),
                cursor.getDouble(11), cursor.getDouble(12), Long.parseLong(cursor.getString(13)), cursor.getString(14),
                cursor.getString(15));

        return e;
    }

    public Food getFoodByName(String foodName) {
        Cursor cursor = database.query(FoodDBHandler.TABLE_FOOD, allColumns,"food_name=?", new String[]{foodName},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Food e = new Food(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getDouble(2),
                cursor.getString(3), cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6),
                cursor.getDouble(7), cursor.getDouble(8), cursor.getDouble(9), cursor.getDouble(10),
                cursor.getDouble(11), cursor.getDouble(12), Long.parseLong(cursor.getString(13)), cursor.getString(14),
                cursor.getString(15));
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
                food.setFood_water(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_WATER)));
                food.setFood_fiber(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_FIBER)));
                food.setFood_vit_a(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_VIT_A)));
                food.setFood_vit_c(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_VIT_C)));
                food.setFood_vit_e(cursor.getDouble(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_VIT_E)));
                food.setFood_category_id(cursor.getLong(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_CATEGORY_ID)));
                food.setFood_image(cursor.getString(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_IMAGE)));
                food.setFood_notes(cursor.getString(cursor.getColumnIndex(FoodDBHandler.COLUMN_FOOD_NOTES)));
                foodList.add(food);
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
        values.put(FoodDBHandler.COLUMN_FOOD_WATER, food.getFood_water());
        values.put(FoodDBHandler.COLUMN_FOOD_FIBER, food.getFood_fiber());
        values.put(FoodDBHandler.COLUMN_FOOD_VIT_A, food.getFood_vit_a());
        values.put(FoodDBHandler.COLUMN_FOOD_VIT_C, food.getFood_vit_c());
        values.put(FoodDBHandler.COLUMN_FOOD_VIT_E, food.getFood_vit_e());
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