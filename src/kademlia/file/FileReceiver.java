/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kademlia.file;

import timeshare.RunningConfiguration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *
 * @author Artista
 */
  public class FileReceiver {

    private String fileName;

    //custom constructor for accept files
    public FileReceiver(String fileName) {
        this.fileName = fileName;
    }

    public static void receive(String fileName) {
        FileReceiver nioServer = new FileReceiver(fileName);
        SocketChannel socketChannel = nioServer.createServerSocketChannel();
        nioServer.readFileFromSocket(socketChannel);
    }

    public FileReceiver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SocketChannel createServerSocketChannel() {

        ServerSocketChannel serverSocketChannel = null;
        SocketChannel socketChannel = null;

        try {
            System.out.println("File receiver listening at port: " + RunningConfiguration.FILE_PORT);
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(RunningConfiguration.FILE_PORT));
            socketChannel = serverSocketChannel.accept();
            System.out.println("Connection established...." + socketChannel.getRemoteAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return socketChannel;
    }

    /**
     * Reads the bytes from socket and writes to file
     *
     * @param socketChannel
     */
    public void readFileFromSocket(SocketChannel socketChannel) {

        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile( this.fileName, "rw");
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            FileChannel fileChannel = aFile.getChannel();
            while (socketChannel.read(buffer) > 0) {
                buffer.flip();
                fileChannel.write(buffer);
                buffer.clear();
            }
           // Thread.sleep(1000);
            fileChannel.close();
            System.out.println("End of file reached..Closing channel");
            socketChannel.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }/*} catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
