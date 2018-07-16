package com.example.mahtak;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Saves data to shared preferences privately that only the app can access.
 */

public class ToSharedPreferences {

    Context maincontext;

    /**
     * Generates UUID an id that is unique for each installation and saves
     * the Model ,Brand ,Manufacturer and SDK int for the first time app opens after
     * installation.
     *
     * @param context the context of called class
     * @maincontext gets the starting context to determine closing app.
     */
    public void generateUUID(Context context) {
        maincontext = context;
        /**
         * saves UUID ,Model ,Brand ,Manufacturer and SDK int to the sharedPreferences by file key "deviceID" privately.
         */
        SharedPreferences sharedPrefs = context.getSharedPreferences("deviceID", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        if (!sharedPrefs.contains("UUID")) {

            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();
            String macAddress = info.getMacAddress();
            ed = sharedPrefs.edit();
            String uniqueID = String.valueOf(UUID.randomUUID());
            ed.putString("macAddress", macAddress);
            ed.putString("UUID", uniqueID);
            ed.putString("Model", Build.MODEL);
            ed.putString("Brand", Build.BRAND);
            ed.putString("Manufacturer", Build.MANUFACTURER);
            ed.putString("SDK int", String.valueOf(Build.VERSION.SDK_INT));
            ed.putString("install date", String.valueOf(new Date((Long) System.currentTimeMillis())));

            ed.putString("PACKAGE_NAME", context.getPackageName());

            PackageInfo pInfo = null;
            try {
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                ed.putString("versuinName", pInfo.versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            ed.apply();

        }
    }

    /**
     * Puts given string in shared preferences.
     *
     * @param context the context of called class
     * @param key     String to be saved
     * @param value   String of key param to be saved
     * @param fileKey the file key to save
     */
    public void putStringInPreferences(Context context, String key, String value, String fileKey) {
        generateUUID(context);

        SharedPreferences sharedPreferences = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();

    }

    /**
     * Gets string from shared preferences.
     *
     * @param context      the context
     * @param defaultValue if the key doesn't exists
     * @param key          the key to read
     * @param fileKey      the key of file to read
     * @return string value of called key from preferences
     */
    public String getStringFromPreferences(Context context, String defaultValue, String key, String fileKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE);
        String temp = sharedPreferences.getString(key, defaultValue);
        return temp;
    }


    /**
     * Gets all keys and values of given file key.
     *
     * @param context the context
     * @param fileKey the key of file to read
     * @return the all keys and values of them as a Map from given fileKey
     */
    public Map<String, ?> getAll(Context context, String fileKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE);
        return sharedPreferences.getAll();

    }

    /**
     * Removes all data in the given fileKey
     *
     * @param context the context
     * @param fileKey the key of file to remove
     */
    public void removeAll(Context context, String fileKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

    }

    /**
     * Puts given string from custom action in shared preferences.
     *
     * @param key   String to be saved
     */
    public void addCustomRecord(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("temp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        key = "CustomEvent_" + key;
        if (!sharedPreferences.contains(key)) {
            editor.putInt(key,1);
        } else {
            int v=sharedPreferences.getInt(key,0);
            editor.putInt(key, v+1);
        }
        editor.apply();

    }
}
