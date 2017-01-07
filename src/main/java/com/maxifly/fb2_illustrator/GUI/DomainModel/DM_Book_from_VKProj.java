package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.TaskInterrupted;
import com.maxifly.fb2_illustrator.TaskInterruptedRuntime;
import com.maxifly.jutils.I_Progress;
import com.maxifly.vapi.*;
import com.maxifly.vapi.model.AlbumAddrAttributes;
import com.maxifly.vapi.model.OwnerAlbumProject;
import com.maxifly.vapi.model.Project_VK;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBException;
import java.nio.file.Files;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maximus on 23.10.2016.
 */
public class DM_Book_from_VKProj extends DM_Book_from_Proj {

    private HashMap<Integer, OwnerProjects> owners = new HashMap<>();
    private ObservableList<OwnerAlbumProject> suitableProjects = FXCollections.observableArrayList();
    private ObjectProperty<OwnerAlbumProject> selectedProject = new SimpleObjectProperty();
    private I_Progress progress;
    private Task task;


    public ObservableList<OwnerAlbumProject> getSuitableProjects() {
        return suitableProjects;
    }

    public List<OwnerAlbumProject> refresh(String addrType, String srcAddr, I_Progress progress, Task task) throws MyException {
        int ownerId = 0;
        AlbumAddrAttributes albumAddrAttributes = null;
        this.progress = progress;
        this.task = task;

        switch (addrType) {
            case "группа":
                ownerId = UrlCreator.getOwnerIdByOwnerURL(srcAddr);
                if (ownerId > 0) ownerId = -1 * ownerId;
                break;
            case "пользователь":
                ownerId = UrlCreator.getOwnerIdByOwnerURL(srcAddr);
                break;
            case "альбом":
                albumAddrAttributes = UrlCreator.parseAlbumURL(srcAddr);
                break;
            default:
                throw new MyException("Unsupported addr type: " + addrType);
        }

        if (albumAddrAttributes == null) {
            return refreshOwner(ownerId);
        } else {
            return refreshAlbum(albumAddrAttributes);
        }

    }

    public void reset(String addrType, String srcAddr) throws MyException {
        if (srcAddr == null) {
            this.factory_gui.getAlbumsContainer().refresh();
        } else {
            switch (addrType) {
                case "группа":
                    Integer ownerId = UrlCreator.getOwnerIdByOwnerURL(srcAddr);
                    if (ownerId > 0) ownerId = -1 * ownerId;
                    this.factory_gui.getAlbumsContainer().refresh(ownerId);
                    break;
                case "пользователь":
                    ownerId = UrlCreator.getOwnerIdByOwnerURL(srcAddr);
                    this.factory_gui.getAlbumsContainer().refresh(ownerId);
                    break;
                case "альбом":
                    AlbumAddrAttributes albumAddrAttributes = UrlCreator.parseAlbumURL(srcAddr);
                    this.factory_gui.getAlbumsContainer().refresh(albumAddrAttributes.ownerId);
                    break;
                default:
                    throw new MyException("Unsupported addr type: " + addrType);
            }
        }

    }

    private List<OwnerAlbumProject> refreshAlbum(AlbumAddrAttributes albumAddrAttributes) throws MyException {
        log.debug("Refresh album. {}.", albumAddrAttributes);
        List<OwnerAlbumProject> result = new ArrayList<>();

        AlbumsContainer albumsContainer = this.factory_gui.getAlbumsContainer();
        AlbumProjects albumProjects = albumsContainer.getAlbumProjects(albumAddrAttributes);
        if (albumProjects == null) {
            albumProjects = new AlbumProjects(this.factory_gui.getDm_statusBar().getToken(),
                    albumAddrAttributes.ownerId,
                    albumAddrAttributes.albumId);
            albumsContainer.putAlbumProjects(albumAddrAttributes, albumProjects);
        }

        try {
            for (Project_VK project_vk : albumProjects) {
                if (task.isCancelled()) {
                    log.debug("Task canceled");
                    result.clear();
                    break;
                }
                if (checkBookname(project_vk)) {
                    log.debug("Project {} suitable for book mask.", project_vk);

                    result.add(new OwnerAlbumProject(
                            albumAddrAttributes.ownerId,
                            albumAddrAttributes.albumId,
                            project_vk));
                }
            }
        } catch (TaskInterruptedRuntime e) {
            log.error("Exception TaskInterruptedRuntime ", e);
            task.cancel();
            result.clear();
        }

        return result;
    }

