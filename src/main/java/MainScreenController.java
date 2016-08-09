import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable, ControlledScreen{

	ScreensController myController; 
	
	
	
    @FXML
    private Button manageEmplBttn;

    @FXML
    private Button managaRouteBttn;

    @FXML
    private Label emplLabel;

    @FXML
    private Label routeLabel;

    @FXML
    private Label logoutLabel;

    @FXML
    private Button logoutBttn;

	

    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
		
		logoutBttn.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	 myController.setScreen(Main.LOGIN); 
	        }
	    });
		
		manageEmplBttn.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	 myController.setScreen(Main.EMPLOYMENU); 
	        }
	    });

		managaRouteBttn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent arg0)
			{
				myController.setScreen(Main.ROUTEMENU);
			}
		});
		
		
		
		
		
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage; 	
	}
	
	
}
