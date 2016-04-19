package com.kuku.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.kuku.fb2_illustrator.fb2_xml.model.FictionBook;
import com.kuku.fb2_illustrator.fb2_xml.model.PType;
import com.kuku.fb2_illustrator.fb2_xml.model.SectionType;
import com.kuku.fb2_illustrator.model.Paragrafs;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.Vector;

/**
 * Created by Maximus on 18.04.2016.
 */
public class ParagrafSearcher {
   Paragrafs paragrafs = new Paragrafs();
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(BookParse.class.getName());

   public void search(FictionBook fictionBook) {


      FictionBook.Body mainBody =  fictionBook.getBody().iterator().next();

      for (SectionType section : mainBody.getSection()) {

          sectionParser(section);

//        for (JAXBElement<?> element : section.getImageOrAnnotationOrSection()) {
//
//            String kk =  element.getDeclaredType().getSimpleName(); //"PType"
            int i = 1;
//        }



      };

   }


   public void sectionParser(SectionType section) {

       for (JAXBElement<?> element : section.getImageOrAnnotationOrSection() ) {

           switch(element.getDeclaredType().getSimpleName()) {
               case "PType":
                   log.debug("Paragraf");
                   paragrafParser((JAXBElement<PType>) element);
                   break;
               case "SectionType":
                   log.debug("Section");
                   int i=1;
                   sectionParser( ((JAXBElement<SectionType>) element).getValue());
                   break;
               default:
                   break;
           }

       }

   }


   private void paragrafParser(JAXBElement<PType> paragraf) {
       this.paragrafs.addParagraf(paragraf);

       for (Object element : paragraf.getValue().getContent()) {

           if ( element instanceof String ) {
               // Отлично. Это строка. Проверим, что она подходит под одну из новых иллюстраций

               if ( ((String) element).contains("Реальность") ) {
                   // Этот параграф подходит
                   int i=1;
               }

           }
       }

   }


}
