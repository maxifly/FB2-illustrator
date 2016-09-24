package com.maxifly.vapi;

import com.maxifly.fb2_illustrator.model.Project;

/**
 * Created by Maximus on 24.09.2016.
 */
public class ProjectUploader {
    private String accessToken;
    private long albumId;

    private RestSender restSender = new RestSender();


    public ProjectUploader(String accessToken, long albumId) {
        this.accessToken = accessToken;
        this.albumId = albumId;
    }


    /**
     * Удаляет проект
     * @param id
     */
    public void deleteProject(String id) {

    }

    public void uploadProject(Project project) {
        // Удалим проект, если он там есть
        deleteProject(project.getId());

        // Загрузим информацию о проекте




    }


}
