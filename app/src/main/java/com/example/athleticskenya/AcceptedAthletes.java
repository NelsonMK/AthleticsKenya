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
import com.example.athleticskenya.adapters.AcceptedAdapter;
import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.Accepted;
import com.example.athleticskenya.getterClasses.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AcceptedAthletes extends AppCompatActivity {

    private List<Accepted> acceptedList;
    RecyclerView recyclerView;

    int id = PrefManager.getInstance(this).userId();

    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_athletes);

        acceptedList = new ArrayList<>();

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));


        loadAccepted();

    }

    private void loadAccepted() {
        User user = db.getUser(id);
        String contact = user.getContact();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_ACCEPTED + contact,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject accepted = array.getJSONObject(i);

                                acceptedList.add(new Accepted(
                                        accepted.getInt("id"),
                                        accepted.getString("first_name"),
                                        accepted.getString("last_name"),
                                        accepted.getString("phone"),
                                        accepted.getString("email"),
                                        accepted.getString("date"),
                                        accepted.getString("race"),
                                        accepted.getString("image"),
                                        accepted.getString("contact"),
                                        accepted.getString("height"),
                                        accepted.getString("weight"),
                                        accepted.getString("location")
                                ));
                            }

                            AcceptedAdapter adapter = new AcceptedAdapter(AcceptedAthletes.this, acceptedList);
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
