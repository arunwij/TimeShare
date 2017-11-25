package kademlia.message;

import com.google.gson.Gson;
import timeshare.scheduler.ProcessInPeer;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import kademlia.KadServer;
import kademlia.file.Serializer;


/**
 * Default receiver if none other is called
 *
 * @author Joshua Kissoon
 * @created 20140202
 */
public class WorkloadReceiver implements Receiver{
   
    public static ProcessInPeer pip = new ProcessInPeer();
    private KadServer server;
    
    public WorkloadReceiver(KadServer server){
        this.server = server;
    }
    
    public WorkloadReceiver(){}
    
    @Override
    public void receive(Message incoming, int comm) throws IOException{
        try {
            //System.out.println("Received message: " + incoming.toString());
            WorkloadMessage msg = (WorkloadMessage) incoming;
            System.out.println("message data: "+msg.getStringData());
            //Integer objarray[][][] = msg.getData();
            
            System.out.println("------------run-----"+msg.getClassName());
            pip.compileFile("data/received/"+msg.getClassName()+".java");
            
            //System.out.println(msg.data);
            
            
            int[][] out = pip.run(msg.getClassName(), msg.getMethodName(),  msg.getData(), 2);
            System.out.println("------------taks executed-----");
            for (int i = 0; i < out.length; i++) {
                for (int j = 0; j < out[0].length; j++) {
                    System.out.println(out[i][j]);
                    
                }
                
            }
            
            ResultMessage resultMsg = new ResultMessage(pip.getReturnType(),Serializer.toJson(out));
            System.out.println("Sending results to originator");
            this.server.reply(msg.getOrigin(), resultMsg, comm);
            System.out.println("done...");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WorkloadReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void timeout(int conversationId) throws IOException{
        System.out.println("SimpleReceiver message timeout.");
    }
}
