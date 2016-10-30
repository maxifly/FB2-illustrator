package com.maxifly.fb2_illustrator;

import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Login;
import com.maxifly.fb2_illustrator.GUI.ExceptionHandler;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Maximus on 23.07.2016.
 */
public class Fb2App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        try {


        Factory_GUI factory_gui = new Factory_GUI();
        MyShutdownHook myShutdownHook = new MyShutdownHook(factory_gui);
        Runtime.getRuntime().addShutdownHook(myShutdownHook);
        Scene mainScene = factory_gui.createMainScene();

        primaryStage.setTitle("FB2 Illustrator");
        primaryStage.setScene(mainScene);


        primaryStage.show();



        }
        catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

