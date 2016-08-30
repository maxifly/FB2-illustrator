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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 26.08.2016.
 */
public class Ctrl_SearchTemplate_Edit
        extends Ctrl_Abstract
        implements Initializable {

    private DM_SearchTemplate dm_searchTemplate;

    @FXML
    TextField template;
    @FXML
    TextField description;

    @FXML
    RadioButton type_text;
    @FXML
    RadioButton type_reg;
    @FXML
    Button btn_save;
    @FXML
    Button btn_cancel;
    @FXML
    Node main_node;


    ObjectProperty<TemplateType> templateTypeObjectProperty = new SimpleObjectProperty<>();



    public Ctrl_SearchTemplate_Edit(DM_SearchTemplate dm_searchTemplate) {
        this.dm_searchTemplate = dm_searchTemplate;
    }

    @FXML
    protected void radio_btn(ActionEvent actionEvent) {
        if (type_text.isSelected()) {
            templateTypeObjectProperty.setValue(TemplateType.substr);
        } else if (type_reg.isSelected()) {
            templateTypeObjectProperty.setValue(TemplateType.regexp);
        }
    }

    @FXML
    protected void cancel(ActionEvent actionEvent) {
        dm_searchTemplate.cancel();
        if (templateTypeObjectProperty.get().equals(TemplateType.substr)) {
            type_text.setSelected(true);
        } else {
            type_reg.setSelected(true);
        }
    }

    @FXML
    protected void save(ActionEvent actionEvent) {
        CheckResult checkResult = dm_searchTemplate.check();

        if (checkResult.result) {
            dm_searchTemplate.save();
        } else {
//            Window win = ((Node) actionEvent.getSource()).getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exception");
            alert.setHeaderText(null);
            if (checkResult.message == null) {
                alert.setContentText("Поля заполнены некорректно");
            } else {
                alert.setContentText("Поля заполнены некорректно \n" + checkResult.message);
            }

            alert.showAndWait();

        }

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        template.textProperty().bindBidirectional(dm_searchTemplate.template_Propery());
        description.textProperty().bindBidirectional(dm_searchTemplate.description_Propery());
        templateTypeObjectProperty.bindBidirectional(dm_searchTemplate.templateType_Propery());

        dm_searchTemplate.cancel();

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
