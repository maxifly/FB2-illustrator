package com.maxifly.fb2_illustrator.GUI.DomainModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Maximus on 12.11.2016.
 */
public class DM_ProgressWindow extends  DM_Abstract
implements DM_I_Progress

{

    private long max = 0;
    private long done = 0;

    private DoubleProperty progress = new SimpleDoubleProperty(0);
    private StringProperty progress_message = new SimpleStringProperty();

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

    @Override
    public void setMaxValue(long max) {
       this.max = max;
       setProgress(done/max);
    }

    @Override
    public void incrementDone(long increment) {
        done = done + increment;
        setProgress(done/max);
    }

    @Override
    public void incrementDone(long increment, String message) {
        incrementDone(increment);
        setProgress_message(message);
    }

    @Override
    public void updateProgress(long workDone, long max) {
        this.max = max;
        this.done = done;
        incrementDone(0);
    }

    @Override
    public void updateProgress(long workDone, long max, String message) {
        updateProgress(workDone, max);
        setProgress_message(message);
    }
}
