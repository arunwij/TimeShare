/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timeshare.allocator;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/*
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
/**
 *
 * @author Sanjula
 */
public class ConvertDataType {

    public int[] arrayLenght = {0, 0};
    public int[][] int_arr_2d;
    public double[][] double_arr_2d;
    public float[][] float_arr_2d;

    public Object conObj(String object, String type) {
        //for types
        if (type.equals("float[]")) {
            String[] sarr = object.replace("[", "").replace("]", "").split(",");

            try {
                arrayLenght[0] = sarr.length;
                float[] arr = new float[sarr.length];
                for (int x = 0; x < sarr.length; x++) {
                    arr[x] = Float.parseFloat(sarr[x]);
                }
                return (Object) arr;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to float array.\n" + ex);
            }
        } else if (type.equals("float[][]")) {
            StringBuffer sb = new StringBuffer(object);
            String obj = object.replace(",[", "").replace("[", "");
            String[] ssarr = obj.split("]");
            String[] syarr = ssarr[0].split(",");

            try {
                arrayLenght[0] = ssarr.length;
                arrayLenght[1] = syarr.length;
                float[][] arr = new float[ssarr.length][syarr.length];
                for (int y = 0; y < ssarr.length; y++) {
                    String[] sarr = ssarr[y].trim().split(",");
                    for (int x = 0; x < sarr.length; x++) {
                        arr[y][x] = Integer.parseInt(sarr[x]);
                    }
                }

                return (Object) arr;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to float 2d array.\n" + ex);
            }
        } else if (type.equals("double[]")) {
            try {
                String[] sarr = object.replace("[", "").replace("]", "").split(",");
                arrayLenght[0] = sarr.length;
                double[] arr = new double[sarr.length];
                for (int x = 0; x < sarr.length; x++) {
                    arr[x] = Double.parseDouble(sarr[x]);
                }
                return (Object) arr;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to double array.\n" + ex);
            }
        } else if (type.equals("double[][]")) {
            StringBuffer sb = new StringBuffer(object);
            String obj = object.replace(",[", "").replace("[", "");
            String[] ssarr = obj.split("]");
            String[] syarr = ssarr[0].split(",");

            try {
                arrayLenght[0] = ssarr.length;
                arrayLenght[1] = syarr.length;
                double[][] arr = new double[ssarr.length][syarr.length];
                for (int y = 0; y < ssarr.length; y++) {
                    String[] sarr = ssarr[y].trim().split(",");
                    for (int x = 0; x < sarr.length; x++) {
                        arr[y][x] = Integer.parseInt(sarr[x]);
                    }
                }

                return (Object) arr;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to double 2d array.\n" + ex);
            }
        } else if (type.equals("int[]")) {
            try {
                String[] sarr = object.replace("[", "").replace("]", "").split(",");
                arrayLenght[0] = sarr.length;
                int[] arr = new int[sarr.length];
                for (int x = 0; x < sarr.length; x++) {
                    arr[x] = Integer.parseInt(sarr[x]);
                }
                return (Object) arr;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to int array.\n" + ex);
            }
        } else if (type.equals("int[][]")) {
            StringBuffer sb = new StringBuffer(object);
            String obj = object.replace(",[", "").replace("[", "");
            String[] ssarr = obj.split("]");
            String[] syarr = ssarr[0].split(",");

            try {
                arrayLenght[0] = ssarr.length;
                arrayLenght[1] = syarr.length;
                int[][] arr = new int[ssarr.length][syarr.length];
                for (int y = 0; y < ssarr.length; y++) {
                    String[] sarr = ssarr[y].trim().split(",");
                    for (int x = 0; x < sarr.length; x++) {
                        arr[y][x] = Integer.parseInt(sarr[x]);
                    }
                }
                int_arr_2d = new int[ssarr.length][syarr.length];
                int_arr_2d = arr;
                return (Object) arr;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to String 2d array.\n" + ex);
            }
        } else if (type.toLowerCase().equals("string[]")) {
            try {

                String[] arr = object.replace("[", "").replace("]", "").split(",");
                return (Object) arr;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to String array.\n" + ex);
            }
        } else if (type.toLowerCase().equals("string[][]")) {
            StringBuffer sb = new StringBuffer(object);
            String obj = object.replace(",[", "").replace("[", "");
            String[] ssarr = obj.split("]");
            String[] syarr = ssarr[0].split(",");

            try {
                arrayLenght[0] = ssarr.length;
                arrayLenght[1] = syarr.length;
                String[][] arr = new String[ssarr.length][syarr.length];
                for (int y = 0; y < ssarr.length; y++) {
                    String[] sarr = ssarr[y].trim().split(",");
                    for (int x = 0; x < sarr.length; x++) {
                        arr[y][x] = sarr[x];
                    }
                }
                return (Object) arr;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to int 2d array.\n" + ex);
            }
        } else if (type.toLowerCase().equals("string")) {
            String arr = (String) object;
            return (Object) arr;
        } else if (type.equals("int")) {
            try {
                int val = Integer.parseInt(object);
                return (Object) (Integer) val;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to int.\n" + ex);
            }
        } else if (type.equals("double")) {

            try {
                Float val = Float.parseFloat(object);
                return (Object) new Double(val);
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to double.\n" + ex);
            }
        } else if (type.equals("float")) {
            try {
                return (Object) new Float(object);
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to float .\n" + ex);
            }
        } else if (type.equals("void")) {
            return null;
        }

        return null;
    }

