package com.kuku.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;


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

     private void shutdown() {
        log.info("SHUTDOWN_NOW");

    }




    public static void main(String[] params) throws ExecutionException, InterruptedException, CloneNotSupportedException {

        App app = new App();

    }

    private class AppShutdownHook extends Thread {

        public void run() {
            shutdown();
            log.info("Done work");
        }
    }

}
