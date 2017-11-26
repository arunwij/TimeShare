/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kademlia.JKademliaNode;
import kademlia.KadConfiguration;
import kademlia.KadServer;
import kademlia.NameGenerator;
import kademlia.node.Node;
import kademlia.node.Port;
import kademlia.operation.ConnectOperation;
import kademlia.routing.Contact;

/**
 *
 * @author Artista
 */
public class RunningConfiguration {
    
    public final static int FILE_PORT = 13000;
    public static int FILE_PORT_ID =0;
    public static int LOCAL_NODE_PORT;
    public static String LOCAL_NODE_NAME;
    public static JKademliaNode LOCAL_JKNODE;
    public static InetAddress LOCAL_INETADDRESS;
    public static Contact LOCAL_NODE_CONTACT;
    private static Node LOCAL_NODE;
    
    public final static String BOOTSTRAP_NODE_NAME = "bootstrap";
    public static final String BOOTSTRAP_ADDRESS = "192.168.1.20";
    public static final int BOOTSTRAP_PORT = 11000;
    public static InetSocketAddress BOOTSTRAP_NODE_SOCKET;
    public static Node BOOTSTRAP_NODE;
    
    public static final boolean IS_BOOTSTRAP_NODE = true;
    
    public static KadServer KAD_SERVER;
    public static KadConfiguration KAD_CONFIGURATION;
    
    public static int resultCount = 0;
    public static String[] results = new String[2];
  
    static {
        
        try {
            LOCAL_INETADDRESS = InetAddress.getLocalHost();
            if(IS_BOOTSTRAP_NODE){
                BOOTSTRAP_NODE_SOCKET = new InetSocketAddress(LOCAL_INETADDRESS,BOOTSTRAP_PORT);
                LOCAL_JKNODE = new JKademliaNode(BOOTSTRAP_NODE_NAME,BOOTSTRAP_NODE_SOCKET);
                LOCAL_NODE_NAME = BOOTSTRAP_NODE_NAME;
                System.out.println("Kad-network bootstrap node started..");
            }else{
                BOOTSTRAP_NODE_SOCKET = new InetSocketAddress(BOOTSTRAP_ADDRESS,BOOTSTRAP_PORT);
                LOCAL_NODE_NAME = NameGenerator.get();
                LOCAL_JKNODE = new JKademliaNode(LOCAL_NODE_NAME,Port.RandomPort());
                System.out.println("Jkademlia local node created...");
                BOOTSTRAP_NODE = new Node(BOOTSTRAP_NODE_SOCKET);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(RunningConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RunningConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        KAD_SERVER = LOCAL_JKNODE.getServer();
        KAD_CONFIGURATION = LOCAL_JKNODE.getCurrentConfiguration();
        LOCAL_NODE = LOCAL_JKNODE.getNode();
        LOCAL_NODE_CONTACT = new Contact(LOCAL_NODE);
    }
    
    public static void run(){
        if(!IS_BOOTSTRAP_NODE){
            System.out.println("Connect operation starting");
            ConnectOperation connectOperation = new ConnectOperation(KAD_SERVER,LOCAL_JKNODE,BOOTSTRAP_NODE,KAD_CONFIGURATION);
            try {
                connectOperation.execute();
                System.out.println("Connect operation done..");
            } catch (IOException ex) {
                Logger.getLogger(RunningConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public static void printResults(){
        try {
            File resultFile = new File("data/received/results/Results.txt");
            resultFile.createNewFile();
            FileWriter writer = new FileWriter(resultFile);
            for (int i = 0; i < results.length; i++) {
                 writer.write(results[i].toString());
            }
           
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(RunningConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
