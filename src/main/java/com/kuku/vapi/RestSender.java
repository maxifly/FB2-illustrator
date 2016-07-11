package com.kuku.vapi;

import com.kuku.vapi.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Maximus on 24.05.2016.
 */
public class RestSender {

    public static void respDelay() throws InterruptedException {
        Thread.sleep(500);
    }

    public RestResponse sendGet(String url) {
        RestResponse result = new RestResponse();

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            result.setResponseCode(con.getResponseCode());

            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + result.getResponseCode());
            BufferedReader in = null;
            try {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                result.setResponseBody(response);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResponseCode(500);
        }

        return result;

    }


}
