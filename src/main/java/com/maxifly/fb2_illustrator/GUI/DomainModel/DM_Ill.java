package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.util.ArrayList;

/**
 * Created by Maxim.Pantuhin on 29.08.2016.
 */
public class DM_Ill {
    private SetProperty<SearchTemplate_POJO> searchTemplates =
            new SimpleSetProperty<>();

    private Illustration ill;


    public SetProperty<SearchTemplate_POJO> searchTemplates_Property() {
        return this.searchTemplates;
    }

    public void setIll(Illustration ill) {
        this.ill = ill;
        ObservableSet<SearchTemplate_POJO> stp = FXCollections.observableSet(ill.getSearchTemplates());
        searchTemplates.setValue(stp);

    }

}
