package com.maxifly.fb2_illustrator.GUI;

import com.maxifly.fb2_illustrator.GUI.Controllers.Ctrl_StatusBar;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_StatusBar;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

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


    final private DM_StatusBar dm_statusBar = new DM_StatusBar();
    private HBox hBox_statusBar;


    public DM_StatusBar getDm_statusBar() {
        return dm_statusBar;
    }

    public HBox getStatusBar() throws IOException {

        if (this.hBox_statusBar == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Factory_GUI.class.getResource("StatusBar.fxml"));
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aClass) {
                    return new Ctrl_StatusBar(dm_statusBar);
                }
            });


            this.hBox_statusBar = loader.load();
        }
        return this.hBox_statusBar;
    }
}
