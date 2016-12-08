package com.maxifly.vapi;

import com.maxifly.fb2_illustrator.MyException;

/**
 * Created by Maximus on 08.12.2016.
 */
public class AddrNoParseException extends MyException {
    public AddrNoParseException(String message) {
        super(message);
    }

    public AddrNoParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
