package com.kuku.fb2_illustrator.vapi.model;

import com.google.gson.Gson;
import com.kuku.vapi.model.DATA.ILL_data;
import com.kuku.vapi.model.REST.REST_Result_photo;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Maximus on 11.07.2016.
 */
public class TestFormat {
    @Test
    public  void bad_ill_image() {
        //TODO Написать тест на неправильный формат
        String desc = "{\"fb_ill\":1,\"num\":1,\"dsc\":\"подпись1\",\"srch\":[{\"s\":\"строка поиска 1\"}, {\"s\":\"строка поиска 2\"},{\"re\":\"регулярное выражение 1\"}]}";
        Gson gson = new Gson();

        ILL_data ill_data = gson.fromJson(desc, ILL_data.class);

        assertEquals((int)1,(int)ill_data.fb_ill);
        assertEquals("srch count", 3, ill_data.srch.size());
        assertEquals("dsc","подпись1", ill_data.dsc);
        assertEquals("num",1,(int) ill_data.num);


    }

    @Test
    public  void ill_image() {
        String desc = "{\"fb_ill\":1,\"num\":1,\"dsc\":\"подпись1\",\"srch\":[{\"s\":\"строка поиска 1\"}, {\"s\":\"строка поиска 2\"},{\"re\":\"регулярное выражение 1\"}]}";
        Gson gson = new Gson();

        ILL_data ill_data = gson.fromJson(desc, ILL_data.class);

        assertEquals((int)1,(int)ill_data.fb_ill);
        assertEquals("srch count", 3, ill_data.srch.size());
        assertEquals("dsc","подпись1", ill_data.dsc);
        assertEquals("num",1,(int) ill_data.num);


    }


    @Test
    public void vk_photo() {

        String jsn = "{\"response\":{\"count\":2,\"items\":[{\"id\":420698652,\"album_id\":233176977,\"owner_id\":320470599,\"photo_75\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333a1\\/gQbgbjDH4qs.jpg\",\"photo_130\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333a2\\/ncpAP3xzDpo.jpg\",\"photo_604\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333a3\\/OweL0T4vt3U.jpg\",\"photo_807\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333a4\\/IHjOABkWqYc.jpg\",\"width\":700,\"height\":700,\"text\":\"{\\\"num\\\":1,\\\"dsc\\\":\\\"подпись1\\\",\\\"srch\\\":[{\\\"s\\\":\\\"строка поиска 1\\\"}, {\\\"s\\\":\\\"строка поиска 2\\\"},{\\\"re\\\":\\\"регулярное выражение 1\\\"}]}\",\"date\":1466017661},{\"id\":420702797,\"album_id\":233176977,\"owner_id\":320470599,\"photo_75\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333ad\\/CWoWyORWyn0.jpg\",\"photo_130\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333ae\\/5flufb0c8Gg.jpg\",\"photo_604\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333af\\/5aky3rGf4mo.jpg\",\"photo_807\":\"http:\\/\\/cs633720.vk.me\\/v633720599\\/333b0\\/qOOmIUbQ6Is.jpg\",\"width\":700,\"height\":700,\"text\":\"{\\\"num\\\":2,\\\"dsc\\\":\\\"подпись2\\\",\\\"srch\\\":[{\\\"s\\\":\\\"строка поиска 21\\\"}, {\\\"s\\\":\\\"строка поиска 22\\\"},{\\\"re\\\":\\\"регулярное выражение 21\\\"}]}\",\"date\":1466018927}]}}";
        Gson gson = new Gson();

        REST_Result_photo rest_result_photo = gson.fromJson(jsn, REST_Result_photo.class);

        assertEquals("response",2,rest_result_photo.response.count);
        assertNull("error null",rest_result_photo.error);

    }

    @Test
    public void vk_photo_error() {

        String error = "{\"error\":{\"error_code\":100,\"error_msg\":\"One of the parameters specified was missing or invalid: album_id is invalid\",\"request_params\":[{\"key\":\"oauth\",\"value\":\"1\"},{\"key\":\"method\",\"value\":\"photos.get\"},{\"key\":\"album_id1\",\"value\":\"233176977\"},{\"key\":\"v\",\"value\":\"5.52\"}]}}";
        Gson gson = new Gson();

        REST_Result_photo rest_result_photo = gson.fromJson(error, REST_Result_photo.class);

        assertEquals(100,rest_result_photo.error.error_code);

    }

}


