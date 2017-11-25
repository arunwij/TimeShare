/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

/**
 *
 * @author Great HARI
 */
public enum MachineHeterogeneity {
    TEST (5),
    LOW (10) ,
    HIGH (1000);

    private int h;

    private MachineHeterogeneity(int h){
        this.h=h;
    }

    public int getNumericValue(){
        return h;
    }
}

