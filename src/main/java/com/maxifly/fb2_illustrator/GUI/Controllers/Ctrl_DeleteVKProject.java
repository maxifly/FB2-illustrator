package com.maxifly.fb2_illustrator.GUI.Controllers;

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
public class Ctrl_DeleteVKProject extends Ctrl_Abstract
        implements Initializable {
    private Factory_GUI factory_gui;
    private DM_StatusBar dm_statusBar;
    private ObjectProperty<DM_Project> currProject = new SimpleObjectProperty<>();

    @FXML
    TextField album_addr;
    @FXML
    TextField prj_code;
    @FXML
    CheckBox current_prj_code;
    @FXML
    Button btn_del;

    @FXML
    protected void chckBox(ActionEvent actionEvent) {
        if (current_prj_code.isSelected()) {
            prj_code.setDisable(true);
            prj_code.textProperty().setValue(getCurrProjId());
        } else {
            prj_code.setDisable(false);
        }
    }

    @FXML
    protected void btn_del_action(ActionEvent actionEvent) throws Exception {
        long albumId = 0;

        albumId = UrlCreator.getAlbumId(album_addr.getText());

        ProjectProcessor projectProcessor = new ProjectProcessor(dm_statusBar.getToken(), albumId);
        projectProcessor.deleteProject(prj_code.getText().trim());

        Alert info = new Alert(Alert.AlertType.INFORMATION, "Процесс окончен.");
        info.setHeaderText(null);
        info.showAndWait();

        Stage stage = (Stage) btn_del.getScene().getWindow();
        stage.close();

    }

    @FXML
    protected void action_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_del.getScene().getWindow();
        stage.close();
    }

    public Ctrl_DeleteVKProject(Factory_GUI factory_gui) {
        this.factory_gui = factory_gui;
    }

    private void change_text() {
        if ("".equals(album_addr.textProperty().getValue().trim()) ||
                "".equals(prj_code.textProperty().getValue().trim())
                ) {
            btn_del.setDisable(true);
        } else {
            btn_del.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dm_statusBar = factory_gui.getDm_statusBar();
        currProject.bindBidirectional(dm_statusBar.dmProject_Property());
        btn_del.setDisable(true);
        current_prj_code.setSelected(true);
        prj_code.setDisable(true);
        prj_code.textProperty().setValue(getCurrProjId());

        prj_code.textProperty().addListener((observable, oldValue, newValue) -> {
            change_text();
        });
        album_addr.textProperty().addListener((observable, oldValue, newValue) -> {
            change_text();
        });


    }

    private String getCurrProjId() {
        if (currProject.getValue() != null) {
            return ((DM_Project) currProject.getValue()).getId();
        }
        return "";
    }
}
