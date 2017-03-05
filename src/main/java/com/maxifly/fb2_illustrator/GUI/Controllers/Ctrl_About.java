package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.google.gson.Gson;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Ill;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Project;
import com.maxifly.fb2_illustrator.GUI.DomainModel.ILL_IllIco_Nodes;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Obj;
import com.maxifly.fb2_illustrator.GUI.IllChangeOrder;
import com.maxifly.fb2_illustrator.LatestVersion;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.Utils;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.vapi.RestSender;
import com.maxifly.vapi.model.RestResponse;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class Ctrl_About extends Ctrl_Abstract implements Initializable {
    @FXML
    Text curr_version;

    @FXML
    Text last_version;


    public void hp_link(ActionEvent e) {
        String url = ((Hyperlink) e.getSource()).getText();
        try {
            Utils.showUrlInBrowser(url);
        } catch (MyException e1) {
            e1.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        curr_version.setText(getCurrVersion());
        last_version.setText(getLastVersion());

    }


    private String getCurrVersion() {
        Package objPackage = this.getClass().getPackage();
        String manifestVersion = objPackage.getSpecificationVersion();
        String version = (manifestVersion == null) ? "0.0" : manifestVersion;
        return version;
    }

    private String getLastVersion() {
        String latestVersionName = "unknown";
        try {
            RestSender restSender = new RestSender();
            RestResponse restResponse = restSender.sendGet("https://api.github.com/repos/maxifly/FB2-illustrator/releases/latest");

            LatestVersion latestVersion = null;
            Gson gson = new Gson();

            if (restResponse.getResponseCode() == 200) {
                latestVersion = gson.fromJson(restResponse.getResponseBody().toString(), LatestVersion.class);
                latestVersionName = latestVersion.tag_name.substring(1);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return latestVersionName;
    }

}
