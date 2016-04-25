package com.kuku.fb2_illustrator;

import com.kuku.fb2_illustrator.fb2_xml.model.PType;
import com.kuku.fb2_illustrator.model.Illustration;
import com.kuku.fb2_illustrator.model.Illustrations;
import com.kuku.fb2_illustrator.model.Paragraf;
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
        illustrations.illustratedParagraf(new Paragraf(),ill2);
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
        illustrations.illustratedParagraf(new Paragraf(),ill3);
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

    @Test
    public void twoIllustration() {
        Paragraf paragraf = new Paragraf();

        int i = 0;
        Iterator<Illustration> illIter = illustrations.getIllustrations(paragraf);
        assertEquals(false,illIter.hasNext());
        assertEquals(false,illustrations.isIllustrated(paragraf));

        illustrations.illustratedParagraf(paragraf,ill2);
        illustrations.illustratedParagraf(paragraf,ill2);
        illustrations.illustratedParagraf(paragraf,ill1);

         i = 0;
         illIter = illustrations.getIllustrations(paragraf);
        assertEquals(true,illustrations.isIllustrated(paragraf));
        ArrayList<Illustration> illArray = new ArrayList();
        while (illIter.hasNext()) {
            Illustration ill = illIter.next();
            illArray.add(i,ill);
            i ++;
        }

        assertEquals(2,i);
        assertEquals(ill2,illArray.get(0));
        assertEquals(ill1,illArray.get(1));
    }


    @Test
    public  void chineNotChined() {
        Paragrafs paragrafs = new Paragrafs();
        Paragraf[] paragraf = new  Paragraf[7];

        for (int i = 0; i<=6 ; i++ ) {
            paragraf[i] = new Paragraf();
            paragrafs.addParagraf(paragraf[i]);
        }

        illustrations.illustratedParagraf(paragraf[1], ill1);
        illustrations.chineByOrder(paragrafs);

        assertEquals("Illustrated paragraf 3", true,illustrations.isIllustrated(paragraf[3]));
        assertEquals("Illustrated paragraf 5",true,illustrations.isIllustrated(paragraf[5]));
    }

}
