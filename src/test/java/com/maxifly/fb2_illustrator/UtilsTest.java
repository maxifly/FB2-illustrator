package com.maxifly.fb2_illustrator;

import com.maxifly.vapi.model.AlbumAddrAttributes;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by Maximus on 17.10.2016.
 */
public class UtilsTest {

    @Test
    public void aaaAsHashMapKey() {
        HashMap<AlbumAddrAttributes, Integer> albumAddrAttributesIntegerHashMap = new HashMap<>();

        AlbumAddrAttributes aaa1 = new AlbumAddrAttributes(1,2);
        AlbumAddrAttributes aaa2 = new AlbumAddrAttributes(1,2);
        AlbumAddrAttributes aaa3 = new AlbumAddrAttributes(1,3);

        albumAddrAttributesIntegerHashMap.put(aaa1,1);
        assertEquals(1L,(long)albumAddrAttributesIntegerHashMap.get(aaa2));
        assertNull(albumAddrAttributesIntegerHashMap.get(aaa3));
    }

    @Test
    public void clearPunctuation() {
        String s = Utils.normalize(" qwer,ty.uio");

        assertEquals("qwer ty uio", s);

        s = Utils.normalize(" Мама .. мыла \n раму!?");
        assertEquals("Мама мыла раму", s);


    }

}