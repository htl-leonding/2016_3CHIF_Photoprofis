/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.ModelSort;

public class FXMLSortController implements Initializable {

    private Stage prevStage;
    private static Stage actualStage;

    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleBtChoosePhotoFolder (ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        File file = dc.showDialog(prevStage);
        File [] tempFiles = file.listFiles();
        
        
        for(int i = 0; i< tempFiles.length; i++){
            if(tempFiles[i].toString().toUpperCase().contains(".JPG")||tempFiles[i].toString().toUpperCase().contains(".PNG")||tempFiles[i].toString().toUpperCase().contains(".JPEG"))
                ModelSort.addTempFile(tempFiles[i]);
        }
        
        
    }

    @FXML
    private void handleBtSave(ActionEvent event) throws IOException{
       Stage stage = new Stage();
       stage.setTitle("ChooseDate");
       Pane myPane = null;
       myPane = FXMLLoader.load(getClass().getResource("/fxml/ChooseDateFXML.fxml"));
       Scene scene = new Scene(myPane);
       stage.setScene(scene);
       prevStage.close();
       stage.show();
    }
    
    public static Stage getActualStage(){
        return actualStage;
    }
   
    
}
