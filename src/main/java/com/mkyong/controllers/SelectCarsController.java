package com.mkyong.controllers;

import com.mkyong.controlMethods.RouteResultsMethods;
import com.mkyong.controlMethods.ViewCarsMethods;
import com.mkyong.controlMethods.ViewWorkersMethods;
import com.mkyong.main.Main;
import com.mkyong.transport.POJAZD;
import com.mkyong.transport.PRACOWNIK;
import com.mkyong.transport.TRASAPOJAZD;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by akielbiewski on 12.10.2016.
 */
public class SelectCarsController implements Initializable, ControlledScreen {


    ScreensController myController;

    @FXML
    private BorderPane pane;

    @FXML
    private BorderPane leftPane;


    @FXML
    private Label addLbl;

    @FXML
    private Button addBttn;

    @FXML
    private Button contBttn;

    @FXML
    private BorderPane rightPane;



    @FXML
    private Label deleteLbl;

    @FXML
    private Button deleteBttn;

    @FXML
    private AnchorPane topPane;

    @FXML
    private Label titleLbl;

    @FXML
    private Label pickLbl;

    @FXML
    private Label pickedLbl;

    @FXML
    private Button backBttn;

    @FXML
    private Label errorLbl;

    public static ListView<String> pickedListView;
    public static ListView<String> chooseListView;
    public static List<POJAZD> pickedList;
    public static List<POJAZD> chooseList;
    public static List<String> pickedListValues;
    public static List<String> chooseListValues;
    public static ObservableList<String> chooseItems;
    public static ObservableList<String> pickedItems;
    public static Long sumOfCapacity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        chooseListView = new ListView<>();
        pickedListView = new ListView<>();
        sumOfCapacity = 0L;
        leftPane.setLeft(chooseListView);
        rightPane.setRight(pickedListView);

        backBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.SELECTSTAFF); } });

        contBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) {

            pickedList = ViewCarsMethods.getCarsByPlate(pickedListValues);
            //pickedList.stream().forEach(e-> System.out.println(e.getMarka() + " " + e.getNrRejestracji()));
            sumOfCapacity = pickedList.stream().mapToLong(POJAZD::getPojemnoscLadowni).sum();

            RouteResultsController.selectedCars = RouteResultsMethods.chooseCars(pickedList, SelectRouteController.objetosc , SelectRouteController.priorytet, SelectStaffController.pickedList.size());
            RouteResultsController.selectedDrivers = RouteResultsMethods.chooseDrivers(SelectStaffController.pickedList, RouteResultsController.selectedCars, SelectRouteController.priorytet);

            if(SelectRouteController.objetosc>sumOfCapacity) {
                errorLbl.setText("Laczna pojemnosc wybranych pojazdow jest mniejsza niz wpisana objetosc towaru!");
                errorLbl.setVisible(true);
            }else if(RouteResultsController.selectedCars == null) {
                errorLbl.setText("Brak mozliwosci obsadzenia wystarczajacej liczby pojazdow!");
                errorLbl.setVisible(true);
            }else{
                errorLbl.setVisible(false);
                setPickedList();
                RouteResultsMethods.setDBObjects();
                Double fuel = RouteResultsController.trasapojazdList.stream().mapToDouble(TRASAPOJAZD::getKosztPaliwa).sum();
                RouteResultsController.fuelLabel.setText("Calkowity Koszt Paliwa: " + fuel.intValue() + " zl");
                RouteResultsController.driversPerCarLabel.setText("Liczba kierowcow na 1 pojazd: " + (RouteResultsController.selectedDrivers.size()/RouteResultsController.selectedCars.size()));
                myController.setScreen(Main.ROUTERESULTS);
            }


        } });

        chooseListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                addBttn.setDisable(false);
            }
        });

        pickedListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                deleteBttn.setDisable(false);
            }
        });


        addBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) {

            String selected = chooseListView.getSelectionModel().getSelectedItem();
            if(selected!=null) {
                pickedListValues.add(selected);
                chooseListValues.remove(selected);
                chooseListView.getItems().remove(selected);
                pickedListView.getItems().add(selected);
            }
        } });

        deleteBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) {

            String selected = pickedListView.getSelectionModel().getSelectedItem();
            if(selected!=null) {
                chooseListValues.add(selected);
                pickedListValues.remove(selected);
                pickedListView.getItems().remove(selected);
                chooseListView.getItems().add(selected);
            }
        } });


    }

    public static void setPickedList(){

        List<String> workersStringList = new ArrayList<>();
        List<String> carsStringList = new ArrayList<>();
        RouteResultsController.selectedCars.stream().forEach(e-> carsStringList.add(e.getMarka() + " " + e.getNrRejestracji()));
        ObservableList <String> carsItems = FXCollections.observableArrayList (carsStringList);
        RouteResultsController.selectedCarsListView.setItems(carsItems);

        RouteResultsController.selectedDrivers.stream().forEach(e-> workersStringList.add(e.getImie() + " " + e.getNazwisko()));
        ObservableList <String> workersItems = FXCollections.observableArrayList (workersStringList);
        RouteResultsController.selectedDriversListView.setItems(workersItems);

    }



    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

}
