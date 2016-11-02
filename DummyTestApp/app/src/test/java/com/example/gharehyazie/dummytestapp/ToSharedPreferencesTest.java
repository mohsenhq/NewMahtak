package com.example.gharehyazie.dummytestapp;

import android.app.Application;

import org.apache.tools.ant.types.Environment;
import org.codehaus.plexus.context.Context;
import org.codehaus.plexus.context.ContextException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.*;

/**
 * Created by MahTak on 11/2/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml", packageName = "com.example.gharehyazie.dummytestapp")
public class ToSharedPreferencesTest {

    ToSharedPreferences shp;
    Application context;

    @Before
    public void setUp() throws Exception {
        shp = new ToSharedPreferences();
        context = RuntimeEnvironment.application;
    }

    @Test
    public void generateUUID() throws Exception {
        //Arrange
        String UUID = shp.getStringFromPreferences(context,null,"UUID","deviceID");

        //Act
        shp.generateUUID(context);

        //Assert
        assertNotNull("no UUID",UUID);
    }


    @Test
    public void putGet() throws Exception {
        //Arrange
        String key = "key";
        String test = "1234 abc";
        String fileKey = "file";
        //Act
        shp.putStringInPreferences(context,key,test,fileKey);

        //Assert
        assertEquals("not Equal",test,shp.getStringFromPreferences(context,null,key,fileKey));
    }



}