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
import com.maxifly.fb2_illustrator.model.TemplateType;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class Ctrl_Project extends Ctrl_Abstract implements Initializable {
    @FXML
    VBox illustrations;

    @FXML
    BorderPane document_pane;

    @FXML
    Button add_ill_btn;
    @FXML
    Button del_ill_btn;


    private Factory_GUI factory_gui;
    private DM_Project dm_project;
    private Node project_info;

    private ObjectProperty<DM_Ill> selected_ill = new SimpleObjectProperty<>();
    private BooleanProperty changed = new SimpleBooleanProperty();
//    private ObjectProperty<IllChangeOrder> ill_change_order = new SimpleObjectProperty<>();


    private Map<DM_Ill, ILL_IllIco_Nodes> illNodes = new HashMap<>();
    private Map<Illustration, DM_Ill> ill_model = new HashMap<>();

    private ListProperty<Illustration> illList = new SimpleListProperty<>();


    public Ctrl_Project(Factory_GUI factory_gui, DM_Project dm_project) {
        this.factory_gui = factory_gui;
        this.dm_project = dm_project;
    }


    private void changeSelected_Ill(ObservableValue<? extends DM_Ill> observable, DM_Ill oldValue, DM_Ill newValue) {
        if (newValue != null) {
            Node node = illNodes.get(newValue).getIll();
            document_pane.setCenter(node);
            del_ill_btn.setDisable(false);
        } else {
            del_ill_btn.setDisable(true);
        }

    }

    private void changeIllLIst(ObservableValue<? extends ObservableList<Illustration>> observable, ObservableList<Illustration> oldValue, ObservableList<Illustration> newValue) {
        ArrayList<Node> changeIll = new ArrayList<>();
        for (Illustration ill : illList) {
            DM_Ill dm_ill = ill_model.get(ill);
            dm_ill.refreshId();
            changeIll.add(illNodes.get(dm_ill).getIllIco());
        }
        illustrations.getChildren().clear();
        illustrations.getChildren().addAll(changeIll);

//        System.out.println("old: " + oldValue.size() + " new: " + newValue.size());

    }

    @FXML
    protected void add_ill_btn_action(ActionEvent actionEvent) throws IOException {
        Integer newId = dm_project.illustrations_Property().getValue().size();
        Illustration ill = new Illustration(newId, null);
        dm_project.addIll(ill);
        DM_Ill dm_ill = addIllToControl(ill);
        selected_ill.setValue(dm_ill);

    }

    @FXML
    protected void del_ill_btn_action(ActionEvent actionEvent) {
        DM_Ill dm_ill = selected_ill.getValue();
        String sId = dm_ill.ill_id_Property().getValue();
        Integer id = Integer.valueOf(sId);
        dm_project.delIll(id);
        delIllFromControl(dm_ill);
        dm_project.refreshIllList();


        List<Illustration> illList = dm_project.illustrations_Property().getValue();
        if (illList.size() > 0) {
            if (illList.size() > id + 1) {
                dm_ill = ill_model.get(illList.get(id));
                selected_ill.setValue(dm_ill);
            } else {
                dm_ill = ill_model.get(illList.get(illList.size() - 1));
                selected_ill.setValue(dm_ill);
            }
        } else {
            selected_ill.setValue(null);
        }


    }

    @FXML
    protected void project_info_btn(ActionEvent actionEvent) throws IOException {
        if (this.project_info == null) {
            GUI_Obj gui_obj = factory_gui.createProjectInfo(dm_project);
            this.project_info = gui_obj.node;
        }
        selected_ill.setValue(null);
        document_pane.setCenter(project_info);
    }

    @FXML
    protected void drag_dropped(DragEvent dragEvent) throws MyException {
        if (dragEvent.isDropCompleted() && dragSuitable(dragEvent)) { //TODO ДОбавить порождение и вставку делимитеров
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


    private DM_Ill addIllToControl(Illustration ill) throws IOException {
        GUI_Obj gui_obj = factory_gui.createIll(ill);
        GUI_Obj gui_obj_ico = factory_gui.createIllIco((DM_Ill) gui_obj.dm_model);
        illNodes.put((DM_Ill) gui_obj.dm_model, new ILL_IllIco_Nodes(gui_obj.node, gui_obj_ico.node));
        ill_model.put(ill, (DM_Ill) gui_obj.dm_model);
        illustrations.getChildren().add(gui_obj_ico.node);
        selected_ill.bindBidirectional(((Ctrl_IllIco) gui_obj_ico.controll).selected_dm_ill_Property());
        return (DM_Ill) gui_obj.dm_model;
    }

    private void delIllFromControl(DM_Ill dm_ill) {
        Illustration ill = dm_ill.getIll();
        illNodes.remove(dm_ill);
        ill_model.remove(ill);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        illList.bindBidirectional(dm_project.illustrations_Property());
        changed.bindBidirectional(dm_project.getChangeProject_Property());

        for (Illustration ill : illList) {
            try {
                addIllToControl(ill);
//                GUI_Obj gui_obj = factory_gui.createIll(ill);
//                GUI_Obj gui_obj_ico = factory_gui.createIllIco((DM_Ill) gui_obj.dm_model);
////                GUI_Obj gui_obj_delimiter = factory_gui.createIcoDelimiter();
//
//                illNodes.put((DM_Ill) gui_obj.dm_model, new ILL_IllIco_Nodes(gui_obj.node,gui_obj_ico.node) );
//                ill_model.put(ill, (DM_Ill) gui_obj.dm_model);
//                selected_ill.bindBidirectional(((Ctrl_IllIco) gui_obj_ico.controll).selected_dm_ill_Property());
////                ill_change_order.bindBidirectional(((Ctrl_IllIco) gui_obj_ico.controll).ill_change_order_Property());
//
////                ((Ctrl_IcoDelimiter) gui_obj_delimiter.controll).setIll_before_id(((DM_Ill) gui_obj.dm_model).ill_id_Property().getValue());
////                illustrations.getChildren().add(gui_obj_delimiter.node); //TODO Переписать драг_and_drop на делимитеры
//                illustrations.getChildren().add(gui_obj_ico.node);

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
