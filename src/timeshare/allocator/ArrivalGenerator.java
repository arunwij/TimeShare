/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timeshare.allocator;

/**
 *
 * @author Great HARI
 * this class is use to create the arrival time of the tasks for simulation
 */
import java.util.Arrays;
import static java.lang.System.out;
import java.util.Calendar;
import java.util.Random;


public class ArrivalGenerator {
    /*Stores the arrival time for each task*/
    int[] arrival_time;
    
    double[] taskEfficiency;
    /*number of arrivals per unit time*/
    double number_of_arrivals;
    
    public ArrivalGenerator(int NUM_TASKS, double Lambda){
        arrival_time=new int[NUM_TASKS];
        taskEfficiency=new double[NUM_TASKS];
        number_of_arrivals=Lambda;
    } 
    
    public ArrivalGenerator ArrivalGenerator(int NUM_TASKS, double Lambda){
        arrival_time=new int[NUM_TASKS];
        taskEfficiency=new double[NUM_TASKS];
        number_of_arrivals=Lambda;
        return this;
    }
    
    public void generateArrival(){
//        RandomGenerator r = new RandomGenerator(number_of_arrivals);
//        arrival_time[0] = r.nextPoisson();
        for(int i =0;i<arrival_time.length;i++){
//            arrival_time[i] = arrival_time[i-1] + r.nextPoisson();
            long millis = Calendar.getInstance().get(Calendar.MILLISECOND);
            arrival_time[i] = (int) millis;
        }
    }
    
    public int[] getArrival(){
        generateArrival();
        return arrival_time;
    }
    
    @Override
    public String toString(){
        String s = Arrays.toString(this.getArrival());
        return s;
    }
    
    public void rDouble() {
        double leftLimit = 0D;
        double rightLimit = 1D;
        for(int i =0;i<taskEfficiency.length;i++){
            double generatedDouble = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
            taskEfficiency[i] = generatedDouble;
        }
    }
    
    public double[] getTaskEfficiency(){
        rDouble();
        return taskEfficiency;
    }
    
    public static void main(String[] args){
        int numOfArrivals = 3;
        int numTasks = 5;
        System.out.println(Arrays.toString(new ArrivalGenerator(numTasks,numOfArrivals).getArrival()));
        System.out.println("eff : "+ Arrays.toString(new ArrivalGenerator(numTasks,numOfArrivals).getTaskEfficiency()));
        long millis = Calendar.getInstance().get(Calendar.MILLISECOND);
        System.out.println("hghj : "+ millis);
    }
}

