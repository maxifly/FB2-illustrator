package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.vapi.ProjectUploader;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.VK_Metods;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Maximus on 08.10.2016.
 */
public class DM_ExportProject extends DM_Abstract {
    private  DM_StatusBar statusBar;

    public DM_ExportProject(DM_StatusBar statusBar) {
        this.statusBar = statusBar;
    }

    public void export(String albumAddr) throws MyException {
        if (statusBar.dmProject_Property().getValue() == null) {
            throw new MyException("Нет открытого проекта");
        }
        try {
            Long albumId = null;

            if (albumId == null) {
                VK_Metods vk_metods = new VK_Metods(statusBar.getToken());

                albumId = vk_metods.createAlbum();
            } else {
                albumId = UrlCreator.getAlbumId(albumAddr);
            }

        ProjectUploader projectUploader = new ProjectUploader(statusBar.getToken(), albumId);
        projectUploader.uploadProject(statusBar.dmProject_Property().getValue().getProject());
    } catch (IOException e) {
            throw new MyException("Неожиданная ошибка при экспорте проекта",e);
        }
    }

}
