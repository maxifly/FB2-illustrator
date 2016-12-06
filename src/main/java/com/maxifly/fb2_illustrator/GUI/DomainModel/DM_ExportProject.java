package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.vapi.ProjectProcessor;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.VK_Metods;

import java.io.IOException;

/**
 * Created by Maximus on 08.10.2016.
 */
public class DM_ExportProject extends DM_Abstract {
    private DM_StatusBar statusBar;

    public DM_ExportProject(DM_StatusBar statusBar) {
        this.statusBar = statusBar;
    }

    public long export(String albumAddr) throws MyException {
        if (statusBar.dmProject_Property().getValue() == null) {
            throw new MyException("Нет открытого проекта");
        }
        try {
            Long albumId = null;

            if (albumAddr == null) {
                VK_Metods vk_metods = new VK_Metods(statusBar.getToken());

                albumId = vk_metods.createAlbum();
            } else {
                albumId = UrlCreator.getAlbumId(albumAddr);
            }

            ProjectProcessor projectProcessor = new ProjectProcessor(statusBar.getToken(), null, albumId);
            projectProcessor.uploadProject(statusBar.dmProject_Property().getValue().getProject());
            return albumId;
        } catch (IOException e) {
            throw new MyException("Неожиданная ошибка при экспорте проекта", e);
        }
    }

}
