package com.maxifly.vapi.model;

/**
 * Created by Maximus on 08.12.2016.
 */
public class AlbumAddrParseResult {
    public long ownerId;
    public long albumId;

    public AlbumAddrParseResult(int ownerId, int albumId) {
        this.ownerId = ownerId;
        this.albumId = albumId;
    }
}
