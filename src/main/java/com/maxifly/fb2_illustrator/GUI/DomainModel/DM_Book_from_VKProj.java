package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.TaskInterruptedRuntime;
import com.maxifly.jutils.I_Progress;
import com.maxifly.vapi.OwnerProjects;
import com.maxifly.vapi.ProjectProcessor;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.model.OwnerAlbumProject;
import com.maxifly.vapi.model.Project_VK;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBException;
import java.nio.file.Files;
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
        Long albumId = null;
        this.progress = progress;
        this.task = task;

        switch (addrType) {
            case "група":
                ownerId = -1 * UrlCreator.getOwnerId(srcAddr);
                break;
            case "пользователь":
                ownerId = UrlCreator.getOwnerId(srcAddr);
                break;
            case "альбом":
                albumId = UrlCreator.getAlbumId(srcAddr);
        }

        if (albumId == null) {
            return refreshOwner(ownerId);
        }

        return new ArrayList<OwnerAlbumProject>();

    }

    private List<OwnerAlbumProject> refreshOwner(int ownerId) throws MyException {
        List<OwnerAlbumProject> result = new ArrayList<>();

        OwnerProjects ownerProjects = owners.get(ownerId);
        if (ownerProjects == null) {
            ownerProjects = new OwnerProjects(this.factory_gui.getDm_statusBar().getToken(), ownerId);
            owners.put(ownerId, ownerProjects);
        }

        ownerProjects.setDm_i_progress(progress);

        // Надо проверить подходит ли проект под маску

        try {
            for (OwnerAlbumProject ownerAlbumProject : ownerProjects) {
                if (task.isCancelled()) {
                    log.debug("Task canceled");
                    result = new ArrayList<>();
                    break;
                }

                if (ownerProjects.isException()) throw ownerProjects.getException();

                if (checkBookname(ownerAlbumProject.project_vk)) {
                    log.debug("Project {} suitable for book mask.");

                    result.add(ownerAlbumProject);
                }

            }
        } catch (TaskInterruptedRuntime e) {
            log.error("Exception TaskInterruptedRuntime ", e);
            task.cancel();
            result = new ArrayList<>();
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
//        super.dm_projectObjectPropertyProperty().bindBidirectional(factory_gui.getDm_statusBar().dmProject_Property());
    }


    @Override
    public void load_ill() throws Exception {
        ProjectProcessor projectProcessor = new ProjectProcessor(this.factory_gui.getDm_statusBar().getToken(), selectedProject.getValue().albumId);

        Project_VK project_vk = projectProcessor.importProject(selectedProject.getValue().project_vk.getId());
        projectProcessor.downloadPhotos(Files.createTempDirectory("fbill_"), project_vk);

        super.projectObjectPropertyProperty().setValue(project_vk);
        super.load_ill();
    }
}
