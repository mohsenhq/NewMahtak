package com.example.gharehyazie.dummytestapp;

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

import static org.junit.Assert.assertEquals;

/**
 * Created by MahTak on 11/2/2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml", packageName = "com.example.gharehyazie.dummytestapp")

public class PostJsonTest {

    @Test
    public void databaseSendTest() throws Exception {
        //Send

        JSONObject jsonObjectToSend = new JSONObject();
        jsonObjectToSend.put("test", "one");
        String test = jsonObjectToSend.toString();
        new PostJson().postData(test);

        //Receive
        URL url = new URL("http://46.101.146.4:8081");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Content-Type", "application/json"); // data type = json
        httpURLConnection.connect();
        BufferedReader in = null;
        in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        JSONArray jsonArray = new JSONArray(in.readLine());
        JSONObject jsonObjectReceived = jsonArray.getJSONObject(0);
        jsonObjectReceived.remove("_id");


        assertEquals("not the same", jsonObjectToSend.toString(), jsonObjectReceived.toString());
    }



}