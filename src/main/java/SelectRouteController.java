import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.elevation.ElevationResult;
import com.lynden.gmapsfx.service.elevation.ElevationServiceCallback;
import com.lynden.gmapsfx.service.elevation.ElevationStatus;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by akielbiewski on 09.08.2016.
 */
public class SelectRouteController implements Initializable, ControlledScreen, MapComponentInitializedListener,
        ElevationServiceCallback, GeocodingServiceCallback, DirectionsServiceCallback {


    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Label distanceLabel;

    @FXML
    private Label prioLabel;

    @FXML
    private Button designateBttn;

    @FXML
    private Button showBttn;

    @FXML
    private ChoiceBox<Integer> truckNumBox;

    @FXML
    private TextField fromTextField;

    @FXML
    private ChoiceBox<Integer> driversNumBox;

    @FXML
    private Label truckLabel;

    @FXML
    private Label driversLabel;

    @FXML
    private RadioButton prioTimeBttn;

    @FXML
    private BorderPane pane;

    @FXML
    private RadioButton prioCostBttn;

    @FXML
    private TextField toTextField;

    ScreensController myController;
    final ToggleGroup group = new ToggleGroup();

    public static MapOptions mapOptions;
    public static GoogleMapView mapView;
    public static double distance;
    public static GoogleMap map;
    protected DirectionsPane directions;
    protected DirectionsService ds;
    protected DirectionsRequest dr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);

        pane.setCenter(mapView);
        truckNumBox.setItems(FXCollections.observableArrayList( 1,2,3,4,5,6,7,8 ));
        driversNumBox.setItems(FXCollections.observableArrayList( 1,2,3,4,5,6,7,8 ));

        prioCostBttn.setToggleGroup(group);
        prioTimeBttn.setToggleGroup(group);

        showBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                selectRoute();
            }
        });


    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }


    DirectionsRenderer renderer;

    @Override
    public void mapInitialized() {

        //Set the initial properties of the map.
        mapOptions = new MapOptions();
        mapOptions.center(new LatLong(52.232222, 21.008333))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions, true);
        directions = mapView.getDirec();
        renderer = new DirectionsRenderer(true, map, directions);
        mapView.getMap().hideDirectionsPane();

    }

    public void selectRoute() {
        ds = new DirectionsService();

        try {
            dr = new DirectionsRequest(
                    fromTextField.getText(),
                    toTextField.getText(),
                    TravelModes.DRIVING
            );

            ds.getRoute(dr, this, renderer);

            renderer.setMap(map);
            renderer.setPanel(directions);

        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void directionsReceived(DirectionsResult directionsResult, DirectionStatus directionStatus) {

        List<DirectionsRoute> lista = directionsResult.getRoutes();
        List<DirectionsLeg> lista2 = lista.get(0).getLegs();
        List<DirectionsSteps> lista3 = lista2.get(0).getSteps();


        distanceLabel.setText("Odleglosc: " + lista2.get(0).getDistance().getText());

    }

    @Override
    public void elevationsReceived(ElevationResult[] elevationResults, ElevationStatus elevationStatus) {

    }

    @Override
    public void geocodedResultsReceived(GeocodingResult[] geocodingResults, GeocoderStatus geocoderStatus) {

    }
}

