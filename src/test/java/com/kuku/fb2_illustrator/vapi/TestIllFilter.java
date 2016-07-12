package com.kuku.fb2_illustrator.vapi;

import com.kuku.fb2_illustrator.model.Illustration;
import com.kuku.fb2_illustrator.model.Illustrations;
import com.kuku.vapi.IllFilter;
import com.kuku.vapi.model.DATA.DATA_photo;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Maximus on 13.07.2016.
 */
public class TestIllFilter {

//    private List<DATA_photo> photoList = new ArrayList<>();
//
//    @Before
//    public void createIllustrations() {
//
//    }


    @Test
    public  void add_without_book () {

        IllFilter illFilter = new IllFilter("book1",true);

        DATA_photo data_photo = new DATA_photo();
        data_photo.url = "url_test";
        data_photo.text =  "{\"fb_ill\":1,\"num\":1,\"dsc\":\"подпись1\",\"srch\":[{\"s\":\"строка поиска 1\"}, {\"s\":\"строка поиска 2\"},{\"re\":\"регулярное выражение 1\"}]}";

        illFilter.add(data_photo);
        assertEquals("add_without_book",0,illFilter.getIllustrations().size());

        data_photo.text =  "{\"bk\":\"book1\", \"num\":1,\"dsc\":\"подпись1\",\"srch\":[{\"s\":\"строка поиска 1\"}, {\"s\":\"строка поиска 2\"},{\"re\":\"регулярное выражение 1\"}]}";
        illFilter.add(data_photo);
        assertEquals("add_incorrect_json",0,illFilter.getIllustrations().size());

        data_photo.text =  "kuku";
        illFilter.add(data_photo);
        assertEquals("add not json",0,illFilter.getIllustrations().size());

        data_photo.text =  "{\"fb_ill\":1,\"bk\":\"book2\", \"num\":1,\"dsc\":\"подпись1\",\"srch\":[{\"s\":\"строка поиска 1\"}, {\"s\":\"строка поиска 2\"},{\"re\":\"регулярное выражение 1\"}]}";
        illFilter.add(data_photo);
        assertEquals("add_incorrect_book",0,illFilter.getIllustrations().size());

        data_photo.text =  "{\"fb_ill\":1,\"bk\":\"book1\", \"num\":1,\"dsc\":\"подпись1\",\"srch\":[{\"s\":\"строка поиска 1\"}, {\"s\":\"строка поиска 2\"},{\"re\":\"регулярное выражение 1\"}]}";
        illFilter.add(data_photo);
        assertEquals("add_correct_book",1,illFilter.getIllustrations().size());

        illFilter.add(data_photo);
        assertEquals("add_correct_book 2",2,illFilter.getIllustrations().size());

    }

}
