package com.maxifly.fb2_illustrator;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Maximus on 17.10.2016.
 */
public class UtilsTest {

    @Test
    public void clearPunctuation() {
        String s = Utils.clearPunctuation(" qwer,ty.uio");

        assertEquals("qwer ty uio", s);

        s = Utils.clearPunctuation(" Мама .. мыла \n раму!?");
        assertEquals("Мама мыла раму", s);


    }

}