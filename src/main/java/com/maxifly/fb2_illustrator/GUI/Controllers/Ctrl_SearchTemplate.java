package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.CheckResult;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_SearchTemplate;
import com.maxifly.fb2_illustrator.model.TemplateType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 26.08.2016.
 */
public class Ctrl_SearchTemplate
        extends Ctrl_Abstract
        implements Initializable {

    private DM_SearchTemplate dm_searchTemplate;
    private Ctrl_Ill ctrl_ill;


    @FXML
    TextField template;
    @FXML
    TextField description;

    @FXML
    RadioButton type_text;
    @FXML
    RadioButton type_reg;

    @FXML
    Node main_node;

    @FXML
    VBox buttons;


    @FXML
    private void buttons(ActionEvent actionEvent) {

//      switch (((Button) actionEvent.getSource()).getId()) {
//          case
//      }

        ctrl_ill.addNeedDelete(
                dm_searchTemplate.getSearchTemplate()
        );

    }


    ObjectProperty<TemplateType> templateTypeObjectProperty = new SimpleObjectProperty<>();


    public Ctrl_SearchTemplate(DM_SearchTemplate dm_searchTemplate) {
        this.dm_searchTemplate = dm_searchTemplate;
    }

    public void setCtrl_ill(Ctrl_Ill ctrl_ill) {
        this.ctrl_ill = ctrl_ill;
    }

    @FXML
    public void gp_mouse(MouseEvent mouseEvent) {
        switch (mouseEvent.getEventType().getName()) {
            case "MOUSE_ENTERED":
                buttons.setVisible(true);
                break;
            case "MOUSE_EXITED":
                buttons.setVisible(false);
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        template.textProperty().bindBidirectional(dm_searchTemplate.template_Propery());
        description.textProperty().bindBidirectional(dm_searchTemplate.description_Propery());
        templateTypeObjectProperty.bindBidirectional(dm_searchTemplate.templateType_Propery());

        dm_searchTemplate.refresh();

        if (templateTypeObjectProperty.get().equals(TemplateType.substr)) {
            type_text.setSelected(true);
        } else {
            type_reg.setSelected(true);
        }


        main_node.focusedProperty().addListener(
                new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            System.out.println("Focused");
                        } else {
                            System.out.println("Focus lost");
                        }
                    }
                }


        );

    }
}
