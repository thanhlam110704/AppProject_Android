package com.example.appproject.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    private static final String PREF_NAME = "SessionPref";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    public static final String KEY_IDUSER = "iduser"; // Sửa từ int thành String
    public static final String KEY_USERNAME = "username";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String username, int id) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_IDUSER, String.valueOf(id));
        editor.putString(KEY_USERNAME, username);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        String id = sharedPreferences.getString(KEY_IDUSER, null);
        String username = sharedPreferences.getString(KEY_USERNAME, null);

        user.put(KEY_IDUSER, String.valueOf(id)); // Chuyển đổi kiểu int thành String để đặt vào HashMap
        user.put(KEY_USERNAME, username);
        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
