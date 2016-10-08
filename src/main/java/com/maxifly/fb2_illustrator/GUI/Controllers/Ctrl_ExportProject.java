package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_ExportProject;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.vapi.UrlCreator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Maximus on 08.10.2016.
 */
public class Ctrl_ExportProject extends Ctrl_Abstract
   implements Initializable
{
    private Factory_GUI factory_gui;
    private DM_ExportProject dm_exportProject;



    @FXML
    RadioButton rb_new;
    @FXML
    RadioButton rb_exists;
    @FXML
    TextField album_addr;
    @FXML
    Button btn_export;

    @FXML
    protected void action_export(ActionEvent actionEvent) throws MyException {

        long album_id;
        if(rb_exists.isSelected()) {
            album_id = dm_exportProject.export(album_addr.getText());
        } else {
            album_id = dm_exportProject.export(null);
        }

        Alert info = new Alert(Alert.AlertType.INFORMATION,
                "Проект экспортирован в альбом с идентификатором " + album_id);
        info.setHeaderText(null);
        info.showAndWait();

        Stage stage = (Stage) btn_export.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void action_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_export.getScene().getWindow();
        stage.close();
    }


    @FXML
    protected void rb_action(ActionEvent actionEvent) {
        disabled();
    }

    private void disabled() {
        if (rb_exists.isSelected()) {
            if (album_addr.textProperty().getValue() != null) {
                btn_export.setDisable(false);
            } else {
                btn_export.setDisable(true);
            }
        } else {
            btn_export.setDisable(false);
        }
    }

    public Ctrl_ExportProject(Factory_GUI factory_gui, DM_ExportProject dm_exportProject) {
        this.factory_gui = factory_gui;
        this.dm_exportProject = dm_exportProject;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        album_addr.textProperty().addListener((observable, oldValue, newValue) -> {
            disabled();
        });


    }
}
