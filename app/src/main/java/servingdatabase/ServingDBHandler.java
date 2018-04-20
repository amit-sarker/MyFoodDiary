package servingdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ServingDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "serving.db";
    private static final int DATABASE_VERSION = 11;

    public static final String TABLE_SERVING = "serving";
    public static final String COLUMN_SERVING_ID = "servingId";
    public static final String COLUMN_FOOD_NAME = "foodname";
    public static final String COLUMN_FOOD_SERVING_MEASUREMENT = "servingMeasurement";
    public static final String COLUMN_SERVING_SIZE_TO_GRAMS = "servingSizeToGrams";
    public static final String COLUMN_SERVING_IMAGE_ID = "servingImageID";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_SERVING + " (" +
                    COLUMN_SERVING_ID + " INTEGER, " +
                    COLUMN_FOOD_NAME + " TEXT, " +
                    COLUMN_FOOD_SERVING_MEASUREMENT + " TEXT, " +
                    COLUMN_SERVING_SIZE_TO_GRAMS + " TEXT, " +
                    COLUMN_SERVING_IMAGE_ID + " TEXT " +
                    ")";

    public ServingDBHandler(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVING);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }
}
