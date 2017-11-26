/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

import timeshare.RunningConfiguration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kademlia.file.Serializer;
import kademlia.message.WorkloadManager;
import kademlia.message.WorkloadMessage;
import kademlia.message.WorkloadReceiver;
import kademlia.routing.Contact;
/**
 *
 * @author Great HARI
 */
public class Scheduler {
    Simulator sim;
    
    public XmlLoader xl;
    public  Map<String, Object>[][] senddata ;
    public  ArrayList<String>[] sendfiles ;
    
    public Scheduler(Simulator sim) throws ClassNotFoundException, IOException{
        this.sim=sim;
        xl = new XmlLoader("data/peer.xml");
        xl.ProcessData();
        senddata= sim.senddata;
        sendfiles= sim.sendfiles;
        
    }
    
    public void schedule(Vector<Task> metaSet,int currentTime) throws IOException{
        /*If any machine has zero assigned tasks then set mat[] for that machine to be the current time.*/
        for(int i=0;i<sim.m;i++){
            if(sim.p[i].isEmpty()){
                sim.mat[i]=currentTime;
            }
        }
        
        schedule_Hybrid(metaSet,currentTime);
    }
    
    /*This function is a helper of schedule_Htbrid()*/
    private void mapTaskCopy(Task t, int machine, Vector<TaskWrapper> pCopy[], double mat[],int index){
        t.set_eTime(sim.etc[t.tid][machine]);
        t.set_cTime( mat[machine]+sim.etc[t.tid][machine] );

        TaskWrapper tw=new TaskWrapper(index,t);
        pCopy[machine].add(tw);
        mat[machine]=t.cTime;
    }
    
    private void schedule_Hybrid(Vector<Task> metaSet, int currentTime) throws IOException {

         /*We don't directly add the tasks to the p[] matrix of simulator rather add in this copy first*/
        Vector<TaskWrapper> pCopy[]=new Vector[sim.m];

        /*Copy of mat matrix*/
        double[] matCopy=new double[sim.m];
        for(int i=0;i<sim.m;i++){
            matCopy[i]=sim.mat[i];
            /*Also initialize the processors Copy , pCopy[]*/
            pCopy[i]=new Vector<TaskWrapper>(4);
        }

        /*First schedule that tasks according to min-min*/
        schedule_MinMinCopy(metaSet,currentTime,pCopy,matCopy);

        /*Find avg completion time for each machine*/
        long sigmaComplTime=0;
        long avgComplTime=0;
        for(int i=0;i<sim.m;i++)
            sigmaComplTime+=matCopy[i];
        avgComplTime=sigmaComplTime/sim.m;
        int k=0;        
        /*Reshufffle tasks from machines which have higher completion time than average to lower compl time machines*/
        for(int i=0;i<sim.m;i++){
            if(matCopy[i]<=avgComplTime)continue;
            k=0;
            while(k+1<=pCopy[i].size()){
                TaskWrapper tw=pCopy[i].elementAt(k);
                Task t=tw.getTask();
                /*Remap this task to another machine with completion time less than average completion time
                 such that the difference of the new completion time of the machine and the average comple-
                 -tion time becomes the minimum.
                 This is analogous to best-fit algorithm
                */
                int delta=Integer.MIN_VALUE;
                int machine=i;
                for(int j=0;j<sim.m;j++){
                    if(j==i || matCopy[j]>=avgComplTime)continue;
                    if(( matCopy[j]+sim.etc[t.tid][j] < avgComplTime) && Math.abs( matCopy[j]+sim.etc[t.tid][j] - avgComplTime) > delta){
                        delta= (int) Math.abs( matCopy[j]+sim.etc[t.tid][j] - avgComplTime);
                        machine=j;
                    }
                }
                /*Map the task to the new machine*/
                if(machine!=i){
                    pCopy[i].remove(tw);
                    matCopy[i]-=sim.etc[t.tid][i];
                    mapTaskCopy(t,machine,pCopy,matCopy,tw.getIndex());

                    /*Note that the new avg completion time may be different from the old one*/
                    //sigmaComplTime-=sim.etc[t.tid][i];
                    //sigmaComplTime+=sim.etc[t.tid][machine];
                    //avgComplTime=sigmaComplTime/sim.m;
                    /*Not included because it increases makespan slightly*/
                }
                k++;
            }
        }
        /*Copy matCopy[] and pCopy[] back to original matrices*/
        for(int i=0;i<sim.m;i++){
            for(int j=0;j<pCopy[i].size();j++){
                
                TaskWrapper tbu=pCopy[i].elementAt(j);
                List list = RunningConfiguration.LOCAL_JKNODE.getRoutingTable().getAllContacts();
                Contact c = (Contact) list.get(i);
                sim.mapTask(tbu.getTask(), i);
                System.out.println("Adding task "+tbu.getTask().tid+" to machine "+i+". Completion time = "+tbu.getTask().cTime+" @time "+currentTime);
                int datacount = 3;
                System.out.println("test");
                Object [] params =new Object[datacount];
                System.err.println("test 1");
                for (int cc = 0; cc < datacount; cc++) {
                    for (Map.Entry m : tbu.getTask().data[cc].entrySet()) {
                        params[cc] =Serializer.toJson( m.getValue());
                    }
                }
                for (int l = 0; l < tbu.getTask().files.size(); l++) {
                    System.out.println(tbu.getTask().files.get(l).toString());
                }
/*
                int [][]arr =(int[][])params[0];
                int [][][] intparams= new int[datacount][arr.length][arr[0].length];
                
                for (int a=0;a<datacount;a++){
                    intparams[a]=(int[][])params[a];
                }**/
                
                WorkloadMessage wmsg = new WorkloadMessage(xl.className,xl.methodName,Serializer.STR1D,Serializer.toJson(params));
                WorkloadManager wmgr = new WorkloadManager();                
                RunningConfiguration.KAD_SERVER.sendMessage(c.getNode(), wmsg, wmgr);
            }
        }
        
        /*By doing this we are preserving the order in which tasks should have been mapped to the machines*/
        System.arraycopy(matCopy, 0, sim.mat, 0, sim.m);
    }
    
