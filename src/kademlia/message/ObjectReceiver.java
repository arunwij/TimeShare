package kademlia.message;

import java.io.IOException;


/**
 * Default receiver if none other is called
 *
 */
public class ObjectReceiver implements Receiver{
   
    
    @Override
    public void receive(Message incoming, int conversationId){
        System.out.println("Received message: " + incoming);
    }

    @Override
    public void timeout(int conversationId) throws IOException{
        System.out.println("SimpleReceiver message timeout.");
    }
}
