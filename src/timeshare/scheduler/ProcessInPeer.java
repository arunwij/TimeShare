/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.scheduler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sanjula
 */
public class ProcessInPeer {

    static P2PJavaCompiler compiler;

    public synchronized void compileFile(String file) {
        InputStream is;
        try {
            is = new FileInputStream(file);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            String source = sb.toString();

            compiler = new P2PJavaCompiler(file, source);
            System.out.println("file  complied");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProcessInPeer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcessInPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized <Any> Any run(String className, String methodName, Object data_a[][][], int datacount) {
        
        final Method m1, m2;
        Object[] params = new Object[datacount];
        Object result = null;
        final Object results;
        try {
            //
            m1 = compiler.compileMethod(methodName, className);
            int[] argss = new int[1];
            argss[0] = 8;
            //argss[1]=9; 
            int lenth = 10;
            float[] addarr = new float[lenth];
            //result = m1.invoke(null, condatatype(nums, m1.getReturnType()));
            Class<?>[] parameterTypes = m1.getParameterTypes();
            for (int cc = 0; cc < datacount; cc++) {
              /*  for (Map.Entry m : data_a[cc].entrySet()) {
             */       
                    params[cc] = data_a[cc];
                    System.out.println(data_a[cc].getClass());
               /* }*/

            }
            if (datacount == 1) {
                result = m1.invoke(null, params[0]);
            } else if (datacount == 2) {
                result = m1.invoke(null, params[0], params[1]);
            } else if (datacount == 3) {
                result = m1.invoke(null, params[0], params[1], params[2]);
            } else if (datacount == 4) {
                result = m1.invoke(null, params[0], params[1], params[2], params[3]);
            } else if (datacount == 5) {
                result = m1.invoke(null, params[0], params[1], params[2], params[3], params[4]);
            } else if (datacount == 6) {
                result = m1.invoke(null, params[0], params[1], params[2], params[3], params[4], params[5]);
            } else if (datacount == 7) {
                result = m1.invoke(null, params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
            } else if (datacount == 8) {
                result = m1.invoke(null, params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
            } else if (datacount == 9) {
                result = m1.invoke(null, params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]);
            } else if (datacount == 10) {
                result = m1.invoke(null, params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9]);

            }

            // float[] farr = conObj(result, m1.getReturnType());
            return (Any) result;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException ex) {
            System.out.println(ex);;
        }
        return null;
    }

    public synchronized <Any> Any conObj(Object object, Class type) {
        //for types
        switch (type.toString()) {
            case "class [F": {
                float[] arr = (float[]) object;
                return (Any) arr;
            }
            case "class [D": {
                double[] arr = (double[]) object;
                return (Any) arr;
            }
            case "class [I": {
                int[] arr = (int[]) object;
                return (Any) arr;
            }
            case "class [B": {
                boolean[] arr = (boolean[]) object;
                return (Any) arr;
            }
            case "class [Ljava.lang.String": {
                String arr = (String) object;
                return (Any) arr;
            }
            case "class [Ljava.lang.Integer": {
                Integer[] arr = (Integer[]) object;
                return (Any) arr;
            }
            case "int":
            case "double":
            case "boolean":
            case "float":
                return (Any) type.cast(object);
            default:
                break;
        }

        return (Any) object;
    }

    public synchronized <Any> Any conObjMap(Object object, Class type) {
        //for types
        switch (type.toString()) {
            case "class [F": {
                float[] arr = (float[]) object;
                return (Any) arr;
            }
            case "class [D": {
                double[] arr = (double[]) object;
                return (Any) arr;
            }
            case "class [I": {
                int[] arr = (int[]) object;
                return (Any) arr;
            }
            case "class [B": {
                boolean[] arr = (boolean[]) object;
                return (Any) arr;
            }
            case "class [Ljava.lang.String": {
                String arr = (String) object;
                return (Any) arr;
            }
            case "class [Ljava.lang.Integer": {
                Integer[] arr = (Integer[]) object;
                return (Any) arr;
            }
            case "int":
            case "double":
            case "boolean":
            case "float":
                return (Any) type.cast(object);
            default:
                break;
        }

        return (Any) object;
    }

}
