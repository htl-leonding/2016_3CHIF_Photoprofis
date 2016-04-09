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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import model.Model;

public class FXMLController implements Initializable {

    
    
    
    private Model model;
    TreeItem<File> rootc;
    
    @FXML
    private TreeView<File> treeView;//Variablen
    @FXML
    private ComboBox<String> comboSelectDrive;
    private List<File> fileList;
    @FXML
    private ImageView imgView;
    @FXML
    private ScrollPane scPane;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = Model.getInstance();
        Path myPath = Paths.get(System.getProperty("user.home"));
        comboSelectDrive.getItems().add(myPath.toString());
        
        File[] paths; 
        paths = File.listRoots(); // returns pathnames for files and directory

        // for each pathname in pathname array
        for (File pa : paths) {
            rootc = model.createNode(new File(pa.toString()));
            comboSelectDrive.getItems().add(rootc.getValue().getPath());
            treeView.setRoot(new TreeItem<>(new File(comboSelectDrive.getItems().get(0))));

            //TreeView treeViewe = new TreeView<File>(roote);
            treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    fileList = new LinkedList<>();
                    if (treeView != null && treeView.getSelectionModel() != null && treeView.getSelectionModel().getSelectedItem() != null) {
                        tlPane.getChildren().clear();
                        File location = treeView.getSelectionModel().getSelectedItem().getValue();
                        model.importPictures(location);
                    }
                }
            });
        }
    }

    private ImageView createImageView(final File imageFile) {

        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                        if (mouseEvent.getClickCount() == 1) {
                            try {
                                int actPos = 0;
                                Image image = new Image(new FileInputStream(imageFile));
                                imgView.setImage(image);
                                actFile = imageFile;
                                imgView.setStyle("-fx-background-color: BLACK");
                                imgView.setPreserveRatio(true);
                                imgView.setSmooth(true);
                                imgView.setCache(true);
                                textAnzeige.setText(imageFile.getName());
                                for (int i = 0; i < fileList.size(); i++) {
                                    if (actFile.getPath().equals(fileList.get(i).getPath())) {
                                        if (i - 1 < 0) {
                                            actPos = fileList.size() - 1;
                                        } else {
                                            actPos = i - 1;
                                        }
                                    }
                                }
                                showMeta(actPos);
                            } catch (FileNotFoundException e) {
                            } catch (ImageProcessingException | IOException ex) {
                                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
        }
        return imageView;
    }

    @FXML
    private void comboSelectDriveOnAction(ActionEvent event) {
        rootc = model.createNode(new File(comboSelectDrive.getValue()));
        treeView.setRoot(rootc);
    }

    @FXML
    private void handleLeftClick(ActionEvent event) throws FileNotFoundException {
        int actPos= fileList.lastIndexOf(actFile)-1;
        if(actPos<0)
            actPos = fileList.size()-1;
        
        actFile = fileList.get(actPos);     
        Image show = new Image(new FileInputStream(fileList.get(actPos)));
        imgView.setImage(show);
        textAnzeige.setText(fileList.get(actPos).getName());  
    }

    @FXML
    private void handleRightClick(ActionEvent event) throws FileNotFoundException, IOException {
        int actPos= fileList.lastIndexOf(actFile)+1;
        if(actPos==fileList.size())
            actPos = 0;
        
        actFile = fileList.get(actPos);
        Image show = new Image(new FileInputStream(fileList.get(actPos)));
        imgView.setImage(show);
        textAnzeige.setText(fileList.get(actPos).getName());
    }
 
    public void showMeta(int actPos) throws ImageProcessingException, IOException {
        String out;
        Metadata metadata = ImageMetadataReader.readMetadata(fileList.get(actPos));

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
}
