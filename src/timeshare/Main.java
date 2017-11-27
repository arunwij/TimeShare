/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare;

import java.util.List;
import java.util.ListIterator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kademlia.routing.Contact;
import timeshare.rating.DeviceRating;

/**
 *
 * @author Artista
 */
public class Main extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
        root.getStylesheets().add("/timeshare/Style.css");
       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop(){
        System.out.println("System is shutting down");
        RunningConfiguration.LOCAL_JKNODE.getStatistician().createLog();
        Platform.exit();
        System.exit(0);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //DeviceRating.deviceQuery();
        RunningConfiguration.run();
        
        List list = RunningConfiguration.LOCAL_JKNODE.getRoutingTable().getAllContacts();
        
        ListIterator lt = list.listIterator();
        while(lt.hasNext()){
            Contact c = (Contact)lt.next();
            
            System.err.println("Node id:"+ c.getNode().getNodeId());
        }
        launch(args);
    }
    
    
}
