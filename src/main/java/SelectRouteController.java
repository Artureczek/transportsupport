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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by akielbiewski on 09.08.2016.
 */
public class SelectRouteController implements Initializable, ControlledScreen, MapComponentInitializedListener,
        ElevationServiceCallback, GeocodingServiceCallback, DirectionsServiceCallback {


    @FXML
    private AnchorPane anchorpane;
    @FXML
    private BorderPane pane;

    ScreensController myController;

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

    }


    @Override
    public void setScreenParent(ScreensController screenPage)
    {
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

        map = mapView.createMap(mapOptions,true);
        directions = mapView.getDirec();
        renderer = new DirectionsRenderer(true, map, directions);
        mapView.getMap().hideDirectionsPane();

    }

    @Override
    public void directionsReceived(DirectionsResult directionsResult, DirectionStatus directionStatus) {

    }

    @Override
    public void elevationsReceived(ElevationResult[] elevationResults, ElevationStatus elevationStatus) {

    }

    @Override
    public void geocodedResultsReceived(GeocodingResult[] geocodingResults, GeocoderStatus geocoderStatus) {

    }
}
