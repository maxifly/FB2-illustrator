package com.maxifly.fb2_illustrator;

import com.maxifly.fb2_illustrator.model.LastAddrs;
import com.maxifly.vapi.model.AlbumAddrAttributes;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Maximus on 17.10.2016.
 */
public class LastAddrsTest {

    @Test
    public void laTest() {
        LastAddrs lastAddrs = new LastAddrs(5);
        List<String> la = new ArrayList<>();

        lastAddrs.change_LastAddr("-1-");
        lastAddrs.change_LastAddr("-2-");
        lastAddrs.change_LastAddr("-3-");
        lastAddrs.change_LastAddr("-4-");
        lastAddrs.change_LastAddr("-5-");
        lastAddrs.change_LastAddr("-6-");

        for (String a:lastAddrs) {
            la.add(a);
        }

        assertEquals(5,la.size());
        assertEquals("-6-",la.get(0));
        assertEquals("-5-",la.get(1));
        assertEquals("-4-",la.get(2));
        assertEquals("-3-",la.get(3));
        assertEquals("-2-",la.get(4));

    }



}