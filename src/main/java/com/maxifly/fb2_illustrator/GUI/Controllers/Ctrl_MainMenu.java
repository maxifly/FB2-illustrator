package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_MainMenu;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Exception;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by Maximus on 07.08.2016.
 */
public class Ctrl_MainMenu {


    private DM_MainMenu dm_mainMenu;
    private Factory_GUI factory_gui;

    @FXML
    private void action_vk(ActionEvent actionEvent) throws IOException, InterruptedException {

        switch ( ((MenuItem) actionEvent.getSource()).getId() ) {
            case "vk_connect":
                dm_mainMenu.vk_connect();
                break;
        }

    }
    @FXML
    private void action_prj(ActionEvent actionEvent) throws IOException, InterruptedException, GUI_Exception {

        switch ( ((MenuItem) actionEvent.getSource()).getId() ) {
            case "prj_open":
                open_project(actionEvent);
                break;
            case "prj_test":
                dm_mainMenu.project_test();
                break;
            case "prj_ill":
                dm_mainMenu.ill();
        }

    }

    public Ctrl_MainMenu(DM_MainMenu dm_mainMenu, Factory_GUI factory_gui) {
        this.dm_mainMenu = dm_mainMenu;
        this.factory_gui = factory_gui;
    }


    protected void open_project (ActionEvent actionEvent) throws IOException, GUI_Exception {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser, "Открыть проект");

        Window win = factory_gui.getMainScene().getWindow(); // ((Node) actionEvent.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(win);

        if (file != null) {
            dm_mainMenu.project_open(file);
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

}