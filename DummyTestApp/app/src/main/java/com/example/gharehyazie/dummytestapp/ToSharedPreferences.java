package com.example.gharehyazie.dummytestapp;
/**
 * Created by Mohsen on 10/24/2016.
 * <p>
 * saves data to shared preferences privately that only this app can access.
 * <p>
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Map;
import java.util.UUID;


public class ToSharedPreferences {

    /**
     * Generate uuid.
     * an id that is unique for each installation
     *
     * @param context the context of called class
     */
    public static void generateUUID(Context context) {

        /**
         * saves UUID ,Model ,Brand ,Manufacturer and SDK int to the sharedPreferences by file key "deviceID" privately.
          */
        SharedPreferences sharedPrefs = context.getSharedPreferences("deviceID", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        if (!sharedPrefs.contains("UUID")) {
            ed = sharedPrefs.edit();
            String uniqueID = String.valueOf(UUID.randomUUID());
            ed.putString("UUID", uniqueID);
            ed.putString("Model", Build.MODEL);
            ed.putString("Brand", Build.BRAND);
            ed.putString("Manufacturer", Build.MANUFACTURER);
            ed.putString("SDK int", String.valueOf(Build.VERSION.SDK_INT));
            ed.commit();
        }
    }

    /**
     * Put string in preferences.
     *
     * @param context the context of called class
     * @param key     the key as String to be saved
     * @param value   the value of key param as String to be saved
     * @param FileKey the file key to save
     */
    public void putStringInPreferences(Context context, String key, String value, String FileKey) {
        generateUUID(context);

        SharedPreferences sharedPreferences = context.getSharedPreferences(FileKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();


    }

    /**
     * Gets string from preferences.
     *
     * @param context      the context
     * @param defaultValue the default value
     * @param key          the key to read
     * @param pKey         the pkey the file key to read
     * @return the string from preferences
     */
    public String getStringFromPreferences(Context context, String defaultValue, String key, String pKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(pKey, Context.MODE_PRIVATE);
        String temp = sharedPreferences.getString(key, defaultValue);
        return temp;
    }


    /**
     * Gets all.
     *
     * @param context the context
     * @param pKey    the pkey the file key to read
     * @return the all data on the file by pkey
     */
    public Map<String, ?> getAll(Context context, String pKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(pKey, Context.MODE_PRIVATE);
        return sharedPreferences.getAll();
    }
}
