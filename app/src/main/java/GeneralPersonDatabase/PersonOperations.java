package GeneralPersonDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import GeneralPersonDatabase.Person;
import GeneralPersonDatabase.PersonDBHandler;

public class PersonOperations {
    public static final String LOGTAG = "PERSON_MNG_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            PersonDBHandler.COLUMN_ID,
            PersonDBHandler.COLUMN_AGE,
            PersonDBHandler.COLUMN_GENDER,
            PersonDBHandler.COLUMN_HEIGHT,
            PersonDBHandler.COLUMN_WEIGHT
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

    public Person addPerson(Person person){
        ContentValues values  = new ContentValues();
        values.put(PersonDBHandler.COLUMN_AGE, person.getAge());
        values.put(PersonDBHandler.COLUMN_GENDER, person.getGender());
        values.put(PersonDBHandler.COLUMN_HEIGHT, person.getHeight());
        values.put(PersonDBHandler.COLUMN_WEIGHT, person.getWeight());
        long insertid = database.insert(PersonDBHandler.TABLE_PERSON,null, values);
        person.setPersonID(insertid);
        return person;
    }

    // Getting single Person
    public Person getPerson(long id) {

        Cursor cursor = database.query(PersonDBHandler.TABLE_PERSON, allColumns,PersonDBHandler.COLUMN_ID + "=?", new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Person e = new Person(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return person
        return e;
    }

    public List<Person> getAllPersons() {

        Cursor cursor = database.query(PersonDBHandler.TABLE_PERSON, allColumns,null,null,null, null, null);

        List<Person> personList = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Person person = new Person();
                person.setPersonID(cursor.getLong(cursor.getColumnIndex(PersonDBHandler.COLUMN_ID)));
                person.setAge(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_AGE)));
                person.setGender(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_GENDER)));
                person.setHeight(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_HEIGHT)));
                person.setWeight(cursor.getString(cursor.getColumnIndex(PersonDBHandler.COLUMN_WEIGHT)));
                personList.add(person);
            }
        }
        // return All Employees
        return personList;
    }

    // Updating Person
    public int updatePerson(Person person) {

        ContentValues values = new ContentValues();
        values.put(PersonDBHandler.COLUMN_AGE, person.getAge());
        values.put(PersonDBHandler.COLUMN_GENDER, person.getGender());
        values.put(PersonDBHandler.COLUMN_HEIGHT, person.getHeight());
        values.put(PersonDBHandler.COLUMN_WEIGHT, person.getWeight());

        // updating row
        return database.update(PersonDBHandler.TABLE_PERSON, values,
                PersonDBHandler.COLUMN_ID + "=?", new String[] { String.valueOf(person.getPersonID())});
    }

    // Deleting Person
    public void removePerson(Person person) {

        database.delete(PersonDBHandler.TABLE_PERSON,PersonDBHandler.COLUMN_ID + "=" + person.getPersonID(),null);
    }
}
