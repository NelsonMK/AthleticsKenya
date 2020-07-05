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
import com.example.athleticskenya.getterClasses.Awards;

import java.util.List;

public class AwardsAdapter extends RecyclerView.Adapter<AwardsAdapter.AwardsViewHolder> {

    private Context mCtx;
    private List<Awards> awardsList;

    public AwardsAdapter(Context mCtx, List<Awards> awardsList) {
        this.mCtx = mCtx;
        this.awardsList = awardsList;
    }

    @NonNull
    @Override
    public AwardsAdapter.AwardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.awards_layout, null);
        return new AwardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AwardsViewHolder holder, int position) {
        Awards awards = awardsList.get(position);

        String full_name = awards.getAthlete_f_name() +" "+ awards.getAthlete_l_name();
        holder.winner_name.setText(full_name);
        holder.winner_discipline.setText(awards.getAthlete_race());
        holder.winner_position.setText(awards.getPosition());
        holder.winner_award.setText(awards.getMedal());

    }

    @Override
    public int getItemCount() {
        return awardsList.size();
    }

    static class AwardsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView winner_name, winner_discipline, winner_position, winner_award;

        AwardsViewHolder(View itemView) {
            super(itemView);

            winner_name = itemView.findViewById(R.id.winner_name);
            winner_discipline = itemView.findViewById(R.id.winner_discipline);
            winner_position = itemView.findViewById(R.id.winner_position);
            winner_award = itemView.findViewById(R.id.winner_award);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v)
        {


        }
    }
}
