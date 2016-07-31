package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_CertainAlbum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 31.07.2016.
 */
public class Ctrl_CertainAlbum implements Initializable{
    final private DM_CertainAlbum dm_certainAlbum;

    @FXML private TextField book_name;
    @FXML private TextField album_addr;
    @FXML private TextField book_src_file;
    @FXML private TextField book_dst_file;
    @FXML private CheckBox book_check;



    @FXML protected void clear_form(ActionEvent actionEvent ) {
        System.out.println("clear");
        book_name.setText(null);
        album_addr.setText(null);
        book_src_file.setText(null);
        book_dst_file.setText(null);
        book_check.setSelected(false);

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
    }
}
