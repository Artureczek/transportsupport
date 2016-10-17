package com.mkyong.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import com.mkyong.main.*;
import com.mkyong.controlMethods.*;
import com.mkyong.transport.POJAZD;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

public class AddCarController implements Initializable, ControlledScreen {

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

		@FXML
		private Label registerLbl;

		@FXML
		private TextField registerTxtFld;

		@FXML
		private ChoiceBox<String> fuelChoiceBox;

        @FXML
        private Label expSilnikLbl;

        @FXML
        private Label expTankLbl;

        @FXML
        private Label expLoadLbl;

        @FXML
        private Label expSpalanieLbl;



@Override
public void initialize(URL arg0, ResourceBundle arg1)
{
	fuelChoiceBox.getItems().addAll("ON", "e95", "e98", "LPG");
	fuelChoiceBox.getSelectionModel().selectFirst();

    silnikTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {
            if (!silnikTextField.getText().matches("^[1-9]([0-9]{1,2})?(\\.[1-9])?$")) {
                expSilnikLbl.setVisible(true);
            }
            else{
                expSilnikLbl.setVisible(false);
            }
        }
    });

    spalanieTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {
            if (!spalanieTextField.getText().matches("^[1-9]([0-9]{1,2})?(\\.[1-9])?$")) {
                expSpalanieLbl.setVisible(true);
            }
            else{
                expSpalanieLbl.setVisible(false);
            }
        }
    });

    ladowniaTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {
            if (!ladowniaTextField.getText().matches("^\\d+$")) {
                expLoadLbl.setVisible(true);
            }
            else{
                expLoadLbl.setVisible(false);
            }
        }
    });

    bakTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {
            if (!bakTextField.getText().matches("^\\d+$")) {
                expTankLbl.setVisible(true);
            }
            else{
                expTankLbl.setVisible(false);
            }
        }
    });


	finishButton.setOnAction(new EventHandler<ActionEvent>() 
	{
        @Override
        public void handle(ActionEvent arg0) 
        {
            if( !expSilnikLbl.isVisible() && !expLoadLbl.isVisible() && !expSpalanieLbl.isVisible() && !expTankLbl.isVisible()) {
                POJAZD car = new POJAZD(markaTextField.getText(), registerTxtFld.getText(), modelTextField.getText(), Double.valueOf(silnikTextField.getText()), Long.valueOf(bakTextField.getText()),
                        Long.valueOf(ladowniaTextField.getText()), Double.valueOf(spalanieTextField.getText()), fuelChoiceBox.getSelectionModel().getSelectedItem(), Main.activeUserEntity);

                boolean result = AddCarMethods.Validate(car);

                if (result)
                    myController.setScreen(Main.EMPLOYMENU);
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