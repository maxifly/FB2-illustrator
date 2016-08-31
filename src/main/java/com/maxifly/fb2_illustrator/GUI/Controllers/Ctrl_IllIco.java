package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Ill;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class Ctrl_IllIco extends Ctrl_Abstract implements Initializable {
    //TODO Возможно стоит сделать этот класс родительским для DM_Ill?


    @FXML
    ImageView picture;

    @FXML
    Label ill_number;

    private DM_Ill dm_ill;
    private ObjectProperty<Path> picture_path = new SimpleObjectProperty<>();


    private String defaultPicture = Factory_GUI.class.getResource("no_image.png").toString();


    public Ctrl_IllIco(DM_Ill dm_ill) {
        this.dm_ill = dm_ill;
    }

    private void showImage(Path file_path) {
        Image image = null;
        if ( file_path!= null && (file_path.toFile().exists())) {
            image = new Image(file_path.toFile().toURI().toString());
        } else {
            image = new Image(defaultPicture);
        }
        picture.setImage(image);
    }

    private void changePicturePath(ObservableValue<? extends Path> observable, Path oldValue, Path newValue) {
        showImage(newValue);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        picture_path.bindBidirectional(dm_ill.picture_path_Property());
        ill_number.textProperty().bindBidirectional(dm_ill.ill_id_Property());

        showImage(picture_path.getValue());

        picture_path.addListener(new ChangeListener<Path>() {
            @Override
            public void changed(ObservableValue<? extends Path> observable, Path oldValue, Path newValue) {
                changePicturePath(observable,oldValue,newValue);
            }
        });

    }
}
