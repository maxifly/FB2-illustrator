package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_SearchTemplate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 26.08.2016.
 */
public class Ctrl_SearchTemplate implements Initializable {

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
    Button btn_check;
    @FXML
    Button btn_cancel;






    public Ctrl_SearchTemplate(DM_SearchTemplate dm_searchTemplate) {
        this.dm_searchTemplate = dm_searchTemplate;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
