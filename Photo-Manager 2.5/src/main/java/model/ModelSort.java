/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ChooseDateFXMLController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ModelSort {
    
    LocalDate beginningDate;
    LocalDate endDate;
    LinkedList<File> folderFiles = new LinkedList<>();
    private static List<File> tempFiles = Model.getInstance().getFileList();
    
    public ModelSort(LocalDate beginningDate, LocalDate endDate){
        this.beginningDate = beginningDate;
        this.endDate = endDate;
    }
    public ModelSort(){
        
    }
    
    public boolean calcDate(LocalDate other){
        return (beginningDate.isBefore(other) && endDate.isAfter(other))|| beginningDate.equals(other) || endDate.equals(other);
    }
     public void moveFiles(String pathnameFolder) {
        for(int i = 0; i<tempFiles.size(); i++){
            try {
                BasicFileAttributes bfa = Files.readAttributes(tempFiles.get(i).toPath(), BasicFileAttributes.class);
                Date date = new Date(bfa.creationTime().toMillis());
                LocalDate other = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if(calcDate(other)){
                    folderFiles.add(tempFiles.get(i));  
                }
            } catch (IOException ex) {
                Logger.getLogger(ChooseDateFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        pathnameFolder = pathnameFolder +"\\"+ beginningDate+" - "+endDate;
        File file = new File(pathnameFolder);
        System.out.println("Directory "+pathnameFolder+" created: "+file.mkdir());
        for (final File folderFile : folderFiles) {
            if(folderFile!=null){
                final String pathnameFile = pathnameFolder+"/"+folderFile.getName();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Files.move(folderFile.toPath(), Paths.get(pathnameFile));
                            } catch (IOException ex) {
                                Logger.getLogger(ModelSort.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                   
                    System.out.println("Picture "+folderFile.getName()+" sucessfully moved to "+pathnameFile);
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sucessfully transfered "+folderFiles.size()+" pictures!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        
        
    
    
}

