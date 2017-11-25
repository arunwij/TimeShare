/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kademlia.file;

import com.google.gson.Gson;
/**
 *
 * @author Artista
 */
public class Serializer {
    
    public static final String INT1D = "[I";
    public static final String INT2D = "[[I";
    public static final String INT3D = "[[[I";
    public static final String STR1D = "[S";
    public static final String STR2D = "[[S";
    public static final String STR3D = "[[[S";
    public static final String STR = "S";
    public static final String OBJ1D = "[Ljava.lang.Object";
    
    public enum CLASSTYPE{
        INT1D, INT2D, INT3D, STR1D,STR3D, STR2D,STR, OBJ1D
    }
    
    public static String toJson(Object obj){
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }
    
    public static int[][] getInt2d(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,int[][].class);
              
    }
    
    public static int[][][] getInt3d(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,int[][][].class);
              
    }
   
    public static int[] getInt1d(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,int[].class);
              
    }
    
    public static String[] getStr1d(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,String[].class);
              
    }
    public static String getStr(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,String.class);
              
    }
    
    public static String[][] getStr2d(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,String[][].class);
              
    }
    public static String[][][] getStr3d(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,String[][][].class);
              
    }
    
}
