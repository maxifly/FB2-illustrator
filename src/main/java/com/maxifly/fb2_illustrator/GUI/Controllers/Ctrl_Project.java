package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.google.gson.Gson;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Ill;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Project;
import com.maxifly.fb2_illustrator.GUI.DomainModel.ILL_IllIco_Nodes;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Obj;
import com.maxifly.fb2_illustrator.GUI.IllChangeOrder;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.model.Illustration;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class Ctrl_Project extends Ctrl_Abstract implements Initializable {
    @FXML
    VBox illustrations;

    @FXML
    BorderPane document_pane;

    private Factory_GUI factory_gui;
    private DM_Project dm_project;

    private ObjectProperty<DM_Ill> selected_ill = new SimpleObjectProperty<>();
//    private ObjectProperty<IllChangeOrder> ill_change_order = new SimpleObjectProperty<>();


    private Map<DM_Ill, ILL_IllIco_Nodes> illNodes = new HashMap<>();
    private Map<Illustration, DM_Ill> ill_model = new HashMap<>();

    private ListProperty<Illustration> illList = new SimpleListProperty<>();


    public Ctrl_Project(Factory_GUI factory_gui, DM_Project dm_project) {
        this.factory_gui = factory_gui;
        this.dm_project = dm_project;
    }


    private void changeSelected_Ill(ObservableValue<? extends DM_Ill> observable, DM_Ill oldValue, DM_Ill newValue) {
        Node node = illNodes.get(newValue).getIll();
        document_pane.setCenter(node);
    }

    private void changeIllLIst(ObservableValue<? extends ObservableList<Illustration>> observable, ObservableList<Illustration> oldValue, ObservableList<Illustration> newValue) {
        ArrayList<Node> changeIll = new ArrayList<>();
        for (Illustration ill:illList) {
            DM_Ill dm_ill = ill_model.get(ill);
            dm_ill.refreshId();
            changeIll.add(illNodes.get(dm_ill).getIllIco());
        }
        illustrations.getChildren().clear();
        illustrations.getChildren().addAll(changeIll);

        System.out.println("old: " + oldValue.size() + " new: " + newValue.size());

    }

    @FXML
    protected void drag_dropped(DragEvent dragEvent) throws MyException {
        if (dragEvent.isDropCompleted() && dragSuitable(dragEvent) ) { //TODO ДОбавить порождение и вставку делимитеров
            // Драг и дроп успешно закончился.
            // Надо перечитать порядок иллюстраций

           String json = getDraggedIllId(dragEvent);
           IllChangeOrder illChangeOrder = new Gson().fromJson(json, IllChangeOrder.class);

           dm_project.moveIll(illChangeOrder.getMoveIllNum(), illChangeOrder.getBeforeIllNum());
           dm_project.refreshIllList();
        }
        dragEvent.consume();
    }

    private String getDraggedIllId(DragEvent dragEvent) {
        String st = dragEvent.getDragboard().getString();
        return st.substring(Constants.drag_string.length());
    }

    private boolean dragSuitable(DragEvent dragEvent) {
        Object gesture = dragEvent.getGestureSource();
        return (gesture instanceof Node)
                && dragEvent.getDragboard().hasString()
                && dragEvent.getDragboard().getString().indexOf(Constants.drag_string) == 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        illList.bindBidirectional(dm_project.illustrations_Property());

        for (Illustration ill : illList) {
            try {
                GUI_Obj gui_obj = factory_gui.createIll(ill);
                GUI_Obj gui_obj_ico = factory_gui.createIllIco((DM_Ill) gui_obj.dm_model);
                GUI_Obj gui_obj_delimiter = factory_gui.createIcoDelimiter();

                illNodes.put((DM_Ill) gui_obj.dm_model, new ILL_IllIco_Nodes(gui_obj.node,gui_obj_ico.node) );
                ill_model.put(ill, (DM_Ill) gui_obj.dm_model);
                selected_ill.bindBidirectional(((Ctrl_IllIco) gui_obj_ico.controll).selected_dm_ill_Property());
//                ill_change_order.bindBidirectional(((Ctrl_IllIco) gui_obj_ico.controll).ill_change_order_Property());

                ((Ctrl_IcoDelimiter) gui_obj_delimiter.controll).setIll_before_id(((DM_Ill) gui_obj.dm_model).ill_id_Property().getValue());
                illustrations.getChildren().add(gui_obj_delimiter.node); //TODO Переписать драг_and_drop на делимитеры
                illustrations.getChildren().add(gui_obj_ico.node);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        selected_ill.addListener(new ChangeListener<DM_Ill>() {
            @Override
            public void changed(ObservableValue<? extends DM_Ill> observable, DM_Ill oldValue, DM_Ill newValue) {
                changeSelected_Ill(observable, oldValue, newValue);
            }
        });

        illList.addListener(new ChangeListener<ObservableList<Illustration>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<Illustration>> observable, ObservableList<Illustration> oldValue, ObservableList<Illustration> newValue) {
                changeIllLIst(observable, oldValue, newValue);
            }
        });



    }
}
