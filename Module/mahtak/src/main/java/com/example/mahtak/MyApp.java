package com.example.mahtak;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;


@ReportsCrashes(formUri = "http://198.143.180.135:8082",
        reportType = HttpSender.Type.JSON,
        httpMethod = HttpSender.Method.POST,
//        formUriBasicAuthLogin = "tubtakedstinumenterences",
//        formUriBasicAuthPassword = "igqMFFMatvtMXVCKgy7u6a5W",
        customReportContent = {
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION,
                ReportField.PACKAGE_NAME,
                ReportField.REPORT_ID,
                ReportField.BUILD,
                ReportField.STACK_TRACE
        },
        mode = ReportingInteractionMode.SILENT,
        formKey = "")
public class MyApp extends Application {

        @Override
        public void onCreate() {
                super.onCreate();
                System.out.println(this.getClass().getAnnotation(ReportsCrashes.class));
                // The following line triggers the initialization of ACRA
                ACRA.init(this);
        }
}
