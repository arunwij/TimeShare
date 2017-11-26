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
public class ArraySplit {

    static String[][] source = new String[10][5];
    static int[][] parts = new int[2][5];

    static double[][] source_d = new double[10][10];
    static double[][][] parts_d = new double[5][10][2];

    /*public synchronized static int[][][] splitData(int org[][], int col,int row) {
        
        int[][][] split = new int[(org[0].length/col)/row][col][row];

        return split;
        
    }*/
    public synchronized static String[][][] splitDataCol2D(String org[][], int peers) {
        int arrLenghtmod = org.length % peers;
        int arrLenght = org.length / peers;
        
        if (arrLenghtmod>0) {
            arrLenght++;
        }
        String[][][] split = new String[peers][org[0].length][arrLenght];

        for (int a = 0; a < org.length; a++) {
            for (int b = 0; b < org[0].length; b++) {

                System.arraycopy(org[a], b, split[a / arrLenght][b], a % arrLenght, 1);

            }
        }
        return split;
    }

    public synchronized static String[][][] splitDataRow2D(String org[][], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        //System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        String[][][] split = new String[peers][(int) arrLenght][org[0].length];

        for (int a = 0; a < split.length; a++) {
            //System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            if ((a + 1) * split[0].length <= org.length) {
                System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            } else {
                System.out.println(a * split[0].length);
                System.arraycopy(org, a * split[0].length, split[a], 0, org.length - (a * split[0].length));
            }
        }

