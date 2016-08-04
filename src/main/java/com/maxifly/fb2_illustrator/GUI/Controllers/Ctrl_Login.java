package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Login;
import com.maxifly.vapi.Connect;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.model.ScopeElement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 04.08.2016.
 */
public class Ctrl_Login implements Initializable {
    private DM_Login dm_login;

    public Ctrl_Login(DM_Login dm_login) {
        this.dm_login = dm_login;
    }

    @FXML
    WebView webView_Login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dm_login.setWebEngine(webView_Login.getEngine());
    }


}
