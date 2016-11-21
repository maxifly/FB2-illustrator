package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.TaskInterrupted;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.jutils.I_Progress;
import com.maxifly.vapi.model.DATA.DATA_photo;
import com.maxifly.vapi.model.DATA.PrjObj;
import com.maxifly.vapi.model.Illustration_VK;
import com.maxifly.vapi.model.PhotoSize;
import com.maxifly.vapi.model.Project_VK;
import com.maxifly.vapi.model.REST.REST_Result_deletePhoto;
import com.maxifly.vapi.model.RestResponse;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Maximus on 24.09.2016.
 */
public class ProjectProcessor { // TODO Переименовать класс?

    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(ProjectProcessor.class.getName());

    private String accessToken;
    private long albumId;
    private String book = "book.jpg";

    private RestSender restSender = new RestSender();
    private PhotoUploader photoUploader;

    private Gson g = new Gson();

    public ProjectProcessor(String accessToken, long albumId) {
        this.accessToken = accessToken;
        this.albumId = albumId;

        photoUploader = new PhotoUploader(accessToken, albumId);

    }

    public void downloadPhotos(Path dest_dir, Project_VK project_vk) throws MyException {
        downloadPhotos(dest_dir, project_vk, null);
    }

    public void downloadPhotos(Path dest_dir, Project_VK project_vk, I_Progress progress_monitor)
            throws MyException {
        PhotoLoader photoLoader = new PhotoLoader(dest_dir);
        photoLoader.setProgress_monitor(progress_monitor);

        List<Illustration_VK> ills_VK = new ArrayList<>();
        for (Illustration ill : project_vk.getIllustrations()) {
            photoLoader.addIllustration((Illustration_VK) ill);
        }

        try {
            photoLoader.download();
        } catch (IOException | ExecutionException e) {
            log.error("Ошибка загрузки файлов: {}", e);
            throw new MyException("Ошибка загрузки файлов", e);
        } catch (InterruptedException e) {
            log.error("Загрузка файлов прервана 1: ", e);
            throw new TaskInterrupted(e);
        }

    }

    public Project_VK importProject(String project_id) throws MyException, InterruptedException {
        PhotoProcessor photoProcessor = new PhotoProcessor(accessToken, albumId, PhotoSize.photo_2560x2048);
        IllFilter illFilter = new IllFilter(project_id);

        while (photoProcessor.hasNext()) {
            DATA_photo data_photo = photoProcessor.next();
            illFilter.add(data_photo);
        }

        Project_VK project_vk = illFilter.getProject_vk();

        if (project_vk == null) {
            throw new MyException("Не найдена информация о проекте");
        }

        List<Illustration_VK> illustrations = illFilter.getIllustrations();

        // Отсортируем иллюстрации по их номероу
        Collections.sort(illustrations);

        int i = 0;
        for (Illustration_VK ill : illustrations) {
            ill.setId(i);
            i++;
            project_vk.addIll(ill);
        }


        return project_vk;
    }


    /**
     * Удаляет проект
     *
     * @param project_id
     */
    public void deleteProject(String project_id) throws MyException {

        try {


            // Загрузим объекты
            PhotoProcessor photoProcessor = new PhotoProcessor(accessToken, albumId, PhotoSize.photo_2560x2048);
            PrjObjFilter prjObjFilter = new PrjObjFilter(project_id);

            while (photoProcessor.hasNext()) {
                DATA_photo data_photo = photoProcessor.next();
                prjObjFilter.add(data_photo);
            }

            log.debug("Found {} objects.", prjObjFilter.getObjCount());

            for (PrjObj prjObj : prjObjFilter.getProject_objects()) {
                System.out.println(prjObj);
                delPhoto(prjObj.getPhoto_id(), prjObj.getOwnerId());
                log.debug("Delete photo {}", prjObj.getPhoto_id());
            }
            log.info("Delete project {} from album {}", project_id, albumId);
        } catch (InterruptedException e) {
            log.error("Task interrupted", e);
            throw new TaskInterrupted("Task interrupted", e);
        }
    }


    private boolean delPhoto(long photoId, int ownerId) throws MyException {
        try {


            RestResponse restResponse = restSender.sendGet(UrlCreator.delPhoto(accessToken, albumId, ownerId, photoId));
            if (restResponse.getResponseCode() != 200) {
                String message = "Can not delete photo: " +
                        restResponse.getResponseCode() +
                        " " +
                        restResponse.getResponseBody().toString();
                log.error(message);
                throw new MyException(message);
            }


            REST_Result_deletePhoto rest_result =
                    g.fromJson(restResponse.getResponseBody().toString(), REST_Result_deletePhoto.class);

            if (rest_result.error != null || rest_result.response != 1) {
                String message = "Error when delete photo: " + rest_result.error.toString() + " response: " + rest_result.response;
                log.error(message);
                throw new MyException(message);
            }

            return true;

        } catch (InterruptedException e) {
            log.error("Task interrupted", e);
            throw new TaskInterrupted("Task interrupted", e);
        }
    }

    public void uploadProject(Project project) throws MyException, IOException {


        // Удалим проект, если он там есть
        deleteProject(project.getId());

        photoUploader.prepare();
        // Загрузим информацию о проекте
        String description = ExportProject_Utl.getProjectInfo(project);
        photoUploader.uploadPhoto(this.book, description);

        // Переберем иллюстрации и тоже их загрузим

        for (Illustration ill : project.getIllustrations()) {
            description = ExportProject_Utl.getIllInfo(ill);
            photoUploader.uploadPhoto(ill.getFile().toFile(), description);
        }


    }


}
