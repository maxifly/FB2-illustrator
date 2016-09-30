package com.maxifly.vapi.model;

import com.maxifly.fb2_illustrator.model.Illustration;

/**
 * Created by Maximus on 13.07.2016.
 */
public class InternetIllustration extends Illustration {
    private String url_picture;
    private long photo_id;

    private int illNum; // TODO Удалить
    private int illSubNum; // TODO Удалить



    public InternetIllustration(int id, String def_description,
                                String url_picture,
                                long photo_id

                                ) {

        // TODO надо поменять мдентификатор иллюстрации на Integer
        super(id, def_description);
        this.url_picture = url_picture;
        this.photo_id = photo_id;
    }

    public String getUrl_picture() {
        return url_picture;
    }

    public int getIllNum() {
        return illNum;
    }

    public int getIllSubNum() {
        return illSubNum;
    }
}
