/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unittest;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 *
 * @author Artista
 */
public class Client {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        int bytesRead;
        int currentTot = 0;
        Socket socket = new Socket("192.168.1.30", 15123);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int size = dis.readInt();
        byte[] nameInBytes = new byte[size];
        dis.readFully(nameInBytes);
        String fileName = new String(nameInBytes, "UTF-8");
        size = dis.readInt();
        byte[] contents = new byte[size];
        dis.readFully(contents);
        
        
        FileOutputStream fos = new FileOutputStream("pics/received/"+fileName);
        fos.write(contents);
        fos.close();
        socket.close();
    }
}
