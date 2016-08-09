package com.maxifly.fb2_illustrator;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Login;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by Maximus on 23.07.2016.
 */
public class Fb2App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Factory_GUI factory_gui = new Factory_GUI();

        Scene mainScene = factory_gui.getMainScene();

//        BorderPane root = new BorderPane();
//        //root.setCenter(btn);
//        root.setCenter(factory_gui.createCertainAction());
//
//        HBox statusBar = factory_gui.getStatusBar();
//        root.setBottom(statusBar);
//        root.setAlignment(statusBar, Pos.BOTTOM_RIGHT);
//
//        root.setTop(factory_gui.createMainMenu());
//
//        factory_gui.getDm_statusBar().setLogin("kuku");
//        Scene scene = new Scene(root, 400, 250);
//
//        scene.getStylesheets().add(Fb2App.class.getResource("GUI/fb2ill.css").toExternalForm());

        primaryStage.setTitle("FB2 Illustrator");
        primaryStage.setScene(mainScene);


//        Parent parent = factory_gui.createLoginForm();
//        Scene scene1 = new Scene(parent, 400, 250);
//        primaryStage.setScene(scene1);

//        DM_Login dm_login = factory_gui.getDm_login();
//
//        dm_login.connect();



        primaryStage.show();
//        Thread.sleep(1000);

//        while (dm_login.getToken1() == null) {
//            Thread.sleep(1000);
//        }

   //     System.out.println("dm_login.getToken1() " + dm_login.getToken1());



    }

    public static void main(String[] args) {
        launch(args);
    }
}

