/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Sanjula
 */
/*
 * JCuda - Java bindings for NVIDIA CUDA driver and runtime API
 * http://www.jcuda.org
 *
 * Copyright 2011 Marco Hutter - http://www.jcuda.org
 */
import static jcuda.driver.JCudaDriver.*;

import java.io.*;

import jcuda.*;
import jcuda.driver.*;
import static jcuda.runtime.JCuda.cudaGetDeviceProperties;
import static jcuda.runtime.JCuda.cudaMallocHost;
import jcuda.runtime.cudaDeviceProp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This is a sample class demonstrating how to use the JCuda driver
 * bindings to load and execute a CUDA vector addition kernel.
 * The sample reads a CUDA file, compiles it to a PTX file
 * using NVCC, loads the PTX file as a module and executes
 * the kernel function. <br />
 */
public  class JCudaVectorAdd
{
	
	List<String> fileList;
    private static final String INPUT_ZIP_FILE = "C:\\zip\\uploads.zip";
    private static final String OUTPUT_FOLDER = "C:\\zip";
	
	public JCudaVectorAdd(){
		
		
	}
    public static int[][] test( int[][] a,int[][] b )
    { 
       

        return a;
       // }
    }

    /**
     * Unzip it
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public void unZipIt(String zipFile, String outputFolder){

     byte[] buffer = new byte[1024];

     try{

    	//create output directory is not exists
    	File folder = new File(OUTPUT_FOLDER);
    	if(!folder.exists()){
    		folder.mkdir();
    	}

    	//get the zip file content
    	ZipInputStream zis =
    		new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();

    	while(ze!=null){

    	   String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);

          // System.out.println("file unzip : "+ newFile.getAbsoluteFile());

            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
    	}

        zis.closeEntry();
    	zis.close();

    	System.out.println("Done");
        
        
        
          
// ... the code being measured ...    
        
    }catch(IOException ex){
       ex.printStackTrace();
    }
   }
	
    /**
     * Entry point of this sample
     *
     * @param args Not used
     * @throws IOException If an IO error occurs
     */
	public static String[][] testass(String[][] a,String[][] b){
		//System.out.println("test method called");
		//String c[][] = new String[a.length][a[0].length];
		//for(int x=0;x<a.length;x++)
		//	for(int y=0;y<a[0].length;y++)
				//System.out.println(a[x][y]);
			//	c[x][y]=Integer.parseInt(a[x][y])+Integer.parseInt(b[x][y]);
				//c[x][y]=a[x][y]+b[x][y];
		
		return a;
	} 
	
	
	
	public static float[] floatTest (float[] a,float[] b){
		
		float[] c= new float[a.length]; 
		for(int x=0;x<a.length;x++)
			c[x]=a[x]+b[x];
		
		
		return c;
	} 
	 
