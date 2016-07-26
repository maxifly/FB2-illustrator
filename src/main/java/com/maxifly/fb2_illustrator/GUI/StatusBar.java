package com.maxifly.fb2_illustrator.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by Maximus on 26.07.2016.
 */
public class StatusBar {
    @FXML private Label userlogin;

    public void setLogin(String login){
        if (login == null) {
            this.userlogin.setText("login:");
        } else {
            this.userlogin.setText("login: " + login);
        }
    }
}
