package com.mkyong.controllers;

import com.mkyong.controlMethods.AddDocumentMethods;
import com.mkyong.controlMethods.WorkersDocumentsMethods;
import com.mkyong.main.Main;
import com.mkyong.transport.DOKUMENTPRACOWNIKA;
import com.mkyong.transport.NAZWADOKUMENTU;
import com.mkyong.transport.PUSTYDOKUMENT;
import com.mkyong.util.HibernateUtil;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.apache.log4j.Layout;
import org.hibernate.Session;
import org.jpedal.examples.viewer.gui.javafx.FXViewerTransitions;
import org.jpedal.exception.PdfException;
import org.jpedal.external.PluginHandler;
import org.jpedal.objects.PdfPageData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Created by akielbiewski on 10.10.2016.
 */
public class WorkersDocumentsController implements Initializable, ControlledScreen {

    ScreensController myController;


    @FXML
    private BorderPane pane;

    @FXML
    private BorderPane listPane;

    @FXML
    private BorderPane centerPane;

    @FXML
    private Label insertDocLbl;

    @FXML
    private Button insertDocBttn;

    @FXML
    private Button deleteDocBttn;

    @FXML
    private Label deleteDocLbl;

    @FXML
    private Label noDocLbl;

    @FXML
    private Button backBttn;

    @FXML
    private Button finishBttn;

    @FXML
    private AnchorPane rightPane;
    

    public static ListView<String> documentsListView;
    public static String selectedDoc;
    public static DOKUMENTPRACOWNIKA selectedDokumentPracownika;
    private ProgressIndicator progressIndicator;

    private FXViewerTransitions.TransitionType transitionType = FXViewerTransitions.TransitionType.None;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pane.setCenter(null);
        pane.setLeft(null);
        pane.setRight(null);
        pane.setBottom(null);
        pane.setTop(null);
        documentsListView = new ListView<>();
        List<NAZWADOKUMENTU.DokumentNazwa> list = Arrays.asList(NAZWADOKUMENTU.DokumentNazwa.values());
        List<String> nameList = new ArrayList<>();
        list.stream().forEach(e->nameList.add(e.value()));
        ObservableList<String> items = FXCollections.observableArrayList (nameList);
        documentsListView.setItems( items);
        documentsListView.getSelectionModel().select(0);
        deleteDocBttn.setDisable(true);
        progressIndicator = new ProgressIndicator();
        progressIndicator.
        setupViewer(pane);


        documentsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            selectedDoc = newValue;
                            File selectedFile = WorkersDocumentsMethods.getWorkersFile(selectedDoc, ViewWorkersController.selectedWorker);
                            if(selectedFile==null) {
                                loadPDF(WorkersDocumentsMethods.getEmpty());
                                noDocLbl.setVisible(true);
                                deleteDocBttn.setDisable(true);
                            }
                            else{
                                deleteDocBttn.setDisable(false);
                                loadPDF(selectedFile);
                                noDocLbl.setVisible(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        insertDocBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0)
        {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                try {
                DOKUMENTPRACOWNIKA dokumentpracownika = new DOKUMENTPRACOWNIKA();
                dokumentpracownika.setAktywny(true);
                dokumentpracownika.setNazwa(EmployeePartAController.getName(selectedFile.getName()));
                dokumentpracownika.setRozszerzenieDokumentu(EmployeePartAController.getExtension(selectedFile.getName()));
                dokumentpracownika.setPracownik(ViewWorkersController.selectedWorker);
                dokumentpracownika.setNazwadokumentu(AddDocumentMethods.findDocumentByNazwa(documentsListView.getSelectionModel().getSelectedItem()));
                dokumentpracownika.setDokument(Files.readAllBytes(selectedFile.toPath()));
                AddDocumentMethods.saveDocument(dokumentpracownika);
                loadPDF(selectedFile);
                noDocLbl.setVisible(false);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("File selection cancelled.");
            }


        } });

        deleteDocBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0)
        {

            try {
                WorkersDocumentsMethods.deleteDocument(selectedDokumentPracownika);
                noDocLbl.setVisible(true);
                loadPDF(WorkersDocumentsMethods.getEmpty());
            } catch (IOException e) {
                e.printStackTrace();
            }


        } });


        backBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.VIEWWORKERS); } });
        finishBttn.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { myController.setScreen(Main.EMPLOYMENU); } });

    }

    private final org.jpedal.PdfDecoderFX pdf = new org.jpedal.PdfDecoderFX();

    PluginHandler customPluginHandle;

    public WorkersDocumentsController() {
    }

    /**
     * Enum to control how we fit the content to the page.
     *
     * AUTO will automatically fit the content to the stage depending on its orientation
     * WIDTH will fit the content to the stage width depending on its orientation
     * HEIGHT will fit the content to the stage height depending on its orientation
     */
    public enum FitToPage{
        AUTO, WIDTH, HEIGHT, NONE
    }

    String PDFfile;
    private String password; //Holds the password from the JVM or from User input
    private boolean closePasswordPrompt; //boolean controls whether or not we should close the prompt box


    private VBox top;
    private ScrollPane center;
    private Group group;


    private float scale = 1.0f;

    private final float[] scalings = {0.01f, 0.1f, 0.25f, 0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f, 4.0f, 7.5f, 10.0f};

    private int currentScaling=5;

    private static final float insetX = 25;

    private static final float insetY = 25;

    private int currentPage = 1;

    FitToPage zoomMode = FitToPage.AUTO;
    public void setupViewer(BorderPane root){
        top = new VBox();

        root.setTop(top);

        top.getChildren().add(setupToolBar());

        center = new ScrollPane();
        center.setPannable(true);
        center.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        center.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        //needs to be added via group so resizes (see http://pixelduke.wordpress.com/2012/09/16/zooming-inside-a-scrollpane/)
        group=new Group();
        group.getChildren().add(pdf);
        center.setContent(group);
        root.setCenter(center);

        center.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(final ObservableValue<? extends Bounds> ov, final Bounds ob, final Bounds nb) {
                adjustPagePosition(nb);
            }
        });

        root.setLeft(documentsListView);
        root.setRight(rightPane);
    }

    private ToolBar setupToolBar() {

        final ToolBar toolbar = new ToolBar();

        final Button back = new Button("Back");
        final ComboBox<String> pages = new ComboBox<String>();
        final Label pageCount = new Label();
        final Button forward = new Button("Forward");
        final Button zoomIn = new Button("Zoom in");
        final Button zoomOut = new Button("Zoom out");
        final Button fitWidth = new Button("Fit to Width");
        final Button fitHeight = new Button("Fit to Height");
        final Button fitPage = new Button("Fit to Page");

        back.setId("back");
        pageCount.setId("pgCount");
        pages.setId("pages");
        forward.setId("forward");
        zoomIn.setId("zoomIn");
        zoomOut.setId("zoomOut");
        fitWidth.setId("fitWidth");
        fitHeight.setId("fitHeight");
        fitPage.setId("fitPage");


        pages.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(final ObservableValue<? extends Number> ov, final Number oldVal, final Number newVal) {
                if(newVal.intValue() != -1 && newVal.intValue()+1 != currentPage){
                    final int newPage = newVal.intValue() + 1;
                    goToPage(newPage);
                }
            }});

        // Navigate backward
        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent t) {
                if (currentPage > 1) {
                    goToPage(currentPage - 1);
                }

            }
        });

        // Navigate forward
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent t) {
                if (currentPage < pdf.getPageCount()) {
                    goToPage(currentPage + 1);
                }

            }
        });

        // Zoom in
        zoomIn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent t) {
                zoomMode = FitToPage.NONE;

                if (currentScaling < scalings.length - 1) {

                    currentScaling = findClosestIndex(scale, scalings);

                    if (scale >= scalings[findClosestIndex(scale, scalings)]) {

                        currentScaling++;

                    }

                    scale = scalings[currentScaling];

                }

                pdf.setPageParameters(scale, currentPage);
                adjustPagePosition(center.getViewportBounds());
            }
        });

        // Zoom out
        zoomOut.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent t) {
                zoomMode = FitToPage.NONE;

                if (currentScaling > 0) {

                    currentScaling = findClosestIndex(scale, scalings);

                    if (scale <= scalings[findClosestIndex(scale, scalings)]) {

                        currentScaling--;

                    }

                    scale = scalings[currentScaling];

                }

                pdf.setPageParameters(scale, currentPage);
                adjustPagePosition(center.getViewportBounds());
            }
        });

        // Fit to width
        fitWidth.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent t) {
                zoomMode = FitToPage.WIDTH;
               // fitToX(FitToPage.WIDTH);

            }
        });

        // Fit to height
        fitHeight.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent t) {
                zoomMode = FitToPage.HEIGHT;
                //fitToX(FitToPage.HEIGHT);

            }
        });

        // Fit to Page
        fitPage.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent t) {
                zoomMode = FitToPage.AUTO;
               // fitToX(FitToPage.AUTO);


            }
        });


        final Region spacerLeft = new Region();
        final Region spacerRight = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);



        // Set up the ComboBox for transitions
        final ObservableList<String> options = FXCollections.observableArrayList();

        for(final FXViewerTransitions.TransitionType transition : FXViewerTransitions.TransitionType.values()){
            options.add(transition.name());
        }


        toolbar.getItems().addAll(spacerLeft, back, pages, pageCount, forward, zoomIn, zoomOut, spacerRight);

        return toolbar;
    }

    /**
     * take a File handle to PDF file on local filesystem and displays in PDF viewer
     * @param input  The PDF file to load in the viewer
     */
    public void loadPDF(final File input){

        if(input == null) {
            return;
        }

        scale = 1; //reset to default for new page

        PDFfile=input.getAbsolutePath();
       // fileLoc.setText(PDFfile);

        openFile(input,null,false);

    }

    private void openFile(final File input,String url, boolean isURL) {
        try {
            //Open the pdf file so we can check for encryption
            if(isURL){
                pdf.openPdfFileFromURL(url,false);
            }else{
                pdf.openPdfFile(input.getAbsolutePath());
            }

            if(customPluginHandle!=null){
                if(isURL){
                    customPluginHandle.setFileName(url);
                }else{
                    customPluginHandle.setFileName(input.getAbsolutePath());
                }
            }

            if(System.getProperty("org.jpedal.page") != null && !System.getProperty("org.jpedal.page").isEmpty()){
                currentPage = currentPage < 1 ? 1 : currentPage;
                currentPage = currentPage > pdf.getPageCount() ? pdf.getPageCount() : currentPage;
            }else{
                currentPage = 1;
            }
            /*
             * This code block deals with user input and JVM passwords in Encrypted PDF documents.
             */
            if(pdf.isEncrypted()){

                int passwordCount = 0;        //Monitors how many attempts there have been to the password
                closePasswordPrompt = false;  //Do not close the prompt box

                //While the PDF content is not viewable, repeat until the correct password is found
                while(!pdf.isFileViewable() && !closePasswordPrompt) {

                    /*
                     * See if there's a JVM flag for the password & Use it if there is
                     * Otherwise prompt the user to enter a password
                     */
                    if(System.getProperty("org.jpedal.password")!=null){
                        password = System.getProperty("org.jpedal.password");
                    }else if(!closePasswordPrompt){
                       // showPasswordPrompt(passwordCount);
                    }

                    //If we have a password, try and open the PdfFile again with the password
                    if (password != null) {

                        if(isURL){
                            pdf.openPdfFileFromURL(url,false,password);
                        }else{
                            pdf.openPdfFile(input.getAbsolutePath());
                        }
                        //pdf.setEncryptionPassword(password);

                    }
                    passwordCount += 1; //Increment he password attempt

                }

            }

            // Set up top bar values
            ((Labeled)top.lookup("#pgCount")).setText("/" + pdf.getPageCount());
            final ComboBox<String> pages = ((ComboBox<String>)top.lookup("#pages"));
            pages.getItems().clear();
            for(int i = 1; i <= pdf.getPageCount(); i++){
                pages.getItems().add(String.valueOf(i));
            }
            // Goes to the first page and starts the decoding process
            goToPage(currentPage);

        } catch (final PdfException ex) {
            ex.printStackTrace();

        }

    }

    private static int findClosestIndex(final float scale, final float[] scalings) {
        float currentMinDiff = Float.MAX_VALUE;
        int closest = 0;

        for(int i = 0; i < scalings.length - 1; i++) {

            final float diff = Math.abs(scalings[i] - scale);

            if(diff < currentMinDiff) {
                currentMinDiff = diff;
                closest = i;
            }

        }
        return closest;
    }

    private void decodePage() {

        try {
            final PdfPageData pageData = pdf.getPdfPageData();
            final int rotation = pageData.getRotation(currentPage); //rotation angle of current page

            //Only call this when the page is displayed vertically, otherwise
            //it will mess up the document cropping on side-ways documents.
            if (rotation == 0 || rotation == 180) {
                pdf.setPageParameters(scale, currentPage);
            }

            pdf.decodePage(currentPage);
            //wait to ensure decoded
            pdf.waitForDecodingToFinish();
        } catch (final Exception e) {
            e.printStackTrace();
        }
       // fitToX(FitToPage.AUTO);
        updateNavButtons();
        setBorder();
        adjustPagePosition(center.getViewportBounds());
    }

    private void updateNavButtons(){
        if(currentPage > 1){
            top.lookup("#back").setDisable(false);
        }else{
            top.lookup("#back").setDisable(true);
        }

        if(currentPage < pdf.getPageCount()){
            top.lookup("#forward").setDisable(false);
        }else{
            top.lookup("#forward").setDisable(true);
        }

        ((ComboBox)top.lookup("#pages")).getSelectionModel().select(currentPage - 1);
    }

    private void goToPage(final int newPage){

        final FXViewerTransitions.TransitionDirection direction ;

        // For sliding Transitions
        if(transitionType != FXViewerTransitions.TransitionType.Fade || transitionType != FXViewerTransitions.TransitionType.None){
            direction = newPage > currentPage ? FXViewerTransitions.TransitionDirection.LEFT: FXViewerTransitions.TransitionDirection.RIGHT;
        }else{
            direction = FXViewerTransitions.TransitionDirection.NONE;
        }

        switch (transitionType){

            case Fade:
                startTransition(  newPage, direction);
                break;

            case Scale:
                startTransition(  newPage, direction);
                break;

            case Rotate:
                startTransition(  newPage, direction);
                break;

            case CardStack:
                startTransition(  newPage, direction);
                break;

            default: //no transition

                currentPage = newPage;
                decodePage();
                break;
        }

    }

    private void startTransition(final int newPage,final FXViewerTransitions.TransitionDirection direction){
        final Transition exitTransition = FXViewerTransitions.exitTransition(pdf, transitionType, direction);
        if(exitTransition != null){
            exitTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override public void handle(final ActionEvent t) {

                    currentPage = newPage;

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            decodePage();
                        }
                    });

                    FXViewerTransitions.TransitionDirection entryDirection = direction;
                    if(direction != FXViewerTransitions.TransitionDirection.NONE){
                        entryDirection = direction == FXViewerTransitions.TransitionDirection.LEFT ? FXViewerTransitions.TransitionDirection.RIGHT : FXViewerTransitions.TransitionDirection.LEFT;
                    }

                    final Transition entryTransition = FXViewerTransitions.entryTransition(pdf, transitionType, entryDirection);
                    entryTransition.play();
                }
            });
            exitTransition.play();
        }
    }

    /**
     * @return the case sensitive full path and name of the PDF file
     */
    private void adjustPagePosition(final Bounds nb){
        // (new scrollbar width / 2) - (page width / 2)
        double adjustment = ((nb.getWidth() / 2) - (group.getBoundsInLocal().getWidth() /2));
        // Keep the group within the viewport of the scrollpane
        if(adjustment < 0) {
            adjustment = 0;
        }
        group.setTranslateX(adjustment);
    }

    // Set a space between the top toolbar and the page
    private void setBorder() {
        // Why it's easier to use a dropshadow for this is beyond me, but here it is...
        final int rotation = pdf.getPdfPageData().getRotation(currentPage);
        final double x = (rotation == 90 || rotation == 270) ? 40 : 0;
        final double y = (rotation == 90 || rotation == 270) ? 0 : 40;
        final DropShadow pdfBorder = new DropShadow(0, x,y, Color.TRANSPARENT);
        pdf.setEffect(pdfBorder);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {  myController = screenPage;  }
}
