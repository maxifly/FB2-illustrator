package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.vapi.model.DATA.DATA_photo;
import com.maxifly.vapi.model.DATA.PrjObj;
import com.maxifly.vapi.model.PhotoSize;
import com.maxifly.vapi.model.REST.REST_Result;
import com.maxifly.vapi.model.REST.REST_Result_deletePhoto;
import com.maxifly.vapi.model.REST.REST_Result_savePhotos;
import com.maxifly.vapi.model.RestResponse;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Maximus on 24.09.2016.
 */
public class ProjectUploader { // TODO Переименовать класс?

    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(ProjectUploader.class.getName());

    private String accessToken;
    private long albumId;
    private String book  = "book.jpg";

    private RestSender restSender = new RestSender();
    private PhotoUploader photoUploader;

    Gson g = new Gson();

    public ProjectUploader(String accessToken, long albumId) {
        this.accessToken = accessToken;
        this.albumId = albumId;

        photoUploader = new PhotoUploader(accessToken,albumId);

    }


    /**
     * Удаляет проект
     * @param project_id
     */
    public void deleteProject(String project_id) throws MyException {

        // Загрузим объекты
        PhotoProcessor photoProcessor = new PhotoProcessor(accessToken,albumId, PhotoSize.photo_2560x2048);
        PrjObjFilter prjObjFilter = new PrjObjFilter(project_id);

        while(photoProcessor.hasNext()) {
            DATA_photo data_photo = photoProcessor.next();
            prjObjFilter.add(data_photo);
        }

        log.debug("Found {} objects.", prjObjFilter.getObjCount());

        for (PrjObj prjObj : prjObjFilter.getProject_objects()) {
            System.out.println(prjObj);
           delPhoto(prjObj.getPhoto_id(),prjObj.getOwnerId());
           log.debug("Delete photo {}", prjObj.getPhoto_id());
        }
        log.info("Delete project {} from album {}", project_id, albumId);
    }


    private boolean delPhoto(long photoId, int ownerId) throws MyException {
       RestResponse restResponse = restSender.sendGet(UrlCreator.delPhoto(accessToken,albumId,ownerId,photoId));
        if (restResponse.getResponseCode() != 200 ) {
            String message = "Can not delete photo: " +
                    restResponse.getResponseCode() +
                    " " +
                    restResponse.getResponseBody().toString();
            log.error(message);
            throw new MyException(message);
        }


        REST_Result_deletePhoto rest_result =
                g.fromJson(restResponse.getResponseBody().toString(),REST_Result_deletePhoto.class);

        if (rest_result.error!= null || rest_result.response != 1) {
            String message = "Error when delete photo: " + rest_result.error.toString() + " response: " + rest_result.response;
            log.error(message);
            throw new MyException(message);
        }

        return true;


    }

    public void uploadProject(Project project) throws MyException, IOException {


        // Удалим проект, если он там есть
        deleteProject(project.getId());

        photoUploader.prepare();
        // Загрузим информацию о проекте
       String description =  ExportProject_Utl.getProjectInfo(project);
       photoUploader.uploadPhoto(this.book,description);

        // Переберем иллюстрации и тоже их загрузим

        for (Illustration ill : project.getIllustrations()) {
            description = ExportProject_Utl.getIllInfo(ill);
            photoUploader.uploadPhoto(ill.getFile().toFile(),description);
        }








    }


}
