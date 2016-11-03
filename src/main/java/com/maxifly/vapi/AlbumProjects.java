package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.vapi.model.DATA.DATA_photo;
import com.maxifly.vapi.model.DATA.Project_VKJ_Serialiser;
import com.maxifly.vapi.model.DATA.SearchTemplate_VKJ_Serialiser;
import com.maxifly.vapi.model.PhotoSize;
import com.maxifly.vapi.model.Project_VK;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Maximus on 02.11.2016.
 *
 * Позволяет вытащить все описания проектов из альбома
 * и получить итератор.
 *
 */
public class AlbumProjects implements
        Iterator<Project_VK>, Iterable<Project_VK> {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(AlbumProjects.class.getName());

    private String accessToken;
    private int ownerId;
    private int albumId;

    private int currentIdx;
    PhotoProcessor photoProcessor;

    private boolean getAll = false;
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(SearchTemplate_POJO.class, new SearchTemplate_VKJ_Serialiser())
            .registerTypeAdapter(Project_VK.class, new Project_VKJ_Serialiser())
            .create();

    private ArrayList<Project_VK> projects = new ArrayList<>();

    public int getOwnerId() {
        return ownerId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public AlbumProjects(String accessToken, int ownerId, int albumId) {
        this.accessToken = accessToken;
        this.ownerId = ownerId;
        this.albumId = albumId;
        reset();
    }


    public void reset() {
        photoProcessor = new PhotoProcessor(accessToken,albumId, PhotoSize.photo_75x75);
        getAll = false;
    };

    /**
     * Позволяет получить итератор
     * Ограничение реализации - в один момент времени может существовать только один итератор
     * @return
     */
    @Override
    public Iterator<Project_VK> iterator() {
        currentIdx = 0;
        return this;
    }

    @Override
    public boolean hasNext() {
        if (currentIdx < projects.size()) {
            return true;
        }
        if (currentIdx >= projects.size() && getAll) {
            return false;
        }

        // Вытаскиваем из альбома все что можно

        try {
            filterAlbumItems();
        } catch (MyException e) {
            e.printStackTrace();
        }

        getAll = true;


        // Делаем еще ожну проверку
        if (currentIdx < projects.size()) {
            return true;
        }
        if (currentIdx >= projects.size() && getAll) {
            return false;
        }

        return false;
    }



    @Override
    public Project_VK next() {
        return projects.get(currentIdx++);
    }



    private void filterAlbumItems() throws MyException {
        while(photoProcessor.hasNext()) {
            DATA_photo data_photo = photoProcessor.next();
            try {

                log.debug("*** data_photo.text {}", data_photo.text);
                Project_VK project_vk = gson.fromJson(data_photo.text, Project_VK.class);
                log.debug("project_vk {}",project_vk);
                if (project_vk != null) {
                    projects.add(project_vk);
                }

            } catch (JsonSyntaxException e) {
                log.warn("Can not parse photo description {}", e);
                return;
            }
        }

    }
}
