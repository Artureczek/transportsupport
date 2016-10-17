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
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Optional;
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

    @FXML
    private ChoiceBox<String> fuelChoiceBox;

    @FXML
    private Button fuelBttn;

    public static ListView<String> carsListView;
    public static POJAZD selectedCar;
    public static Alert usedCarAlert;

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        carsListView = new ListView<>();
        listPane.setCenter(carsListView);
        fuelChoiceBox.getItems().addAll("ON", "e95", "e98", "LPG");

        usedCarAlert = new Alert(Alert.AlertType.INFORMATION);
        usedCarAlert.setTitle("Uwaga");
        usedCarAlert.setHeaderText(null);
        usedCarAlert.setContentText("Przed usunieciem pojazdu nalezy usunac zapisane Trasy, w ktorych bral udzial");

       /* Optional<ButtonType> result = usedCarAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
        } else {
            // ... user chose CANCEL or closed the dialog
        }*/


        markBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { markTxtFld.setEditable(true); }  });
        fuelBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { fuelChoiceBox.setDisable(false); }  });
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
                    selectedCar.setSilnik(Double.valueOf(engineTxtFld.getText()));
                    selectedCar.setPojemnoscBaku(Long.valueOf(tankCapacityTxtFld.getText()));
                    selectedCar.setPojemnoscLadowni(Long.valueOf(loadCapacityTxtFld.getText()));
                    selectedCar.setSrednieSpalanie(Double.valueOf(avgConsTxtFld.getText()));
                    selectedCar.setNrRejestracji(registryTxtFld.getText());
                    selectedCar.setRodzajPaliwa(fuelChoiceBox.getSelectionModel().getSelectedItem());
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
                            engineTxtFld.setText(String.valueOf(selectedCar.getSilnik()));
                            tankCapacityTxtFld.setText(String.valueOf(selectedCar.getPojemnoscBaku()));
                            loadCapacityTxtFld.setText(String.valueOf(selectedCar.getPojemnoscLadowni()));
                        if(selectedCar.getSrednieSpalanie()!=null)
                            avgConsTxtFld.setText(String.valueOf(selectedCar.getSrednieSpalanie()));

                        if(selectedCar.getRodzajPaliwa()!=null){
                            for(String fuel : fuelChoiceBox.getItems()){
                                if(selectedCar.getRodzajPaliwa().equals(fuel))
                                    fuelChoiceBox.getSelectionModel().select(fuel);
                            }
                        }


                    }
                });

            }
        });

    }

}
