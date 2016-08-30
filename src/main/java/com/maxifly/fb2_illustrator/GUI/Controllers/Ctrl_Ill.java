package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Ill;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_SearchTemplate;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Obj;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
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
    private Map<SearchTemplate_POJO, Node> searchTemplatesNodes = new HashMap<>();
    private Map<SearchTemplate_POJO, DM_SearchTemplate> searchTemplateModels = new HashMap<>();



    private DM_Ill dm_ill;
    private Factory_GUI factory_gui;

    private ObservableList<SearchTemplate_POJO> needDeleteList = FXCollections.observableList(new ArrayList<>());
    private ObservableList<SearchTemplate_POJO> needEditList = FXCollections.observableList(new ArrayList<>());


    public Ctrl_Ill(Factory_GUI factory_gui, DM_Ill dm_ill) {
        this.dm_ill = dm_ill;
        this.factory_gui = factory_gui;
    }


    private void needDeleted(ListChangeListener.Change<? extends SearchTemplate_POJO> change){
        while (change.next()) {
            if (change.wasAdded()) {
                // Надо удалить запись с шаблоном
               for (SearchTemplate_POJO stp : change.getAddedSubList()) {
                   System.out.println("delete stp " + stp);
                   Node node = searchTemplatesNodes.get(stp);
                   System.out.println("node " + node);
                   templates.getChildren().remove(node);
                   searchTemplates.getValue().remove(stp);
                   searchTemplatesNodes.remove(stp);
                   searchTemplateModels.remove(stp);
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
                    System.out.println("edit stp " + stp);
                    GUI_Obj gui_obj = factory_gui.createSearchTemplate_Edit(stp);
//                    Dialog<Boolean> dialog = new Dialog();
//                    dialog.setTitle("Edit Dialog");
//                    dialog.getDialogPane().setContent(gui_obj.node);
//                    //dialog.getDialogPane().getButtonTypes().addAll( ButtonType.CANCEL);
//                    dialog.showAndWait();
//
//                    System.out.println("kuku");


                    System.out.println("kuku");
                    Stage stage = factory_gui.createModalWindow(gui_obj.node);
                    stage.showAndWait();
                    System.out.println("after");
//TODO надо через диалог

                    searchTemplateModels.get(stp).refresh();
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchTemplates.bindBidirectional(dm_ill.searchTemplates_Property());
        try {
            for (SearchTemplate_POJO stp : searchTemplates.getValue()) {
                GUI_Obj gui_obj = factory_gui.createSearchTemplate(stp);
                ((Ctrl_SearchTemplate) gui_obj.controll).setCtrl_ill(this);
                // Отобразим эту ноду
                templates.getChildren().add(gui_obj.node);
                // Запомним связку этой ноды с шаблоном
                searchTemplatesNodes.put(stp,gui_obj.node);
                searchTemplateModels.put(stp,((DM_SearchTemplate) gui_obj.dm_model));

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
