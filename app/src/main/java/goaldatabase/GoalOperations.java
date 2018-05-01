package goaldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import static goaldatabase.GoalDBHandler.TABLE_GOAL;

public class GoalOperations {

    public static final String LOGTAG = "GOAL_MNG_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            GoalDBHandler.COLUMN_GOAL_ID,
            GoalDBHandler.COLUMN_GOAL_NAME,
            GoalDBHandler.COLUMN_GOAL_DESCRIPTION,
            GoalDBHandler.COLUMN_GOAL_DURATION,
            GoalDBHandler.COLUMN_MY_GOAL_STREAK,
            GoalDBHandler.COLUMN_GOAL_STATUS,
            GoalDBHandler.COLUMN_GOAL_IMAGE,
            GoalDBHandler.COLUMN_GOAL_COMPLETION,
            GoalDBHandler.COLUMN_GOAL_POINT
    };

    public GoalOperations(Context context){
        dbhandler = new GoalDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public long getRowCount() {
        SQLiteDatabase db = dbhandler.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_GOAL);
        return count;
    }

    public Goal addGoal(Goal goal){
        ContentValues values  = new ContentValues();
        values.put(GoalDBHandler.COLUMN_GOAL_NAME, goal.getGoal_name());
        values.put(GoalDBHandler.COLUMN_GOAL_DESCRIPTION, goal.getGoal_description());
        values.put(GoalDBHandler.COLUMN_GOAL_DURATION, goal.getGoal_duration());
        values.put(GoalDBHandler.COLUMN_MY_GOAL_STREAK, goal.getMy_goal_streak());
        values.put(GoalDBHandler.COLUMN_GOAL_STATUS, goal.getGoal_status());
        values.put(GoalDBHandler.COLUMN_GOAL_IMAGE, goal.getGoal_image());
        values.put(GoalDBHandler.COLUMN_GOAL_COMPLETION, goal.getGoal_completion());
        values.put(GoalDBHandler.COLUMN_GOAL_POINT, goal.getGoal_point());

        long insertid = database.insert(TABLE_GOAL,null, values);
        goal.setGoal_id(insertid);
        return goal;
    }

    public Goal getGoal(long id) {
        Cursor cursor = database.query(TABLE_GOAL, allColumns,GoalDBHandler.COLUMN_GOAL_ID + "=?", new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Goal e = new Goal(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                Long.parseLong(cursor.getString(3)), Long.parseLong(cursor.getString(4)),
                cursor.getString(5), cursor.getString(6), cursor.getString(7), Long.parseLong(cursor.getString(8)));
        return e;
    }

    public List<Goal> getAllGoals() {

        Cursor cursor = database.query(TABLE_GOAL, allColumns,null,null,null, null, null);

        List<Goal> goalList = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()) {
                Goal goal = new Goal();
                goal.setGoal_id(cursor.getLong(cursor.getColumnIndex(GoalDBHandler.COLUMN_GOAL_ID)));
                goal.setGoal_name(cursor.getString(cursor.getColumnIndex(GoalDBHandler.COLUMN_GOAL_NAME)));
                goal.setGoal_description(cursor.getString(cursor.getColumnIndex(GoalDBHandler.COLUMN_GOAL_DESCRIPTION)));
                goal.setGoal_duration(cursor.getLong(cursor.getColumnIndex(GoalDBHandler.COLUMN_GOAL_DURATION)));
                goal.setMy_goal_streak(cursor.getLong(cursor.getColumnIndex(GoalDBHandler.COLUMN_MY_GOAL_STREAK)));
                goal.setGoal_status(cursor.getString(cursor.getColumnIndex(GoalDBHandler.COLUMN_GOAL_STATUS)));
                goal.setGoal_image(cursor.getString(cursor.getColumnIndex(GoalDBHandler.COLUMN_GOAL_IMAGE)));
                goal.setGoal_completion(cursor.getString(cursor.getColumnIndex(GoalDBHandler.COLUMN_GOAL_COMPLETION)));
                goal.setGoal_point(cursor.getLong(cursor.getColumnIndex(GoalDBHandler.COLUMN_GOAL_POINT)));

                goalList.add(goal);
            }
        }
        return goalList;
    }

    public int updateGoal(Goal goal) {

        ContentValues values  = new ContentValues();
        values.put(GoalDBHandler.COLUMN_GOAL_NAME, goal.getGoal_name());
        values.put(GoalDBHandler.COLUMN_GOAL_DESCRIPTION, goal.getGoal_description());
        values.put(GoalDBHandler.COLUMN_GOAL_DURATION, goal.getGoal_duration());
        values.put(GoalDBHandler.COLUMN_MY_GOAL_STREAK, goal.getMy_goal_streak());
        values.put(GoalDBHandler.COLUMN_GOAL_STATUS, goal.getGoal_status());
        values.put(GoalDBHandler.COLUMN_GOAL_IMAGE, goal.getGoal_image());
        values.put(GoalDBHandler.COLUMN_GOAL_COMPLETION, goal.getGoal_completion());
        values.put(GoalDBHandler.COLUMN_GOAL_POINT, goal.getGoal_point());

        // updating row
        return database.update(TABLE_GOAL, values,
                GoalDBHandler.COLUMN_GOAL_ID + "=?", new String[] { String.valueOf(goal.getGoal_id())});
    }

    public void deleteAllGoalData() {
        database.execSQL("delete from "+ TABLE_GOAL);
        database.execSQL("DELETE FROM sqlite_sequence WHERE name = '"+TABLE_GOAL+"' ");
    }

    public void deleteSingleRow(long id) {
        database.delete(TABLE_GOAL,GoalDBHandler.COLUMN_GOAL_ID + "=" + id,null);
    }
}
