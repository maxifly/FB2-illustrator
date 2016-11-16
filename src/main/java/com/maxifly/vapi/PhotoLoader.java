package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.vapi.model.Illustration_VK;
import com.maxifly.jutils.downloader.*;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Maximus on 16.07.2016.
 */
public class PhotoLoader {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(PhotoLoader.class.getName());


    Path destDir;
    List<Illustration_VK> illustrationList = new ArrayList<>();
    Downloader downloader;


    public PhotoLoader(Path destDir) {
        this.destDir = destDir;
        downloader = new Downloader();
    }


    public void setIllustrationList(List<Illustration_VK> illustrationList) {
        this.illustrationList = illustrationList;
    }

    public void addIllustration(Illustration_VK ill) {
        this.illustrationList.add(ill);
    }

    public void download() throws IOException, ExecutionException, InterruptedException {
        for (Illustration_VK illustration : illustrationList) {
            String pictureURL = illustration.getUrl_picture();
            String fileType = UrlCreator.getFileType(pictureURL);
            File temp = File.createTempFile("ill", "." + fileType, destDir.toFile());
            illustration.setFile(temp.toPath());
            Download download = new Download(new URL(pictureURL), temp);
            this.downloader.startTask(download);
        }

        // Теперь проверим, что все файлы загрузились
        while (!downloader.checkTasks()) {
            log.debug("Illustration not load");
            Thread.sleep(10000);
        }
        log.debug("Illustration load");

        try {
            downloader.close();
        } catch (Exception e) {
            log.error("Error when try close downloader {}", e);
        }
    }


}
