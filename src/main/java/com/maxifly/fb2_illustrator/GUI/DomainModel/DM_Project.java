package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

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

    public DM_Project(Project project) {
        this.project = project;
        this.illustrations.setValue(FXCollections.observableList(project.getIllustrations()));
    }

    public ListProperty<Illustration> illustrations_Property() {
       return illustrations;
    }

    public void refreshIllList() {
        log.debug("Refresh illustrations list.");
        this.illustrations.setValue(FXCollections.observableList(project.getIllustrations()));
    }

    public void moveIll(Integer movedIllId, Integer beforeIllId) throws MyException {
        log.debug("Move ill " + movedIllId + " set it before ill " + beforeIllId);
        project.moveIll(movedIllId,beforeIllId);
    }


}