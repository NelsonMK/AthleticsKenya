package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.athleticskenya.Utils.SimpleDividerItemDecoration;
import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.CoachRequest;
import com.example.athleticskenya.getterClasses.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Coach_Athlete_Request extends AppCompatActivity {

    private List<CoachRequest> coachRequestList;
    RecyclerView recyclerView;

    int id = PrefManager.getInstance(this).userId();

    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach__athlete__request);

        coachRequestList = new ArrayList<>();

        recyclerView = findViewById(R.id.request_recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

        loadRequests();
    }

    private void loadRequests() {
        User user = db.getUser(id);
        int id = user.getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_REQUEST + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {


                                JSONObject coachRequest = array.getJSONObject(i);

                                    coachRequestList.add(new CoachRequest(
                                            coachRequest.getInt("coach_id"),
                                            coachRequest.getInt("athlete_id"),
                                            coachRequest.getString("contact_id"),
                                            coachRequest.getString("athlete_fname"),
                                            coachRequest.getString("athlete_lname")

                                    ));

                            }

                            RequestAdapter adapter = new RequestAdapter(Coach_Athlete_Request.this, coachRequestList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}
