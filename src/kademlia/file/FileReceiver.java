/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kademlia.file;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import timeshare.RunningConfiguration;
/**
 *
 * @author Artista
 */
public class FileReceiver {
    public static void receive() throws IOException{
        ServerSocket serverSocket = new ServerSocket(RunningConfiguration.FILE_PORT);
        Socket socket = serverSocket.accept();
        System.out.println ("Accepted connection : " + socket);
        
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int files = dis.readInt();
        FileOutputStream fos = null;
        
        for(int i=1;i<=files;i++){
            
            int size = dis.readInt();
            byte[] typeInBytes = new byte[size];
            dis.readFully(typeInBytes);
            String TYPE = new String(typeInBytes, "UTF-8");
            
            size = dis.readInt();
            byte[] nameInBytes = new byte[size];
            dis.readFully(nameInBytes);
            String fileName = new String(nameInBytes, "UTF-8");
            
            size = dis.readInt();
            byte[] contents = new byte[size];
            dis.readFully(contents);
            
            
            fos = new FileOutputStream(getPath(TYPE)+fileName);
            fos.write(contents);
            fos.close();
        }
        System.out.println("Files received... Socket channel closing");
        socket.close();
    }
    
    public static String getPath(String TYPE){
        switch(TYPE){
            case FileType.SOURCE:
                return "data/executor/source/host/";
            
            case FileType.KERNEL:
                return "data/executor/source/kernel/";
            
            default:
                return "data/executor/files/";
        }
    }
   
}
