package com.maxifly.fb2_illustrator.model;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxifly.fb2_illustrator.*;
import com.maxifly.fb2_illustrator.GUI.Controllers.Ctrl_IllIco;
import javafx.beans.property.*;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * Created by Maxim.Pantuhin on 25.08.2016.
 */
public class Project {
    public static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    public static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(Project.class.getName());

    private String id;
    private String projectParagraf;
    private Set<SearchTemplate_POJO> bookNameTemplates = new HashSet<>();
    private List<Illustration> illustrations = new ArrayList(); //TODO тут надо как-то сохранять порядок вставки

    private ObjectProperty<File> projectFileProperty = new SimpleObjectProperty<>();
    private BooleanProperty changeProjectProperty = new SimpleBooleanProperty();

    private StringProperty bookName = new SimpleStringProperty();

    //private File projectFile;


    public Project() {
        super();
        generateId();
    }

    public void addBookNameTempale(SearchTemplate_POJO bookNameTemplate) {
        this.bookNameTemplates.add(bookNameTemplate);
    }

    public void addIll(Illustration illustration) {
        illustration.setId(illustrations.size());
        this.illustrations.add(illustration);
        illustration.setProject(this);
    }

    public List<Illustration> getIllustrations() {
        return illustrations;
    }

    public File getProjectFile() {
        return projectFileProperty.getValue();
    }

    public void setProjectFile(File projectFile) {
        this.projectFileProperty.setValue(projectFile);
        ;
    }

    public ObjectProperty<File> projectFile_Property() {
        return this.projectFileProperty;
    }

    public BooleanProperty changeProject_Property() {
        return changeProjectProperty;
    }

    public void setChanged(Boolean isChanged) {
        changeProjectProperty.setValue(isChanged);
    }


    /**
     * Убеждается, что есть каталог для файлов проекта
     *
     * @return
     * @throws IOException
     */
    public File ensureProjectDirectory() throws IOException {
        File project_file = this.getProjectFile();
        Path projectFilePath = project_file.toPath();
        String dir_name =
                FileOperations.stripExtension(projectFilePath.getFileName().toFile().toString()) + "_files";

        Path dir_path = projectFilePath.getParent().resolve(dir_name);
        File dir_file = dir_path.toFile();
        if (dir_file.exists()) {
            return dir_file;
        } else {
            return Files.createDirectories(dir_path).toFile();
        }

    }

