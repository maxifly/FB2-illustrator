package com.maxifly.fb2_illustrator;

/**
 * Created by Maximus on 16.11.2016.
 */
public class TaskInterrupted extends MyException {
    public TaskInterrupted(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskInterrupted(Throwable cause) {
        super(cause);
    }
}