    private void schedule_MinMinCopy(Vector<Task> metaSet, int currentTime, Vector<TaskWrapper>[] pCopy, double[] matCopy){

        /*We do not actually delete the task from the meta-set rather mark it as removed*/
        boolean[] isRemoved=new boolean[metaSet.size()];

        /*Matrix to contain the completion time of each task in the meta-set on each machine.*/
        double c[][]=schedule_MinMinCopyHelper(metaSet,matCopy);
        int i=0;

        int tasksRemoved=0;
        do{
            double minTime=Integer.MAX_VALUE;
            int machine=-1;
            int taskNo=-1;
            /*Find the task in the meta set with the earliest completion time and the machine that obtains it.*/
            for(i=0;i<metaSet.size();i++){
                if(isRemoved[i])continue;
                for(int j=0;j<sim.m;j++){
                    if(c[i][j]<minTime){
                        minTime=c[i][j];
                        machine=j;
                        taskNo=i;
                    }
                }
            }
            Task t=metaSet.elementAt(taskNo);
            this.mapTaskCopy(t,machine,pCopy,matCopy,taskNo);

            /*Mark this task as removed*/
            tasksRemoved++;
            isRemoved[taskNo]=true;
            //metaSet.remove(taskNo);

            /*Update c[][] Matrix for other tasks in the meta-set*/
            for(i=0;i<metaSet.size();i++){
                if(isRemoved[i])continue;
                else{
                    c[i][machine]=matCopy[machine]+sim.etc[metaSet.get(i).tid][machine];
                }
            }

        }while(tasksRemoved!=metaSet.size());
    }
    
    /*This function is a helper of schedule_MinMin()*/
    private double[][] schedule_MinMinCopyHelper(Vector<Task> metaSet, double[] matCopy){
        double c[][]=new double[metaSet.size()][sim.m];
        int i=0;
        for(Iterator it=metaSet.iterator();it.hasNext();){
            Task t=(Task)it.next();
            for(int j=0;j<sim.m;j++){
                c[i][j]=matCopy[j]+sim.etc[t.tid][j];
            }
            i++;
        }
        return c;
    }
}

