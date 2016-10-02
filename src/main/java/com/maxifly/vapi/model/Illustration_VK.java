package com.maxifly.vapi.model;

import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.vapi.model.DATA.PrjObj;

/**
 * Created by Maximus on 13.07.2016.
 */
public class Illustration_VK extends Illustration
implements PrjObj{
    private String url_picture;
    private long photo_id;
    private String project_id;
    private int ownerId;

    public Illustration_VK(int id, String def_description,
                           String url_picture,
                           long photo_id

                                ) {

        super(id, def_description);
        this.url_picture = url_picture;
        this.photo_id = photo_id;
    }

    public Illustration_VK(Integer id, String def_description, String project_id) {
        super(id, def_description);
        this.project_id = project_id;
    }

    public void setUrl_picture(String url_picture) {
        this.url_picture = url_picture;
    }

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

    public String getUrl_picture() {
        return url_picture;
    }

}
