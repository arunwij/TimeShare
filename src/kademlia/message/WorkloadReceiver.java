package kademlia.message;

import com.google.gson.Gson;
import java.io.File;
import timeshare.scheduler.ProcessInPeer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kademlia.KadServer;
import kademlia.file.FileSender;
import kademlia.file.Serializer;
import timeshare.RunningConfiguration;

/**
 * Default receiver if none other is called
 *
 * @author Joshua Kissoon
 * @created 20140202
 */
public class WorkloadReceiver implements Receiver {

    public static ProcessInPeer pip = new ProcessInPeer();
    private KadServer server;

    public WorkloadReceiver(KadServer server) {
        this.server = server;
    }

    public WorkloadReceiver() {
    }

    @Override
    public void receive(Message incoming, int comm) throws IOException {
        
        if(!RunningConfiguration.IS_REDUNDANT){
            //System.out.println("Received message: " + incoming.toString());
        WorkloadMessage msg = (WorkloadMessage) incoming;
        System.out.println("message data: " + msg.getStringData());
        //Integer objarray[][][] = msg.getData();

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
        
        Class<?>[] parameterTypes =pip.getParameterTypes(msg.getMethodName(), msg.getClassName());
        Object senddata[]= new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
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
    }

    @Override
    public void timeout(int conversationId) throws IOException {
        System.out.println("SimpleReceiver message timeout.");
    }
}
