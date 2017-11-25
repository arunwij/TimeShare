/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

import java.util.Map;

/**
 *
 * @author Sanjula
 */
public final class Data {

    private ConvertDataType con;
    private Map<String, Object>[] objects;
    private int currentpos = -1;
    private int maxCount = 0;

    public Data(int count) {

        con = new ConvertDataType();
        setObjectCount(count);
    }

    private void setObjectCount(int count) {
        objects = new Map[count];
        maxCount = count;
    }

    public boolean addData(Map<String, Object> obj) {
        
        boolean value = false;
        if (currentpos < (maxCount - 1)) {
            objects[++currentpos] = obj;
            value = true;
        }
        return value;
    }

    public Map<String, Object>[] getData() {
        return objects;
    }
    public int getDataCount() {
        return currentpos+1;
    }

}
