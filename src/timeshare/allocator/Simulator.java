/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Great HARI
 */
public class Simulator {

    /*p[i] represents all tasks submitted to the ith machine*/
    public PriorityQueue<Task> p[];

    /*Comparator for tasks*/
    private Comparator<Task> comparator;

    /*The total number of tasks*/
    int n;

    /*The number of machines*/
    int m;

    /*The poisson arrival rate*/
    double lambda;

    /*Meta-task set size*/
    int S;

    /*Arrival time of tasks*/
    public int arrivals[];

    /*Efficiency of tasks*/
    public double taskEff[];

    /*ETC matrix*/
    public double etc[][];

    /*Machine availability time, the time at which machine i finishes all previously assigned tasks.*/
    public double mat[];

    private Scheduler eng;

    TaskHeterogeneity TH;
    MachineHeterogeneity MH;

    /*For calculating avg completion time*/
    long sigma;

    /*For calculating makespan*/
    long makespan;

    double[] ratings = {2, 3, 4, 5, 6};
    public  Map<String, Object>[][] senddata;
    public ArrayList<String> sendfiles[];
    public XmlLoader xl;

    public Simulator(int NUM_MACHINES, int NUM_TASKS, double ARRIVAL_RATE, int metaSetSize, TaskHeterogeneity th, MachineHeterogeneity mh, String xmlFile) {
        try {
            xl = new XmlLoader(xmlFile);
            xl.ProcessData();
            senddata = xl.senddata;
            sendfiles = xl.sendfiles;  //schadule this to relevent peers access by sendfiles[peer_id]
            if (!sendfiles.equals(null)) {
                for (int i = 0; i < sendfiles.length; i++) {
                    System.out.println("peer id :" + i);
                    for (int a = 0; a < sendfiles[i].size(); a++) {
                        System.out.println("\t file :" + sendfiles[i].get(a));
                    }
                }
            }
            sigma = 0;
            makespan = 0;

            MH = mh;
            TH = th;
            n = xl.getTaskCount();
            S = metaSetSize;
            m = NUM_MACHINES;
            lambda = ARRIVAL_RATE;
            comparator = new TaskComparator();
            p = new PriorityQueue[m];
            eng = new Scheduler(this);

            for (int i = 0; i < p.length; i++) {
                p[i] = new PriorityQueue<Task>(5, comparator);
            }

            generateRandoms();
            mat = new double[m];
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateRandoms() {
        arrivals = new ArrivalGenerator(n, lambda).getArrival();
        taskEff = new ArrivalGenerator(n, lambda).getTaskEfficiency();
        etc = new ETCGenerator(ratings, taskEff).getETC();
//        etc = new ETCGenerator(ratings,taskEff).getETC();
    }

    public void newSimulation(boolean generateRandoms) {
        makespan = 0;
        sigma = 0;
        if (generateRandoms) {
            generateRandoms();
        }
        for (int i = 0; i < m; i++) {
            mat[i] = 0;
            p[i].clear();
        }
    }

//    public void setHeuristic(Heuristic h){
//        this.eng.h=h;
//    }
    public long getMakespan() {
        return makespan;
    }

    public int[] getArrivals() {
        return arrivals;
    }

    public double[][] getEtc() {
        return etc;
    }

    public void mapTask(Task t, int machine) {
        t.set_eTime(etc[t.tid][machine]);
        t.set_cTime(mat[machine] + etc[t.tid][machine]);
        p[machine].offer(t);
        mat[machine] = t.cTime;
    }

    public void simulate() throws IOException {
        /*tick represents the current time*/
        int tick = 0;

        Vector<Task> metaSet = new Vector<Task>(S);
        int i1 = 0;
        int i2 = S;

        /*Initialization*/
 /*Add the first S tasks to the meta set and schedule them*/
        for (int i = i1; i < i2; i++) {
            Task t = new Task(arrivals[i], i, senddata[i],sendfiles[i]);
            metaSet.add(t);
        }
        i1 = i2;
        i2 = (int) min(i1 + S, arrivals.length);
        /*Set tick to the time of the first mapping event*/
        tick = arrivals[i1 - 1];
        eng.schedule(metaSet, tick);

        /*Set tick to the time of the next mapping event*/
        tick = arrivals[i2 - 1];

        /*Simulation Loop*/
        do {

            /*Set the current tick value*/
            if (i2 == i1) {
                tick = Integer.MAX_VALUE;
                /*Remove all the completed tasks from all the machines*/
                removeCompletedTasks(tick);
                break;
            } else {
                /*The time at which the next mapping event takes place*/
                tick = arrivals[i2 - 1];
                /*Remove all the completed tasks from all the machines*/
                removeCompletedTasks(tick);
            }
            /**/

 /*Collect next S OR (i2-i1) tasks to the meta set and schedule them*/
            metaSet = new Vector<Task>(i2 - i1);

            for (int i = i1; i < i2; i++) {
                Task t = new Task(arrivals[i], i, senddata[i],sendfiles[i]);
                metaSet.add(t);
            }
            eng.schedule(metaSet, tick);
            /**/

 /*Set values for next iteration.*/
            i1 = i2;
            i2 = (int) min(i1 + S, arrivals.length);
            /**/

        } while (!discontinueSimulation());
    }

    private void removeCompletedTasks(int currentTime) {
        for (int i = 0; i < this.m; i++) {
            if (!p[i].isEmpty()) {
                Task t = p[i].peek();
                while (t.cTime <= currentTime) {
                    sigma += t.cTime;
                    makespan = max(makespan, (long) t.cTime);
                    //out.println("Removing task "+t.tid+" at time "+currentTime);////////////////////////
                    t = p[i].poll();
                    if (!p[i].isEmpty()) {
                        t = p[i].peek();
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private boolean discontinueSimulation() {
        boolean result = true;
        for (int i = 0; i < this.m && result; i++) {
            result = result && p[i].isEmpty();
        }
        return result;
    }

    private long max(long a, long b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    private long min(long a, long b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }
}
