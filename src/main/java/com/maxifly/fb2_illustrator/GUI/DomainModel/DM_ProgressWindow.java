package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.Controllers.Ctrl_ImportVKProject;
import com.maxifly.jutils.I_Progress;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

/**
 * Created by Maximus on 12.11.2016.
 */
public class DM_ProgressWindow extends DM_Abstract
        implements I_Progress

{
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(DM_ProgressWindow.class.getName());

    private long max = 0;
    private long done = 0;

    private DoubleProperty progress = new SimpleDoubleProperty(0);
    private StringProperty progress_message = new SimpleStringProperty();

    private Task task;

    public double getProgress() {
        return progress.get();
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress.set(progress);
    }

    public String getProgress_message() {
        return progress_message.get();
    }

    public StringProperty progress_messageProperty() {
        return progress_message;
    }

    public void setProgress_message(String progress_message) {
        this.progress_message.set(progress_message);
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void cancel() {
        task.cancel();
    }

    @Override
    public void setMaxValue(long max) {
        this.max = max;
        setProgress(((double) done) / ((double) max));
    }

    @Override
    public void incrementDone(long increment) {
        done = done + increment;
        setProgress(((double) done) / ((double) max));
    }

    @Override
    public void incrementDone(long increment, String message) {
        incrementDone(increment);
        setProgress_message(message);
    }

    @Override
    public void updateProgress(long workDone, long max) {
        this.max = max;
        this.done = workDone;
        incrementDone(0);
    }

    @Override
    public void updateProgress(long workDone, long max, String message) {
        log.debug("updateProgress {}, {}, {}", workDone, max, message);
        updateProgress(workDone, max);
        setProgress_message(message);
    }
}
