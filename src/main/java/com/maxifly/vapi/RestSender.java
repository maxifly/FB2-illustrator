package com.maxifly.vapi;

import com.maxifly.fb2_illustrator.fb2_xml.model.FictionBook;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.vapi.model.*;
import com.maxifly.vapi.model.REST.REST_FileUpload;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Maximus on 24.05.2016.
 */
public class RestSender {

    public static void respDelay() throws InterruptedException {
        Thread.sleep(500);
    }
    public RestResponse sendGet(String url){
        try {
            respDelay();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sendGet_withoutDely(url);
    }

    public RestResponse sendGet_withoutDely(String url) {
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


    public RestResponse sendPOST_uploadFiles(String url, List<REST_FileUpload> files) {
        RestResponse result = new RestResponse();
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        try {
            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);

            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream request = new DataOutputStream(
                    con.getOutputStream());

            // Перебираем файлы и записываем их в запрос

            for (REST_FileUpload fileUploadInfo :files) {
                request.writeBytes(twoHyphens + boundary + crlf);
                request.writeBytes("Content-Disposition: form-data; name=\"" +
                        fileUploadInfo.field_name + "\";filename=\"" +
                        fileUploadInfo.file.getCanonicalPath() + "\"" + crlf);
                request.writeBytes(crlf);
            }





        } catch (Exception e) {
            e.printStackTrace();
            result.setResponseCode(500);
        }
        return result;
    }




    private FictionBook.Binary genBinary(Illustration ill) {
        Path path = ill.getFile();
        try {
            byte[] data = Files.readAllBytes(path);

            FictionBook.Binary fictionBookBinary = objectFactory.createFictionBookBinary();
            fictionBookBinary.setValue(data);
            fictionBookBinary.setId(ill.getId().toString());
            fictionBookBinary.setContentType("image/jpeg");

            return fictionBookBinary;

        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error when create binary: {}", e);
            return null;
        }


    }
}
