/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unittest;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Artista
 */
public class Client {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
     
        Socket socket = new Socket("192.168.1.20", 15123);
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
