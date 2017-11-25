/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

import java.util.Comparator;

/**
 *
 * @author Great HARI
 */
public class TaskComparator implements Comparator<Task>{

    @Override
    public int compare(Task t1, Task t2) {
        if(t1.get_cTime()<t2.get_cTime())
            return -1;
        else if(t1.get_cTime()==t2.get_cTime())
            return 0;
        else if(t1.get_cTime()>t2.get_cTime())
            return 1;
        return -2;
    }
}
