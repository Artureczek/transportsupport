package com.mkyong.controllers;

import com.mkyong.controlMethods.ViewWorkersMethods;
import com.mkyong.controlMethods.WorkersDocumentsMethods;
import com.mkyong.main.Main;
import com.mkyong.transport.DOKUMENTPRACOWNIKA;
import com.mkyong.transport.PRACOWNIK;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
/**
 * Created by artur on 08.10.2016.
 */
public class ViewWorkersController implements Initializable, ControlledScreen {


    @FXML
    private BorderPane pane;

    @FXML
    private Label nameLbl;

    @FXML
    private Label surnameLbl;

    @FXML
    private Label peselLbl;

    @FXML
    private Label birthLbl;

    @FXML
    private Label wageLbl;

    @FXML
    private TextField nameTxtFld;

    @FXML
    private TextField surnameTxtFld;

    @FXML
    private TextField peselTxtFld;

    @FXML
    private DatePicker birthDatePick;

    @FXML
    private TextField wageTxtFld;

    @FXML
    private Button nameBttn;

    @FXML
    private Button surnameBttn;

    @FXML
    private Button peselBttn;

    @FXML
    private Button birthBttn;

    @FXML
    private Button wageBttn;

    @FXML
    private Button saveBttn;

    @FXML
    private Button backBttn;

    @FXML
    private Button deleteBttn;

    @FXML
    private Button docsBttn;

    @FXML
    private BorderPane listPane;

    @FXML
    private RadioButton isDriverRadioBttn;

    public static ListView<String> workersListView;
    public static PRACOWNIK selectedWorker;
    public static Alert usedWorkerAlert;

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        workersListView = new ListView<>();
        listPane.setCenter(workersListView);

        usedWorkerAlert = new Alert(Alert.AlertType.INFORMATION);
        usedWorkerAlert.setTitle("Uwaga");
        usedWorkerAlert.setHeaderText(null);
        usedWorkerAlert.setContentText("Przed usunieciem pracownika nalezy usunac zapisane Trasy, w ktorych bral udzial");

        nameBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { nameTxtFld.setEditable(true); }  });
        surnameBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { surnameTxtFld.setEditable(true); }  });
        peselBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { peselTxtFld.setEditable(true); }  });
        wageBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { wageTxtFld.setEditable(true); }  });
        birthBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { birthDatePick.setEditable(true); }  });
        backBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.EMPLOYMENU); } });



        docsBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) {
           // setDocsList(selectedWorker);
            myController.setScreen(Main.WORKERSDOCUMENTS);
        } });


        saveBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0)
        {


            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    selectedWorker.setImie(nameTxtFld.getText());
                    selectedWorker.setNazwisko(surnameTxtFld.getText());
                    selectedWorker.setDataUrodzenia(Date.from(birthDatePick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    selectedWorker.setPesel(peselTxtFld.getText());
                    selectedWorker.setStawka(Long.valueOf(wageTxtFld.getText()));
                    selectedWorker.setCzyKierowca(isDriverRadioBttn.isSelected());
                    ViewWorkersMethods.saveWorker(selectedWorker);
                    EmployMenuController.setWorkerList();
                }
            });

        }  });


        deleteBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0)
        {
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    ViewWorkersMethods.deleteWorker(selectedWorker);
                    EmployMenuController.setWorkerList();
                }
            });

        }  });
       


        workersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        selectedWorker = ViewWorkersMethods.getOneWorker(newValue.split(" ")[0], newValue.split(" ")[1]);
                        if(selectedWorker.getImie()!=null)
                        nameTxtFld.setText(selectedWorker.getImie());
                        if(selectedWorker.getNazwisko()!=null)
                        surnameTxtFld.setText(selectedWorker.getNazwisko());
                        if(selectedWorker.getPesel()!=null)
                        peselTxtFld.setText(selectedWorker.getPesel());
                        if(selectedWorker.getDataUrodzenia()!=null)
                        birthDatePick.setValue(selectedWorker.getDataUrodzenia().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        if(selectedWorker.getStawka()!=null)
                        wageTxtFld.setText(String.valueOf(selectedWorker.getStawka()));
                        if(selectedWorker.getCzyKierowca()!=null && selectedWorker.getCzyKierowca())
                            isDriverRadioBttn.setSelected(true);

                    }
                });

            }
        });

    }

    public static void setDocsList(PRACOWNIK pracownik){

        List<DOKUMENTPRACOWNIKA> documentPracownikaList = WorkersDocumentsMethods.getWorkersDocuments(pracownik);
        if(documentPracownikaList.size()>0) {
            List<String> docList = new ArrayList<>();
            documentPracownikaList.stream().forEach(e-> docList.add(e.getNazwadokumentu().getNazwaDokumentu()));
            ObservableList<String> items = FXCollections.observableArrayList(docList);
            WorkersDocumentsController.documentsListView.setItems(null);
            WorkersDocumentsController.documentsListView.setItems(items);
            WorkersDocumentsController.documentsListView.refresh();
            WorkersDocumentsController.documentsListView.getSelectionModel().select(0);
        }

    }

}
