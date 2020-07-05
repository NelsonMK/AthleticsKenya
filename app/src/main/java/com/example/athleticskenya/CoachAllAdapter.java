package com.example.athleticskenya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.athleticskenya.getterClasses.Coach;

import java.util.ArrayList;
import java.util.List;

public class CoachAllAdapter extends RecyclerView.Adapter<CoachAllAdapter.ProductViewHolder> implements Filterable {


    private Context mCtx;
    private List<Coach> coachList;
    private List<Coach> arraylist;
    private int lastPosition = -1;

    public CoachAllAdapter(Context mCtx, List<Coach> coachList) {
        this.mCtx = mCtx;
        this.coachList = coachList;
        arraylist = new ArrayList<>(coachList);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_all_coaches, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Coach coach = coachList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(coach.getImage())
                .placeholder(R.drawable.person)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageView);
        holder.textViewFirstname.setText(coach.getFirstname());
        holder.textViewLastname.setText(coach.getLastname());
        holder.textViewPhone.setText(coach.getPhone());
        holder.textViewEmail.setText(coach.getEmail());

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
        return coachList.size();
    }

    @Override
    public Filter getFilter(){
        return coachFilter;
    }

    private Filter coachFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Coach> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(arraylist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Coach coach : arraylist){
                    if (coach.getFirstname().toLowerCase().contains(filterPattern) || coach.getLastname().toLowerCase().contains(filterPattern)
                            || coach.getEmail().toLowerCase().contains(filterPattern)){
                        filteredList.add(coach);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            coachList.clear();
            coachList.addAll((List) results.values );
            notifyDataSetChanged();
        }
    };

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewFirstname, textViewLastname, textViewPhone, textViewEmail;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewFirstname = itemView.findViewById(R.id.coach_firstname);
            textViewLastname = itemView.findViewById(R.id.coach_lastname);
            textViewPhone = itemView.findViewById(R.id.coach_phone);
            textViewEmail = itemView.findViewById(R.id.coach_email);
            imageView = itemView.findViewById(R.id.imageView);
        }
        @Override
        public void onClick(View v)
        {
            int i = getAdapterPosition();

        }
    }
}
