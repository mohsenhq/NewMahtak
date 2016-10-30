package com.example.gharehyazie.dummytestapp;

/**
 * Created by Mohsen on 10/25/2016.
 * <p>
 * This class is for logging activities life cycle and seconds each activity is used
 * and if devise is online send data to server and if not save data to shared preferences
 * to send next time the device is online.
 * also at first time app is installed it creates a unique UUID for each device and
 * save it to the shared preferences.
 * <p>
 */


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * The type Life cycle reporter.
 */
public class LifeCycleReporter implements Application.ActivityLifecycleCallbacks {

    /**
     * The Duration map.
     */
    Map<String, Long> durationMap = new HashMap<String, Long>();
    /**
     * The Time map.
     */
    Map<String, Long> timeMap = new HashMap<String, Long>();

    /**
     * name of the app main calls for using in onDestroyed method
     */
    String mainActivity = null;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    /**
     * @param activity = context that contains the info of the onStarted activity.
     *                 mainActivity sets app main activity name.
     */
    @Override
    public void onActivityStarted(Activity activity) {
        /**
         * If condition works when app installed and opend for the first time
         */
        if (mainActivity == null) {
            mainActivity = activity.getClass().getSimpleName();
            new ToSharedPreferences().generateUUID(activity);
            /**
             * sets the current date
             */
            durationMap.put("date", System.currentTimeMillis());
        }
        /**
         * sets the onStart time
         */
        timeMap.put(activity.getClass().getSimpleName(), System.currentTimeMillis());
    }

    @Override
    public void onActivityResumed(Activity activity) {
//        Log.e(activity.getClass().getSimpleName(), "onResume()");
    }

    /**
     * @param activity = context that contains the info of the onStarted activity.
     *                 difference calculates the time in seconds form on start to on pause of activity and add the value to the
     *                 durationMap
     */
    @Override
    public void onActivityPaused(Activity activity) {
        Long difference = (System.currentTimeMillis() - timeMap.get(activity.getClass().getSimpleName())) / 1000;
        try {
            durationMap.put(activity.getClass().getSimpleName(), durationMap.get(activity.getClass().getSimpleName()) + difference);
        } catch (Exception e) {
            durationMap.put(activity.getClass().getSimpleName(), difference);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//        Log.e(activity.getClass().getSimpleName(), "onSaveInstanceState(Bundle)");
    }

    @Override
    public void onActivityStopped(Activity activity) {
//        Log.e(activity.getClass().getSimpleName(), "onStop()");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        /**
         * condition checks if the app main activity is closed meaning the app is closed
         */

        if (mainActivity.equals(activity.getClass().getSimpleName())) {

            /**
             * save both deviceID from shared preferences and durationMap to result JSONObject
             */
            ToSharedPreferences SHP = new ToSharedPreferences();
            Map lifeCycleID = new HashMap<>();
            lifeCycleID.putAll(SHP.getAll(activity, "deviceID"));
            lifeCycleID.putAll(durationMap);
            JSONObject result = new JSONObject(lifeCycleID);
            /**
             * converts date value to date format from milliseconds
             */
            try {
                String date = String.valueOf(new Date((Long) result.get("date")));
                result.put("date", date);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /**
             * when device is online  checks if there is any on sent data and send them + current data
             * and if device is offline saves the data to shared preferences for next time bye file key "data"
             */
            if (new PostJson().isOnline(activity)) {
                for (int i = SHP.getAll(activity, "data").size(); i == 0; i--) {
                    String s = String.valueOf(i);
                    if (SHP.getStringFromPreferences(activity, null, s, "data") != null) {
                        new PostJson().execute(SHP.getStringFromPreferences(activity, null, s, "data"));
                        SHP.putStringInPreferences(activity, s, null, "data");
                    }
                }


            } else {
                int i = SHP.getAll(activity, "data").size();
                String s = String.valueOf(i) + 1;
                SHP.putStringInPreferences(activity, s, result.toString(), "data");

            }
        }
        /**
         * unregisters the ActivityLifecycleCallbacks for preventing duplicate data on the next start of app
         */
        activity.getApplication().unregisterActivityLifecycleCallbacks(this);
    }

}
