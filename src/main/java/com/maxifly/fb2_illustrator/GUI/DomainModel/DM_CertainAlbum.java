package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.BookProcessor;
import com.maxifly.fb2_illustrator.BookProcessor_FB20;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Illustrations;
import com.maxifly.vapi.*;
import com.maxifly.vapi.model.DATA.DATA_photo;
import com.maxifly.vapi.model.InternetIllustration;
import com.maxifly.vapi.model.PhotoSize;
import com.maxifly.vapi.model.ScopeElement;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Created by Maximus on 31.07.2016.
 */
public class DM_CertainAlbum {
    private StringProperty book_name;

    private StringProperty album_addr;
    private StringProperty book_src_file;
    private StringProperty book_dst_file;

    private BooleanProperty book_check;

    public DM_CertainAlbum() {
        book_name = new SimpleStringProperty();
        album_addr = new SimpleStringProperty();
        book_src_file = new SimpleStringProperty();
        book_dst_file = new SimpleStringProperty();
        book_check = new SimpleBooleanProperty();
    }

    public StringProperty book_name_Propery() {
        return book_name;
    }
    public StringProperty album_addr_Propery() {
        return album_addr;
    }
    public StringProperty book_src_file_Propery() {
        return book_src_file;
    }
    public StringProperty book_dst_file_Propery() {
        return book_dst_file;
    }

    public BooleanProperty book_check_Propery() {
        return book_check;
    }

    /**
     * Загрузка иллюстраций
     */
    public void load_ill() throws Exception {
        if (true) {
            throw new Exception("kuku");
        }

        // TODO Написать форму получения токена
        ScopeElement[] scopes = {ScopeElement.photos, ScopeElement.groups, ScopeElement.e_mail};
        Connect connect = new Connect("5509552",scopes,"maxpant@mail.ru", "kukuku");

        String accessToken = connect.getAccessToken(60000);



        PhotoProcessor photoProcessor = new PhotoProcessor(accessToken,233176977, PhotoSize.photo_2560x2048);
        IllFilter illFilter = new IllFilter();
        PhotoLoader photoLoader = new PhotoLoader(Files.createTempDirectory("fbill_"));



        while(photoProcessor.hasNext()) {
            DATA_photo data_photo = photoProcessor.next();
            System.out.println(data_photo.text +"\n"+data_photo.url);
            illFilter.add(data_photo);
        }

        System.out.println("Illustrations count " + illFilter.getIllustrations().size());

        photoLoader.setIllustrationList(illFilter.getIllustrations());
        photoLoader.download();

        int i = 1;

        List<InternetIllustration> illustrationList = illFilter.getIllustrations();

        Collections.sort(illustrationList,new InternetIllComparator());

        Illustrations illustrations = new Illustrations();

        for (Illustration illustration : illustrationList) {
            illustrations.addIllustration(illustration);
        }



        Path inputFile = (FileSystems.getDefault().getPath("priv_fales", "kukutest2.fb2"));
        Path outputFile = (FileSystems.getDefault().getPath("priv_fales", "test_out2.fb2"));
        BookProcessor bookParse = new BookProcessor_FB20();
        bookParse.processBook(illustrations,inputFile,outputFile);

    }
}
