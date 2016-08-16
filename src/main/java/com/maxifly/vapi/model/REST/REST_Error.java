package com.maxifly.vapi.model.REST;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maximus on 19.06.2016.
 */
public class REST_Error {
    public int error_code;
    public String error_msg;
    public List<REST_ErrorParam> request_params = new ArrayList<>();

    @Override
    public String toString() {
        return "REST_Error{" +
                "error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                '}';
    }
}
