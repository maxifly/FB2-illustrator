package com.maxifly.vapi;

import com.google.gson.Gson;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.fb2_xml.model.FictionBook;
import com.maxifly.vapi.model.REST.REST_Result_photo;
import com.maxifly.vapi.model.REST.REST_uploadServer;
import com.maxifly.vapi.model.RestResponse;

import java.io.File;

/**
 * Created by Maximus on 24.09.2016.
 */
public class PhotoUploader {
    private String accessToken;
    private long albumId;

    private RestSender restSender = new RestSender();
    private Gson g = new Gson();
    private REST_uploadServer uploadServer;

    public PhotoUploader(String accessToken, long albumId) {
        this.accessToken = accessToken;
        this.albumId = albumId;
    }

    public void prepare() throws MyException {
        String url = UrlCreator.getUploadServer(this.accessToken, albumId);

        RestResponse restResponse = restSender.sendGet(url);
        if (restResponse.getResponseCode() != 200) {
            throw new MyException("Error when get upluad Server: \n"
                    + "REST responce code != 200 (responce code:"
                    + restResponse.getResponseCode() + ")");
        }


        uploadServer =
                g.fromJson(restResponse.getResponseBody().toString(), REST_uploadServer.class);



    }
    public void uploadPhoto(File photo, String description) throws MyException {
        String url = UrlCreator.getUploadServer(this.accessToken, albumId);

        RestResponse restResponse = restSender.sendGet(url);
        if (restResponse.getResponseCode() != 200) {
            throw new MyException("Error when get upluad Server: \n"
                    + "REST responce code != 200 (responce code:"
                    + restResponse.getResponseCode() + ")");
        }


        uploadServer =
                g.fromJson(restResponse.getResponseBody().toString(), REST_uploadServer.class);



    }

}
