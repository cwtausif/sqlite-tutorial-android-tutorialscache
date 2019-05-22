package com.tutorialscache.sqliteeventsapp.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tutorialscache.sqliteeventsapp.R;
import com.tutorialscache.sqliteeventsapp.models.Event;

import java.util.ArrayList;

    /**
     * The type Event recycler view adapter.
     */

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Event> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    /**
     * Instantiates a new Event recycler view adapter.
     *
     * @param context the context
     * @param data    the data
     */

    // data is passed into the constructor
    public EventRecyclerViewAdapter(Context context, ArrayList<Event> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.event_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.eventNameTextView.setText(mData.get(position).getTitle());
        holder.venueTextView.setText(mData.get(position).getVenue());
        holder.dateTextView.setText(mData.get(position).getEventDate());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        else return 0;
    }

    /**
     * Gets item.
     * @param id the id
     * @return the item
     */

    // convenience method for getting data at click position
    public Event getItem(int id) {
        return mData.get(id);
    }

    /**
     * Sets click listener.
     * @param itemClickListener the item click listener
     */

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * The interface Item click listener.
     */
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        /**
         * On item click.
         * @param view     the view
         * @param position the position
         */
        void onItemClick(View view, int position);
    }

    /**
     * The type View holder.
     */

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView eventNameTextView,venueTextView,dateTextView;
        ImageView optionsImageView;

        /**
         * Instantiates a new View holder.
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTV);
            venueTextView = itemView.findViewById(R.id.venueTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            optionsImageView = itemView.findViewById(R.id.optionsImageView);
            optionsImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
