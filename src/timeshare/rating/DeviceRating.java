/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.rating;

import timeshare.*;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.jocl.CL.*;
import org.jocl.*;
import java.nio.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Artista
 */
public class DeviceRating extends GetDeviceInfo {
    
    static int numPlatforms[];
    static cl_platform_id platforms[];
    static int numDevices[];
    static cl_device_id deviceArray[];
    static List<ClDevice> devices = new <ClDevice>ArrayList();
    
    public static void main(String args[]){
        deviceQuery();
    }
    
    public static String deviceQuery(){
        numPlatforms = new int[1];
        clGetPlatformIDs(0, null, numPlatforms);
        platforms = new cl_platform_id[numPlatforms[0]];
        clGetPlatformIDs(platforms.length, platforms, null);
        
        for(cl_platform_id platformID: platforms){
            String platformName = GetDeviceInfo.getString(platformID,CL_PLATFORM_NAME);
            numDevices = new int[1];
            clGetDeviceIDs(platformID,CL_DEVICE_TYPE_ALL,0,null,numDevices);
            deviceArray = new cl_device_id[numDevices[0]];
            clGetDeviceIDs(platformID,CL_DEVICE_TYPE_ALL,numDevices[0],deviceArray,null);
            
            for(cl_device_id deviceID : deviceArray){
                devices.add(new ClDevice(deviceID));
            }
           // devices.addAll(Arrays.asList(deviceArray));
        }
        
        Gson gson = new Gson();
        String json =gson.toJson(devices);
        File deviceInfo = new File("logs/devices.json");
        
        if(deviceInfo.exists() && !deviceInfo.isFile()){
            try {
                FileWriter writer = new FileWriter(deviceInfo);
                writer.write(json);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(DeviceRating.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                deviceInfo.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DeviceRating.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        return json;
    }
}
