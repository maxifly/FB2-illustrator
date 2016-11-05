package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.vapi.OwnerProjects;
import com.maxifly.vapi.ProjectProcessor;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.model.OwnerAlbumProject;
import com.maxifly.vapi.model.Project_VK;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBException;
import java.nio.file.Files;
import java.security.acl.Owner;
import java.util.HashMap;

/**
 * Created by Maximus on 23.10.2016.
 */
public class DM_Book_from_VKProj extends DM_Book_from_Proj {

    private HashMap<Integer,OwnerProjects> owners = new HashMap<>();
    private ObservableList<OwnerAlbumProject> suitableProjects = FXCollections.observableArrayList();
    private ObjectProperty<OwnerAlbumProject> selectedProject = new SimpleObjectProperty();

    public ObservableList<OwnerAlbumProject> getSuitableProjects() {
        return suitableProjects;
    }

    public void refresh(String addrType, String srcAddr) throws MyException {
        int ownerId = 0;
        Long albumId = null;

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
            refreshOwner(ownerId);
        }

    }

    private void refreshOwner(int ownerId){
     OwnerProjects ownerProjects = owners.get(ownerId);
        if (ownerProjects == null) {
            ownerProjects = new OwnerProjects(this.factory_gui.getDm_statusBar().getToken(),ownerId);
        }

        // Надо проверить подходит ли проект под маску

        for (OwnerAlbumProject ownerAlbumProject : ownerProjects) {
            if (checkBookname(ownerAlbumProject.project_vk) || true) {
               log.debug("Project {} suitable for book mask.");

               suitableProjects.add(ownerAlbumProject) ;
            }

        }


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
        ProjectProcessor projectProcessor = new ProjectProcessor(this.factory_gui.getDm_statusBar().getToken(),selectedProject.getValue().albumId);

        Project_VK project_vk = projectProcessor.importProject(selectedProject.getValue().project_vk.getId());
        projectProcessor.downloadPhotos(Files.createTempDirectory("fbill_"), project_vk);

        super.projectObjectPropertyProperty().setValue(project_vk);
        super.load_ill();
    }
}
