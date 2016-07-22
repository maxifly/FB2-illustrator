package com.maxifly.vapi.model.REST;

/**
 * Created by Maximus on 20.06.2016.
 */
public class REST_photo {
    public int id;
    public int album_id;
    public int owner_id;
    public String photo_75;
    public String photo_130;
    public String photo_604;
    public String photo_807;
    public String photo_1280;
    public String photo_2560;
    public String text;


    public String getPhotoUrl() {
        if (photo_2560 != null) return photo_2560;
        if (photo_1280 != null) return photo_1280;
        if (photo_807 != null) return photo_807;
        if (photo_604 != null) return photo_604;
        if (photo_130 != null) return photo_130;
        if (photo_75 != null) return photo_75;
        return null;
    }


}
