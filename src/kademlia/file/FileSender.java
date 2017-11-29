/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kademlia.file;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import timeshare.RunningConfiguration;

/**
 *
 * @author Artista
 */
public class FileSender {
    
    public static String FILE_TYPE = null;
    

    public static void send(InetAddress ip, List<File> files) throws IOException{
        send(new Socket(ip,RunningConfiguration.FILE_PORT),files);
    }
    
    public static void send(Socket socket, List<File> files) throws IOException{
        
        ListIterator li = files.listIterator();
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(files.size());
        while (li.hasNext()) {
            File file = (File) li.next();
           // System.out.println(file.getPath()+"---"+file.getName());
         
                FILE_TYPE = getFileType(file.getName());
                byte[] typeInBytes = FILE_TYPE.getBytes("UTF-8");
                byte[] nameInBytes = file.getName().getBytes("UTF-8");
                File transferFile = new File(file.getPath());
                byte[] contents = new byte[(int) transferFile.length()];
                FileInputStream fin = new FileInputStream(transferFile);
                BufferedInputStream bin = new BufferedInputStream(fin);
                bin.read(contents, 0, contents.length);

                dos.writeInt(typeInBytes.length);
                dos.write(typeInBytes);
                dos.writeInt(nameInBytes.length);
                dos.write(nameInBytes);
                dos.writeInt(contents.length);
                dos.write(contents);
            
        }
        dos.flush();
        System.out.println("File transfer complete... closing the stream");
        socket.close();
    }
    
    public static String getExtention(String fileName){
        int i = fileName.lastIndexOf(".");
        if( i > 0){
            return fileName.substring(i+1);
        }
        return null;
    }
    
    public static String getFileType(String fileName){
        String extention = getExtention(fileName);
        if(extention.equals(FileType.SOURCE)){
            return FileType.SOURCE;
        }else if(extention.equals(FileType.KERNEL)){
            return FileType.KERNEL;
        }else{
            return FileType.DATA;
        }
    }
}
