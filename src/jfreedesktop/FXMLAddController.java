/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfreedesktop;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author herve
 */
public class FXMLAddController implements Initializable {
    @FXML
    private TextField tfGenericName;
    @FXML
    private Button btnDupName;
    @FXML
    private TextField tfName;
    @FXML
    private Button btnValidate;
    @FXML
    private Button btnCancel;
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private ComboBox<String> cbVersion;
    @FXML
    private Button btnLoadIcon;
    @FXML
    private TextField tfExec;
    @FXML
    private Button btnLoadExec;
    @FXML
    private Button btnDupExec;
    @FXML
    private TextField tfTryExec;

    ObservableList<String> obType=javafx.collections.FXCollections.observableArrayList();
    ObservableList<String> obVersion=javafx.collections.FXCollections.observableArrayList();
    
    String fileIco=null;
    boolean runterm=false;
    
    /**
     * Initializes the controller class.
     */
    
    Stage stage;
    @FXML
    private ImageView imIcon;
    @FXML
    private TextField tfComment;
    @FXML
    private CheckBox ckbRun;
    
    String rootDesktop;
    
    public void setStage(Stage stg, String rd){
      stage=stg;  
      rootDesktop=rd;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnValidate.setDisable(false);
        btnCancel.setDefaultButton(true);
        
        //type
        obType.addAll("Application","Link","Directory");
        cbType.setItems(obType);
        
        cbType.getSelectionModel().selectFirst();
        
        obVersion.addAll("0.9.2","0.9.3","0.9.5","0.9.6","0.9.7","0.9.8","1.0","1.1");
        cbVersion.setItems(obVersion);
        cbVersion.getSelectionModel().select(6);
    }    

    @FXML
    private void handleBtnDupName(ActionEvent event) {
        tfName.setText(tfGenericName.getText());
    }

    @FXML
    private void handleBtnValidate(ActionEvent event) {
       //Save file
       try {
           String fileName=rootDesktop+tfName.getText()+".desktop";
        PrintWriter pw=new PrintWriter(fileName);
        pw.println("[Desktop Entry]");
        pw.println("Encoding=UTF-8");
        pw.println("Version="+cbVersion.getValue());
        pw.println("Type="+cbType.getValue());
        pw.println("Terminal="+runterm);
        pw.println("StartupNotify=false");
        pw.println("GenericName="+tfGenericName.getText());
//Categories=Java;IDE;
        pw.println("Exec="+tfExec.getText());
        pw.println("TryExec="+tfExec.getText());
        pw.println("Icon="+fileIco);
        pw.println("Name="+tfName.getText());
        pw.println("Comment="+tfComment.getText());
        pw.println("X-GNOME-Autostart-enabled=true");
        pw.close();
        
        File f=new File (fileName);
        f.setExecutable(true, true);

       } catch(Exception e){System.out.println("error");}
        stage.close();
    }

    @FXML
    private void handleBtnCancel(ActionEvent event) {
      stage.close();
    }

    @FXML
    private void handleCBType(ActionEvent event) {
    }

    @FXML
    private void handleBtnLoadIcon(ActionEvent event) {
         FileChooser fcIco=new FileChooser();
        fcIco.setTitle("Select a png icon file");
        fcIco.getExtensionFilters().add(new FileChooser.ExtensionFilter("png Icons(*.png)", "*.png"));
        File repIco=fcIco.showOpenDialog(stage);
        if (repIco!=null){
            fileIco=repIco.getPath();
             //set icon
        Image im=new Image("file://"+fileIco);
        double scale=imIcon.getFitWidth()/im.getWidth();
        if (scale>1.0) scale=1.0;
        imIcon.setScaleX(scale);
      imIcon.setImage(im);
        }
    }

    @FXML
    private void handleBtnExec(ActionEvent event) {
        FileChooser fcExec=new FileChooser();
        fcExec.setTitle("Select a executable file");
        File repExec=fcExec.showOpenDialog(stage);
        if (repExec!=null){
            tfExec.setText(repExec.getPath());
        }
    }

    @FXML
    private void handleBtnDupExec(ActionEvent event) {
        tfTryExec.setText(tfExec.getText());
    }

    @FXML
    private void hcbkRun(ActionEvent event) {
        if (ckbRun.isSelected()) {
            runterm=true;
        }
        else
        {
            runterm=false;
        }
    }

    
}
