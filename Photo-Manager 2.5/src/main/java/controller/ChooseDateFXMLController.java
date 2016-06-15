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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.DirectoryChooser;
import model.ModelSort;

public class ChooseDateFXMLController implements Initializable {

    @FXML
    private DatePicker dateBeginning;
    @FXML
    private DatePicker dateEnding;
    
    private  LocalDate dateEnd;
    private LocalDate dateBeginn;
    @FXML
    private Button btOk;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dateBeginning.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dateBeginn = dateBeginning.getValue();
                System.out.println(dateBeginn);
            }
        });
        dateEnding.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dateEnd = dateEnding.getValue();
                System.out.println(dateEnd);
            }
        });
        btOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                
                if(dateBeginn!=null && dateEnd!=null){
                    ModelSort model = new ModelSort(dateBeginn, dateEnd);
                    
                    alert.setContentText("Please choose a saving directory");
                    alert.setTitle("Choose Saving Directory");
                    alert.showAndWait();
                    
                    DirectoryChooser dc = new DirectoryChooser();
                    File file = dc.showDialog(FXMLController.getActStage());
                    
                    model.moveFiles(file.toString());
                    
                    
                }
                else{
                    alert.setContentText("Date invalid!");
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Error 401 occured");
                    alert.showAndWait();
                    
                }
            }
        });
        
    }
   
}
