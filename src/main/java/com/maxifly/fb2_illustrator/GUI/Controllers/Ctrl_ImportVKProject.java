package com.maxifly.fb2_illustrator.GUI.Controllers;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.DomainModel.*;
import com.maxifly.fb2_illustrator.GUI.ExceptionHandler;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Obj;
import com.maxifly.jutils.I_Progress;
import com.maxifly.vapi.ProjectProcessor;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.model.OwnerAlbumProject;
import com.maxifly.vapi.model.Project_VK;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 02.10.2016.
 */
public class Ctrl_ImportVKProject extends Ctrl_Abstract
        implements Initializable {
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(Ctrl_ImportVKProject.class.getName());

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

        GUI_Obj gui_obj = factory_gui.createProgressWindow();
        dm_importVKProject.setProgress_monitor((I_Progress) gui_obj.dm_model);

        DM_Task_DownloadProject download_task = new DM_Task_DownloadProject(dm_importVKProject);


        ((DM_ProgressWindow) gui_obj.dm_model).setTask(download_task);

        Stage monitorWindow = factory_gui.createModalWindow(gui_obj.node);
        monitorWindow.show();

//        load_progress.progressProperty().bind(refreshTask.progressProperty());
//        load_message.textProperty().bind(refreshTask.messageProperty());

        download_task.setOnSucceeded(event -> refresh_complite(event, monitorWindow));
        download_task.setOnFailed(event -> refresh_filed(event, monitorWindow));
        download_task.setOnCancelled(event -> refresh_cancelled(event, monitorWindow));
        new Thread(download_task).start();

////TODO Запустить
//        dm_importVKProject.importProject();
//
//        Alert info = new Alert(Alert.AlertType.INFORMATION, "Проект загружен.");
//        info.setHeaderText(null);
//        info.showAndWait();
//
//        Stage stage = (Stage) btn_import.getScene().getWindow();
//        stage.close();

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
        if (album_addr.textProperty().getValue() == null ||
                "".equals(album_addr.textProperty().getValue().trim()) ||
                prj_code.textProperty().getValue() == null ||
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


    private void refresh_cancelled(WorkerStateEvent event, Stage monitorWindow) {
        log.error("Task cancelled");
        monitorWindow.close();
    }

    private void refresh_filed(WorkerStateEvent event, Stage monitorWindow) {
        log.error("Error {}", event.getSource().getException());
        monitorWindow.close();
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        exceptionHandler.uncaughtException(Thread.currentThread(), event.getSource().getException());

    }

    private void refresh_complite(WorkerStateEvent event, Stage monitorWindow) {
        log.debug("task successed complite");
        monitorWindow.close();
        Alert info = new Alert(Alert.AlertType.INFORMATION, "Проект загружен.");
        info.setHeaderText(null);
        info.showAndWait();

        Stage stage = (Stage) btn_import.getScene().getWindow();
        stage.close();
    }


}
