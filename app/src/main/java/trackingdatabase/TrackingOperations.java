package trackingdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    private static final String[] distColumns = {
            TrackingDBHandler.COLUMN_TRACKING_ID,
            TrackingDBHandler.COLUMN_TRACKING_DATE,
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

    public CalorieTracking addTrackingData(CalorieTracking trackingData){
        ContentValues values  = new ContentValues();
        values.put(TrackingDBHandler.COLUMN_TRACKING_ID, trackingData.getCalorie_tracking_id());
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

        database.insert(TrackingDBHandler.TABLE_TRACKING,null, values);
        return trackingData;
    }

    public List<CalorieTracking> getTrackingData(long id) {

        Cursor cursor = database.query(TrackingDBHandler.TABLE_TRACKING, allColumns,null,null,null, null, null);

        List<CalorieTracking> trackingDataList = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                if(cursor.getLong(cursor.getColumnIndex(TrackingDBHandler.COLUMN_TRACKING_ID)) == id) {
                    CalorieTracking trackingData = new CalorieTracking();
                    trackingData.setCalorie_tracking_id(cursor.getLong(cursor.getColumnIndex(TrackingDBHandler.COLUMN_TRACKING_ID)));
                    trackingData.setDate(cursor.getString(cursor.getColumnIndex(TrackingDBHandler.COLUMN_TRACKING_DATE)));
                    trackingData.setCal_needed(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_CAL_NEEDED)));
                    trackingData.setCal_consumed(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_CAL_CONSUMED)));
                    trackingData.setCal_remaining(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_CAL_REMAINING)));

                    trackingData.setProtein_needed(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_PROTEIN_NEEDED)));
                    trackingData.setProtein_consumed(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_PROTEIN_CONSUMED)));
                    trackingData.setProtein_remaining(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_PROTEIN_REMAINING)));

                    trackingData.setFat_needed(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_FAT_NEEDED)));
                    trackingData.setFat_consumed(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_FAT_CONSUMED)));
                    trackingData.setFat_remaining(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_FAT_REMAINING)));

                    trackingData.setCarbs_needed(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_CARBS_NEEDED)));
                    trackingData.setCarbs_consumed(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_CARBS_CONSUMED)));
                    trackingData.setCarbs_remaining(cursor.getDouble(cursor.getColumnIndex(TrackingDBHandler.COLUMN_CARBS_REMAINING)));

                    trackingDataList.add(trackingData);
                }
            }
        }
        //System.err.println("FFFFFFFFFFFFFFFFFFFFFFF  " + trackingDataList.size());
        return trackingDataList;
    }

    public void removeTracking(CalorieTracking trackingData) {
        database.delete(TrackingDBHandler.TABLE_TRACKING,TrackingDBHandler.COLUMN_TRACKING_ID + "=" + trackingData.getCalorie_tracking_id(),null);
    }
}
