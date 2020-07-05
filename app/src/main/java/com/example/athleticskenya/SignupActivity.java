package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SignupActivity extends AppCompatActivity {
    EditText  editFirst, editLast, editPhone, editEmail, editPassword, editConfirm_password;
    Spinner spinner;
    RadioGroup radioGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editFirst = findViewById(R.id.editTextFirstname);
        editLast = findViewById(R.id.editTextLastname);
        radioGender = findViewById(R.id.radioGender);
        editPhone = findViewById(R.id.editTextPhone);
        editEmail = findViewById(R.id.editTextEmail);
        spinner = findViewById(R.id.spinnerusertype);
        editPassword = findViewById(R.id.editTextPassword);
        editConfirm_password = findViewById(R.id.confirm_password);


        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean online = checkConnectivity();

                if (online) {
                    registerUser();
                } else {
                    Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

    }

    private void registerUser() {
        final String first_name = editFirst.getText().toString().trim();
        final String last_name = editLast.getText().toString().trim();
        final String gender = ((RadioButton) findViewById(radioGender.getCheckedRadioButtonId())).getText().toString().trim();
        final String phone = editPhone.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String user_type = spinner.getSelectedItem().toString().trim();
        String class1 = null;
        final String password = editPassword.getText().toString().trim();
        final String confirm_password = editConfirm_password.getText().toString().trim();

        switch (user_type){
            case "Athlete": class1="1";
                break;
            case "Coach": class1="2";
                break;
        }

        //validations
        String mobile_pattern = "^07[0-9]{8}$";
        String email_pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com$";
        TextInputLayout f_name= findViewById(R.id.f_name);
        TextInputLayout l_name= findViewById(R.id.l_name);
        TextInputLayout mail= findViewById(R.id.email);
        TextInputLayout pass= findViewById(R.id.password);
        TextInputLayout phoneNum= findViewById(R.id.phone);
        TextInputLayout c_pass = findViewById(R.id.c_pass);


        if (TextUtils.isEmpty(first_name)) {
            f_name.setError("Please enter your first name!");
            editFirst.requestFocus();
            return;
        } else {
            f_name.setError(null);
        }
        if (TextUtils.isEmpty(last_name)) {
            l_name.setError("Please enter your last name!");
            editLast.requestFocus();
            return;
        } else {
            l_name.setError(null);
        }
        if (TextUtils.isEmpty(phone)) {
            phoneNum.setError("Please enter your phone number!");
            editPhone.requestFocus();
            return;
        } else {
            phoneNum.setError(null);
        }
        if (editPhone.length()<10){
            phoneNum.setError("Phone number cannot be less than 10 digits");
            editPhone.requestFocus();
            return;
        } else {
            phoneNum.setError(null);
        }
        if (!editPhone.getText().toString().matches(mobile_pattern)){
            phoneNum.setError("Enter a valid phone number");
            editPhone.requestFocus();
            return;
        } else {
            phoneNum.setError(null);
        }
        if (!android.util.Patterns.PHONE.matcher(phone).matches()){
            phoneNum.setError("Enter a valid phone number");
            editPhone.requestFocus();
            return;
        } else {
            phoneNum.setError(null);
        }
        if (TextUtils.isEmpty(email)) {
            mail.setError("Please enter your email address!");
            editEmail.requestFocus();
            return;
        } else {
            mail.setError(null);
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Enter a valid email address!");
            editEmail.requestFocus();
            return;
        } else {
            mail.setError(null);
        }
        if (!editEmail.getText().toString().matches(email_pattern)){
            mail.setError("Enter a valid email address!");
            editEmail.requestFocus();
            return;
        } else {
            mail.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            pass.setError("Enter your password!");
            editPassword.requestFocus();
            return;
        } else {
            pass.setError(null);
        }
        if (password.equals(confirm_password)){
            c_pass.setError("Passwords do not match");
            editConfirm_password.requestFocus();
        } else {
            c_pass.setError(null);
        }

        //after validations
        RegisterUser registerUser = new RegisterUser(first_name, last_name,gender,phone,email,class1,password);
        registerUser.execute();
    }
    @SuppressLint("StaticFieldLeak")
    private class RegisterUser extends AsyncTask<Void, Void, String> {
        private ProgressBar progressBar;
        private String first_name,last_name,gender,phone,email,class1,password;
        RegisterUser(String first_name,String last_name, String gender, String phone, String email, String class1, String password){
            this.first_name = first_name;
            this.last_name = last_name;
            this.gender = gender;
            this.phone = phone;
            this.password = password;
            this.class1 = class1;
            this.email = email;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("gender", gender);
            params.put("phone", phone);
            params.put("email", email);
            params.put("class", class1);
            params.put("password", password);

            return requestHandler.sendPostRequest(URLS.URL_REGISTER, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("SignUp","sign up : "+s);

            progressBar.setVisibility(View.GONE);
            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
    
}