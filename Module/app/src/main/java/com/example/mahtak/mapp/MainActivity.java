package com.example.mahtak.mapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mahtak.LifeCycleReporter;
import com.example.mahtak.ToSharedPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LifeCycleReporter reporter = new LifeCycleReporter();
        getApplication().registerActivityLifecycleCallbacks(reporter);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_crash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crashString = null;
                crashString.length();
            }
        });
    }

    public void custom1(View v) {
        ToSharedPreferences t = new ToSharedPreferences();
        t.addCustomRecord(getApplicationContext(), "customButton1");
    }

    public void custom2(View v) {
        ToSharedPreferences t = new ToSharedPreferences();
        t.addCustomRecord(getApplicationContext(), "customButton2");
    }

    public void custom3(View v) {
        ToSharedPreferences t = new ToSharedPreferences();
        t.addCustomRecord(getApplicationContext(), "customButton3");
    }

}
