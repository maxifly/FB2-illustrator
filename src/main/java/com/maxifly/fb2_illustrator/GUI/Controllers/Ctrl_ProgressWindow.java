package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_ProgressWindow;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Project;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_StatusBar;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.vapi.ProjectProcessor;
import com.maxifly.vapi.UrlCreator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 02.10.2016.
 */
public class Ctrl_ProgressWindow extends Ctrl_Abstract
        implements Initializable {
    private Factory_GUI factory_gui;
    private DM_ProgressWindow dm_progressWindow;

    @FXML
    ProgressBar progress;
    @FXML
    TextField progress_mess;

    @FXML
    private void action_cancel(ActionEvent actionEvent) {
        dm_progressWindow.cancel();
    }

    public Ctrl_ProgressWindow(Factory_GUI factory_gui, DM_ProgressWindow dm_progressWindow) {
        this.factory_gui = factory_gui;
        this.dm_progressWindow = dm_progressWindow;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progress.progressProperty().bindBidirectional(dm_progressWindow.progressProperty());
        progress_mess.textProperty().bindBidirectional(dm_progressWindow.progress_messageProperty());

    }
}
