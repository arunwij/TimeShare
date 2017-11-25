/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timeshare.allocator;

/**
 *
 * @author Great HARI
 */
public enum TaskHeterogeneity {

     TEST (5),
     LOW (100),
     HIGH (3000);

     private int h;
     
     private TaskHeterogeneity(int h){
        this.h=h;
     }

     public int getNumericValue(){
        return h;
     }
}
