package com.example.mahtak;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLocationManager;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by MahTak on 1/14/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "AndroidManifest.xml")
// ".\\src\\debug\\AndroidManifest.xml"
public class LocationReporterTest {
    LocationReporter lr;

    protected Context instance() {
        ShadowApplication shadowApp = shadowOf(RuntimeEnvironment.application);
        shadowApp.grantPermissions("android.permission.INTERNET");

        return shadowApp.getApplicationContext();
    }

    @Before
    public void setUp() throws Exception {
        Context context= instance();
        lr = new LocationReporter(context);
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.setTestProviderEnabled("gps",true);
        ShadowLocationManager shadowLocationManager = shadowOf(lm);
//        System.out.println(lm.getAllProviders());

        System.out.println(shadowLocationManager.getAllProviders());


        Location location = new Location("gps");
        location.setLatitude(10.0);
        location.setLongitude(20.0);
        location.setTime(System.currentTimeMillis());

        shadowLocationManager.simulateLocation(location);

        System.out.println(shadowLocationManager.getLastKnownLocation("gps"));
    }

    @Test
    public void test() {
        System.out.println(lr.locationList);
        assertNotNull("", lr.locationList);
    }

}