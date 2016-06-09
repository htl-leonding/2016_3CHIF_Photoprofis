/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Model;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class ProjectInfoController implements Initializable {
    @FXML
    private TextArea buchiText;
    @FXML
    private TextArea projectBeschreibung;
    @FXML
    private ImageView buchiPic;
    @FXML
    private ImageView yimPic;
    @FXML
    private ImageView kajicPic;
    @FXML
    private ImageView thomasPic;
    @FXML
    private ImageView mayrhoferPic;
    @FXML
    private TextArea yimText;
    @FXML
    private TextArea kajicText;
    @FXML
    private TextArea thomasText;
    @FXML
    private TextArea mayrhoferText;
    Model model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setProjectText();
        setGroupPics();
        setTeamDiscription();
    }    
    
    public void setGroupPics(){
        try {
            buchiPic.setImage(new Image(new FileInputStream("Buchi.png")));
            yimPic.setImage(new Image(new FileInputStream("Yim.png")));
            kajicPic.setImage(new Image(new FileInputStream("Kajic.png")));
            thomasPic.setImage(new Image(new FileInputStream("Thomas.png")));
            mayrhoferPic.setImage(new Image(new FileInputStream("Mayrhofer.png")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProjectInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setProjectText() {
        projectBeschreibung.setText("Die Bilder von Beispielweise einer Kamera oder eines Handy werden sehr oft zur Sicherung \nauf den Computer übertragen. Möchte man jetzt die Urlaubsbilder der Familie oder \nFreunden präsentieren, sind die Bilder oft schwer zu finden und durcheinander vermischt.");
    }

    //muss noch gemacht werden! 
    private void setTeamDiscription() {
        /*
        String [] teamDes =  model.getTeamDiscription();
        buchiText.setText(teamDes[0]);
        yimText.setText(teamDes[1]);
        kajicText.setText(teamDes[2]);
        thomasText.setText(teamDes[3]);
        mayrhoferText.setText(teamDes[4]);
                */
    } 
}
