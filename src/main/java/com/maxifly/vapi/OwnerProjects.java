package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.TaskInterruptedRuntime;
import com.maxifly.jutils.EmptyProgress;
import com.maxifly.jutils.I_Progress;
import com.maxifly.vapi.model.AlbumAddrAttributes;
import com.maxifly.vapi.model.DATA.*;
import com.maxifly.vapi.model.OwnerAlbumProject;
import com.maxifly.vapi.model.Project_VK;
import com.maxifly.vapi.model.REST.REST_Result_albums;
import com.maxifly.vapi.model.REST.REST_photoAlbum;
import com.maxifly.vapi.model.RestResponse;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Maximus on 31.10.2016.
 */
public class OwnerProjects implements
        Iterator<OwnerAlbumProject>,
        Iterable<OwnerAlbumProject> {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(OwnerProjects.class.getName());

    private String accessToken;
    private int ownerId;
    private RestSender restSender = new RestSender();

    private AlbumsContainer albumsContainer; //TODO Вот эта штука должна устанавливаться снаружи и быть как бы общим контейнером для всех найденных альбомов
    private List<OwnerAlbumProject> projects = new ArrayList<>();
    private Iterator<AlbumAddrAttributes> albumIterator;

    private I_Progress dm_i_progress = new EmptyProgress();

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
    }


    public void setAlbumsContainer(AlbumsContainer albumsContainer) {
        this.albumsContainer = albumsContainer;
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
        albumsContainer.refresh(ownerId);
        projects = new ArrayList<>();


    }

    public void setDm_i_progress(I_Progress dm_i_progress) {
        this.dm_i_progress = dm_i_progress;
    }

    @Override
    public boolean hasNext() throws TaskInterruptedRuntime {
        if (currentProjIdx < projects.size()) return true;

        if (getAll && currentProjIdx >= projects.size()) return false;

        while (albumIterator.hasNext() && currentProjIdx >= projects.size()) {
            dm_i_progress.incrementDone(1, "Всего найдено " + projects.size() + " проектов");
            AlbumProjects albumProjects = albumsContainer.getAlbumProjects(albumIterator.next());
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

        if (!albumsContainer.getOwnerFull(ownerId)) {
            try {
                fillAlbums();
            } catch (MyException e) {
                log.error("Error when get all albumsContainer.", e);
                exception = e;
            } catch (InterruptedException e) {
                log.error("Task interrupted.", e);
                if (Thread.interrupted()) Thread.currentThread().interrupt();
                throw new TaskInterruptedRuntime(e);
            }
            getAll = false;
        }
        albumIterator = albumsContainer.getOwnerAlbumsAttributes(ownerId).iterator(); //TODO Доделать
        currentProjIdx = 0;
        projects.clear();
        dm_i_progress.setMaxValue(albumsContainer.getOwnerAlbumsAttributes(ownerId).size());
        return this;
    }


    private void addAlbumProjects(AlbumProjects albumProjects) {
        int ownerId = albumProjects.getOwnerId();
        int albumId = albumProjects.getAlbumId();

        for (Project_VK project_vk : albumProjects) {
            log.debug("project_vk " + project_vk);
            projects.add(new OwnerAlbumProject(ownerId, albumId, project_vk));
        }
    }

    private void fillAlbums() throws MyException, InterruptedException {
        String url = UrlCreator.getAlbums(accessToken, ownerId);
        RestResponse restResponse = restSender.sendGet(url);
        if (restResponse.getResponseCode() != 200) {
            throw new MyException("Error when get album. restCode: " + restResponse.getResponseCode());
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
                Album album = g.fromJson(rest_album.description, Album.class);
                if (album.fb_ill == 1) {
                    AlbumAddrAttributes aaa =  new AlbumAddrAttributes(rest_album.owner_id, rest_album.id);

                    if (albumsContainer.getAlbumProjects(aaa) == null) {
                        // Таких проектов нет - добавим
                        albumsContainer.putAlbumProjects(aaa, new AlbumProjects(accessToken, rest_album.owner_id, rest_album.id));
                    }
                }
                     } catch (Exception e) {
                log.debug("Can not decode album description {}, {}", rest_album.description, e);
            }
        };

    }
}
