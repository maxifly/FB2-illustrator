package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by Maxim.Pantuhin on 29.08.2016.
 */
public class DM_Ill extends DM_Abstract{
    private SetProperty<SearchTemplate_POJO> searchTemplates =
            new SimpleSetProperty<>();

    private ObjectProperty<Path> picture_path = new SimpleObjectProperty<>();
    private Illustration ill;

    StringProperty ill_id = new SimpleStringProperty();
    StringProperty ill_default_desc = new SimpleStringProperty();



    public SetProperty<SearchTemplate_POJO> searchTemplates_Property() {
        return this.searchTemplates;
    }
    public StringProperty ill_id_Property() {
        return this.ill_id;
    }
    public StringProperty ill_default_desc_Property() {
        return this.ill_default_desc;
    }

    public ObjectProperty<Path> picture_path_Property() {
        return this.picture_path;
    }

    public void setIll(Illustration ill) {
        this.ill = ill;
        ObservableSet<SearchTemplate_POJO> stp = FXCollections.observableSet(ill.getSearchTemplates());
        searchTemplates.setValue(stp);
        picture_path.setValue(ill.getFile());
        ill_id.setValue(ill.getId());
        ill_default_desc.setValue(ill.getDescription());
    }

    public void addSearchTemplate(SearchTemplate_POJO searchTemplate_pojo) {
        ill.addSearchTempale(searchTemplate_pojo);
    }

    public void setFile(Path file) {
        ill.setFile(file);
        picture_path.setValue(file);
    }

//    public void moveIll(String movedIllId) throws MyException {
//        ill.getProject().moveIll(movedIllId,ill_id.getValue());
//    }
    public void refreshId(){
        ill_id.setValue(ill.getId());
    }


}
