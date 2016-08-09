import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeePartAController implements Initializable, ControlledScreen{

	ScreensController myController;

	 @FXML
	    private Label kwestionariuszLabel;

	    @FXML
	    private Label swiadectwoLabel;

	    @FXML
	    private Label kwalifikacjeLabel;

	    @FXML
	    private Label szkolaLabel;

	    @FXML
	    private Label lekarzLabel;

	    @FXML
	    private Button kwestionariuszButton;

	    @FXML
	    private Button swiadectwoButton;

	    @FXML
	    private Button kwalifikacjeButton;

	    @FXML
	    private Button szkolaButton;

	    @FXML
	    private Button lekarzButton;

	    @FXML
	    private Button continueButton; 	
	    
	    @FXML
	    private Button backButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		continueButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	 myController.setScreen(Main.EMPLOYEEPARTB); 
	        }
	    });		
		
		backButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	
	        	 myController.setScreen(Main.ADDEMPLOYEE); 
	        	 
	        }
	    });
		
	}

	
	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage;
		
	}
	
	
}
