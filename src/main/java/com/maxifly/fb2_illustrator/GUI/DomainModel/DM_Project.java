package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class DM_Project extends DM_Abstract {
    public static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    public static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(DM_Project.class.getName());

    private Project project;
    private ListProperty<Illustration> illustrations = new SimpleListProperty<>();
    private BooleanProperty changeProjectProperty = new SimpleBooleanProperty();
    private SetProperty<SearchTemplate_POJO> searchTemplates =
            new SimpleSetProperty<>();
    private StringProperty projectParagrafProperty = new SimpleStringProperty();


    public void addIll(Illustration ill) {
        project.addIll(ill);
    }

    public DM_Project(Project project) {
        this.project = project;
        this.illustrations.setValue(FXCollections.observableList(project.getIllustrations()));
        this.changeProjectProperty.bindBidirectional(project.changeProject_Property());
        this.getChangeProject_Property().setValue(false);
        ObservableSet<SearchTemplate_POJO> stp = FXCollections.observableSet(this.project.getBookNameTemplates());
        searchTemplates.setValue(stp);
        this.projectParagrafProperty.setValue(project.getProjectParagraf());

        projectParagrafProperty.addListener((observable, oldValue, newValue) -> {
            project.setProjectParagraf(newValue);
        });


    }

    public ListProperty<Illustration> illustrations_Property() {
        return illustrations;
    }

    public BooleanProperty getChangeProject_Property() {
        return this.changeProjectProperty;
    }

    public StringProperty projectParagraf_Property() {
        return projectParagrafProperty;
    }

    public void refreshIllList() {
        log.debug("Refresh illustrations list.");
        this.illustrations.setValue(FXCollections.observableList(project.getIllustrations()));
    }

    public void moveIll(Integer movedIllId, Integer beforeIllId) throws MyException {
        log.debug("Move ill " + movedIllId + " set it before ill " + beforeIllId);
        project.moveIll(movedIllId, beforeIllId);
        changeProjectProperty.setValue(true);
    }

    public void delIll(Integer delIllId) {
        log.debug("Delete illustration with id " + delIllId);
        project.delIll(delIllId);
        changeProjectProperty.setValue(true);
    }

    public ObjectProperty<File> projectFile_Property() {
        return project.projectFile_Property();
    }

    public void addBookNameTempale(SearchTemplate_POJO searchTemplate_pojo) {
        project.addBookNameTempale(searchTemplate_pojo);
    }

    public SetProperty<SearchTemplate_POJO> searchTemplates_Property() {
        return this.searchTemplates;
    }

    public String getId() {
        return project.getId();
    }

    public Project getProject() {
        return project;
    }
}
