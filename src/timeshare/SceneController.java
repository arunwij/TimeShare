/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;


/**
 *
 * @author Artista
 */
public class SceneController implements Initializable {
    
    @FXML private BorderPane borderPane;
    @FXML private Button buttonExit;
    @FXML private TextField txtFileSelected;
    @FXML private Label lblUploadSource;
    @FXML private ProgressIndicator indicatorSource;
    @FXML private Label lblUploadXml;
    @FXML private ProgressIndicator indicatorXml;
    @FXML private Label lblCsv;
    @FXML private ProgressIndicator indicatorCsv;
    @FXML private Button btn;
    @FXML private ProgressBar prgsTotMem;
    @FXML private ProgressIndicator indicatorTotMem;
    @FXML private ProgressBar prgsFreeMem;
    @FXML private ProgressIndicator indicatorFreeMem;
    @FXML private ProgressBar prgsUsdMem;
    @FXML private ProgressIndicator indicatorUsdMem;
    @FXML private Label lblOs;
    @FXML private Label lblVersion;
    @FXML private Label lblArch;
    @FXML private Label lblProcess;
    @FXML private Label lblUpdate;
    @FXML private Label lblDone;
    @FXML private ProgressBar prgsUpdate;
    
    
    File selectedSource;
    List<File> selectedFiles;
    File selectedXml;
    File selectedCsv;
    
    
    
    
    @FXML
    private void handleExit() {
       System.exit(0);
    }
    
    @FXML 
    private void sendObject(){};
    
    @FXML
    private void handleMinimize(){}
    
    
    @FXML
    private void uploadSource(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the Source File");
        selectedSource = fileChooser.showOpenDialog(null);
        if(selectedSource != null){
            lblUploadSource.setText(selectedSource.getName());
            indicatorSource.setVisible(true);
        }else{
            lblUploadSource.setText("no file selected");
            indicatorSource.setVisible(false);
        }
    }
    
     @FXML
    private void uploadXML(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the XML File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml*"));
        selectedXml = fileChooser.showOpenDialog(null);
        if(selectedXml != null){
            lblUploadXml.setText(selectedXml.getName());
            indicatorXml.setVisible(true);
        }else{
            lblUploadXml.setText("no file selected");
            lblUploadXml.setVisible(false);
        }
    }
    
    @FXML
    private void uploadCSV(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the CSV File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv*"));
        selectedCsv = fileChooser.showOpenDialog(null);
        if(selectedCsv != null){
            lblCsv.setText(selectedCsv.getName());
            indicatorCsv.setVisible(true);
        }else{
            lblCsv.setText("no file selected");
            indicatorCsv.setVisible(false);
        }
    }
    
    @FXML
    private void sendFile() throws InterruptedException  {
        String sourceName = selectedSource.getName();
        String csvFile = selectedCsv.getName();
        String xmlName = selectedXml.getName(); 
        try {
//            Xmlreader xml  = new Xmlreader(selectedXml,selectedSource,selectedCsv);
//            xml.run();
        } catch (Exception ex) {
            Logger.getLogger(SceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    @FXML 
    private void showProgress() throws InterruptedException{
        lblUpdate.setVisible(true);
        prgsUpdate.setVisible(true);
        Thread.sleep(1000);
        stopProgress();
    }
    
    @FXML 
    private void stopProgress(){
        lblUpdate.setVisible(false);
        prgsUpdate.setVisible(false);
        lblDone.setVisible(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            long totalMemory = Runtime.getRuntime().totalMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            long usedMemory = totalMemory - freeMemory;
            float tm = (float) totalMemory/totalMemory;
            float fm = (float) freeMemory/totalMemory;
            float um = (float) usedMemory/totalMemory;
            String os = System.getProperty("os.name");
            String version  = System.getProperty("os.version");
            String arch = System.getProperty("os.arch");
            String proc = Integer.toString(Runtime.getRuntime().availableProcessors());
            prgsTotMem.setProgress(tm);
            indicatorTotMem.setProgress(tm);
            prgsFreeMem.setProgress(fm);
            indicatorFreeMem.setProgress(fm);
            prgsUsdMem.setProgress(um);
            indicatorUsdMem.setProgress(um);
            lblOs.setText(os);
            lblVersion.setText("OS Version : "+ version);
            lblArch.setText("Processor Architecture : " + arch);
            lblProcess.setText("Available processors (cores) : " + proc);
        }
      }, 0, 2000);
    } 
}
