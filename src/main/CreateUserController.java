package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateUserController implements Initializable, ControlledScreen {

	ScreensController myController; 
	
	
    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label mailLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField mailTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passTextField;

    @FXML
    private Button backButton;

    @FXML
    private Button createButton;

    @FXML
    private Label repeatPassLabel;

    @FXML
    private TextField repeatPassTextField;
    
    @FXML
    private Label incorrectTextField;

	

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
	        	 CreateUserMethods CUM = new CreateUserMethods(nameTextField.getText(), surnameTextField.getText(),
	        	 mailTextField.getText(), usernameTextField.getText(), passTextField.getText(), repeatPassTextField.getText());
	        	
	        	 if(CUM.Validate())
	        	 myController.setScreen(Main.MAINSCREEN); 
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
