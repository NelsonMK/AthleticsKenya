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

import com.example.athleticskenya.getterClasses.CoachRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private Context mCtx;
    private List<CoachRequest> coachRequestList;

    RequestAdapter(Context mCtx, List<CoachRequest> coachRequestList) {
        this.mCtx = mCtx;
        this.coachRequestList = coachRequestList;
    }

    @NonNull
    @Override
    public RequestAdapter.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
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

                String athlete_id = String.valueOf(coachRequest.getAthlete_id());
                String contact_id = coachRequest.getContact();

                AcceptRequest acceptRequest = new AcceptRequest(athlete_id, contact_id);
                acceptRequest.execute();
            }
            if (v == reject){

                String athlete_id = String.valueOf(coachRequest.getAthlete_id());
                String coach_id = String.valueOf(coachRequest.getCoach_id());

                RejectRequest rejectRequest = new RejectRequest(athlete_id, coach_id);
                rejectRequest.execute();
            }
        }

        @SuppressLint("StaticFieldLeak")
        private class AcceptRequest extends AsyncTask<Void, Void, String> {
            private String athlete_id, contact_id;
            AcceptRequest(String athlete_id, String contact_id){
                this.athlete_id = athlete_id;
                this.contact_id = contact_id;
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

                return requestHandler.sendPostRequest(URLS.URL_ACCEPT, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i("SendRequest","request : "+s);

                try {

                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        coachRequestList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(),coachRequestList.size());

                    } else {
                        Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @SuppressLint("StaticFieldLeak")
        private class RejectRequest extends AsyncTask<Void, Void, String> {
            private String athlete_id, coach_id;
            RejectRequest(String athlete_id, String coach_id){
                this.athlete_id = athlete_id;
                this.coach_id = coach_id;
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

                return requestHandler.sendPostRequest(URLS.URL_REJECT, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i("RejectRequest","reject : "+s);

                try {

                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        coachRequestList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(),coachRequestList.size());

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
