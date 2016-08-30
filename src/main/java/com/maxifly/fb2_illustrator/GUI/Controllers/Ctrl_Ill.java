package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Ill;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_SearchTemplate;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Obj;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.fb2_illustrator.model.TemplateType;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Maxim.Pantuhin on 29.08.2016.
 */
public class Ctrl_Ill implements Initializable {
    @FXML
    VBox templates;

    @FXML
    GridPane gridpane;


    @FXML
    protected void kuku(ActionEvent actionEvent) {
        System.out.println("W " + gridpane.widthProperty().getValue());
    }

    private SetProperty<SearchTemplate_POJO> searchTemplates = new SimpleSetProperty<>();
    private Map<SearchTemplate_POJO, GUI_Obj> searchTemplates_GUIs = new HashMap<>();



    private DM_Ill dm_ill;
    private Factory_GUI factory_gui;

    private ObservableList<SearchTemplate_POJO> needDeleteList = FXCollections.observableList(new ArrayList<>());
    private ObservableList<SearchTemplate_POJO> needEditList = FXCollections.observableList(new ArrayList<>());


    public Ctrl_Ill(Factory_GUI factory_gui, DM_Ill dm_ill) {
        this.dm_ill = dm_ill;
        this.factory_gui = factory_gui;
    }

    @FXML
    private void btn_add(ActionEvent actionEvent) throws IOException {
        SearchTemplate_POJO stp = new SearchTemplate_POJO(TemplateType.substr,null,null);
        GUI_Obj gui_obj = factory_gui.createSearchTemplate_Edit(stp);
        Stage stage = factory_gui.createModalWindow(gui_obj.node);
        stage.showAndWait();

        if (stp.template != null) {
            showSearchTemplate(stp);
        }
    }

    private void needDeleted(ListChangeListener.Change<? extends SearchTemplate_POJO> change){
        while (change.next()) {
            if (change.wasAdded()) {
                // Надо удалить запись с шаблоном
               for (SearchTemplate_POJO stp : change.getAddedSubList()) {
                   System.out.println("delete stp " + stp);
                   Node node = searchTemplates_GUIs.get(stp).node;
                   System.out.println("node " + node);
                   templates.getChildren().remove(node);
                   searchTemplates.getValue().remove(stp);
                   searchTemplates_GUIs.remove(stp);
                   needDeleteList.remove(stp);
               }
            }
        }
    }

    private void needEdit(ListChangeListener.Change<? extends SearchTemplate_POJO> change) throws IOException {
        while (change.next()) {
            if (change.wasAdded()) {
                // Надо редактировать запись с шаблоном
                for (SearchTemplate_POJO stp : change.getAddedSubList()) {
                    GUI_Obj gui_obj = factory_gui.createSearchTemplate_Edit(stp);
                    Stage stage = factory_gui.createModalWindow(gui_obj.node);
                    stage.showAndWait();
                    ((DM_SearchTemplate) searchTemplates_GUIs.get(stp).dm_model).refresh();
                    ((Ctrl_SearchTemplate) searchTemplates_GUIs.get(stp).controll).refresh();
                    needEditList.remove(stp);
                }
            }
        }
    }


    public void addNeedDelete(SearchTemplate_POJO needDelete_Template){
        needDeleteList.add(needDelete_Template);
    }

    public void addNeedEdit(SearchTemplate_POJO needEdit_Template) {
        needEditList.add(needEdit_Template);
    }


    private void showSearchTemplate(SearchTemplate_POJO stp) throws IOException {
        GUI_Obj gui_obj = factory_gui.createSearchTemplate(stp);
        ((Ctrl_SearchTemplate) gui_obj.controll).setCtrl_ill(this);
        // Отобразим эту ноду
        templates.getChildren().add(gui_obj.node);
        // Запомним связку этой ноды с шаблоном
        searchTemplates_GUIs.put(stp,gui_obj);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchTemplates.bindBidirectional(dm_ill.searchTemplates_Property());
        try {
            for (SearchTemplate_POJO stp : searchTemplates.getValue()) {
                showSearchTemplate(stp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        needDeleteList.addListener(new ListChangeListener<SearchTemplate_POJO>() {
            @Override
            public void onChanged(Change<? extends SearchTemplate_POJO> c) {
                needDeleted(c);
            }
        });
        needEditList.addListener(new ListChangeListener<SearchTemplate_POJO>() {
            @Override
            public void onChanged(Change<? extends SearchTemplate_POJO> c) {
                try {
                    needEdit(c);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
