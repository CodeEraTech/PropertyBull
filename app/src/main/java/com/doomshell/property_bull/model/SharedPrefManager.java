package com.doomshell.property_bull.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Anuj on 12/19/2016.
 */

public class SharedPrefManager {


    private static final String user_details= "user_details";
    private static final String Login_status= "loginstatus";
    private static final String fav_property= "fav";
    private static final String recent_fav= "recent_fav";
    private static final String recent_pro= "recent_pro";
    private static final String fav_position= "fav_position";
    private static final String recent_fav_position= "recent_fav_position";

    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }

    public boolean saveuser_details(String skey,String svalue){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(user_details, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(skey, svalue);
        editor.apply();
        return true;
    }

    public boolean save_Login_status(String skey,boolean svalue){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Login_status, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(skey, svalue);
        editor.apply();
        return true;
    }

    public boolean savefav_property(String skey,String svalue){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(fav_property, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(skey, svalue);
        editor.apply();
        return true;
    }

    public boolean save_recent_fav_property(String skey,String svalue){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(recent_fav, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(skey, svalue);
        editor.apply();
        return true;
    }

    public boolean save_recent_fav_project(String skey,String svalue){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(recent_pro, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(skey, svalue);
        editor.apply();
        return true;
    }
    public boolean savefav_position(String skey,String svalue){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(fav_position, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(skey, svalue);
        editor.apply();
        return true;
    }

    public boolean save_recent_fav_position(String skey,String svalue){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(recent_fav_position, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(skey, svalue);
        editor.apply();
        return true;
    }

    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }

    public String getuser_details(String skey){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(user_details, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(skey, null);
    }


    public String getfav_property(String skey){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(fav_property, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(skey, null);
    }

    public String get_recent_fav_property(String skey){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(recent_fav, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(skey, null);
    }

    public String getfav_position(String skey){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(fav_position, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(skey, null);
    }

    public boolean getProperty_Login_status(String skey){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Login_status, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(skey,false);
    }
}
