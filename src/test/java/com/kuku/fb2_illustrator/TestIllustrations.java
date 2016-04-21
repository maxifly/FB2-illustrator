package com.kuku.fb2_illustrator;

import com.kuku.fb2_illustrator.fb2_xml.model.PType;
import com.kuku.fb2_illustrator.model.Illustration;
import com.kuku.fb2_illustrator.model.Illustrations;
import com.kuku.fb2_illustrator.model.Paragrafs;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Maximus on 21.04.2016.
 */
public class TestIllustrations {
    Illustrations illustrations;
    Paragrafs paragrafs;

    Illustration ill1,ill2,ill3;

    @Before
    public void createIllustrations() {
        illustrations = new Illustrations();
        ill1 = new Illustration("file1", "desc1");
        ill2 = new Illustration("file2", "desc2");
        ill3 = new Illustration("file3", "desc3");
        illustrations.addIllustration(ill1);
        illustrations.addIllustration(ill2);
        illustrations.addIllustration(ill3);
    }

    @Test
    public  void allNotChined() {
       Iterator<Illustration> iterator = illustrations.getNotChained();
       ArrayList illArray = new ArrayList();
       int i = 0;
       while (iterator.hasNext()) {
           Illustration ill = iterator.next();
           illArray.add(i,ill);
           i ++;
       }
       assertEquals(3,i);
       assertEquals("file1",((Illustration) illArray.get(0)).getFile());
       assertEquals("file2",((Illustration) illArray.get(1)).getFile());
       assertEquals("file3",((Illustration) illArray.get(2)).getFile());
    }

    @Test
    public  void chined2() {
        illustrations.illustratedParagraf(new PType(),ill2);
        Iterator<Illustration> iterator = illustrations.getNotChained();
        ArrayList illArray = new ArrayList();
        int i = 0;
        while (iterator.hasNext()) {
            Illustration ill = iterator.next();
            illArray.add(i,ill);
            i ++;
        }
        assertEquals(1,i);
        assertEquals("file3",((Illustration) illArray.get(0)).getFile());
    }

    @Test
    public  void chinedLast() {
        illustrations.illustratedParagraf(new PType(),ill3);
        Iterator<Illustration> iterator = illustrations.getNotChained();
        ArrayList illArray = new ArrayList();
        int i = 0;
        while (iterator.hasNext()) {
            Illustration ill = iterator.next();
            illArray.add(i,ill);
            i ++;
        }
        assertEquals(0,i);

    }
}
