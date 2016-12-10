package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.vapi.ProjectProcessor;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.VK_Metods;
import com.maxifly.vapi.model.AlbumAddrParseResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Maximus on 08.10.2016.
 */
public class DM_ExportProject extends DM_Abstract {
    private DM_StatusBar statusBar;

    public DM_ExportProject(DM_StatusBar statusBar) {
        this.statusBar = statusBar;
    }


    public long exportInNewUserAlbum() throws MyException {
        if (statusBar.dmProject_Property().getValue() == null) {
            throw new MyException("Нет открытого проекта");
        }
        try {
            VK_Metods vk_metods = new VK_Metods(statusBar.getToken());
            long albumId = vk_metods.createAlbum(null);
            return export(statusBar.getUserId(), albumId);
        } catch (IOException e) {
            throw new MyException("Неожиданная ошибка при экспорте проекта", e);
        }
    }

    public long exportInNewGroupAlbum(String groupAddr) throws MyException {
        if (statusBar.dmProject_Property().getValue() == null) {
            throw new MyException("Нет открытого проекта");
        }
        try {
            Long ownerId = (long) UrlCreator.getOwnerIdByOwnerURL(groupAddr);
            if (ownerId > 0) {ownerId = -1 * ownerId;}

            VK_Metods vk_metods = new VK_Metods(statusBar.getToken());
            long albumId = vk_metods.createAlbum(-1 *ownerId);
            return export(ownerId, albumId);
        } catch (IOException e) {
            throw new MyException("Неожиданная ошибка при экспорте проекта", e);
        }
    }

    public long export(String albumAddr) throws MyException {
        if (statusBar.dmProject_Property().getValue() == null) {
            throw new MyException("Нет открытого проекта");
        }

        AlbumAddrParseResult albumAddrParseResult = UrlCreator.parseAlbumURL(albumAddr);
        return export(albumAddrParseResult.ownerId, albumAddrParseResult.albumId);

    }


    public long export(Long ownerId, Long albumId) throws MyException {

        if (statusBar.dmProject_Property().getValue() == null) {
            throw new MyException("Нет открытого проекта");
        }
        try {
//            Long albumId = null;
//            Long ownerId = null;
//
//
//            if (albumAddr == null) {
//                VK_Metods vk_metods = new VK_Metods(statusBar.getToken());
//
//                albumId = vk_metods.createAlbum(); // TODO Сделать создание альюлма в группе
//            } else {
//                AlbumAddrParseResult albumAddrParseResult = UrlCreator.parseAlbumURL(albumAddr);
//                albumId = albumAddrParseResult.albumId;
//                ownerId = albumAddrParseResult.ownerId;
//
//                System.out.println("albumId " + albumId + "  ownerId " + ownerId);
//
//            }

            ProjectProcessor projectProcessor = new ProjectProcessor(statusBar.getToken(), ownerId, albumId);
            projectProcessor.uploadProject(statusBar.dmProject_Property().getValue().getProject());
            return albumId;
        } catch (IOException e) {
            throw new MyException("Неожиданная ошибка при экспорте проекта", e);
        }
    }

}
