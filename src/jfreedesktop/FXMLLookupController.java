/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfreedesktop;

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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author herve
 */
public class FXMLLookupController implements Initializable {
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
    
    /**
     * Initializes the controller class.
     */
    
    Stage stage;
    @FXML
    private ImageView imIcon;
    @FXML
    private CheckBox cbkRun;
    @FXML
    private TextField tfComment;
    
    public void setStage(Stage stg){
      stage=stg;  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnValidate.setDisable(true);
        btnDupExec.setDisable(true);
        btnDupName.setDisable(true);
        btnLoadExec.setDisable(true);
        btnLoadIcon.setDisable(true);
        tfExec.setEditable(false);
        tfComment.setEditable(false);
        tfGenericName.setEditable(false);
        tfName.setEditable(false);
        tfTryExec.setEditable(false);
        cbkRun.setDisable(true);
        cbType.setDisable(true);
        cbVersion.setDisable(true);
        btnCancel.setDefaultButton(true);
        
        //type
        obType.addAll("Application","Link","Directory");
        cbType.setItems(obType);
        
        obVersion.addAll("0.9.2","0.9.3","0.9.5","0.9.6","0.9.7","0.9.8","1.0","1.1");
        cbVersion.setItems(obVersion);
        
        tfExec.setText(Working.DeskTop.getExec());
        tfGenericName.setText(Working.DeskTop.getGenericName());
        tfComment.setText(Working.DeskTop.getComment());
        tfName.setText(Working.DeskTop.getName());
        tfTryExec.setText(Working.DeskTop.getTryExec());
        cbType.getSelectionModel().select(Working.DeskTop.getType());
        cbVersion.getSelectionModel().select(Working.DeskTop.getVersion());
        
        cbkRun.setSelected(Boolean.parseBoolean(Working.DeskTop.getTerminal()));
        //set icon
        Image im=new Image("file://"+Working.DeskTop.getIcon());
        double scale=imIcon.getFitWidth()/im.getWidth();
        if (scale>1.0) scale=1.0;
        imIcon.setScaleX(scale);
      imIcon.setImage(im);
        
    }    

    @FXML
    private void handleBtnDupName(ActionEvent event) {
        tfName.setText(tfGenericName.getText());
    }

    @FXML
    private void handleBtnValidate(ActionEvent event) {
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
    }

    @FXML
    private void handleBtnExec(ActionEvent event) {
    }

    @FXML
    private void handleBtnDupExec(ActionEvent event) {
    }
    
}
