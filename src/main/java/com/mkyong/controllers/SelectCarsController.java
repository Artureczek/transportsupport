package com.mkyong.controllers;

import com.mkyong.controlMethods.RouteResultsMethods;
import com.mkyong.controlMethods.ViewCarsMethods;
import com.mkyong.controlMethods.ViewWorkersMethods;
import com.mkyong.main.Main;
import com.mkyong.transport.POJAZD;
import com.mkyong.transport.PRACOWNIK;
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
    public static int sumOfCapacity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        chooseListView = new ListView<>();
        pickedListView = new ListView<>();
        sumOfCapacity = 0;
        leftPane.setLeft(chooseListView);
        rightPane.setRight(pickedListView);

        backBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.SELECTSTAFF); } });

        contBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) {

            pickedList = ViewCarsMethods.getCarsByPlate(pickedListValues);
            //pickedList.stream().forEach(e-> System.out.println(e.getMarka() + " " + e.getNrRejestracji()));
            sumOfCapacity = pickedList.stream().mapToInt(POJAZD::getPojemnoscLadowni).sum();

            RouteResultsMethods.chooseCars(pickedList, SelectRouteController.objetosc ,"Koszt");

            if(SelectRouteController.objetosc>sumOfCapacity) {
                errorLbl.setVisible(true);
            }else{
                errorLbl.setVisible(false);
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

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

}
