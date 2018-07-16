//package com.example.mahtak;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.pm.PackageManager;
//import android.support.v4.app.ActivityCompat;
//
//
///**
// * Created by MahTak on 2/1/2017.
// */
//
//public class PermissionChecker {
//
//    public boolean PermissionChecker(final String permission, final Context context, String message) {
//        if (
//                ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
////                        || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                ) {
//
//            // checks if never ask again is marked
//            boolean shouldAsk = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);
//            if (shouldAsk) {
//                showMessageOKCancel(message, (Activity) context,
//                        // dialog for adding permission
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 11);
//
//                            }
//                        });
//            }
//            return false;
//        }else {
//            return true;
//        }
//    }
//
//    // dialog to show before asking the setting change
//    private void showMessageOKCancel(String message, Context context, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(context)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
//}
