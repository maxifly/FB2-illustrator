package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.List;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class DM_Project extends DM_Abstract {
    private Project project;
    private ListProperty<Illustration> illustrations = new SimpleListProperty<>();

    public DM_Project(Project project) {
        this.project = project;
        this.illustrations.setValue(FXCollections.observableList(project.getIllustrations()));
    }

    public ListProperty<Illustration> illustrations_Property() {
       return illustrations;
    }



}
