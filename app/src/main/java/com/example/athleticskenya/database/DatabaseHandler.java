package com.example.athleticskenya.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.athleticskenya.Utils.Cipher;
import com.example.athleticskenya.getterClasses.Events;
import com.example.athleticskenya.getterClasses.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Athletics_kenya";
    // Table names
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_PROFILE = "profile";

    // Events Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EVENT_ID = "event_id";
    private static final String KEY_EVENTNAME = "title";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_MORE_DETAILS = "more_details";

    // Profile Table Columns names
    private static final String USER_ID = "id";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_PHONE = "phone";
    private static final String USER_EMAIL = "email";
    private static final String USER_D_O_B = "date_of_birth";
    private static final String USER_RACE_TYPE = "race_type";
    private static final String USER_IMAGE = "image";
    private static final String USER_CLASS = "class";
    private static final String USER_STATUS = "status";
    private static final String USER_ARCHIVE = "archive";
    private static final String USER_CONTACT = "contact";
    private static final String USER_WEIGHT = "weight";
    private static final String USER_HEIGHT = "height";
    private static final String USER_LOCATION = "location";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_EVENTS
                +"(" + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_EVENT_ID + " INTEGER, "
                + KEY_EVENTNAME + " TEXT, "
                + KEY_IMAGE + " VARCHAR, "
                + KEY_LOCATION + " VARCHAR, "
                + KEY_DATE + " VARCHAR, "
                + KEY_TIME + " VARCHAR, "
                + KEY_DETAILS + " VARCHAR, "
                + KEY_MORE_DETAILS + " VARCHAR " + ");";

        db.execSQL(sql);

        String sql_profile = "CREATE TABLE " + TABLE_PROFILE
                +"(" + USER_ID + " INTEGER PRIMARY KEY, "
                + USER_FIRST_NAME + " TEXT, "
                + USER_LAST_NAME + " TEXT, "
                + USER_PHONE + " VARCHAR, "
                + USER_EMAIL + " VARCHAR, "
                + USER_D_O_B + " VARCHAR, "
                + USER_RACE_TYPE + " VARCHAR, "
                + USER_IMAGE + " VARCHAR, "
                + USER_CLASS + " INTEGER, "
                + USER_STATUS + " INTEGER, "
                + USER_ARCHIVE + " INTEGER, "
                + USER_CONTACT + " VARCHAR, "
                + USER_WEIGHT + " VARCHAR, "
                + USER_HEIGHT + " VARCHAR, "
                + USER_LOCATION + " VARCHAR " + ");";

        db.execSQL(sql_profile);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        // Create tables again
        onCreate(db);
    }
    /**
     * Storing events in database
     * */
    public void addEvents(String id, String title,String image, String location, String date, String time, String details, String more_details) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_ID, id);
        values.put(KEY_EVENTNAME, title);
        values.put(KEY_IMAGE, image);
        values.put(KEY_LOCATION, location);
        values.put(KEY_DATE, date);
        values.put(KEY_TIME, time);
        values.put(KEY_DETAILS, details);
        values.put(KEY_MORE_DETAILS, more_details);
        // Inserting Row
        db.insert(TABLE_EVENTS, null, values);
        db.close(); // Closing database connection
       // return true;
    }

    /**
     *method for storing the user to SQLite
     */
    public void addUser(int id, String first_name, String last_name, String phone, String email, String d_o_b, String race_type, String image, int class1,
                        int status, int archive, String contact, String weight, String height, String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, id);
        values.put(USER_FIRST_NAME, first_name);
        values.put(USER_LAST_NAME, last_name);
        values.put(USER_PHONE, phone);
        values.put(USER_EMAIL, email);
        values.put(USER_D_O_B, d_o_b);
        values.put(USER_RACE_TYPE, race_type);
        values.put(USER_IMAGE, image);
        values.put(USER_CLASS, class1);
        values.put(USER_STATUS, status);
        values.put(USER_ARCHIVE, archive);
        values.put(USER_CONTACT, contact);
        values.put(USER_WEIGHT, weight);
        values.put(USER_HEIGHT, height);
        values.put(USER_LOCATION, location);
        // Inserting Row
        db.insert(TABLE_PROFILE, null, values);
        db.close(); // Closing database connection
        // return true;
    }

    /**
     * Getting events data from database
     * */
    public Cursor getSqliteEvents(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_EVENTS + " ORDER BY " + KEY_DATE + " DESC";
        return db.rawQuery(sql, null);

        /*private void loadSQLiteEvents() {
            Cursor cursor = db.getSqliteEvents();
            if (cursor.moveToFirst()) {
                do {
                    Events events = new Events(
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_EVENTNAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_IMAGE)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LOCATION)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_DATE)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TIME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_DETAILS)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_MORE_DETAILS))

                    );
                    eventsList.add(events);
                } while (cursor.moveToNext());
            }

            eventsAdapter = new EventsAdapter(AthleteActivity.this, eventsList);
            recyclerView.setAdapter(eventsAdapter);
        }*/
    }

    public List<Events> getSQLiteEvents(){
        List<Events> eventsList = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_EVENTS + " ORDER BY " + KEY_DATE + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Events events = new Events();
                events.setEventname(Cipher.decrypt(cursor.getString(cursor.getColumnIndex(KEY_EVENTNAME))));
                events.setImage(Cipher.decrypt(cursor.getString(cursor.getColumnIndex(KEY_IMAGE))));
                events.setLocation(Cipher.decrypt(cursor.getString(cursor.getColumnIndex(KEY_LOCATION))));
                events.setDate(Cipher.decrypt(cursor.getString(cursor.getColumnIndex(KEY_DATE))));
                events.setTime(Cipher.decrypt(cursor.getString(cursor.getColumnIndex(KEY_TIME))));
                events.setDetails(Cipher.decrypt(cursor.getString(cursor.getColumnIndex(KEY_DETAILS))));
                events.setMore_details(Cipher.decrypt(cursor.getString(cursor.getColumnIndex(KEY_MORE_DETAILS))));
                eventsList.add(events);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return eventsList;

        //usage
       /* private void loadSQLiteEvents() {

            eventsList.addAll(db.getSQLiteEvents());
            eventsAdapter = new EventsAdapter(AthleteActivity.this, eventsList);
            recyclerView.setAdapter(eventsAdapter);
        }*/
    }

    /**
     *
     * @param id get user by id
     * @return return user
     */
    public User getUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_PROFILE + " WHERE " + USER_ID + " = '"+ id + "'";
        Cursor cursor = db.rawQuery(sql, null);

        if ( cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        User user = new User(
                cursor.getInt(cursor.getColumnIndex(USER_ID)),
                cursor.getString(cursor.getColumnIndex(USER_FIRST_NAME)),
                cursor.getString(cursor.getColumnIndex(USER_LAST_NAME)),
                cursor.getString(cursor.getColumnIndex(USER_PHONE)),
                cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                cursor.getString(cursor.getColumnIndex(USER_D_O_B)),
                cursor.getString(cursor.getColumnIndex(USER_RACE_TYPE)),
                cursor.getString(cursor.getColumnIndex(USER_IMAGE)),
                cursor.getInt(cursor.getColumnIndex(USER_CLASS)),
                cursor.getInt(cursor.getColumnIndex(USER_STATUS)),
                cursor.getInt(cursor.getColumnIndex(USER_ARCHIVE)),
                cursor.getString(cursor.getColumnIndex(USER_CONTACT)),
                cursor.getString(cursor.getColumnIndex(USER_HEIGHT)),
                cursor.getString(cursor.getColumnIndex(USER_WEIGHT)),
                cursor.getString(cursor.getColumnIndex(USER_LOCATION))
        );

        cursor.close();
        db.close();

        return user;

    }

    /**
     * update user by id
     */
    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_FIRST_NAME, user.getFirstname());
        values.put(USER_LAST_NAME, user.getLastname());
        values.put(USER_PHONE, user.getPhone());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_D_O_B, user.getDate_of_birth());
        values.put(USER_RACE_TYPE, user.getRace_type());
        values.put(USER_IMAGE, user.getImage());
        values.put(USER_HEIGHT, user.getHeight());
        values.put(USER_WEIGHT, user.getWeight());
        values.put(USER_LOCATION, user.getLocation());

        db.update(TABLE_PROFILE, values, USER_ID + " = ? ", new String[]{String.valueOf(user.getId())});

        db.close();
    }

    /**
     * Checking if an event exists in the SQLite database using its unique id gotten from the server
     **/
    public boolean checkIfEventsExists(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String SqlQuery = "SELECT " + KEY_EVENT_ID + " FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENT_ID + " = '"+ id + "'";
        cursor = db.rawQuery(SqlQuery, null);

        boolean exists = false;
        if (cursor.moveToFirst()){
            exists = true;
        }
        cursor.close();
        db.close();
        return exists;
    }

    /**
     * get the total number of rows in events table
     */
    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_PROFILE);
    }

    public int getCartCount(){
        String query = " SELECT * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public void deleteEvent(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_EVENT_ID + " = ? ", new String[]{KEY_EVENT_ID});
        db.close();

        /*private void deleteEvent(int position){
            List<Events> eventsList;//using int
            db.deleteEvent(eventsList.get(position));

            adapter.notifyItemRemoved(position);
        }*/

    }

    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILE, USER_ID + " = ? ", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * Re create database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_EVENTS, null, null);
        db.delete(TABLE_PROFILE, null, null);
        db.close();
    }

}
