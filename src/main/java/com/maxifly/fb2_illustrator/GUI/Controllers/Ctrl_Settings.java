package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Settings;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 30.01.2017.
 */
public class Ctrl_Settings extends Ctrl_Abstract implements Initializable {
    private DM_Settings dm_settings;
    private Factory_GUI factory_gui;

    @FXML
    private TextField resH;

    @FXML
    private TextField resV;
    @FXML
    private TextField projDir;


    public Ctrl_Settings(Factory_GUI factory_gui, DM_Settings dm_settings) {
        this.factory_gui = factory_gui;
        this.dm_settings = dm_settings;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resH.textProperty().bindBidirectional(dm_settings.resHProperty());

    }
}
