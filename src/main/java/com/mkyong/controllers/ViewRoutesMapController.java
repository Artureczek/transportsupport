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

public class ViewRoutesMapController implements Initializable, ControlledScreen {

    ScreensController myController;


    @FXML
    private BorderPane pane;

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button backBttn;

    @FXML
    private Button finishBttn;

    public static String from;
    public static String to;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
       // pane.setCenter(mapView2);

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





    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }


}
