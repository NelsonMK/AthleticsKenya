package com.example.athleticskenya.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.athleticskenya.DetailActivity;
import com.example.athleticskenya.R;
import com.example.athleticskenya.getterClasses.Events;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> implements Filterable{

    public static final String EXTRA_TITLES = "title";
    public static final String EXTRA_DETAILS = "details";
    public static final String EXTRA_IMAGE = "image";
    private Context mCtx;
    private List<Events> eventsList;
    private List<Events> new_eventsList;
    private int lastPosition = -1;

    public EventsAdapter(Context mCtx, List<Events> eventsList) {
        this.mCtx = mCtx;
        this.eventsList = eventsList;
        new_eventsList = new ArrayList<>(eventsList);
    }
    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.layout, null);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventsViewHolder holder, int position) {
        Events events = eventsList.get(position);

        holder.tvv1.setTextColor(Color.BLACK);
        holder.tvv2.setTextColor(Color.BLACK);
        holder.tvv3.setTextColor(Color.BLACK);
        holder.tvv4.setTextColor(Color.BLACK);
        holder.tvv5.setTextColor(Color.BLACK);
        holder.tvv1.setText(events.getEventname());
        holder.tvv2.setText(events.getLocation());
        holder.tvv3.setText(events.getDate());
        holder.tvv4.setText(events.getTime());
        holder.tvv5.setText(events.getDetails());
        if (events.getImage().equals("")){
            holder.ivv.setVisibility(View.GONE);
        } else {
            Glide.with(mCtx)
                    .load(events.getImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.no_image)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(holder.ivv);
        }
        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mCtx, R.anim.slide_in_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    @Override
    public Filter getFilter(){
        return eventsFilter;
    }

    private Filter eventsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Events> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(new_eventsList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Events events : new_eventsList){
                    if (events.getEventname().toLowerCase().contains(filterPattern) || events.getDetails().toLowerCase().contains(filterPattern)
                    || events.getLocation().toLowerCase().contains(filterPattern) || events.getDate().contains(filterPattern)
                    || events.getTime().contains(filterPattern)){
                        filteredList.add(events);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            eventsList.clear();
            eventsList.addAll((List) results.values );
            notifyDataSetChanged();
        }
    };

    class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvv1;
        TextView tvv2;
        TextView tvv3;
        TextView tvv4;
        TextView tvv5;
        ImageView ivv;
        ImageView share;
        ProgressBar progressBar;

        EventsViewHolder(View itemView) {
            super(itemView);

            tvv1 = itemView.findViewById(R.id.eventname);
            tvv2 = itemView.findViewById(R.id.location);
            tvv3 = itemView.findViewById(R.id.date);
            tvv4 = itemView.findViewById(R.id.time);
            tvv5 = itemView.findViewById(R.id.details);
            ivv = itemView.findViewById(R.id.imageview);
            share = itemView.findViewById(R.id.share_event);
            progressBar = itemView.findViewById(R.id.event_loading);

            itemView.setOnClickListener(this);
            share.setOnClickListener(this);

        }
        @Override
        public void onClick(View v)
        {
            int i = getAdapterPosition();
            Events events = eventsList.get(i);

            Intent intent = new Intent(mCtx, DetailActivity.class);
            intent.putExtra(EXTRA_TITLES, events.getEventname());
            intent.putExtra(EXTRA_IMAGE, events.getImage());
            intent.putExtra(EXTRA_DETAILS, events.getMore_details());
            mCtx.startActivity(intent);

            if (v == share){

                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, ""
                        + events.getEventname()
                        + "\n" + events.getImage() +"\n" + events.getDetails() +
                        "\nFind more about the event in Athletics Kenya app available at play store");
                share.setType("text/plain");
                mCtx.startActivity(Intent.createChooser(share, "Share To"));
            }
        }
    }


}
