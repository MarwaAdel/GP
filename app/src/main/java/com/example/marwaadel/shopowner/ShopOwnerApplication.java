package com.example.marwaadel.shopowner;

import android.content.SharedPreferences;

import com.firebase.client.Firebase;

/**
 * Includes one-time initialization of Firebase related code
 */
public class ShopOwnerApplication extends android.app.Application {
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    public void onCreate() {
        super.onCreate();
   /* Initialize Firebase */
        Firebase.setAndroidContext(this);

    }

}