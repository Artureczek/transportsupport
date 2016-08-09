import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;




public class Main extends Application {
	
	
	 static StackPane root;
	 static Stage mystage;

	 public static Stage mainStage;
	 
	 public static final String LOGIN = "login";
	 public static final String LOGIN_FXML = "/src/main/fxmlCR/fxml_LogIn.fxml"; 
		
	 public static final String MAINSCREEN = "main";
	 public static final String MAINSCREEN_FXML = "/src/main/fxmlCR/fxml_MainScreen.fxml"; 
	
	 public static final String CREATEUSER = "create";
	 public static final String CREATEUSER_FXML = "/src/main/fxmlCR/fxml_CreateUser.fxml"; 
	
	 public static final String EMPLOYMENU = "employ";
	 public static final String EMPLOYMENU_FXML = "/src/main/fxmlCR/fxml_EmployMenu.fxml"; 
	 
	 public static final String ADDEMPLOYEE = "addemploy";
	 public static final String ADDEMPLOYEE_FXML = "/src/main/fxmlCR/fxml_AddEmployeeScreen.fxml"; 
	 
	 public static final String EMPLOYEEPARTA = "partA";
	 public static final String EMPLOYEEPARTA_FXML = "/src/main/fxmlCR/fxml_PartA.fxml"; 
	 
	 public static final String EMPLOYEEPARTB = "partB";
	 public static final String EMPLOYEEPARTB_FXML = "/src/main/fxmlCR/fxml_PartB.fxml"; 
	 
	 public static final String EMPLOYEEPARTC = "partC";
	 public static final String EMPLOYEEPARTC_FXML = "/src/main/fxmlCR/fxml_PartC.fxml"; 
	 
	 public static final String ADDCAR = "addcar";
	 public static final String ADDCAR_FXML = "/src/main/fxmlCR/fxml_AddCarScreen.fxml";

	 public static final String ROUTEMENU = "route";
	 public static final String ROUTEMENU_FXML = "/src/main/fxmlCR/fxml_RouteMenu.fxml";



	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			mainStage = primaryStage;		
		    ScreensController mainContainer = new ScreensController();
		    
		    mainContainer.loadScreen(Main.LOGIN, Main.LOGIN_FXML);
		    mainContainer.loadScreen(Main.MAINSCREEN, Main.MAINSCREEN_FXML);
		    mainContainer.loadScreen(Main.CREATEUSER, Main.CREATEUSER_FXML);
		    mainContainer.loadScreen(Main.EMPLOYMENU, Main.EMPLOYMENU_FXML);
			mainContainer.loadScreen(Main.ROUTEMENU, Main.ROUTEMENU_FXML);
		    mainContainer.loadScreen(Main.ADDEMPLOYEE, Main.ADDEMPLOYEE_FXML);
		    mainContainer.loadScreen(Main.EMPLOYEEPARTA, Main.EMPLOYEEPARTA_FXML);
		    mainContainer.loadScreen(Main.EMPLOYEEPARTB, Main.EMPLOYEEPARTB_FXML);
		    mainContainer.loadScreen(Main.EMPLOYEEPARTC, Main.EMPLOYEEPARTC_FXML);
		    mainContainer.loadScreen(Main.ADDCAR, Main.ADDCAR_FXML);
		    
		    mainContainer.setScreen(Main.LOGIN);
							
			root = new StackPane();
			root.getChildren().addAll(mainContainer);
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.getScene().getStylesheets().add(getClass().getResource("fxmlCR/application.css").toExternalForm());
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