package com.example.athleticskenya;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.Coach;
import com.example.athleticskenya.getterClasses.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoachAdapter extends RecyclerView.Adapter<CoachAdapter.ProductViewHolder> implements Filterable {

    private Context mCtx;
    private List<Coach> coachList;
    private List<Coach> new_coachList;
    private int lastPosition = -1;


    CoachAdapter(Context mCtx, List<Coach> coachList) {
        this.mCtx = mCtx;
        this.coachList = coachList;
        new_coachList = new ArrayList<>(coachList);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_availabe_coaches, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Coach coach = coachList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(coach.getImage())
                .placeholder(R.drawable.person)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageView);
        holder.textViewFirst_name.setText(coach.getFirstname());
        holder.textViewLast_name.setText(coach.getLastname());
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
                filteredList.addAll(new_coachList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Coach coach : new_coachList){
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

        TextView textViewFirst_name, textViewLast_name, textViewPhone, textViewEmail;
        ImageView imageView;
        Button contact_coach;
        private ProgressBar progressBar;


        ProductViewHolder(View itemView) {
            super(itemView);

            textViewFirst_name = itemView.findViewById(R.id.coach_firstname);
            textViewLast_name = itemView.findViewById(R.id.coach_lastname);
            textViewPhone = itemView.findViewById(R.id.coach_phone);
            textViewEmail = itemView.findViewById(R.id.coach_email);
            imageView = itemView.findViewById(R.id.imageView);
            contact_coach = itemView.findViewById(R.id.contact_coach);
            progressBar = itemView.findViewById(R.id.progressBar);
            contact_coach.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int i = getAdapterPosition();

            Coach coach = coachList.get(i);

            if (v == contact_coach){

                int id1 = PrefManager.getInstance(mCtx).userId();

                DatabaseHandler db = new DatabaseHandler(mCtx);

                User user = db.getUser(id1);

                String id = String.valueOf(coach.getId());
                String contact = coach.getContact();
                String athlete_id = String.valueOf(user.getId());
                String athlete_name = user.getFirstname();
                String athlete_Last_name = user.getLastname();

                ContactCoach contactCoach = new ContactCoach(id, contact, athlete_id, athlete_name, athlete_Last_name);
                contactCoach.execute();


            }
        }

        @SuppressLint("StaticFieldLeak")
        private class ContactCoach extends AsyncTask<Void, Void, String> {
            private String coach_id, contact_id, athlete_id, athlete_name, athlete_Last_name;
            ContactCoach(String id, String contact, String athlete_id,String athlete_name, String athlete_Last_name){
                this.coach_id = id;
                this.contact_id = contact;
                this.athlete_id = athlete_id;
                this.athlete_name = athlete_name;
                this. athlete_Last_name = athlete_Last_name;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
                // Toast.makeText(mCtx, "Request", Toast.LENGTH_SHORT).show();

            }
            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("coach_id", coach_id);
                params.put("contact_id", contact_id);
                params.put("athlete_id", athlete_id);
                params.put("athlete_fname", athlete_name);
                params.put("athlete_lname",  athlete_Last_name);

                return requestHandler.sendPostRequest(URLS.URL_CONTACT, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i("SignUp","sfdsds : "+s);

                progressBar.setVisibility(View.GONE);
                try {

                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
