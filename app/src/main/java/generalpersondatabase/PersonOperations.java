package generalpersondatabase;

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
import static generalpersondatabase.PersonDBHandler.TABLE_PERSON;

public class PersonOperations {
    public static final String LOGTAG = "PERSON_MNG_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            PersonDBHandler.COLUMN_ID,
            PersonDBHandler.COLUMN_AGE,
            PersonDBHandler.COLUMN_GENDER,
            PersonDBHandler.COLUMN_HEIGHT,
            PersonDBHandler.COLUMN_WEIGHT,
            PersonDBHandler.COLUMN_ACTIVITY_LEVEL,
            PersonDBHandler.COLUMN_TARGET_WEIGHT,
            PersonDBHandler.COLUMN_BMR_WITHOUT_ACTIVITY,
            PersonDBHandler.COLUMN_BMR_WITH_ACTIVITY,
            PersonDBHandler.COLUMN_WEIGHT_UPDATE_AMOUNT
    };

    public PersonOperations(Context context){
        dbhandler = new PersonDBHandler(context);
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
        long count = DatabaseUtils.queryNumEntries(db, TABLE_PERSON);
        return count;
    }

    public Person addPerson(Person person){
        ContentValues values  = new ContentValues();
        values.put(PersonDBHandler.COLUMN_AGE, person.getAge());
        values.put(PersonDBHandler.COLUMN_GENDER, person.getGender());
        values.put(PersonDBHandler.COLUMN_HEIGHT, person.getHeight());
        values.put(PersonDBHandler.COLUMN_WEIGHT, person.getWeight());
        values.put(PersonDBHandler.COLUMN_ACTIVITY_LEVEL, person.getActivityLevel());
        values.put(PersonDBHandler.COLUMN_TARGET_WEIGHT, person.getTargetWeight());
        values.put(PersonDBHandler.COLUMN_BMR_WITHOUT_ACTIVITY, person.getBMRWithoutActivity());
        values.put(PersonDBHandler.COLUMN_BMR_WITH_ACTIVITY, person.getBMRWithActivity());
        values.put(PersonDBHandler.COLUMN_WEIGHT_UPDATE_AMOUNT, person.getWeightUpdateAmount());
        long insertid = database.insert(TABLE_PERSON,null, values);
        person.setPersonID(insertid);
        return person;
    }

    // Getting single Person
    public Person getPerson(long id) {
        Cursor cursor = database.query(TABLE_PERSON, allColumns,PersonDBHandler.COLUMN_ID + "=?", new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Person e = new Person(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                Long.parseLong(cursor.getString(5)), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
        // return person
        return e;
    }

    public List<Person> getAllPersons() {

        Cursor cursor = database.query(TABLE_PERSON, allColumns,null,null,null, null, null);

        List<Person> personList = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()) {
                Person person = new Person();
                person.setPersonID(cursor.getLong(cursor.getColumnIndex(PersonDBHandler.COLUMN_ID)));
                person.setAge(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_AGE)));
                person.setGender(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_GENDER)));
                person.setHeight(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_HEIGHT)));
                person.setWeight(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_WEIGHT)));
                person.setActivityLevel(cursor.getLong(cursor.getColumnIndex(PersonDBHandler.COLUMN_ACTIVITY_LEVEL)));
                person.setTargetWeight(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_TARGET_WEIGHT)));
                person.setBMRWithoutActivity(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_BMR_WITHOUT_ACTIVITY)));
                person.setBMRWithActivity(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_BMR_WITH_ACTIVITY)));
                person.setWeightUpdateAmount(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_WEIGHT_UPDATE_AMOUNT)));
                personList.add(person);
            }
        }
        // return All Employees
        return personList;
    }

    public List<Double> getTotalUpdatedweight() {
        Cursor cursor = database.query(TABLE_PERSON, new String[]{PersonDBHandler.COLUMN_WEIGHT_UPDATE_AMOUNT},null,null,null, null, null);
        List<Double> updatedWeightList = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                updatedWeightList.add(Double.valueOf(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_WEIGHT_UPDATE_AMOUNT))));
            }
        }
        return updatedWeightList;
    }

    // Updating Person
    public int updatePerson(Person person) {

        ContentValues values = new ContentValues();
        values.put(PersonDBHandler.COLUMN_AGE, person.getAge());
        values.put(PersonDBHandler.COLUMN_GENDER, person.getGender());
        values.put(PersonDBHandler.COLUMN_HEIGHT, person.getHeight());
        values.put(PersonDBHandler.COLUMN_WEIGHT, person.getWeight());
        values.put(PersonDBHandler.COLUMN_ACTIVITY_LEVEL, person.getActivityLevel());
        values.put(PersonDBHandler.COLUMN_TARGET_WEIGHT, person.getTargetWeight());
        values.put(PersonDBHandler.COLUMN_BMR_WITHOUT_ACTIVITY, person.getBMRWithoutActivity());
        values.put(PersonDBHandler.COLUMN_BMR_WITH_ACTIVITY, person.getBMRWithActivity());
        values.put(PersonDBHandler.COLUMN_WEIGHT_UPDATE_AMOUNT, person.getWeightUpdateAmount());

        // updating row
        return database.update(TABLE_PERSON, values,
                PersonDBHandler.COLUMN_ID + "=?", new String[] { String.valueOf(person.getPersonID())});
    }

    public void deleteAllPersonData() {
        database.execSQL("delete from "+ TABLE_PERSON);
        //database.delete(SQLITE_SEQUENCE, NAME, TABLE_PERSON);
        database.execSQL("DELETE FROM sqlite_sequence WHERE name = '"+TABLE_PERSON+"' ");
        /*delete from your_table;
        delete from sqlite_sequence where name='your_table';*/
    }

    // Deleting Person
    public void removePerson(long id) {
        database.delete(TABLE_PERSON,PersonDBHandler.COLUMN_ID + "=" + id,null);
    }
}
