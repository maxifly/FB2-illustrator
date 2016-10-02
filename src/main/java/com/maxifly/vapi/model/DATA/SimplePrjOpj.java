package com.maxifly.vapi.model.DATA;

/**
 * Created by Maximus on 01.10.2016.
 */
public class SimplePrjOpj implements PrjObj{
    private Long photo_id;
    private int ownerId;

    @Override
    public Long getPhoto_id() {
        return this.photo_id;
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

    @Override
    public String toString() {
        return "SimplePrjOpj{" +
                "photo_id=" + photo_id +
                ", ownerId=" + ownerId +
                '}';
    }
}
