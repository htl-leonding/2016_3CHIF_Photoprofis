/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author ericb
 */
public class Model {
    private List <File> fileList;
    private static Model instance;
    
    private Model(){
        fileList = new LinkedList<>();
    }
    public static Model getInstance()
    {
        if(instance == null)
            return instance = new Model();
        return instance;
    }
    
    public TreeItem<File> createNode(File f) {
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

        @Override
        public boolean isLeaf() {
            if (isFirstTimeLeaf) {
                isFirstTimeLeaf = false;
                File f = (File) getValue();
                isLeaf = f.isFile();
            }
            return isLeaf;
        }

        private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
            File f = TreeItem.getValue();
            if (f == null) {
                return FXCollections.emptyObservableList();
                }
            if (f.isFile()) {
                return FXCollections.emptyObservableList();
                }
            File[] files = f.listFiles();

            if (files != null) {
                ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();
                for (File childFile : files) {
                    children.add(createNode(childFile));
                }
                return children;
            }
            return FXCollections.emptyObservableList();
            }
        };
    }

    public File importPictures(File location) {
        File[] files = location.listFiles();
            for (File file : files) {
                if (!file.isDirectory()) {
                    //überprüfung ob es eine Bilddatei ist
                    //mit länger des Array die endlänge angeben und prüfen
                    String path = String.valueOf(file.getPath());

                    if ((path.toUpperCase().contains(".JPG")) || (path.toUpperCase().contains(".PNG")) || (path.toUpperCase().contains(".JPEG"))) {
                        System.out.println("It's an image");
                        fileList.add(file);
                    } else {
                        System.out.println("It's NOT an image");
                    }
                }
                
            }
            return null;
    }
    
    public List<File> getFileList(){
        return fileList;
    }
    public void addFile(File file){
        fileList.add(file);
    }
}
    
