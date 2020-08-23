package com.example.athleticskenya;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.CoachRequest;
import com.example.athleticskenya.getterClasses.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private Context context;
    private List<CoachRequest> coachRequestList;

    RequestAdapter(Context context, List<CoachRequest> coachRequestList) {
        this.context = context;
        this.coachRequestList = coachRequestList;
    }

    @NonNull
    @Override
    public RequestAdapter.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.request, null);
        return new RequestAdapter.RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestAdapter.RequestViewHolder holder, int position) {
        CoachRequest coachRequest = coachRequestList.get(position);

        holder.textViewFirstname.setText(coachRequest.getAfirstname());
        holder.textViewLastname.setText(coachRequest.getAlastname());

    }

    @Override
    public int getItemCount() {
        return coachRequestList.size();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewFirstname, textViewLastname;
        ImageView accept, reject;

        RequestViewHolder(View itemView) {
            super(itemView);

            textViewFirstname = itemView.findViewById(R.id.athlete_fname);
            textViewLastname = itemView.findViewById(R.id.athlete_lname);
            accept = itemView.findViewById(R.id.accept_request);
            accept.setOnClickListener(this);
            reject = itemView.findViewById(R.id.reject_request);
            reject.setOnClickListener(this);
        }
        @Override
        public void onClick(View v)
        {
            int i = getAdapterPosition();
            CoachRequest coachRequest = coachRequestList.get(i);
            if (v == accept){
                DatabaseHandler db = new DatabaseHandler(context);
                User user = db.getUser(PrefManager.getInstance(context).userId());

                String athlete_id = String.valueOf(coachRequest.getAthlete_id());
                String contact_id = coachRequest.getContact();
                String email = user.getEmail();

                AcceptRequest acceptRequest = new AcceptRequest(athlete_id, contact_id, email);
                acceptRequest.execute();
            }
            if (v == reject){
                DatabaseHandler db = new DatabaseHandler(context);
                User user = db.getUser(PrefManager.getInstance(context).userId());

                String athlete_id = String.valueOf(coachRequest.getAthlete_id());
                String coach_id = String.valueOf(coachRequest.getCoach_id());
                String email = user.getEmail();

                RejectRequest rejectRequest = new RejectRequest(athlete_id, coach_id, email);
                rejectRequest.execute();
            }
        }

        @SuppressLint("StaticFieldLeak")
        private class AcceptRequest extends AsyncTask<Void, Void, String> {

            private String athlete_id, contact_id, email;

            AcceptRequest(String athlete_id, String contact_id, String email){
                this.athlete_id = athlete_id;
                this.contact_id = contact_id;
                this.email = email;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("athlete_id", athlete_id);
                params.put("contact_id", contact_id);
                params.put("email", email);

                return requestHandler.sendPostRequest(URLS.URL_ACCEPT, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i("SendRequest","request : "+s);

                try {

                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        coachRequestList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(),coachRequestList.size());

                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @SuppressLint("StaticFieldLeak")
        private class RejectRequest extends AsyncTask<Void, Void, String> {
            private String athlete_id, coach_id, email;
            RejectRequest(String athlete_id, String coach_id, String email){
                this.athlete_id = athlete_id;
                this.coach_id = coach_id;
                this.email = email;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }
            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("athlete_id", athlete_id);
                params.put("coach_id", coach_id);
                params.put("email", email);

                return requestHandler.sendPostRequest(URLS.URL_REJECT, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i("RejectRequest","reject : "+s);

                try {

                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        coachRequestList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(),coachRequestList.size());

                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
