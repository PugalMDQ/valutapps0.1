package com.vibro.care.Config;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.vibro.care.Activity.LoginActivity;

public class AppPref {

    private static final String PREF_NAME = "AppData";
    final SharedPreferences pref;
    final SharedPreferences.Editor editor;
    final Context _context;
    final int PRIVATE_MODE = 0;

    public AppPref(Context mContext) {
        this._context = mContext;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        _context.startActivity(new Intent(_context, LoginActivity.class));
        System.exit(0);
    }

    public void putResponse(String name, String data){
        editor.putString(name, data);
        editor.commit();
    }

    public String getResponse(String name) {
        return pref.getString(name, "null");
    }
}
