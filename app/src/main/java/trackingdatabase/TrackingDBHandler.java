package trackingdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrackingDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tracking.db";
    private static final int DATABASE_VERSION = 11;

    public static final String TABLE_TRACKING = "tracking";
    public static final String COLUMN_TRACKING_ID = "trackingId";
    public static final String COLUMN_TRACKING_DATE = "trackingDate";
    public static final String COLUMN_CAL_NEEDED = "calNeeded";
    public static final String COLUMN_CAL_CONSUMED = "calConsumed";
    public static final String COLUMN_CAL_REMAINING = "calRemaining";
    public static final String COLUMN_PROTEIN_NEEDED = "proteinNeeded";
    public static final String COLUMN_PROTEIN_CONSUMED = "proteinConsumed";
    public static final String COLUMN_PROTEIN_REMAINING = "proteinRemaining";
    public static final String COLUMN_FAT_NEEDED = "fatNeeded";
    public static final String COLUMN_FAT_CONSUMED = "fatConsumed";
    public static final String COLUMN_FAT_REMAINING = "fatRemaining";
    public static final String COLUMN_CARBS_NEEDED = "carbsNeeded";
    public static final String COLUMN_CARBS_CONSUMED = "carbsConsumed";
    public static final String COLUMN_CARBS_REMAINING = "carbsRemaining";
    public static final String COLUMN_WATER_CONSUMED = "waterConsumed";
    public static final String COLUMN_GOAL_POINT = "goalPoint";
    public static final String COLUMN_RANK = "rank";


    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TRACKING + " (" +
                    COLUMN_TRACKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TRACKING_DATE + " TEXT, " +
                    COLUMN_CAL_NEEDED + " DOUBLE, " +
                    COLUMN_CAL_CONSUMED + " DOUBLE, " +
                    COLUMN_CAL_REMAINING + " DOUBLE, " +
                    COLUMN_PROTEIN_NEEDED + " DOUBLE, " +
                    COLUMN_PROTEIN_CONSUMED + " DOUBLE, " +
                    COLUMN_PROTEIN_REMAINING + " DOUBLE, " +
                    COLUMN_FAT_NEEDED + " DOUBLE, " +
                    COLUMN_FAT_CONSUMED + " DOUBLE, " +
                    COLUMN_FAT_REMAINING + " DOUBLE, " +
                    COLUMN_CARBS_NEEDED + " DOUBLE, " +
                    COLUMN_CARBS_CONSUMED + " DOUBLE, " +
                    COLUMN_CARBS_REMAINING + " DOUBLE, " +
                    COLUMN_WATER_CONSUMED + " DOUBLE, " +
                    COLUMN_GOAL_POINT + " INTEGER, " +
                    COLUMN_RANK + " INTEGER " +
                    ")";

    public TrackingDBHandler(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKING);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }
}
