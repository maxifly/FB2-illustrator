package com.maxifly.fb2_illustrator;

/**
 * Created by Maxim.Pantuhin on 16.08.2016.
 */
public class MyException extends Exception {
    public MyException() {
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(Throwable cause) {
        super(cause);
    }
}
