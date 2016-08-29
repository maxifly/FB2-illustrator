package com.maxifly.fb2_illustrator.GUI.Controllers;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_MainMenu;
import com.maxifly.fb2_illustrator.IllustrationParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.IOException;

/**
 * Created by Maximus on 07.08.2016.
 */
public class Ctrl_MainMenu {


    private DM_MainMenu dm_mainMenu;

    @FXML
    private void action_vk(ActionEvent actionEvent) throws IOException, InterruptedException {

        switch ( ((MenuItem) actionEvent.getSource()).getId() ) {
            case "vk_connect":
                dm_mainMenu.vk_connect();
                break;
        }

    }
    @FXML
    private void action_prj(ActionEvent actionEvent) throws IOException, InterruptedException {

        switch ( ((MenuItem) actionEvent.getSource()).getId() ) {
            case "prj_open":
                dm_mainMenu.project_open();
                break;
            case "prj_ill":
                dm_mainMenu.ill();
        }

    }

    public Ctrl_MainMenu(DM_MainMenu dm_mainMenu) {
        this.dm_mainMenu = dm_mainMenu;
    }
}
