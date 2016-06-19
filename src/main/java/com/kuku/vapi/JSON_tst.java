package com.kuku.vapi;


import com.google.gson.Gson;
import com.kuku.vapi.model.REST.REST_Result_photo;
import com.kuku.vapi.model.ScopeElement;

/**
 * Created by Maximus on 17.06.2016.
 */
public class JSON_tst {

    public static void main(String[] params) throws Exception {

        String jsn = "{\"response\":{\"count\":2,\"items\":[{\"id\":420698652,\"album_id\":233176977,\"owner_id\":320470599,\"photo_75\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333a1\\/gQbgbjDH4qs.jpg\",\"photo_130\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333a2\\/ncpAP3xzDpo.jpg\",\"photo_604\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333a3\\/OweL0T4vt3U.jpg\",\"photo_807\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333a4\\/IHjOABkWqYc.jpg\",\"width\":700,\"height\":700,\"text\":\"{\\\"num\\\":1,\\\"dsc\\\":\\\"подпись1\\\",\\\"srch\\\":[{\\\"s\\\":\\\"строка поиска 1\\\"}, {\\\"s\\\":\\\"строка поиска 2\\\"},{\\\"re\\\":\\\"регулярное выражение 1\\\"}]}\",\"date\":1466017661},{\"id\":420702797,\"album_id\":233176977,\"owner_id\":320470599,\"photo_75\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333ad\\/CWoWyORWyn0.jpg\",\"photo_130\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333ae\\/5flufb0c8Gg.jpg\",\"photo_604\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333af\\/5aky3rGf4mo.jpg\",\"photo_807\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333b0\\/qOOmIUbQ6Is.jpg\",\"width\":700,\"height\":700,\"text\":\"{\\\"num\\\":2,\\\"dsc\\\":\\\"подпись2\\\",\\\"srch\\\":[{\\\"s\\\":\\\"строка поиска 21\\\"}, {\\\"s\\\":\\\"строка поиска 22\\\"},{\\\"re\\\":\\\"регулярное выражение 21\\\"}]}\",\"date\":1466018927}]}}";
String error = "{\"error\":{\"error_code\":100,\"error_msg\":\"One of the parameters specified was missing or invalid: album_id is invalid\",\"request_params\":[{\"key\":\"oauth\",\"value\":\"1\"},{\"key\":\"method\",\"value\":\"photos.get\"},{\"key\":\"album_id1\",\"value\":\"233176977\"},{\"key\":\"v\",\"value\":\"5.52\"}]}}";
        Gson gson = new Gson();

        REST_Result_photo latestVersion = gson.fromJson(error, REST_Result_photo.class);

        int i = 1;

    }
}
