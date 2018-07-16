package com.example.mahtak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MahTak on 12/27/2016.
 */

public class MainA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LifeCycleReporter reporter = new LifeCycleReporter();
        getApplication().registerActivityLifecycleCallbacks(reporter);

        super.onCreate(savedInstanceState);
    }


}
