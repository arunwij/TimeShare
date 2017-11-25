/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timeshare.allocator;

import java.util.Map;

/**
 *
 * @author Great HARI
 * Every task has been taken to be of batch-mode type. i.e. The tasks assemble in the meta-task set and are mapped at
 * the mapping events
 */


public class Task {

    int tid;
    
    /*The arrival time, as given by the ArrivalGenerator*/
    int aTime;
    
    /*The completion time*/
    double cTime;

    /*The time required to execute the task on the machine on which it has been scheduled*/
    double eTime;
    
    /*data on the task*/
    Map<String, Object>[] data;
    
    int[] datas;

//    public Task(int arrivalTime, int task_id, Map dataOfTask){
//        tid=task_id;
//        aTime=arrivalTime;
//        data = dataOfTask;
//    }
    
    public Task(int arrivalTime, int task_id, int[] dataOfTask){
        tid=task_id;
        aTime=arrivalTime;
        datas = dataOfTask;
    }

    public Task(int arrivalTime, int task_id, Map<String, Object>[]dataOfTask) {
        tid=task_id;
        aTime=arrivalTime;
        data = dataOfTask;
    }

    public int get_aTime() {
        return aTime;
    }

    public void set_aTime(int aTime) {
        this.aTime = aTime;
    }

    public double get_cTime() {
        return cTime;
    }

    public void set_cTime(double cTime) {
        this.cTime = cTime;
    }

    public double get_eTime() {
        return eTime;
    }

    public void set_eTime(double eTime) {
        this.eTime = eTime;
    }   
}

