package trackingdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static trackingdatabase.TrackingDBHandler.TABLE_TRACKING;

public class TrackingOperations {
    public static final String LOGTAG = "TRACKING_MNG_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            TrackingDBHandler.COLUMN_TRACKING_ID,
            TrackingDBHandler.COLUMN_TRACKING_DATE,
            TrackingDBHandler.COLUMN_CAL_NEEDED,
            TrackingDBHandler.COLUMN_CAL_CONSUMED,
            TrackingDBHandler.COLUMN_CAL_REMAINING,
            TrackingDBHandler.COLUMN_PROTEIN_NEEDED,
            TrackingDBHandler.COLUMN_PROTEIN_CONSUMED,
            TrackingDBHandler.COLUMN_PROTEIN_REMAINING,
            TrackingDBHandler.COLUMN_FAT_NEEDED,
            TrackingDBHandler.COLUMN_FAT_CONSUMED,
            TrackingDBHandler.COLUMN_FAT_REMAINING,
            TrackingDBHandler.COLUMN_CARBS_NEEDED,
            TrackingDBHandler.COLUMN_CARBS_CONSUMED,
            TrackingDBHandler.COLUMN_CARBS_REMAINING
    };

    public TrackingOperations(Context context){
        dbhandler = new TrackingDBHandler(context);
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
        long count = DatabaseUtils.queryNumEntries(db, TABLE_TRACKING);
        return count;
    }

    public CalorieTracking addTrackingData(CalorieTracking trackingData){
        ContentValues values  = new ContentValues();

        values.put(TrackingDBHandler.COLUMN_TRACKING_DATE, trackingData.getDate());
        values.put(TrackingDBHandler.COLUMN_CAL_NEEDED, trackingData.getCal_needed());
        values.put(TrackingDBHandler.COLUMN_CAL_CONSUMED, trackingData.getCal_consumed());
        values.put(TrackingDBHandler.COLUMN_CAL_REMAINING, trackingData.getCal_remaining());

        values.put(TrackingDBHandler.COLUMN_PROTEIN_NEEDED, trackingData.getProtein_needed());
        values.put(TrackingDBHandler.COLUMN_PROTEIN_CONSUMED, trackingData.getProtein_consumed());
        values.put(TrackingDBHandler.COLUMN_PROTEIN_REMAINING, trackingData.getProtein_remaining());

        values.put(TrackingDBHandler.COLUMN_FAT_NEEDED, trackingData.getFat_needed());
        values.put(TrackingDBHandler.COLUMN_FAT_CONSUMED, trackingData.getFat_consumed());
        values.put(TrackingDBHandler.COLUMN_FAT_REMAINING, trackingData.getFat_remaining());

        values.put(TrackingDBHandler.COLUMN_CARBS_NEEDED, trackingData.getCarbs_needed());
        values.put(TrackingDBHandler.COLUMN_CARBS_CONSUMED, trackingData.getCarbs_consumed());
        values.put(TrackingDBHandler.COLUMN_CARBS_REMAINING, trackingData.getCarbs_remaining());

        long insertid = database.insert(TABLE_TRACKING,null, values);
        trackingData.setCalorie_tracking_id(insertid);
        return trackingData;
    }

    public CalorieTracking getTracking(long id) {
        Cursor cursor = database.query(TrackingDBHandler.TABLE_TRACKING, allColumns,TrackingDBHandler.COLUMN_TRACKING_ID + "=?", new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CalorieTracking e = new CalorieTracking(Long.parseLong(cursor.getString(0)), cursor.getString(1), Double.parseDouble(cursor.getString(2)),
                Double.parseDouble(cursor.getString(3)), Double.parseDouble(cursor.getString(4)), Double.parseDouble(cursor.getString(5)),
                Double.parseDouble(cursor.getString(6)), Double.parseDouble(cursor.getString(7)), Double.parseDouble(cursor.getString(8)),
                Double.parseDouble(cursor.getString(9)), Double.parseDouble(cursor.getString(10)), Double.parseDouble(cursor.getString(11)),
                Double.parseDouble(cursor.getString(12)), Double.parseDouble(cursor.getString(13)));

        return e;
    }


    public int updateTracking(CalorieTracking newTrackingData) {

        ContentValues values = new ContentValues();
        values.put(TrackingDBHandler.COLUMN_TRACKING_DATE, newTrackingData.getDate());
        values.put(TrackingDBHandler.COLUMN_CAL_NEEDED, newTrackingData.getCal_needed());
        values.put(TrackingDBHandler.COLUMN_CAL_CONSUMED, newTrackingData.getCal_consumed());
        values.put(TrackingDBHandler.COLUMN_CAL_REMAINING, newTrackingData.getCal_remaining());

        values.put(TrackingDBHandler.COLUMN_PROTEIN_NEEDED, newTrackingData.getProtein_needed());
        values.put(TrackingDBHandler.COLUMN_PROTEIN_CONSUMED, newTrackingData.getProtein_consumed());
        values.put(TrackingDBHandler.COLUMN_PROTEIN_REMAINING, newTrackingData.getProtein_remaining());

        values.put(TrackingDBHandler.COLUMN_FAT_NEEDED, newTrackingData.getFat_needed());
        values.put(TrackingDBHandler.COLUMN_FAT_CONSUMED, newTrackingData.getFat_consumed());
        values.put(TrackingDBHandler.COLUMN_FAT_REMAINING, newTrackingData.getFat_remaining());

        values.put(TrackingDBHandler.COLUMN_CARBS_NEEDED, newTrackingData.getCarbs_needed());
        values.put(TrackingDBHandler.COLUMN_CARBS_CONSUMED, newTrackingData.getCarbs_consumed());
        values.put(TrackingDBHandler.COLUMN_CARBS_REMAINING, newTrackingData.getCarbs_remaining());

        // updating row
        return database.update(TABLE_TRACKING, values,
                TrackingDBHandler.COLUMN_TRACKING_ID + "=?", new String[] { String.valueOf(newTrackingData.getCalorie_tracking_id())});
    }

    public void removeTracking(CalorieTracking trackingData) {
        database.delete(TABLE_TRACKING,TrackingDBHandler.COLUMN_TRACKING_ID + "=" + trackingData.getCalorie_tracking_id(),null);
    }
}
