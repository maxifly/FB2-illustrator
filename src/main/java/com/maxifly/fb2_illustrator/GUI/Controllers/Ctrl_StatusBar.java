package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_StatusBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 26.07.2016.
 */
public class Ctrl_StatusBar implements Initializable{
    final private DM_StatusBar dm_statusBar;

    @FXML private Label userlogin;

    public Ctrl_StatusBar(DM_StatusBar dm_statusBar) {
        this.dm_statusBar = dm_statusBar;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userlogin.textProperty().bindBidirectional(dm_statusBar.loginProperty());
    }
}
