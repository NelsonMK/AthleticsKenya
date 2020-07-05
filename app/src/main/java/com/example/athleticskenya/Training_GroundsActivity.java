package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.athleticskenya.Utils.DividerItemDecoration;
import com.example.athleticskenya.adapters.Training_Ground_Adapter;
import com.example.athleticskenya.getterClasses.TrainingGround;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Training_GroundsActivity extends AppCompatActivity {

    private List<TrainingGround> trainingGroundList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training__grounds);

        trainingGroundList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));

        loadTrainingGround();

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.groundRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boolean online = checkConnectivity();

                if (online) {
                    trainingGroundList.clear();
                    loadTrainingGround();
                } else {
                    Toast.makeText(getApplicationContext(), "No network connection!", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadTrainingGround() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_TRAINING_GROUND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject training_ground = array.getJSONObject(i);

                                trainingGroundList.add(new TrainingGround(
                                        training_ground.getString("ground_name"),
                                        training_ground.getString("ground_image"),
                                        training_ground.getString("ground_location"),
                                        training_ground.getString("ground_availability"),
                                        training_ground.getString("ground_condition")
                                ));
                            }

                            Training_Ground_Adapter adapter = new Training_Ground_Adapter(Training_GroundsActivity.this, trainingGroundList);
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

    public boolean checkConnectivity(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean online = false;
        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                online = true;
            }
        }

        return online;
    }
}
