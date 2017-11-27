/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kademlia.simulations;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import timeshare.allocator.MachineHeterogeneity;
import timeshare.allocator.Simulator;
import timeshare.allocator.TaskHeterogeneity;
/**
 *
 * @author Great HARI
 */
public class Sheduling_Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        long t1=System.currentTimeMillis();
        
        /*Specify the parameters here*/
        int NUM_MACHINES=5;
        int NUM_TASKS=10;
        double ARRIVAL_RATE=3;
        int metaSetSize=5;

        TaskHeterogeneity TH=null;
        MachineHeterogeneity MH=null;

        TaskHeterogeneity th=TH.HIGH;
        MachineHeterogeneity mh=MH.HIGH;

        int no_of_simulations=1;

        /*Specify the parameters here*/        

        long sigmaMakespan = 0;
        long avgMakespan=0;
        String xmlFile="data/peer.xml";
        File xml = new File("data/peer.xml");
        Simulator se=new Simulator(NUM_MACHINES, NUM_TASKS, ARRIVAL_RATE, metaSetSize,th, mh,xml,xml,xml);

        for(int i=0;i<no_of_simulations;i++){
              
            se.newSimulation(true);
            
//            for(int j=0;j<htype.length;j++){                
//                se.setHeuristic(htype[j]);
//                //out.println(Arrays.deepToString(se.getEtc()));//////////
//                //out.println(Arrays.toString(se.getArrivals()));/////////
//                se.simulate();
//                //out.println("Makespan ="+se.getMakespan() +"strategy:"+htype[j].toString());///////////////
//                sigmaMakespan[j]+=se.getMakespan();
//                se.newSimulation(false);
//            }
            System.out.println(Arrays.deepToString(se.getEtc()));
            System.out.println(Arrays.toString(se.getArrivals()));
            se.simulate();
            System.out.println("Makespan ="+se.getMakespan() +"strategy : Hybrid Algorithm");
            sigmaMakespan += se.getMakespan();
            se.newSimulation(false);
         }

//        for(int j=0;j<htype.length;j++){
//            avgMakespan=sigmaMakespan[j]/no_of_simulations;
//
//            String hName=htype[j].toString();
//            String tmp=(String.format("%9s",hName));
//
//            DecimalFormat myFormatter = new DecimalFormat("00000000");
//            String output=myFormatter.format(avgMakespan);
//
//            System.out.println("Avg makespan for "+tmp+" heuristic for "+no_of_simulations+ " simulations is =  "+output);
//         }

        avgMakespan=sigmaMakespan/no_of_simulations;
        DecimalFormat myFormatter = new DecimalFormat("00000000");
        String output=myFormatter.format(avgMakespan);
        System.out.println("Avg makespan for Hybrid Algorithm for "+no_of_simulations+ " simulations is =  "+output);

        long t2=System.currentTimeMillis();
        System.out.println("Total time taken in the simulation = "+(t2-t1)/1000+" sec.");
    }
}

