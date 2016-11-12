package com.maxifly.fb2_illustrator.GUI;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.Fb2App;
import com.maxifly.fb2_illustrator.GUI.Controllers.*;
import com.maxifly.fb2_illustrator.GUI.DomainModel.*;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.Settings;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.fb2_illustrator.model.TemplateType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by Maximus on 23.07.2016.
 */
public class Factory_GUI {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(Factory_GUI.class.getName());

    final private DM_StatusBar dm_statusBar = new DM_StatusBar();
    final private DM_MainMenu dm_mainMenu = new DM_MainMenu(this);
    private HBox hBox_statusBar;
    private DM_Login dm_login = null;
    private Scene mainScene;

    private Ctrl_Project ctrl_project;
    private Ctrl_StatusBar ctrl_statusBar;


    public Scene getMainScene(){
      return this.mainScene;
    }

    public Scene createMainScene() throws IOException, MyException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("Root.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_Root();
            }
        });

        BorderPane root = loader.load();

        root.setCenter(this.createCertainAction());
        HBox statusBar = this.getStatusBar();
        root.setBottom(statusBar);
        root.setAlignment(statusBar, Pos.BOTTOM_RIGHT);
        root.setTop(this.createMainMenu());
        Settings settings = null;
        try {
            settings = Settings.readSettings(Constants.ensureAppDataDir());
        } catch (MyException e) {
            log.warn("Exception when read settings: {}", e);
        }

        if (settings == null) settings = Settings.createNewSettings();

        this.getDm_statusBar().setSettings(settings);

        Scene scene = new Scene(root, settings.getWinSize_W(), settings.getWinSize_H());
        Ctrl_Root ctrl_root = loader.getController();

        scene.getStylesheets().add(Fb2App.class.getResource("GUI/fb2ill.css").toExternalForm());
        this.mainScene = scene;
        return scene;
    }

    public DM_StatusBar getDm_statusBar() {
        return dm_statusBar;
    }

    public HBox getStatusBar() throws IOException {
        Factory_GUI factory_gui = this;

        if (this.hBox_statusBar == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Factory_GUI.class.getResource("StatusBar.fxml"));
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aClass) {
                    return new Ctrl_StatusBar(dm_statusBar, factory_gui);
                }
            });


            this.hBox_statusBar = loader.load();
            this.ctrl_statusBar = loader.getController();
        }
        return this.hBox_statusBar;
    }


    public GUI_Obj createSearchTemplate()
            throws IOException {
        SearchTemplate_POJO searchTemplate_pojo = new SearchTemplate_POJO(TemplateType.substr,null,null);
        return createSearchTemplate(searchTemplate_pojo);

    }

    private GUI_Obj innerCreateSearchTemplate(String FXMLFile, SearchTemplate_POJO searchTemplate_pojo)
            throws IOException {
//        Factory_GUI factory_gui = this;
        FXMLLoader loader = new FXMLLoader();
        DM_SearchTemplate dm_searchTemplate = new DM_SearchTemplate();
        dm_searchTemplate.setSearchTemplate(searchTemplate_pojo);

        loader.setLocation(Factory_GUI.class.getResource(FXMLFile));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_SearchTemplate(dm_searchTemplate);
            }
        });
        Pane pane = loader.load();
        return new GUI_Obj(pane,loader.getController(),dm_searchTemplate);
    }

    public GUI_Obj createSearchTemplate(SearchTemplate_POJO searchTemplate_pojo)
            throws IOException {

        return innerCreateSearchTemplate("FormSearchTemplate.fxml", searchTemplate_pojo);
    }

    public GUI_Obj createSearchTemplate_Sht(SearchTemplate_POJO searchTemplate_pojo)
            throws IOException {

        return innerCreateSearchTemplate("FormSearchTemplate_Sht.fxml", searchTemplate_pojo);
    }


    private GUI_Obj innerCreateSearchTemplate_Edit(String FXMLFile, SearchTemplate_POJO searchTemplate_pojo)
            throws IOException {
//        Factory_GUI factory_gui = this;
        FXMLLoader loader = new FXMLLoader();
        DM_SearchTemplate dm_searchTemplate = new DM_SearchTemplate();
        dm_searchTemplate.setSearchTemplate(searchTemplate_pojo);

        loader.setLocation(Factory_GUI.class.getResource(FXMLFile));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_SearchTemplate_Edit(dm_searchTemplate);
            }
        });
        Pane pane = loader.load();
        return new GUI_Obj(pane,loader.getController(),dm_searchTemplate);
    }


    public GUI_Obj createSearchTemplate_Edit(SearchTemplate_POJO searchTemplate_pojo)
            throws IOException {
        return innerCreateSearchTemplate_Edit("FormSearchTemplate_Edit.fxml", searchTemplate_pojo);
    }
    public GUI_Obj createSearchTemplate_Edit_Sht(SearchTemplate_POJO searchTemplate_pojo)
            throws IOException {
        return innerCreateSearchTemplate_Edit("FormSearchTemplate_Edit_Sht.fxml", searchTemplate_pojo);
    }

    public GUI_Obj createProjectInfo(DM_Project dm_project)
            throws IOException {
        Factory_GUI factory_gui = this;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("FormProjectInfo.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {

                return new Ctrl_ProjectInfo(factory_gui, dm_project);
            }
        });
        Node node = loader.load();
        return new GUI_Obj(node,loader.getController(),dm_project);

    }

    public GUI_Obj createIll(Illustration ill)
            throws IOException {
        Factory_GUI factory_gui = this;
        DM_Ill dm_ill = new DM_Ill();
        dm_ill.setIll(ill);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("FormIll.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {

                return new Ctrl_Ill(factory_gui, dm_ill);
            }
        });
        Node node = loader.load();
        return new GUI_Obj(node,loader.getController(),dm_ill);

    }

    public GUI_Obj createIllIco(DM_Ill dm_ill)
            throws IOException {
        Factory_GUI factory_gui = this;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("FormIllIco.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_IllIco(dm_ill);
            }
        });
        Pane pane = loader.load();
        return new GUI_Obj(pane, loader.getController(), dm_ill);
    }

    public GUI_Obj createExportProject()
            throws IOException {
        Factory_GUI factory_gui = this;
        DM_ExportProject dm_exportProject = new DM_ExportProject(factory_gui.getDm_statusBar());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("Form_ProjectExport.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_ExportProject(factory_gui, dm_exportProject);
            }
        });
        Node node = loader.load();

        return new GUI_Obj(node, loader.getController(), dm_exportProject);
    }


    public GUI_Obj createProjectDelete()
            throws IOException {
        Factory_GUI factory_gui = this;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("Form_DeleteVKProj.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_DeleteVKProject(factory_gui);
            }
        });
        Node node = loader.load();

        return new GUI_Obj(node, loader.getController(), null);
    }

    public GUI_Obj createImportVKProject()
            throws IOException {
        Factory_GUI factory_gui = this;
        DM_ImportVKProject dm_importVKProject = new DM_ImportVKProject(factory_gui.getDm_statusBar());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("Form_ImportVKProj.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_ImportVKProject(factory_gui,dm_importVKProject);
            }
        });
        Node node = loader.load();

        return new GUI_Obj(node, loader.getController(), dm_importVKProject);
    }
    public GUI_Obj createProject(Project project)
            throws IOException {
        Factory_GUI factory_gui = this;
        DM_Project dm_project = new DM_Project(project);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("FormProject.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_Project(factory_gui, dm_project);
            }
        });
        Node node = loader.load();

        // Надо сохранить эту переменную, чтобы не разрушился биндинг на значение
        // интересное только контроллеру
        // подробнее смотри: http://stackoverflow.com/questions/26312651/bidirectional-javafx-binding-is-destroyed-by-unrelated-code

        this.ctrl_project = loader.getController();

        return new GUI_Obj(node, ctrl_project, dm_project);
    }

    public GUI_Obj createBookFromVKProj()
            throws IOException, JAXBException {
        Factory_GUI factory_gui = this;
        DM_Book_from_VKProj dm_book_from_proj = new DM_Book_from_VKProj(this);



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("FormBook_from_VKProject.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_Book_from_VKProj(dm_book_from_proj, factory_gui);
            }
        });
        Node node = loader.load();

        // Надо сохранить эту переменную, чтобы не разрушился биндинг на значение
        // интересное только контроллеру
        // подробнее смотри: http://stackoverflow.com/questions/26312651/bidirectional-javafx-binding-is-destroyed-by-unrelated-code

        Ctrl_Book_from_VKProj ctrl_book_from_VK_proj = loader.getController();
        return new GUI_Obj(node, ctrl_book_from_VK_proj, dm_book_from_proj);
    }

   public GUI_Obj createBookFromCurProj()
            throws IOException, JAXBException {
        Factory_GUI factory_gui = this;
        DM_Book_from_CurrProj dm_book_from_proj = new DM_Book_from_CurrProj(this);



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("FormBook_from_CurrProject.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_Book_from_CurrProj(dm_book_from_proj);
            }
        });
        Node node = loader.load();

        // Надо сохранить эту переменную, чтобы не разрушился биндинг на значение
        // интересное только контроллеру
        // подробнее смотри: http://stackoverflow.com/questions/26312651/bidirectional-javafx-binding-is-destroyed-by-unrelated-code

        Ctrl_Book_from_CurrProj ctrl_book_from_Curr_proj = loader.getController();
        return new GUI_Obj(node, ctrl_book_from_Curr_proj, dm_book_from_proj);
    }


   public GUI_Obj createProgressWindow()
            throws IOException {
        Factory_GUI factory_gui = this;
        DM_ProgressWindow dm_progressWindow = new DM_ProgressWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("Form_ProgressWindow.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_ProgressWindow(factory_gui,dm_progressWindow);
            }
        });
        Node node = loader.load();

        return new GUI_Obj(node, loader.getController(), dm_progressWindow);
    }


    public GUI_Obj createLoading()
            throws IOException, JAXBException {
        Factory_GUI factory_gui = this;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("FormLoading.fxml"));
        Node node = loader.load();

        // Надо сохранить эту переменную, чтобы не разрушился биндинг на значение
        // интересное только контроллеру
        // подробнее смотри: http://stackoverflow.com/questions/26312651/bidirectional-javafx-binding-is-destroyed-by-unrelated-code

        Ctrl_Book_from_CurrProj ctrl_book_from_Curr_proj = loader.getController();
        return new GUI_Obj(node, ctrl_book_from_Curr_proj, null);
    }


    public Pane createCertainAction() throws IOException {
        Factory_GUI factory_gui = this;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("FormCertainAlbum.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                DM_CertainAlbum dm_certainAlbum = new DM_CertainAlbum(factory_gui);
                return new Ctrl_CertainAlbum(dm_certainAlbum);
            }
        });
        Pane pane = loader.load();
        return pane;

    }

    public Stage createLoginWindow() throws IOException, MyException {
        Factory_GUI factory_gui = this;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("FormLogin.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                dm_login = new DM_Login(factory_gui);
                return new Ctrl_Login(dm_login);
            }
        });
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("My modal window");
        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(this.getMainScene().getWindow() );

        return stage;

    }

    public Stage createModalWindow(Node node) throws IOException {
        Factory_GUI factory_gui = this;
        Stage stage = new Stage();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Factory_GUI.class.getResource("FormLogin.fxml"));
//        loader.setControllerFactory(new Callback<Class<?>, Object>() {
//            @Override
//            public Object call(Class<?> aClass) {
//                dm_login = new DM_Login(factory_gui);
//                return new Ctrl_Login(dm_login);
//            }
//        });
        Parent root = new BorderPane();
        ((BorderPane)root).setCenter(node);
        stage.setScene(new Scene(root));
        stage.setTitle("My modal window");
        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(this.getMainScene().getWindow() );

        return stage;

    }


    public MenuBar createMainMenu() throws IOException {

        Factory_GUI factory_gui = this;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("MainMenu.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
                                        @Override
                                        public Object call(Class<?> aClass) {
                                            return new Ctrl_MainMenu(dm_mainMenu, factory_gui);
                                        }
                                    });
        MenuBar menu = loader.load();
        return  menu;


    }


    public DM_Login getDm_login() {
        return dm_login;
    }
}
