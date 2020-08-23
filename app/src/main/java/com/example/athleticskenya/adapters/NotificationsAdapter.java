package com.example.athleticskenya.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athleticskenya.R;
import com.example.athleticskenya.getterClasses.Notifications;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {

    private Context context;
    private List<Notifications> notificationsList;

    public NotificationsAdapter(Context context, List<Notifications> notificationsList){
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.notification_layout, null);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notifications notifications = notificationsList.get(position);

        holder.time.setText(notifications.getTime());
        holder.titles.setText(notifications.getTitle());
        holder.message.setText(notifications.getMessage());

    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView titles, message, time;

        NotificationViewHolder(View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time);
            titles = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);

        }

    }
}
