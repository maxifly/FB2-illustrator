package com.maxifly.vapi.model.DATA;

/**
 * Created by Maximus on 21.06.2016.
 */
public class DATA_photo {
    public long id;
    //        public int album_id;
    public int owner_id;
    public String url;
    public String text;

    @Override
    public String toString() {
        return "DATA_photo{" +
                "id=" + id +
                ", owner_id=" + owner_id +
                '}';
    }
}
