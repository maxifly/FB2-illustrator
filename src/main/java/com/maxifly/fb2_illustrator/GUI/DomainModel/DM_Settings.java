package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.Settings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Maximus on 30.01.2017.
 */
public class DM_Settings extends DM_Abstract {
    private StringProperty ResW = new SimpleStringProperty();
    private StringProperty ResH = new SimpleStringProperty();

    private Factory_GUI factory_gui;
    private Settings settings;

    public DM_Settings(Factory_GUI factory_gui) {
        this.factory_gui = factory_gui;
        this.settings = factory_gui.getDm_statusBar().getSettings();
        refresh();

    }

    private void refresh() {
        setResH(settings.getBookSize_H().toString());
        setResW(settings.getBookSize_W().toString());
    }



    public String getResW() {
        return ResW.get();
    }

    public StringProperty resWProperty() {
        return ResW;
    }

    public void setResW(String resW) {
        this.ResW.set(resW);
    }

    public String getResH() {
        return ResH.get();
    }

    public StringProperty resHProperty() {
        return ResH;
    }

    public void setResH(String resH) {
        this.ResH.set(resH);
    }
}
