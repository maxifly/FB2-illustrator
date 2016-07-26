package com.maxifly.fb2_illustrator.GUI;

import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Maximus on 23.07.2016.
 */
public class Factory_GUI {
//    StatusUser status_user;
//
//    public StatusUser getStatus_user() {
//        if (status_user == null) {
//            status_user = new StatusUser();
//        }
//        return status_user;
//    }

    StatusBar statusBar = new StatusBar();

//    public HBox getStateRow() {
//        HBox stateRow = new HBox(10);
//        stateRow.setAlignment(Pos.CENTER_RIGHT);
//        stateRow.getChildren().add(this.getStatus_user());
//
//        stateRow.setStyle("-fx-border-color: blue");
//        stateRow.setSpacing(10);
//        stateRow.setPadding(new Insets(5,10,5,10));
//        return stateRow;
//    }


    public StatusBar getStatusBar() {
        return statusBar;
    }
    public HBox getStatusNBox() throws IOException {

        HBox hBox = FXMLLoader.load(Factory_GUI.class.getResource("StatusBar.fxml"));

        return hBox;
    }
}
