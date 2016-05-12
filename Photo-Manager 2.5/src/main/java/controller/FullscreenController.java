/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class FullscreenController implements Initializable {
    @FXML
    private ImageView fullscreenView;
    FXMLController fxmlCon;
    @FXML
    private AnchorPane fullscreenAnchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fullscreenView.setImage(FXMLController.ActImage());
        fullscreenView.isResizable();
        fullscreenAnchor.setMaxHeight(fullscreenView.getImage().getHeight());
        fullscreenAnchor.setMaxWidth(fullscreenView.getImage().getWidth());
    }    
    
}
