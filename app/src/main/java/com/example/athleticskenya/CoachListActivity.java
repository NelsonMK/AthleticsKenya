package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.athleticskenya.Utils.SimpleDividerItemDecoration;
import com.example.athleticskenya.getterClasses.Coach;

public class CoachListActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner;

    List<Coach> coachList;

    SearchView searchView;

    RecyclerView recyclerView;
    CoachAdapter coachAdapter;
    CoachAllAdapter coachAllAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_list);

      /*  toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        spinner = findViewById(R.id.spinner_coach);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

        coachList = new ArrayList<>();

       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String coach = adapterView.getItemAtPosition(i).toString();

                if (coach.equals("Available coaches")){
                    coachList.clear();
                    loadCoaches();
                } else if (coach.equals("All coaches")){
                    coachList.clear();
                    loadAllCoaches();
                } else {
                    coachList.clear();
                    loadCoaches();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        loadCoaches();

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.coachRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boolean online = checkConnectivity();

                if (online) {
                    coachList.clear();
                    loadCoaches();
                } else {
                    Toast.makeText(getApplicationContext(), "No network connection!", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem mySearchView = menu.findItem(R.id.search);

        searchView = (SearchView) mySearchView.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                coachAdapter.getFilter().filter(query);
                //coachAllAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                coachAdapter.getFilter().filter(newText);
                //coachAllAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadCoaches() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_COACH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {


                                JSONObject coach = array.getJSONObject(i);


                                coachList.add(new Coach(
                                        coach.getInt("id"),
                                        coach.getString("first_name"),
                                        coach.getString("last_name"),
                                        coach.getString("phone"),
                                        coach.getString("email"),
                                        coach.getString("image"),
                                        coach.getString("contact")
                                ));
                            }

                            coachAdapter = new CoachAdapter(CoachListActivity.this, coachList);
                            recyclerView.setAdapter(coachAdapter);
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

    private void loadAllCoaches() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_ALL_COACHES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject coach = array.getJSONObject(i);

                                coachList.add(new Coach(
                                        coach.getInt("id"),
                                        coach.getString("first_name"),
                                        coach.getString("last_name"),
                                        coach.getString("phone"),
                                        coach.getString("email"),
                                        coach.getString("image"),
                                        coach.getString("contact")
                                ));
                            }

                            coachAllAdapter = new CoachAllAdapter(CoachListActivity.this, coachList);
                            recyclerView.setAdapter(coachAllAdapter);
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
