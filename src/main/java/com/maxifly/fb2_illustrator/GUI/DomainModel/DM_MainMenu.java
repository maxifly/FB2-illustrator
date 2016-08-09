package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import javafx.stage.Stage;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.IOException;

/**
 * Created by Maximus on 07.08.2016.
 */
public class DM_MainMenu {
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(DM_MainMenu.class.getName());
    private Factory_GUI factory_gui;

    public DM_MainMenu(Factory_GUI factory_gui) {
        this.factory_gui = factory_gui;
    }

    public void vk_connect() throws IOException, InterruptedException {
        log.debug("connect");
        Stage stage = factory_gui.createLoginWindow();
        DM_Login dm_login = factory_gui.getDm_login();

        dm_login.connect();
        stage.show();
    }
}
