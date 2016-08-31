package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Abstract;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Project;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class Ctrl_Project extends Ctrl_Abstract implements Initializable
{
    private Factory_GUI factory_gui;
    private DM_Project dm_project;


    public Ctrl_Project(Factory_GUI factory_gui, DM_Project dm_project) {
        this.factory_gui = factory_gui;
        this.dm_project = dm_project;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
