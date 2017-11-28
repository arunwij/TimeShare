package kademlia.message;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kademlia.KadServer;
import kademlia.file.FileSender;
import kademlia.file.Serializer;
import static kademlia.message.WorkloadReceiver.pip;
import timeshare.RunningConfiguration;
import timeshare.scheduler.ProcessInPeer;


/**
 * Default receiver if none other is called
 *
 * @author Joshua Kissoon
 * @created 20140202
 */
public class ExecuteReceiver implements Receiver{
    public static ProcessInPeer pip = new ProcessInPeer();
    private KadServer server;
    
    public ExecuteReceiver(KadServer server){
        this.server = server;
    }
    
    @Override
    public void receive(Message incoming, int comm) throws IOException{
        
        ExecuteMessage msg = (ExecuteMessage) incoming;
        pip.compileFile("data\\executor\\source\\host\\" + msg.getClassName() + ".java");

        System.out.println("------------run-----" + msg.getClassName());
        pip.compileFile("data\\executor\\source\\host\\" + msg.getClassName() + ".java");

        //System.out.println(msg.data);
       /* int data[][][] = (int[][][]) msg.getData();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                for (int k = 0; k < data[i][j].length; k++) {
                    System.out.print(data[i][j][k] + "  ");

                }
                System.out.println("");
            }

        }*/
        System.out.println(msg.data);
        String data[]=Serializer.getStr1d(msg.data);
        Object senddata[]= new Object[data.length];
        Class<?>[] parameterTypes =pip.getParameterTypes(msg.getMethodName(), msg.getClassName());
        for (int i = 0; i < data.length; i++) {
            Gson json = new Gson();
            System.out.println("convert :"+parameterTypes[i].toString());
            senddata[i] = json.fromJson(data[i],parameterTypes[i] );
        }
        
         pip.compileFile("data\\executor\\source\\host\\" + msg.getClassName() + ".java");
        Object out = pip.run(msg.getClassName(), msg.getMethodName(), senddata , data.length);
        System.out.println("------------taks executed-----");

        ResultMessage resultMsg = new ResultMessage(
                pip.getReturnType().toString(),
                Serializer.toJson(
                       out
                )
        );
        System.out.println("Sending results to originator");
        this.server.reply(msg.getOrigin(), resultMsg, comm);
        System.out.println("done...");
        
        FileMessage fm = new FileMessage("");
        FileListner fl = new FileListner();
        RunningConfiguration.KAD_SERVER.sendMessage(msg.getOrigin(), fm, fl);
        List<File> results = new ArrayList<File>();
        
        File folder = new File("data/executor/results/");
        results = Arrays.asList(folder.listFiles());
        FileSender.send(msg.getOrigin().getSocketAddress().getAddress(), results);


    }

    @Override
    public void timeout(int conversationId) throws IOException{
        System.out.println("SimpleReceiver message timeout.");
    }
}
