package com.maxifly.fb2_illustrator.GUI.Controllers;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Book_from_Proj;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_CertainAlbum;
import com.maxifly.fb2_illustrator.model.Project;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
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
public class Ctrl_Book_from_CurrProj
        extends Ctrl_Book_from_Proj
        implements Initializable {

    public Ctrl_Book_from_CurrProj(DM_Book_from_Proj dm_book_from_proj) {
        super(dm_book_from_proj);
    }

    private boolean disableLoad() {
        return (book_src_file.getText() == null ||
                "".equals(book_src_file.getText().trim()) ||
                projectObjectProperty.getValue() == null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        Ctrl_Book_from_Proj s = this;
        BooleanBinding disableLoad =
                new BooleanBinding() {

                    {
                        super.bind(
                                s.book_src_file.textProperty(),
                                s.book_dst_file.textProperty(),
                                s.projectObjectProperty);
                    }

                    @Override
                    protected boolean computeValue() {
                        return disableLoad();
                    }
                };
        super.setDisableLoad(disableLoad);
    }
}
