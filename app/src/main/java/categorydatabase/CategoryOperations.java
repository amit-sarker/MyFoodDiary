package categorydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static categorydatabase.CategoryDBHandler.TABLE_CATEGORY;

public class CategoryOperations {

    public static final String LOGTAG = "CATEGORY_MNG_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            CategoryDBHandler.COLUMN_CATEGORY_ID,
            CategoryDBHandler.COLUMN_CATEGORY_NAME,
            CategoryDBHandler.COLUMN_FOOD_ID,
            CategoryDBHandler.COLUMN_FOOD_NAME,
            CategoryDBHandler.COLUMN_FOOD_IMAGE,
            CategoryDBHandler.COLUMN_CATEGORY_IMAGE
    };

    private static final String[] distColumns = {
            CategoryDBHandler.COLUMN_CATEGORY_ID,
            CategoryDBHandler.COLUMN_CATEGORY_NAME,
            CategoryDBHandler.COLUMN_CATEGORY_IMAGE
    };

    public CategoryOperations(Context context){
        dbhandler = new CategoryDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }


    public FoodCategory addCategory(FoodCategory foodCategory){
        ContentValues values  = new ContentValues();
        values.put(CategoryDBHandler.COLUMN_CATEGORY_ID, foodCategory.getCategoryID());
        values.put(CategoryDBHandler.COLUMN_CATEGORY_NAME, foodCategory.getCategoryName());
        values.put(CategoryDBHandler.COLUMN_FOOD_ID, foodCategory.getFoodID());
        values.put(CategoryDBHandler.COLUMN_FOOD_NAME, foodCategory.getFoodName());
        values.put(CategoryDBHandler.COLUMN_FOOD_IMAGE, foodCategory.getFoodImage());
        values.put(CategoryDBHandler.COLUMN_CATEGORY_IMAGE, foodCategory.getCategoryImage());
        database.insert(TABLE_CATEGORY,null, values);
        return foodCategory;
    }


    public List<FoodCategory> getDistinctFoodCategory() {

        Cursor cursor = database.query(true, TABLE_CATEGORY, distColumns, null, null, null, null, null, null);

        List<FoodCategory> foodCategoryList = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                    FoodCategory foodCategory = new FoodCategory();
                    foodCategory.setCategoryID(cursor.getLong(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_ID)));
                    foodCategory.setCategoryName(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_NAME)));
                    foodCategory.setCategoryImage(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_IMAGE)));
                    foodCategoryList.add(foodCategory);
            }
        }
        return foodCategoryList;
    }



    public List<FoodCategory> getFoodCategoryByName(String categoryName) {

        Cursor cursor = database.query(TABLE_CATEGORY, allColumns,null,null,null, null, null);

        List<FoodCategory> foodCategoryList = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                if(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_NAME)).equals(categoryName)) {
                    FoodCategory foodCategory = new FoodCategory();
                    foodCategory.setCategoryID(cursor.getLong(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_ID)));
                    foodCategory.setCategoryName(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_NAME)));
                    foodCategory.setFoodID(cursor.getLong(cursor.getColumnIndex(CategoryDBHandler.COLUMN_FOOD_ID)));
                    foodCategory.setFoodName(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_FOOD_NAME)));
                    foodCategory.setFoodImage(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_FOOD_IMAGE)));
                    foodCategory.setCategoryImage(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_IMAGE)));
                    foodCategoryList.add(foodCategory);
                }
            }
        }
        System.err.println("ggggggggggggggggggggggggggggggggg  " + foodCategoryList.size());
        // return All Employees
        return foodCategoryList;
    }


    public List<FoodCategory> getFoodCategory(long id) {

        Cursor cursor = database.query(TABLE_CATEGORY, allColumns,null,null,null, null, null);

        List<FoodCategory> foodCategoryList = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                if(cursor.getLong(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_ID)) == id) {
                    FoodCategory foodCategory = new FoodCategory();
                    foodCategory.setCategoryID(cursor.getLong(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_ID)));
                    foodCategory.setCategoryName(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_NAME)));
                    foodCategory.setFoodID(cursor.getLong(cursor.getColumnIndex(CategoryDBHandler.COLUMN_FOOD_ID)));
                    foodCategory.setFoodName(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_FOOD_NAME)));
                    foodCategory.setFoodImage(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_FOOD_IMAGE)));
                    foodCategory.setCategoryImage(cursor.getString(cursor.getColumnIndex(CategoryDBHandler.COLUMN_CATEGORY_IMAGE)));
                    foodCategoryList.add(foodCategory);
                }
            }
        }
        //System.err.println("FFFFFFFFFFFFFFFFFFFFFFF  " + foodCategoryList.size());
        // return All Employees
        return foodCategoryList;
    }

    public long getRowCount() {
        SQLiteDatabase db = dbhandler.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_CATEGORY);
        return count;
    }

    // Updating Person
    /*public int updateFoodCategory(FoodCategory foodCategory) {

        ContentValues values = new ContentValues();
        values.put(CategoryDBHandler.COLUMN_CATEGORY_NAME, foodCategory.getCategoryName());
        values.put(CategoryDBHandler.COLUMN_FOOD_ID, foodCategory.getFoodID());
        values.put(CategoryDBHandler.COLUMN_FOOD_NAME, foodCategory.getFoodName());
        values.put(CategoryDBHandler.COLUMN_FOOD_IMAGE, foodCategory.getFoodImage());
        values.put(CategoryDBHandler.COLUMN_CATEGORY_IMAGE, foodCategory.getCategoryImage());

        // updating row
        return database.update(CategoryDBHandler.TABLE_CATEGORY, values,
                CategoryDBHandler.COLUMN_CATEGORY_ID + "=?", new String[] { String.valueOf(foodCategory.getCategoryID())});
    }*/

    // Deleting Person
    public void removeCategory(FoodCategory foodCategory) {
        database.delete(TABLE_CATEGORY,CategoryDBHandler.COLUMN_CATEGORY_ID + "=" + foodCategory.getCategoryID(),null);
    }

}
