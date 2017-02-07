package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.Settings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

/**
 * Created by Maximus on 30.01.2017.
 */
public class DM_Settings extends DM_Abstract {
    private StringProperty resV = new SimpleStringProperty();
    private StringProperty resH = new SimpleStringProperty();
    private StringProperty projDir = new SimpleStringProperty();

    private Factory_GUI factory_gui;
    private Settings settings;

    public DM_Settings(Factory_GUI factory_gui) {
        this.factory_gui = factory_gui;
        this.settings = factory_gui.getDm_statusBar().getSettings();
        refresh();

    }

    public void refresh() {
        setResH(settings.getBookSize_H().toString());
        setResV(settings.getBookSize_V().toString());
        setProjDir(settings.getProjectsDir());
    }

    public void save() {
        settings.setBookSize_H(Integer.parseInt(getResH()));
        settings.setBookSize_V(Integer.parseInt(getResV()));
        settings.setProjectsDir(getProjDir());

        try {
            settings.writeSettings(Constants.ensureAppDataDir());
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }

        factory_gui.getDm_statusBar().refresh();


    }





    public String getResV() {
        return resV.get();
    }

    public StringProperty resVProperty() {
        return resV;
    }

    public void setResV(String resW) {
        this.resV.set(resW);
    }

    public String getResH() {
        return resH.get();
    }

    public StringProperty resHProperty() {
        return resH;
    }

    public void setResH(String resH) {
        this.resH.set(resH);
    }

    public String getProjDir() {
        return projDir.get();
    }

    public StringProperty projDirProperty() {
        return projDir;
    }

    public void setProjDir(String projDir) {
        this.projDir.set(projDir);
    }
}
