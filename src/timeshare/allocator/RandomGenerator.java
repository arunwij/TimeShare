/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timeshare.allocator;

import java.util.Random;

/**
 *
 * @author Great HARI
 * this class is used to create random numbers and will be used as arrival times for 
 * arrival generator
 */
public class RandomGenerator {
    
    Random r;
    /*number of arrivals per unit time*/
    double number_of_arrivals;

    public RandomGenerator(double number_of_arrivals) {
        r = new Random();
        this.number_of_arrivals = number_of_arrivals;
    }
    
    public int nextPoisson(){
        double L = Math.exp(-number_of_arrivals);
        double p = 1.0;
        int k = 0;
        
        do{
            k++;
            p *= r.nextDouble();
        }while(p>L);
        
        return k-1;
    }
    
    public static void main(String[] args){
        int numOfArrivals=2;
        RandomGenerator rg=new RandomGenerator(numOfArrivals);

        int sum=0;
        double n_trials=50.0;
        for(int i=0;i<n_trials;i++){
            int a=rg.nextPoisson();
            sum+=a;
            System.out.print(a + " ");
        }
        System.out.println("\nsum "+sum);
        System.out.println("avg: "+(double)sum/n_trials+ " -> number of arrivals : "+numOfArrivals);
    }
    
}

