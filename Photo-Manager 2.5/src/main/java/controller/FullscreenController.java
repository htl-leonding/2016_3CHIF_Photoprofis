/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Toolkit;
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
        Toolkit tk = Toolkit.getDefaultToolkit();
        fullscreenView.setImage(FXMLController.ActImage());
        fullscreenView.setScaleY(tk.getScreenSize().getHeight()/(fullscreenView.getImage().getHeight()+500));
        fullscreenView.setScaleX(tk.getScreenSize().getWidth()/(fullscreenView.getImage().getWidth()+500));
        fullscreenAnchor.setMaxHeight(tk.getScreenSize().getHeight()/(fullscreenView.getImage().getHeight()));
        fullscreenAnchor.setMaxWidth(tk.getScreenSize().getWidth()/(fullscreenView.getImage().getWidth()));
        
    }
    
    /*public void SetFullScreen(DisplayMode dm, Frame window){
        try {
            window.setIconImage(new FileInputStream("Unbenannt.png"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FullscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }*/
    
   /* private Image ScaledImage(Image img, int w,int h){
        BufferedImage resizedImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();
        return resizedImage;
    }*/ 
    
}
