package com.kuku.vapi.model;

import com.kuku.fb2_illustrator.model.Illustration;

/**
 * Created by Maximus on 13.07.2016.
 */
public class InternetIllustration extends Illustration {
    private String url_picture;
    private int illNum;
    private int illSubNum;

    public InternetIllustration(String id, String def_description,
                                String url_picture,
                                int illNum,
                                int illSubNum

                                ) {
        super(id, def_description);
        this.url_picture = url_picture;
        this.illNum = illNum;
        this.illSubNum = illSubNum;
    }

}
