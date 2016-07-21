package com.kuku.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.kuku.fb2_illustrator.model.Illustrations;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;


import java.nio.file.FileSystems;
import java.nio.file.Path;

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
        BookProcessor bookParse = new BookProcessor_FB20();
        IllustrationParser illustrationParser = new IllustrationParser();

//        Path path = FileSystems.getDefault().getPath("priv_fales", "test.fb2");
//        Path pathOut = FileSystems.getDefault().getPath("priv_fales", "test_out.fb2");
//        bookParse.setBookFile(path);
//        bookParse.setOutpootBook(pathOut);
//        bookParse.parse();

        Path inputFile = (FileSystems.getDefault().getPath("priv_fales", "test2.fb2"));
        Path outputFile = (FileSystems.getDefault().getPath("priv_fales", "test_out2.fb2"));

        Path imageXML = FileSystems.getDefault().getPath("priv_fales", "images1.xml");


        Illustrations illustrations = illustrationParser.parseIll(imageXML);


//        illustrations.addIllustration(new Illustration("ill_f1",
//                FileSystems.getDefault().getPath("priv_fales", "afrika_768.jpg"),
//                "Частное, пренебрегая деталями"));
//        illustrations.addIllustration(new Illustration("ill_f2",
//                FileSystems.getDefault().getPath("priv_fales", "ill2.jpg"),
//                "НеРеальность"));
//        illustrations.addIllustration(new Illustration("ill_f3",
//                FileSystems.getDefault().getPath("priv_fales", "ill3.jpg"),
//                "трансформирует"));

        bookParse.processBook(illustrations,inputFile,outputFile);
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
