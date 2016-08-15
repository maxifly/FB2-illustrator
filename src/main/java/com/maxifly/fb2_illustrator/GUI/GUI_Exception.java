package com.maxifly.fb2_illustrator.GUI;

/**
 * Created by Maximus on 16.08.2016.
 */
public class GUI_Exception extends Exception {
    public GUI_Exception() {
    }

    public GUI_Exception(String message) {
        super(message);
    }

    public GUI_Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
