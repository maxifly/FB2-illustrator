package com.maxifly.fb2_illustrator;

/**
 * Created by Maximus on 16.11.2016.
 */
public class TaskInterruptedRuntime extends MyRuntimeException {
    public TaskInterruptedRuntime() {
    }

    public TaskInterruptedRuntime(String message) {
        super(message);
    }

    public TaskInterruptedRuntime(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskInterruptedRuntime(Throwable cause) {
        super(cause);
    }
}
