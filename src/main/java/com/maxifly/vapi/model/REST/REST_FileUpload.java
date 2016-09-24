package com.maxifly.vapi.model.REST;

import java.io.File;

/**
 * Created by Maximus on 24.09.2016.
 */
public class REST_FileUpload {
    public File file;
    public String resourcePath;
    public String field_name;


    public REST_FileUpload(File file, String resourcePath, String field_name) {
        this.file = file;
        this.resourcePath = resourcePath;
        this.field_name = field_name;
    }

    @Override
    public String toString() {
        return "REST_FileUpload{" +
                "file=" + file +
                ", resourcePath='" + resourcePath + '\'' +
                ", field_name='" + field_name + '\'' +
                '}';
    }


}
