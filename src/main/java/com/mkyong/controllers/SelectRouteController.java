package com.mkyong.controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.elevation.ElevationResult;
import com.lynden.gmapsfx.service.elevation.ElevationServiceCallback;
import com.lynden.gmapsfx.service.elevation.ElevationStatus;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;
import com.mkyong.transport.PRACOWNIK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.mkyong.main.*;
import com.mkyong.controlMethods.*;
/**
 * Created by akielbiewski on 09.08.2016.
 */
public class SelectRouteController implements Initializable, ControlledScreen, MapComponentInitializedListener,
        ElevationServiceCallback, GeocodingServiceCallback, DirectionsServiceCallback {

    @FXML
    private BorderPane pane;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private Button showBttn;

    @FXML
    private RadioButton prioTimeBttn;

    @FXML
    private RadioButton prioCostBttn;

    @FXML
    private Button continueBttn;

    @FXML
    private Label distanceLabel;

    @FXML
    private Label prioLabel;

    @FXML
    private TextField sizeTxtFld;

    @FXML
    private Button backBttn;

    ScreensController myController;
    final ToggleGroup group = new ToggleGroup();

    public static MapOptions mapOptions;
    public static GoogleMapView mapView;
    public static Double distance;
    public static GoogleMap map;
    protected DirectionsPane directions;
    protected DirectionsService ds;
    protected DirectionsRequest dr;
    public static String startLocation;
    public static String endLocation;
    public static String priorytet;
    public static int objetosc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);

        pane.setCenter(mapView);
        prioCostBttn.setToggleGroup(group);
        prioTimeBttn.setToggleGroup(group);

        showBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                sizeTxtFld.setDisable(false);
                selectRoute();
            }
        });

        fromTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!="")
                toTextField.setDisable(false);
            else
                toTextField.setDisable(true);
        });

        toTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!="" && fromTextField.getText()!="")
                showBttn.setDisable(false);
            else
                showBttn.setDisable(true);
        });

        sizeTxtFld.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!="")
                continueBttn.setDisable(false);
            else
                continueBttn.setDisable(true);
        });

        backBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.ROUTEMENU); } });
        continueBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) {
            setWorkerList();
            myController.setScreen(Main.SELECTSTAFF);
            startLocation = fromTextField.getText();
            endLocation = toTextField.getText();
            distance = Double.valueOf(distanceLabel.getText().replace(" km", "").replace("Odleglosc: ", "").replace(" ",""));
            priorytet = ((RadioButton) group.getSelectedToggle()).getText();
            objetosc = Integer.valueOf(sizeTxtFld.getText());
            System.out.println(startLocation + " " + endLocation + " " + priorytet);

        } });

    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }


    DirectionsRenderer renderer;

    @Override
    public void mapInitialized() {

        //Set the initial properties of the map.
        mapOptions = new MapOptions();
        mapOptions.center(new LatLong(52.232222, 21.008333))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions, true);
        directions = mapView.getDirec();
        renderer = new DirectionsRenderer(true, map, directions);
        mapView.getMap().hideDirectionsPane();

    }

    public void selectRoute() {
        ds = new DirectionsService();

        try {
            dr = new DirectionsRequest(
                    fromTextField.getText(),
                    toTextField.getText(),
                    TravelModes.DRIVING
            );

            ds.getRoute(dr, this, renderer);

            renderer.setMap(map);
            renderer.setPanel(directions);

        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void directionsReceived(DirectionsResult directionsResult, DirectionStatus directionStatus) {

    }

    @Override
    public void elevationsReceived(ElevationResult[] elevationResults, ElevationStatus elevationStatus) {
    }

    @Override
    public void geocodedResultsReceived(GeocodingResult[] geocodingResults, GeocoderStatus geocoderStatus) {

    }

    public static void setWorkerList(){

        SelectStaffController.chooseList = ViewWorkersMethods.getWorkers(true);
        SelectStaffController.chooseListValues = new ArrayList<>();
        SelectStaffController.pickedListValues = new ArrayList<>();
        SelectStaffController.pickedList = new ArrayList<>();
        SelectStaffController.chooseList.stream().forEach(e-> SelectStaffController.chooseListValues.add(e.getImie() + " " + e.getNazwisko()));
        SelectStaffController.chooseItems = FXCollections.observableArrayList (SelectStaffController.chooseListValues);
        SelectStaffController.chooseListView.setItems(SelectStaffController.chooseItems);
        SelectStaffController.pickedItems = FXCollections.observableArrayList (SelectStaffController.pickedListValues);
        SelectStaffController.pickedListView.setItems(SelectStaffController.pickedItems);

    }
}

