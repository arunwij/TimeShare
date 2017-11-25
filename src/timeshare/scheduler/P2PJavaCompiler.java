/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.scheduler;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.*;

/**
 * Simple interface to Java compiler using JSR 199 Compiler API.
 */
 
public class P2PJavaCompiler {
    private javax.tools.JavaCompiler tool;
    private StandardJavaFileManager stdManager;
    private static String className;
    private static String source;
    private  Map<String, byte[]> classBytes;
    private static P2PClassLoader classLoader;
    private Class clazz;
    public P2PJavaCompiler() {
        tool = ToolProvider.getSystemJavaCompiler();
        if (tool == null) {
            throw new RuntimeException("Could not get Java compiler. Please, ensure that JDK is used instead of JRE.");
        }
        stdManager = tool.getStandardFileManager(null, null, null);
    }
    
    public P2PJavaCompiler(String fileName, String source) {
        tool = ToolProvider.getSystemJavaCompiler();
        if (tool == null) {
            throw new RuntimeException("Could not get Java compiler. Please, ensure that JDK is used instead of JRE.");
        }
        stdManager = tool.getStandardFileManager(null, null, null);
        compile(fileName,source) ;
    }

    /**
     * Compile a single static method.
     */
    
    public synchronized Method compileMethod( String methodName,String className)
        throws ClassNotFoundException {
        
        classLoader = new P2PClassLoader(classBytes);
        System.out.println(className);
        clazz = classLoader.loadClass(className);
         Method[] methods = clazz.getDeclaredMethods();
        for ( Method method : methods) {
            System.out.println("method"+method.getName());
            if (method.getName().equals(methodName)) {
                if (!method.isAccessible()) method.setAccessible(true);
                return method;
            }
        }
        throw new NoSuchMethodError(methodName);
    }
    
    public synchronized void ctrate_instance(int a){
            
            //Class c = classLoader.forName(className);
            Constructor cons;
        try {
            cons = clazz.getConstructor();
            Object o=  cons.newInstance();
            Field [] f = clazz.getDeclaredFields();
            f[0].set("ab", a);
            System.out.println( f[0].getName()+ f[0].get(o).toString());
            
            
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(P2PJavaCompiler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(P2PJavaCompiler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(P2PJavaCompiler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(P2PJavaCompiler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(P2PJavaCompiler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(P2PJavaCompiler.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }


    public synchronized Map<String, byte[]> compile(String fileName, String source) {
       this.source=source;
       
        return compile(fileName, source, new PrintWriter(System.err), null, null);
    }


    /**
     * compile given String source and return bytecodes as a Map.
     *
     * @param fileName source fileName to be used for error messages etc.
     * @param source Java source as String
     * @param err error writer where diagnostic messages are written
     * @param sourcePath location of additional .java source files
     * @param classPath location of additional .class files
     */
    private Map<String, byte[]> compile(String fileName, String source,
        Writer err, String sourcePath, String classPath) {
        // to collect errors, warnings etc.
        DiagnosticCollector<JavaFileObject> diagnostics =
            new DiagnosticCollector<JavaFileObject>();

        // create a new memory JavaFileManager
        P2PJavaFileManager fileManager = new P2PJavaFileManager(stdManager);

        // prepare the compilation unit
        List<JavaFileObject> compUnits = new ArrayList<JavaFileObject>(1);
        compUnits.add(fileManager.makeStringSource(fileName, source));

        return compile(compUnits, fileManager, err, sourcePath, classPath);
    }

    private Map<String, byte[]> compile(final List<JavaFileObject> compUnits,
        final P2PJavaFileManager fileManager,
        Writer err, String sourcePath, String classPath) {
        // to collect errors, warnings etc.
        DiagnosticCollector<JavaFileObject> diagnostics =
            new DiagnosticCollector<JavaFileObject>();

        // javac options
        List<String> options = new ArrayList<String>();
        options.add("-Xlint:all");
        //       options.add("-g:none");
        options.add("-deprecation");
        if (sourcePath != null) {
            options.add("-sourcepath");
            options.add(sourcePath);
        }

        if (classPath != null) {
            options.add("-classpath");
            options.add(classPath);
        }

        // create a compilation task
        javax.tools.JavaCompiler.CompilationTask task =
            tool.getTask(err, fileManager, diagnostics,
                options, null, compUnits);

        if (task.call() == false) {
            PrintWriter perr = new PrintWriter(err);
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                perr.println(diagnostic);
            }
            perr.flush();
            return null;
        }

        Map<String, byte[]> classBytes = fileManager.getClassBytes();
        try {
            fileManager.close();
        } catch (IOException ex) {
            Logger.getLogger(P2PJavaCompiler.class.getName()).log(Level.SEVERE, null, ex);
            //classBytes=null;
        }
        this.classBytes=classBytes;
        
        return classBytes;
    }
}