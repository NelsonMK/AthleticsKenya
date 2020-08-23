package com.example.athleticskenya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.athleticskenya.Utils.Cipher;
import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import static android.view.View.GONE;

public class EditProfile extends AppCompatActivity {

    int id = PrefManager.getInstance(this).userId();

    private DatabaseHandler db = new DatabaseHandler(this);

    Bitmap FixBitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String update_picture;

    private int GALLERY = 1, CAMERA = 2;

    EditText first_name, last_name, phone, email, height, weight, location, date_of_birth, raceType;
    ImageView profile_picture;
    Button Upload_Btn;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profile();

       /* date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateOfBirth();
            }
        });*/

        Upload_Btn = findViewById(R.id.upload);
        Upload_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean online = checkConnectivity();

                if (online) {
                    updateUser();
                } else {
                    Toast.makeText(getApplicationContext(), "No network connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

       /* if (ContextCompat.checkSelfPermission(EditProfile.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }*/

    }

    void profile() {
        /*
        start of setting up profile with data from shared preferences handled by UserManager class
         */
        User user = db.getUser(id);

        first_name = findViewById(R.id.firstname);
        first_name.setText(user.getFirstname());
        last_name = findViewById(R.id.lastname);
        last_name.setText(user.getLastname());
        phone = findViewById(R.id.phone);
        phone.setText(user.getPhone());
        email = findViewById(R.id.email);
        email.setText(user.getEmail());
       /* date_of_birth = findViewById(R.id.date_of_birth);
        date_of_birth.setText(user.getDate_of_birth());
        raceType = findViewById(R.id.raceType);
        raceType.setText(user.getRace_type());
        height = findViewById(R.id.height);
        height.setText(user.getHeight());
        weight = findViewById(R.id.weight);
        weight.setText(user.getWeight());

        location = findViewById(R.id.location);
        location.setText(user.getLocation());

        profile_picture = findViewById(R.id.profile_picture);
        String userImage = user.getImage();
        Glide.with(this)
                .load(userImage)
                .placeholder(R.drawable.person)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .apply(RequestOptions.circleCropTransform())
                .into(profile_picture);

        byteArrayOutputStream = new ByteArrayOutputStream();

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });*/
        /*
        end of setting up profile
         */
    }

    void dateOfBirth(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                date_of_birth.setText(date);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    Glide.with(this)
                            .load(FixBitmap)
                            .apply(RequestOptions.circleCropTransform())
                            .into(profile_picture);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfile.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {

            FixBitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            Glide.with(this)
                    .load(FixBitmap)
                    .apply(RequestOptions.circleCropTransform())
                    .into(profile_picture);
        }
    }

    private void updateUser() {
        final String update_first_name = first_name.getText().toString().trim();
        final String update_last_name = last_name.getText().toString().trim();
        final String update_phone = phone.getText().toString().trim();
        final String update_email = email.getText().toString().trim();
        /*final String update_date_of_birth = date_of_birth.getText().toString().trim();
        final String update_raceType = raceType.getText().toString().trim();
        final String update_height = height.getText().toString().trim();
        final String update_weight = weight.getText().toString().trim();
        final String update_location = location.getText().toString().trim();*/
        final String user_id = String.valueOf(id);

        /*FixBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        update_picture = Base64.encodeToString(byteArray, Base64.DEFAULT);*/

        String mobile_pattern = "^07[0-9]{8}$";
        String email_pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com$";

       /* if (TextUtils.isEmpty(update_picture)){
            Toast.makeText(getApplicationContext(), "Please choose a picture!", Toast.LENGTH_SHORT).show();
            profile_picture.requestFocus();
            return;
        }*/
        if (TextUtils.isEmpty(update_first_name)) {
            first_name.setError("Please enter your first name!");
            first_name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(update_last_name)) {
            last_name.setError("Please enter your last name!");
            last_name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(update_phone)) {
            phone.setError("Please enter your phone number!");
            phone.requestFocus();
            return;
        }
        if (phone.length()<10){
            phone.setError("Phone number cannot be less than 10 digits");
            phone.requestFocus();
            return;
        }
        if (!phone.getText().toString().matches(mobile_pattern)){
            phone.setError("Enter a valid phone number");
            phone.requestFocus();
            return;
        }
        if (!android.util.Patterns.PHONE.matcher(update_phone).matches()){
            phone.setError("Enter a valid phone number");
            phone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(update_email)) {
            email.setError("Please enter your email address!");
            email.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(update_email).matches()) {
            email.setError("Enter a valid email address!");
            email.requestFocus();
            return;
        }
        if (!email.getText().toString().matches(email_pattern)){
            email.setError("Enter a valid email address!");
            email.requestFocus();
            return;
        }
     /*   if (TextUtils.isEmpty(update_height)){
            height.setError("Enter your height!");
            height.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(update_weight)){
            weight.setError("Enter your height!");
            weight.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(update_location)){
            location.setError("Enter your height!");
            location.requestFocus();
            return;
        }*/

        UpdateUser updateUser = new UpdateUser(update_first_name, update_last_name,update_phone,update_email, user_id);
        updateUser.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateUser extends AsyncTask<Void, Void, String> {
        private ProgressBar progressBar;
        private String update_picture, update_first_name, update_last_name,update_phone,update_email, user_id, update_height, update_weight, update_location;
        private String update_date_of_birth, update_raceType;

        UpdateUser(String update_first_name, String update_last_name, String update_phone, String update_email, String user_id){
            //this.update_picture = update_picture;
            this.update_first_name = update_first_name;
            this.update_last_name = update_last_name;
            this.update_phone = update_phone;
            this.update_email = update_email;
          /*  this.update_date_of_birth = update_date_of_birth;
            this.update_raceType = update_raceType;*/
            this.user_id = user_id;
           /* this.update_height = update_height;
            this.update_weight = update_weight;
            this.update_location = update_location;*/
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.profile_progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
           // params.put("image", update_picture);
            params.put("first_name", Cipher.encrypt(update_first_name));
            params.put("last_name", update_last_name);
            params.put("phone", update_phone);
            params.put("email", update_email);
         /*   params.put("date_of_birth", update_date_of_birth);
            params.put("race_type", update_raceType);*/
            params.put("id", user_id);
           /* params.put("height", update_height);
            params.put("weight", update_weight);
            params.put("location", update_location);*/

            return requestHandler.sendPostRequest(URLS.URL_UPDATE, params);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("profile","update : "+s);

            progressBar.setVisibility(GONE);
            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    JSONObject userJson = obj.getJSONObject("user");

                    //fetch updated user from online database
                    String SQLite_first_name = userJson.getString("first_name");
                    String SQLite_last_name = userJson.getString("last_name");
                    String SQLite_phone = userJson.getString("phone");
                    String SQLite_email = userJson.getString("email");
                    String SQLite_date_of_birth = userJson.getString("date_of_birth");
                    String SQLite_raceType = userJson.getString("raceType");
                    String SQLite_image = userJson.getString("image");
                    String SQLite_height = userJson.getString("height");
                    String SQLite_weight = userJson.getString("weight");
                    String SQLite_location = userJson.getString("location");

                    //save the user details to SQLite database using updateUserSQLite method
                    updateUserSQLite(SQLite_first_name, SQLite_last_name, SQLite_phone, SQLite_email, SQLite_date_of_birth, SQLite_raceType, SQLite_image,
                            SQLite_height, SQLite_weight, SQLite_location);

                    //pass the values to the editTexts in EditProfile
                    User user = db.getUser(id);

                    first_name.setText(user.getFirstname());
                    last_name.setText(user.getLastname());
                    phone.setText(user.getPhone());
                    email.setText(user.getEmail());
                   /* date_of_birth.setText(user.getDate_of_birth());
                    raceType.setText(user.getRace_type());
                    Glide.with(EditProfile.this)
                            .load(user.getImage())
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .apply(RequestOptions.circleCropTransform())
                            .into(profile_picture);
                    height.setText(user.getHeight());
                    weight.setText(user.getWeight());
                    location.setText(user.getLocation());*/

                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera

            }
            else {

                Toast.makeText(EditProfile.this, "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void updateUserSQLite(String first_name, String last_name, String phone, String email, String d_o_b, String race_type, String image, String height,
                                  String weight, String location){

        DatabaseHandler db = new DatabaseHandler(this);

        User user = db.getUser(id);

        user.setFirstname(first_name);
        user.setLastname(last_name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setDate_of_birth(d_o_b);
        user.setRace_type(race_type);
        user.setImage(image);
        user.setHeight(height);
        user.setWeight(weight);
        user.setLocation(location);

        db.updateUser(user);
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
