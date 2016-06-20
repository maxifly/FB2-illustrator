package com.kuku.vapi;

import com.kuku.vapi.model.DATA.DATA_photo;
import com.kuku.vapi.model.PhotoSize;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Maximus on 19.06.2016.
 */
public class PhotoProcessor {
    private String accessToken;
    private int albumId;
    private int offset = 0;
    private final int WINDOWS_SIZE = 100;
    private PhotoSize photoSize;
    private int index;
    private ArrayList<DATA_photo> container = new ArrayList<>();
    private Iterator<DATA_photo> iterator;



    public PhotoProcessor(String accessToken, int albumId, PhotoSize max_photoSize) {
        this.accessToken = accessToken;
        this.albumId = albumId;
        this.photoSize = max_photoSize;
        iterator = container.iterator();
    }



    public boolean hasNext() {
        if (!iterator.hasNext()) {
            // Надо получить новую порцию
            return getNewPortion() != 0;
        }
        return true;
    }


    public DATA_photo next() {
        return iterator.next();
    }


    private int getNewPortion() {
        iterator = null;
        container.clear();

        String URL = UrlCreator.getPhotos(this.accessToken,this.albumId,this.offset,this.WINDOWS_SIZE);

        return 0; //TODO написать
    }


}
