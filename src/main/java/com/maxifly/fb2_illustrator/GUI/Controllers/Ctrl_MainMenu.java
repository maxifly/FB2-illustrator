package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

/**
 * Created by Maximus on 07.08.2016.
 */
public class Ctrl_MainMenu {
    private DM_MainMenu dm_mainMenu;

    @FXML private void action_File_Close(ActionEvent actionEvent) {
        MenuItem menuItem = (MenuItem) actionEvent.getSource();


        System.out.println("action "+ menuItem.getId());
    }

    public Ctrl_MainMenu(DM_MainMenu dm_mainMenu) {
        this.dm_mainMenu = dm_mainMenu;
    }
}
