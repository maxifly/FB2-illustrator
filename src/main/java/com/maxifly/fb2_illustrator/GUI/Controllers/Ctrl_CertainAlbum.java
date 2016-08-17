package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_CertainAlbum;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 31.07.2016.
 */
public class Ctrl_CertainAlbum implements Initializable {
    final private DM_CertainAlbum dm_certainAlbum;

    @FXML
    private TextField book_name;
    @FXML
    private TextField album_addr;
    @FXML
    private TextField book_src_file;
    @FXML
    private TextField book_dst_file;
    @FXML
    private CheckBox book_check;

    @FXML
    private Button btnLoad;

    private BooleanBinding enable_Load;


    @FXML
    protected void clear_form(ActionEvent actionEvent) {
        System.out.println("clear");
        book_name.setText(null);
        album_addr.setText(null);
        book_src_file.setText(null);
        book_dst_file.setText(null);
        book_check.setSelected(false);

    }

    @FXML
    protected void load_ill(ActionEvent actionEvent) throws Exception {
        // Проверяем, что нет файла-приемника

        File dstFile = new File(book_dst_file.getText());
        boolean isContinue = true;

        if (dstFile.exists() && dstFile.isFile()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Файл \n" + book_dst_file.getText()
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
            dm_certainAlbum.load_ill();
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

    public Ctrl_CertainAlbum(DM_CertainAlbum dm_certainAlbum) {
        this.dm_certainAlbum = dm_certainAlbum;


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        book_name.textProperty().bindBidirectional(dm_certainAlbum.book_name_Propery());
        album_addr.textProperty().bindBidirectional(dm_certainAlbum.album_addr_Propery());
        book_src_file.textProperty().bindBidirectional(dm_certainAlbum.book_src_file_Propery());
        book_dst_file.textProperty().bindBidirectional(dm_certainAlbum.book_dst_file_Propery());


        book_check.selectedProperty().bindBidirectional(dm_certainAlbum.book_check_Propery());

        enable_Load =
                new BooleanBinding() {
                    {
                        super.bind(album_addr.textProperty(),
                                book_src_file.textProperty(),
                                book_dst_file.textProperty());
                    }

                    @Override
                    protected boolean computeValue() {

                        if (album_addr.textProperty().get() == null ||
                                book_src_file.textProperty().get() == null ||
                                book_dst_file.textProperty().get() == null ||
                                "".equals(album_addr.textProperty().get().trim()) ||
                                "".equals(book_src_file.textProperty().get()) ||
                                "".equals(book_dst_file.textProperty().get())


                                ) {

                            return true;
                        } else {
                            return false;
                        }
                    }
                };
        btnLoad.disableProperty().bind(enable_Load);
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
                new FileChooser.ExtensionFilter("FB2", "*.fb2")
        );
    }

}
