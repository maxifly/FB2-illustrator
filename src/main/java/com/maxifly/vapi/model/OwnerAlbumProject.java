package com.maxifly.vapi.model;

/**
 * Created by Maximus on 31.10.2016.
 */
public class OwnerAlbumProject {
    public int ownerId;
    public int albumId;
    public Project_VK project_vk;

    public OwnerAlbumProject(int ownerId, int albumId, Project_VK project_vk) {
        this.ownerId = ownerId;
        this.albumId = albumId;
        this.project_vk = project_vk;
    }

    @Override
    public String toString() {
        return "(" + project_vk.getIllCount() + ") " + project_vk.getBookName();
    }
}
