package com.example.gharehyazie.dummytestapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohsen on 10/25/2016.
 */

public class LifeCycleReporter implements Application.ActivityLifecycleCallbacks {
    Map<String, Long> durationMap = new HashMap<String, Long>();
    Map<String, Long> timeMap = new HashMap<String, Long>();
    String mainActivity = null;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//        Log.e(activity.getClass().getSimpleName(), "onCreate(Bundle)");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (mainActivity == null) {
            mainActivity = activity.getClass().getSimpleName();
            new ToSharedPreferences().generateUUID(activity);
            durationMap.put("date", System.currentTimeMillis());
        }
        Log.e("mainActivity", mainActivity);
        timeMap.put(activity.getClass().getSimpleName(), System.currentTimeMillis());
        Log.e(activity.getClass().getSimpleName(), "onStart()");
    }

    @Override
    public void onActivityResumed(Activity activity) {
//        Log.e(activity.getClass().getSimpleName(), "onResume()");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Long difference = (System.currentTimeMillis() - timeMap.get(activity.getClass().getSimpleName())) / 1000;
        try {
            durationMap.put(activity.getClass().getSimpleName(), durationMap.get(activity.getClass().getSimpleName()) + difference);
        } catch (Exception e) {
            durationMap.put(activity.getClass().getSimpleName(), difference);
        }
        Log.e(activity.getClass().getSimpleName(), "onPause()" + durationMap.get(activity.getClass().getSimpleName()));
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
        if (mainActivity.equals(activity.getClass().getSimpleName())) {

            ToSharedPreferences SHP = new ToSharedPreferences();
            PostJson send = new PostJson();
            if (send.isOnline(activity)) {
                HashMap lifeCycleID = new HashMap<>();
                lifeCycleID.putAll(SHP.getAll(activity, "deviceID"));
                lifeCycleID.putAll(durationMap);
                JSONObject result = new JSONObject(lifeCycleID);
                try {
                    String date = String.valueOf(new Date((Long) result.get("date")));
                    result.put("date", date);
                    Log.e("date", result.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                send.execute(result.toString());

            } else {

            }
        }
    }
}
