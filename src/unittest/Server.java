/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unittest;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Artista
 */
public class Server {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(15123);
        Socket socket = serverSocket.accept();
        String fileName = "night.jpg";
        byte[] nameInBytes = fileName.getBytes("UTF-8");
        System.out.println ("Accepted connection : " + socket);
        File transferFile = new File(fileName);
        byte[] contents = new byte[(int) transferFile.length()];
        FileInputStream fin = new FileInputStream(transferFile);
        BufferedInputStream bin = new BufferedInputStream(fin);
        bin.read(contents,0,contents.length);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(nameInBytes.length);
        dos.write(nameInBytes);
        dos.writeInt(contents.length);
        dos.write(contents);
        dos.flush();
        socket.close ();
        System.out.println ("File transfer complete");
    }
    
}
