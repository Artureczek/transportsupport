package com.mkyong.controllers;
import com.mkyong.main.*;
import com.mkyong.controlMethods.*;
import com.mkyong.transport.PRACOWNIK;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable, ControlledScreen {

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

	@FXML
	private RadioButton isDriverRadioBttn;

	@FXML
	private Label expPeselLbl;

	@FXML
	private Label expWageLbl;

	@FXML
	private Label expNameLbl;

	@FXML
	private Label expSurnameLbl;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub

		nameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				if (!nameTextField.getText().matches("^[A-ZŻŹĆĄŚĘŁÓŃ][a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ]+$")) {
					expNameLbl.setVisible(true);
				}
				else{
					expNameLbl.setVisible(false);
				}
			}
		});
		surnameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				if (!surnameTextField.getText().matches("^[A-ZŻŹĆĄŚĘŁÓŃ][a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ]+$")) {
					expSurnameLbl.setVisible(true);
				}
				else{
					expSurnameLbl.setVisible(false);
				}
			}
		});

		peselTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				if (!peselTextField.getText().matches("^\\d{11}$")) {
					expPeselLbl.setVisible(true);
				}
				else{
					expPeselLbl.setVisible(false);
				}
			}
		});

		salaryTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				if (!salaryTextField.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
					expWageLbl.setVisible(true);
				}
				else{
					expWageLbl.setVisible(false);
				}
			}
		});




		continueButton.setOnAction(new EventHandler<ActionEvent>() 
		{
	        @Override
	        public void handle(ActionEvent arg0) 
	        {
				if(!expNameLbl.isVisible() && !expPeselLbl.isVisible() && !expSurnameLbl.isVisible() && !expWageLbl.isVisible()) {
					Instant instant = Instant.from(birthDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()));
					Date date = Date.from(instant);
					PRACOWNIK pracownik = new PRACOWNIK(nameTextField.getText(), surnameTextField.getText(), peselTextField.getText(),
							Long.valueOf(salaryTextField.getText()), date, isDriverRadioBttn.isSelected(), Main.activeUserEntity);

					boolean result = AddEmployeeMethods.Validate(pracownik);

					if (result)
						myController.setScreen(Main.EMPLOYEEPARTA);
					else
						incorrectLabel.setVisible(true);
				}
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
