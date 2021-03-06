package controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Model;

public class FXMLController implements Initializable {

    
    
    
    private Model model;
    private TreeItem<File> rootc;
    private ImageView imageView;
    private static int actPos = 0;
    
    @FXML
    private TreeView<File> treeView;//Variablen
    @FXML
    private ImageView imgView;
    @FXML
    private TilePane tlPane;
    @FXML
    private Button buttonLeft;
    @FXML
    private Label textAnzeige;
    @FXML
    private Button buttonRight;
    private File actFile;
    @FXML
    private Label lbShowMeta;
    @FXML
    private Button buttonPosFirst;
    @FXML
    private Button buttonPosLast;
    @FXML
    private ScrollPane scPane;
    @FXML
    private ImageView teamIcon;
    @FXML
    private ComboBox<String> comboSelectDrive;
    @FXML
    private AnchorPane treeAnchor;
    @FXML
    private AnchorPane picAnchor;
    @FXML
    private Button btRightToLeft;
    @FXML
    private Button btLeftToRight;
    @FXML
    private Button bt_Edit;
    private static ImageView img;
    @FXML
    private SplitPane splitP;
    @FXML
    private Button bt_Sort;
    private static Stage stage;
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Initialize">
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableButtons();
//        bt_Sort.setDisable(true);
//        bt_Edit.setDisable(true);
        model = Model.getInstance();
        Path myPath = Paths.get(System.getProperty("user.home"));
        comboSelectDrive.getItems().add(myPath.toString());
        imgView.setStyle("-fx-background-color: BLACK");
        imgView.setPreserveRatio(true);
        imgView.setSmooth(true);
        imgView.setCache(true);
        teamIcon.setImage(model.getIcon());
        img = imgView;
        
        File[] paths; 
        paths = File.listRoots(); // returns pathnames for files and directory

