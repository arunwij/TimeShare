/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kademlia.message;

import java.io.IOException;

/**
 *
 * @author Artista
 */
public class FileListner implements Receiver{

    @Override
    public void receive(Message incoming, int conversationId) throws IOException {
        System.out.println("Received "+ incoming);
        //Deprecated
        //.receive(incoming.toString());
    }

    @Override
    public void timeout(int conversationId) throws IOException {
        
    }
    
}
