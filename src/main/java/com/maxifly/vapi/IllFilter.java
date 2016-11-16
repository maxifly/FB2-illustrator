package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.vapi.model.DATA.*;
import com.maxifly.vapi.model.Illustration_VK;
import com.maxifly.vapi.model.Project_VK;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maximus on 12.07.2016.
 * <p>
 * Фильтрует, подходящие по критериям иллюстрации и накапливает их в себе.
 * Кроме того фильтрует описание проекта
 * Потом пачкой отдает
 */
public class IllFilter {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(IllFilter.class.getName());

    private String project_id;

    private List<Illustration_VK> illustrations = new ArrayList<>();
    private Project_VK project_vk;
    private List<PrjObj> project_objects = new ArrayList<>();


    private Gson gson_project;
    private Gson gson_ill;

    private int currentIllNum = 0;
    private int currentIllSubNum = 0;


    public IllFilter(String project_id) {
        this.project_id = project_id;


        gson_project = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(SearchTemplate_POJO.class, new SearchTemplate_VKJ_Serialiser())
                .registerTypeAdapter(Project_VK.class, new Project_VKJ_Serialiser(project_id))
                .create();
        gson_ill = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(SearchTemplate_POJO.class, new SearchTemplate_VKJ_Serialiser())
                .registerTypeAdapter(Illustration_VK.class, new Ill_VKJ_Serialiser(project_id))
                .create();
    }

//    public IllFilter() {
//        this(null);
//    }


    /**
     * Проверяет, что фотография является иллюстрацией или описанием проекта
     * что она является подходящей иллюстрацией и добавляет ее к общему списку иллюстраций
     *
     * @param photo
     */

    public void add(DATA_photo photo) {
        addAsProject(photo);


        Illustration_VK illustration = null;
        try {
            illustration = gson_ill.fromJson(photo.text, Illustration_VK.class);
        } catch (JsonSyntaxException e) {
            log.warn("Can not parse photo description {}", e);
            return;
        }

        if (illustration == null) return;

        illustration.setPhoto_id(photo.id);
        illustration.setUrl_picture(photo.url);

        if (project_vk != null) {
            illustration.setProject(project_vk);
        }

        this.illustrations.add(illustration);

    }

    /**
     * Пытается разобрать объект как описание проекта
     *
     * @param photo
     * @return - операция успешна
     */
    private boolean addAsProject(DATA_photo photo) {
        // Проверим, может это проект, если мы еще еuо ищем
        if (this.project_vk == null) {

            log.debug("Try check photo desc {}", photo.text);

            try {


                project_vk = gson_project.fromJson(photo.text, Project_VK.class);
                if (project_vk != null) {
                    project_vk.setPhoto_id(photo.id);

                    for (Illustration_VK ill : illustrations) {
                        ill.setProject(project_vk);
                    }
                    return true;
                }
            } catch (JsonSyntaxException e) {
                log.warn("Can not parse photo description {}", e);
            }
        }

        return false;

    }


    /**
     * Получить список накопленных иллюстраций
     *
     * @return список иллюстраций
     */
    public List<Illustration_VK> getIllustrations() {
        return this.illustrations;
    }

    /**
     * Получить данные о проекте
     *
     * @return
     */
    public Project_VK getProject_vk() {
        return project_vk;
    }
}
