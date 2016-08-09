import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

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
	        public void handle(ActionEvent arg0) 
	        {
	        	 myController.setScreen(Main.ADDEMPLOYEE); 
	        }
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
	
	
	
	
	
	
	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage; 		
	}

}
