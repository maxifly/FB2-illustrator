package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.jutils.I_Progress;
import javafx.concurrent.Task;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

/**
 * Created by Maximus on 20.11.2016.
 */
public class DM_Task_LoadIllVkProj extends Task<Boolean> {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(DM_ImportVKProject.class.getName());

    private DM_Book_from_VKProj dm_book_from_vkProj;
    private I_Progress progress_monitor;

    public DM_Task_LoadIllVkProj(DM_Book_from_VKProj dm_book_from_vkProj) {
        this.dm_book_from_vkProj = dm_book_from_vkProj;
    }

    public void setProgress_monitor(I_Progress progress_monitor) {
        this.progress_monitor = progress_monitor;
    }

    @Override
    protected Boolean call() throws Exception {
        dm_book_from_vkProj.load_ill(progress_monitor);
        return true;
    }
}
