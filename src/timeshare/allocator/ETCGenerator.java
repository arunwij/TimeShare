/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Great HARI
 * this class is used to generate ETC(Expected Time Completion) to be used in the simulation
 * e[i][j] represents the ETC of execute ith task in jth machine
 */
public class ETCGenerator {
    
    /*The number of machines in the Heterogenous Computing suite*/
    int m;
    double ratings[];

    /*The number of tasks in the Heterogenous Computing suite*/
    int n;
    double taskEff[];

    /*The Expected Time to Complete matrix*/
    int [][]e;
    double [][]estTime;

    /*Task Heterogeneity*/
    int T_t;

    /*Machine Heterogeneity*/
    int T_m;
    
    /*Machine Ratings*/
//    double ratings[];
    
    /*task effiient*/
//    double taskEffcient[];
    
//    public ETCGenerator(int NUM_MACHINES, int NUM_TASKS, TaskHeterogeneity TASK_HETEROGENEITY, MachineHeterogeneity MACHINE_HETEROGENEITY) {
//        m=NUM_MACHINES;
//        n=NUM_TASKS;
//        e=new int[n][m];/*e[i][j] represents the time taken to complete the ith task on the jth machine.*/
//        T_t=TASK_HETEROGENEITY.getNumericValue();
//        T_m=MACHINE_HETEROGENEITY.getNumericValue();
//    }
    
    public ETCGenerator(double ratingsArr[], double taskEffArr[]){
        ratings = ratingsArr;
        taskEff = taskEffArr;
        estTime = new double[taskEff.length][ratings.length];/*est[i][j] represents the time taken to complete the ith task on the jth machine.*/
    }

//    public ETCGenerator ETCEngine(int NUM_MACHINES, int NUM_TASKS, TaskHeterogeneity TASK_HETEROGENEITY, MachineHeterogeneity MACHINE_HETEROGENEITY) {
//        m=NUM_MACHINES;
//        n=NUM_TASKS;
//        e=new int[n][m];/*e[i][j] represents the time taken to complete the ith task on the jth machine.*/
//        T_t=TASK_HETEROGENEITY.getNumericValue();
//        T_m=MACHINE_HETEROGENEITY.getNumericValue();
//        return this;
//    }
    
    public ETCGenerator ETCEngine(double ratingsArr[], double taskEffArr[]){
        ratings = ratingsArr;
        taskEff = taskEffArr;
        estTime = new double[taskEff.length][ratings.length];/*est[i][j] represents the time taken to complete the ith task on the jth machine.*/
        return this;
    }

    private void generateETC2(){
        Random rt=new Random();
        Random rm=new Random();
        int q[]=new int[n];

        for(int i=0;i<n;i++){
            int N_t=rt.nextInt(T_t);
            q[i]=N_t;
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                int N_m=rm.nextInt(T_m);
                e[i][j]=q[i]*N_m +1;
            }
        }
    }
    
    private void generateETC(){

        for(int i=0;i<taskEff.length;i++){
            for(int j=0;j<ratings.length;j++){
                double et = 1/taskEff[i]/ratings[j];
                estTime[i][j]=et;
            }
        }
    }

//    public int[][] getETC(){
//        generateETC();
//        return e;
//    }
    
    public double[][] getETC(){
        generateETC();
        return estTime;
    }

    @Override
    public String toString(){
        String s=Arrays.deepToString(this.getETC());
        return s;
    }



    public static void main(String...args){
        TaskHeterogeneity TH = null;
        MachineHeterogeneity MH=null;
//        int ee[][]=new ETCGenerator(3,5,TH.LOW,MH.LOW).getETC();
//        System.out.println(Arrays.deepToString(ee));
        System.out.println("------------------");
        double[] ratings = {2,3,4};
        double[] taskEff = {1.2,1.4,1.7,1.8,1.9};
        double estTime[][] = new ETCGenerator(ratings,taskEff).getETC();
        System.out.println(Arrays.deepToString(estTime));
    }
    
}

