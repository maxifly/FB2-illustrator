package com.maxifly.fb2_illustrator.vapi;

import com.maxifly.vapi.UrlCreator;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Maximus on 31.07.2016.
 */
public class TestUrlCreator {
    @Test
    public void parseAlbumAddr() throws Exception {
        int id =  UrlCreator.getAlbumId("http://vk.com/albums320470599");
        assertEquals(320470599, 320470599);

    }

    @Test
    public void getToken() {
        String token =
                UrlCreator.getToken("http://oauth.vk.com/blank.html#access_token=abcd&expires_in=86400&user_id=320470599&email=kuku@mail.ru");

        assertEquals("abcd",token);

    }
    @Test
    public void getEmail() {
        String token =
                UrlCreator.getEmail("http://oauth.vk.com/blank.html#access_token=abcd&expires_in=86400&user_id=320470599&email=kuku@mail.ru");

        assertEquals("kuku@mail.ru",token);

    }
}
