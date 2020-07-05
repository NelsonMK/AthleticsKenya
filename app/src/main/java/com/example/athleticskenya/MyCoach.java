package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.User;

import org.json.JSONException;
import org.json.JSONObject;

public class MyCoach extends AppCompatActivity {

    ImageView imageView;
    TextView first_name, last_name, phone, email;

    int id = PrefManager.getInstance(this).userId();

    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coach);

        textBox();

        loadCoach();

    }

    void textBox(){
        imageView = findViewById(R.id.photo_coach);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
    }

    private void loadCoach() {
        User user = db.getUser(id);
        String contact = user.getContact();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_MY_COACH + contact,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")){
                                JSONObject coach = obj.getJSONObject("coach");

                                first_name.setText(coach.getString("first_name"));
                                last_name.setText(coach.getString("last_name"));
                                phone.setText(coach.getString("phone"));
                                email.setText(coach.getString("email"));

                            } else {
                                Toast.makeText(getApplicationContext(), "No Coach found!", Toast.LENGTH_SHORT).show();
                            }

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
