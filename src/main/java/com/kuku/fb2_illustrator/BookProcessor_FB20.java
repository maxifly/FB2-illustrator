package com.kuku.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.kuku.fb2_illustrator.fb2_xml.model.FictionBook;
import com.kuku.fb2_illustrator.model.Illustrations;
import com.kuku.fb2_illustrator.model.Paragrafs;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;


public class BookProcessor_FB20 implements BookProcessor {
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(BookProcessor_FB20.class.getName());


    @Override
    public void processBook(Illustrations illustrations, Path inputFile, Path outputFile) throws Exception {
        log.debug("Book XML " + inputFile);

        // Разберем XML
        JAXBContext jc = JAXBContext.newInstance("com.kuku.fb2_illustrator.fb2_xml.model");
        Unmarshaller unmarshaller = jc.createUnmarshaller();


        FictionBook fictionBook = (FictionBook) unmarshaller.unmarshal(inputFile.toFile());

        // Поищем иллюстрации

        ParagrafSearcher_FB20 ps = new ParagrafSearcher_FB20();
        Paragrafs paragrafs = new Paragrafs();
        ps.search(fictionBook, illustrations, paragrafs);

        // Теперь вставим иллюстрации в книгу

        ps.process(fictionBook, illustrations, paragrafs);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(fictionBook, outputFile.toFile());

    }
}