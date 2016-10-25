package com.example.gharehyazie.dummytestapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohsen on 10/25/2016.
 */

public class LifeCycleReporter implements Application.ActivityLifecycleCallbacks {
    Map<String,Long> durationMap=new HashMap<String, Long>();


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//        Log.e(activity.getClass().getSimpleName(), "onCreate(Bundle)");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        durationMap.put(activity.getClass().getSimpleName(),System.currentTimeMillis());
        Log.e(activity.getClass().getSimpleName(), "onStart()");
    }

    @Override
    public void onActivityResumed(Activity activity) {
//        Log.e(activity.getClass().getSimpleName(), "onResume()");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Long difference =(System.currentTimeMillis()- durationMap.get(activity.getClass().getSimpleName()))/1000 ;
        try {
            durationMap.put(activity.getClass().getSimpleName() + "Duration",durationMap.get(activity.getClass().getSimpleName()+"Duration" )+difference);
        }catch (Exception e){
            durationMap.put(activity.getClass().getSimpleName() + "Duration",difference);
        }
        Log.e(activity.getClass().getSimpleName(), "onPause()" + durationMap.get(activity.getClass().getSimpleName()+"Duration"));
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
        Log.e(activity.getClass().getSimpleName(), "onDestroy()");
    }








}
