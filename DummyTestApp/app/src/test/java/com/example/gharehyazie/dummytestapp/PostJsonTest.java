package com.example.gharehyazie.dummytestapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by MahTak on 11/2/2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml", packageName = "com.example.gharehyazie.dummytestapp")

public class PostJsonTest {

    @Test
    public void get() throws Exception {
        URL url = new URL("http://46.101.146.4:8081");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Content-Type", "application/json"); // data type = json
        httpURLConnection.connect();
        BufferedReader in = null;
        in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        JSONArray jsonArray= new JSONArray(in.readLine());
        jsonArray.remove(1);
        assertEquals("not the same",jsonArray.toString(),"hi");
    }

    @Test
    public void send() throws Exception {
        //Arrange
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("test","one");
        String t1 = jsonObject.toString();

        //Act
        String respond =new PostJson().postData(t1);

        //Assert

        assertEquals(respond,"2001");
    }

}