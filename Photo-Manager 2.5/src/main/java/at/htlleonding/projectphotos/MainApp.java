package at.htlleonding.projectphotos;

import java.io.File;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    public static void main(String[] args) {
    launch(args);
  }

  private TreeItem<File> createNode(File f) {
    return new TreeItem<File>(f) {
      private boolean isLeaf;
      private boolean isFirstTimeChildren = true;
      private boolean isFirstTimeLeaf = true;

      @Override
      public ObservableList<TreeItem<File>> getChildren() {
        if (isFirstTimeChildren) {
          isFirstTimeChildren = false;
          super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
      }
      
      public void TreeViewImage(){
          
      }

      @Override
      public boolean isLeaf() {
        if (isFirstTimeLeaf) {
          isFirstTimeLeaf = false;
          File f = (File) getValue();
          isLeaf = f.isFile();
        }
        return isLeaf;
      }

      private ObservableList<TreeItem<File>> buildChildren(
          TreeItem<File> TreeItem) {
        File f = TreeItem.getValue();
        if (f == null) {
          return FXCollections.emptyObservableList();
        }
        if (f.isFile()) {
          return FXCollections.emptyObservableList();
        }
        File[] files = f.listFiles();
        
        if (files != null) {
          ObservableList<TreeItem<File>> children = FXCollections
              .observableArrayList();
          for (File childFile : files) {
            children.add(createNode(childFile));
          }
          return children;
        }
        return FXCollections.emptyObservableList();
      }
    };
  }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Project Photos Pre Alpha 1.0");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */

}
