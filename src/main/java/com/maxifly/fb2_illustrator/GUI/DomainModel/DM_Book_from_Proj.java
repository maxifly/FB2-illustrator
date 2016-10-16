package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.BookProcessor;
import com.maxifly.fb2_illustrator.BookProcessor_FB20;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Exception;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Illustrations;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.vapi.ProjectProcessor;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.model.Project_VK;
import javafx.beans.property.*;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Maximus on 31.07.2016.
 */
public class DM_Book_from_Proj
    extends DM_Abstract
{
    private StringProperty book_name;

    private StringProperty book_src_file;
    private StringProperty book_dst_file;

    private Factory_GUI factory_gui;

    private ObjectProperty<Project> projectObjectProperty = new SimpleObjectProperty<>();
    private ObjectProperty<DM_Project> dm_projectObjectProperty = new SimpleObjectProperty<>();


    public Project getProjectObjectProperty() {
        return projectObjectProperty.get();
    }

    public ObjectProperty<Project> projectObjectPropertyProperty() {
        return projectObjectProperty;
    }

    private void change_dmProject(DM_Project newValue){
        if (newValue != null) {
            projectObjectProperty.setValue(newValue.getProject());
        } else {
            projectObjectProperty.setValue(null);
        }
    };

    public DM_Book_from_Proj(Factory_GUI factory_gui) {
        book_name = new SimpleStringProperty();
        book_src_file = new SimpleStringProperty();
        book_dst_file = new SimpleStringProperty();
        this.factory_gui = factory_gui;
        projectObjectProperty = new SimpleObjectProperty<>();
        dm_projectObjectProperty.bindBidirectional(factory_gui.getDm_statusBar().dmProject_Property());
        dm_projectObjectProperty.addListener((observable, oldValue, newValue) -> change_dmProject(newValue));
        change_dmProject(factory_gui.getDm_statusBar().dmProject_Property().getValue());
    }

    public StringProperty book_name_Propery() {
        return book_name;
    }
    public StringProperty book_src_file_Propery() {
        return book_src_file;
    }
    public StringProperty book_dst_file_Propery() {
        return book_dst_file;
    }


    /**
     * Загрузка иллюстраций
     */
    public void load_ill() throws Exception {

        // Проверки
        Path inputFile = (FileSystems.getDefault().getPath(book_src_file.getValue()));
        Path outputFile = (FileSystems.getDefault().getPath(book_dst_file.getValue()));

System.out.println("book_dst_file "+ book_dst_file);

        File src_file = inputFile.toFile();
        if (!(src_file.exists() && src_file.isFile())) {
           throw new GUI_Exception("Исходный файл \n" + src_file.toString() + "\n не найден.");
        }




        Project project = projectObjectProperty.getValue();

        Illustrations illustrations = new Illustrations();

        for (Illustration illustration : project.getIllustrations()) {
            illustrations.addIllustration(illustration);
        }


        // Вставим иллюстрации
        BookProcessor bookParse = new BookProcessor_FB20();
        bookParse.loadBook(inputFile);
        bookParse.processBook(illustrations,project.getProjectParagraf(), outputFile);

        System.out.println("getProjectParagraf: " + project.getProjectParagraf());

    }
}
