package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Book_from_Proj;
import com.maxifly.fb2_illustrator.model.Project;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 31.07.2016.
 */
public abstract class Ctrl_Book_from_Proj
        extends Ctrl_Abstract
        implements Initializable {
    final private DM_Book_from_Proj dm_book_from_proj;

    @FXML
    private TextField book_name;
    @FXML
    protected TextField book_src_file;
    @FXML
    protected TextField book_dst_file;

    @FXML
    private Button btnLoad;

    @FXML
    private TextArea warnings;


    private StringProperty book_dst = new SimpleStringProperty();
    protected ObjectProperty<Project> projectObjectProperty = new SimpleObjectProperty<>();

    private BooleanBinding disableLoad;


    public void setDisableLoad(BooleanBinding disableLoad) {
        if (this.disableLoad != null) btnLoad.disableProperty().unbind();
        this.disableLoad = disableLoad;
        btnLoad.disableProperty().bind(disableLoad);
    }





    @FXML
    protected void clear_form(ActionEvent actionEvent) {
        System.out.println("clear");
        book_name.setText(null);
        book_src_file.setText(null);
        book_dst_file.setText(null);

    }

    @FXML
    protected void load_ill(ActionEvent actionEvent) throws Exception {
        // Проверяем, что нет файла-приемника

        File dstFile = new File(book_dst.getValue());
        boolean isContinue = true;

        if (dstFile.exists() && dstFile.isFile()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Файл \n" + book_dst.getValue()
                            + "\n существует. Перезаписать?",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            Optional<ButtonType> alert_button = alert.showAndWait();

            if (ButtonType.YES == alert_button.get()) {
                isContinue = true;
            } else {
                isContinue = false;
            }

        }

        if (isContinue) {
            dm_book_from_proj.load_ill();
            Alert info = new Alert(Alert.AlertType.INFORMATION, "Процесс окончен.");
            info.setHeaderText(null);
            info.showAndWait();
        }

    }

    @FXML
    protected void button_src_file_action(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser, "Выбрать исходный файл ");
        Window win = ((Node) actionEvent.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(win);

        if (file == null) {
            book_src_file.setText(null);
        } else {
            book_src_file.setText(file.getAbsolutePath());
        }
    }


    @FXML
    protected void btnFromFile_action(ActionEvent actionEvent) {
        dm_book_from_proj.setBookNameFromFile();
    }

    @FXML
    protected void button_dst_file_action(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser, "Выбрать результирующий файл ");
        Window win = ((Node) actionEvent.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(win);

        if (file == null) {
            book_dst_file.setText(null);
        } else {
            book_dst_file.setText(file.getAbsolutePath());
        }

    }



    private void srcFileChange(String newValue) {
        if (newValue == null) {
            book_dst_file.setPromptText(null);
        } else {

            int position = newValue.lastIndexOf('.');
            if (position == -1) {
                book_dst_file.setPromptText(newValue + "_ill");
            } else {
                book_dst_file.setPromptText(newValue.substring(0, position) + "_ill" + newValue.substring(position));
            }
        }

        if (book_dst_file.getText() == null || "".equals(book_dst_file.getText().trim())) {
            book_dst.setValue(book_dst_file.getPromptText());
        } else {
            book_dst.setValue(book_dst_file.getText());
        }
    }

    private void change_warnings(String newValue) {
        warnings.setVisible(newValue != null && !"".equals(newValue));
    }

    public Ctrl_Book_from_Proj(DM_Book_from_Proj dm_book_from_proj) {
        this.dm_book_from_proj = dm_book_from_proj;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        book_name.textProperty().bindBidirectional(dm_book_from_proj.book_name_Propery());
        book_src_file.textProperty().bindBidirectional(dm_book_from_proj.book_src_file_Propery());
        book_dst.bindBidirectional(dm_book_from_proj.book_dst_file_Propery());
        projectObjectProperty.bindBidirectional(dm_book_from_proj.projectObjectPropertyProperty());
        warnings.textProperty().bindBidirectional(dm_book_from_proj.warningsProperty());

        book_src_file.textProperty().addListener((observable, oldValue, newValue) -> srcFileChange(newValue));
        warnings.textProperty().addListener((observable, oldValue, newValue) -> change_warnings(newValue));

    }

    private static void configureFileChooser(
            final FileChooser fileChooser,
            String title) {
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("FB2", "*.fb2")
        );
    }

}
