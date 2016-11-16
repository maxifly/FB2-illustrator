package com.maxifly.vapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.vapi.model.DATA.Ill_VKJ_Serialiser;
import com.maxifly.vapi.model.DATA.Project_VKJ_Serialiser;
import com.maxifly.vapi.model.DATA.SearchTemplate_VKJ_Serialiser;

import java.util.List;

/**
 * Created by Maximus on 24.09.2016.
 */
public class ExportProject_Utl {
    /**
     * Получает описание проекта
     *
     * @param project - проект
     * @return описание проекта
     */

    public static String getProjectInfo(Project project) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(SearchTemplate_POJO.class, new SearchTemplate_VKJ_Serialiser())
                .registerTypeAdapter(Project.class, new Project_VKJ_Serialiser())
                .create();

        return gson.toJson(project);

    }

    public static Project getProjectFromInfo(String projectInfo) {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(SearchTemplate_POJO.class, new SearchTemplate_VKJ_Serialiser())
                .registerTypeAdapter(Project.class, new Project_VKJ_Serialiser())
                .create();
        return gson.fromJson(projectInfo, Project.class);
    }

    public static String getIllInfo(Illustration ill) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(SearchTemplate_POJO.class, new SearchTemplate_VKJ_Serialiser())
                .registerTypeAdapter(Illustration.class, new Ill_VKJ_Serialiser())
                .create();

        return gson.toJson(ill);

    }

}
