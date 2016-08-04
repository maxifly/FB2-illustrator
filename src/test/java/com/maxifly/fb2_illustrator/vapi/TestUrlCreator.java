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
}
