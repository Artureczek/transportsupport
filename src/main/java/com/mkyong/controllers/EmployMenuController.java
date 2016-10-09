package com.mkyong.controllers;

import com.mkyong.transport.PRACOWNIK;
import com.sun.glass.ui.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.mkyong.main.*;
import com.mkyong.controlMethods.*;
public class EmployMenuController implements Initializable, ControlledScreen
{

	ScreensController myController;


    @FXML
    private Label addEmplLabel;

    @FXML
    private Label viewEmplLabel;

    @FXML
    private Button addEmplBttn;

    @FXML
    private Button viewEmplBttn;

    @FXML
    private Button addCarBttn;

    @FXML
    private Button viewCarBttn;

    @FXML
    private Label addCarLabel;

    @FXML
    private Label viewCarLabel;

	@FXML
	private Button backBttn;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
		addEmplBttn.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	 myController.setScreen(Main.ADDEMPLOYEE); 
	        }
	    });


		viewEmplBttn.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0)  {

				setList();
				myController.setScreen(Main.VIEWWORKERS); }
	    });
		
		addCarBttn.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	 myController.setScreen(Main.ADDCAR); 
	        }
	    });
		
		viewCarBttn.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	 myController.setScreen(Main.ADDEMPLOYEE); 
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
		
		
		
		
		
	}

	public static void setList(){

		List<PRACOWNIK> pracownikList = ViewWorkersMethods.getWorkers();
		List<String> imieNaziwskoList = new ArrayList<>();
		pracownikList.stream().forEach(e-> imieNaziwskoList.add(e.getImie() + " " + e.getNazwisko()));
		ObservableList<String> items = FXCollections.observableArrayList (imieNaziwskoList);
		ViewWorkersController.workersListView.setItems(null);
		ViewWorkersController.workersListView.setItems(items);
		ViewWorkersController.workersListView.refresh();

	}
	
	
	
	
	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage; 		
	}

}
