package com.maxifly.fb2_illustrator.GUI;

import com.maxifly.fb2_illustrator.Fb2App;
import com.maxifly.fb2_illustrator.GUI.Controllers.*;
import com.maxifly.fb2_illustrator.GUI.DomainModel.*;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.fb2_illustrator.model.TemplateType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/**
 * Created by Maximus on 23.07.2016.
 */
public class Factory_GUI {
//    StatusUser status_user;
//
//    public StatusUser getStatus_user() {
//        if (status_user == null) {
//            status_user = new StatusUser();
//        }
//        return status_user;
//    }


//    public HBox getStateRow() {
//        HBox stateRow = new HBox(10);
//        stateRow.setAlignment(Pos.CENTER_RIGHT);
//        stateRow.getChildren().add(this.getStatus_user());
//
//        stateRow.setStyle("-fx-border-color: blue");
//        stateRow.setSpacing(10);
//        stateRow.setPadding(new Insets(5,10,5,10));
//        return stateRow;
//    }


    final private DM_StatusBar dm_statusBar = new DM_StatusBar();
    final private DM_MainMenu dm_mainMenu = new DM_MainMenu(this);
    private HBox hBox_statusBar;
    private DM_Login dm_login = null;
    private Scene mainScene;

    private Ctrl_Project ctrl_project;


    public Scene getMainScene() throws IOException {
      if (this.mainScene == null) {
          this.mainScene = createMainScene();
      }
      return this.mainScene;
    }

    private Scene createMainScene() throws IOException {
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


        Scene scene = new Scene(root, 400, 250);
        Ctrl_Root ctrl_root = loader.getController();
//        ctrl_root.setListeners();
        scene.getStylesheets().add(Fb2App.class.getResource("GUI/fb2ill.css").toExternalForm());
        return scene;
    }

    public DM_StatusBar getDm_statusBar() {
        return dm_statusBar;
    }

    public HBox getStatusBar() throws IOException {

        if (this.hBox_statusBar == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Factory_GUI.class.getResource("StatusBar.fxml"));
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aClass) {
                    return new Ctrl_StatusBar(dm_statusBar);
                }
            });


            this.hBox_statusBar = loader.load();
        }
        return this.hBox_statusBar;
    }


    public GUI_Obj createSearchTemplate()
            throws IOException {
        SearchTemplate_POJO searchTemplate_pojo = new SearchTemplate_POJO(TemplateType.substr,null,null);
        return createSearchTemplate(searchTemplate_pojo);

    }

    public GUI_Obj createSearchTemplate(SearchTemplate_POJO searchTemplate_pojo)
            throws IOException {
//        Factory_GUI factory_gui = this;
        FXMLLoader loader = new FXMLLoader();
        DM_SearchTemplate dm_searchTemplate = new DM_SearchTemplate();
        dm_searchTemplate.setSearchTemplate(searchTemplate_pojo);

        loader.setLocation(Factory_GUI.class.getResource("FormSearchTemplate.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_SearchTemplate(dm_searchTemplate);
            }
        });
        Pane pane = loader.load();
        return new GUI_Obj(pane,loader.getController(),dm_searchTemplate);
    }

    public GUI_Obj createSearchTemplate_Edit(SearchTemplate_POJO searchTemplate_pojo)
            throws IOException {
//        Factory_GUI factory_gui = this;
        FXMLLoader loader = new FXMLLoader();
        DM_SearchTemplate dm_searchTemplate = new DM_SearchTemplate();
        dm_searchTemplate.setSearchTemplate(searchTemplate_pojo);

        loader.setLocation(Factory_GUI.class.getResource("FormSearchTemplate_Edit.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Ctrl_SearchTemplate_Edit(dm_searchTemplate);
            }
        });
        Pane pane = loader.load();
        return new GUI_Obj(pane,loader.getController(),dm_searchTemplate);
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

    public Stage createLoginWindow() throws IOException {
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Factory_GUI.class.getResource("MainMenu.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
                                        @Override
                                        public Object call(Class<?> aClass) {
                                            return new Ctrl_MainMenu(dm_mainMenu);
                                        }
                                    });
        MenuBar menu = loader.load();
        return  menu;


    }


    public DM_Login getDm_login() {
        return dm_login;
    }
}
