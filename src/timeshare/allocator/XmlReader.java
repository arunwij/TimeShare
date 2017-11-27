/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

import timeshare.RunningConfiguration;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Sanjula
 */
public class XmlReader {

    private File xml,javaFile,csv;

    public XmlReader(File xml, File javaFile, File csv) {
        this.xml = xml;
        this.javaFile = javaFile;
        this.csv = csv;
    }

    

    public void run() throws Exception {
        // XmlLoader xml = new XmlLoader("data/peer.xml");
        // xml.ProcessData();
        //CSVReader csv = new CSVReader();
        //System.out.println(csv.read("bank-data.csv"));
        long t1 = System.currentTimeMillis();
        int NUM_MACHINES = RunningConfiguration.getPeersCount();
        int NUM_TASKS = 2;
        double ARRIVAL_RATE = 3;
        int metaSetSize = NUM_TASKS;

        TaskHeterogeneity TH = null;
        MachineHeterogeneity MH = null;

        TaskHeterogeneity th = TH.HIGH;
        MachineHeterogeneity mh = MH.HIGH;

        int no_of_simulations = 1;

        //Specify the parameters here
        long sigmaMakespan = 0;
        long avgMakespan = 0;
        Simulator se = new Simulator(NUM_MACHINES, NUM_TASKS, ARRIVAL_RATE, metaSetSize, th, mh, xml,javaFile,csv);

        for (int i = 0; i < no_of_simulations; i++) {

            se.newSimulation(true);
            System.out.println(Arrays.deepToString(se.getEtc()));
            System.out.println(Arrays.toString(se.getArrivals()));
            se.simulate();
            System.out.println("Makespan =" + se.getMakespan() + "strategy : Hybrid Algorithm");
            sigmaMakespan += se.getMakespan();
            se.newSimulation(false);
        }

        avgMakespan = sigmaMakespan / no_of_simulations;
        DecimalFormat myFormatter = new DecimalFormat("00000000");
        String output = myFormatter.format(avgMakespan);
        System.out.println("Avg makespan for Hybrid Algorithm for " + no_of_simulations + " simulations is =  " + output);

        long t2 = System.currentTimeMillis();
        System.out.println("Total time taken in the simulation = " + (t2 - t1) / 1000 + " sec.");
    }

}
