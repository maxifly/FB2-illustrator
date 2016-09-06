package com.maxifly.fb2_illustrator.GUI.Controllers;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.IllChangeOrder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.effect.SepiaTone;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maxim.Pantuhin on 06.09.2016.
 */
public class Ctrl_IcoDelimiter extends Ctrl_Abstract implements Initializable {
    public static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    public static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(Ctrl_IcoDelimiter.class.getName());

    @FXML
    Label delimiterText;
    @FXML
    Line delimLine;

    private String ill_before_id = null;
    private Effect dragEnterEffect = new SepiaTone();


    @FXML
    protected void drag_entered(DragEvent dragEvent) {
        log.debug("drag_entered ");
        if (dragSuitable(dragEvent)
                && dragEvent.getGestureSource() != dragEvent.getSource()) {
            log.debug("set effect");

//            ((Node)dragEvent.getSource()).setStyle(
//                    "-fx-border-style: solid outside;"
//                            + "-fx-border-color: blue;"
//                            + "-fx-border-width: 4;");
//            ((Node)dragEvent.getSource()).setEffect(dragEnterEffect);
            Integer draggedIllId = Integer.valueOf(getDraggedIllId(dragEvent));

            if (Integer.valueOf(ill_before_id) != draggedIllId + 1 &&
                    Integer.valueOf(ill_before_id) != draggedIllId) {
                delimLine.setStroke(Color.RED);
            }

        }
        dragEvent.consume();
    }

    @FXML
    protected void drag_exited(DragEvent dragEvent) {
        if (dragSuitable(dragEvent)
                && dragEvent.getGestureSource() != dragEvent.getSource()) {
            delimLine.setStroke(Color.web("#d7d7d7"));
        }
        dragEvent.consume();
    }

    @FXML
    protected void drag_over(DragEvent dragEvent) {
        log.debug("drag over");
        if (dragSuitable(dragEvent)
                && dragEvent.getGestureSource() != dragEvent.getSource()
                ) {
            Integer draggedIllId = Integer.valueOf(getDraggedIllId(dragEvent));
            if (Integer.valueOf(ill_before_id) != draggedIllId + 1 &&
                    Integer.valueOf(ill_before_id) != draggedIllId) {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
            }
        }
        dragEvent.consume();
    }

    @FXML
    protected void drag_dropped(DragEvent dragEvent) {
        log.debug("drug dropped");
        boolean success = false;
        if (dragSuitable(dragEvent)
                && dragEvent.getGestureSource() != dragEvent.getSource()) {

            Integer draggedIllId = Integer.valueOf(getDraggedIllId(dragEvent));
            if (Integer.valueOf(ill_before_id) != draggedIllId + 1 &&
                    Integer.valueOf(ill_before_id) != draggedIllId) {

                // В клипбоард вставим информацию о том, какой объект и куда переместился
                IllChangeOrder illChangeOrder = new IllChangeOrder(getDraggedIllId(dragEvent), ill_before_id);
                ClipboardContent content = new ClipboardContent();
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                String json = gson.toJson(illChangeOrder);
                content.putString(Constants.drag_string + json);
                dragEvent.getDragboard().setContent(content);
                success = true;
            }
        }
        dragEvent.setDropCompleted(success);

        // Теперь будет вызвано такое же событие у родительского объекта, который отвечает за проект.
        // Он и обработает перемещение.

    }

    /**
     * Определяет подходит ли объект для драг анд дропа или нет
     *
     * @param dragEvent
     * @return
     */
    private boolean dragSuitable(DragEvent dragEvent) {
        Object gesture = dragEvent.getGestureSource();
//        Boolean b = ((gesture instanceof Node)
//                && dragEvent.getDragboard().hasString()
//                && dragEvent.getDragboard().getString().indexOf(Constants.drag_string) == 0);
//
//        log.debug("dragSuitable " + b);
        return (gesture instanceof Node)
                && dragEvent.getDragboard().hasString()
                && dragEvent.getDragboard().getString().indexOf(Constants.drag_string) == 0;
    }

    private String getDraggedIllId(DragEvent dragEvent) {
        String st = dragEvent.getDragboard().getString();
        return st.substring(Constants.drag_string.length());
    }

    public void setIll_before_id(String ill_before_id) {
        this.ill_before_id = ill_before_id;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        delimLine.setStroke(Color.web("#d7d7d7"));
    }
}
