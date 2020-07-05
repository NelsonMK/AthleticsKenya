package com.example.athleticskenya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.athleticskenya.R;
import com.example.athleticskenya.getterClasses.TrainingGround;

import java.util.List;

public class Training_Ground_Adapter extends RecyclerView.Adapter<Training_Ground_Adapter.TrainingGroundsViewHolder> {

    private Context mCtx;
    private List<TrainingGround> trainingGroundList;
    private int lastPosition = -1;

    public Training_Ground_Adapter(Context mCtx, List<TrainingGround> trainingGroundList) {
        this.mCtx = mCtx;
        this.trainingGroundList = trainingGroundList;
    }

    @Override
    public TrainingGroundsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.trainging_ground_layout, null);
        return new TrainingGroundsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingGroundsViewHolder holder, int position) {
        TrainingGround trainingGround = trainingGroundList.get(position);

        String available = trainingGround.getGround_availability();

        if (available.equals("1")){
            String available1 = "available";
            holder.textViewAvailability.setText(available1);
        } else {
            String available1 = "not available";
            holder.textViewAvailability.setText(available1);
        }

        //loading the image
        Glide.with(mCtx)
                .load(trainingGround.getGround_image())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(holder.imageView);
        holder.textViewName.setText(trainingGround.getGround_name());
        holder.textViewLocation.setText(trainingGround.getGround_location());

        switch (trainingGround.getGround_condition()){
            case "1":
                holder.textViewCondition.setTextColor(Color.RED);
                String condition1 = "Poor";
                holder.textViewCondition.setText(condition1);
                break;
            case "2":
                holder.textViewCondition.setTextColor(Color.BLUE);
                String condition2 = "Good";
                holder.textViewCondition.setText(condition2);
                break;
            case "3":
                holder.textViewCondition.setTextColor(Color.GREEN);
                String condition3 = "Excellent";
                holder.textViewCondition.setText(condition3);
                break;
        }
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mCtx, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return trainingGroundList.size();
    }

    class TrainingGroundsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewName, textViewLocation, textViewAvailability, textViewCondition;
        ImageView imageView;


        public TrainingGroundsViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewAvailability = itemView.findViewById(R.id.textViewAvailability);
            textViewCondition = itemView.findViewById(R.id.textViewCondition);
            imageView = itemView.findViewById(R.id.trainingGroundImage);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v)
        {
            int i = getAdapterPosition();
            TrainingGround trainingGround = trainingGroundList.get(i);

        }
    }
}
