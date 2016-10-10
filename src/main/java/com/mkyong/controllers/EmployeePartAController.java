package com.mkyong.controllers;

import com.mkyong.controlMethods.AddDocumentMethods;
import com.mkyong.transport.DOKUMENTPRACOWNIKA;
import com.mkyong.transport.NAZWADOKUMENTU;
import com.mkyong.transport.PRACOWNIK;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import com.mkyong.main.*;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class EmployeePartAController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML
	private BorderPane pane;

	@FXML
	private ListView<String> fileTypeList;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Button chooseFileBttn;

	@FXML
	private Label chooseFileLbl;

	@FXML
	private Label nameLbl;

	@FXML
	private Label sizeLbl;

	@FXML
	private Label extensionLbl;

	@FXML
	private Button saveFileBttn;

	@FXML
	private Button backBttn;

	@FXML
	private Button finishBttn;

	public static PRACOWNIK editedPracownik;
	public static File file;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{

		List<NAZWADOKUMENTU.DokumentNazwa> list = Arrays.asList(NAZWADOKUMENTU.DokumentNazwa.values());
		List<String> nameList = new ArrayList<>();
		list.stream().forEach(e->nameList.add(e.value()));
		ObservableList<String> items = FXCollections.observableArrayList (nameList);
		fileTypeList.setItems( items);
		fileTypeList.getSelectionModel().select(0);

		
		finishBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.EMPLOYMENU);} });
		backBttn.setOnAction(new EventHandler<ActionEvent>(){ @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.ADDEMPLOYEE); } });

		chooseFileBttn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent arg0)
			{
				FileChooser fileChooser = new FileChooser();
				File selectedFile = fileChooser.showOpenDialog(null);

				if (selectedFile != null) {

					file = selectedFile;
					Platform.runLater(new Runnable(){
					@Override
					public void run() {

						nameLbl.setText("Nazwa Wybranego Pliku: " + getName(selectedFile.getName()));
						extensionLbl.setText("Rozszerzenie Wybranego Pliku: " + getExtension(selectedFile.getName()));
						sizeLbl.setText("Rozmiar Wybranego Pliku: " + selectedFile.length() + " Bytes");
						}
					});
					saveFileBttn.setDisable(false);
				}
				else {
					System.out.println("File selection cancelled.");

			}

			}
		});


		saveFileBttn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent arg0)
			{

				try {
					DOKUMENTPRACOWNIKA dokumentpracownika = new DOKUMENTPRACOWNIKA();
					dokumentpracownika.setAktywny(true);
					dokumentpracownika.setNazwa(getName(file.getName()));
					dokumentpracownika.setRozszerzenieDokumentu(getExtension(file.getName()));
					dokumentpracownika.setPracownik(editedPracownik);
					dokumentpracownika.setNazwadokumentu(AddDocumentMethods.findDocumentByNazwa(fileTypeList.getSelectionModel().getSelectedItem()));
					dokumentpracownika.setDokument(Files.readAllBytes(file.toPath()));
					AddDocumentMethods.saveDocument(dokumentpracownika);


				}catch(Exception e){
					e.printStackTrace();
				}

				saveFileBttn.setDisable(true);
			}
		});
		
	}

	
	@Override
	public void setScreenParent(ScreensController screenPage) 
	{
		myController = screenPage;
		
	}

	public static String getExtension (String fileName){

		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i+1);
		}

		return extension;
	}

	public static String getName (String fileName){

		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(0,i);
		}

		return extension;
	}
	
}
