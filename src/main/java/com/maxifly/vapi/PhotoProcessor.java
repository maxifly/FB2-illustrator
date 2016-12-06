package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.vapi.model.DATA.DATA_photo;
import com.maxifly.vapi.model.PhotoSize;
import com.maxifly.vapi.model.REST.REST_Result_photo;
import com.maxifly.vapi.model.REST.REST_photo;
import com.maxifly.vapi.model.RestResponse;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Maximus on 19.06.2016.
 */
public class PhotoProcessor {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(PhotoProcessor.class.getName());

    private String accessToken;
    private long albumId;
    private Long ownerId;
    private int offset = 0;
    private final int WINDOWS_SIZE = 100;
    private PhotoSize photoSize;
    private int index;
    private ArrayList<DATA_photo> container = new ArrayList<>();
    private Iterator<DATA_photo> iterator;
    private RestSender restSender = new RestSender();


    public PhotoProcessor(String accessToken, Long ownerId, long albumId,  PhotoSize max_photoSize) {
        this.accessToken = accessToken;
        this.albumId = albumId;
        this.ownerId = ownerId;
        this.photoSize = max_photoSize;
        resetContainer();
    }


    public boolean hasNext() throws MyException, InterruptedException {
        if (iterator == null) {
           resetContainer();
        }


        if (!iterator.hasNext()) {
            // Надо получить новую порцию
            return getNewPortion() != 0;
        }
        return true;
    }


    public DATA_photo next() {
        return iterator.next();
    }


    private void resetContainer() {
        container.clear();
        iterator = container.iterator();
        offset = 0;
    }

    private int getNewPortion() throws MyException, InterruptedException { //throws Exception {
        iterator = null;
        container.clear();

        String URL = UrlCreator.getPhotos(this.accessToken, this.albumId, this.ownerId, this.offset, this.WINDOWS_SIZE);
//        RestSender.respDelay();
        RestResponse restResponse = restSender.sendGet(URL);
//TODO count в результате это похоже общее количество итемов, а не количество, полученное в окне. Сколько их получено в окне надо судить по количеству элементов в массиве
        if (restResponse.getResponseCode() != 200) {
            throw new MyException("Error when get photos: \n"
                    + "REST responce code != 200 (responce code:"
                    + restResponse.getResponseCode() + ")");
        }

        Gson g = new Gson();

        REST_Result_photo rest_result_photo =
                g.fromJson(restResponse.getResponseBody().toString(), REST_Result_photo.class);

        log.debug("rest_result_photo.response: {}", rest_result_photo.response);

        if (rest_result_photo.error != null) {
            log.error("Error return by get photos: {}", rest_result_photo.error);
            throw new MyException("Error when get photos.\n" + rest_result_photo.error);
        }

        if (rest_result_photo.response != null) {
            if (rest_result_photo.response.count > 0) {
                for (REST_photo rest_photo : rest_result_photo.response.items) {
                    DATA_photo data_photo = new DATA_photo(); //TODO переделать создание на конструктор с rest_result_photo
                    data_photo.text = rest_photo.text;
                    data_photo.url = rest_photo.getPhotoUrl();
                    data_photo.id = rest_photo.id;
                    data_photo.owner_id = rest_photo.owner_id;
                    log.debug("add DATA_photo {}", data_photo);

                    container.add(data_photo);
                }
                iterator = container.iterator();
                offset = offset + rest_result_photo.response.items.size();
                return rest_result_photo.response.items.size();
            }
        }

        return 0;
    }


}
