package com.maxifly.fb2_illustrator;

import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Maximus on 23.07.2016.
 */
public class Fb2App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Factory_GUI factory_gui = new Factory_GUI();

        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        BorderPane root = new BorderPane();
        root.setCenter(btn);
        root.setBottom(factory_gui.getStatusNBox());

        factory_gui.getStatusBar().setLogin("kuku");
        Scene scene = new Scene(root, 400, 250);

        scene.getStylesheets().add(Fb2App.class.getResource("GUI/fb2ill.css").toExternalForm());

        primaryStage.setTitle("FB2 Illustrator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

