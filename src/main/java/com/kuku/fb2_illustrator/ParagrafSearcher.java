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
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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

        ArrayList<SectionType> newSectionList = new ArrayList<>();

        for (SectionType section : mainBody.getSection()) {
            SectionType newSection = sectionProcessor(section);
            newSectionList.add(newSection);
        }

        List<SectionType> sectionLists = mainBody.getSection();
        sectionLists.clear();

        for (SectionType newElement : newSectionList) {
            sectionLists.add(newElement);
        }

        addBinarys(fictionBook, illustrations);

    }


    public void addBinarys(FictionBook fictionBook, Illustrations illustrations) {
        List<FictionBook.Binary> binaryList = fictionBook.getBinary();
        for (Illustration ill : illustrations.getAllIllustrations()) {
            FictionBook.Binary fbBinary = genBinary(ill);
            if (fbBinary != null) {
                binaryList.add(fbBinary);
            }
        }
    }

    private FictionBook.Binary genBinary(Illustration ill) {
        Path path = ill.getFile();
        try {
            byte[] data = Files.readAllBytes(path);

            FictionBook.Binary fictionBookBinary = objectFactory.createFictionBookBinary();
            fictionBookBinary.setValue(data);
            fictionBookBinary.setId(ill.getId());
            fictionBookBinary.setContentType("image/jpeg");

            return fictionBookBinary;

        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error when create binary: {}", e);
            return null;
        }


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
                    SectionType newSectionType = sectionProcessor(((JAXBElement<SectionType>) element).getValue());
                    JAXBElement<SectionType> new_stjxb = objectFactory.createSectionTypeSection(newSectionType);
                    newSectionObjectsList.add(new_stjxb);
                    break;
                default:
                    newSectionObjectsList.add(element);
            }

        }
        List<JAXBElement<?>> newSectionElements = section.getImageOrAnnotationOrSection();
        newSectionElements.clear();
        for (JAXBElement<?> newElement : newSectionObjectsList) {
            newSectionElements.add(newElement);
        }

        return section;
    }

    private List<JAXBElement<?>> paragrafProcessor(JAXBElement<PType> element) {
        ArrayList<JAXBElement<?>> elements = new ArrayList<>();

        elements.add(element);

        Paragraf paragraf = this.paragrafs.getParagrafByBookElement(element);


        if (this.illustrations.isIllustrated(paragraf)) {
            // параграф иллюстрирован
            log.debug("Paragraf {} illustrated.", paragraf);

            Iterator<Illustration> illIter = this.illustrations.getIllustrations(paragraf);
            while (illIter.hasNext()) {
                elements.add(createIllustration(illIter.next()));
            }
        }

        return elements;

    }


    private JAXBElement<?> createIllustration(Illustration ill) {

        ImageType imageType = objectFactory.createImageType();
        imageType.setHref(ill.getId());

        JAXBElement<ImageType> imageTypeJAXBElement =
                objectFactory.createStyleTypeImage(imageType);


        PType pType = objectFactory.createPType();
        List<Serializable> content = pType.getContent();
        content.add(imageTypeJAXBElement);

        PType pTypeDesc = objectFactory.createPType();
        List<Serializable> contentPTypeDesc = pTypeDesc.getContent();
        contentPTypeDesc.add(ill.getDescription());

        CiteType citeType = objectFactory.createCiteType();
        List<Object> citeList = citeType.getPOrPoemOrEmptyLine();
        citeList.add(pType);
        citeList.add(pTypeDesc);

        return objectFactory.createSectionTypeCite(citeType);

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
