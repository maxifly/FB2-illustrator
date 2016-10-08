package com.maxifly.vapi.model.REST;

/**
 * Created by Maximus on 05.10.2016.
 * Объект, в котором содержаться данные для осуществления пост-запроса
 */
public class REST_POST_Data {
    public String url;
    public String body;

    public REST_POST_Data(String url, String body) {
        this.url = url;
        this.body = body;
    }
}
