package com.mkyong.controllers;

import com.mkyong.main.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
public class RouteResultsController implements Initializable, ControlledScreen
{

    ScreensController myController;

    @FXML
    private Button backBttn;

    @FXML
    private Button saveBttn;

    @FXML
    private Button endBttn;

    @FXML
    private Label fuelLbl;



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        backBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.SELECTCARS); } });
        endBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.ROUTEMENU); } });


    }

    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

}
