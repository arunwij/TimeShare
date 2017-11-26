/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

/**
 *
 * @author Sanjula
 */
class ArrayBroadcast {

    static boolean[][][] boolean2D(boolean[][] b, int peerCount) {
        boolean arr[][][] = new boolean[peerCount][b.length][b[0].length];
        for (int i = 0; i < peerCount; i++) {
            arr[i]= b;
        }
        return arr;
    }

    static float[][][] Float2D(float[][] f, int peerCount) {
        float arr[][][] = new float[peerCount][f.length][f[0].length];
        for (int i = 0; i < peerCount; i++) {
            arr[i]= f;
        }
        return arr;
    }

    static double[][][] Double2D(double[][] d, int peerCount) {
        double arr[][][] = new double[peerCount][d.length][d[0].length];
        for (int i = 0; i < peerCount; i++) {
            arr[i]= d;
        }
        return arr;
    }

    static int[][][] Int2D(int[][] d, int peerCount) {
        int arr[][][] = new int[peerCount][d.length][d[0].length];
        for (int i = 0; i < peerCount; i++) {
            arr[i]= d;
        }
        return arr;
    }

    static String[][][] String2D(String[][] d, int peerCount) {
        String arr[][][] = new String[peerCount][d.length][d[0].length];
        for (int i = 0; i < peerCount; i++) {
            arr[i]= d;
        }
        return arr;
    }

    static String[][] String1D(String[] d, int peerCount) {
        String arr[][] = new String[peerCount][d.length];
        for (int i = 0; i < peerCount; i++) {
            arr[i]= d;
        }
        return arr;
    }

    static int[][] Int1D(int[] d, int peerCount) {
        int arr[][] = new int[peerCount][d.length];
        for (int i = 0; i < peerCount; i++) {
            arr[i]= d;
        }
        return arr;
    }

    static double[][] Double1D(double[] d, int peerCount) {
        double arr[][] = new double[peerCount][d.length];
        for (int i = 0; i < peerCount; i++) {
            arr[i]= d;
        }
        return arr;
    }

    static float[][] Float1D(float[] d, int peerCount) {
        float arr[][] = new float[peerCount][d.length];
        for (int i = 0; i < peerCount; i++) {
            arr[i]= d;
        }
        return arr;
    }

    static boolean[][] boolean1D(boolean[] d, int peerCount) {
        boolean arr[][] = new boolean[peerCount][d.length];
        for (int i = 0; i < peerCount; i++) {
            arr[i]= d;
        }
        return arr;
    }
    
    
}
