package com.maxifly.fb2_illustrator.GUI.DomainModel;

/**
 * Created by Maximus on 06.11.2016.
 */
public class EmptyProgress implements DM_I_Progress {


    @Override
    public void setMaxValue(long max) {

    }

    @Override
    public void incrementDone(long increment) {

    }

    @Override
    public void incrementDone(long increment, String message) {

    }

    @Override
    public void updateProgress(long workDone, long max) {

    }

    @Override
    public void updateProgress(long workDone, long max, String message) {

    }
}