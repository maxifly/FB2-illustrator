package com.maxifly.fb2_illustrator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Maximus on 17.10.2016.
 */
public class UtilsTest {

    @Test
    public void clearPunctuation() {
        String s = Utils.normalize(" qwer,ty.uio");

        assertEquals("qwer ty uio", s);

        s = Utils.normalize(" Мама .. мыла \n раму!?");
        assertEquals("Мама мыла раму", s);


    }

}