package com.maxifly.fb2_illustrator.GUI;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.vapi.PhotoProcessor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Maximus on 16.08.2016.
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(ExceptionHandler.class.getName());


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("{}", e);

        MyException gui_exception = getGuiException(e);
        Throwable throwable = (gui_exception == null) ? e : gui_exception;


        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);


        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText(null);
        if (gui_exception == null) {
            alert.setContentText("Unexpected error: \n" + throwable.getMessage());
        } else {
            alert.setContentText(throwable.getMessage());
        }


        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(sw.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();


    }


    /**
     * Пробирается по причинам исключения и если встречает MyException, то возвращает его
     * иначе null.
     * Обрабатывает до 100 уровней вложенности
     *
     * @param t исключение
     * @return MyException или null
     */
    private MyException getGuiException(Throwable t) {
        try {
            Throwable cause = t;

            for (int i = 1; i <= 100; i++) {

                if (cause == null) return null;
                if (MyException.class.isAssignableFrom(cause.getClass())) {
                    return (MyException) cause;
                }
                cause = cause.getCause();
            }
            return null;
        } catch
                (Exception e) {
            return null;
        }
    }

}
