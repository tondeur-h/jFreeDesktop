/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfreedesktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author herve
 */
public class FXMLjfreeDesktopController implements Initializable {
    
    @FXML
    private ListView<String> lvDesktop;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField tfShortcutName;
    @FXML
    private Button btnQuit;
   
    ObservableList<String> oLvDesktop=javafx.collections.FXCollections.observableArrayList();
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfExec;
    @FXML
    private TextField tfComment;
    @FXML
    private ImageView imgIcon;
    @FXML
    private TextArea taKeyWords;
    @FXML
    private TextArea taPermissions;
    @FXML
    private TextArea taText;
    
    
   String rootDeskTop;
   String fileDesktop=null;
    @FXML
    private Button btnLookup;
    @FXML
    private Button btnPath;
   
    
    private void update_screen(){
      taText.setText(fileDesktop);
      Working.parseFileDeskTop(rootDeskTop+lvDesktop.getSelectionModel().getSelectedItem()+".desktop"); 
      
      tfShortcutName.setText(Working.DeskTop.getName());
      tfType.setText(Working.DeskTop.getType());
      tfExec.setText(Working.DeskTop.getExec());
      tfComment.setText(Working.DeskTop.getComment());
      taKeyWords.setText(Working.DeskTop.getCategories());
      taPermissions.setText(Working.DeskTop.getPermissions());
      //set icon
        Image im=new Image("file://"+Working.DeskTop.getIcon());
        double scale=imgIcon.getFitWidth()/im.getWidth();
        if (scale>1.0) scale=1.0;
        imgIcon.setScaleX(scale);
      imgIcon.setImage(im);
      
      btnDelete.setDisable(false);
      btnLookup.setDisable(false);
    }
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        read_pathProperty();
       refreshList();
    }    

    
    public void refreshList(){
          //rafraichir l'affichage
        oLvDesktop.clear();
          //load desktop files list
       oLvDesktop=Working.listFileDesktop(rootDeskTop);
       //set to listview
       lvDesktop.setItems(oLvDesktop);  
    }
    
    
    @FXML
    private void handleLVDeskTop(MouseEvent event) {
    fileDesktop=Working.readFileDeskTop(rootDeskTop+lvDesktop.getSelectionModel().getSelectedItem()+".desktop");
    update_screen();
    }

    
    @FXML
    private void handleBtnAdd(ActionEvent event) throws IOException {
    //add a new desktop shortcut
        Stage stage=new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLAdd.fxml"));
        Parent root = loader.load();
 
        final FXMLAddController controller = loader.getController();
        controller.setStage(stage, rootDeskTop);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Add a Shortcut");
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();     
        
        //refresh list
        refreshList();
    }

    
    @FXML
    private void handleBtnDelete(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure to delete this shortcut?");
                alert.setContentText("Confirm your chose");
                
                if (alert.showAndWait().get()==ButtonType.OK){
        File fileDelete=new File(rootDeskTop+lvDesktop.getSelectionModel().getSelectedItem()+".desktop");
        fileDelete.delete();
        //refreash list
        refreshList();
                }
    }

    
    @FXML
    private void handleBtnQuit(ActionEvent event) {
         Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure you want to Quit?");
                alert.setContentText("Confirm your chose");
                
                if (alert.showAndWait().get()==ButtonType.OK){
        Platform.exit(); //quit application
                }
    }

    
    @FXML
    private void handleKPLVDeskTop(KeyEvent event) {
    fileDesktop=Working.readFileDeskTop(rootDeskTop+lvDesktop.getSelectionModel().getSelectedItem()+".desktop");
    update_screen();
 
    }

    
    @FXML
    private void handleBtnLookup(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLLookup.fxml"));
        Parent root = loader.load();
 
        final FXMLLookupController controller = loader.getController();
        controller.setStage(stage);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Lookup a Shortcut");
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
    }

    
    @FXML
    private void handleBtnPath(ActionEvent event) {
    //modify path for user Desktop
       // Alert path=new Alert(Alert.AlertType.INFORMATION);
       // path.setTitle("Path for Desktop User");
       // path.setContentText(rootDeskTop);
       // path.showAndWait();
   
TextInputDialog dialog = new TextInputDialog(rootDeskTop);
dialog.setTitle("Path of your Desktop");
dialog.setContentText("Please enter your Path:");
Optional<String> result = dialog.showAndWait();
result.ifPresent(pathD->save_pathProperty(pathD));    

    }

    
    private void save_pathProperty(String path){
      try {
            Properties p=new Properties();
            p.setProperty("desktopPath", path);    
            p.storeToXML(new FileOutputStream("jfreedesktop.xml"), path);  
            rootDeskTop=path;
        } catch (IOException ex) {}
    }
    
    private void read_pathProperty(){
        try {
            Properties p=new Properties();
            p.loadFromXML(new FileInputStream("jfreedesktop.xml"));
            
            rootDeskTop=p.getProperty("desktopPath", "~/Desktop/");
            
        } catch (IOException ex) {
            rootDeskTop="/home/herve/Desktop/";
        }
        
    }
    
    
}
