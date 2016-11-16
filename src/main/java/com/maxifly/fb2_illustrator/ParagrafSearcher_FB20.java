package com.maxifly.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.fb2_xml.model.*;
import com.maxifly.fb2_illustrator.model.*;
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
public class ParagrafSearcher_FB20
        implements ParagrafSearcher<FictionBook> {
    private ObjectFactory objectFactory = new ObjectFactory();
    private Paragrafs paragrafs;
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(ParagrafSearcher_FB20.class.getName());
    private Illustrations illustrations;

    private int currentParagrafNumber;
    private int lastParagrafNumber;

    private String projectInfo;


    @Override
    public void search(FictionBook fictionBook, Illustrations illustrations, Paragrafs paragrafs) {

        this.illustrations = illustrations;
        this.paragrafs = paragrafs;
        FictionBook.Body mainBody = fictionBook.getBody().iterator().next();

        for (SectionType section : mainBody.getSection()) {
            sectionParser(section);
        }
        illustrations.chineByOrder(paragrafs);

    }


    @Override
    public FictionBook process(FictionBook fictionBook, Illustrations illustrations, Paragrafs paragrafs, String projectInfo) {

        this.illustrations = illustrations;
        this.paragrafs = paragrafs;
        this.projectInfo = projectInfo;
        this.currentParagrafNumber = 0;

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
        return fictionBook;
    }


    private void addBinarys(FictionBook fictionBook, Illustrations illustrations) {
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
        log.debug("*** ill path {}", path);
        try {
            byte[] data = Files.readAllBytes(path);

            log.debug("Read file {}. Size: {}", path, data.length);

            FictionBook.Binary fictionBookBinary = objectFactory.createFictionBookBinary();
            fictionBookBinary.setValue(data);
            fictionBookBinary.setId("ill_" + ill.getId().toString());
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
                    List<JAXBElement<?>> newElementsList = paragrafProcessor((JAXBElement<PType>) element);
                    for (JAXBElement<?> newElement : newElementsList) {
                        newSectionObjectsList.add(newElement);
                    }
                    break;
                case "SectionType":
                    int i = 1;
                    SectionType newSectionType = sectionProcessor(((JAXBElement<SectionType>) element).getValue());
                    JAXBElement<SectionType> new_stjxb = objectFactory.createSectionTypeSection(newSectionType);
                    newSectionObjectsList.add(new_stjxb);
                    break;
                default:
                    newSectionObjectsList.add(element);
            }

            // Выясним последний это был параграф или нет
            this.currentParagrafNumber++;
            if (this.currentParagrafNumber == this.lastParagrafNumber) {
                log.debug("last paragraf {}", this.currentParagrafNumber);

                PType pType_projectInfo = objectFactory.createPType();
                List<Serializable> contentPTypeDesc = pType_projectInfo.getContent();
                contentPTypeDesc.add(this.projectInfo);

                newSectionObjectsList.add(objectFactory.createSectionTypeP(pType_projectInfo));

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
        imageType.setHref("ill_" + ill.getId().toString());

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
            this.lastParagrafNumber++;
            switch (element.getDeclaredType().getSimpleName()) {
                case "PType":
                    paragrafParser((JAXBElement<PType>) element);
                    break;
                case "SectionType":
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

        // Подготовим данные для сравнения
        List<ComparedText> comparedTexts = new ArrayList<>();
        for (Object element : pType.getValue().getContent()) {
            if (element instanceof String) {
                comparedTexts.add(new ComparedText((String) element));
            }
        }

        exitlabel:
        while (illIter.hasNext()) {
            Illustration ill = illIter.next();

            for (ComparedText comparedText : comparedTexts) {
                if (ill.isSuitable(comparedText)) {
                    // Этот параграф подходит
                    illustrations.illustratedParagraf(paragraf, ill);
                    // Выйдем из цикла перебора иллюстраций
                    break exitlabel;
                }
            }
        }
    }

}
