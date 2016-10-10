package com.mkyong.controllers;

import com.mkyong.controlMethods.ViewCarsMethods;
import com.mkyong.main.Main;
import com.mkyong.transport.POJAZD;
import com.mkyong.transport.PRACOWNIK;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Created by artur on 08.10.2016.
 */
public class ViewCarsController implements Initializable, ControlledScreen {



    @FXML
    private BorderPane pane;

    @FXML
    private Label markLbl;

    @FXML
    private Label modelLbl;

    @FXML
    private Label engineLbl;

    @FXML
    private TextField tankCapacityTxtFld;

    @FXML
    private Label loadCapacityLbl;

    @FXML
    private TextField markTxtFld;

    @FXML
    private TextField modelTxtFld;

    @FXML
    private TextField loadCapacityTxtFld;

    @FXML
    private Button markBttn;

    @FXML
    private Button modelBttn;

    @FXML
    private Button engineBttn;

    @FXML
    private Button tankCapacityBttn;

    @FXML
    private Button loadCapacityBttn;

    @FXML
    private Button saveBttn;

    @FXML
    private Button backBttn;

    @FXML
    private Label avgConsLbl;

    @FXML
    private TextField engineTxtFld;

    @FXML
    private TextField avgConsTxtFld;

    @FXML
    private Button avgConsBttn;

    @FXML
    private BorderPane listPane;
    
    @FXML
    private Label registryLbl;

    @FXML
    private TextField registryTxtFld;

    @FXML
    private Button registryBttn;

    @FXML
    private Button deleteBttn;

    public static ListView<String> carsListView;
    public static POJAZD selectedCar;

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        carsListView = new ListView<>();
        listPane.setCenter(carsListView);

        markBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { markTxtFld.setEditable(true); }  });
        modelBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { modelTxtFld.setEditable(true); }  });
        engineBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { engineTxtFld.setEditable(true); }  });
        tankCapacityBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { tankCapacityTxtFld.setEditable(true); }  });
        loadCapacityBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { loadCapacityTxtFld.setEditable(true); }  });
        avgConsBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { avgConsTxtFld.setEditable(true); }  });
        backBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.EMPLOYMENU); } });
        saveBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0)
        {


            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    selectedCar.setMarka(markTxtFld.getText());
                    selectedCar.setModel(modelTxtFld.getText());
                    selectedCar.setSilnik(engineTxtFld.getText());
                    selectedCar.setPojemnoscBaku(Integer.valueOf(tankCapacityTxtFld.getText()));
                    selectedCar.setPojemnoscLadowni(Integer.valueOf(loadCapacityTxtFld.getText()));
                    selectedCar.setSrednieSpalanie(Float.valueOf(avgConsTxtFld.getText()));
                    selectedCar.setNrRejestracji(registryTxtFld.getText());
                    ViewCarsMethods.saveCar(selectedCar);
                    EmployMenuController.setWorkerList();
                }
            });

        }  });

        deleteBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0)
        {
            Platform.runLater(new Runnable(){
                @Override
                public void run() {

                    ViewCarsMethods.deleteCar(selectedCar);
                    EmployMenuController.setCarList();
                }
            });

        }  });



        carsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        selectedCar = ViewCarsMethods.getOneCar(newValue.split(" ")[1]);
                        if(selectedCar.getNrRejestracji()!=null)
                            registryTxtFld.setText(selectedCar.getNrRejestracji());
                        if(selectedCar.getMarka()!=null)
                            markTxtFld.setText(selectedCar.getMarka());
                        if(selectedCar.getModel()!=null)
                            modelTxtFld.setText(selectedCar.getModel());
                        if(selectedCar.getSilnik()!=null)
                            engineTxtFld.setText(selectedCar.getSilnik());
                            tankCapacityTxtFld.setText(String.valueOf(selectedCar.getPojemnoscBaku()));
                            loadCapacityTxtFld.setText(String.valueOf(selectedCar.getPojemnoscLadowni()));
                        if(selectedCar.getSrednieSpalanie()!=null)
                            avgConsTxtFld.setText(String.valueOf(selectedCar.getSrednieSpalanie()));


                    }
                });

            }
        });

    }

}
