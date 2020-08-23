package com.example.athleticskenya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.User;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        PrefManager prefManager = PrefManager.getInstance(LoginActivity.this);
        int class1 = prefManager.userClass();
        if(prefManager.isLoggedIn()) {

            this.finish();
            switch (class1) {
                case 1:
                    Intent athlete = new Intent(LoginActivity.this, AthleteActivity.class);
                    athlete.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(athlete);
                    finish();
                    break;
                case 2:
                    finish();
                    startActivity(new Intent(LoginActivity.this, CoachActivity.class));
                    break;
            }
        }

        db = new DatabaseHandler(this);

        init();
    }


    void init(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        //if user presses on login calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean online = checkConnectivity();

                /*if (online){}
                else {
                    Toast.makeText(getApplicationContext(),"No internet connection!", Toast.LENGTH_SHORT).show();
                }*/
                userLogin();
            }
        });
        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                finish();
            }
        });
        //if the user clicks on forgot password
        findViewById(R.id.forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //forgotPasswordDialog();
                startActivity(new Intent(LoginActivity.this, RecoverPasswordActivity.class));
            }
        });
    }

    private void userLogin() {
        //first getting the values
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();
        //validating inputs
        String email_pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com$";

        TextInputLayout mail= findViewById(R.id.email);
        TextInputLayout pass= findViewById(R.id.password);

        if (TextUtils.isEmpty(email)) {
            mail.setError("Please enter your email address!");
            editTextEmail.requestFocus();
            return;
        } else {
            mail.setError(null);
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Enter a valid email address!");
            editTextEmail.requestFocus();
            return;
        } else {
            mail.setError(null);
        }
        if (!editTextEmail.getText().toString().matches(email_pattern)){
            mail.setError("Enter a valid email address!");
            editTextEmail.requestFocus();
            return;
        } else {
            mail.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            pass.setError("Enter your password!");
            editTextPassword.requestFocus();
            return;
        } else {
            pass.setError(null);
        }

       /* if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email!");
            editTextEmail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if (!editTextEmail.getText().toString().matches(email_pattern)){
            editTextEmail.setError("Enter a valid email address!");
            editTextEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password!");
            editTextPassword.requestFocus();
            return;
        }*/
        //if everything is fine
        UserLogin ul = new UserLogin(email,password);
        ul.execute();
    }

    @SuppressLint("StaticFieldLeak")
    class UserLogin extends AsyncTask<Void, Void, String> {
        ProgressBar progressBar;
        String email, password;
        UserLogin(String email,String password) {
            this.email = email;
            this.password = password;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);

                //if no error in response
                if (!obj.getBoolean("error")) {
                    //getting the user from the response
                    JSONObject userJson = obj.getJSONObject("user");

                    int id = userJson.getInt("id");
                    String first_name = userJson.getString("first_name");
                    String last_name = userJson.getString("last_name");
                    String phone = userJson.getString("phone");
                    String email = userJson.getString("email");
                    String d_o_b = userJson.getString("date_of_birth");
                    String race_type = userJson.getString("race_type");
                    String image = userJson.getString("image");
                    int class1 = userJson.getInt("class");
                    int status = userJson.getInt("status");
                    int archive = userJson.getInt("archive");
                    String contact = userJson.getString("contact");
                    String height = userJson.getString("height");
                    String weight = userJson.getString("weight");
                    String location = userJson.getString("location");

                    String class2 = userJson.getString("class");

                    //creating a new user object
                    User user = new User(
                            userJson.getInt("id"),
                            userJson.getString("first_name"),
                            userJson.getString("last_name"),
                            userJson.getString("phone"),
                            userJson.getString("email"),
                            userJson.getString("date_of_birth"),
                            userJson.getString("race_type"),
                            userJson.getString("image"),
                            userJson.getInt("class"),
                            userJson.getInt("status"),
                            userJson.getInt("archive"),
                            userJson.getString("contact"),
                            userJson.getString("height"),
                            userJson.getString("weight"),
                            userJson.getString("location")
                    );


                    //starting the respective activities
                        //store log in session in shared preferences
                        PrefManager.getInstance(getApplicationContext()).setUserLogin(user);
                        //save the user to SQLite database
                        db.addUser(id, first_name, last_name ,phone, email, d_o_b, race_type, image, class1, status, archive, contact, weight, height, location);
                        switch (class2) {
                            case "1":
                                Intent athlete = new Intent(LoginActivity.this, AthleteActivity.class);
                                athlete.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(athlete);
                                finish();
                                break;
                            case "2":
                                Intent coach = new Intent(LoginActivity.this, CoachActivity.class);
                                coach.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(coach);
                                finish();
                                break;
                        }
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);

            //returning the response
            return requestHandler.sendPostRequest(URLS.URL_LOGIN, params);
        }
    }

    private void forgotPasswordDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.reset_password, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Forgot Password");
        dialogBuilder.setCancelable(false);

        final TextInputLayout mEditEmail = dialogView.findViewById(R.id.editEmail);

        dialogBuilder.setPositiveButton("Reset",  new DialogInterface.OnClickListener() {
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

        Objects.requireNonNull(mEditEmail.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mEditEmail.getEditText().getText().length() > 0){
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
                        String email = mEditEmail.getEditText().getText().toString();

                        if (!email.isEmpty()) {
                            if (isValidEmailAddress(email)) {
                               // resetPassword(email);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(), "Email is not valid!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Fill all values!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        alertDialog.show();
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
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
