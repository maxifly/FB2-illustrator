package com.maxifly.vapi.model;

/**
 * Created by Maximus on 08.12.2016.
 */
public class AlbumAddrAttributes {
    public long ownerId;
    public long albumId;

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
        int result = (int) (ownerId ^ (ownerId >>> 32));
        result = 31 * result + (int) (albumId ^ (albumId >>> 32));
        return result;
    }
}
