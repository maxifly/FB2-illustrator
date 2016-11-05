package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Book_from_Proj;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Book_from_VKProj;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.vapi.model.OwnerAlbumProject;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    private BooleanProperty isPrjSelected = new SimpleBooleanProperty();
    private ObjectProperty<OwnerAlbumProject>  selectedProject = new SimpleObjectProperty();

    @FXML
    private ChoiceBox<String> vk_src_type;
    @FXML
    private TextField src_addr;
    @FXML
    private ListView<OwnerAlbumProject> projects;

    @FXML
    protected void refresh(ActionEvent actionEvent) throws MyException {
        clearProjects();
        dm_book_from_vkProj.refresh(vk_src_type.getValue(), src_addr.getText());

    }

    private void changeSelectedPrj(OwnerAlbumProject newValue) {
        isPrjSelected.setValue((newValue != null));
        selectedProject.setValue(newValue);
    }

    private void clearProjects() {
        projects.getItems().remove(0,projects.getItems().size());
        isPrjSelected.setValue(false);
    }

    private boolean disableLoad() {
        return (book_src_file.getText() == null ||
                "".equals(book_src_file.getText().trim()) ||
                !isPrjSelected.getValue());
    }

    public Ctrl_Book_from_VKProj(DM_Book_from_VKProj dm_book_from_proj) {
        super(dm_book_from_proj);
        this.dm_book_from_vkProj= dm_book_from_proj;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        selectedProject.bindBidirectional(dm_book_from_vkProj.selectedProjectProperty());
        vk_src_type.getItems().addAll("группа","пользователь","альбом");
        vk_src_type.setValue("группа");

        projects.setItems(dm_book_from_vkProj.getSuitableProjects());
        projects.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> changeSelectedPrj(newValue)
        );
        this.book_name.textProperty().addListener(
                (observable, oldValue, newValue) -> clearProjects()
        );

        Ctrl_Book_from_VKProj s = this;

        BooleanBinding disableLoad =
                new BooleanBinding() {

                    {
                        super.bind(
                                s.book_src_file.textProperty(),
                                s.book_dst_file.textProperty(),
                                s.isPrjSelected);
                    }

                    @Override
                    protected boolean computeValue() {
                        return disableLoad();
                    }
                };

        s.setDisableLoad(disableLoad);

    }


}
