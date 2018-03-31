package generalpersondatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PERSON = "person";
    public static final String COLUMN_ID = "personId";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEIGHT = "weight";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PERSON + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_AGE + " TEXT, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_HEIGHT + " TEXT, " +
                    COLUMN_WEIGHT + " TEXT " +
                    ")";

    public PersonDBHandler(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }
}
