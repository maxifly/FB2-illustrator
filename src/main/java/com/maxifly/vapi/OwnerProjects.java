package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_I_Progress;
import com.maxifly.fb2_illustrator.GUI.DomainModel.EmptyProgress;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.TaskInterruptedRuntime;
import com.maxifly.vapi.model.DATA.*;
import com.maxifly.vapi.model.OwnerAlbumProject;
import com.maxifly.vapi.model.Project_VK;
import com.maxifly.vapi.model.REST.REST_Result_albums;
import com.maxifly.vapi.model.REST.REST_photoAlbum;
import com.maxifly.vapi.model.RestResponse;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Maximus on 31.10.2016.
 */
public class OwnerProjects implements
        Iterator<OwnerAlbumProject>,
Iterable<OwnerAlbumProject>{
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(OwnerProjects.class.getName());

    private String accessToken;
    private int   ownerId;
    private RestSender restSender = new RestSender();

    private HashMap<Album, AlbumProjects> albums;
    private List<OwnerAlbumProject> projects;
    private Iterator<Album> albumIterator;

    private DM_I_Progress dm_i_progress = new EmptyProgress();

    private MyException exception = null;

//    private int currentAlbumIdx;
    private int currentProjIdx;
    private boolean getAll = false;


    private Gson g = new GsonBuilder()
            .setPrettyPrinting()
            .create();



    public OwnerProjects(String accessToken, int ownerId) {
        this.accessToken = accessToken;
        this.ownerId = ownerId;
        reset();
    }


    public boolean isException() {
        return this.exception != null;
    }

    public MyException getException() {
        return exception;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void reset() {
        albums = new HashMap<>();
        projects = new ArrayList<>();


    }

    public void setDm_i_progress(DM_I_Progress dm_i_progress) {
        this.dm_i_progress = dm_i_progress;
    }

    @Override
    public boolean hasNext() throws TaskInterruptedRuntime{
        if (currentProjIdx < projects.size()) return true;

        if (getAll && currentProjIdx >= projects.size()) return false;

        while (albumIterator.hasNext() && currentProjIdx >= projects.size() ) {
            dm_i_progress.incrementDone(1,"Всего найдено " + projects.size() +" проектов");
            AlbumProjects albumProjects = albums.get(albumIterator.next());
            addAlbumProjects(albumProjects);
        }

        if (!albumIterator.hasNext()) getAll = true;

        return !(getAll && currentProjIdx >= projects.size());
    }

    @Override
    public OwnerAlbumProject next() {
        System.out.println("currentProjIdx " + currentProjIdx + " projects: " + projects.size());
        return projects.get(currentProjIdx++);
    }



    @Override
    public Iterator<OwnerAlbumProject> iterator() {
        exception = null;

        if (albums.size() == 0) {
            try {
                fillAlbums();
            } catch (MyException e) {
                log.error("Error when get all albums.", e);
                exception = e;
            } catch (InterruptedException e) {
                log.error("Task interrupted.", e);
                if (Thread.interrupted()) Thread.currentThread().interrupt();
                throw  new TaskInterruptedRuntime(e);
            }
            getAll = false;
        }
        albumIterator = albums.keySet().iterator();
        currentProjIdx = 0;
        dm_i_progress.setMaxValue(albums.keySet().size());
        return this;
    }


    private void addAlbumProjects(AlbumProjects albumProjects) {
        int ownerId = albumProjects.getOwnerId();
        int albumId = albumProjects.getAlbumId();

        for (Project_VK project_vk : albumProjects) {
            log.debug("project_vk " + project_vk);
            projects.add(new OwnerAlbumProject(ownerId,albumId,project_vk));
        }
    }

    private void fillAlbums() throws MyException, InterruptedException {
        String url = UrlCreator.getAlbums(accessToken,ownerId);
        RestResponse restResponse = restSender.sendGet(url);
        if (restResponse.getResponseCode() != 200) {
            throw new MyException("Error when get album. restCode: "+ restResponse.getResponseCode());
        }

        REST_Result_albums rest_result_albums =
                g.fromJson(restResponse.getResponseBody().toString(), REST_Result_albums.class);

        if (rest_result_albums.error != null) {
            log.error("Error when get photo album. {}", rest_result_albums.error);
            throw new MyException("Error when get photo album. " + rest_result_albums.error);
        }

        // Подберем подходящие альбомы
        for (REST_photoAlbum rest_album : rest_result_albums.response.items) {
            try {
                Album album = g.fromJson(rest_album.description,Album.class);
                if (album.fb_ill == 1) albums.put(album, new AlbumProjects(accessToken,rest_album.owner_id,rest_album.id));
            } catch (Exception e) {
                log.debug("Can not decode album description {}, {}",rest_album.description,e);
            }
        };

    }
}
