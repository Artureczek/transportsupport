package com.mkyong.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.mkyong.main.*;
import com.mkyong.controlMethods.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML
	private Label incorrectLabel;

	@FXML
	private PasswordField passTxtField;

	@FXML
	private Label headLabel;

	@FXML
	private Label noAccLabel;

	@FXML
	private Button createButton;

	@FXML
	private Label passLabel;

	@FXML
	private TextField nameTxtField;

	@FXML
	private Button logButton;

	@FXML
	private Label nameLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
		logButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {


	        	//LoginMethods log = new LoginMethods(nameTxtField.getText(), passTxtField.getText());
	        	 if(LoginMethods.validate(nameTxtField.getText(), passTxtField.getText()))
	        		 myController.setScreen(Main.MAINSCREEN); 	 
	        	 else 
	        		 incorrectLabel.setVisible(true);

				System.out.println(Main.activeUser);


			}
	    });
		
		
		
		createButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	           myController.setScreen(Main.CREATEUSER); 	 
	        }
	    });
		
		
		
		
		
		
	}

	
	
	
	
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		
		myController = screenPage; 
		
	}
	
	
	
	
	
}
