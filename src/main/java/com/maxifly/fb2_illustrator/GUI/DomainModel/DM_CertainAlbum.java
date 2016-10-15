package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.BookProcessor;
import com.maxifly.fb2_illustrator.BookProcessor_FB20;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Exception;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Illustrations;
import com.maxifly.vapi.*;
import com.maxifly.vapi.model.DATA.DATA_photo;
import com.maxifly.vapi.model.Illustration_VK;
import com.maxifly.vapi.model.PhotoSize;
import com.maxifly.vapi.model.Project_VK;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
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
    private Factory_GUI factory_gui;

    public DM_CertainAlbum(Factory_GUI factory_gui) {
        book_name = new SimpleStringProperty();
        album_addr = new SimpleStringProperty();
        book_src_file = new SimpleStringProperty();
        book_dst_file = new SimpleStringProperty();
        book_check = new SimpleBooleanProperty();
        this.factory_gui = factory_gui;
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

        // Проверки
        Path inputFile = (FileSystems.getDefault().getPath(book_src_file.getValue()));
        Path outputFile = (FileSystems.getDefault().getPath(book_dst_file.getValue()));


        File src_file = inputFile.toFile();
        if (!(src_file.exists() && src_file.isFile())) {
           throw new GUI_Exception("Исходный файл \n" + src_file.toString() + "\n не найден.");
        }


        String accessToken = factory_gui.getDm_statusBar().getToken();
        long albumId = UrlCreator.getAlbumId(album_addr.getValue());

        // Загрузим проект
        ProjectProcessor projectProcessor = new ProjectProcessor(accessToken,albumId);
        Project_VK project_vk = projectProcessor.importProject("123456789"); //TODO надо как-то пойти от проверки идентификатора проекта
        projectProcessor.downloadPhotos(Files.createTempDirectory("fbill_"), project_vk);


        Illustrations illustrations = new Illustrations();

        for (Illustration illustration : project_vk.getIllustrations()) {
            illustrations.addIllustration(illustration);
        }


        // Вставим иллюстрации
        BookProcessor bookParse = new BookProcessor_FB20();
        bookParse.processBook(illustrations,project_vk.getProjectParagraf(), inputFile,outputFile);

        System.out.println("getProjectParagraf: " + project_vk.getProjectParagraf());

        // TODO Есть подозрение, что когда иллюстрация называется "1" и тп, то она не отображается корректно.

    }
}
