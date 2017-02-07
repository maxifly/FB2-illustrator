package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Settings;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Created by Maximus on 30.01.2017.
 */
public class Ctrl_Settings extends Ctrl_Abstract implements Initializable {
    private static final int RES_H = 1;
    private static final int RES_V = 2;
    private static final int PROJ_DIR = 3;


    private DM_Settings dm_settings;
    private Factory_GUI factory_gui;
    private Pattern intPattern;

    private BooleanProperty invalidResH = new SimpleBooleanProperty();
    private BooleanProperty invalidResV = new SimpleBooleanProperty();
    private BooleanProperty invalidProjDir = new SimpleBooleanProperty();


    @FXML
    private TextField resH;

    @FXML
    private TextField resV;
    @FXML
    private TextField projDir;

    @FXML
    private ImageView warnResH;

    @FXML
    private ImageView warnResV;

    @FXML
    private ImageView warnProjDir;

    @FXML
    private Button btnSave;


    public Ctrl_Settings(Factory_GUI factory_gui, DM_Settings dm_settings) {
        this.factory_gui = factory_gui;
        this.dm_settings = dm_settings;
    }



    @FXML
    protected void actionSave(ActionEvent actionEvent) {
        dm_settings.save();
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
    @FXML
    protected void actionCancel(ActionEvent actionEvent) {
        dm_settings.refresh();
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        intPattern = Pattern.compile("^[0-9]+$");
        resH.textProperty().bindBidirectional(dm_settings.resHProperty());
        resV.textProperty().bindBidirectional(dm_settings.resVProperty());
        projDir.textProperty().bindBidirectional(dm_settings.projDirProperty());


        warnResH.visibleProperty().bindBidirectional(invalidResH);
        warnResV.visibleProperty().bindBidirectional(invalidResV);
        warnProjDir.visibleProperty().bindBidirectional(invalidProjDir);

        invalidResH.setValue(false);
        invalidResV.setValue(false);
        invalidProjDir.setValue(false);

//        resH.focusedProperty().addListener(
//                (observable, oldValue, newValue) -> focused(RES_H, newValue)
//        );
        resH.textProperty().addListener((observable, oldValue, newValue) ->
                changeValue(RES_H, newValue)
        );
        resV.textProperty().addListener((observable, oldValue, newValue) ->
                changeValue(RES_V, newValue)
        );
        projDir.textProperty().addListener((observable, oldValue, newValue) ->
                changeValue(PROJ_DIR, newValue)
        );


    }

    private boolean isInt(String str) {
        return intPattern.matcher(str).matches();
    }

    private void changeValue(int Field, String newValue) {

        switch (Field) {
            case RES_H:
                invalidResH.setValue(!isInt(newValue));
                break;
            case RES_V:
                invalidResV.setValue(!isInt(newValue));
                break;
            case PROJ_DIR:
                invalidProjDir.setValue(!Files.exists(Paths.get(newValue)));
                break;
        }

        btnSave.disableProperty().setValue(
                invalidResV.getValue()
                        || invalidResH.getValue()
                        || invalidProjDir.getValue());

    }


}
