package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.TaskInterrupted;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.jutils.I_Progress;
import com.maxifly.vapi.ProjectProcessor;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.model.Project_VK;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Maximus on 09.10.2016.
 */
public class DM_ImportVKProject extends DM_Abstract {

    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(DM_ImportVKProject.class.getName());

    private Project_VK project_vk;
    private DM_StatusBar dm_statusBar;
    private StringProperty projectId = new SimpleStringProperty();
    private StringProperty albumAddr = new SimpleStringProperty();
    private I_Progress progress_monitor;

    public DM_ImportVKProject(DM_StatusBar dm_statusBar) {
        this.dm_statusBar = dm_statusBar;
    }

    public void setProgress_monitor(I_Progress progress_monitor) {
        this.progress_monitor = progress_monitor;
    }

    public void importProject() throws MyException, IOException {
        long albumId = UrlCreator.getAlbumId(albumAddr.getValue());
        ProjectProcessor projectProcessor = new ProjectProcessor(dm_statusBar.getToken(), albumId);
        progress_monitor.updateProgress(1,100, "download project");
        try {
            this.project_vk = projectProcessor.importProject(projectId.getValue());
        } catch (InterruptedException e) {
            log.error("Task interrupted", e);
            throw new TaskInterrupted("Task interrupted", e);
        }
        projectProcessor.downloadPhotos(Files.createTempDirectory("fbill_"), project_vk, progress_monitor);
    }


    public StringProperty projectIdProperty() {
        return projectId;
    }

    public StringProperty albumAddrProperty() {
        return albumAddr;
    }

    public Project_VK getProject_vk() {
        return project_vk;
    }

}