    /**
     * Переносит все иллюстрации в определенный каталог
     *
     * @param ill_dir Каталог, в который надо перенести иллюстрации
     */
    public void copyIllToDir(File ill_dir) throws MyException {
        Path ill_dir_path = ill_dir.toPath();

        for (Illustration ill : this.illustrations) {
            Path ill_path = ill.getFile();
            if (ill_path != null && !ill_dir_path.equals(ill_path.getParent())) {
                Path dest = ill_dir_path.resolve(ill_path.getFileName());

                log.debug("Copy ill from {} to {}", ill_path, dest);

                try {
                    Files.copy(ill_path, dest, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new MyException("Can not copy illustration", e);
                }

                ill.setFile(dest);

            }
        }


    }


    /**
     * Меняет порядок иллюстраций
     *
     * @param moveIllId   - какую иллюстрацию передвинуть
     * @param beforeIllId - перед какой поставить (если null - то сделать последней)
     */
    public void moveIll(Integer moveIllId, Integer beforeIllId) throws MyException {
        log.debug("Move ill {} before {} ", moveIllId, beforeIllId);
        if (moveIllId.equals(beforeIllId)) return;

        ArrayList<Illustration> changeList = new ArrayList();

        // Сначала найдем иллюстрацию, которую надо передвинуть
        Illustration moveIll = null;
        int beforeIll_Idx = -1;
        int moveIll_idx = -1;

        int idx = 0;

        for (Illustration ill : illustrations) {
            if (ill.getId().equals(moveIllId)) {
                moveIll = ill;
                moveIll_idx = idx;
            }
            if (ill.getId().equals(beforeIllId)) {
                beforeIll_Idx = idx;
            }
            idx++;
        }

        // Проанализируем ошибки

        if (moveIll_idx == -1) {
            throw new MyException("Не найдена иллюстрация с идентификатором " + moveIllId);
        }
//        if (beforeIllId != null && beforeIll_Idx == -1) {
//            throw new MyException("Не найдена иллюстрация с идентификатором " + beforeIllId);
//        }

        // Сформируем новый список
        if (beforeIll_Idx == -1) {
            idx = moveIll_idx;
        } else if (beforeIll_Idx < moveIll_idx) {
            idx = beforeIll_Idx;
        } else {
            idx = moveIll_idx;
        }

        // Скопируем в новый массив элементы, которые идут без изменений
        if (idx != 0) {
            changeList.addAll(illustrations.subList(0, idx));
        }

        // Теперь переберем остаток, так как у остатка надо менять id
        Integer curr_idx_InNewList = changeList.size();
        int curr_idx = idx;

        for (Illustration ill : illustrations.subList(idx, illustrations.size())) {
            if (curr_idx != moveIll_idx) {

                if (curr_idx == beforeIll_Idx) {
                    moveIll.setId(curr_idx_InNewList);
                    changeList.add(moveIll);
                    curr_idx_InNewList++;
                }

                ill.setId(curr_idx_InNewList);
                changeList.add(ill);
                curr_idx_InNewList++;
            }
            curr_idx++;
        }

        // Если надо было поставить передвигаемый элемент в конец списка. То добавим его
        if (beforeIll_Idx == -1) {
            moveIll.setId(curr_idx_InNewList);
            changeList.add(moveIll);
            curr_idx_InNewList++;
        }

        // Теперь вставим новый список вместо старого
        illustrations.clear();
        illustrations.addAll(changeList);

    }


    /**
     * Удаляет иллюстрацию с указанным идентификатором из списка иллюстраций
     * Считается, что иллюстрации в списке отсортированы по идентификатору
     *
     * @param illId
     */
    public void delIll(Integer illId) {
        ArrayList<Illustration> changeList = new ArrayList();

        int idx = illId;
        // Скопируем в новый массив элементы, которые идут без изменений
        if (idx > 0) {
            changeList.addAll(illustrations.subList(0, idx));
        }

        // Теперь переберем остаток, так как у остатка надо менять id
        Integer curr_idx_InNewList = changeList.size();
        idx = illId + 1;

        if (idx < illustrations.size()) {

            for (Illustration ill : illustrations.subList(idx, illustrations.size())) {

                ill.setId(curr_idx_InNewList);
                changeList.add(ill);
                curr_idx_InNewList++;
            }
        }
        // Теперь вставим новый список вместо старого
        illustrations.clear();
        illustrations.addAll(changeList);
    }

    /**
     * Сохраняет проект
     *
     * @throws IOException
     * @throws MyException
     */
    public void save() throws IOException, MyException {
        File projectDid = ensureProjectDirectory();
        copyIllToDir(projectDid);

        String string_project = toJson();
        FileOperations.writeAll(getProjectFile(), string_project);
        setChanged(false);
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Illustration.class, new Ill_J_Serializer())
                .registerTypeAdapter(Project.class, new Project_J_Serializer())
                .create();

        return gson.toJson(this);

    }

    static public Project fromJson(String json) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Illustration.class, new Ill_J_Serializer())
                .registerTypeAdapter(Project.class, new Project_J_Serializer())
                .create();
        return gson.fromJson(json, Project.class);

        // TODO добавить сортировку иллюстраций
    }

    public String getId() {
        return id;
    }

    public Set<SearchTemplate_POJO> getBookNameTemplates() {
        return bookNameTemplates;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectParagraf() {
        return projectParagraf;
    }

    public void setProjectParagraf(String projectParagraf) {
        this.projectParagraf = projectParagraf;
    }

    public String getBookName() {
        return bookName.get();
    }

    public StringProperty bookNameProperty() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    /**
     * Генерирует новый идентификатор
     */
    public void generateId() {
        Random rnd = new Random();
        this.id = "" + (new Date()).getTime() + rnd.nextInt(100);
    }

}
