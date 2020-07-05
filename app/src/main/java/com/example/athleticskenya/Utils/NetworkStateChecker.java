package com.example.athleticskenya.Utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.URLS;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkStateChecker extends BroadcastReceiver {

    //Context context;
    private DatabaseHandler db;
    static int noOfTimes = 0;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, Intent intent) {

        db = new DatabaseHandler(context);
        noOfTimes++;
        //Toast.makeText(context, "BC Service Running for " + noOfTimes + " times", Toast.LENGTH_SHORT).show();

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_EVENTS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    // Extract JSON array from the response
                                    JSONArray arr = new JSONArray(response);
                                    // System.out.println(arr.length());
                                    // If no of array elements is not zero
                                    if(arr.length() != 0){
                                        // Loop through each array element, get JSON object
                                        for (int i = 0; i < arr.length(); i++) {
                                            // Get JSON object
                                            JSONObject events = (JSONObject) arr.get(i);

                                            String id = String.valueOf(events.getInt("id"));
                                            String event_name = events.getString("eventname");
                                            String image =  events.getString("image");
                                            String location = events.getString("location");
                                            String date = events.getString("date");
                                            String time = events.getString("time");
                                            String details = events.getString("details");
                                            String more_details = events.getString("more_details");

                                            boolean exists = db.checkIfEventsExists(id);

                                            if (!exists) {//if the record does not exist save to SQLite
                                                db.addEvents(id, event_name, image, location, date, time, details, more_details);
                                            }

                                        }

                                    }
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                Volley.newRequestQueue(context).add(stringRequest);
            }
        }
    }
}
