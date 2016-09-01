package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Ill;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Project;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Obj;
import com.maxifly.fb2_illustrator.model.Illustration;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class Ctrl_Project extends Ctrl_Abstract implements Initializable
{
    @FXML
    VBox illustrations;

    @FXML
    BorderPane document_pane;

    private Factory_GUI factory_gui;
    private DM_Project dm_project;

    private ObjectProperty<DM_Ill> selected_ill = new SimpleObjectProperty<>();

    private Map<DM_Ill, Node> illNodes = new HashMap<>();


    public Ctrl_Project(Factory_GUI factory_gui, DM_Project dm_project) {
        this.factory_gui = factory_gui;
        this.dm_project = dm_project;
    }


    private void changeSelected_Ill(ObservableValue<? extends DM_Ill> observable, DM_Ill oldValue, DM_Ill newValue) {
        Node node = illNodes.get(newValue);
        document_pane.setCenter(node);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Illustration ill:dm_project.getIllList()) { //TODO здесь надо на биндинг поменять
            try {
                GUI_Obj gui_obj = factory_gui.createIll(ill);
                GUI_Obj gui_obj_ico = factory_gui.createIllIco( (DM_Ill) gui_obj.dm_model);

                illNodes.put((DM_Ill) gui_obj.dm_model,gui_obj.node);


                selected_ill.bindBidirectional( ((Ctrl_IllIco) gui_obj_ico.controll).selected_dm_ill_Property());

                illustrations.getChildren().add(gui_obj_ico.node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        selected_ill.addListener(new ChangeListener<DM_Ill>() {
            @Override
            public void changed(ObservableValue<? extends DM_Ill> observable, DM_Ill oldValue, DM_Ill newValue) {
                changeSelected_Ill(observable,  oldValue,  newValue);
            }
        });

    }
}
