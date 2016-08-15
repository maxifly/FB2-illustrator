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

//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//
//            @Override
//            public void uncaughtException(Thread thread, Throwable t) {
//                StringWriter sw = new StringWriter();
//                PrintWriter pw = new PrintWriter(sw);
//                t.printStackTrace(pw);
//               // sw.toString();
//
//                System.out.println("There was an exception. " + t.getMessage());
//                System.out.println("There was an exception. " + sw.toString());
//
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Exception Dialog");
//                alert.setHeaderText("Look, an Exception Dialog");
//                alert.setContentText("Could not find file blabla.txt!");
//
////                Exception ex = new FileNotFoundException("Could not find file blabla.txt");
////
////// Create expandable Exception.
////                StringWriter sw = new StringWriter();
////                PrintWriter pw = new PrintWriter(sw);
////                ex.printStackTrace(pw);
//                String exceptionText = sw.toString();
//
//                Label label = new Label("The exception stacktrace was:");
//
//                TextArea textArea = new TextArea(exceptionText);
//                textArea.setEditable(false);
//                textArea.setWrapText(true);
//
//                textArea.setMaxWidth(Double.MAX_VALUE);
//                textArea.setMaxHeight(Double.MAX_VALUE);
//                GridPane.setVgrow(textArea, Priority.ALWAYS);
//                GridPane.setHgrow(textArea, Priority.ALWAYS);
//
//                GridPane expContent = new GridPane();
//                expContent.setMaxWidth(Double.MAX_VALUE);
//                expContent.add(label, 0, 0);
//                expContent.add(textArea, 0, 1);
//
//// Set expandable Exception into the dialog pane.
//                alert.getDialogPane().setExpandableContent(expContent);
//
//                alert.showAndWait();
//
//
//            }
//
//        });


        try {


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
        catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

