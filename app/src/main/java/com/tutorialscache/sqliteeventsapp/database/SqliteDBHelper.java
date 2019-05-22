package com.tutorialscache.sqliteeventsapp.database;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tutorialscache.sqliteeventsapp.models.Event;

import java.util.ArrayList;
import java.util.Calendar;

public class SqliteDBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "events_db";


    public SqliteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Event.CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table
        db.execSQL("DROP TABLE IF EXISTS " + Event.TABLE_NAME);

        // Recreate table
        onCreate(db);
    }

    /**
     * Insert event long.
     * @param event the event
     * @return the long created event id
     */
    public Long createEvent(Event event) {

        //  writable database instance
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Event.COLUMN_TITLE, event.getTitle());
        values.put(Event.COLUMN_EVENT_DATE, event.getEventDate());
        values.put(Event.COLUMN_VENUE, event.getVenue());
        values.put(Event.COLUMN_DATE_CREATED, event.getDateCreated());

        //saving data
        long id = db.insert(Event.TABLE_NAME, null, values);

        // close db connection
        db.close();

        return id;
    }

    /**
     * Gets event by id.
     * @param id the id
     * @return the event by id
     */
    public Event getEventByID(long id) {
        // get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Event.TABLE_NAME,
                new String[]{
                        Event.COLUMN_ID,
                        Event.COLUMN_TITLE,
                        Event.COLUMN_EVENT_DATE,
                        Event.COLUMN_VENUE,
                        Event.COLUMN_DATE_CREATED},
                Event.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare event object
        Event event = new Event(
                Long.parseLong(cursor.getString(cursor.getColumnIndex(Event.COLUMN_ID))),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_EVENT_DATE)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_VENUE)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_DATE_CREATED)));

        // close the db connection
        cursor.close();

        return event;
    }


    /**
     * Gets all events.
     * @return the all events
     */
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> noteArrayList = new ArrayList<>();

        // Select All Events Query
        String selectQuery = "SELECT  * FROM "
                + Event.TABLE_NAME
                + " ORDER BY "
                + Event.COLUMN_EVENT_DATE
                + " DESC";

        //Instance of database
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        //looping all rows
         if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex(Event.COLUMN_ID)))); // getting the id
                event.setTitle(cursor.getString(cursor.getColumnIndex(Event.COLUMN_TITLE)));
                event.setEventDate(cursor.getString(cursor.getColumnIndex(Event.COLUMN_EVENT_DATE)));
                event.setVenue(cursor.getString(cursor.getColumnIndex(Event.COLUMN_VENUE)));
                event.setDateCreated(cursor.getString(cursor.getColumnIndex(Event.COLUMN_DATE_CREATED)));
                noteArrayList.add(event); // add to the arrayList
                event.toString();
            } while (cursor.moveToNext());
        }

        db.close();

        return noteArrayList;
    }

    /**
     * Update event int.
     *
     * @param event the event
     * @return the int
     */
    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Event.COLUMN_TITLE, event.getTitle());
        values.put(Event.COLUMN_EVENT_DATE, event.getEventDate());
        values.put(Event.COLUMN_VENUE, event.getVenue());
        // updating event row
        return db.update(Event.TABLE_NAME, values, Event.COLUMN_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
    }

    /**
     * Delete event.
     * @param event the event
     */
    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Event.TABLE_NAME, Event.COLUMN_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
        db.close();
    }
}