        // for each pathname in pathname array
        for (File pa : paths) {
            rootc = model.createNode(new File(pa.toString()));

            comboSelectDrive.getItems().add(rootc.getValue().toString());
            treeView.setRoot(rootc);

            treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if ((treeView != null && treeView.getSelectionModel() != null) && treeView.getSelectionModel().getSelectedItem() != null) {
                        bt_Sort.setDisable(false);
                        bt_Edit.setDisable(false);
                        tlPane.getChildren().clear();
                        File location = treeView.getSelectionModel().getSelectedItem().getValue();
                        model.importPictures(location);
                        try {
                            tlPane.getChildren().addAll(loadImages());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                    }
                }
            });
        }
    }
    // </editor-fold>            
                    
    // <editor-fold defaultstate="collapsed" desc="Bild auswählen - anzeigen - MEtadatem">
    private List<ImageView> loadImages() throws FileNotFoundException {

        List<ImageView> images = new LinkedList<>();
        imageView = null;
        for(int i=0; i<model.getFileList().size(); i++){
            final Image image = new Image(new FileInputStream(model.getFileList().get(i)),150,0,true,true);
            final File imageFile = model.getFileList().get(i);
            imageView = new ImageView(image);
            images.add(imageView);        
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                        if (mouseEvent.getClickCount() == 1) {
                                actPos = 0;
                                actFile = imageFile;
                            try {
                                imgView.setImage(new Image(new FileInputStream(imageFile)));
                                imgView.setPreserveRatio(true);
                                imgView.setSmooth(true);
                                imgView.setCache(true);
                                enableButtons();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                
                                actPos= model.getFileList().lastIndexOf(actFile);
                        }
               }
            }
            });
        }
            
        return images;
    }
    
    public void rotateBitmap(int rotation)
    {
        imgView.setRotate(180.0);
    }
    
    public void showMeta(int actPos) throws ImageProcessingException, IOException {
        String out;
        Metadata metadata = ImageMetadataReader.readMetadata(model.getFileList().get(actPos));

        for (Directory directory : metadata.getDirectories()) {
            out = "";
            for (Tag tag : directory.getTags()) {
                out = out + " " + String.format("[%s] - %s = %s \n", directory.getName(), tag.getTagName(), tag.getDescription());

                                        //String output = (directory.getName() + " " + tag.getTagName() + " " + tag.getDescription());
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
            lbShowMeta.setText(out);
        }
    }

    void indent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
    }

    @FXML
    private void getPicClicked(MouseEvent event) {
        if(event.getClickCount()%2 == 0){
            if(treeAnchor.getMaxHeight() == 0){
                treeAnchor.setMaxHeight(360);
                picAnchor.setMaxHeight(360);
            }
            else{
                treeAnchor.setMaxHeight(0);
                picAnchor.setMaxHeight(720);
            }
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Buttons First-toLast Pos">
    @FXML
    private void handleButtonPosFirst(ActionEvent event) throws FileNotFoundException {
        int actPos = 0;
        
        actFile = model.getFileList().get(actPos);
        Image show = new Image(new FileInputStream(model.getFileList().get(actPos)));
        imgView.setImage(show);
        textAnzeige.setText(model.getFileList().get(actPos).getName());
    }

    @FXML
    private void handleButtonPosLast(ActionEvent event) throws FileNotFoundException {
        int actPos = model.getFileList().size()-1;
        
        actFile = model.getFileList().get(actPos);
        Image show = new Image(new FileInputStream(model.getFileList().get(actPos)));
        imgView.setImage(show);
        textAnzeige.setText(model.getFileList().get(actPos).getName());
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fenster: Exif, Projectinfo">
    @FXML
    private void IconClicked(MouseEvent event) throws IOException {
        Stage st = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ProjectInfo.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        st.setTitle("ProjectInfo");
        st.setScene(scene);
        stage = st;
        st.show();
    }
    @FXML
    private void handleButtonEdit(ActionEvent event) throws IOException {
        disableButtons();
//        bt_Sort.setDisable(true);
        Stage st = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ExifdataView.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        st.setTitle("Metadaten");
        st.setScene(scene);
        stage = st;
        st.show();
    }
    
    @FXML
    private void PictureFullScreen(MouseEvent event) {
       if(imgView.getImage() != null){
            if(event.getClickCount()%2 == 0){
                try {
                    Stage st = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/FullScreen.fxml"));
                
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add("/styles/Styles.css");
                
                    st.setTitle("ProjectInfo");
                    st.setScene(scene);
                    st.setFullScreen(true);
                    stage = st;
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public static Image ActImage(){
        return img.getImage();
    }
    
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Rotate Buttons">
    @FXML
    private void handelRotatingLeftButton(ActionEvent event) {
        imgView.setRotate(imgView.getRotate() - 90);
        MatchPicSize();
    }

    @FXML
    private void handelRotatingRightButton(ActionEvent event) {
        imgView.setRotate(imgView.getRotate() + 90);
        MatchPicSize();
    }
    
    public void MatchPicSize(){
        if(imgView.getRotate() >= 90 && imgView.getRotate() < 180||imgView.getRotate() > 180 && imgView.getRotate() <= 270){
            imgView.setScaleX((ActImage().getWidth()/imgView.getImage().getWidth())/1.7);
            imgView.setScaleY((ActImage().getHeight()/imgView.getImage().getHeight())*1.5);
        }
        else if(imgView.getRotate() <= -90 && imgView.getRotate() > -180||imgView.getRotate() < -180 && imgView.getRotate() >= -270){
            imgView.setScaleX((ActImage().getWidth()/imgView.getImage().getWidth())/1.7);
            imgView.setScaleY((ActImage().getHeight()/imgView.getImage().getHeight()*1.5));
        }
        else if(imgView.getRotate() >= 360 || imgView.getRotate() <= -360){
            imgView.setRotate(0);
            imgView.setScaleX(ActImage().getWidth()/imgView.getImage().getWidth());
            imgView.setScaleY(ActImage().getHeight()/imgView.getImage().getHeight());
        }
        else{
            imgView.setScaleX(ActImage().getWidth()/imgView.getImage().getWidth());
            imgView.setScaleY(ActImage().getHeight()/imgView.getImage().getHeight());
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Buttons Left-Right">
    @FXML
    private void handleLeftClick(ActionEvent event) throws FileNotFoundException {
        int actPos= model.getFileList().lastIndexOf(actFile)-1;
        if(actPos<0)
            actPos = model.getFileList().size()-1;
        
        actFile = model.getFileList().get(actPos);     
        Image show = new Image(new FileInputStream(model.getFileList().get(actPos)));
        imgView.setImage(show);
        textAnzeige.setText(model.getFileList().get(actPos).getName());  
    }

    @FXML
    private void handleRightClick(ActionEvent event) throws FileNotFoundException, IOException {
        int actPos= model.getFileList().lastIndexOf(actFile)+1;
        if(actPos==model.getFileList().size())
            actPos = 0;
        
        actFile = model.getFileList().get(actPos);
        Image show = new Image(new FileInputStream(model.getFileList().get(actPos)));
        imgView.setImage(show);
        textAnzeige.setText(model.getFileList().get(actPos).getName());
    }
    // </editor-fold>
    
    @FXML
    private void comboSelectDriveOnAction(ActionEvent event) {
        rootc = model.createNode(new File(comboSelectDrive.getValue()));
        treeView.setRoot(rootc);
    }

    @FXML
    private void ChangeSize(MouseEvent event) {
    }

    @FXML
    private void handleButtonSort(ActionEvent event) throws IOException {
        
//        bt_Edit.setDisable(true);
        disableButtons();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChooseDateFXML.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Choose Date");
        stage.setScene(scene);
        stage.show();
    }
    private void enableButtons()
    {
        bt_Sort.setDisable(false);
        bt_Edit.setDisable(false);
        bt_Sort.setDisable(false);
        buttonLeft.setDisable(false);
        buttonRight.setDisable(false);
        buttonPosFirst.setDisable(false);
        buttonPosLast.setDisable(false);
        btRightToLeft.setDisable(false);
        btLeftToRight.setDisable(false);
    }
    private void disableButtons()
    {
        buttonLeft.setDisable(true);
        buttonRight.setDisable(true);
        buttonPosFirst.setDisable(true);
        buttonPosLast.setDisable(true);
        btRightToLeft.setDisable(true);
        btLeftToRight.setDisable(true);
    }
        /**
     * @return the actPos
     */
    public static int getActPos() {
        return actPos;
    }
    public static Stage getActStage(){
        return stage;
    }
}
