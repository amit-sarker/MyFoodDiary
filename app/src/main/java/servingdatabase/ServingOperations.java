package servingdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static servingdatabase.ServingDBHandler.TABLE_SERVING;

public class ServingOperations {
    public static final String LOGTAG = "CATEGORY_MNG_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            ServingDBHandler.COLUMN_SERVING_ID,
            ServingDBHandler.COLUMN_FOOD_NAME,
            ServingDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT,
            ServingDBHandler.COLUMN_SERVING_SIZE_TO_GRAMS,
            ServingDBHandler.COLUMN_SERVING_IMAGE_ID
    };

    public ServingOperations(Context context) {
        dbhandler = new ServingDBHandler(context);
    }

    public void open() {
        Log.i(LOGTAG, "Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public FoodServing addServing(FoodServing foodServing) {
        ContentValues values = new ContentValues();
        values.put(ServingDBHandler.COLUMN_SERVING_ID, foodServing.getServing_id());
        values.put(ServingDBHandler.COLUMN_FOOD_NAME, foodServing.getFood_name());
        values.put(ServingDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT, foodServing.getFood_serving_measurement());
        values.put(ServingDBHandler.COLUMN_SERVING_SIZE_TO_GRAMS, foodServing.getServing_size_to_grams());
        values.put(ServingDBHandler.COLUMN_SERVING_IMAGE_ID, foodServing.getServing_image_id());

        database.insert(TABLE_SERVING, null, values);
        return foodServing;
    }

    public List<FoodServing> getFoodServingByName(String foodName) {

        Cursor cursor = database.query(TABLE_SERVING, allColumns, null, null, null, null, null);

        List<FoodServing> foodServingList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(ServingDBHandler.COLUMN_FOOD_NAME)).equals(foodName)) {
                    FoodServing foodServing = new FoodServing();
                    foodServing.setServing_id(cursor.getLong(cursor.getColumnIndex(ServingDBHandler.COLUMN_SERVING_ID)));
                    foodServing.setFood_name(cursor.getString(cursor.getColumnIndex(ServingDBHandler.COLUMN_FOOD_NAME)));
                    foodServing.setFood_serving_measurement(cursor.getString(cursor.getColumnIndex(ServingDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT)));
                    foodServing.setServing_size_to_grams(cursor.getString(cursor.getColumnIndex(ServingDBHandler.COLUMN_SERVING_SIZE_TO_GRAMS)));
                    foodServing.setServing_image_id(cursor.getString(cursor.getColumnIndex(ServingDBHandler.COLUMN_SERVING_IMAGE_ID)));

                    foodServingList.add(foodServing);
                }
            }
        }
        return foodServingList;
    }

    public List<FoodServing> getFoodServingByID(long id) {

        Cursor cursor = database.query(TABLE_SERVING, allColumns, null, null, null, null, null);

        List<FoodServing> foodServingList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getLong(cursor.getColumnIndex(ServingDBHandler.COLUMN_SERVING_ID)) == id) {
                    FoodServing foodServing = new FoodServing();
                    foodServing.setServing_id(cursor.getLong(cursor.getColumnIndex(ServingDBHandler.COLUMN_SERVING_ID)));
                    foodServing.setFood_name(cursor.getString(cursor.getColumnIndex(ServingDBHandler.COLUMN_FOOD_NAME)));
                    foodServing.setFood_serving_measurement(cursor.getString(cursor.getColumnIndex(ServingDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT)));
                    foodServing.setServing_size_to_grams(cursor.getString(cursor.getColumnIndex(ServingDBHandler.COLUMN_SERVING_SIZE_TO_GRAMS)));
                    foodServing.setServing_image_id(cursor.getString(cursor.getColumnIndex(ServingDBHandler.COLUMN_SERVING_IMAGE_ID)));

                    foodServingList.add(foodServing);
                }
            }
        }
        return foodServingList;
    }

    public long getRowCount() {
        SQLiteDatabase db = dbhandler.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_SERVING);
        return count;
    }

    public int updateFoodServing(FoodServing foodServing) {

        ContentValues values = new ContentValues();
        values.put(ServingDBHandler.COLUMN_FOOD_NAME, foodServing.getFood_name());
        values.put(ServingDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT, foodServing.getFood_serving_measurement());
        values.put(ServingDBHandler.COLUMN_SERVING_SIZE_TO_GRAMS, foodServing.getServing_size_to_grams());
        values.put(ServingDBHandler.COLUMN_SERVING_IMAGE_ID, foodServing.getServing_image_id());

        // updating row
        return database.update(ServingDBHandler.TABLE_SERVING, values,
                ServingDBHandler.COLUMN_SERVING_ID + "=?", new String[]{String.valueOf(foodServing.getServing_id())});
    }

    public void removeServing(FoodServing foodServing) {
        database.delete(TABLE_SERVING, ServingDBHandler.COLUMN_SERVING_ID + "=" + foodServing.getServing_id(), null);
    }
}
