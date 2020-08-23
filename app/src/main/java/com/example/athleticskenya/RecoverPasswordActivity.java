package com.example.athleticskenya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RecoverPasswordActivity extends AppCompatActivity {

    EditText editText_email, edit_password, edit_confirmPassword;
    Button recover_password, confirmPasswordRecovery;
    ProgressDialog progressDialog;
    LinearLayout linear_recover_password;
    RelativeLayout relativePhoneEmail;

    public static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        editText_email = findViewById(R.id.email);
        edit_password = findViewById(R.id.password);
        edit_confirmPassword = findViewById(R.id.confirmPassword);
        recover_password = findViewById(R.id.recover);
        confirmPasswordRecovery = findViewById(R.id.confirmPasswordRecovery);
        linear_recover_password = findViewById(R.id.recover_password);
        relativePhoneEmail = findViewById(R.id.relativePhoneEmail);

        recover_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverPassword();
            }
        });

        confirmPasswordRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPasswordRecovery();
            }
        });

        recover_password = findViewById(R.id.recover);
        recover_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverPassword();
            }
        });

    }

    private void recoverPassword() {
        String email = editText_email.getText().toString().trim();

        String email_pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com$";

        if (TextUtils.isEmpty(email)) {
            editText_email.setError("Please enter your email address!");
            editText_email.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText_email.setError("Enter a valid email address!");
            editText_email.requestFocus();
            return;
        }
        if (!editText_email.getText().toString().matches(email_pattern)){
            editText_email.setError("Enter a valid email address!");
            editText_email.requestFocus();
            return;
        }

        RecoverPassword recoverPassword = new RecoverPassword(email);
        recoverPassword.execute();
    }

    private void confirmPasswordRecovery () {
        String password = edit_password.getText().toString().trim();
        String confirm_password = edit_confirmPassword.getText().toString().trim();
        String user_id = PrefManager.getInstance(RecoverPasswordActivity.this).getKeyChangePasswordId();

        if (TextUtils.isEmpty(password)){
            edit_password.setError("Please enter your password!");
            edit_password.requestFocus();
            return;
        }
        if (password.length() < 5){
            edit_password.setError("Password cannot be less than 5 characters!");
            edit_password.requestFocus();
            return;
        }
        if (!password.equals(confirm_password)){
            edit_confirmPassword.setError("Passwords do not match!");
            edit_confirmPassword.requestFocus();
            return;
        }

        ConfirmPasswordRecovery confirmPasswordRecovery = new ConfirmPasswordRecovery(password, user_id);
        confirmPasswordRecovery.execute();
    }

    @SuppressLint("StaticFieldLeak")
    class RecoverPassword extends AsyncTask<Void, Void, String> {

        String email;

        RecoverPassword(String email){
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RecoverPasswordActivity.this, "Recovering password...", null, true, true);
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);

            return requestHandler.sendPostRequest(URLS.URL_RECOVER_PASSWORD, params);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("password recovery","message from server " + s);
            progressDialog.dismiss();
            try {

                final JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    PrefManager.getInstance(RecoverPasswordActivity.this).setKeyChangePasswordId(obj.getString("id"));
                    relativePhoneEmail.setVisibility(View.GONE);
                    linear_recover_password.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    class ConfirmPasswordRecovery extends AsyncTask<Void, Void, String> {

        String password, user_id;

        ConfirmPasswordRecovery(String password, String user_id){
            this.password = password;
            this.user_id = user_id;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RecoverPasswordActivity.this, "Changing password...", null, true, true);
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("password", password);
            params.put("user_id", user_id);

            return requestHandler.sendPostRequest(URLS.URL_CHANGE_PASSWORD, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("password confirmation","message from server "+s);
            progressDialog.dismiss();
            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    PrefManager.getInstance(RecoverPasswordActivity.this).destroyPasswordRecoveryId();

                    startActivity(new Intent(RecoverPasswordActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}