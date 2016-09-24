package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_MainMenu;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Exception;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.vapi.PhotoLoader;
import com.maxifly.vapi.PhotoUploader;
import com.maxifly.vapi.ProjectUploader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 07.08.2016.
 */
public class Ctrl_MainMenu implements Initializable {


    private DM_MainMenu dm_mainMenu;
    private Factory_GUI factory_gui;
    private ObjectProperty<Project> currentProjectProperty = new SimpleObjectProperty<>();

    @FXML
    private MenuItem prj_save;
    @FXML
    private MenuItem prj_save_as;


    @FXML
    private void action_vk(ActionEvent actionEvent) throws IOException, InterruptedException, MyException {

        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case "vk_connect":
                dm_mainMenu.vk_connect();
                break;
            case "vk_test":

//                Project currproject = dm_mainMenu.currentProjectProperty().getValue();
//                ProjectUploader projectUploader = new ProjectUploader(factory_gui.getDm_statusBar().getToken(),233176977);
//                projectUploader.uploadProject(currproject);


                PhotoUploader photoUploader = new PhotoUploader(factory_gui.getDm_statusBar().getToken(),233176977);
                photoUploader.prepare();
                photoUploader.uploadPhoto(new File("c:\\kuku.jpg"), "desc");

        }

    }

    @FXML
    private void action_prj(ActionEvent actionEvent) throws IOException, InterruptedException, GUI_Exception {

        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case "prj_open":
                open_project(actionEvent);
                break;
            case "prj_new":
                new_project(actionEvent);
                break;
            case "prj_save_as":
                save_as_project(actionEvent);
                break;
            case "prj_save":
                save_project(actionEvent); // TODO Сделать сохранение
                break;
            case "prj_test":
                dm_mainMenu.project_test();
                projectItemsDisable();
                break;
            case "prj_ill":
                dm_mainMenu.ill();
        }

    }

    public Ctrl_MainMenu(DM_MainMenu dm_mainMenu, Factory_GUI factory_gui) {
        this.dm_mainMenu = dm_mainMenu;
        this.factory_gui = factory_gui;
    }


    protected void open_project(ActionEvent actionEvent) throws IOException, GUI_Exception {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser, "Открыть проект");

        Window win = factory_gui.getMainScene().getWindow(); // ((Node) actionEvent.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(win);

        if (file != null) {
            dm_mainMenu.project_open(file);
        }
        projectItemsDisable();
    }

    protected void save_project(ActionEvent actionEvent) throws IOException, GUI_Exception {
        dm_mainMenu.project_save();
        projectItemsDisable();
    }


    protected void save_as_project(ActionEvent actionEvent) throws IOException, GUI_Exception {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser, "Сохранить проект как");

        Window win = factory_gui.getMainScene().getWindow(); // ((Node) actionEvent.getSource()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(win);

        if (file != null) {
            dm_mainMenu.project_save(file);
        }


        projectItemsDisable();
    }

    protected void new_project(ActionEvent actionEvent) throws IOException, GUI_Exception {
        dm_mainMenu.project_new();
        projectItemsDisable();
    }


    private void projectItemsDisable() {
        if (currentProjectProperty.getValue() != null) {
            prj_save_as.setDisable(false);
            if (((Project) currentProjectProperty.getValue()).getProjectFile() != null) {
                prj_save.setDisable(false);
            } else {
                prj_save.setDisable(true);
            }
        } else {
            prj_save.setDisable(true);
            prj_save_as.setDisable(true);
        }


    }

    private static void configureFileChooser(
            final FileChooser fileChooser,
            String title) {
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PILL", "*.pill")
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.currentProjectProperty.bindBidirectional(dm_mainMenu.currentProjectProperty());
        projectItemsDisable();

    }
}