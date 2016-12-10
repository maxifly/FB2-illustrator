package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.TaskInterrupted;
import com.maxifly.vapi.model.REST.REST_Result_createAlbum;
import com.maxifly.vapi.model.REST.REST_Result_photo;
import com.maxifly.vapi.model.RestResponse;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by Maximus on 08.10.2016.
 */
public class VK_Metods {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(VK_Metods.class.getName());


    private String accessToken;
    private RestSender restSender = new RestSender();
    Gson g = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public VK_Metods(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long createAlbum(Long groupId) throws UnsupportedEncodingException, MyException {
        String url = UrlCreator.createPhotoAlbum(accessToken, "Иллюстрации к книгам", groupId, "{\"fb_ill\":1}");
        RestResponse restResponse = null;
        try {
            restResponse = restSender.sendGet(url);
        } catch (InterruptedException e) {
            log.error("Task interrupted.", e);
            throw new TaskInterrupted("Task interrupted", e);
        }

        if (restResponse.getResponseCode() != 200) {
            throw new MyException("Error when create album. restCode: " + restResponse.getResponseCode());
        }

        REST_Result_createAlbum rest_result_createAlbum =
                g.fromJson(restResponse.getResponseBody().toString(), REST_Result_createAlbum.class);

        if (rest_result_createAlbum.error != null) {
            log.error("Error when create photo album. " + rest_result_createAlbum.error);
            throw new MyException("Error when create photo album. " + rest_result_createAlbum.error);
        }

        log.debug("Create album {}", rest_result_createAlbum.response.id);
        return (long) rest_result_createAlbum.response.id;

    }


}