    public Map<String, Object> conObjMap(String object, String type) {
        //for types
        Map<String, Object> map = new HashMap<>();
        if (type.toLowerCase().equals("float[]")) {
            String[] sarr = object.split(",");

            try {
                float[] arr = new float[sarr.length];
                for (int x = 0; x < sarr.length; x++) {
                    arr[x] = Float.parseFloat(sarr[x]);
                }
                map.put(arr.getClass().getName(), conObj(object, type));
                return map;

                //return (Map<String, Object>) map.entrySet().;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to float array.\n" + ex);
            }
        }
        if (type.toLowerCase().equals("float[]")) {
            String[] sarr = object.split(",");

            try {
                float[] arr = new float[sarr.length];
                for (int x = 0; x < sarr.length; x++) {
                    arr[x] = Float.parseFloat(sarr[x]);
                }
                map.put(arr.getClass().getName(), conObj(object, type));
                return map;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to float array.\n" + ex);
            }
        } else if (type.toLowerCase().equals("double[]")) {
            try {
                String[] sarr = object.split(",");
                double[] arr = new double[sarr.length];
                for (int x = 0; x < sarr.length; x++) {
                    arr[x] = Double.parseDouble(sarr[x]);
                }
                map.put(arr.getClass().getName(), conObj(object, type));
                return map;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to double array.\n" + ex);
            }
        } else if (type.toLowerCase().equals("int[]")) {
            try {
                String[] sarr = object.split(",");
                int[] arr = new int[sarr.length];
                for (int x = 0; x < sarr.length; x++) {
                    arr[x] = Integer.parseInt(sarr[x]);
                }
                map.put(arr.getClass().getName(), conObj(object, type));
                return map;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to int array.\n" + ex);
            }
        } else if (type.toLowerCase().equals("string")) {
            String arr = (String) object;
            map.put(arr.getClass().getName(), conObj(object, type));
            return map;
        } else if (type.toLowerCase().equals("int")) {
            try {
                Integer val = Integer.parseInt(object);

                map.put(val.getClass().getName(), conObj(object, type));

                return map;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to int.\n" + ex);
            }
        } else if (type.toLowerCase().equals("double")) {

            try {
                Double val = Double.parseDouble(object);

                map.put(val.getClass().getName(), conObj(object, type));
                return map;

            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to double.\n" + ex);
            }
        } else if (type.toLowerCase().equals("float")) {
            try {
                Float val = Float.parseFloat(object);

                map.put(val.getClass().getName(), conObj(object, type));
                return map;
            } catch (NumberFormatException ex) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Unable to converted to float .\n" + ex);
            }
        } else if (type.toLowerCase().equals("void")) {
            return null;
        }

        return null;

    }

}
