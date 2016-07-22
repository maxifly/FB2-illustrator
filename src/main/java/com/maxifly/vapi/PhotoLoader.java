package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.vapi.model.InternetIllustration;
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
    List<InternetIllustration> illustrationList = new ArrayList<>();
    Downloader downloader;




    public PhotoLoader(Path destDir) {
        this.destDir = destDir;
        downloader = new Downloader();
    }


    public void setIllustrationList(List<InternetIllustration> illustrationList) {
        this.illustrationList = illustrationList;
    }

    public void download() throws IOException, ExecutionException, InterruptedException {
        for (InternetIllustration illustration : illustrationList) {
            File temp = File.createTempFile("ill", ".tmp",destDir.toFile());
            illustration.setFile(temp.toPath());
            Download download = new Download(new URL(illustration.getUrl_picture()),temp);
            this.downloader.startTask(download);
        }

        // Теперь проверим, что все файлы загрузились
        while (!downloader.checkTasks()) {
            log.debug("Illustration not load");
            Thread.sleep(10000);
        }
        log.debug("Illustration load");
    }


}
