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
        
        List<File> files = new <File>ArrayList();
        File a = new File("city.jpg");
        File b = new File("night.jpg");
        
       
        
    }
    
    public void send(List<File> files, Socket socket) throws IOException{
        ListIterator li = files.listIterator();
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(files.size());
        while(li.hasNext()){
            File file = (File) li.next();
            byte[] nameInBytes = file.getName().getBytes("UTF-8");
            File transferFile = new File(file.getName());
            byte[] contents = new byte[(int) transferFile.length()];
            FileInputStream fin = new FileInputStream(transferFile);
            BufferedInputStream bin = new BufferedInputStream(fin);
            bin.read(contents,0,contents.length);
            
            dos.writeInt(nameInBytes.length);
            dos.write(nameInBytes);
            dos.writeInt(contents.length);
            dos.write(contents);     
        }
        dos.flush();
        socket.close ();
        System.out.println ("File transfer complete");
    }
    
}
