/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kademlia.file;

import timeshare.RunningConfiguration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.ListIterator;
import kademlia.message.FileListner;
import kademlia.message.FileMessage;
import kademlia.node.Node;
/**
 *
 * @author Artista
 */
public class FileSender {
    private final InetSocketAddress fileSocketAddress;
    private InetSocketAddress communicationSocketAddress;
    private final File file;
 
    public FileSender(InetAddress inetAddress, File file) throws IOException{
        this.fileSocketAddress = new InetSocketAddress(inetAddress,RunningConfiguration.FILE_PORT);
        this.file = file;
    }
    
    public static void send(InetSocketAddress inetSocketAddress, File file) throws IOException{
        RunningConfiguration.KAD_SERVER.sendMessage(inetSocketAddress, new FileMessage(file.getName()), new FileListner());
        FileSender nioClient = new FileSender(inetSocketAddress.getAddress(),file);
        SocketChannel socketChannel = nioClient.createChannel();
        nioClient.sendFile(socketChannel);
    }
    
    public static void send(Node to, File file) throws IOException{
        RunningConfiguration.KAD_SERVER.sendMessage(to, new FileMessage(file.getName()), new FileListner());
        FileSender nioClient = new FileSender(to.getSocketAddress().getAddress(),file);
        SocketChannel socketChannel = nioClient.createChannel();
        nioClient.sendFile(socketChannel);
    }
    
    public static void send(Node to, File file,String filepath) throws IOException{
        RunningConfiguration.KAD_SERVER.sendMessage(to, new FileMessage(filepath+file.getName()), new FileListner());
        FileSender nioClient = new FileSender(to.getSocketAddress().getAddress(),file);
        SocketChannel socketChannel = nioClient.createChannel();
        nioClient.sendFile(socketChannel);
    }
    
    public static void send(Node to,List<File> files,String filepath) throws IOException{
        ListIterator ltr = files.listIterator();
        while(ltr.hasNext()){
            File file = (File) ltr.next();
            RunningConfiguration.KAD_SERVER.sendMessage(to, new FileMessage(filepath+file.getName()), new FileListner());
            FileSender nioClient = new FileSender(to.getSocketAddress().getAddress(),file);
            SocketChannel socketChannel = nioClient.createChannel();
            nioClient.sendFile(socketChannel);
        }
    }
    
/**
* Establishes a socket channel connection
*
* @return
*/
    public SocketChannel createChannel() {
        SocketChannel socketChannel = null;
        
        try {
            socketChannel = SocketChannel.open();
            SocketAddress socketAddress = this.fileSocketAddress;
            socketChannel.connect(socketAddress);
            System.out.println("Connected..Now sending the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socketChannel;
    }

    
    public void sendFile(SocketChannel socketChannel) {
        RandomAccessFile aFile = null;
        try {
            //File file = new File("data\\web.exe");
            aFile = new RandomAccessFile(this.file, "r");
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            
            while (inChannel.read(buffer) > 0) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }
        
            Thread.sleep(400);
            System.out.println("End of file reached..");
            socketChannel.close();
            aFile.close();
            
        } catch (FileNotFoundException e ) { 
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}