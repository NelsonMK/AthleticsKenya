package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.athleticskenya.adapters.NotificationsAdapter;
import com.example.athleticskenya.getterClasses.Notifications;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Notifications> notificationsList;
    private NotificationsAdapter notificationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerView = findViewById(R.id.notificationsRecycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationsActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(NotificationsActivity.this, LinearLayoutManager.VERTICAL));

        notificationsList = new ArrayList<>();

        loadNotifications();

    }

    private void loadNotifications() {
        String user_id = String.valueOf(PrefManager.getInstance(NotificationsActivity.this).userId());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_NOTIFICATIONS + user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("notifications","response "+response);
                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i<jsonArray.length(); i++){

                                JSONObject jsonNotification = jsonArray.getJSONObject(i);

                                Notifications notifications = new Notifications();

                                notifications.setTitle(jsonNotification.getString("title"));
                                notifications.setMessage(jsonNotification.getString("message"));
                                notifications.setTime(jsonNotification.getString("time"));

                                notificationsList.add(notifications);
                            }
                            notificationsAdapter = new NotificationsAdapter(NotificationsActivity.this, notificationsList);
                            recyclerView.setAdapter(notificationsAdapter);
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

        Volley.newRequestQueue(NotificationsActivity.this).add(stringRequest);
    }
}