package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.BookProcessor;
import com.maxifly.fb2_illustrator.BookProcessor_FB20;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Exception;
import com.maxifly.fb2_illustrator.Utils;
import com.maxifly.fb2_illustrator.model.*;
import javafx.beans.property.*;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by Maximus on 31.07.2016.
 */
public abstract class DM_Book_from_Proj
        extends DM_Abstract
        implements ValueToTest {

    protected static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    protected static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(DM_Book_from_Proj.class.getName());

    private StringProperty warnings = new SimpleStringProperty();
    private StringProperty book_name;
    private String normilizeBookName;
    private String book_name_FromFile = "";

    private StringProperty book_src_file;
    private StringProperty book_dst_file;

    protected Factory_GUI factory_gui;

    private ObjectProperty<Project> projectObjectProperty = new SimpleObjectProperty<>();
    private ObjectProperty<DM_Project> dm_projectObjectProperty = new SimpleObjectProperty<>();


    private BookProcessor bookParse;


    public Project getProjectObjectProperty() {
        return projectObjectProperty.get();
    }

    public ObjectProperty<Project> projectObjectPropertyProperty() {
        return projectObjectProperty;
    }

    private void change_dmProject(DM_Project newValue) {
        if (newValue != null) {
            projectObjectProperty.setValue(newValue.getProject());
        } else {
            projectObjectProperty.setValue(null);
        }
    }

    ;

    protected boolean checkBookname(Project project) {
        if (project != null) {
            for (SearchTemplate_POJO searchTemplate_pojo : project.getBookNameTemplates()) {
                try {
                    if (searchTemplate_pojo.checkAsSubstring(this)) return true;
                } catch (Check_Exception e) {
                    log.warn("Error when check book name: {}", e);
                }
            }

        }

        return false;
    }

    private boolean checkBookName() {
        return checkBookname(projectObjectProperty.getValue());
    }


    private void change_BookName(String newValue) {
        normilizeBookName = (newValue == null) ? null : Utils.normalize(newValue);

        if (newValue == null || "".equals(newValue.trim())) {
            warnings.setValue("Название книги не задано");
        } else {
            if (!checkBookName()) {
                warnings.setValue("Название книги не соответствует шаблону проекта");
            } else {
                warnings.setValue("");
            }
        }
    }

    private void change_srcFile(String newValue) {
        if (newValue != null && !"".equals(newValue)) {
            Path inputFile = (FileSystems.getDefault().getPath(newValue));
            log.debug("Src file: {}", newValue);

            File src_file = inputFile.toFile();
            if ((src_file.exists() && src_file.isFile())) {
                try {
                    bookParse.loadBook(inputFile);
                    book_name_FromFile = bookParse.getTitle();
                    book_name.setValue(bookParse.getTitle());
                    log.debug("title: {}", book_name.getValue());
                } catch (Exception e) {
                    log.error("Error {}", e);
                }
            } else {
                book_name.setValue(null);
                book_name_FromFile = "";
            }
        }
    }

    public void setBookNameFromFile() {
        book_name.setValue(book_name_FromFile);
    }

    public DM_Book_from_Proj(Factory_GUI factory_gui) throws JAXBException {
        book_name = new SimpleStringProperty();
        book_src_file = new SimpleStringProperty();
        book_dst_file = new SimpleStringProperty();
        this.factory_gui = factory_gui;
        projectObjectProperty = new SimpleObjectProperty<>();
        dm_projectObjectProperty.addListener((observable, oldValue, newValue) -> change_dmProject(newValue));
        book_name.addListener((observable, oldValue, newValue) -> change_BookName(newValue));
        book_src_file.addListener((observable, oldValue, newValue) -> change_srcFile(newValue));
        bookParse = new BookProcessor_FB20();

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


    public String getWarnings() {
        return warnings.get();
    }

    public StringProperty warningsProperty() {
        return warnings;
    }


    public ObjectProperty<DM_Project> dm_projectObjectPropertyProperty() {
        return dm_projectObjectProperty;
    }

    /**
     * Загрузка иллюстраций
     */
    public void load_ill() throws Exception {

        // Проверки
        Path inputFile = (FileSystems.getDefault().getPath(book_src_file.getValue()));
        Path outputFile = (FileSystems.getDefault().getPath(book_dst_file.getValue()));

        System.out.println("book_dst_file " + book_dst_file);

        // Проверка наличия исходного файла
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
        bookParse.processBook(illustrations, project.getProjectParagraf(), outputFile);

    }

    @Override
    public String getNormalize() {
        return normilizeBookName;
    }

    @Override
    public String getOriginal() {
        return book_name.getValue();
    }
}
