package com.mkyong.controllers;

import com.mkyong.controlMethods.RouteResultsMethods;
import com.mkyong.controlMethods.ViewWorkersMethods;
import com.mkyong.main.Main;
import com.mkyong.transport.*;
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
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class RouteResultsController implements Initializable, ControlledScreen
{

    ScreensController myController;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button backBttn;

    @FXML
    private Button saveBttn;

    @FXML
    private Button endBttn;

    public static Label fuelLabel;
    public static Label singleFuelLabel;
    public static Label driversPerCarLabel;
    public static List<POJAZD> selectedCars;
    public static List<PRACOWNIK> selectedDrivers;
    public static ListView<String> selectedCarsListView;
    public static ListView<String> selectedDriversListView;
    public static TRASA trasa;
    public static List<TRASAPOJAZD> trasapojazdList;
    public static List<TRASAPRACOWNIK> trasapracownikList;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        fuelLabel = new Label();
        fuelLabel.setText("Calkowity Koszt Paliwa: ");
        fuelLabel.setLayoutY(103);
        fuelLabel.setLayoutX(608);
        fuelLabel.setPrefHeight(21);
        fuelLabel.setPrefWidth(264);

        singleFuelLabel = new Label();
        singleFuelLabel.setText("Koszt Paliwa Pojazdu: ");
        singleFuelLabel.setLayoutY(135);
        singleFuelLabel.setLayoutX(608);
        singleFuelLabel.setPrefHeight(21);
        singleFuelLabel.setPrefWidth(264);

        driversPerCarLabel = new Label();
        driversPerCarLabel.setText("Liczba kierowcow na 1 pojzd: ");
        driversPerCarLabel.setLayoutY(167);
        driversPerCarLabel.setLayoutX(608);
        driversPerCarLabel.setPrefHeight(21);
        driversPerCarLabel.setPrefWidth(264);

        pane.getChildren().addAll(fuelLabel, singleFuelLabel, driversPerCarLabel);
        trasapojazdList = new ArrayList<>();
        trasapracownikList = new ArrayList<>();
        selectedCarsListView = new ListView<>();
        selectedDriversListView = new ListView<>();
        pane.getChildren().addAll(selectedCarsListView, selectedDriversListView);
        
        selectedDriversListView.setLayoutX(78);
        selectedDriversListView.setLayoutY(149);
        selectedDriversListView.setPrefWidth(200);
        selectedDriversListView.setPrefHeight(309);

        selectedCarsListView.setLayoutX(278);
        selectedCarsListView.setLayoutY(149);
        selectedCarsListView.setPrefWidth(200);
        selectedCarsListView.setPrefHeight(309);
        
        backBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.SELECTCARS); } });
        saveBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) {
            RouteResultsMethods.saveRoute(trasa,trasapojazdList,trasapracownikList); } });
        endBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.ROUTEMENU); } });
        selectedCarsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {

                        POJAZD pojazd;
                        for(TRASAPOJAZD trasapojazd: trasapojazdList){
                            if(trasapojazd.getPojazd().getNrRejestracji().equals(selectedCarsListView.getSelectionModel().getSelectedItem().split(" ")[1]))
                                singleFuelLabel.setText("Koszt Paliwa Pojazdu: " + trasapojazd.getKosztPaliwa().intValue() + "zl");
                        }
                    }
                });

            }
        });
    }

    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

}
