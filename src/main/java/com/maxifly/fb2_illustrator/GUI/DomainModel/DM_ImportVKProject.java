package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.vapi.ProjectProcessor;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.model.Project_VK;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Maximus on 09.10.2016.
 */
public class DM_ImportVKProject extends DM_Abstract{
    private Project_VK project_vk;
    private DM_StatusBar dm_statusBar;
    private StringProperty projectId = new SimpleStringProperty();
    private StringProperty albumAddr = new SimpleStringProperty();

    public DM_ImportVKProject(DM_StatusBar dm_statusBar) {
        this.dm_statusBar = dm_statusBar;
    }

    public void importProject() throws MyException, IOException {
        long albumId = UrlCreator.getAlbumId(albumAddr.getValue());
        ProjectProcessor projectProcessor = new ProjectProcessor(dm_statusBar.getToken(), albumId);
        this.project_vk = projectProcessor.importProject(projectId.getValue());
        projectProcessor.downloadPhotos(Files.createTempDirectory("fbill_"), project_vk);
    }



    public StringProperty projectIdProperty() {
        return projectId;
    }

    public StringProperty albumAddrProperty() {
        return albumAddr;
    }



}
