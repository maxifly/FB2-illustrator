package com.kuku.fb2_illustrator;

import com.kuku.fb2_illustrator.fb2_xml.model.FictionBook;
import com.kuku.fb2_illustrator.fb2_xml.model.SectionType;

import javax.xml.bind.JAXBElement;
import java.util.Vector;

/**
 * Created by Maximus on 18.04.2016.
 */
public class ParagrafSearcher {


   public void search(FictionBook fictionBook) {


      FictionBook.Body mainBody =  fictionBook.getBody().iterator().next();

      for (SectionType section : mainBody.getSection()) {

        for (JAXBElement<?> element : section.getImageOrAnnotationOrSection()) {

            String kk =  element.getDeclaredType().getSimpleName(); "PType"
            int i = 1;
        }



      };

   }


}
