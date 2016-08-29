package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Ill;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Maxim.Pantuhin on 29.08.2016.
 */
public class Ctrl_Ill implements Initializable {
    @FXML
    VBox templates;

    private ListProperty<SearchTemplate_POJO> searchTemplates = new SimpleListProperty<>();

    private DM_Ill dm_ill;
    private Factory_GUI factory_gui;

    public Ctrl_Ill(Factory_GUI factory_gui, DM_Ill dm_ill) {
        this.dm_ill = dm_ill;
        this.factory_gui = factory_gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchTemplates.bindBidirectional(dm_ill.searchTemplates_Property());
        try {

            for (SearchTemplate_POJO stp : searchTemplates.getValue()) {
                Node node = factory_gui.createSearchTemplate(stp);
                templates.getChildren().add(node);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
