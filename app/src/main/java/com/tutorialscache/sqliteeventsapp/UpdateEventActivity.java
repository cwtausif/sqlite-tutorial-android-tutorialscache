package com.tutorialscache.sqliteeventsapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tutorialscache.sqliteeventsapp.database.SqliteDBHelper;
import com.tutorialscache.sqliteeventsapp.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateEventActivity extends AppCompatActivity implements View.OnClickListener {

private static final String TAG = "UpdateEventActivity";
private static Date eventDate;
private String startDateStr;
private EditText titleEditText;
private EditText eventDateET;
private EditText venueEditText;
private Button createEventButton;
private Calendar myCalendar;
private Event event;
private SqliteDBHelper sqliteDBHelper;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sqliteDBHelper = new SqliteDBHelper(UpdateEventActivity.this);

        event = sqliteDBHelper.getEventByID(getIntent().getExtras().getLong("id"));
        getSupportActionBar().setTitle("Update Event");


        titleEditText = findViewById(R.id.titleET);
        titleEditText.setText(event.getTitle());
        eventDateET = findViewById(R.id.eventDateET);
        eventDateET.setText(event.getEventDate());
        venueEditText = findViewById(R.id.venueET);
        venueEditText.setText(event.getVenue());
        createEventButton = findViewById(R.id.createEventButton);

        createEventButton.setOnClickListener(this);
        eventDateET.setOnClickListener(this);
        createEventButton.setOnClickListener(this);
        createEventButton.setText("Save");
        }

/**
 * Called when a view has been clicked.
 * @param v The view that was clicked.
 */
@Override
public void onClick(View v) {
        if (v == eventDateET) {
        getDate();
        }

        if (v == createEventButton) {
        //getting text from edit texts
        String title = titleEditText.getText().toString().trim();
        String eventDate = eventDateET.getText().toString().trim();
        String venue = venueEditText.getText().toString().trim();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(eventDate) && !TextUtils.isEmpty(venue)) {
        long temp_id = 1;
        Event event = new Event(temp_id,title, eventDate, venue,Calendar.getInstance().getTime().toString());

        sqliteDBHelper.updateEvent(event);

        Toast.makeText(getApplicationContext(), "Event Updated Successfully.", Toast.LENGTH_LONG).show();

        } else {
        Toast.makeText(getApplicationContext(), "Please fill fields to continue", Toast.LENGTH_SHORT).show();
        }
        }
        }


private void getDate() {
        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

@Override
public void onDateSet(DatePicker view, int year, int monthOfYear,
                      int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        try {
        eventDate = sdf.parse(sdf.format(myCalendar.getTime()));
        startDateStr = sdf.format(myCalendar.getTime());
        getTime();
        } catch (ParseException e) {
        e.printStackTrace();
        }

        }
        };
        new DatePickerDialog(UpdateEventActivity.this, date, myCalendar
        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }



private void getTime() {
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(UpdateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
@SuppressLint({"SetTextI18n", "DefaultLocale"})
@Override
public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        boolean isPM = (hourOfDay >= 12);
        String time = " " + String.format("%02d:%02d:00 %s",
        (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM");
        eventDateET.setText(startDateStr.concat(time));
        }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
        finish();
        return true;
        }
        return super.onOptionsItemSelected(item);
        }
        }
