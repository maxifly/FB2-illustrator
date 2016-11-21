package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.TaskInterrupted;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.jutils.I_Progress;
import com.maxifly.vapi.ProjectProcessor;
import com.maxifly.vapi.model.Project_VK;
import javafx.concurrent.Task;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.nio.file.Files;

/**
 * Created by Maximus on 20.11.2016.
 */
public class DM_Task_DownloadProject extends Task<Boolean> {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(DM_ImportVKProject.class.getName());

    private DM_ImportVKProject dm_importVKProject;

    public DM_Task_DownloadProject(DM_ImportVKProject dm_importVKProject) {
        this.dm_importVKProject = dm_importVKProject;
    }

    @Override
    protected Boolean call() throws Exception {
        try
        {
            dm_importVKProject.importProject();
        }
        catch (Exception e) {
            log.error("Exception Exception", e);
            throw e;
        }

        return true;
    }
}
