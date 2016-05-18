/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Model;

/**
 *
 * @author josipkajic
 */
public class ExifFensterController {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAufnahmeDatum;
    @FXML
    private TextField tfAufnahmeOrt;
    @FXML
    private Label lbTwo;
    @FXML
    private Label lbThree;
    @FXML
    private Label lbFour;
    @FXML
    private Label lbFive;

    public void initialize() {
        try {
            showMeta(FXMLController.getActPos());
        } catch (ImageProcessingException ex) {
            Logger.getLogger(ExifFensterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExifFensterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void showMeta(int actPos) throws ImageProcessingException, IOException {
        String out;
        Metadata metadata = ImageMetadataReader.readMetadata(Model.getInstance().getFileList().get(actPos));

        for (Directory directory : metadata.getDirectories()) {
            out = "";
            for (Tag tag : directory.getTags()) {
                //out = out + " " + String.format("[%s] - %s = %s \n", directory.getName(), tag.getTagName(), tag.getDescription());
                File i = Model.getInstance().getFileList().get(actPos);
                tfName.setText(i.getName());
                tfAufnahmeDatum.setText(tag.getDescription());
                tfAufnahmeOrt.setText(tag.getDirectoryName());
                                        //String output = (directory.getName() + " " + tag.getTagName() + " " + tag.getDescription());
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
        }
    }
    
}
