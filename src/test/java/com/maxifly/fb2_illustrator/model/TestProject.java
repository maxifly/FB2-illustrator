package com.maxifly.fb2_illustrator.model;

import com.maxifly.fb2_illustrator.MyException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Maxim.Pantuhin on 05.09.2016.
 */
public class TestProject {
    Project project;
    Illustration ill1,ill2,ill3,ill4;

    @Before
    public void createProject(){
        project = new Project();
        ill1 = new Illustration("01","ill_1");
        ill2 = new Illustration("02","ill_2");
        ill3 = new Illustration("03","ill_3");
        ill4 = new Illustration("04","ill_4");

        project.addIll(ill1);
        project.addIll(ill2);
        project.addIll(ill3);
        project.addIll(ill4);
    }




    @Test
    public void changeIllId() {
        assertEquals("size",4,project.getIllustrations().size());
        assertEquals("Ill 1", "0", ill1.getId());
        assertEquals("Ill 2", "1", ill2.getId());
        assertEquals("Ill 3", "2", ill3.getId());
        assertEquals("Ill 4", "3", ill4.getId());

    }

    @Test
    public void move_2_before_1() throws MyException {
        project.moveIll("1","0");
        assertEquals("size",4,project.getIllustrations().size());
        assertEquals("Ill 2_in 0", "0", ill2.getId());
        assertEquals("Ill 1 in 1", "1", ill1.getId());
        assertEquals("Ill 3 in 3", "2", ill3.getId());
        assertEquals("Ill 4 in 4", "3", ill4.getId());

    }

    @Test
    public void move_3_before_1() throws MyException {
        project.moveIll("2","0");
        assertEquals("size",4,project.getIllustrations().size());
        assertEquals("Ill 3_in 0", "0", ill3.getId());
        assertEquals("Ill 1 in 1", "1", ill1.getId());
        assertEquals("Ill 2 in 3", "2", ill2.getId());
        assertEquals("Ill 4 in 4", "3", ill4.getId());

    }

    @Test
    public void move_1_to_last() throws MyException {
        project.moveIll("0",null);

        assertEquals("size",4,project.getIllustrations().size());

        assertEquals("Ill 2_in 0", "0", ill2.getId());
        assertEquals("Ill 3 in 1", "1", ill3.getId());
        assertEquals("Ill 4 in 3", "2", ill4.getId());
        assertEquals("Ill 1 in 4", "3", ill1.getId());

    }

    @Test
    public void move_3_before_2() throws MyException {
        project.moveIll("2","1");
        assertEquals("size",4,project.getIllustrations().size());
        assertEquals("Ill 1_in 0", "0", ill1.getId());
        assertEquals("Ill 3 in 1", "1", ill3.getId());
        assertEquals("Ill 2 in 3", "2", ill2.getId());
        assertEquals("Ill 4 in 4", "3", ill4.getId());

    }
}
