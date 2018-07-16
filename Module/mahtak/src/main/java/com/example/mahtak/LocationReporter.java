package com.example.mahtak;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by MahTak on 1/1/2017.
 */
public class LocationReporter {

    /**
     * Object of ToSharedPreferences for saving location list
     */
    ToSharedPreferences SHP;
    /**
     * The list of Locations
     */
    public ArrayList<Location> locationList;

    /**
     * Instantiates a new Location reporter.
     *
     * @param context the context
     */
    public LocationReporter(final Context context) {
        SHP = new ToSharedPreferences();
        locationList = new ArrayList<Location>();
        LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        locationList.add(locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER));
//        Log.e("location",locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER).toString());
        LocationListener locationListener = new LocationListener() {
            /**
             * On location update save location to the sharedPreferences
             * @param location, updated location
             */
            @Override
            public void onLocationChanged(Location location) {
                locationList.add(location);
                SHP.putStringInPreferences(context, "Locations", locationList.toString(), "temp");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        /**
         * Register Network and Gps provider to update location every 10 minutes
         */

        // If app have the permission requests location update otherwise asks for permission
        if (
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // checks if permission never ask again is marked
            boolean shouldAsk = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION);
            if (shouldAsk) {
                showMessageOKCancel("You need to allow access to Location", (Activity) context,
                        // dialog for adding permission
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 11);

                            }
                        });
            }
            return;
        }


//  requests for GPS and network base location every 10 minutes
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
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