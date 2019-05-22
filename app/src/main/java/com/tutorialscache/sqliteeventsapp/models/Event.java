package com.tutorialscache.sqliteeventsapp.models;

import android.util.Log;

public class Event {

    public static final String TABLE_NAME = "events";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_EVENT_DATE = "event_date";
    public static final String COLUMN_VENUE = "venue";
    public static final String COLUMN_DATE_CREATED = "dateCreated";


    public static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " VARCHAR,"
                    + COLUMN_EVENT_DATE + " VARCHAR,"
                    + COLUMN_VENUE + " VARCHAR,"
                    + COLUMN_DATE_CREATED + " VARCHAR"
                    + ")";

    private long id;
    private String title;
    private String eventDate;
    private String venue;
    private String dateCreated;

    public Event() {

    }

    public Event(long id, String title, String eventDate, String venue, String dateCreated) {
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.venue = venue;
        this.dateCreated = dateCreated;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        Log.d("response ","Title: "+title);
        return super.toString();
    }
}

