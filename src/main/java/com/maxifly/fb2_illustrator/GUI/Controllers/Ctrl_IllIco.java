package com.maxifly.fb2_illustrator.GUI.Controllers;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.App;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Ill;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class Ctrl_IllIco extends Ctrl_Abstract implements Initializable {
    //TODO Возможно стоит сделать этот класс родительским для DM_Ill?
    public static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    public static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(Ctrl_IllIco.class.getName());

    @FXML
    ImageView picture;
    @FXML
    VBox ico;

    @FXML
    Label ill_number;
    String kuku = "kuku";

    private DM_Ill dm_ill;
    private ObjectProperty<Path> picture_path = new SimpleObjectProperty<>();
    private ObjectProperty<DM_Ill> selected_dm_ill = new SimpleObjectProperty<>();


    private String defaultPicture = Factory_GUI.class.getResource("no_image.png").toString();


    public Ctrl_IllIco(DM_Ill dm_ill) {
        this.dm_ill = dm_ill;
    }

    @FXML
    protected void mouse_clicked(MouseEvent mouseEvent) {
        // int i = 1;
        selected_dm_ill.setValue(dm_ill);
    }

    @FXML
    protected void drag_detected(MouseEvent mouseEvent){
        log.debug("Start drag and drop " + ill_number.getText());
        Dragboard db = ((Node)mouseEvent.getSource()).startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        
        Image drop_image = createImage(picture_path.getValue(), 70, 70);

        content.putString(kuku); //ill_number.getText());
        db.setContent(content);
        db.setDragView(drop_image);

        mouseEvent.consume();
    }

    @FXML
    protected void drag_over(DragEvent dragEvent) {
//        log.debug("src: " + dragEvent.getSource() + " gesture: " + dragEvent.getGestureSource());
        if (dragEvent.getGestureSource() != dragEvent.getSource()) {
log.debug("true");
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
        dragEvent.consume();
    }

    public ObjectProperty<DM_Ill> selected_dm_ill_Property() {

        return this.selected_dm_ill;
    }

    private void changeSelected(ObservableValue<? extends DM_Ill> observable, DM_Ill oldValue, DM_Ill newValue) {

        if (dm_ill.equals(oldValue) && (!dm_ill.equals(newValue))) {
            // Снять фокус
            ico.setStyle(
                    "-fx-border-style: solid outside;"
                            + "-fx-border-color: blue;"
                            + "-fx-border-width: 0;");

        } else if (dm_ill.equals(newValue) && (!dm_ill.equals(oldValue))) {
            // Поставить фокус
            ico.setStyle(
                    "-fx-border-style: solid outside;"
                            + "-fx-border-color: blue;"
                            + "-fx-border-width: 2;");

        }
    }


    private Image createImage(Path file_path, int H, int W) {
        Image image = null;
        if (file_path != null && (file_path.toFile().exists())) {
            image = new Image(file_path.toFile().toURI().toString(), H, W, true, false);
        } else {
            image = new Image(defaultPicture, H, W, true, false);
        }
        return image;
    }

    private void showImage(Path file_path) {
        Image image = null;
        if (file_path != null && (file_path.toFile().exists())) {
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
                changePicturePath(observable, oldValue, newValue);
            }
        });

        selected_dm_ill.addListener(
                new ChangeListener<DM_Ill>() {
                    @Override
                    public void changed(ObservableValue<? extends DM_Ill> observable, DM_Ill oldValue, DM_Ill newValue) {
                        changeSelected(observable, oldValue, newValue);
                    }
                }
        );

    }
}
