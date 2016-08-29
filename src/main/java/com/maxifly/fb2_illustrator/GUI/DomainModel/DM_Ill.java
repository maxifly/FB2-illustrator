package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Maxim.Pantuhin on 29.08.2016.
 */
public class DM_Ill {
    private ListProperty<SearchTemplate_POJO> searchTemplates =
            new SimpleListProperty<>();

    private Illustration ill;


    public ListProperty<SearchTemplate_POJO> searchTemplates_Property() {
        return this.searchTemplates;
    }

    public void setIll(Illustration ill) {
        this.ill = ill;
        ArrayList<SearchTemplate_POJO> al = new ArrayList<>(ill.getSearchTemplates());
        ObservableList<SearchTemplate_POJO> stp = FXCollections.observableList(al);
        searchTemplates.setValue(stp);

    }

}
