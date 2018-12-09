package com.example.advancedalarm;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class SharedPreferencesOperator {
    private static Gson gson = new Gson();

    public static void putList(SharedPreferences preferences, String key, ArrayList<?> list){
        String listString = gson.toJson(list);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, listString);
        editor.apply();
    }

    public static void getListToList(List toList, SharedPreferences preferences, String key, Type listType){
        String listString = preferences.getString(key, null);
        if (listString != null) {
            toList = gson.fromJson(listString, listType);
        }
    }
}
