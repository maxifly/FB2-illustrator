package com.maxifly.fb2_illustrator.GUI.DomainModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Maximus on 28.07.2016.
 */
public class DM_StatusBar {
   private StringProperty login;
   private String token;

    public DM_StatusBar() {
        this.login = new SimpleStringProperty("login: ");
    }

    public void setLogin(String login, String token) {
        this.login.set("login: " + login);
        this.token = token;
    }

    public StringProperty loginProperty() {
        return login;
    }
}
