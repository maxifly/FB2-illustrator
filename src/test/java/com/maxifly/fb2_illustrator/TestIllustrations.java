package com.maxifly.fb2_illustrator;

import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Illustrations;
import com.maxifly.fb2_illustrator.model.Paragraf;
import com.maxifly.fb2_illustrator.model.Paragrafs;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Iterator;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Maximus on 21.04.2016.
 */
public class TestIllustrations {
    Illustrations illustrations, illustrations_2, illustrations_3;
    Paragrafs paragrafs;

    Illustration ill1,ill2,ill3, ill21, ill22, ill31;

    @Before
    public void createIllustrations() {
        illustrations = new Illustrations();
        illustrations_2 = new Illustrations();
        illustrations_3 = new Illustrations();

        ill1 = new Illustration(1, FileSystems.getDefault().getPath("file1"), "desc1");
        ill2 = new Illustration(2,FileSystems.getDefault().getPath("file2"), "desc2");
        ill3 = new Illustration(3,FileSystems.getDefault().getPath("file3"), "desc3");
        illustrations.addIllustration(ill1);
        illustrations.addIllustration(ill2);
        illustrations.addIllustration(ill3);

        ill21 = new Illustration(21, FileSystems.getDefault().getPath("file1"), "desc1");
        ill22 = new Illustration(22,FileSystems.getDefault().getPath("file2"), "desc2");

        illustrations_2.addIllustration(ill21);
        illustrations_2.addIllustration(ill22);

        ill31 = new Illustration(31, FileSystems.getDefault().getPath("file1"), "desc1");

        illustrations_3.addIllustration(ill31);

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
       assertEquals("file1",((Illustration) illArray.get(0)).getFile().toString());
       assertEquals("file2",((Illustration) illArray.get(1)).getFile().toString());
       assertEquals("file3",((Illustration) illArray.get(2)).getFile().toString());
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
        assertEquals("file3",true, "file3".equals( ((Illustration) illArray.get(0)).getFile().toString() ));
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
    public  void chineNotChined_ill_2_3() {
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

    @Test
    public  void chineNotChined_ill_1_3() {
        Paragrafs paragrafs = new Paragrafs();
        Paragraf[] paragraf = new  Paragraf[7];

        for (int i = 0; i<=6 ; i++ ) {
            paragraf[i] = new Paragraf();
            paragrafs.addParagraf(paragraf[i]);
        }

        illustrations.illustratedParagraf(paragraf[3], ill2);
        illustrations.chineByOrder(paragrafs);

        assertEquals("Illustrated paragraf 1", true,illustrations.isIllustrated(paragraf[1]));
        assertEquals("Illustrated paragraf 5", true,illustrations.isIllustrated(paragraf[5]));
//        assertEquals("Illustrated paragraf 5",true,illustrations.isIllustrated(paragraf[5]));
    }

    @Test
    public  void chineNotChined_ill_2by2() {
        Paragrafs paragrafs = new Paragrafs();
        Paragraf[] paragraf = new  Paragraf[7];

        for (int i = 0; i<=6 ; i++ ) {
            paragraf[i] = new Paragraf();
            paragrafs.addParagraf(paragraf[i]);
        }

        illustrations_2.chineByOrder(paragrafs);

        assertEquals("Illustrated paragraf 2", true,illustrations_2.isIllustrated(paragraf[2]));
        assertEquals("Illustrated paragraf 4", true,illustrations_2.isIllustrated(paragraf[4]));
//        assertEquals("Illustrated paragraf 5",true,illustrations.isIllustrated(paragraf[5]));
    }

    @Test
    public  void chineNotChined_ill_1by1() {
        Paragrafs paragrafs = new Paragrafs();
        Paragraf[] paragraf = new  Paragraf[7];

        for (int i = 0; i<=6 ; i++ ) {
            paragraf[i] = new Paragraf();
            paragrafs.addParagraf(paragraf[i]);
        }

        illustrations_3.chineByOrder(paragrafs);

        assertEquals("Illustrated paragraf 3", true,illustrations_3.isIllustrated(paragraf[3]));
//        assertEquals("Illustrated paragraf 4", true,illustrations_2.isIllustrated(paragraf[4]));
//        assertEquals("Illustrated paragraf 5",true,illustrations.isIllustrated(paragraf[5]));
    }

}
