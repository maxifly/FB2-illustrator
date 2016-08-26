package com.maxifly.fb2_illustrator.GUI;

/**
 * Created by Maximus on 27.08.2016.
 */
public class CheckResult {
    public boolean result;
    public String message;

    public CheckResult(boolean result) {
        this.result = result;
    }

    public CheckResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
