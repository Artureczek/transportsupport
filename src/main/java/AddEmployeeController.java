import com.mkyong.transport.PRACOWNIK;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable, ControlledScreen{

	ScreensController myController;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label peselLabel;

    @FXML
    private Label salaryLabel;

    @FXML
    private Label birthDateLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField peselTextField;

    @FXML
    private TextField salaryTextField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private Label starLabel;

    @FXML
    private Label incorrectLabel;

    @FXML
    private Button continueButton;
    
    @FXML
    private Button backButton;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		
		continueButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
	        	Instant instant = Instant.from(birthDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()));
				Date date = Date.from(instant);
				PRACOWNIK pracownik = new PRACOWNIK(nameTextField.getText(), surnameTextField.getText(), peselTextField.getText(),
						Float.valueOf(salaryTextField.getText()), /*date,*/ Main.activeUser);

				 boolean result = AddEmployeeMethods.Validate(pracownik);
	        	 
	        	 if(result)
	        	 myController.setScreen(Main.EMPLOYEEPARTA); 
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
