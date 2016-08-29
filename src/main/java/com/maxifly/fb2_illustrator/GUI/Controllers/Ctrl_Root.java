package com.maxifly.fb2_illustrator.GUI.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 29.08.2016.
 */
public class Ctrl_Root implements Initializable{
 @FXML
    BorderPane root;

    public void setListeners() {
        root.getScene().widthProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        System.out.println("with " + newValue);
                        System.out.println("root with "+  root.getWidth());
                    }
                });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {





    }
}