        return split;
    }

    public synchronized static String[][] splitDataRow1D(String org[], int peers) {
        System.out.println(org.length);
        System.out.println(peers);
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        //System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        System.out.println(arrLenght);
        String[][] split = new String[peers][(int) arrLenght];

        for (int a = 0; a < split.length; a++) {
            if ((a + 1) * split[0].length <= org.length) {
                System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            } else {
                System.out.println(a * split[0].length);
                System.arraycopy(org, a * split[0].length, split[a], 0, org.length - (a * split[0].length));
            }
        }

        return split;
    }

    public synchronized static int[][][] splitDataCol2D(int org[][], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
       // System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        int[][][] split = new int[peers][org.length][arrLenght];
        for (int a = 0; a < org.length; a++) {
            for (int b = 0; b < org[0].length; b++) {
                split[b/ arrLenght][a][b % arrLenght]=org[a][b];
               // System.arraycopy(org[a], b, split[ a % arrLenght][b],a / arrLenght, 1);

            }
        }
        return split;
    }

    public synchronized static int[][][] splitDataRow2D(int org[][], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        int[][][] split = new int[peers][(int) arrLenght][org[0].length];

        for (int a = 0; a < split.length; a++) {
            //System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            if ((a + 1) * split[0].length <= org.length) {
                System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            } else {
                System.out.println(a * split[0].length);
                System.arraycopy(org, a * split[0].length, split[a], 0, org.length - (a * split[0].length));
            }
        }

        return split;
    }

    public synchronized static int[][] splitDataRow1D(int org[], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        System.out.println(arrLenght);
        int[][] split = new int[peers][(int) arrLenght];

        for (int a = 0; a < split.length; a++) {
            if ((a + 1) * split[0].length <= org.length) {
                System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            } else {
                System.out.println(a * split[0].length);
                System.arraycopy(org, a * split[0].length, split[a], 0, org.length - (a * split[0].length));
            }
        }

        return split;
    }

    public synchronized static double[][][] splitDataCol2D(double org[][], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        double[][][] split = new double[peers][org[0].length][arrLenght];

        for (int a = 0; a < org.length; a++) {
            for (int b = 0; b < org[0].length; b++) {

                System.arraycopy(org[a], b, split[a / arrLenght][b], a % arrLenght, 1);

            }
        }
        return split;
    }

    public synchronized static double[][][] splitDataRow2D(double org[][], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        double[][][] split = new double[peers][(int) arrLenght][org[0].length];

        for (int a = 0; a < split.length; a++) {
            //System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            if ((a + 1) * split[0].length <= org.length) {
                System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            } else {
                System.out.println(a * split[0].length);
                System.arraycopy(org, a * split[0].length, split[a], 0, org.length - (a * split[0].length));
            }
        }

        return split;
    }

    public synchronized static double[][] splitDataRow1D(double org[], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        System.out.println(arrLenght);
        double[][] split = new double[peers][(int) arrLenght];

        for (int a = 0; a < split.length; a++) {
            if ((a + 1) * split[0].length <= org.length) {
                System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            } else {
                System.out.println(a * split[0].length);
                System.arraycopy(org, a * split[0].length, split[a], 0, org.length - (a * split[0].length));
            }
        }

        return split;
    }

    public synchronized static float[][][] splitDataCol2D(float org[][], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        float[][][] split = new float[peers][org[0].length][arrLenght];

        for (int a = 0; a < org.length; a++) {
            for (int b = 0; b < org[0].length; b++) {

                System.arraycopy(org[a], b, split[a / arrLenght][b], a % arrLenght, 1);

            }
        }
        return split;
    }

    public synchronized static float[][][] splitDataRow2D(float org[][], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        float[][][] split = new float[peers][(int) arrLenght][org[0].length];

        for (int a = 0; a < split.length; a++) {
            //System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            if ((a + 1) * split[0].length <= org.length) {
                System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            } else {
                System.out.println(a * split[0].length);
                System.arraycopy(org, a * split[0].length, split[a], 0, org.length - (a * split[0].length));
            }
        }

        return split;
    }

    public synchronized static float[][] splitDataRow1D(float org[], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        System.out.println(arrLenght);
        float[][] split = new float[peers][(int) arrLenght];

        for (int a = 0; a < split.length; a++) {
            if ((a + 1) * split[0].length <= org.length) {
                System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            } else {
                System.out.println(a * split[0].length);
                System.arraycopy(org, a * split[0].length, split[a], 0, org.length - (a * split[0].length));
            }
        }

        return split;
    }

    public synchronized static Object[][][] splitDataCol2D(Object org[][], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        Object[][][] split = new Object[peers][org[0].length][arrLenght];

        for (int a = 0; a < org.length; a++) {
            for (int b = 0; b < org[0].length; b++) {

                System.arraycopy(org[a], b, split[a / arrLenght][b], a % arrLenght, 1);

            }
        }
        return split;
    }

    public synchronized static Object[][][] splitDataRow2D(Object org[][], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        Object[][][] split = new Object[peers][(int) arrLenght][org[0].length];

        for (int a = 0; a < split.length; a++) {
            //System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            if ((a + 1) * split[0].length <= org.length) {
                System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            } else {
                System.out.println(a * split[0].length);
                System.arraycopy(org, a * split[0].length, split[a], 0, org.length - (a * split[0].length));
            }
        }

        return split;
    }

    public synchronized static Object[][] splitDataRow1D(Object org[], int peers) {
        double arrLenghtD = (double) org.length / peers;
        int arrLenght = org.length / peers;
        System.out.println(arrLenghtD + " " + (double) arrLenght);
        if (arrLenghtD != (double) arrLenght) {
            arrLenght++;
        }
        System.out.println(arrLenght);
        Object[][] split = new Object[peers][(int) arrLenght];

        for (int a = 0; a < split.length; a++) {
            if ((a + 1) * split[0].length <= org.length) {
                System.arraycopy(org, a * split[0].length, split[a], 0, split[0].length);
            } else {
                System.out.println(a * split[0].length);
                System.arraycopy(org, a * split[0].length, split[a], 0, org.length - (a * split[0].length));
            }
        }

        return split;
    }



    public static void main(String[] args) throws Exception {

        //adddata();
        String splitdata[][][] = splitDataCol2D(source, 4);
        for (int a = 0; a < source.length; a++) {

            for (int b = 0; b < source[0].length; b++) {
                System.out.print(source[a][b] + " \t");

            }
            System.out.println("");
        }
        for (int a = 0; a < splitdata.length; a++) {
            System.out.println("part " + a);
            for (int b = 0; b < splitdata[0].length; b++) {
                for (int c = 0; c < splitdata[0][0].length; c++) {
                    System.out.print(splitdata[a][b][c] + " ");
                }
                System.out.println("");
            }
            System.out.println("");
        }

    }
}
