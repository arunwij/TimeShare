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
public class TaskWrapper {

    /*index represents that index of this task in the metaset*/
    private int i;

    /*The task which this wrapper is wrapping*/
    private Task t;

    public TaskWrapper(int index, Task task){
        i=index;
        t=task;
    }

    public int getIndex() {
        return i;
    }

    public Task getTask() {
        return t;
    }
}
