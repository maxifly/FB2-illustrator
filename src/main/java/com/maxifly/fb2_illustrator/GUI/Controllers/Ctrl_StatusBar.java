package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Project;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_StatusBar;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 26.07.2016.
 */
public class Ctrl_StatusBar implements Initializable{
    final private DM_StatusBar dm_statusBar;
    private Factory_GUI factory_gui;

    private ObjectProperty<DM_Project> dmProjectProperty = new SimpleObjectProperty<>();
    private ObjectProperty<File> projectFileProperty = new SimpleObjectProperty<>();
    private BooleanProperty changedProjectProperty = new SimpleBooleanProperty();

    @FXML private Label userlogin;

    public Ctrl_StatusBar(DM_StatusBar dm_statusBar, Factory_GUI factory_gui) {
        this.dm_statusBar = dm_statusBar;
        this.factory_gui = factory_gui;
    }

    private void project_changed(DM_Project newValue){
        this.projectFileProperty.bindBidirectional(newValue.projectFile_Property());
        show_Title();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userlogin.textProperty().bindBidirectional(dm_statusBar.loginProperty());
        dmProjectProperty.bindBidirectional(dm_statusBar.dmProject_Property());

        dmProjectProperty.addListener((observable, oldValue, newValue) -> project_changed( newValue));


    }

    private void show_Title() {
        String title = "fb2_illustrator";
        if (this.projectFileProperty.getValue() != null) {
            title = title + " - " + this.projectFileProperty.getValue().toString();
        }
        if (this.changedProjectProperty.getValue()!=null && this.changedProjectProperty.getValue() ) {
            title = title + " (*)";
        }

        try {
            ((Stage)factory_gui.getMainScene().getWindow()).setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
