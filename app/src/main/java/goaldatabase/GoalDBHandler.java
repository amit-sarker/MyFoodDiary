package goaldatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GoalDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "goal.db";
    private static final int DATABASE_VERSION = 11;

    public static final String TABLE_GOAL = "goal";
    public static final String COLUMN_GOAL_ID = "goalId";
    public static final String COLUMN_GOAL_NAME = "goalName";
    public static final String COLUMN_GOAL_DESCRIPTION = "goalDescription";
    public static final String COLUMN_GOAL_DURATION = "goalDuration";
    public static final String COLUMN_MY_GOAL_STREAK = "goalStreak";
    public static final String COLUMN_GOAL_STATUS = "goalStatus";
    public static final String COLUMN_GOAL_IMAGE = "goalImage";
    public static final String COLUMN_GOAL_COMPLETION = "goalCompletion";
    public static final String COLUMN_GOAL_POINT = "goalPoint";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_GOAL + " (" +
                    COLUMN_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GOAL_NAME + " TEXT, " +
                    COLUMN_GOAL_DESCRIPTION + " TEXT, " +
                    COLUMN_GOAL_DURATION + " INTEGER, " +
                    COLUMN_MY_GOAL_STREAK + " INTEGER, " +
                    COLUMN_GOAL_STATUS + " TEXT, " +
                    COLUMN_GOAL_IMAGE + " TEXT, " +
                    COLUMN_GOAL_COMPLETION + " TEXT, " +
                    COLUMN_GOAL_POINT + " INTEGER " +
                    ")";

    public GoalDBHandler(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
