/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unittest;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Artista
 */
public class Server {
    public static void main(String[] args) throws IOException, InterruptedException{
        ServerSocket serverSocket = new ServerSocket(15123);
        Socket socket = serverSocket.accept();
        System.out.println ("Accepted connection : " + socket);
        
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int files = dis.readInt();
        FileOutputStream fos = null;
        
        for(int i=1;i<=files;i++){
            int size = dis.readInt();
            byte[] nameInBytes = new byte[size];
            dis.readFully(nameInBytes);
            String fileName = new String(nameInBytes, "UTF-8");
            size = dis.readInt();
            byte[] contents = new byte[size];
            dis.readFully(contents);
            fos = new FileOutputStream("pics/received/"+fileName);
            fos.write(contents);
            fos.close();
        }
        socket.close();
    }
   
}
