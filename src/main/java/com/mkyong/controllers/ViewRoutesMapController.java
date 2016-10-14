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
import com.mkyong.main.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewRoutesMapController implements Initializable, ControlledScreen, MapComponentInitializedListener,
        ElevationServiceCallback, GeocodingServiceCallback, DirectionsServiceCallback {

    ScreensController myController;


    @FXML
    private BorderPane pane;

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button backBttn;

    @FXML
    private Button finishBttn;

    public static MapOptions mapOptions;
    public static GoogleMapView mapView;
    public static GoogleMap map;
    protected DirectionsPane directions;
    protected DirectionsService ds;
    protected DirectionsRequest dr;
    public static String from;
    public static String to;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);
        pane.setCenter(mapView);

        backBttn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0)
            {
                myController.setScreen(Main.VIEWROUTES);
            }
        });
        finishBttn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0)
            {
                myController.setScreen(Main.ROUTEMENU);
            }
        });

    }


    DirectionsRenderer renderer;

    public void selectRoute() {
        ds = new DirectionsService();

        try {
            dr = new DirectionsRequest(
                    from,
                    to,
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


    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
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
}
