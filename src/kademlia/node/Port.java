/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kademlia.node;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import static java.util.logging.Level.*;

/**
 *
 * @author Artista
 */
public class Port {
    private static int MIN_PORT_NUMBER = 9000;
    private static int MAX_PORT_NUMBER = 10000;
    private static boolean PORT_AVAILABLE = false;
    
    private static Handler consoleHandler = null;
    private static Handler fileHandler  = null; 
   
    private static final Logger LOGGER = Logger.getLogger(Port.class.getName());
    private static int ASSIGNED_PORT;
    
    public static int RandomPort(){
        consoleHandler = new ConsoleHandler();
        try{
            fileHandler  = new FileHandler("logs/system.log");
        }catch(IOException ex){
            LOGGER.severe(ex.toString());
        }
        int randomPort = -1;
        
        while(!PORT_AVAILABLE){
            randomPort = ThreadLocalRandom.current().nextInt(MIN_PORT_NUMBER, MAX_PORT_NUMBER + 1);
            PORT_AVAILABLE = available(randomPort);
        }
        
        ASSIGNED_PORT = randomPort;
        return randomPort;
    }
    
    private static boolean available(int port){
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            LOGGER.severe("Invalid start port");
            throw new IllegalArgumentException("Invalid start port: " + port);
        }
        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }
}
