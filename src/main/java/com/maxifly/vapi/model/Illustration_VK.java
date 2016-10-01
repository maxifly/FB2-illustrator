package com.maxifly.vapi.model;

import com.maxifly.fb2_illustrator.model.Illustration;

/**
 * Created by Maximus on 13.07.2016.
 */
public class Illustration_VK extends Illustration {
    private String url_picture;
    private long photo_id;
    private String project_id;

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

    public long getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(long photo_id) {
        this.photo_id = photo_id;
    }

    public String getUrl_picture() {
        return url_picture;
    }

}
