package com.maxifly.fb2_illustrator.vapi;

import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.model.AlbumAddrParseResult;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Maximus on 31.07.2016.
 */
public class TestUrlCreator {
    @Test
    public void parseAlbumAddr() throws Exception {
        long id = UrlCreator.getAlbumId("http://vk.com/albums32047059911");
        assertEquals(32047059911L, id);

    }

    @Test
    public void parseAlbumAddr1() throws Exception {
        String addr = "https://vk.com/album320470599_237876710";
        AlbumAddrParseResult albumAddrParseResult = UrlCreator.parseAlbumURL(addr);
        assertEquals(320470599, albumAddrParseResult.ownerId);
        assertEquals(237876710, albumAddrParseResult.albumId);

        addr = "https://vk.com/album-320470599_237876710";
        albumAddrParseResult = UrlCreator.parseAlbumURL(addr);
        assertEquals(-320470599, albumAddrParseResult.ownerId);
        assertEquals(237876710, albumAddrParseResult.albumId);
    }


    @Test
    public void getToken() {
        String token =
                UrlCreator.getToken("http://oauth.vk.com/blank.html#access_token=abcd&expires_in=86400&user_id=320470599&email=kuku@mail.ru");

        assertEquals("abcd", token);

    }

    @Test
    public void getEmail() {
        String token =
                UrlCreator.getEmail("http://oauth.vk.com/blank.html#access_token=abcd&expires_in=86400&user_id=320470599&email=kuku@mail.ru");

        assertEquals("kuku@mail.ru", token);

    }
    @Test
    public void getUserId() {
        String userId =
                UrlCreator.getUserId("http://oauth.vk.com/blank.html#access_token=abcd&expires_in=86400&user_id=320470599&email=kuku@mail.ru");

        assertEquals("320470599", userId);

    }
    @Test
    public void getFileType() {
        String token =
                UrlCreator.getFileType("http://cs633720.vk.me/v633720599/333b0/qOOmIUbQ6Is.jpg");

        assertEquals("jpg", token);

    }

}
