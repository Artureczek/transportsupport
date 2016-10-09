package com.mkyong.controllers;

import com.mkyong.transport.APPUSER;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import com.mkyong.main.*;
import com.mkyong.controlMethods.*;
public class CreateUserController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML
	private TextField mailTextField;

	@FXML
	private Label repeatPassLabel;

	@FXML
	private Label surnameLabel;

	@FXML
	private TextField nameTextField;

	@FXML
	private Label passLabel;

	@FXML
	private Label usernameLabel;

	@FXML
	private PasswordField passTextField;

	@FXML
	private TextField surnameTextField;

	@FXML
	private Label correctLabel;

	@FXML
	private Label mailLabel;

	@FXML
	private Label incorrectTextField;

	@FXML
	private Button backButton;

	@FXML
	private Button createButton;

	@FXML
	private Label nameLabel;

	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField repeatPassTextField;

	

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		backButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	 myController.setScreen(Main.LOGIN); 
	        }
	    });
		
		createButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {

	        	APPUSER user = new APPUSER();
				user.setimie(nameTextField.getText());
				user.setnazwaUzytkownika(usernameTextField.getText());
				user.setnazwisko(surnameTextField.getText());
				user.setEmail(mailTextField.getText());
				user.setHaslo(passTextField.getText());


				boolean result = CreateUserMethods.createUser(user, repeatPassTextField.getText());


				if(result)
					correctLabel.setVisible(true);
	        	else
	        		incorrectTextField.setVisible(true);
	        }
	    });
		
		
		
		
	}
    
    
	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage; 
	}
    
    

}
