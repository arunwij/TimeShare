/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare;

import timeshare.allocator.Xmlreader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;
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
import kademlia.routing.Contact;


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
    
    
    File selectedFile;
    List<File> selectedFiles;
    File selectedxml;
    File selectedcsv;
    
    
    
    
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
        selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            lblUploadSource.setText(selectedFile.getName());
            indicatorSource.setVisible(true);
        }else{
            lblUploadSource.setText("no file selected");
            indicatorSource.setVisible(false);
        }
    }
    
    @FXML
    private void sendFile() throws UnknownHostException, IOException, InterruptedException {
        
        String fileName = selectedFile.getName();
        String csvFile = selectedcsv.getName();
        Path target = Paths.get("data", csvFile);
        String fileNamexml = selectedxml.getName();
        
        //Files.copy(selectedcsv.toPath(), target);
        // Files.copy(selectedxml.toPath(), target);
        // Files.copy(selectedFile.toPath(), target);
        //FileSender.send(to.getNode(), selectedFile);
        showProgress();
                        
        List list = RunningConfiguration.LOCAL_JKNODE.getRoutingTable().getAllContacts();
        ListIterator ltr = list.listIterator();
        System.out.println("total contacts :"+list.size());
        Contact c;
        
        while(ltr.hasNext()){
            c = (Contact) ltr.next();
            if(!c.equals(RunningConfiguration.LOCAL_NODE_CONTACT)){
              //  FileSender.send(c.getNode(), selectedFile); 
            }
        } 
        
        Xmlreader xml  = new Xmlreader();
        try {
            xml.run();
        } catch (Exception ex) {
            Logger.getLogger(SceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    private void uploadXML(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the XML File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml*"));
        selectedxml = fileChooser.showOpenDialog(null);
        if(selectedxml != null){
            lblUploadXml.setText(selectedxml.getName());
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
        selectedcsv = fileChooser.showOpenDialog(null);
        if(selectedcsv != null){
            lblCsv.setText(selectedcsv.getName());
            indicatorCsv.setVisible(true);
        }else{
            lblCsv.setText("no file selected");
            indicatorCsv.setVisible(false);
        }
    }
    
    @FXML 
    private void showProgress() throws InterruptedException{
        lblUpdate.setVisible(true);
        prgsUpdate.setVisible(true);
        Thread.sleep(5000);
        stopProgress();
    }
    
    @FXML 
    private void stopProgress(){
        lblUpdate.setVisible(false);
        prgsUpdate.setVisible(false);
        lblDone.setVisible(true);
       // RunningConfiguration.printResults();
    }
    
//    @FXML
//    private void runTask() {
//
//        final double wndwWidth = 300.0d;
//        Label updateLabel = new Label();
//        updateLabel.setPrefWidth(wndwWidth);
//        ProgressBar progress = new ProgressBar();
//        progress.setPrefWidth(wndwWidth);
//
//        VBox updatePane = new VBox();
//        updatePane.setSpacing(5.0d);
//        updatePane.getChildren().addAll(updateLabel, progress);
//        final Stage taskUpdateStage = new Stage(StageStyle.UTILITY);
//        taskUpdateStage.setScene(new Scene(updatePane));
//        taskUpdateStage.show();
//
//        Task longTask = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                int max = 50;
//                for (int i = 1; i <= max; i++) {
//                    if (isCancelled()) {
//                        break;
//                    }
//                    updateProgress(i, max);
//                    updateMessage("Task part " + String.valueOf(i) + " complete");
//
//                    Thread.sleep(100);
//                }
//                return null;
//            }
//        };
//
//        longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//            @Override
//            public void handle(WorkerStateEvent t) {
//                taskUpdateStage.hide();
//            }
//        });
//        progress.progressProperty().bind(longTask.progressProperty());
//        updateLabel.textProperty().bind(longTask.messageProperty());
//
//        taskUpdateStage.show();
//        new Thread(longTask).start();
//    }
            
    
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
