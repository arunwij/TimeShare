package kademlia.message;

import com.timeshare.RunningConfiguration;
import com.timeshare.scheduler.ProcessInPeer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kademlia.KadServer;
import kademlia.file.Serializer;


/**
 * Default receiver if none other is called
 *
 * @author Joshua Kissoon
 * @created 20140202
 */
public class WorkloadManager implements Receiver{
   
    private static int WORKLOAD_COUNT = 0;
    private static int WOKLOAD_RECEIVED = 0;
    private static List<int[][]> RESULTS = new ArrayList<int[][]>();
    private static List<Object> String_RESULTS = new ArrayList<Object>();
     ResultMessage public_msg[] =  new ResultMessage[2];
    public WorkloadManager(){}
    
    public static void IncreaseWorkloadCount(){
        WORKLOAD_COUNT++;
    }
    
    @Override
    public void receive(Message incoming, int comm) throws IOException{
        ++WOKLOAD_RECEIVED;
        if(incoming instanceof WorkloadMessage){
            System.out.println("Workload message");
            WorkloadReceiver msg = new WorkloadReceiver(RunningConfiguration.KAD_SERVER) ;
            msg.receive(incoming, comm);
        } 
        if(incoming instanceof ResultMessage){
            System.out.println("Num Workload :"+WORKLOAD_COUNT);
            System.out.println("Num Workload Recived :"+WOKLOAD_RECEIVED);
            
            System.out.println("Result message");
            ResultMessage msg = (ResultMessage) incoming;
           
            System.out.println("Result received: " + msg.toString());
            //String_RESULTS.add(msg.getData().toString());
            //System.out.println(msg.getData().toString());
            RunningConfiguration.results[RunningConfiguration.resultCount] = msg.toString();
            //RunningConfiguration.results[RunningConfiguration.resultCount+1] = msg.toString();
            RunningConfiguration.resultCount ++;
            if(RunningConfiguration.resultCount == 2){
                System.out.println("print result");
                RunningConfiguration.printResults();
            }
        } 
        

    }

    @Override
    public void timeout(int conversationId) throws IOException{
        //System.out.println("WorkladManager message timeout.");
    }
}
