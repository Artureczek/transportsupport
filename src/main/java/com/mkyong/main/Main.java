package com.mkyong.main;

import com.mkyong.controllers.ScreensController;
import com.mkyong.transport.APPUSER;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	 public static int activeUser = -1;
	 public static APPUSER activeUserEntity = null;
	 static StackPane root;
	 static Stage mystage;

	 public static Stage mainStage;
	 
	 public static final String LOGIN = "login";
	 public static final String LOGIN_FXML = "/fxmlCR/fxml_LogIn.fxml";
		
	 public static final String MAINSCREEN = "main";
	 public static final String MAINSCREEN_FXML = "/fxmlCR/fxml_MainScreen.fxml"; 
	
	 public static final String CREATEUSER = "create";
	 public static final String CREATEUSER_FXML = "/fxmlCR/fxml_CreateUser.fxml"; 
	
	 public static final String EMPLOYMENU = "employ";
	 public static final String EMPLOYMENU_FXML = "/fxmlCR/fxml_EmployMenu.fxml"; 
	 
	 public static final String ADDEMPLOYEE = "addemploy";
	 public static final String ADDEMPLOYEE_FXML = "/fxmlCR/fxml_AddEmployeeScreen.fxml"; 
	 
	 public static final String EMPLOYEEPARTA = "partA";
	 public static final String EMPLOYEEPARTA_FXML = "/fxmlCR/fxml_PartA.fxml"; 
	 
	 public static final String EMPLOYEEPARTB = "partB";
	 public static final String EMPLOYEEPARTB_FXML = "/fxmlCR/fxml_PartB.fxml"; 
	 
	 public static final String EMPLOYEEPARTC = "partC";
	 public static final String EMPLOYEEPARTC_FXML = "/fxmlCR/fxml_PartC.fxml"; 
	 
	 public static final String ADDCAR = "addcar";
	 public static final String ADDCAR_FXML = "/fxmlCR/fxml_AddCarScreen.fxml";

	 public static final String ROUTEMENU = "route";
	 public static final String ROUTEMENU_FXML = "/fxmlCR/fxml_RouteMenu.fxml";

	 public static final String SELECTROUTE = "selectroute";
	 public static final String SELECTROUTE_FXML = "/fxmlCR/fxml_SelectRoute.fxml";

	public static final String VIEWWORKERS = "viewworkers";
	public static final String VIEWWORKERS_FXML = "/fxmlCR/fxml_viewWorkers.fxml";

	public static final String VIEWCARS = "viewcars";
	public static final String VIEWCARS_FXML = "/fxmlCR/fxml_ViewCars.fxml";

	public static final String WORKERSDOCUMENTS = "workersdocuments";
	public static final String WORKERSDOCUMENTS_FXML = "/fxmlCR/fxml_WorkersDocuments.fxml";



	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			mainStage = primaryStage;		
		    ScreensController mainContainer = new ScreensController();
		    
		    mainContainer.loadScreen(Main.LOGIN, Main.LOGIN_FXML);
		    mainContainer.loadScreen(Main.MAINSCREEN, Main.MAINSCREEN_FXML);
		    mainContainer.loadScreen(Main.CREATEUSER, Main.CREATEUSER_FXML);
		    mainContainer.loadScreen(Main.EMPLOYMENU, Main.EMPLOYMENU_FXML);
			mainContainer.loadScreen(Main.ROUTEMENU, Main.ROUTEMENU_FXML);;
			mainContainer.loadScreen(Main.SELECTROUTE, Main.SELECTROUTE_FXML);
		    mainContainer.loadScreen(Main.ADDEMPLOYEE, Main.ADDEMPLOYEE_FXML);
		    mainContainer.loadScreen(Main.EMPLOYEEPARTA, Main.EMPLOYEEPARTA_FXML);
		    mainContainer.loadScreen(Main.EMPLOYEEPARTB, Main.EMPLOYEEPARTB_FXML);
		    mainContainer.loadScreen(Main.EMPLOYEEPARTC, Main.EMPLOYEEPARTC_FXML);
		    mainContainer.loadScreen(Main.ADDCAR, Main.ADDCAR_FXML);
			mainContainer.loadScreen(Main.VIEWWORKERS, Main.VIEWWORKERS_FXML);
			mainContainer.loadScreen(Main.VIEWCARS, Main.VIEWCARS_FXML);
			mainContainer.loadScreen(Main.WORKERSDOCUMENTS, Main.WORKERSDOCUMENTS_FXML);

		    mainContainer.setScreen(Main.LOGIN);
							
			root = new StackPane();
			root.getChildren().addAll(mainContainer);
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.getScene().getStylesheets().add(getClass().getResource("/fxmlCR/application.css").toExternalForm());
			primaryStage.show(); 
					
			mystage = primaryStage;
			mystage.setResizable(false);
		
		
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    
	public static void refreshWindowContents()
	{
		mystage.getScene().getWindow().sizeToScene();
	}
    
    
    
	public static void main(String[] args) {
		launch(args);

	}
}
