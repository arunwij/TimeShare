/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kademlia.file;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Artista
 */
public class FileSender {
    
    public static String FILE_TYPE = null;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        Socket socket = new Socket("192.168.1.20", 15123);

        List<File> files = new <File>ArrayList();
        File a = new File("JCudaVectorAdd.java");
        File b = new File("peer.xml");
        File c = new File("desert.jpg");
        File d = new File("kernel.jpg");
        File e = new File("Tharumini.mp3");
        files.add(a);
        files.add(b);
        files.add(c);
        files.add(d);
        files.add(e);

        send(socket, files);
    }
    
    public static void send(Socket socket, List<File> files) throws IOException{
        ListIterator li = files.listIterator();
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(files.size());
        while (li.hasNext()) {
            File file = (File) li.next();
            FILE_TYPE = getFileType(file.getName());
            byte[] typeInBytes = FILE_TYPE.getBytes("UTF-8");
            byte[] nameInBytes = file.getName().getBytes("UTF-8");
            File transferFile = new File(file.getName());
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
