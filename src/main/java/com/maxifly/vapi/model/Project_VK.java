package com.maxifly.vapi.model;

import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.vapi.model.DATA.PrjObj;

/**
 * Created by Maximus on 01.10.2016.
 */
public class Project_VK extends Project
implements PrjObj{
    private long photo_id;
    private int ownerId;
    private int illCount;

    @Override
    public Long getPhoto_id() {
        return photo_id;
    }

    @Override
    public void setPhoto_id(Long photo_id) {
        this.photo_id = photo_id;
    }

    @Override
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public int getOwnerId() {
        return ownerId;
    }

    public int getIllCount() {
        return illCount;
    }

    public void setIllCount(int illCount) {
        this.illCount = illCount;
    }
}

