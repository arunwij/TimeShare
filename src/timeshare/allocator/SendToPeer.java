/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

import timeshare.scheduler.ProcessInPeer;
import java.io.IOException;
import java.util.Map;
import kademlia.JKademliaNode;
import kademlia.file.Serializer;
import kademlia.message.WorkloadReceiver;
import kademlia.message.WorkloadMessage;
import kademlia.node.KademliaId;

/**
 *
 * @author Sanjula
 */
public final class SendToPeer {

    private static ProcessInPeer pip = new ProcessInPeer();
    private String javafile;
    private Object sObj[];
    private Object data;
    private int peercount;

    public synchronized <Any> Any send(String className, String methodName, Map<String, Object> data[], int datacount) throws IOException {
        pip.compileFile(javafile);
        Object [] params =new Object[datacount];
        
        for (int cc = 0; cc < datacount; cc++) {
                for (Map.Entry m : data[cc].entrySet()) {
                    
                    params[cc] = m.getValue();
                    
                   
                }

            }
        int [][]arr =(int[][])params[0];
        int [][][] intparams= new int[datacount][arr.length][arr[0].length];
        for (int a=0;a<datacount;a++)
            intparams[a]=(int[][])params[a];
        
        
//        try{
//            JKademliaNode kad1 = new JKademliaNode("Joshua", new KademliaId("12345678901234567890"), 7574);
//            JKademliaNode kad2 = new JKademliaNode("Crystal", new KademliaId("12345678901234567891"), 7572);
//            kad1.getServer().sendMessage(kad2.getNode(), new WorkloadMessage(className,methodName,Serializer.INT3D,Serializer.toJson(intparams)), new WorkloadReceiver());
//        }
//        catch (IOException e){
//            
//        }
        
        return null;
        //return pip.run(className, methodName, intparams, datacount);
    }

    public synchronized void uploadJavaFile(String file) {
        System.out.println("file complied");
        this.javafile = file;
        pip.compileFile(javafile);
    }


}
