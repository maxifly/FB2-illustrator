package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_ImportVKProject;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 02.10.2016.
 */
public class Ctrl_ImportVKProject extends Ctrl_Abstract
        implements Initializable {
    private Factory_GUI factory_gui;
    private DM_ImportVKProject dm_importVKProject;

//TODO Написать создание окна

    @FXML
    TextField album_addr;
    @FXML
    TextField prj_code;

    @FXML
    Button btn_import;

    @FXML
    protected void btn_import_action(ActionEvent actionEvent) throws Exception {

        dm_importVKProject.importProject();

        Alert info = new Alert(Alert.AlertType.INFORMATION, "Проект загружен.");
        info.setHeaderText(null);
        info.showAndWait();

        Stage stage = (Stage) btn_import.getScene().getWindow();
        stage.close();

    }

    @FXML
    protected void action_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_import.getScene().getWindow();
        stage.close();
    }


    public Ctrl_ImportVKProject(Factory_GUI factory_gui, DM_ImportVKProject dm_importVKProject) {
        this.factory_gui = factory_gui;
        this.dm_importVKProject = dm_importVKProject;
    }

    private void change_text() {
        if ("".equals(album_addr.textProperty().getValue().trim()) ||
                "".equals(prj_code.textProperty().getValue().trim())
                ) {
            btn_import.setDisable(true);
        } else {
            btn_import.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_import.setDisable(true);
        prj_code.textProperty().bindBidirectional(dm_importVKProject.projectIdProperty());
        album_addr.textProperty().bindBidirectional(dm_importVKProject.albumAddrProperty());

        prj_code.textProperty().addListener((observable, oldValue, newValue) -> {
            change_text();
        });
        album_addr.textProperty().addListener((observable, oldValue, newValue) -> {
            change_text();
        });


    }

}
