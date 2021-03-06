package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.Settings;
import com.maxifly.fb2_illustrator.model.Project;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Maximus on 28.07.2016.
 */
public class DM_StatusBar {
    private StringProperty login;
    private Long userId;
    private String token;
    private ObjectProperty<DM_Project> dmProjectProperty = new SimpleObjectProperty<>();

    private StringProperty bookRes = new SimpleStringProperty();



    private Settings settings;


    public DM_StatusBar() {
        this.login = new SimpleStringProperty("login: ");
    }

    public void setLogin(String login, String token) {
        this.login.set("login: " + login);
        this.token = token;
    }

    /**
     * Обновляет данные в статусной строке
     */
    public void refresh() {
        setBookRes(settings.getBookSize_H().toString()+ "x" + settings.getBookSize_V().toString());
    }

    public StringProperty loginProperty() {
        return login;
    }

    public String getToken() {
        return token;
    }

    public ObjectProperty<DM_Project> dmProject_Property() {
        return this.dmProjectProperty;
    }

    public void setCurrentProject(DM_Project dm_project) {
        this.dmProjectProperty.set(dm_project);
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
        refresh();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getBookRes() {
        return bookRes.get();
    }

    public StringProperty bookResProperty() {
        return bookRes;
    }

    public void setBookRes(String bookRes) {
        this.bookRes.set(bookRes);
    }
}
