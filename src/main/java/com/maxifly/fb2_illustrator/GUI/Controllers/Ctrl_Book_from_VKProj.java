package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Book_from_Proj;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Book_from_VKProj;
import com.maxifly.fb2_illustrator.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 31.07.2016.
 */
public class Ctrl_Book_from_VKProj
        extends Ctrl_Book_from_Proj
        implements Initializable {

//    private ObservableList srcTypes = FXCollections.observableArrayList("группа","пользователь","альбом");

    private DM_Book_from_VKProj dm_book_from_vkProj;

    @FXML
    private ChoiceBox<String> vk_src_type;
    @FXML
    private TextField src_addr;
    @FXML
    private ListView<String> projects;

    @FXML
    protected void refresh(ActionEvent actionEvent) throws MyException {
        dm_book_from_vkProj.refresh(vk_src_type.getValue(), src_addr.getText());

    }


    public Ctrl_Book_from_VKProj(DM_Book_from_VKProj dm_book_from_proj) {
        super(dm_book_from_proj);
        this.dm_book_from_vkProj= dm_book_from_proj;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        vk_src_type.getItems().addAll("группа","пользователь","альбом");
        vk_src_type.setValue("группа");

        projects.setItems(dm_book_from_vkProj.getSuitableProjects());
//        projects.get

    }
}
