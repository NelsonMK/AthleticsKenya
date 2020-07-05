package com.example.athleticskenya;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.athleticskenya.getterClasses.User;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static final String SHARED_PREF_NAME = "athletics_kenya";
    private static final String KEY_ID = "user_id";
    private static final String KEY_CLASS = "user_class";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    //Creating a volley request queue object
    private RequestQueue mRequestQueue;

    //Creatting class object
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    //Public static method to get the instance of this class
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    //This method would return the request queue
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    //This method would add the requeust to the queue for execution
    public <T> void addToRequestQueue(Request<T> req) {
        //Setting a tag to the request
        req.setTag(TAG);

        //calling the method to get the request queue and adding the requeust the the queuue
        getRequestQueue().add(req);
    }

    //method to cancle the pending requests
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void setUserLogin(User user) {
        SharedPreferences sharedPreferences = mInstance.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putInt(KEY_CLASS, user.getClass_id());
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
        editor.apply();
    }
}
