package com.mkyong.controllers;

import com.mkyong.transport.PRACOWNIK;
import com.mkyong.transport.TRASA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.mkyong.main.*;
import com.mkyong.controlMethods.*;
/**
 * Created by akielbiewski on 09.08.2016.
 */
public class RouteMenuController implements Initializable, ControlledScreen {


    ScreensController myController;

    @FXML
    private Button viewRoutesBttn;

    @FXML
    private Button backBttn;

    @FXML
    private Label newRouteLbl;

    @FXML
    private Label viewRoutesLbl;

    @FXML
    private Button newRouteBttn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {



        newRouteBttn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0)
            {
                myController.setScreen(Main.SELECTROUTE);
            }
        });



        backBttn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0)
            {
                myController.setScreen(Main.MAINSCREEN);
            }
        });

        viewRoutesBttn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0)
            {
                setRoutesList();
                myController.setScreen(Main.VIEWROUTES);
            }
        });
    }


    public static void setRoutesList(){

        ViewRoutesController.trasaList = ViewRoutesMethods.getAllRoutes();
        List<String> imieNaziwskoList = new ArrayList<>();
        ViewRoutesController.trasaList.stream().forEach(e-> imieNaziwskoList.add(e.getLokacjaStartowa() + "-" + e.getLokacjaKoncowa()));
        ObservableList<String> items = FXCollections.observableArrayList (imieNaziwskoList);
        ViewRoutesController.routesListView.setItems(null);
        ViewRoutesController.routesListView.setItems(items);
        ViewRoutesController.routesListView.refresh();
        if(ViewRoutesController.routesListView.getItems().size()>0)
            ViewRoutesController.routesListView.getSelectionModel().select(0);

    }




    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

}
