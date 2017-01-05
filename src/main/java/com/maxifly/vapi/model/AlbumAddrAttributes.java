package com.maxifly.vapi.model;

/**
 * Created by Maximus on 08.12.2016.
 */
public class AlbumAddrAttributes {
    public int ownerId;
    public int albumId;

    public AlbumAddrAttributes(int ownerId, int albumId) {
        this.ownerId = ownerId;
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumAddrAttributes that = (AlbumAddrAttributes) o;

        if (ownerId != that.ownerId) return false;
        return albumId == that.albumId;
    }

    @Override
    public int hashCode() {
        int result = ownerId;
        result = 31 * result + albumId;
        return result;
    }
}
