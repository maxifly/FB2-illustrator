package com.maxifly.fb2_illustrator.GUI.DomainModel;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.FileOperations;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Obj;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.fb2_illustrator.model.TemplateType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

/**
 * Created by Maximus on 07.08.2016.
 */
public class DM_MainMenu {
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(DM_MainMenu.class.getName());
    private Factory_GUI factory_gui;

    private ObjectProperty<Project> currentProjectProperty = new SimpleObjectProperty<>();

    public DM_MainMenu(Factory_GUI factory_gui) {
        this.factory_gui = factory_gui;
    }

    public void vk_connect() throws IOException, InterruptedException, MyException {
        log.debug("connect");
        Stage stage = factory_gui.createLoginWindow();
        DM_Login dm_login = factory_gui.getDm_login();

        dm_login.connect();
        stage.show();
    }

    public void project_open(File file) throws IOException {
        String string_project =  FileOperations.readAll(file);
        Project project = Project.fromJson(string_project);
        project.setProjectFile(file);
        currentProjectProperty.setValue(project);
        showProject(project);
    }

    public void project_save(File file) throws IOException {
        Project project = (Project)currentProjectProperty.getValue();
        project.setProjectFile(file);
        String string_project =  project.toJson();
        FileOperations.writeAll(file,string_project);
        showProject(project);
    }

    public void project_save() throws IOException {
        Project project = (Project)currentProjectProperty.getValue();
        String string_project =  project.toJson();
        FileOperations.writeAll(project.getProjectFile(),string_project);
        project.setChanged(false);
        showProject(project);
    }



    public void setAndShowNewCurrentProject(Project project) throws IOException {
        currentProjectProperty.setValue(project);
        showProject(project);
    }

    public void project_new() throws IOException {
        Project project = new Project();
        currentProjectProperty.setValue(project);
        showProject(project);
    }

    public void project_test() throws IOException {

        Project project = new Project();
        Illustration illustration = createIll(1,"kuku");
        project.addIll(illustration);
         illustration = createIll(2,"tutu");
        project.addIll(illustration);
         illustration = createIll(3,"lulu");
        project.addIll(illustration);
        illustration = createIll(4,"pupu");
        project.addIll(illustration);
         illustration = createIll(11,"kuku");
        project.addIll(illustration);
         illustration = createIll(12,"tutu");
        project.addIll(illustration);
         illustration = createIll(13,"lulu");
        project.addIll(illustration);
        illustration = createIll(14,"pupu");
        project.addIll(illustration);

        currentProjectProperty.setValue(project);
        showProject(project);

    }

    public void showProject(Project project) throws IOException {
        GUI_Obj gui_obj = factory_gui.createProject(project);
        Scene scene = factory_gui.getMainScene();
        DM_StatusBar statusBar = factory_gui.getDm_statusBar();
        statusBar.setCurrentProject((DM_Project) gui_obj.dm_model);
        ( (BorderPane) scene.getRoot()).setCenter(gui_obj.node);
    }

    public void ill() throws IOException {
        Illustration illustration = createIll(2,"kuku");

        GUI_Obj gui_obj = factory_gui.createIll(illustration);

        Scene scene = factory_gui.getMainScene();
        ( (BorderPane) scene.getRoot()).setCenter(gui_obj.node);



    }


    private Illustration createIll(Integer number, String prefix) {
        Illustration illustration = new Illustration(number, FileSystems.getDefault().getPath("file.jpg"), "desc_ill");
        illustration.addSearchTempale(new SearchTemplate_POJO(TemplateType.regexp, prefix + "0","description"));
        illustration.addSearchTempale(new SearchTemplate_POJO(TemplateType.substr,prefix + "1","description1"));
        illustration.addSearchTempale(new SearchTemplate_POJO(TemplateType.substr,prefix + "2","description1"));
        illustration.addSearchTempale(new SearchTemplate_POJO(TemplateType.substr,prefix + "3","description1"));
        illustration.addSearchTempale(new SearchTemplate_POJO(TemplateType.substr,prefix + "4","description1"));
        return illustration;

    }

    public ObjectProperty<Project> currentProjectProperty() {
        return this.currentProjectProperty;
    }


}
