package com.mkyong.controllers;

import com.mkyong.main.Main;
import com.mkyong.transport.TRASA;
import com.mkyong.transport.TRASAPOJAZD;
import com.mkyong.transport.TRASAPRACOWNIK;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import org.jpedal.parser.text.TR;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by akielbiewski on 14.10.2016.
 */
public class ViewRoutesController implements Initializable, ControlledScreen  {

    ScreensController myController;

    public static ListView<String> routesListView;

    @FXML
    private BorderPane pane;

    @FXML
    private Label startLbl;

    @FXML
    private Label endLbl;

    @FXML
    private Label prioLbl;

    @FXML
    private Label fuelCostLbl;

    @FXML
    private Button backBttn;

    @FXML
    private Button deleteBttn;

    @FXML
    private Button mapBttn;

    @FXML
    private ListView<String> driversListView;

    @FXML
    private ListView<String> carsListView;

    @FXML
    private Label distanceLbl;

    public static List<TRASA> trasaList;
    public static TRASA selectedTrasa;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trasaList = new ArrayList<>();
        routesListView = new ListView<>();
        pane.setLeft(routesListView);

        routesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for(TRASA trasa : trasaList){
                    if(trasa.getLokacjaStartowa().equals(newValue.split("-")[0]) && trasa.getLokacjaKoncowa().equals(newValue.split("-")[1])) {
                        selectedTrasa = trasa;
                        startLbl.setText("Lokacja Startowa: " + selectedTrasa.getLokacjaStartowa());
                        endLbl.setText("Lokacja Koncowa: " + selectedTrasa.getLokacjaKoncowa());
                        distanceLbl.setText("Odleglosc: " + selectedTrasa.getOdleglosc());
                        prioLbl.setText("Priorytet Trasy: " + selectedTrasa.getPriorytet());
                        Double cost = selectedTrasa.getPojazdy().stream().mapToDouble(TRASAPOJAZD::getKosztPaliwa).sum();
                        fuelCostLbl.setText("Koszt Paliwa: " + cost.intValue());

                        List<String> carsString = new ArrayList<String>();
                        for(TRASAPOJAZD trasapojazd: trasa.getPojazdy())
                            carsString.add(trasapojazd.getPojazd().getMarka() + " " + trasapojazd.getPojazd().getNrRejestracji());
                        ObservableList<String> carsItems = FXCollections.observableArrayList (carsString);
                        carsListView.setItems(carsItems);

                        List<String> driversString = new ArrayList<String>();
                        for(TRASAPRACOWNIK trasapojazd: trasa.getKierowcy())
                            driversString.add(trasapojazd.getPracownik().getImie() + " " + trasapojazd.getPracownik().getNazwisko());
                        ObservableList<String> driversItems = FXCollections.observableArrayList (driversString);
                        driversListView.setItems(driversItems);

                    }
                }


            }
        });

        backBttn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0)
            {
                myController.setScreen(Main.ROUTEMENU);
            }
        });

        mapBttn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0)
            {
                myController.setScreen(Main.VIEWROUTESMAP);
            }
        });

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

}
