/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.rating;

import static org.jocl.CL.CL_DEVICE_GLOBAL_MEM_SIZE;
import static org.jocl.CL.CL_DEVICE_MAX_CLOCK_FREQUENCY;
import static org.jocl.CL.CL_DEVICE_MAX_COMPUTE_UNITS;
import static org.jocl.CL.CL_DEVICE_PLATFORM;
import static org.jocl.CL.CL_DEVICE_TYPE;
import org.jocl.cl_device_id;



/**
 *
 * @author Artista 
 */
//git test
public class ClDevice {
    
    long platformId;
    long type;
    double rating;
    
    public ClDevice(cl_device_id device){
        this.platformId = GetDeviceInfo.getLong(device, CL_DEVICE_PLATFORM);
//        this.name = GetInfo.getString(device, CL_DEVICE_NAME);
//        this.clVersion = GetInfo.getString(device, CL_DEVICE_VERSION);
        this.type = GetDeviceInfo.getLong(device, CL_DEVICE_TYPE);
        int maxComputeUnits = GetDeviceInfo.getInt(device, CL_DEVICE_MAX_COMPUTE_UNITS);
        double maxClockFrequency = ((double)GetDeviceInfo.getInt(device, CL_DEVICE_MAX_CLOCK_FREQUENCY));
        double globalMemSize = (double)GetDeviceInfo.getLong(device, CL_DEVICE_GLOBAL_MEM_SIZE);
        this.rating = calcRating(maxComputeUnits,maxClockFrequency,globalMemSize);
    }
    
    private double calcRating(int maxComputeUnits,double maxClockFrequency,double globalMemSize){
        //rating is based on max compute units, max clock frequency, global mem size
        return (maxClockFrequency/1000) * maxComputeUnits * (globalMemSize/(10^8));
    }
}
