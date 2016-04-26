package com.kuku.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.kuku.fb2_illustrator.fb2_xml.model.FictionBook;
import com.kuku.fb2_illustrator.fb2_xml.model.PType;
import com.kuku.fb2_illustrator.fb2_xml.model.SectionType;
import com.kuku.fb2_illustrator.model.Illustration;
import com.kuku.fb2_illustrator.model.Illustrations;
import com.kuku.fb2_illustrator.model.Paragraf;
import com.kuku.fb2_illustrator.model.Paragrafs;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.Iterator;
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
    private Illustrations illustrations;

    public void search(FictionBook fictionBook, Illustrations illustrations) {

        this.illustrations = illustrations;
        FictionBook.Body mainBody = fictionBook.getBody().iterator().next();

        for (SectionType section : mainBody.getSection()) {
            sectionParser(section);
        }
        illustrations.chineByOrder(paragrafs);

    }


    private void sectionParser(SectionType section) {

        for (JAXBElement<?> element : section.getImageOrAnnotationOrSection()) {

            switch (element.getDeclaredType().getSimpleName()) {
                case "PType":
                    log.debug("Paragraf");
                    paragrafParser((JAXBElement<PType>) element);
                    break;
                case "SectionType":
                    log.debug("Section");
                    int i = 1;
                    sectionParser(((JAXBElement<SectionType>) element).getValue());
                    break;
                default:
                    break;
            }

        }

    }


    private void paragrafParser(JAXBElement<PType> pType) {
        Paragraf paragraf = new Paragraf((pType));
        this.paragrafs.addParagraf(paragraf);


        Iterator<Illustration> illIter = this.illustrations.getNotChained();

        exitlabel:
        while (illIter.hasNext()) {
            Illustration ill = illIter.next();

            // Теперь проверим, что эта иллюстрация подходит к параграфу
            for (Object element : pType.getValue().getContent()) {

                if (element instanceof String) {
                    //log.debug("Paragraf string {}",(String)element);
                    // Отлично. Это строка. Проверим, что она подходит под одну из новых иллюстраций
                    if (ill.isSuitable((String) element)) {
                        // Этот параграф подходит
                        illustrations.illustratedParagraf(paragraf, ill);
                        // Выйдем из цикла перебора иллюстраций
                        break exitlabel;
                    }
                }
            }

        }
    }


}
