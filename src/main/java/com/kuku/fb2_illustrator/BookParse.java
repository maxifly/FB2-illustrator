package com.kuku.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.kuku.fb2_illustrator.fb2_xml.model.FictionBook;
import com.kuku.fb2_illustrator.model.Illustration;
import com.kuku.fb2_illustrator.model.Illustrations;
import com.kuku.fb2_illustrator.model.Paragrafs;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.nio.file.Path;


public class BookParse {
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(BookParse.class.getName());

    private Path BookFile = null;
    private Path outpootBook = null;


//    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.0Z'");


    public void setBookFile(Path bookFile) {
      this.BookFile = bookFile;
    }


    public void parse(Illustrations illustrations) throws Exception {

        log.debug("Book XML " + this.BookFile);

        // Разберем XML
        JAXBContext jc = JAXBContext.newInstance("com.kuku.fb2_illustrator.fb2_xml.model");
        Unmarshaller unmarshaller = jc.createUnmarshaller();


        FictionBook fictionBook = (FictionBook) unmarshaller.unmarshal(this.BookFile.toFile());

        // Поищем иллюстрации

        ParagrafSearcher ps = new ParagrafSearcher();
        Paragrafs paragrafs = new Paragrafs();
        ps.search(fictionBook, illustrations, paragrafs);

        // Теперь вставим иллюстрации в книгу



 int i = 1;

        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(fictionBook, this.outpootBook.toFile());


        // TODO сделать
    }


    public void setOutpootBook(Path outpootBook) {
        this.outpootBook = outpootBook;
    }
}