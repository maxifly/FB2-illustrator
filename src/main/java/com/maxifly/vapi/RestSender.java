package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.fb2_xml.model.FictionBook;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.vapi.model.*;
import com.maxifly.vapi.model.REST.REST_FileUpload;
import com.maxifly.vapi.model.REST.REST_POST_Data;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.*;
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
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(RestSender.class.getName());

    public static void respDelay() throws InterruptedException {

        Thread.sleep(500);

    }

    public RestResponse sendGet(String url) throws InterruptedException {
        try {
            respDelay();
        } catch (InterruptedException e) {
            e.printStackTrace();

            if (Thread.interrupted()) Thread.currentThread().interrupt();
            throw e;
        }
        return sendGet_withoutDely(url);
    }

    public RestResponse sendPost(REST_POST_Data rest_post_data) {
        try {
            respDelay();
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (Thread.interrupted()) Thread.currentThread().interrupt();
        }
        return sendPost_withoutDely(rest_post_data);
    }

    public RestResponse sendGet_withoutDely(String url) {
        RestResponse result = new RestResponse();
        HttpURLConnection con = null;

        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();

            System.out.println("\nSending 'GET' request to URL : " + url);
            con.setRequestMethod("GET");
            result.setResponseCode(con.getResponseCode());


            System.out.println("Response Code : " + result.getResponseCode());
            readRestResponse(result, con);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResponseCode(500);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return result;

    }


    public RestResponse sendPost_withoutDely(REST_POST_Data rest_post_data) {
        RestResponse result = new RestResponse();
        HttpURLConnection con = null;

        try {
            URL obj = new URL(rest_post_data.url);
            con = (HttpURLConnection) obj.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);

            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty(
                    "Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", "" + rest_post_data.body.getBytes().length);

            DataOutputStream request = new DataOutputStream(
                    con.getOutputStream());

            request.writeBytes(rest_post_data.body);
            request.flush();
            request.close();

            result.setResponseCode(con.getResponseCode());

            log.debug("Sending 'POST' request to URL : {} \n body {} ", rest_post_data.url, rest_post_data.body);
            log.debug("Response Code : {}", result.getResponseCode());
            readRestResponse(result, con);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResponseCode(500);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return result;

    }

    private void readRestResponse(RestResponse result, HttpURLConnection con) {
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
    }


    public RestResponse sendPOST_uploadFiles(String url, List<REST_FileUpload> files) {
        log.debug("Upload file to upload server {}", url);


        RestResponse result = new RestResponse();
        HttpURLConnection con = null;
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            URL obj = new URL(url);

            con = (HttpURLConnection) obj.openConnection();
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

            for (REST_FileUpload fileUploadInfo : files) {
                log.debug("Upload file: " + fileUploadInfo.toString());


                request.writeBytes(twoHyphens + boundary + crlf);
                request.writeBytes("Content-Disposition: form-data; name=\"" +
                        fileUploadInfo.field_name
                        + "\";filename=\""
                        + ((fileUploadInfo.resourcePath != null) ? fileUploadInfo.resourcePath : fileUploadInfo.file.getCanonicalPath())
                        + "\"" + crlf);
                request.writeBytes(crlf);
                // TODO Обеспечить передачу ресурса по имени и его чтение

                if (fileUploadInfo.resourcePath == null) {
                    exportFile(fileUploadInfo.file, request);
                } else {
                    exportResourceFile(fileUploadInfo.resourcePath, request);
                }


//                byte[] data = Files.readAllBytes(fileUploadInfo.file.toPath());
//                request.write(data);
                request.writeBytes(crlf);

            }

            request.writeBytes(twoHyphens + boundary +
                    twoHyphens + crlf);

            request.flush();
            request.close();

            readRestResponse(result, con);

            log.debug("Response code of upload files: {}", result.getResponseCode());
            log.debug("Response body of upload files: {}", result.getResponseBody());


        } catch (Exception e) {
            e.printStackTrace();
            result.setResponseCode(500);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return result;
    }

    private void exportResourceFile(String resourceName, DataOutputStream dataOutputStream) throws IOException {
        int readBytes;
        byte[] buffer = new byte[4096];

        try (InputStream inputStream = Factory_GUI.class.getResourceAsStream(resourceName)) {
            while ((readBytes = inputStream.read(buffer)) > 0) {
                log.debug("read {} bytes", readBytes);
                dataOutputStream.write(buffer, 0, readBytes);
            }
        }
    }

    private void exportFile(File file, DataOutputStream dataOutputStream) throws IOException {
        int readBytes;
        byte[] buffer = new byte[4096];

        try (InputStream inputStream = new FileInputStream(file)) {
            while ((readBytes = inputStream.read(buffer)) > 0) {
                dataOutputStream.write(buffer, 0, readBytes);
            }
        }
    }


//    /**
//     * Export a resource embedded into a Jar file to the local file path.
//     *
//     * @param resourceName ie.: "/SmartLibrary.dll"
//     * @return The path to the exported resource
//     * @throws Exception
//     */
//    static public String ExportResource(String resourceName) throws Exception {
//        InputStream stream = null;
//        OutputStream resStreamOut = null;
//        String jarFolder;
//        try {
//            stream = ExecutingClass.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
//            if(stream == null) {
//                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
//            }
//
//            int readBytes;
//            byte[] buffer = new byte[4096];
//            jarFolder = new File(ExecutingClass.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
//            resStreamOut = new FileOutputStream(jarFolder + resourceName);
//            while ((readBytes = stream.read(buffer)) > 0) {
//                resStreamOut.write(buffer, 0, readBytes);
//            }
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            stream.close();
//            resStreamOut.close();
//        }
//
//        return jarFolder + resourceName;
//    }

}
