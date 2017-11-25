package kademlia.message;

import timeshare.scheduler.ProcessInPeer;
import java.io.IOException;
import java.util.Map;
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
        //System.out.println("Received message: " + incoming.toString());
        WorkloadMessage msg = (WorkloadMessage) incoming; 
        System.out.println("message data: "+msg.getStringData());
        String objarray[][][] =  (String[][][]) msg.getData();
       
        System.out.println("------------run-----"+msg.getClassName());
        pip.compileFile("data/received/"+msg.getClassName()+".java");
        String out[][] = (String[][])pip.run(msg.getClassName(), msg.getMethodName(), objarray, 2);
        System.out.println("------------run-----");
        
        for(String[] i: out){
            for(String j: i){
                System.out.println(j);
            }
        }
        
        ResultMessage resultMsg = new ResultMessage(msg.getType(),Serializer.toJson(out));
        System.out.println("Sending results to originator");
        this.server.reply(msg.getOrigin(), resultMsg, comm);
        System.out.println("done...");
        
    }

    @Override
    public void timeout(int conversationId) throws IOException{
        System.out.println("SimpleReceiver message timeout.");
    }
}