    public static float[] add(int a) throws IOException
    {
        // Enable exceptions and omit all subsequent error checks
        JCudaDriver.setExceptionsEnabled(true);

        // Create the PTX file by calling the NVCC
        String ptxFileName = preparePtxFile("data/received/JCudaVectorAddKernel.cu");
        //String ptxFileName = "JCudaVectorAddKernel.ptx";
        
        // Initialize the driver and create a context for the first device.
        cuInit(0);
        CUdevice device = new CUdevice();
        cuDeviceGet(device, 0);
        CUcontext context = new CUcontext();
        cuCtxCreate(context, 0, device);

        // Load the ptx file.
        CUmodule module = new CUmodule();
        cuModuleLoad(module, ptxFileName);

        // Obtain a function pointer to the "add" function.
        CUfunction function = new CUfunction();
        cuModuleGetFunction(function, module, "add");

        int numElements = a;

        // Allocate and fill the host input data
        float hostInputA[] = new float[numElements];
        float hostInputB[] = new float[numElements];
        for(int i = 0; i < numElements; i++)
        {
            hostInputA[i] = (float)i;
            hostInputB[i] = (float)i;
        }

        // Allocate the device input data, and copy the
        // host input data to the device
        CUdeviceptr deviceInputA = new CUdeviceptr();
        cuMemAlloc(deviceInputA, numElements * Sizeof.FLOAT);
        cuMemcpyHtoD(deviceInputA, Pointer.to(hostInputA),numElements * Sizeof.FLOAT);
        
        CUdeviceptr deviceInputB = new CUdeviceptr();
        cuMemAlloc(deviceInputB, numElements * Sizeof.FLOAT);
        cuMemcpyHtoD(deviceInputB, Pointer.to(hostInputB), numElements * Sizeof.FLOAT);

        // Allocate device output memory
        CUdeviceptr deviceOutput = new CUdeviceptr();
        cuMemAlloc(deviceOutput, numElements * Sizeof.FLOAT);

        // Set up the kernel parameters: A pointer to an array
        // of pointers which point to the actual values.
        Pointer kernelParameters = Pointer.to(
            Pointer.to(new int[]{numElements}),
            Pointer.to(deviceInputA),
            Pointer.to(deviceInputB),
            Pointer.to(deviceOutput)
        );

        // Call the kernel function.
       cudaDeviceProp prop = new cudaDeviceProp();

        cudaGetDeviceProperties(prop, 0);
        int blockSizeX =  prop.maxThreadsDim[0];
        
        
        int gridSizeX = (int)Math.ceil((double)numElements / blockSizeX);
        
        Pointer p14= Pointer.to(deviceInputB);
        cudaMallocHost(p14, numElements* Sizeof.INT);
        
        
         long t1 = System.nanoTime();


        cuLaunchKernel(function,
            gridSizeX,  1, 1,      // Grid dimension
            blockSizeX, 1, 1,      // Block dimension
            0, null,               // Shared memory size and stream
            kernelParameters, null // Kernel- and extra parameters
        );
        cuCtxSynchronize();
         long t2 = System.nanoTime();
        float hostOutput[] = new float[numElements];
        cuMemcpyDtoH(Pointer.to(hostOutput), deviceOutput,
            numElements * Sizeof.FLOAT);
        
         System.out.println("Time "+(t2-t1));
       
        // Allocate host output memory and copy the device output
        // to the host.
        // Verify the result
        boolean passed = true;
        for(int i = 0; i < numElements; i++)
        {
            //float expected = i+i;
            //System.out.println(hostOutput[i]+"  "+expected);
            
            /*if (Math.abs(hostOutput[i] - expected) > 1e-5)
            {
                System.out.println(
                    "At index "+i+ " found "+hostOutput[i]+
                    " but expected "+expected);
                passed = false;
                break;
            }*/
        }
        System.out.println("Test "+(passed?"PASSED":"FAILED"));

        // Clean up.
        cuMemFree(deviceInputA);
        cuMemFree(deviceInputB);
        cuMemFree(deviceOutput);
        
        File file = new File(ptxFileName);
        file.delete();
		return hostOutput;
    }

    /**
     * The extension of the given file name is replaced with "ptx".
     * If the file with the resulting name does not exist, it is
     * compiled from the given file using NVCC. The name of the
     * PTX file is returned.
     *
     * @param cuFileName The name of the .CU file
     * @return The name of the PTX file
     * @throws IOException If an I/O error occurs
     */
    private static String preparePtxFile(String cuFileName) throws IOException
    {
        int endIndex = cuFileName.lastIndexOf('.');
        if (endIndex == -1)
        {
            endIndex = cuFileName.length()-1;
        }
        
        String ptxFileName = cuFileName.substring(0, endIndex+1)+"ptx";
        File ptxFile = new File(ptxFileName);
        if (ptxFile.exists())
        {
            return ptxFileName;
        }
        
        File cuFile = new File(cuFileName);
        if (!cuFile.exists())
        {
            throw new IOException("Input file not found: "+cuFileName);
        }
        System.out.println("cuda file there");
        String modelString = "-m"+System.getProperty("sun.arch.data.model");
        String command =
            "nvcc " + modelString + " -ptx "+
            cuFile.getPath()+" -o "+ptxFileName;

        System.out.println("Executing\n"+command);
        Process process = Runtime.getRuntime().exec(command);

        String errorMessage =
            new String(toByteArray(process.getErrorStream()));
        String outputMessage =
            new String(toByteArray(process.getInputStream()));
        int exitValue = 0;
        try
        {
            exitValue = process.waitFor();
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new IOException(
                "Interrupted while waiting for nvcc output", e);
        }

        if (exitValue != 0)
        {
            System.out.println("nvcc process exitValue "+exitValue);
            System.out.println("errorMessage:\n"+errorMessage);
            System.out.println("outputMessage:\n"+outputMessage);
            throw new IOException(
                "Could not create .ptx file: "+errorMessage);
        }

        System.out.println("Finished creating PTX file");
        return ptxFileName;
    }

    /**
     * Fully reads the given InputStream and returns it as a byte array
     *
     * @param inputStream The input stream to read
     * @return The byte array containing the data from the input stream
     * @throws IOException If an I/O error occurs
     */
    private static byte[] toByteArray(InputStream inputStream)
        throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buffer[] = new byte[8192];
        while (true)
        {
            int read = inputStream.read(buffer);
            if (read == -1)
            {
                break;
            }
            baos.write(buffer, 0, read);
        }
        return baos.toByteArray();
    }


}