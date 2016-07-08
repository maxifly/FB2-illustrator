package com.kuku.vapi.model;

/**
 * Created by Maximus on 24.05.2016.
 */
public class RestResponse {
    private int responseCode;
    private StringBuffer responseBody;


    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public StringBuffer getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(StringBuffer responseBody) {
        this.responseBody = responseBody;
    }
}
