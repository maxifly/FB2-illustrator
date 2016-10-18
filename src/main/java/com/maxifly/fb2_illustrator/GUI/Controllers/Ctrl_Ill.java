package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Ill;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_SearchTemplate;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Obj;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.fb2_illustrator.model.TemplateType;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by Maxim.Pantuhin on 29.08.2016.
 */
public class Ctrl_Ill extends Ctrl_WithTemplates implements Initializable {
    @FXML
    VBox templates;

    @FXML
    GridPane gridpane;

    @FXML
    ImageView picture;
    @FXML
    TextField picture_string;

    @FXML
    TextArea default_desc;

    @FXML
    Label ill_number;



    @FXML
    protected void kuku(ActionEvent actionEvent) {
        System.out.println("W " + gridpane.widthProperty().getValue());
    }

    private ObjectProperty<Path> picture_path = new SimpleObjectProperty<>();


    private Map<SearchTemplate_POJO, GUI_Obj> searchTemplates_GUIs = new HashMap<>();

    private String defaultPicture = Factory_GUI.class.getResource("no_image.png").toString();



    private DM_Ill dm_ill;
//    private Factory_GUI factory_gui;

//    private ObservableList<SearchTemplate_POJO> needDeleteList = FXCollections.observableList(new ArrayList<>());
//    private ObservableList<SearchTemplate_POJO> needEditList = FXCollections.observableList(new ArrayList<>());


    public Ctrl_Ill(Factory_GUI factory_gui, DM_Ill dm_ill) {
        super(factory_gui);
        this.dm_ill = dm_ill;
    }

    @FXML
    private void btn_add(ActionEvent actionEvent) throws IOException {
        SearchTemplate_POJO stp = super.addTemplate();
         if (stp.getTemplate() != null) {
            dm_ill.addSearchTemplate(stp);
        }
    }

    @FXML
    protected void button_picture_action(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser, "Выбрать рисунок ");
        Window win = ((Node) actionEvent.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(win);

        if (file == null) {
            picture_string.setText(null);
        } else {
            picture_string.setText(file.getAbsolutePath());
        }
    }


//    private void needDeleted(ListChangeListener.Change<? extends SearchTemplate_POJO> change){
//        while (change.next()) {
//            if (change.wasAdded()) {
//                // Надо удалить запись с шаблоном
//               for (SearchTemplate_POJO stp : change.getAddedSubList()) {
//                   System.out.println("delete stp " + stp);
//                   Node node = searchTemplates_GUIs.get(stp).node;
//                   System.out.println("node " + node);
//                   templates.getChildren().remove(node);
//                   searchTemplates.getValue().remove(stp);
//                   searchTemplates_GUIs.remove(stp);
//                   needDeleteList.remove(stp);
//               }
//            }
//        }
//    }

//    private void needEdit(ListChangeListener.Change<? extends SearchTemplate_POJO> change) throws IOException {
//        while (change.next()) {
//            if (change.wasAdded()) {
//                // Надо редактировать запись с шаблоном
//                for (SearchTemplate_POJO stp : change.getAddedSubList()) {
//                    GUI_Obj gui_obj = factory_gui.createSearchTemplate_Edit(stp);
//                    Stage stage = factory_gui.createModalWindow(gui_obj.node);
//                    stage.showAndWait();
//                    ((DM_SearchTemplate) searchTemplates_GUIs.get(stp).dm_model).refresh();
//                    ((Ctrl_SearchTemplate) searchTemplates_GUIs.get(stp).controll).refresh();
//                    needEditList.remove(stp);
//                }
//            }
//        }
//    }

    private void picture_string_change(ObservableValue<? extends String> observable, String oldValue, String newValue){
        Path newPath = FileSystems.getDefault().getPath(newValue);
        dm_ill.setFile(newPath);
        showImage(newPath);
    }


//
//    public void addNeedDelete(SearchTemplate_POJO needDelete_Template){
//        needDeleteList.add(needDelete_Template);
//    }
//
//    public void addNeedEdit(SearchTemplate_POJO needEdit_Template) {
//        needEditList.add(needEdit_Template);
//    }
//

//    private void showSearchTemplate(SearchTemplate_POJO stp) throws IOException {
//        GUI_Obj gui_obj = factory_gui.createSearchTemplate(stp);
//        ((Ctrl_SearchTemplate) gui_obj.controll).setCtrl_ill(this);
//        // Отобразим эту ноду
//        templates.getChildren().add(gui_obj.node);
//        // Запомним связку этой ноды с шаблоном
//        searchTemplates_GUIs.put(stp,gui_obj);
//    }

    private void showImage(Path file_path) {
        Image image = null;
         if ( file_path!= null && (file_path.toFile().exists())) {
            image = new Image(file_path.toFile().toURI().toString());
        } else {
            image = new Image(defaultPicture);
        }
        picture.setImage(image);
    }


    @Override
    protected GUI_Obj createSearchTemplate(SearchTemplate_POJO stp) throws IOException {
        return factory_gui.createSearchTemplate(stp);
    }

    @Override
    protected GUI_Obj createSearchTemplate_Edit(SearchTemplate_POJO stp) throws IOException {
        return factory_gui.createSearchTemplate_Edit(stp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        searchTemplates.bindBidirectional(dm_ill.searchTemplates_Property());
        picture_path.bindBidirectional(dm_ill.picture_path_Property());

        ill_number.textProperty().bindBidirectional(dm_ill.ill_id_Property());
        default_desc.textProperty().bindBidirectional(dm_ill.ill_default_desc_Property());

        showImage(picture_path.getValue());

        if (picture_path.getValue() != null) {
            picture_string.setText(picture_path.getValue().toString());
        }


        showSearchTemplates();

//        needDeleteList.addListener(new ListChangeListener<SearchTemplate_POJO>() {
//            @Override
//            public void onChanged(Change<? extends SearchTemplate_POJO> c) {
//                needDeleted(c);
//            }
//        });
//        needEditList.addListener(new ListChangeListener<SearchTemplate_POJO>() {
//            @Override
//            public void onChanged(Change<? extends SearchTemplate_POJO> c) {
//                try {
//                    needEdit(c);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        picture_string.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                picture_string_change(observable, oldValue, newValue);
            }
        });


    }

    private static void configureFileChooser(
            final FileChooser fileChooser,
            String title) {
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
    }

}
