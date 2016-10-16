package com.maxifly.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.fb2_xml.model.FictionBook;
import com.maxifly.fb2_illustrator.model.Illustrations;
import com.maxifly.fb2_illustrator.model.Paragrafs;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;


public class BookProcessor_FB20 implements BookProcessor {
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(BookProcessor_FB20.class.getName());

    private  FictionBook fictionBook;
    private JAXBContext jc;

    public BookProcessor_FB20() throws JAXBException {
        jc = JAXBContext.newInstance("com.maxifly.fb2_illustrator.fb2_xml.model");
    }

    @Override
    public void loadBook(Path inputFile)  throws Exception {
        log.debug("Book XML " + inputFile);

        // Разберем XML
        Unmarshaller unmarshaller = jc.createUnmarshaller();


        fictionBook = (FictionBook) unmarshaller.unmarshal(inputFile.toFile());
    }

    @Override
    public void processBook(Illustrations illustrations, String projectInfo, Path outputFile) throws Exception {

        // Поищем иллюстрации
        ParagrafSearcher_FB20 ps = new ParagrafSearcher_FB20();
        Paragrafs paragrafs = new Paragrafs();
        ps.search(fictionBook, illustrations, paragrafs);

        // Теперь вставим иллюстрации в книгу

        ps.process(fictionBook, illustrations, paragrafs, projectInfo);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(fictionBook, outputFile.toFile());

    }
}