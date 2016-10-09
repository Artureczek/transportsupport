package com.mkyong.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.mkyong.main.*;
import com.mkyong.controlMethods.*;
public class EmployeePartBController implements Initializable, ControlledScreen {

	ScreensController myController;
		
	 @FXML
	    private Label regulaminLabel;

	    @FXML
	    private Label BHBLabel;

	    @FXML
	    private Label zamiaryRodzicielskieLabel;

	    @FXML
	    private Label kontoBankoweLabel;

	    @FXML
	    private Label PIT2Label;

	    @FXML
	    private Button regulaminButton;

	    @FXML
	    private Button BHPButton;

	    @FXML
	    private Button zamiaryRodzicielskieButton;

	    @FXML
	    private Button kontoBankoweButton;

	    @FXML
	    private Button PIT2Button;

	    @FXML
	    private Button continueButton;

	    @FXML
	    private Button backButton;

	    @FXML
	    private Label traktowanieLabel;

	    @FXML
	    private Button traktowanieButton;

	    @FXML
	    private Label zakazKonkurencjiLabel;

	    @FXML
	    private Button zakazKonkurencjiButton;

	    @FXML
	    private Label urlopLabel;

	    @FXML
	    private Button urlopButton;

	    @FXML
	    private Label umowaLabel;

	    @FXML
	    private Button umowaButton;	
	
	    
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1) 
		{
			
	    	continueButton.setOnAction(new EventHandler<ActionEvent>() 
			{
		        @Override
		        public void handle(ActionEvent arg0) 
		        {
		        	 myController.setScreen(Main.EMPLOYEEPARTC); 
		        }
		    });		
			
			backButton.setOnAction(new EventHandler<ActionEvent>() 
			{
		        @Override
		        public void handle(ActionEvent arg0) 
		        {
		        	
		        	 myController.setScreen(Main.EMPLOYEEPARTA); 
		        	 
		        }
		    });
			
		}
			
			
			
			
			
		

		
		@Override
		public void setScreenParent(ScreensController screenPage) 
		{
			myController = screenPage;
			
		}
}
