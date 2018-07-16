package com.example.mahtak;

/**
 * Created by Mohsen on 10/25/2016.
 */


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The type Life cycle reporter.
 * This class is for logging activities life cycle and seconds each activity is
 * used.
 * <p>
 * If device was online sends data to server and if not save data to
 * shared preferences to send it next time the device is online.
 * If the app was terminated it will save the data to shared preferences
 * and send it next time.
 * Also at first time app is installed it creates a unique UUID for each
 * device and save it to the shared preferences.
 */
public class LifeCycleReporter implements Application.ActivityLifecycleCallbacks {

    ToSharedPreferences SHP;
    LocationReporter locationReporter;
    /**
     * The Time map.
     */
    Map<String, Long> timeMap = new LinkedHashMap<>();

    /**
     * Name of the app main calls for using in onDestroyed method.
     */
    Context mainActivity = null;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    /**
     * Logs the started activities and time of them and the date and time which
     * app used.
     * <p>
     * If the app is just installed at first time it opens Generates device UUID.
     * If last time app was terminated creates Json body for post
     *
     * @param activity Activity that is Started.
     */
    @Override
    public void onActivityStarted(Activity activity) {
        SHP = new ToSharedPreferences();

        /**
         * If condition works when app installed and opened for the first time
         */
        if (mainActivity == null) {
            mainActivity = activity;
            // gets the phone state information
            getPhoneStateInpho();
            // registers the location tracker class
            locationReporter = new LocationReporter(mainActivity);
            // if temp data not cleared (sent) send it with terminated true flag
            if (SHP.getStringFromPreferences(mainActivity, "0", mainActivity.getClass().getSimpleName(), "temp") != "0") {
                SHP.putStringInPreferences(mainActivity, "Terminated", "true", "temp");
                createPostJsonBody();
            }
            new ToSharedPreferences().generateUUID(activity);

            SHP.putStringInPreferences(mainActivity, "date", String.valueOf(new Date((Long) System.currentTimeMillis())), "temp");
        }
        /**
         * Sets the onStart time
         */
        timeMap.put(activity.getClass().getSimpleName(), System.currentTimeMillis());
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    /**
     * Logs the Activity paused time and time it was on resume and save it in
     * shared preferences.
     * <p>
     *
     * @param activity Activity that is Paused.
     */
    @Override
    public void onActivityPaused(Activity activity) {
        Long difference = (System.currentTimeMillis() - timeMap.get(activity.getClass().getSimpleName())) / 1000;

        String differenceString = String.valueOf(Long.valueOf(SHP.getStringFromPreferences(mainActivity, "0", activity.getClass().getSimpleName(), "temp")) + difference);
        // saves  paused activity duration
        SHP.putStringInPreferences(mainActivity, activity.getClass().getSimpleName(), differenceString, "temp");
        // saves time of last paused activity
        SHP.putStringInPreferences(mainActivity, "endDate", String.valueOf(new Date((Long) System.currentTimeMillis())), "temp");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    /**
     * If the destroyed activity be the app mainActivity calls the createPostJsonBody method
     * and unregisters lifeCycle class.
     *
     * @param activity Activity that is Destroyed
     */
    @Override
    public void onActivityDestroyed(Activity activity) {
        /**
         * Condition checks if the app main activity is closed meaning the app is closed
         */

        if (mainActivity.equals(activity)) {
            SHP.putStringInPreferences(mainActivity, "endDate", String.valueOf(new Date((Long) System.currentTimeMillis())), "temp");
            createPostJsonBody();

            /**
             * Unregisters the ActivityLifecycleCallbacks for preventing duplicate data on the next start of app
             */
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }
    }

    /**
     * Joins the Device ID and app Log into a JSONObject.
     * If the device was offline save it for next time online and if was online
     * sends current and previous data.
     */
    public void createPostJsonBody() {
        /**
         * save both deviceID from shared preferences and temp to result JSONObject
         */


        Map lifeCycleID = new LinkedHashMap<>();
        lifeCycleID.putAll(SHP.getAll(mainActivity, "deviceID"));
        lifeCycleID.putAll(SHP.getAll(mainActivity, "temp"));
        SHP.removeAll(mainActivity, "temp");

        JSONObject result = new JSONObject(lifeCycleID);

        /**
         * When device is online  checks if there is any on sent data and send them + current data
         * and if device is offline saves the data to shared preferences for next time bye file key "data"
         */
        if (new PostJson().isOnline(mainActivity)) {
            Map<String, ?> keys = SHP.getAll(mainActivity, "data");
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                if (SHP.getStringFromPreferences(mainActivity, null, entry.getKey(), "data") != null) {
                    new PostJson().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,SHP.getStringFromPreferences(mainActivity, null, entry.getKey(), "data"));
                    SHP.putStringInPreferences(mainActivity, entry.getKey(), null, "data");
                }
            }

            new PostJson().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,result.toString());

        } else {
            int i = SHP.getAll(mainActivity, "data").size() + 1;
            String s = String.valueOf(i);
            SHP.putStringInPreferences(mainActivity, s, result.toString(), "data");
        }
    }


    public void getPhoneStateInpho() {
        // If app have the permission requests Read phone state update otherwise asks for permission
        if (
                ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            // checks if never ask again is marked
            boolean shouldAsk = ActivityCompat.shouldShowRequestPermissionRationale((Activity) mainActivity, Manifest.permission.READ_PHONE_STATE);
            if (shouldAsk) {
                showMessageOKCancel("You need to allow access to read Phone State", mainActivity,
                        // dialog for adding permission
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) mainActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, 12);

                            }
                        });
            }
            return;
        }
        TelephonyManager tm = (TelephonyManager) mainActivity.getSystemService(Context.TELEPHONY_SERVICE);
        SHP.putStringInPreferences(mainActivity, "phone Number", tm.getLine1Number(), "temp");
        SHP.putStringInPreferences(mainActivity, "Network Operator name", tm.getNetworkOperatorName(), "temp");
        SHP.putStringInPreferences(mainActivity, "Sim Operator name", tm.getSimOperatorName(), "temp");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SHP.putStringInPreferences(mainActivity, "data network Type", String.valueOf(tm.getDataNetworkType()), "temp");
        }
        ConnectivityManager cm = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        SHP.putStringInPreferences(mainActivity, "connection Type", netInfo.getTypeName(), "temp");

    }

    // dialog to show before asking the setting change
    private void showMessageOKCancel(String message, Context context, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
