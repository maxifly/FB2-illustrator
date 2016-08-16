package com.maxifly.fb2_illustrator.GUI;

import com.maxifly.fb2_illustrator.MyException;

/**
 * Created by Maximus on 16.08.2016.
 */
public class GUI_Exception extends MyException {
    public GUI_Exception() {
    }

    public GUI_Exception(String message) {
        super(message);
    }

    public GUI_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public GUI_Exception(Throwable cause) {
        super(cause);
    }
}
