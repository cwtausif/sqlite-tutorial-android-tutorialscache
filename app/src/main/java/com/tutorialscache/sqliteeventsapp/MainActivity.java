package com.tutorialscache.sqliteeventsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.tutorialscache.sqliteeventsapp.adapter.EventRecyclerViewAdapter;
import com.tutorialscache.sqliteeventsapp.database.SqliteDBHelper;
import com.tutorialscache.sqliteeventsapp.models.Event;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements EventRecyclerViewAdapter.ItemClickListener {

    private static final String TAG = "MainActivity";
    private EventRecyclerViewAdapter adapter;
    private ArrayList<Event> events;
    private SqliteDBHelper sqliteDBHelper;
    private int totalEvents = 0;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DB Helper Instance
        sqliteDBHelper = new SqliteDBHelper(MainActivity.this);
        setContentView(R.layout.activity_main);



        // set up the RecyclerView
        recyclerView = findViewById(R.id.eventsRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        loadEvents();

    }

    @Override
    protected void onResume() {

        //update events home
        events.clear();
        loadEvents();

        super.onResume();
    }

    private void loadEvents() {
        // Fetch all events and save in Events ArrayList
        events = sqliteDBHelper.getAllEvents();
        totalEvents = events.size();

        Log.d(TAG, "Total Events: "+totalEvents);
        if (totalEvents==0){
            Toast.makeText(getApplicationContext(),"No Events found.",Toast.LENGTH_SHORT).show();
        }
        adapter = new EventRecyclerViewAdapter(getApplicationContext(), events);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    //creating option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createEvent:
                //move to create event page
                Intent intent = new Intent(getApplicationContext(),CreateEventActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, final int position) {
        final Event event = adapter.getItem(position);
        Log.d("response ",event.getTitle());
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(event.getTitle())
                .setMessage("What action do you want to perform?")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //update Event Activity
                        Intent intent = new Intent(getApplicationContext(),UpdateEventActivity.class);
                        intent.putExtra("id",event.getId());
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Event Deleted Successfully", Toast.LENGTH_SHORT).show();
                        sqliteDBHelper.deleteEvent(event);
                        events.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setCancelable(true)
                .show();
    }
}
