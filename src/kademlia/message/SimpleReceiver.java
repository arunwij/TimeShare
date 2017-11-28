package kademlia.message;

import java.io.IOException;
import kademlia.file.Serializer;


/**
 * Default receiver if none other is called
 *
 */
public class SimpleReceiver implements Receiver{
   
    
    @Override
    public void receive(Message incoming, int conversationId){
        System.out.println("Received message: " + incoming);
    }

    @Override
    public void timeout(int conversationId) throws IOException{
        System.out.println("SimpleReceiver message timeout.");
    }
}
