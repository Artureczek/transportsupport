package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

public class AddCarController implements Initializable, ControlledScreen{

	ScreensController myController;

	  @FXML
	    private Label markaLabel;

	    @FXML
	    private Label modelLabel;

	    @FXML
	    private Label silnikLabel;

	    @FXML
	    private Label bakLabel;

	    @FXML
	    private TextField markaTextField;

	    @FXML
	    private TextField modelTextField;

	    @FXML
	    private TextField silnikTextField;

	    @FXML
	    private TextField bakTextField;

	    @FXML
	    private Label starLabel;

	    @FXML
	    private Label incorrectLabel;

	    @FXML
	    private Button finishButton;

	    @FXML
	    private Button backButton;

	    @FXML
	    private Label titleLabel;

	    @FXML
	    private Label ladowniaLabel;

	    @FXML
	    private TextField ladowniaTextField;

	    @FXML
	    private Label spalanieLabel;

	    @FXML
	    private TextField spalanieTextField;


@Override
public void initialize(URL arg0, ResourceBundle arg1) 
{
	finishButton.setOnAction(new EventHandler<ActionEvent>() 
	{
        @Override
        public void handle(ActionEvent arg0) 
        {
        	 AddCarMethods acm = new AddCarMethods(markaTextField.getText(), modelTextField.getText(), silnikTextField.getText(), bakTextField.getText(), 
        			 ladowniaTextField.getText(), spalanieTextField.getText());
        	 
        	 if(acm.Validate())
        	 myController.setScreen(Main.EMPLOYMENU); 
        	 else
        	 incorrectLabel.setVisible(true);
        }
    });
	
	backButton.setOnAction(new EventHandler<ActionEvent>() 
	{
        @Override
        public void handle(ActionEvent arg0) 
        {
        	
        	 myController.setScreen(Main.EMPLOYMENU); 
        	 
        }
    });
	
}

@Override
public void setScreenParent(ScreensController screenPage)
{
	myController = screenPage;
}




}