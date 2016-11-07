package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.vapi.model.OwnerAlbumProject;
import javafx.concurrent.Task;

import java.util.List;

/**
 * Created by Maximus on 06.11.2016.
 */
public class DM_Task_FindSuitableVKProj extends Task<List<OwnerAlbumProject>>
implements DM_I_Progress
{
    private DM_Book_from_VKProj dm_book_from_vkProj;
    private String addrType; //TODO Может надо просто в классе dm устанавливать?
    private String srcAddr;

    private long max = 0;
    private long doneCnt = 0;

    public DM_Task_FindSuitableVKProj(DM_Book_from_VKProj dm_book_from_vkProj, String addrType, String srcAddr) {
        this.dm_book_from_vkProj = dm_book_from_vkProj;
        this.addrType = addrType;
        this.srcAddr = srcAddr;
    }

    @Override
    protected List<OwnerAlbumProject> call() throws Exception {
        return dm_book_from_vkProj.refresh(addrType,srcAddr, this);
    }


    @Override
    public void setMaxValue(long max) {
        this.max = max;
    }

    @Override
    public void incrementDone(long increment) {
        doneCnt  = doneCnt + increment;
        updateProgress(doneCnt,max);
    }

    @Override
    public void incrementDone(long increment, String message) {
        doneCnt  = doneCnt + increment;
        updateProgress(doneCnt,max, message);
    }

    @Override
    public void updateProgress(long workDone, long max) {
        super.updateProgress(workDone,max);
    }

    @Override
    public void updateProgress(long workDone, long max, String message) {
        this.updateProgress(workDone,max);
        this.updateMessage(message);
    }


}
