package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.Dates;
import com.example.athleticskenya.getterClasses.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DietActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    TextView textView1, textView2, textView3, textView4, textView5, textView6;
    TextView lunch_food, lunch_calories, lunch_carbohydrates, lunch_proteins, lunch_fats;
    TextView dinner_food, dinner_calories, dinner_carbohydrates, dinner_proteins, dinner_fats;

    CardView card1, card2, card3;

    private Spinner spinner;

    ProgressDialog progressDialog;

    private ArrayList<Dates> datesArrayList;

    int id = PrefManager.getInstance(this).userId();

    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        spinner = findViewById(R.id.spinner_date);

        datesArrayList = new ArrayList<>();
        spinner.setOnItemSelectedListener(this);

        textBox();

        cardDesign();

        loadBreakfast();

        loadLunch();

        loadDinner();

        loadDates();
        //new getDates().execute();

    }

    void textBox(){

        textView1 = findViewById(R.id.input_food_name);
        // textView1.setText(breakfast.getFood());
        textView2 = findViewById(R.id.input_brand);
        // textView2.setText(breakfast.getBrand());
        textView3 = findViewById(R.id.input_calories);
        //textView3.setText(breakfast.getCalories());
        textView4 = findViewById(R.id.input_carbohydrates);
        //textView4.setText(breakfast.getCarbohydrates());
        textView5 = findViewById(R.id.input_proteins);
        //textView5.setText(breakfast.getProteins());
        textView6 = findViewById(R.id.input_fats);
        //textView6.setText(breakfast.getFats());
        /*
        end of breakfast
         */
        /*
        start of lunch
         */
        lunch_food = findViewById(R.id.input_lunch_food);

        lunch_calories = findViewById(R.id.input_lunch_calories);

        lunch_carbohydrates = findViewById(R.id.input_lunch_carbohydrates);

        lunch_proteins = findViewById(R.id.input_lunch_proteins);

        lunch_fats = findViewById(R.id.input_lunch_fats);

        /*
        end of lunch
         */
        /*
        start of dinner
         */
        dinner_food = findViewById(R.id.input_dinner_food);

        dinner_calories = findViewById(R.id.input_dinner_calories);

        dinner_carbohydrates = findViewById(R.id.input_dinner_carbohydrates);

        dinner_proteins = findViewById(R.id.input_dinner_proteins);

        dinner_fats = findViewById(R.id.input_dinner_fats);
    }

    void cardDesign(){
                /*
        card view design
         */
        card1 = findViewById(R.id.card_one);
        card1.setCardBackgroundColor(colorOne());
        card2 = findViewById(R.id.card_two);
        card2.setCardBackgroundColor(colorTwo());
        card3 = findViewById(R.id.card_three);
        card3.setCardBackgroundColor(colorThree());

        /*
        end of card design
         */
    }

    private void loadBreakfast() {
        User user = db.getUser(id);
        String id = String.valueOf(user.getId());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_BREAKFAST + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")){
                                JSONObject breakfast = obj.getJSONObject("breakfast");
                                textView1.setText(breakfast.getString("food"));
                                textView2.setText(breakfast.getString("brand"));
                                textView3.setText(breakfast.getString("calories"));
                                textView4.setText(breakfast.getString("carbohydrates"));
                                textView5.setText(breakfast.getString("proteins"));
                                textView6.setText(breakfast.getString("fats"));

                            } else {
                                Toast.makeText(getApplicationContext(), "No breakfast!", Toast.LENGTH_SHORT).show();
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

    private void loadLunch() {
        User user = db.getUser(id);
        String id = String.valueOf(user.getId());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_LUNCH + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")){
                                JSONObject lunch = obj.getJSONObject("lunch");
                                lunch_food.setText(lunch.getString("food"));
                                lunch_calories.setText(lunch.getString("calories"));
                                lunch_carbohydrates.setText(lunch.getString("carbohydrates"));
                                lunch_proteins.setText(lunch.getString("proteins"));
                                lunch_fats.setText(lunch.getString("fats"));

                            } else {
                                Toast.makeText(getApplicationContext(), "No lunch!", Toast.LENGTH_SHORT).show();
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

    private void loadDinner() {
        User user = db.getUser(id);
        String id = String.valueOf(user.getId());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_DINNER + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")){
                                JSONObject dinner = obj.getJSONObject("dinner");
                                dinner_food.setText(dinner.getString("food"));
                                dinner_calories.setText(dinner.getString("calories"));
                                dinner_carbohydrates.setText(dinner.getString("carbohydrates"));
                                dinner_proteins.setText(dinner.getString("proteins"));
                                dinner_fats.setText(dinner.getString("fats"));

                            } else {
                                Toast.makeText(getApplicationContext(), "No dinner!", Toast.LENGTH_SHORT).show();
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

    public int colorOne(){
        return Color.argb(255, 133, 147, 88);
    }

    public int colorTwo(){
        return Color.argb(255, 222, 127, 37);
    }

    public int colorThree(){
        return Color.argb(255, 72, 182, 135);
    }

    private void loadDates() {
        User user = db.getUser(id);
        String id = String.valueOf(user.getId());
        final String dates_url = "http://192.168.43.114/athleticskenya/muriithi/Dates.php?id=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dates_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);

                                JSONArray dates = obj.getJSONArray("date");
                                for (int i = 0; i < dates.length(); i++) {
                                    JSONObject dateObj = (JSONObject) dates.get(i);
                                    Toast.makeText(getApplicationContext(), "found" + dateObj.getString("date"), Toast.LENGTH_SHORT).show();
                                    Dates dates1 = new Dates(dateObj.getInt("id"), dateObj.getString("date"));
                                    datesArrayList.add(dates1);
                                }
                                populateSpinner();


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

    private class getDates extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(DietActivity.this);
            progressDialog.setMessage("Fetching dates...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0){
            User user = db.getUser(id);
            String id = String.valueOf(user.getId());
            final String dates_url = "http://192.168.43.114/athleticskenya/muriithi/Dates.php?id=" + id;
            RequestHandler requestHandler = new RequestHandler();
            String json = requestHandler.sendGetRequest(dates_url);

            if (json != null){
                try {

                    JSONObject jsonObject = new JSONObject(json);

                    if (!jsonObject.getBoolean("error")) {
                        JSONArray dates = jsonObject.getJSONArray("dates");
                        for (int i = 0; i < dates.length(); i++) {
                            JSONObject dateObj = (JSONObject) dates.get(i);
                            Toast.makeText(getApplicationContext(), "found" + dateObj.getString("date"), Toast.LENGTH_SHORT).show();
                            Dates dates1 = new Dates(dateObj.getInt("id"), dateObj.getString("date"));
                            datesArrayList.add(dates1);
                        }
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "did not receive data");
                Toast.makeText(getApplicationContext(),"No dates", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            populateSpinner();
        }
    }

    private void populateSpinner(){
        List<String> labels = new ArrayList<String>();

        for (int i = 0; i < datesArrayList.size(); i++){
            labels.add(datesArrayList.get(i).getDates());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

}
