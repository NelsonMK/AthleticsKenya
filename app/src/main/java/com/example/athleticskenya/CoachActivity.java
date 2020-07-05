package com.example.athleticskenya;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.athleticskenya.Utils.Cipher;
import com.example.athleticskenya.Utils.DividerItemDecoration;
import com.example.athleticskenya.Utils.NetworkStateChecker;
import com.example.athleticskenya.adapters.EventsAdapter;
import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.Events;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.athleticskenya.getterClasses.User;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoachActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Events> eventsList;
    RecyclerView recyclerView;

    TextView first_name;
    TextView last_name;
    ImageView profile_pic;
    final Context context = this;

    SearchView searchView;

    EventsAdapter eventsAdapter;

    int id = PrefManager.getInstance(this).userId();

    private DatabaseHandler db = new DatabaseHandler(this);

    PrefManager prefManager = PrefManager.getInstance(CoachActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View header = navigationView.getHeaderView(0);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                @SuppressLint("InflateParams") View view1 = layoutInflater.inflate(R.layout.prompt, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setView(view1);

                final RelativeLayout relativeLayout = view1.findViewById(R.id.relative_profile);
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(CoachActivity.this, ProfileActivity.class));

                    }
                });

                final RelativeLayout relativeLayout1 = view1.findViewById(R.id.relative_log_out);
                relativeLayout1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        PrefManager.getInstance(getApplicationContext()).logout();
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        User user = db.getUser(id);

        first_name = header.findViewById(R.id.coach_profile_firstname);
        first_name.setText(user.getFirstname());
        last_name = header.findViewById(R.id.coach_profile_lastname);
        last_name.setText(user.getLastname());
        profile_pic = header.findViewById(R.id.coach_profile_picture);
        Glide.with(this)
                .load(user.getImage())
                .placeholder(R.drawable.person)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .apply(RequestOptions.circleCropTransform())
                .into(profile_pic);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boolean online = checkConnectivity();

                if (online) {
                    eventsList.clear();
                    loadServerEvents();
                } else {
                    Toast.makeText(getApplicationContext(), "No network connection!", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        db = new DatabaseHandler(this);

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));

        eventsList = new ArrayList<>();

        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        loadSQLiteEvents();

    }

    /*@Override
    protected void onStart(){
        super.onStart();
        eventsList.clear();
        loadServerEvents();
    }*/

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.coach, menu);

        final MenuItem mySearchView = menu.findItem(R.id.app_bar_search_coach);

        searchView = (SearchView) mySearchView.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                eventsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                eventsAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
           startActivity(new Intent(CoachActivity.this, AcceptedAthletes.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(CoachActivity.this, Training_GroundsActivity.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(CoachActivity.this, Awards.class));
        } else if (id == R.id.athlete_request) {
            startActivity(new Intent(CoachActivity.this, Coach_Athlete_Request.class));
        } else if (id == R.id.nav_share) {
            feedback();
        } else if (id == R.id.nav_send) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT,
                    "\nFind more about athletics kenya in Athletics Kenya app available at play store");
            share.setType("text/plain");
            startActivity(Intent.createChooser(share, "Share To"));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean checkConnectivity(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
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

    private void  loadServerEvents() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_EVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {


                                JSONObject events = array.getJSONObject(i);

                                eventsList.add(new Events(
                                        Cipher.decrypt(events.getString("eventname")),
                                        Cipher.decrypt(events.getString("image")),
                                        Cipher.decrypt(events.getString("location")),
                                        Cipher.decrypt(events.getString("date")),
                                        Cipher.decrypt(events.getString("time")),
                                        Cipher.decrypt(events.getString("details")),
                                        Cipher.decrypt(events.getString("more_details"))
                                ));
                            }

                            EventsAdapter adapter = new EventsAdapter(CoachActivity.this, eventsList);
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

    private void loadSQLiteEvents() {
        eventsList.addAll(db.getSQLiteEvents());
        eventsAdapter = new EventsAdapter(CoachActivity.this, eventsList);
        recyclerView.setAdapter(eventsAdapter);
    }

    private void feedback() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.feedback, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Feedback");
        dialogBuilder.setCancelable(false);

        final EditText title = dialogView.findViewById(R.id.message_title);
        final EditText email = dialogView.findViewById(R.id.f_email);
        final EditText message = dialogView.findViewById(R.id.message);


        if (prefManager.isLoggedIn()){
            email.setVisibility(View.GONE);
        }

        dialogBuilder.setPositiveButton("Send",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // empty
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog alertDialog = dialogBuilder.create();

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(message.getText().length() > 0){
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                final Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setEnabled(false);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        User user = db.getUser(id);
                        String send_title = title.getText().toString();
                        String send_message = message.getText().toString();
                        String email = user.getEmail();

                        if (TextUtils.isEmpty(send_title)){
                            title.setError("Please enter message title!");
                            title.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(send_message)){
                            message.setError("Please enter the message!");
                            message.requestFocus();
                            return;
                        }

                        SendFeedback sendFeedback = new SendFeedback(email, send_title, send_message);
                        sendFeedback.execute();
                        dialog.dismiss();

                    }
                });
            }
        });

        alertDialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    private class SendFeedback extends AsyncTask<Void, Void, String> {
        private String email, send_title, send_message;
        SendFeedback(String email, String send_title, String send_message){
            this.email = email;
            this.send_title = send_title;
            this.send_message = send_message;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("send_title", send_title);
            params.put("send_message", send_message);

            return requestHandler.sendPostRequest(URLS.URL_FEEDBACK, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("Feedback","feedback : "+s);

            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
