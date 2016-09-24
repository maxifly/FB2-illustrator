package com.maxifly.vapi;

import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.model.Project;

import java.io.File;
import java.io.IOException;

/**
 * Created by Maximus on 24.09.2016.
 */
public class ProjectUploader {
    private String accessToken;
    private long albumId;
    private String book  = "book.jpg";

    private RestSender restSender = new RestSender();
    private PhotoUploader photoUploader;


    public ProjectUploader(String accessToken, long albumId) {
        this.accessToken = accessToken;
        this.albumId = albumId;

        photoUploader = new PhotoUploader(accessToken,albumId);

    }


    /**
     * Удаляет проект
     * @param id
     */
    public void deleteProject(String id) {

    }

    public void uploadProject(Project project) throws MyException, IOException {


        // Удалим проект, если он там есть
        deleteProject(project.getId());

        photoUploader.prepare();
        // Загрузим информацию о проекте
       String projectInfo =  ExportProject_Utl.getProjectInfo(project);
       photoUploader.uploadPhoto(this.book,projectInfo);








    }


}
