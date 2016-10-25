package com.example.gharehyazie.dummytestapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Mohsen on 10/25/2016.
 */

public class LifeCycleReporter implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.e(activity.getClass().getSimpleName(), "onCreate(Bundle)");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e(activity.getClass().getSimpleName(), "onStart()");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.e(activity.getClass().getSimpleName(), "onResume()");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.e(activity.getClass().getSimpleName(), "onPause()");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.e(activity.getClass().getSimpleName(), "onSaveInstanceState(Bundle)");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e(activity.getClass().getSimpleName(), "onStop()");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e(activity.getClass().getSimpleName(), "onDestroy()");
    }








}
