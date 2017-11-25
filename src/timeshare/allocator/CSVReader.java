/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Sanjula
 */
public class CSVReader {
    
    
    public String read(String path){
    
        String csvFile = path;//"/Users/mkyong/csv/country.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        StringBuilder stringBuffer =new StringBuilder(); 
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
               
                // use comma as separator
                stringBuffer.append("["+line+"],");

               

            }
           stringBuffer.deleteCharAt( stringBuffer.lastIndexOf(","));
            System.out.println(stringBuffer.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }
    
}