    private List<OwnerAlbumProject> refreshOwner(int ownerId) throws MyException {
        log.debug("Refresh albums by owner {}", ownerId);

        List<OwnerAlbumProject> result = new ArrayList<>();

        OwnerProjects ownerProjects = owners.get(ownerId);
        if (ownerProjects == null) {
            ownerProjects = new OwnerProjects(this.factory_gui.getDm_statusBar().getToken(), ownerId);
            ownerProjects.setAlbumsContainer(this.factory_gui.getAlbumsContainer());
            owners.put(ownerId, ownerProjects);
        }

        ownerProjects.setDm_i_progress(progress);

        // Надо проверить подходит ли проект под маску

        try {
            for (OwnerAlbumProject ownerAlbumProject : ownerProjects) {
                if (task.isCancelled()) {
                    log.debug("Task canceled");
                    result.clear();
                    break;
                }

                if (ownerProjects.isException()) throw ownerProjects.getException();

                if (checkBookname(ownerAlbumProject.project_vk)) {
                    log.debug("Project {} suitable for book mask.", ownerAlbumProject.project_vk);

                    result.add(ownerAlbumProject);
                }

            }
        } catch (TaskInterruptedRuntime e) {
            log.error("Exception TaskInterruptedRuntime ", e);
            task.cancel();
            result.clear();
        }

        if (ownerProjects.isException()) throw ownerProjects.getException();
        return result;

    }

    public void setSuitableProjects(List<OwnerAlbumProject> list) {
        suitableProjects.addAll(list);
    }

    public OwnerAlbumProject getSelectedProject() {
        return selectedProject.get();
    }

    public ObjectProperty<OwnerAlbumProject> selectedProjectProperty() {
        return selectedProject;
    }

    public DM_Book_from_VKProj(Factory_GUI factory_gui) throws JAXBException {
        super(factory_gui);
        selectedProject.addListener((observable, oldValue, newValue) -> changeSelectedProject(newValue)
        );
//        super.dm_projectObjectPropertyProperty().bindBidirectional(factory_gui.getDm_statusBar().dmProject_Property());
    }


    @Override
    public void load_ill(I_Progress progress) throws Exception {
        ProjectProcessor projectProcessor = new ProjectProcessor(this.factory_gui.getDm_statusBar().getToken(),
                selectedProject.getValue().ownerId,
                selectedProject.getValue().albumId);

        progress.updateProgress(1, 100, "download project");
        Project_VK project_vk = null;
        try {
            project_vk = projectProcessor.importProject(selectedProject.getValue().project_vk.getId());
        } catch (InterruptedException e) {
            log.error("Task interrupted", e);
            throw new TaskInterrupted("Task interrupted", e);
        }
        projectProcessor.downloadPhotos(Files.createTempDirectory("fbill_"), project_vk, progress);

        super.projectObjectPropertyProperty().setValue(project_vk);
        super.load_ill(progress);
    }

    private void changeSelectedProject(OwnerAlbumProject newValue) {
        log.debug("Change selected projexct: {}", newValue);
        if (newValue != null) {
            super.projectObjectProperty.setValue(newValue.project_vk);
        } else {
            super.projectObjectProperty.setValue(null);
        }
    }

    @Override
    protected void checks() {
//        super.checks();
    }
}
