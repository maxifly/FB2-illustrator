package com.maxifly.fb2_illustrator.GUI.Controllers;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.GUI.DomainModel.*;
import com.maxifly.fb2_illustrator.GUI.ExceptionHandler;
import com.maxifly.fb2_illustrator.GUI.Factory_GUI;
import com.maxifly.fb2_illustrator.GUI.GUI_Obj;
import com.maxifly.fb2_illustrator.MyException;
import com.maxifly.fb2_illustrator.MyRuntimeException;
import com.maxifly.jutils.I_Progress;
import com.maxifly.vapi.UrlCreator;
import com.maxifly.vapi.model.OwnerAlbumProject;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Maximus on 31.07.2016.
 */
public class Ctrl_Book_from_VKProj
        extends Ctrl_Book_from_Proj
        implements Initializable {

    protected static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    protected static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(Ctrl_Book_from_VKProj.class.getName());

//    private ObservableList srcTypes = FXCollections.observableArrayList("группа","пользователь","альбом");

    private DM_Book_from_VKProj dm_book_from_vkProj;

    private BooleanProperty isPrjSelected = new SimpleBooleanProperty();
    private ObjectProperty<OwnerAlbumProject> selectedProject = new SimpleObjectProperty();

    private Factory_GUI factory_gui;


    @FXML
    private ChoiceBox<String> vk_src_type;
    @FXML
    private ComboBox<String> src_addr;
    @FXML
    private ListView<OwnerAlbumProject> projects;


    @FXML
    protected void refresh(ActionEvent actionEvent) throws MyException, IOException {
        clearProjects();
        DM_Task_FindSuitableVKProj refreshTask = new DM_Task_FindSuitableVKProj(
                dm_book_from_vkProj, vk_src_type.getValue(),
                src_addr.getSelectionModel().getSelectedItem());

        GUI_Obj gui_obj = factory_gui.createProgressWindow();

        refreshTask.setProgress_monitor((I_Progress) gui_obj.dm_model);
        ((DM_ProgressWindow) gui_obj.dm_model).setTask(refreshTask);

        Stage monitorWindow = factory_gui.createModalWindow(gui_obj.node);
        monitorWindow.show();

//        load_progress.progressProperty().bind(refreshTask.progressProperty());
//        load_message.textProperty().bind(refreshTask.messageProperty());

        refreshTask.setOnSucceeded(event -> refresh_complite(event, monitorWindow));
        refreshTask.setOnFailed(event -> refresh_filed(event, monitorWindow));
        refreshTask.setOnCancelled(event -> refresh_cancelled(event, monitorWindow));
        new Thread(refreshTask).start();


    }

    @FXML
    protected void reset(ActionEvent actionEvent) throws MyException, IOException {
        dm_book_from_vkProj.reset(
                vk_src_type.getValue(),
                src_addr.getSelectionModel().getSelectedItem());
        refresh(actionEvent);
    }

    private void taskCancelled(Stage monitorWindow) {
        log.error("Task cancelled");
        monitorWindow.close();
    }

    private void taskFailed(WorkerStateEvent event, Stage monitorWindow) {
        log.error("Error {}", event.getSource().getException());
        monitorWindow.close();
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        exceptionHandler.uncaughtException(Thread.currentThread(), event.getSource().getException());

    }

    private void refresh_cancelled(WorkerStateEvent event, Stage monitorWindow) {
        taskCancelled(monitorWindow);
    }

    private void refresh_filed(WorkerStateEvent event, Stage monitorWindow) {
        taskFailed(event, monitorWindow);

    }

    private void refresh_complite(WorkerStateEvent event, Stage monitorWindow) {
        log.debug("task successed complite");
        monitorWindow.close();
        dm_book_from_vkProj.setSuitableProjects((List<OwnerAlbumProject>) event.getSource().getValue());
    }

    private void load_ill_cancelled(WorkerStateEvent event, Stage monitorWindow) {
        taskCancelled(monitorWindow);
    }

    private void load_ill_failed(WorkerStateEvent event, Stage monitorWindow) {
        taskFailed(event, monitorWindow);
    }

    private void load_ill_complite(WorkerStateEvent event, Stage monitorWindow) {
        log.debug("task successed complite");
        monitorWindow.close();
        Alert info = new Alert(Alert.AlertType.INFORMATION, "Процесс окончен.");
        info.setHeaderText(null);
        info.showAndWait();
    }


    private void changeSelectedPrj(OwnerAlbumProject newValue) {
        isPrjSelected.setValue((newValue != null));
        selectedProject.setValue(newValue);

    }

    private void clearProjects() {
        projects.getItems().remove(0, projects.getItems().size());
        isPrjSelected.setValue(false);
    }

    private boolean disableLoad() {
        return (book_src_file.getText() == null ||
                "".equals(book_src_file.getText().trim()) ||
                !isPrjSelected.getValue());
    }


    /**
     * Действия /которые надо совершить, если изменился тип вводимого адреса
     * @param newValue
     */
    private void changeAddrType(String newValue) {
        try {
            this.src_addr.getItems().clear();
            this.src_addr.getItems().addAll(dm_book_from_vkProj.getAddrs(newValue)); //TODO Добавить итемы
        } catch (MyException e) {
            throw new MyRuntimeException(e);
        }
    }

    public Ctrl_Book_from_VKProj(DM_Book_from_VKProj dm_book_from_proj, Factory_GUI factory_gui) {
        super(dm_book_from_proj);
        this.dm_book_from_vkProj = dm_book_from_proj;
        this.factory_gui = factory_gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        selectedProject.bindBidirectional(dm_book_from_vkProj.selectedProjectProperty());
        vk_src_type.getItems().addAll("группа", "пользователь", "альбом");



        projects.setItems(dm_book_from_vkProj.getSuitableProjects());
        projects.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> changeSelectedPrj(newValue)
        );
        this.book_name.textProperty().addListener(
                (observable, oldValue, newValue) -> clearProjects()
        );

        vk_src_type.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> changeAddrType(newValue)
        );
        vk_src_type.setValue("группа");


        Ctrl_Book_from_VKProj s = this;

        BooleanBinding disableLoad =
                new BooleanBinding() {

                    {
                        super.bind(
                                s.book_src_file.textProperty(),
                                s.book_dst_file.textProperty(),
                                s.isPrjSelected);
                    }

                    @Override
                    protected boolean computeValue() {
                        return disableLoad();
                    }
                };

        s.setDisableLoad(disableLoad);

    }

    @Override
    protected void load_ill() throws Exception {
        DM_Task_LoadIllVkProj load_task = new DM_Task_LoadIllVkProj(dm_book_from_vkProj);

        GUI_Obj gui_obj = factory_gui.createProgressWindow();

        load_task.setProgress_monitor((I_Progress) gui_obj.dm_model);
        ((DM_ProgressWindow) gui_obj.dm_model).setTask(load_task);

        Stage monitorWindow = factory_gui.createModalWindow(gui_obj.node);
        monitorWindow.show();

        load_task.setOnSucceeded(event -> load_ill_complite(event, monitorWindow));
        load_task.setOnFailed(event -> load_ill_failed(event, monitorWindow));
        load_task.setOnCancelled(event -> load_ill_cancelled(event, monitorWindow));
        new Thread(load_task).start();
    }

    @Override
    protected void clear_form() {
        super.clear_form();
        clearProjects();
    }
}
