package com.quangtd.qsokoban.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by hainguyen on 8/24/17.
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils mInstance = null;

    private SharedPreferencesUtils() {
    }

    public static SharedPreferencesUtils getInstance() {
        synchronized (SharedPreferencesUtils.class) {
            if (mInstance == null) {
                mInstance = new SharedPreferencesUtils();
            }
            return mInstance;
        }
    }

    private SharedPreferences getSharedPreferences(Context context) {
        String SHARE_PREFERENCE = "QSokobanSharePreference";
        return context.getSharedPreferences(SHARE_PREFERENCE, Context.MODE_PRIVATE);
    }

    public boolean getBool(Context context, String key) {
        return this.getSharedPreferences(context).getBoolean(key, false);
    }

    public float getFloat(Context context, String key) {
        return getSharedPreferences(context).getFloat(key, -1.0F);
    }

    public int getInt(Context context, String key) {
        return getSharedPreferences(context).getInt(key, -1);
    }

    public long getLong(Context context, String key) {
        return getSharedPreferences(context).getLong(key, -1L);
    }

    public String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    public void setBool(Context context, String key, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void setFloat(Context context, String key, float value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void setInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void setString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setObject(Context context, String key, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, json);
        editor.apply();
    }

    public Object getObject(Context context, String key, Class<?> object) {
        try {
            Gson e = new Gson();
            String json = getSharedPreferences(context).getString(key, null);
            Object obj = e.fromJson(json, object);
            return obj;
        } catch (Exception var6) {
            return null;
        }
    }

    public boolean isExists(Context context, String key) {
        try {
            return getSharedPreferences(context).getString(key, null) != null;
        } catch (Exception var3) {
            return true;
        }
    }

    public void remove(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(key);
        editor.apply();
    }
}
