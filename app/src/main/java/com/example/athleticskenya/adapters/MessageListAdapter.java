package com.example.athleticskenya.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athleticskenya.R;
import com.example.athleticskenya.getterClasses.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    private Context context;
    private ArrayList<Message> messagesItems;
    private int userId;
    private int SELF = 786;
    private static String today;

    public MessageListAdapter(Context context, ArrayList<Message> messagesItems, int userId) {
        this.context = context;
        this.messagesItems = messagesItems;
        this.userId = userId;
    }

    @Override
    public int getItemViewType(int position){
        Message message = messagesItems.get(position);

        if (message.getId() == userId) {
            return SELF;
        }

        return position;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        // view type is to identify where to render the chat message
        // left or right
        if (viewType == SELF) {
            // self message
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_right, parent, false);
        } else {
            // others message
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_left, parent, false);
        }


        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messagesItems.get(position);

        holder.message.setTextColor(Color.BLACK);
        holder.message.setText(message.getMessage());

        holder.timestamp.setTextColor(Color.BLACK);
        holder.timestamp.setText(message.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return messagesItems.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView message, timestamp;

        public MessageViewHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.txtMsg);
            timestamp = itemView.findViewById(R.id.timestamp);

        }

    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeStamp(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = "";

        today = today.length() < 2 ? "0" + today : today;

        try {
            Date date = format.parse(dateStr);
            SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
            assert date != null;
            String dateToday = todayFormat.format(date);
            format = dateToday.equals(today) ? new SimpleDateFormat("hh:mm a") : new SimpleDateFormat("dd LLL, hh:mm a");
            String date1 = format.format(date);
            timestamp = date1.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
}
