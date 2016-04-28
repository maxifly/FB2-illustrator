package com.kuku.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.kuku.fb2_illustrator.fb2_xml.model.*;
import com.kuku.fb2_illustrator.model.Illustration;
import com.kuku.fb2_illustrator.model.Illustrations;
import com.kuku.fb2_illustrator.model.Paragraf;
import com.kuku.fb2_illustrator.model.Paragrafs;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import javax.xml.bind.JAXBElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Created by Maximus on 18.04.2016.
 */
public class ParagrafSearcher {
    ObjectFactory objectFactory = new ObjectFactory();
    Paragrafs paragrafs;
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(BookParse.class.getName());
    private Illustrations illustrations;

    public void search(FictionBook fictionBook, Illustrations illustrations, Paragrafs paragrafs) {

        this.illustrations = illustrations;
        this.paragrafs = paragrafs;
        FictionBook.Body mainBody = fictionBook.getBody().iterator().next();

        for (SectionType section : mainBody.getSection()) {
            sectionParser(section);
        }
        illustrations.chineByOrder(paragrafs);

    }

    public void process(FictionBook fictionBook, Illustrations illustrations, Paragrafs paragrafs) {

        this.illustrations = illustrations;
        this.paragrafs = paragrafs;
        FictionBook.Body mainBody = fictionBook.getBody().iterator().next();

        for (SectionType section : mainBody.getSection()) {
            sectionProcessor(section);
        }
        illustrations.chineByOrder(paragrafs);

    }

    private SectionType sectionProcessor(SectionType section) {
        ArrayList<JAXBElement<?>> newSectionObjectsList = new ArrayList<>(); // Сюда будем складывать новый список элементов

        for (JAXBElement<?> element : section.getImageOrAnnotationOrSection()) {
            switch (element.getDeclaredType().getSimpleName()) {
                case "PType":
                    log.debug("Paragraf");
                    List<JAXBElement<?>> newElementsList = paragrafProcessor((JAXBElement<PType>) element);
                    for (JAXBElement<?> newElement : newElementsList) {
                        newSectionObjectsList.add(newElement);
                    }

                    break;
                case "SectionType":
                    log.debug("Section");
                    int i = 1;
                    sectionProcessor(((JAXBElement<SectionType>) element).getValue()); //TODO Понять всеж таки какой тип тут должен быть
                    break;
                default:
                    newSectionObjectsList.add(element);
            }

        }
        return null; //TODO
    }

    private List<JAXBElement<?>> paragrafProcessor(JAXBElement<PType> element) {
        ArrayList<JAXBElement<?>> elements = new ArrayList<>();

        elements.add(element);

        if (1 = 1) { // TODO Надо как-то понять, что параграф иллюстрирован
            // параграф иллюстрирован
            elements.add(createIllustration());
        }

        return elements;

    }


    private JAXBElement<PType> createIllustration() {
        //TODO Надо написать что сюда вставлять как картинку и все такое

        ImageType imageType = objectFactory.createImageType();
        imageType.setAlt("Alt");
        imageType.setHref("href_href");

        JAXBElement<ImageType> imageTypeJAXBElement =
                objectFactory.createStyleTypeImage(imageType);


        PType pType = objectFactory.createPType();
        List<Serializable> content = pType.getContent();
        content.add(imageTypeJAXBElement);
        content.add("test image text");

        return objectFactory.createSectionTypeP(pType);

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
