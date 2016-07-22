package com.maxifly.fb2_illustrator.GUI;

import javafx.scene.control.Label;

/**
 * Created by Maximus on 23.07.2016.
 */
public class StatusUser extends Label {
    public StatusUser() {
        super("login: ");
    }

    public void setLogin(String login) {
        this.setText("login: " + login);
    }
}
