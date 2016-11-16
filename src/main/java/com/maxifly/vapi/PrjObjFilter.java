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
public class PrjObjFilter {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(PrjObjFilter.class.getName());

    private String project_id;

    private List<PrjObj> project_objects = new ArrayList<>();


    private Gson gson_prjobj;


    public PrjObjFilter(String project_id) {
        this.project_id = project_id;


        gson_prjobj = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(SimplePrjOpj.class, new PrjObj_VKJ_Serialiser(project_id))
                .create();
    }


    /**
     * Проверяет, что фотография является объектом проекта, неважно какого типа
     * и добавляет ее к общему списку
     *
     * @param photo
     */

    public void add(DATA_photo photo) {
        SimplePrjOpj prjObj = null;
        try {
            prjObj = gson_prjobj.fromJson(photo.text, SimplePrjOpj.class);
        } catch (JsonSyntaxException e) {
            log.warn("Can not parse photo description {}", e);
            return;
        }

        if (prjObj == null) return;
        prjObj.setPhoto_id(photo.id);
        prjObj.setOwnerId(photo.owner_id);

        this.project_objects.add(prjObj);

    }


    /**
     * Получить список накопленных объектов
     *
     * @return список иллюстраций
     */
    public List<PrjObj> getProject_objects() {
        return project_objects;
    }

    public int getObjCount() {
        return project_objects.size();
    }
}
