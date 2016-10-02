package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.fb2_xml.model.FictionBook;
import com.maxifly.vapi.model.REST.*;
import com.maxifly.vapi.model.RestResponse;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maximus on 24.09.2016.
 */
public class PhotoUploader {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(PhotoUploader.class.getName());
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
            throw new MyException("Error when get upload Server: \n"
                    + "REST responce code != 200 (responce code:"
                    + restResponse.getResponseCode() + ")");
        }


        log.debug("get upload server result: {}", restResponse.getResponseBody().toString());
        REST_Result_uploadServer rest_result_uploadServer =
                g.fromJson(restResponse.getResponseBody().toString(), REST_Result_uploadServer.class);

        if (rest_result_uploadServer.error != null) {
            log.error("Error return by get photos: {}", rest_result_uploadServer.error);
            throw new MyException("Error when get photos.\n" + rest_result_uploadServer.error);
        }

        uploadServer = rest_result_uploadServer.response;

    }

        public void uploadPhoto(File photo, String description) throws MyException, IOException {
            uploadPhoto(photo, null, description);
        }
        public void uploadPhoto(String resourcePath, String description) throws MyException, IOException {
        uploadPhoto(null, resourcePath, description);
        }

        private void uploadPhoto(File photo, String resourcePath, String description) throws MyException, IOException {

        List<REST_FileUpload> files = new ArrayList<>();

        files.add(new REST_FileUpload(photo,resourcePath,"file1"));
        RestResponse restResponse_upload =  restSender.sendPOST_uploadFiles(uploadServer.upload_url, files);

        REST_Result_upload rest_result_upload = g.fromJson(restResponse_upload.getResponseBody().toString(),
                REST_Result_upload.class);

        if (rest_result_upload.photos_list == null) {
            String message = "Error when upload photo " + photo.getCanonicalPath();
            log.error(message);
            throw new MyException(message);
        }

        // Фотография загружена. Добавим ее в альбом

        String url = UrlCreator.photosSave(accessToken,albumId,null,rest_result_upload.server,
                rest_result_upload.photos_list,
                rest_result_upload.hash,
                description);
        RestResponse restResponse_Save = restSender.sendGet(url);
        if (restResponse_Save.getResponseCode() != 200 ) {
            throw new MyException("Error code " + restResponse_Save.getResponseCode() +"when save photo in album ");
        }

        REST_Result_savePhotos rest_result_savePhotos =
                g.fromJson(restResponse_Save.getResponseBody().toString(),REST_Result_savePhotos.class);

        if (rest_result_savePhotos.error!= null) {
            String message = "Error when save photo: " + rest_result_savePhotos.error.toString();
            log.error(message);
            throw new MyException(message);
        }




    }

}
