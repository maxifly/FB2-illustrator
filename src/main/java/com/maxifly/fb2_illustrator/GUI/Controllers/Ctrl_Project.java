package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Abstract;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Ill;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Project;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Obj;
import com.maxifly.fb2_illustrator.model.Illustration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class Ctrl_Project extends Ctrl_Abstract implements Initializable
{
    @FXML
    VBox illustrations;


    private Factory_GUI factory_gui;
    private DM_Project dm_project;


    public Ctrl_Project(Factory_GUI factory_gui, DM_Project dm_project) {
        this.factory_gui = factory_gui;
        this.dm_project = dm_project;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Illustration ill:dm_project.getIllList()) {
            try {
                GUI_Obj gui_obj = factory_gui.createIll(ill);
                GUI_Obj gui_obj_ico = factory_gui.createIllIco( (DM_Ill) gui_obj.dm_model);
                illustrations.getChildren().add(gui_obj_ico.node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
