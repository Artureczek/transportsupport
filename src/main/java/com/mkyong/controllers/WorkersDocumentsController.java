package com.mkyong.controllers;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.jpedal.examples.baseviewer.BaseViewerFX;
import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.gui.javafx.FXViewerTransitions;
import org.jpedal.examples.viewer.gui.javafx.dialog.FXInputDialog;
import org.jpedal.exception.PdfException;
import org.jpedal.external.PluginHandler;
import org.jpedal.objects.PdfPageData;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by akielbiewski on 10.10.2016.
 */
public class WorkersDocumentsController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    private ListView<String> docsListView;

    @FXML
    private BorderPane pane;

    @FXML
    private BorderPane listPane;

    @FXML
    private BorderPane centerPane;

    @FXML
    private Button loadBttn;

    public static ListView<String> documentsListView;


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

    //Variable to hold the current file/directory
    File file;

    //These two variables are todo with PDF encryption & passwords
    private String password; //Holds the password from the JVM or from User input
    private boolean closePasswordPrompt; //boolean controls whether or not we should close the prompt box


    // Layout panes
    //private VBox top;
    //private HBox bottom;
    private ScrollPane center;
    //Group is a container which holds the decoded PDF content
    //private Group group;

    // for the location of the pdf file
    //private Text fileLoc;

    private float scale = 1.0f;

    private final float[] scalings = {0.01f, 0.1f, 0.25f, 0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f, 4.0f, 7.5f, 10.0f};

    private int currentScaling=5;

    private static final float insetX = 25;

    private static final float insetY = 25;

    private int currentPage = 1;

    Stage stage;

    Scene scene;

    //Controls size of the stage, in theory setting this to a higher value will
    //increase image quality as there's more pixels due to higher image resolutions
    static final int FXscaling=1;

    FitToPage zoomMode = FitToPage.AUTO;

    private FXViewerTransitions.TransitionType transitionType = FXViewerTransitions.TransitionType.None;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        documentsListView = new ListView<>();
        listPane.setCenter(documentsListView);


        try {
            center = new ScrollPane();
            center.setPannable(true);
            center.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            center.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            Group group = new Group();
            group.getChildren().add(pdf);
            center.setContent(group);
            centerPane.setCenter(group);

        }catch(Exception e){
            e.printStackTrace();
        }

        loadBttn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0)
            {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(null);

                if (selectedFile != null) {

                    try {
                        loadPDF(selectedFile);
                    } catch (PdfException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("File selection cancelled.");

                }

            }
        });


    }

    public void loadPDF(final File input) throws PdfException {

        if(input == null) {
            return;
        }

        scale = 1; //reset to default for new page

//        PDFfile=input.getAbsolutePath();
  //      fileLoc.setText(PDFfile);

        openFile(input,null,false);

    }

    /**
     * take a File handle to PDF file on local filesystem and displays in PDF viewer
     * @param input The PDF file to load in the viewer
     */
    public void loadPDF(final String input) throws PdfException {

        if(input == null) {
            return;
        }

        scale = 1; //reset to default for new page
        PDFfile=input;
     //   fileLoc.setText(PDFfile);

        if(input.startsWith("http")){
            openFile(null, input,true);
        }else{
            openFile(new File(input),null,false);
        }

    }


    private void openFile(final File input,String url, boolean isURL) throws PdfException {
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
                        showPasswordPrompt(passwordCount);
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
           // ((Labeled)top.lookup("#pgCount")).setText("/" + pdf.getPageCount());
            /*final ComboBox<String> pages = ((ComboBox<String>)top.lookup("#pages"));
            pages.getItems().clear();
            for(int i = 1; i <= pdf.getPageCount(); i++){
                pages.getItems().add(String.valueOf(i));
            }*/
            // Goes to the first page and starts the decoding process
            goToPage(currentPage);

        } catch (final PdfException ex) {
            ex.printStackTrace();

        }

    }

    private void showPasswordPrompt(final int passwordCount){

        //Setup password prompt content
        final Text titleText = new Text("Password Request");
        final TextField inputPasswordField = new TextField("Please Enter Password");

        //If the user has attempted to enter the password more than once, change the text
        if(passwordCount >= 1){
            titleText.setText("Incorrect Password");
            inputPasswordField.setText("Please Try Again");
        }

        final FXInputDialog passwordInput = new FXInputDialog(stage, titleText.getText()){
            @Override
            protected void positiveClose(){
                super.positiveClose();
                closePasswordPrompt = true;
            }
        };

        password = passwordInput.showInputDialog();

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
        //fitToX(FitToPage.AUTO);
       // updateNavButtons();
        setBorder();
        //adjustPagePosition(center.getViewportBounds());
    }

    /*private void fitToX(final FitToPage fitToPage) {

        if(fitToPage == FitToPage.NONE) {
            return;
        }

        final float pageW=pdf.getPdfPageData().getCropBoxWidth2D(currentPage);
        final float pageH=pdf.getPdfPageData().getCropBoxHeight2D(currentPage);
        final int rotation = pdf.getPdfPageData().getRotation(currentPage);

        //Handle how we auto fit the content to the page
        if(fitToPage == FitToPage.AUTO && (pageW < pageH)){
            if(pdf.getPDFWidth()<pdf.getPDFHeight()) {
                fitToX(FitToPage.HEIGHT);
            }
            else {
                fitToX(FitToPage.WIDTH);
            }
        }

        //Handle how we fit the content to the page width or height
        if(fitToPage == FitToPage.WIDTH){
            final float width=(float) (scene.getWidth());
            if(rotation==90 || rotation==270){
                scale = (width - insetX - insetX) / pageH;
            }else{
                scale = (width - insetX - insetX) / pageW;
            }
        }else if(fitToPage == FitToPage.HEIGHT){
            final float height=(float) (scene.getHeight()-top.getBoundsInLocal().getHeight()-bottom.getHeight());

            if(rotation==90 || rotation==270){
                scale = (height - insetY - insetY) / pageW;
            }else{
                scale = (height - insetY - insetY) / pageH;
            }
        }

        pdf.setPageParameters(scale, currentPage);
    }*/


    /*private void updateNavButtons(){
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
    }*/

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
    public String getPDFfilename() {
        return PDFfile;
    }

   /* private void adjustPagePosition(final Bounds nb){
        // (new scrollbar width / 2) - (page width / 2)
        double adjustment = ((nb.getWidth() / 2) - (group.getBoundsInLocal().getWidth() /2));
        // Keep the group within the viewport of the scrollpane
        if(adjustment < 0) {
            adjustment = 0;
        }
        group.setTranslateX(adjustment);
    }*/

    // Set a space between the top toolbar and the page
    private void setBorder() {
        // Why it's easier to use a dropshadow for this is beyond me, but here it is...
        final int rotation = pdf.getPdfPageData().getRotation(currentPage);
        final double x = (rotation == 90 || rotation == 270) ? 40 : 0;
        final double y = (rotation == 90 || rotation == 270) ? 0 : 40;
        final DropShadow pdfBorder = new DropShadow(0, x,y, Color.TRANSPARENT);
        pdf.setEffect(pdfBorder);
    }

    public void addExternalHandler(PluginHandler customPluginHandle){
        this.customPluginHandle=customPluginHandle;
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {  myController = screenPage;  }
}
