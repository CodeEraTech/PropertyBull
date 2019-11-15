package com.doomshell.property_bull;

import android.content.Context;
import android.content.SharedPreferences;

import com.doomshell.property_bull.helper.Config;


public class SharedPreferencesDatabase {
    private Context context;
    private SharedPreferences sharedpreferences;

    public SharedPreferencesDatabase(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void createDatabase() {
        sharedpreferences = context.getSharedPreferences(Config.SHARED_PREFERENCE_DB_NAME, Context.MODE_PRIVATE);
    }

    public void addDataPrivilege(String key, String[] values) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        for (int i = 0; i < values.length; i++) {
            editor.putString(key + "_" + i, values[i]);
        }
        editor.commit();
    }

    public void addData(String key, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void addDataBool(String key, boolean value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String[] getDataPrivilege(String key, int count) {
        String s[] = {};
        for (int i = 0; i < count; i++) {
            s[i] = sharedpreferences.getString(key + "_" + i, null);
        }
        return s;
    }

    public String getData(String key) {
        return sharedpreferences.getString(key, "");
    }

    public boolean getDataBool(String key) {
        return sharedpreferences.getBoolean(key, false);
    }


    public void removeData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void removeDataByKey(String key) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
