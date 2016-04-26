package com.kuku.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.kuku.fb2_illustrator.model.Illustration;
import com.kuku.fb2_illustrator.model.Illustrations;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;


import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

public class App {
    public static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    public static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(App.class.getName());


    public App() {
        AppShutdownHook appShutdownHook = new AppShutdownHook();
        Runtime.getRuntime().addShutdownHook(appShutdownHook);
    }

    public void run() throws Exception {
        BookParse bookParse = new BookParse();
//        Path path = FileSystems.getDefault().getPath("priv_fales", "test.fb2");
//        Path pathOut = FileSystems.getDefault().getPath("priv_fales", "test_out.fb2");
//        bookParse.setBookFile(path);
//        bookParse.setOutpootBook(pathOut);
//        bookParse.parse();

        bookParse.setBookFile(FileSystems.getDefault().getPath("priv_fales", "test2.fb2"));
        bookParse.setOutpootBook(FileSystems.getDefault().getPath("priv_fales", "test_out2.fb2"));

        Illustrations illustrations = new Illustrations();

        illustrations.addIllustration(new Illustration("file1","Частное, пренебрегая деталями"));
        illustrations.addIllustration(new Illustration("file2","НеРеальность"));
        illustrations.addIllustration(new Illustration("file3","трансформирует"));

        bookParse.parse(illustrations);
    }

     private void shutdown() {
        log.info("SHUTDOWN_NOW");

    }




    public static void main(String[] params) throws Exception {

        App app = new App();
        app.run();

    }

    private class AppShutdownHook extends Thread {

        public void run() {
            shutdown();
            log.info("Done work");
        }
    }

}
