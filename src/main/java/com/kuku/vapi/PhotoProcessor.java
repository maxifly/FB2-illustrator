package com.kuku.vapi;

/**
 * Created by Maximus on 19.06.2016.
 */
public class PhotoProcessor {
    private String accessToken;
    private String albumId;
    private int offset = 0;
    private final int WINDOWS_SIZE = 100;


    public PhotoProcessor(String accessToken, String albumId) {
        this.accessToken = accessToken;
        this.albumId = albumId;
    }
}
