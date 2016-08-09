package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EmployeePartCController implements Initializable, ControlledScreen{

	ScreensController myController;
	   
	    @FXML
	    private Label umowaLabel;

	    @FXML
	    private Label swiadectwoPracyLabel;

	    @FXML
	    private Label czynnoscLabel;

	    @FXML
	    private Label konkurencjaLabel;

	    @FXML
	    private Label orzeczenieLekarskieLabel;

	    @FXML
	    private Button umowaButton;

	    @FXML
	    private Button swiadectwoPracyButton;

	    @FXML
	    private Button czynnoscButton;

	    @FXML
	    private Button konkurencjaButton;

	    @FXML
	    private Button orzecznieLekarskieButton;

	    @FXML
	    private Button finishButton;

	    @FXML
	    private Button backButton;

	    @FXML
	    private Label titleLabel;
	
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		finishButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	 myController.setScreen(Main.EMPLOYMENU); 
	        }
	    });		
		
		backButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	
	        	 myController.setScreen(Main.EMPLOYEEPARTB); 
	        	 
	        }
	    });
		
	}
	@Override
	public void setScreenParent(ScreensController screenPage)
	{
		myController = screenPage;		
	}

	
		

}
