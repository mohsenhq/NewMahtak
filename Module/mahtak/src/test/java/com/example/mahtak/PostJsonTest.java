package com.example.mahtak;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static junit.framework.Assert.assertEquals;

/**
 * Created by MahTak on 11/2/2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "Module/mahtak/src/main/AndroidManifest.xml", packageName = "com.example.mahtak")

public class PostJsonTest {

    @Test
    public void databaseSendTest() throws Exception {
        //Send
        //Read testStrings file extra spaces and breaks should be removed
        String sendString = "";
        char current;
        File file = new File("src/test/java/com/example/mahtak/testStrings");
        FileInputStream fis = new FileInputStream(file);

        while (fis.available() > 0) {
            current = (char) fis.read();
            sendString = sendString + String.valueOf(current);
        }
        //Splits JsonObjects to send them one by one to server
        String[] strings = sendString.split(";");


        String receivedString = "";
        for (String s : strings) {
            new PostJson().postData(s);

            Thread.sleep(2000);

            //Receive
            //Get last data entered in database
            URL url = new URL("http://198.143.180.135:8081");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // data type = json
            httpURLConnection.connect();
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            JSONArray jsonArray = new JSONArray(in.readLine());
            JSONObject jsonObjectReceived = jsonArray.getJSONObject(0);
            jsonObjectReceived.remove("_id");

            //Put all received data in one string
            receivedString += jsonObjectReceived.toString() + ";";
        }

        //Check if send and recived data are the same
        assertEquals("not the same", sendString, receivedString);
    }


}