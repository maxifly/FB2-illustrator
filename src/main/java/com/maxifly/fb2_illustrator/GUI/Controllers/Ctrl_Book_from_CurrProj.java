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
}
