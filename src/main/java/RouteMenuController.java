import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by akielbiewski on 09.08.2016.
 */
public class RouteMenuController implements Initializable, ControlledScreen{


    ScreensController myController;

    @FXML
    private Button viewRoutesBttn;

    @FXML
    private Button backBttn;

    @FXML
    private Label newRouteLbl;

    @FXML
    private Label viewRoutesLbl;

    @FXML
    private Button newRouteBttn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {







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
