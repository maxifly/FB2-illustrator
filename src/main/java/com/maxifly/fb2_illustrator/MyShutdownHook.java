package com.maxifly.fb2_illustrator;

import com.maxifly.fb2_illustrator.GUI.Factory_GUI;

import java.io.IOException;

/**
 * Created by Maximus on 30.10.2016.
 */
public class MyShutdownHook extends Thread {
    private Factory_GUI factory_gui;

    public MyShutdownHook(Factory_GUI factory_gui) {
        this.factory_gui = factory_gui;
    }

    public void run() {
        Settings settings = this.factory_gui.getDm_statusBar().getSettings();

        double H = factory_gui.getMainScene().getWindow().getHeight();
        double W = factory_gui.getMainScene().getWindow().getWidth();

        settings.setWinSize_H(H);
        settings.setWinSize_W(W);

        System.out.println("shutdown");
        try {
            settings.writeSettings(Constants.ensureAppDataDir());
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }
}
