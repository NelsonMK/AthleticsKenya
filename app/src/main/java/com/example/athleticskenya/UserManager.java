package com.example.athleticskenya;

import android.content.Context;

import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.User;

public class UserManager {

    private Context mCtx;

    public UserManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public User getUser() {
        DatabaseHandler db = new DatabaseHandler(mCtx);
        int id = PrefManager.getInstance(mCtx).userId();

        return db.getUser(id);
    }

}
