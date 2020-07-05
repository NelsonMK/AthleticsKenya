package com.example.athleticskenya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.athleticskenya.AcceptedProfile.EXTRA_ID_EXTRA;
import static com.example.athleticskenya.AcceptedProfile.EXTRA_CONTACT_EXTRA;

public class RecommedDiet extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4, textView5, textView6;
    TextView lunch_food, lunch_calories, lunch_carbohydrates, lunch_proteins, lunch_fats;
    TextView dinner_food, dinner_calories, dinner_carbohydrates, dinner_proteins, dinner_fats;
    CardView card1, card2, card3;
    TextView button, button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommed_diet);

        textBox();

        cardDesign();

        loadBreakfast();

        loadLunch();

        loadDinner();

        uploadDiet();
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
        /*animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        button_diet = findViewById(R.id.diet_button);
        button_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ScrollView relativeLayout = findViewById(R.id.scrollView_diet);
                if (relativeLayout.isShown()){
                    relativeLayout.startAnimation(animationUp);
                    CountDownTimer countDownTimer = new CountDownTimer(COUNTDOWN_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            relativeLayout.setVisibility(View.GONE);
                        }
                    };
                    countDownTimer.start();
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    relativeLayout.startAnimation(animationDown);
                }
            }
        });*/

        /*
        end of card design
         */
    }

    void uploadDiet(){
        button = findViewById(R.id.btn_breakfast);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakfastDialog();
            }
        });
        button1 = findViewById(R.id.btn_lunch);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunchDialog();
            }
        });
        button2 = findViewById(R.id.btn_dinner);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dinnerDialog();
            }
        });
    }

    private void breakfastDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.breakfast, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Breakfast");
        dialogBuilder.setCancelable(false);

        final EditText mEditFoodName = dialogView.findViewById(R.id.editFood);
        final EditText mEditBrand = dialogView.findViewById(R.id.editBrand);
        final EditText mEditCalories = dialogView.findViewById(R.id.editCalories);
        final EditText mEditCarbohydrates = dialogView.findViewById(R.id.editCarbohydrates);
        final EditText mEditProteins = dialogView.findViewById(R.id.editProteins);
        final EditText mEditFats = dialogView.findViewById(R.id.editFats);

        dialogBuilder.setPositiveButton("Save",  new DialogInterface.OnClickListener() {
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

        mEditFats.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mEditFats.getText().length() > 0){
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

                        String food = mEditFoodName.getText().toString();
                        String brand = mEditBrand.getText().toString();
                        String calories = mEditCalories.getText().toString();
                        String carbohydrates = mEditCarbohydrates.getText().toString();
                        String proteins = mEditProteins.getText().toString();
                        String fats = mEditFats.getText().toString();
                        String type = "breakfast";

                        Intent intent = getIntent();
                        String id = intent.getStringExtra(EXTRA_ID_EXTRA);
                        String contact = intent.getStringExtra(EXTRA_CONTACT_EXTRA);

                        if (TextUtils.isEmpty(food)){
                            mEditFoodName.setError("Please enter the foodname!");
                            mEditFoodName.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(brand)){
                            mEditBrand.setError("Please enter the brand!");
                            mEditBrand.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(calories)){
                            mEditCalories.setError("Please enter the calories!");
                            mEditCalories.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(carbohydrates)){
                            mEditCarbohydrates.setError("Please enter the carbohydrates level!");
                            mEditCarbohydrates.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(proteins)){
                            mEditProteins.setError("Please enter the protein level!");
                            mEditProteins.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(fats)){
                            mEditFats.setError("Please enter the fat level!");
                            mEditFats.requestFocus();
                            return;
                        }

                        SaveDiet saveDiet = new SaveDiet(id, food, brand, calories, carbohydrates, proteins, fats, contact, type);
                        saveDiet.execute();
                        dialog.dismiss();


                    }
                });
            }
        });

        alertDialog.show();
    }

    private void lunchDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.lunch, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Lunch");
        dialogBuilder.setCancelable(false);

        final EditText mEditFoodName = dialogView.findViewById(R.id.editLunchFood);
        final EditText mEditCalories = dialogView.findViewById(R.id.editLunchCalories);
        final EditText mEditCarbohydrates = dialogView.findViewById(R.id.editLunchCarbohydrates);
        final EditText mEditProteins = dialogView.findViewById(R.id.editLunchProteins);
        final EditText mEditFats = dialogView.findViewById(R.id.editLunchFats);

        dialogBuilder.setPositiveButton("Save",  new DialogInterface.OnClickListener() {
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

        mEditFats.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mEditFats.getText().length() > 0){
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

                        String food = mEditFoodName.getText().toString();
                        String calories = mEditCalories.getText().toString();
                        String carbohydrates = mEditCarbohydrates.getText().toString();
                        String proteins = mEditProteins.getText().toString();
                        String fats = mEditFats.getText().toString();
                        String type = "lunch";
                        String brand = "lunch";

                        Intent intent = getIntent();
                        String id = intent.getStringExtra(EXTRA_ID_EXTRA);
                        String contact = intent.getStringExtra(EXTRA_CONTACT_EXTRA);

                        if (TextUtils.isEmpty(food)){
                            mEditFoodName.setError("Please enter the foodname!");
                            mEditFoodName.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(calories)){
                            mEditCalories.setError("Please enter the calories!");
                            mEditCalories.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(carbohydrates)){
                            mEditCarbohydrates.setError("Please enter the carbohydrates level!");
                            mEditCarbohydrates.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(proteins)){
                            mEditProteins.setError("Please enter the protein level!");
                            mEditProteins.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(fats)){
                            mEditFats.setError("Please enter the fat level!");
                            mEditFats.requestFocus();
                            return;
                        }

                        SaveDiet saveDiet = new SaveDiet(id, food, brand, calories, carbohydrates, proteins, fats, contact, type);
                        saveDiet.execute();
                        dialog.dismiss();


                    }
                });
            }
        });

        alertDialog.show();
    }

    private void dinnerDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dinner, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Dinner");
        dialogBuilder.setCancelable(false);

        final EditText mEditFoodName = dialogView.findViewById(R.id.editDinnerFood);
        final EditText mEditCalories = dialogView.findViewById(R.id.editDinnerCalories);
        final EditText mEditCarbohydrates = dialogView.findViewById(R.id.editDinnerCarbohydrates);
        final EditText mEditProteins = dialogView.findViewById(R.id.editDinnerProteins);
        final EditText mEditFats = dialogView.findViewById(R.id.editDinnerFats);

        dialogBuilder.setPositiveButton("Save",  new DialogInterface.OnClickListener() {
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

        mEditFats.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mEditFats.getText().length() > 0){
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

                        String food = mEditFoodName.getText().toString();
                        String calories = mEditCalories.getText().toString();
                        String carbohydrates = mEditCarbohydrates.getText().toString();
                        String proteins = mEditProteins.getText().toString();
                        String fats = mEditFats.getText().toString();
                        String type = "dinner";
                        String brand = "dinner";

                        Intent intent = getIntent();
                        String id = intent.getStringExtra(EXTRA_ID_EXTRA);
                        String contact = intent.getStringExtra(EXTRA_CONTACT_EXTRA);

                        if (TextUtils.isEmpty(food)){
                            mEditFoodName.setError("Please enter the foodname!");
                            mEditFoodName.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(calories)){
                            mEditCalories.setError("Please enter the calories!");
                            mEditCalories.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(carbohydrates)){
                            mEditCarbohydrates.setError("Please enter the carbohydrates level!");
                            mEditCarbohydrates.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(proteins)){
                            mEditProteins.setError("Please enter the protein level!");
                            mEditProteins.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(fats)){
                            mEditFats.setError("Please enter the fat level!");
                            mEditFats.requestFocus();
                            return;
                        }

                        SaveDiet saveDiet = new SaveDiet(id, food, brand, calories, carbohydrates, proteins, fats, contact, type);
                        saveDiet.execute();
                        dialog.dismiss();


                    }
                });
            }
        });

        alertDialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    private class SaveDiet extends AsyncTask<Void, Void, String> {

        private String id, food, brand, calories, carbohydrates, proteins, fats, contact, type;

        SaveDiet(String id, String food, String brand, String calories, String carbohydrates, String proteins, String fats, String contact, String type){
            this.id = id;
            this.food = food;
            this.brand = brand;
            this.calories = calories;
            this.carbohydrates = carbohydrates;
            this.proteins = proteins;
            this.fats = fats;
            this.contact = contact;
            this.type = type;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //do nothing
        }
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("id", id);
            params.put("food", food);
            params.put("brand", brand);
            params.put("calories", calories);
            params.put("carbohydrates", carbohydrates);
            params.put("proteins", proteins);
            params.put("fats", fats);
            params.put("contact", contact);
            params.put("type", type);

            return requestHandler.sendPostRequest(URLS.URL_DIET, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("Diet","diet : "+s);

            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject diet = obj.getJSONObject("diet");
                    String type = diet.getString("type");

                    switch (type){
                        case "breakfast":
                            textView1.setText(diet.getString("food"));
                            textView2.setText(diet.getString("brand"));
                            textView3.setText(diet.getString("calories"));
                            textView4.setText(diet.getString("carbohydrates"));
                            textView5.setText(diet.getString("proteins"));
                            textView6.setText(diet.getString("fats"));
                            break;
                        case "lunch":
                            lunch_food.setText(diet.getString("food"));
                            lunch_calories.setText(diet.getString("calories"));
                            lunch_carbohydrates.setText(diet.getString("carbohydrates"));
                            lunch_proteins.setText(diet.getString("proteins"));
                            lunch_fats.setText(diet.getString("fats"));
                            break;
                        case "dinner":
                            dinner_food.setText(diet.getString("food"));
                            dinner_calories.setText(diet.getString("calories"));
                            dinner_carbohydrates.setText(diet.getString("carbohydrates"));
                            dinner_proteins.setText(diet.getString("proteins"));
                            dinner_fats.setText(diet.getString("fats"));
                            break;
                    }


                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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

    private void loadBreakfast() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(EXTRA_ID_EXTRA);
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
        Intent intent = getIntent();
        String id = intent.getStringExtra(EXTRA_ID_EXTRA);
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
        Intent intent = getIntent();
        String id = intent.getStringExtra(EXTRA_ID_EXTRA);
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
}
